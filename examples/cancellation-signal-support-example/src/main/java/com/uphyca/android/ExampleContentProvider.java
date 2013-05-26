
package com.uphyca.android;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.uphyca.support.v4.content.ContentProviderCompat;
import com.uphyca.support.v4.database.sqlite.SQLiteDatabaseCompat;
import com.uphyca.support.v4.database.sqlite.SQLiteOpenHelperCompat;
import com.uphyca.support.v4.database.sqlite.SQLiteQueryBuilderCompat;
import com.uphyca.support.v4.os.CancellationSignalCompat;

public class ExampleContentProvider extends ContentProviderCompat {

    private SQLiteOpenHelperCompat mSqLiteOpenHelper;

    @Override
    public boolean onCreate() {
        mSqLiteOpenHelper = new ExampleSQLiteOpenHelper(getContext());
        return true;
    }

    //    @Override
    //    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    //        return query(uri, projection, selection, selectionArgs, sortOrder, (CancellationSignalCompat) null);
    //    }

    @Override
    public Cursor supportQuery(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return supportQuery(uri, projection, selection, selectionArgs, sortOrder, null);
    }

    @Override
    public Cursor supportQuery(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignalCompat cancellationSignal) {

        final SQLiteDatabaseCompat db = mSqLiteOpenHelper.getSupportReadableDatabase();
        SQLiteQueryBuilderCompat builder = new SQLiteQueryBuilderCompat();
        builder.setTables("example");
        return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder, null, cancellationSignal);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

}
