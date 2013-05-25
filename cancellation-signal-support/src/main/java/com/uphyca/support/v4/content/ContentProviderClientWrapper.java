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

import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.content.ContentProvider;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

class ContentProviderClientWrapper {

    final ContentProviderClient mUnderlying;

    ContentProviderClientWrapper(ContentProviderClient underlying) {
        mUnderlying = underlying;
    }

    public final ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws RemoteException, OperationApplicationException {
        return mUnderlying.applyBatch(operations);
    }

    public final int bulkInsert(Uri url, ContentValues[] initialValues) throws RemoteException {
        return mUnderlying.bulkInsert(url, initialValues);
    }

    //    @TargetApi(17)
    //    public final Bundle call(String method, String arg, Bundle extras) throws RemoteException {
    //        return mUnderlying.call(method, arg, extras);
    //    }

    public final int delete(Uri url, String selection, String[] selectionArgs) throws RemoteException {
        return mUnderlying.delete(url, selection, selectionArgs);
    }

    public boolean equals(Object o) {
        return mUnderlying.equals(o);
    }

    public final ContentProvider getLocalContentProvider() {
        return mUnderlying.getLocalContentProvider();
    }

    public final String[] getStreamTypes(Uri url, String mimeTypeFilter) throws RemoteException {
        return mUnderlying.getStreamTypes(url, mimeTypeFilter);
    }

    public final String getType(Uri url) throws RemoteException {
        return mUnderlying.getType(url);
    }

    public int hashCode() {
        return mUnderlying.hashCode();
    }

    public final Uri insert(Uri url, ContentValues initialValues) throws RemoteException {
        return mUnderlying.insert(url, initialValues);
    }

    public final AssetFileDescriptor openAssetFile(Uri url, String mode) throws RemoteException, FileNotFoundException {
        return mUnderlying.openAssetFile(url, mode);
    }

    public final ParcelFileDescriptor openFile(Uri url, String mode) throws RemoteException, FileNotFoundException {
        return mUnderlying.openFile(url, mode);
    }

    //    public final Cursor query(Uri url, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignal cancellationSignal) throws RemoteException {
    //        return mUnderlying.query(url, projection, selection, selectionArgs, sortOrder, cancellationSignal);
    //    }

    public final Cursor query(Uri url, String[] projection, String selection, String[] selectionArgs, String sortOrder) throws RemoteException {
        return mUnderlying.query(url, projection, selection, selectionArgs, sortOrder);
    }

    public final boolean release() {
        return mUnderlying.release();
    }

    public String toString() {
        return mUnderlying.toString();
    }

    public final int update(Uri url, ContentValues values, String selection, String[] selectionArgs) throws RemoteException {
        return mUnderlying.update(url, values, selection, selectionArgs);
    }
}
