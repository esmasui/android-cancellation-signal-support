
package com.uphyca.support.v4.content;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.uphyca.support.v4.database.sqlite.SQLiteDatabaseCompat;

public class ContextCompat extends ContextWrapper {

    public static final ContextCompat newInstance(Context context) {
        return new ContextCompat(context);
    }

    ContentResolverCompat mContentResolver;

    private ContextCompat(Context base) {
        super(base);
        mContentResolver = ContentResolverCompat.newInstance(getContentResolver());
    }

    /** Return a support version of ContentResolver instance for your application's package. */
    public ContentResolverCompat getSupportContentResolver() {
        return mContentResolver;
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
        return SQLiteDatabaseCompat.newInstance(openOrCreateDatabase(name, mode, factory));
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
        return SQLiteDatabaseCompat.newInstance(openOrCreateDatabase(name, mode, factory, errorHandler));
    }

}
