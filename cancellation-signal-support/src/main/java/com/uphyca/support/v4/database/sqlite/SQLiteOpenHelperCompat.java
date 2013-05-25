
package com.uphyca.support.v4.database.sqlite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class SQLiteOpenHelperCompat extends SQLiteOpenHelper {

    public SQLiteOpenHelperCompat(Context context, String name, CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public SQLiteOpenHelperCompat(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SQLiteDatabaseCompat getSupportReadableDatabase() {
        return SQLiteDatabaseCompat.newInstance(super.getReadableDatabase());
    }

    public SQLiteDatabaseCompat getSupportWritableDatabase() {
        return SQLiteDatabaseCompat.newInstance(super.getWritableDatabase());
    }
}
