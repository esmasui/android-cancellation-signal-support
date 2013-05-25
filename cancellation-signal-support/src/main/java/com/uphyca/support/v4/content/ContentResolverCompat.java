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

package com.uphyca.support.v4.content;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import com.uphyca.support.v4.database.sqlite.SQLiteCursorCompat;
import com.uphyca.support.v4.os.CancellationSignalCompat;
import com.uphyca.support.v4.os.ExceptionConverter;

public final class ContentResolverCompat extends ContentResolverWrapper {

    public static final ContentResolverCompat newInstance(ContentResolver underlying) {
        if (underlying == null) {
            return null;
        }
        return new ContentResolverCompat(underlying);
    }

    private ContentResolverCompat(ContentResolver underlying) {
        super(underlying);
    }

    public final Cursor query(Uri url, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignalCompat cancellationSignal) {
        try {
            return SQLiteCursorCompat.newInstance(sQueryExecutor.query(this, url, projection, selection, selectionArgs, sortOrder, cancellationSignal));
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
        Cursor query(ContentResolverCompat resolver, Uri url, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignalCompat cancellationSignal);
    }

    private static final class LegacyQueryExecutor implements QueryExecutor {
        @Override
        public Cursor query(ContentResolverCompat resolver, Uri url, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignalCompat cancellationSignal) {
            return resolver.mUnderlying.query(url, projection, selection, selectionArgs, sortOrder);
        }
    }

    @SuppressLint("NewApi")
    private static final class ModernQueryExecutor implements QueryExecutor {
        @Override
        public Cursor query(ContentResolverCompat resolver, Uri url, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignalCompat cancellationSignal) {
            if (cancellationSignal == null) {
                return resolver.mUnderlying.query(url, projection, selection, selectionArgs, sortOrder);
            } else {
                return resolver.mUnderlying.query(url, projection, selection, selectionArgs, sortOrder, cancellationSignal.toCancellationSignal());
            }
        }
    }
}
