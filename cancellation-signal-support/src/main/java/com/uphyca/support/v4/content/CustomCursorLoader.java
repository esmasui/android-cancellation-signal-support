
package com.uphyca.support.v4.content;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.uphyca.support.v4.os.CancellationSignalCompat;
import com.uphyca.support.v4.os.OperationCanceledExceptionCompat;

public class CustomCursorLoader extends CursorLoaderCompat {

    public CustomCursorLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }

    public CustomCursorLoader(Context context) {
        super(context);
    }

    /* Runs on a worker thread */
    @Override
    public Cursor loadInBackground() {
        synchronized (this) {
            if (isLoadInBackgroundCanceled()) {
                throw new OperationCanceledExceptionCompat();
            }
            mCancellationSignal = new CancellationSignalCompat();
        }
        try {
            Cursor cursor = executeQuery();
            if (cursor != null) {
                // Ensure the cursor window is filled
                cursor.getCount();
                registerContentObserver(cursor, mObserver);
            }
            return cursor;
        } finally {
            synchronized (this) {
                mCancellationSignal = null;
            }
        }
    }

    protected Cursor executeQuery() {
        Cursor cursor = ContentResolverCompat.newInstance(getContext().getContentResolver())
                                             .query(mUri, mProjection, mSelection, mSelectionArgs, mSortOrder, mCancellationSignal);
        return cursor;
    }

    @Override
    protected Cursor onLoadInBackground() {
        try {
            return super.onLoadInBackground();
        } catch (OperationCanceledExceptionCompat cancelled) {
            return null;
        }
    }
}
