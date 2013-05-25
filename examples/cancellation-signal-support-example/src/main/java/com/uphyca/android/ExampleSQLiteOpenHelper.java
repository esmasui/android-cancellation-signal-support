
package com.uphyca.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.uphyca.support.v4.database.sqlite.SQLiteOpenHelperCompat;

public class ExampleSQLiteOpenHelper extends SQLiteOpenHelperCompat {

    private static final String CREATE_TABLE = "create table example(_id integer primary key autoincrement, name text)";

    public ExampleSQLiteOpenHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        loadSamples(db);
    }

    private void loadSamples(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("name", "foo");
        db.insert("example", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
