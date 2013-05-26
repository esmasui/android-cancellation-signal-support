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

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.os.OperationCanceledException;
import android.util.Log;

import com.uphyca.support.v4.os.CancellationSignalCompat;
import com.uphyca.support.v4.os.OperationCanceledExceptionCompat;

public abstract class ContentProviderCompat extends ContentProvider {

    @TargetApi(16)
    @Override
    public final Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignal cancellationSignal) {
        try {
            return supportQuery(uri, projection, selection, selectionArgs, sortOrder, CancellationSignalCompat.valueOf(cancellationSignal));
        } catch (OperationCanceledExceptionCompat e) {
            RuntimeException operationCanceledException = OperationCanceledExceptionCompat.class.cast(e)
                                                                                                .getOperationCanceledException();
            if (operationCanceledException != null) {
                throw operationCanceledException;
            } else {
                RuntimeException e2 = OperationCanceledExceptionFactory.newInstance();
                e2.fillInStackTrace();
                throw e2;
            }
        }
    }

    private Handler mHandler = new Handler(Looper.myLooper());

    @Override
    public final Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String cancellationSignal = uri.getQueryParameter("cancellationsignal");
        if (cancellationSignal == null) {
            return supportQuery(uri, projection, selection, selectionArgs, sortOrder);
        } else {
            final CancellationSignalCompat signal = new CancellationSignalCompat();
            final ContentObserver contentObserver = new ContentObserver(mHandler) {
                @Override
                public void onChange(boolean selfChange) {
                    Log.d("SQLiteLog", "cancel received");
                    signal.cancel();
                }
            };
            final ContentResolver resolver = getContext().getContentResolver();
            try {
                resolver.registerContentObserver(uri, false, contentObserver);
                return supportQuery(uri, projection, selection, selectionArgs, sortOrder, signal);
            } finally {
                resolver.unregisterContentObserver(contentObserver);
            }
        }
    }

    public abstract Cursor supportQuery(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder);

    public Cursor supportQuery(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignalCompat cancellationSignal) {
        return supportQuery(uri, projection, selection, selectionArgs, sortOrder);
    }

    @TargetApi(16)
    static final class OperationCanceledExceptionFactory {
        static RuntimeException newInstance() {
            return new OperationCanceledException();
        }
    }
}
