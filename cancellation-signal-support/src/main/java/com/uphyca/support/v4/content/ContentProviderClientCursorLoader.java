
package com.uphyca.support.v4.content;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;

public class ContentProviderClientCursorLoader extends CustomCursorLoader {

    private final ContentProviderClientCompat mClient;

    public ContentProviderClientCursorLoader(Context context, ContentProviderClientCompat client, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
        mClient = client;
    }

    public ContentProviderClientCursorLoader(Context context, ContentProviderClientCompat client) {
        super(context);
        mClient = client;
    }

    protected Cursor executeQuery() {
        try {
            return mClient.query(mUri, mProjection, mSelection, mSelectionArgs, mSortOrder, mCancellationSignal);
        } catch (RemoteException ignore) {
        }
        return null;
    }
}
