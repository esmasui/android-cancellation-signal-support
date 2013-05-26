/*
 * Copyright (C) 2013 uPhyca Inc. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uphyca.support.v4.database.sqlite;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Build;

import com.uphyca.support.v4.os.CancellationSignalCompat;
import com.uphyca.support.v4.os.ExceptionConverter;

public class SQLiteQueryBuilderCompat extends SQLiteQueryBuilder {

    public Cursor query(SQLiteDatabaseCompat db, String[] projectionIn, String selection, String[] selectionArgs, String groupBy, String having, String sortOrder, String limit, CancellationSignalCompat cancellationSignal) {
        try {
            return SQLiteCursorCompat.newInstance(sQueryExecutor.query(this, db, projectionIn, selection, selectionArgs, groupBy, having, sortOrder, limit, cancellationSignal));
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    private static final QueryExecutor sQueryExecutor = newQueryExecutor();

    private static final QueryExecutor newQueryExecutor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return ModernQueryExecutorFactory.newInstance();
        } else {
            return LegacyQueryExecutorFactory.newInstance();
        }
    }

    private static final class ModernQueryExecutorFactory {
        static final QueryExecutor newInstance() {
            return new ModernQueryExecutor();
        }
    }

    private static final class LegacyQueryExecutorFactory {
        static final QueryExecutor newInstance() {
            return new LegacyQueryExecutor();
        }
    }

    private static interface QueryExecutor {
        Cursor query(SQLiteQueryBuilderCompat builder, SQLiteDatabaseCompat db, String[] projectionIn, String selection, String[] selectionArgs, String groupBy, String having, String sortOrder, String limit, CancellationSignalCompat cancellationSignal);
    }

    private static final class LegacyQueryExecutor implements QueryExecutor {
        @Override
        public Cursor query(SQLiteQueryBuilderCompat builder, SQLiteDatabaseCompat db, String[] projectionIn, String selection, String[] selectionArgs, String groupBy, String having, String sortOrder, String limit, CancellationSignalCompat cancellationSignal) {
            return builder.query(db.mUnderlying, projectionIn, selection, selectionArgs, groupBy, having, sortOrder, limit);
        }
    }

    @SuppressLint("NewApi")
    private static final class ModernQueryExecutor implements QueryExecutor {
        @Override
        public Cursor query(SQLiteQueryBuilderCompat builder, SQLiteDatabaseCompat db, String[] projectionIn, String selection, String[] selectionArgs, String groupBy, String having, String sortOrder, String limit, CancellationSignalCompat cancellationSignal) {
            if (cancellationSignal == null) {
                return builder.query(db.mUnderlying, projectionIn, selection, selectionArgs, groupBy, having, sortOrder, limit);
            } else {
                return builder.query(db.mUnderlying, projectionIn, selection, selectionArgs, groupBy, having, sortOrder, limit, cancellationSignal.toCancellationSignal());
            }
        }
    }
}
