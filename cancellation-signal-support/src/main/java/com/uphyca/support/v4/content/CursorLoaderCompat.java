/*
 * Copyright (C) 2013 uPhyca Inc.
 * 
 * Copyright (C) 2010 The Android Open Source Project
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

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.UUID;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.LoaderTrojanHorse;
import android.util.Log;

import com.uphyca.support.v4.database.sqlite.CancellingCursor;
import com.uphyca.support.v4.os.CancellationSignalCompat;
import com.uphyca.support.v4.os.OperationCanceledExceptionCompat;

/**
 * A loader that queries the {@link ContentResolver} and returns a {@link Cursor}.
 * This class implements the {@link LoaderCompat} protocol in a standard way for
 * querying cursors, building on {@link AsyncTaskLoaderCompat} to perform the cursor
 * query on a background thread so that it does not block the application's UI.
 * <p>
 * A CursorLoader must be built with the full information for the query to perform, either through the {@link #CursorLoader(Context, Uri, String[], String, String[], String)} or creating an empty instance with {@link #CursorLoader(Context)} and filling in the desired paramters with {@link #setUri(Uri)}, {@link #setSelection(String)}, {@link #setSelectionArgs(String[])}, {@link #setSortOrder(String)}, and {@link #setProjection(String[])}.
 */
public class CursorLoaderCompat extends AsyncTaskLoaderCompat<Cursor> {
    final ForceLoadContentObserver mObserver;

    Uri mUri;
    String[] mProjection;
    String mSelection;
    String[] mSelectionArgs;
    String mSortOrder;

    Cursor mCursor;
    CancellationSignalCompat mCancellationSignal;

    /* Runs on a worker thread */
    @Override
    public Cursor loadInBackground() {

        final Object[] cancelled;
        final CancellingCursor[] cursorHolder;
        final ContentObserver contentObserver;
        final String signalId = UUID.randomUUID()
                                    .toString();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            cancelled = new Object[1];
            cursorHolder = new CancellingCursor[1];
            contentObserver = new ContentObserver(mHandler) {
                @Override
                public void onChange(boolean selfChange, Uri uri) {
                    if (signalId.equals(uri.getQueryParameter("cancellationsignal"))) {
                        cancelled[0] = new Object();
                        Log.d("SQLiteLog", "cancel accepted");
                    }
                }
            };
        } else {
            cancelled = null;
            contentObserver = null;
            cursorHolder = null;
        }

        synchronized (this) {
            if (isLoadInBackgroundCanceled()) {
                throw new OperationCanceledExceptionCompat();
            }
            mCancellationSignal = new CancellationSignalCompat();
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                final ContentResolverCompat resolver = getSupportContext().getSupportContentResolver();
                mCancellationSignal.setOnCancelListener(new CancellationSignalCompat.OnCancelListener() {
                    @Override
                    public void onCancel() {
                        resolver.notifyChange(mUri.buildUpon()
                                              .appendQueryParameter("cancellationsignal", signalId)
                                              .build(), null);
                        if (cursorHolder[0] != null) {
                            cursorHolder[0].setCancelled();
                            cursorHolder[0].close();
                        }
                        Log.d("SQLiteLog", "cancel requested");
                    }
                });
                resolver.registerContentObserver(mUri.buildUpon()
                                                 .appendQueryParameter("cancellationsignal", signalId)
                                                 .build(), false, contentObserver);
            }
        }
        try {
            final Cursor cursor;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                Cursor c = getSupportContext().getSupportContentResolver()
                                              .query(mUri.buildUpon()
                                                         .appendQueryParameter("cancellationsignal", signalId)
                                                         .build(), mProjection, mSelection, mSelectionArgs, mSortOrder, mCancellationSignal);
                if (c != null) {
                    cursorHolder[0] = new CancellingCursor(c);
                    cursor = cursorHolder[0];
                } else {
                    cursor = c;
                }
            } else {
                cursor = getSupportContext().getSupportContentResolver()
                                            .query(mUri, mProjection, mSelection, mSelectionArgs, mSortOrder, mCancellationSignal);
            }

            if (cursor != null) {
                // Ensure the cursor window is filled
                cursor.getCount();
                registerContentObserver(cursor, mObserver);
            }
            return cursor;
        } finally {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                getSupportContext().getSupportContentResolver()
                                   .unregisterContentObserver(contentObserver);
            }
            synchronized (this) {
                mCancellationSignal = null;
            }

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                if (cancelled[0] != null) {
                    OperationCanceledExceptionCompat e = new OperationCanceledExceptionCompat();
                    e.fillInStackTrace();
                    throw e;
                }
            }
        }
    }

    @Override
    public void cancelLoadInBackground() {
        super.cancelLoadInBackground();

        synchronized (this) {
            if (mCancellationSignal != null) {
                mCancellationSignal.cancel();
            }
        }
    }

    /**
     * Registers an observer to get notifications from the content provider
     * when the cursor needs to be refreshed.
     */
    void registerContentObserver(Cursor cursor, ContentObserver observer) {
        cursor.registerContentObserver(mObserver);
    }

    /* Runs on the UI thread */
    @Override
    public void deliverResult(Cursor cursor) {
        if (isReset()) {
            // An async query came in while the loader is stopped
            if (cursor != null) {
                cursor.close();
            }
            return;
        }
        Cursor oldCursor = mCursor;
        mCursor = cursor;

        if (isStarted()) {
            super.deliverResult(cursor);
        }

        if (oldCursor != null && oldCursor != cursor && !oldCursor.isClosed()) {
            oldCursor.close();
        }
    }

    /**
     * Creates an empty unspecified CursorLoader. You must follow this with
     * calls to {@link #setUri(Uri)}, {@link #setSelection(String)}, etc
     * to specify the query to perform.
     */
    public CursorLoaderCompat(Context context) {
        super(context);
        mObserver = new ForceLoadContentObserver();
    }

    /**
     * Creates a fully-specified CursorLoader. See {@link ContentResolver#query(Uri, String[], String, String[], String)
     * ContentResolver.query()} for documentation on the meaning of the
     * parameters. These will be passed as-is to that call.
     */
    public CursorLoaderCompat(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context);
        mObserver = new ForceLoadContentObserver();
        mUri = uri;
        mProjection = projection;
        mSelection = selection;
        mSelectionArgs = selectionArgs;
        mSortOrder = sortOrder;
    }

    /**
     * Starts an asynchronous load of the contacts list data. When the result is ready the callbacks
     * will be called on the UI thread. If a previous load has been completed and is still valid
     * the result may be passed to the callbacks immediately.
     * Must be called from the UI thread
     */
    @Override
    protected void onStartLoading() {
        if (mCursor != null) {
            deliverResult(mCursor);
        }
        if (takeContentChanged() || mCursor == null) {
            forceLoad();
        }
    }

    /**
     * Must be called from the UI thread
     */
    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    @Override
    public void onCanceled(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        mCursor = null;
    }

    public Uri getUri() {
        return mUri;
    }

    public void setUri(Uri uri) {
        mUri = uri;
    }

    public String[] getProjection() {
        return mProjection;
    }

    public void setProjection(String[] projection) {
        mProjection = projection;
    }

    public String getSelection() {
        return mSelection;
    }

    public void setSelection(String selection) {
        mSelection = selection;
    }

    public String[] getSelectionArgs() {
        return mSelectionArgs;
    }

    public void setSelectionArgs(String[] selectionArgs) {
        mSelectionArgs = selectionArgs;
    }

    public String getSortOrder() {
        return mSortOrder;
    }

    public void setSortOrder(String sortOrder) {
        mSortOrder = sortOrder;
    }

    @Override
    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(prefix, fd, writer, args);
        writer.print(prefix);
        writer.print("mUri=");
        writer.println(mUri);
        writer.print(prefix);
        writer.print("mProjection=");
        writer.println(Arrays.toString(mProjection));
        writer.print(prefix);
        writer.print("mSelection=");
        writer.println(mSelection);
        writer.print(prefix);
        writer.print("mSelectionArgs=");
        writer.println(Arrays.toString(mSelectionArgs));
        writer.print(prefix);
        writer.print("mSortOrder=");
        writer.println(mSortOrder);
        writer.print(prefix);
        writer.print("mCursor=");
        writer.println(mCursor);
        writer.print(prefix);
        writer.print("mContentChanged=");
        writer.println(LoaderTrojanHorse.getContentChanged(this));
    }
}
