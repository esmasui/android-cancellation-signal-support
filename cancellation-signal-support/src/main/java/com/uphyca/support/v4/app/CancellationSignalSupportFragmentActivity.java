/*
 * Copyright (C) 2013 uPhyca Inc.
 * 
 * Copyright (C) 2006 The Android Open Source Project
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

package com.uphyca.support.v4.app;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.support.v4.app.FragmentActivity;

import com.uphyca.support.v4.content.ContentResolverCompat;
import com.uphyca.support.v4.content.ContextCompat;
import com.uphyca.support.v4.database.sqlite.SQLiteDatabaseCompat;

public class CancellationSignalSupportFragmentActivity extends FragmentActivity {

    private ContextCompat mContext;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        mContext = ContextCompat.newInstance(newBase);
    }

    /** Return a support version of ContentResolver instance for your application's package. */
    public ContentResolverCompat getSupportContentResolver() {
        return mContext.getSupportContentResolver();
    }

    /**
     * Open support version of a new private SQLiteDatabase associated with this Context's
     * application package. Create the database file if it doesn't exist.
     * 
     * @param name The name (unique in the application package) of the database.
     * @param mode Operating mode. Use 0 or {@link #MODE_PRIVATE} for the
     *            default operation, {@link #MODE_WORLD_READABLE} and {@link #MODE_WORLD_WRITEABLE} to control permissions.
     *            Use {@link #MODE_ENABLE_WRITE_AHEAD_LOGGING} to enable write-ahead logging by default.
     * @param factory An optional factory class that is called to instantiate a
     *            cursor when query is called.
     * @return The contents of a newly created database with the given name.
     * @throws android.database.sqlite.SQLiteException if the database file could not be opened.
     * @see #MODE_PRIVATE
     * @see #MODE_WORLD_READABLE
     * @see #MODE_WORLD_WRITEABLE
     * @see #MODE_ENABLE_WRITE_AHEAD_LOGGING
     * @see #deleteDatabase
     */
    public SQLiteDatabaseCompat openOrCreateSupportDatabase(String name, int mode, CursorFactory factory) {
        return mContext.openOrCreateSupportDatabase(name, mode, factory);
    }

    /**
     * Open support version of a new private SQLiteDatabase associated with this Context's
     * application package. Creates the database file if it doesn't exist.
     * <p>
     * Accepts input param: a concrete instance of {@link DatabaseErrorHandler} to be used to handle corruption when sqlite reports database corruption.
     * </p>
     * 
     * @param name The name (unique in the application package) of the database.
     * @param mode Operating mode. Use 0 or {@link #MODE_PRIVATE} for the
     *            default operation, {@link #MODE_WORLD_READABLE} and {@link #MODE_WORLD_WRITEABLE} to control permissions.
     *            Use {@link #MODE_ENABLE_WRITE_AHEAD_LOGGING} to enable write-ahead logging by default.
     * @param factory An optional factory class that is called to instantiate a
     *            cursor when query is called.
     * @param errorHandler the {@link DatabaseErrorHandler} to be used when sqlite reports database
     *            corruption. if null, {@link android.database.DefaultDatabaseErrorHandler} is assumed.
     * @return The contents of a newly created database with the given name.
     * @throws android.database.sqlite.SQLiteException if the database file could not be opened.
     * @see #MODE_PRIVATE
     * @see #MODE_WORLD_READABLE
     * @see #MODE_WORLD_WRITEABLE
     * @see #MODE_ENABLE_WRITE_AHEAD_LOGGING
     * @see #deleteDatabase
     */
    public SQLiteDatabaseCompat openOrCreateSupportDatabase(String name, int mode, CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return mContext.openOrCreateSupportDatabase(name, mode, factory, errorHandler);
    }
}
