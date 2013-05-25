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
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.os.Build;

import com.uphyca.support.v4.os.CancellationSignalCompat;
import com.uphyca.support.v4.os.ExceptionConverter;

public final class SQLiteDatabaseCompat extends SQLiteDatabaseWrapper {

    public static final SQLiteDatabaseCompat newInstance(SQLiteDatabase underlying) {
        if (underlying == null) {
            return null;
        }
        return new SQLiteDatabaseCompat(underlying);
    }

    private SQLiteDatabaseCompat(SQLiteDatabase underlying) {
        super(underlying);
    }

    public final Cursor query(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit, CancellationSignalCompat cancellationSignal) {
        try {
            return SQLiteCursorCompat.newInstance(sQueryExecutor.query(this, distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit, cancellationSignal));
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    public final Cursor queryWithFactory(CursorFactory cursorFactory, boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit, CancellationSignalCompat cancellationSignal) {
        try {
            return SQLiteCursorCompat.newInstance(sQueryExecutor.queryWithFactory(this, cursorFactory, distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit, cancellationSignal));
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    public final Cursor rawQuery(String sql, String[] selectionArgs, CancellationSignalCompat cancellationSignal) {
        try {
            return SQLiteCursorCompat.newInstance(sQueryExecutor.rawQuery(this, sql, selectionArgs, cancellationSignal));
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    public final Cursor rawQueryWithFactory(CursorFactory cursorFactory, String sql, String[] selectionArgs, String editTable, CancellationSignalCompat cancellationSignal) {
        try {
            return SQLiteCursorCompat.newInstance(sQueryExecutor.rawQueryWithFactory(this, cursorFactory, sql, selectionArgs, editTable, cancellationSignal));
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
        Cursor query(SQLiteDatabaseCompat db, boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit, CancellationSignalCompat cancellationSignal);

        Cursor queryWithFactory(SQLiteDatabaseCompat db, CursorFactory cursorFactory, boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit, CancellationSignalCompat cancellationSignal);

        Cursor rawQuery(SQLiteDatabaseCompat db, String sql, String[] selectionArgs, CancellationSignalCompat cancellationSignal);

        Cursor rawQueryWithFactory(SQLiteDatabaseCompat db, CursorFactory cursorFactory, String sql, String[] selectionArgs, String editTable, CancellationSignalCompat cancellationSignal);
    }

    private static final class LegacyQueryExecutor implements QueryExecutor {

        @Override
        public Cursor query(SQLiteDatabaseCompat db, boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit, CancellationSignalCompat cancellationSignal) {
            return db.mUnderlying.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        }

        @Override
        public Cursor queryWithFactory(SQLiteDatabaseCompat db, CursorFactory cursorFactory, boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit, CancellationSignalCompat cancellationSignal) {
            return db.mUnderlying.queryWithFactory(cursorFactory, distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        }

        @Override
        public Cursor rawQuery(SQLiteDatabaseCompat db, String sql, String[] selectionArgs, CancellationSignalCompat cancellationSignal) {
            return db.mUnderlying.rawQuery(sql, selectionArgs);
        }

        @Override
        public Cursor rawQueryWithFactory(SQLiteDatabaseCompat db, CursorFactory cursorFactory, String sql, String[] selectionArgs, String editTable, CancellationSignalCompat cancellationSignal) {
            return db.mUnderlying.rawQueryWithFactory(cursorFactory, sql, selectionArgs, editTable);
        }
    }

    @SuppressLint("NewApi")
    private static final class ModernQueryExecutor implements QueryExecutor {

        @Override
        public Cursor query(SQLiteDatabaseCompat db, boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit, CancellationSignalCompat cancellationSignal) {
            if (cancellationSignal == null) {
                return db.mUnderlying.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
            } else {
                return db.mUnderlying.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit, cancellationSignal.toCancellationSignal());
            }
        }

        @Override
        public Cursor queryWithFactory(SQLiteDatabaseCompat db, CursorFactory cursorFactory, boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit, CancellationSignalCompat cancellationSignal) {
            if (cancellationSignal == null) {
                return db.mUnderlying.queryWithFactory(cursorFactory, distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
            } else {
                return db.mUnderlying.queryWithFactory(cursorFactory, distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit, cancellationSignal.toCancellationSignal());
            }
        }

        @Override
        public Cursor rawQuery(SQLiteDatabaseCompat db, String sql, String[] selectionArgs, CancellationSignalCompat cancellationSignal) {
            if (cancellationSignal == null) {
                return db.mUnderlying.rawQuery(sql, selectionArgs);
            } else {
                return db.mUnderlying.rawQuery(sql, selectionArgs, cancellationSignal.toCancellationSignal());
            }
        }

        @Override
        public Cursor rawQueryWithFactory(SQLiteDatabaseCompat db, CursorFactory cursorFactory, String sql, String[] selectionArgs, String editTable, CancellationSignalCompat cancellationSignal) {
            if (cancellationSignal == null) {
                return db.mUnderlying.rawQueryWithFactory(cursorFactory, sql, selectionArgs, editTable);
            } else {
                return db.mUnderlying.rawQueryWithFactory(cursorFactory, sql, selectionArgs, editTable, cancellationSignal.toCancellationSignal());
            }
        }
    }
}
