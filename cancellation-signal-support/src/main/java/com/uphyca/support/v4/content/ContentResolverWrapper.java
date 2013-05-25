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
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.res.AssetFileDescriptor;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

class ContentResolverWrapper {

    final ContentResolver mUnderlying;

    ContentResolverWrapper(ContentResolver underlying) {
        mUnderlying = underlying;
    }

    public final ContentProviderClient acquireContentProviderClient(String name) {
        return mUnderlying.acquireContentProviderClient(name);
    }

    public final ContentProviderClient acquireContentProviderClient(Uri uri) {
        return mUnderlying.acquireContentProviderClient(uri);
    }

    public final ContentProviderClient acquireUnstableContentProviderClient(String name) {
        return mUnderlying.acquireUnstableContentProviderClient(name);
    }

    public final ContentProviderClient acquireUnstableContentProviderClient(Uri uri) {
        return mUnderlying.acquireUnstableContentProviderClient(uri);
    }

    public ContentProviderResult[] applyBatch(String authority, ArrayList<ContentProviderOperation> operations) throws RemoteException, OperationApplicationException {
        return mUnderlying.applyBatch(authority, operations);
    }

    public final int bulkInsert(Uri url, ContentValues[] values) {
        return mUnderlying.bulkInsert(url, values);
    }

    public final Bundle call(Uri uri, String method, String arg, Bundle extras) {
        return mUnderlying.call(uri, method, arg, extras);
    }

    @Deprecated
    public void cancelSync(Uri uri) {
        mUnderlying.cancelSync(uri);
    }

    public final int delete(Uri url, String where, String[] selectionArgs) {
        return mUnderlying.delete(url, where, selectionArgs);
    }

    public boolean equals(Object o) {
        return mUnderlying.equals(o);
    }

    public String[] getStreamTypes(Uri url, String mimeTypeFilter) {
        return mUnderlying.getStreamTypes(url, mimeTypeFilter);
    }

    public final String getType(Uri url) {
        return mUnderlying.getType(url);
    }

    public int hashCode() {
        return mUnderlying.hashCode();
    }

    public final Uri insert(Uri url, ContentValues values) {
        return mUnderlying.insert(url, values);
    }

    public void notifyChange(Uri uri, ContentObserver observer, boolean syncToNetwork) {
        mUnderlying.notifyChange(uri, observer, syncToNetwork);
    }

    public void notifyChange(Uri uri, ContentObserver observer) {
        mUnderlying.notifyChange(uri, observer);
    }

    public final AssetFileDescriptor openAssetFileDescriptor(Uri uri, String mode) throws FileNotFoundException {
        return mUnderlying.openAssetFileDescriptor(uri, mode);
    }

    public final ParcelFileDescriptor openFileDescriptor(Uri uri, String mode) throws FileNotFoundException {
        return mUnderlying.openFileDescriptor(uri, mode);
    }

    public final InputStream openInputStream(Uri uri) throws FileNotFoundException {
        return mUnderlying.openInputStream(uri);
    }

    public final OutputStream openOutputStream(Uri uri, String mode) throws FileNotFoundException {
        return mUnderlying.openOutputStream(uri, mode);
    }

    public final OutputStream openOutputStream(Uri uri) throws FileNotFoundException {
        return mUnderlying.openOutputStream(uri);
    }

    public final AssetFileDescriptor openTypedAssetFileDescriptor(Uri uri, String mimeType, Bundle opts) throws FileNotFoundException {
        return mUnderlying.openTypedAssetFileDescriptor(uri, mimeType, opts);
    }

    //    public final Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignal cancellationSignal) {
    //        return mUnderlying.query(uri, projection, selection, selectionArgs, sortOrder, cancellationSignal);
    //    }

    public final Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return mUnderlying.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    public final void registerContentObserver(Uri uri, boolean notifyForDescendents, ContentObserver observer) {
        mUnderlying.registerContentObserver(uri, notifyForDescendents, observer);
    }

    @Deprecated
    public void startSync(Uri uri, Bundle extras) {
        mUnderlying.startSync(uri, extras);
    }

    public String toString() {
        return mUnderlying.toString();
    }

    public final void unregisterContentObserver(ContentObserver observer) {
        mUnderlying.unregisterContentObserver(observer);
    }

    public final int update(Uri uri, ContentValues values, String where, String[] selectionArgs) {
        return mUnderlying.update(uri, values, where, selectionArgs);
    }

}
