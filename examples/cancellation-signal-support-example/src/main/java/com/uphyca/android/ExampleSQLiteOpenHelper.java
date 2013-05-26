
package com.uphyca.android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.uphyca.support.v4.database.sqlite.SQLiteOpenHelperCompat;

public class ExampleSQLiteOpenHelper extends SQLiteOpenHelperCompat {

    private static final String CREATE_TABLE = "create table example(_id integer primary key autoincrement, name text)";

    public ExampleSQLiteOpenHelper(Context context) {
        super(context, "example.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
