android-cancellation-signal-support
===================================

android-cancellation-signal-support is a compatibility library of [android.os.CancellationSignal][1]



Your application that build with android-cancellation-signal-support is running on Jelly Bean(API16) or later works with underlying CancellationSignal, or simply ignore cancellation signal on older versions.


Getting started
-------


### SQLiteOpenHelper
 
- Extends com.uphyca.support.v4.database.sqlite.SQLiteOpenHelperCompat instead of android.database.sqlite.SQLiteOpenHelperCompat

```Java
public class ExampleSQLiteOpenHelper extends SQLiteOpenHelperCompat {

    ...
}
```

### ContentProvider

- Extends com.uphyca.support.v4.content.ContentProviderCompat instead of android.content.ContentProvider
- Overrides com.uphyca.support.v4.content.ContentProviderCompat.query(..., com.uphyca.support.v4.os.CancellationSignalCompat) instead of android.content.ContentProvider.query(..., android.os.CancellationSignal)
- Call com.uphyca.support.v4.database.sqlite.SQLiteOpenHelperCompat.getSupportReadableDatabase() instead of android.database.sqlite.SQLiteOpenHelper.getReadableDatabase()
- Call com.uphyca.support.v4.database.sqlite.SQLiteDatabaseCompat.query(..., com.uphyca.support.v4.os.CancellationSignalCompat) instead of andrid.database.sqlite.SQLiteDatabase.query(..., android.os.CandellationSignal)
- Call com.uphyca.support.v4.database.sqlite.SQLiteDatabaseCompat.rawQuery(..., com.uphyca.support.v4.os.CancellationSignalCompat) instead of andrid.database.sqlite.SQLiteDatabase.rawQuery(..., android.os.CandellationSignal)
- Call com.uphyca.support.v4.database.sqlite.SQLiteQueryBuilderCompat.query(..., com.uphyca.support.v4.os.CancellationSignalCompat) instead of android.database.sqlite.SQLiteQueryBuilder.query(..., android.os.CancellationSignal)


```Java
public class ExampleContentProvider extends ContentProviderCompat {

    private SQLiteOpenHelperCompat mSqLiteOpenHelper;

    @Override
    public boolean onCreate() {
        mSqLiteOpenHelper = new ExampleSQLiteOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return query(uri, projection, selection, selectionArgs, sortOrder, (CancellationSignalCompat) null);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignalCompat cancellationSignal) {

        //Obtain a database that support cancellation-signal
        final SQLiteDatabaseCompat db = mSqLiteOpenHelper.getSupportReadableDatabase();

        //Execute query with cancellation-signal
        Cursor returnThis = db.query(false, "example", projection, selection, selectionArgs, null, null, null, null, cancellationSignal);

        return returnThis;
    }

    ...
}
```

### Loader

- Extends com.uphyca.support.v4.content.LoaderCompat instead of android.support.v4.content.Loader
- Extends com.uphyca.support.v4.content.AsyncTaskLoaderCompat instead of android.support.v4.content.AsyncTaskLoader
- Extends com.uphyca.support.v4.content.CursorLoaderCompat instead of android.support.v4.content.CursorLoader

```Java
public class ExampleActivity extends FragmentActivity implements LoaderCallbacks<Cursor>, Runnable {

    //Define the loader that extends CursorLoaderCompat
    private static final class ExampleCursorLoader extends CursorLoaderCompat {

        public ExampleCursorLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
            super(context, uri, projection, selection, selectionArgs, sortOrder);
        }

        @Override
        protected Cursor onLoadInBackground() {
            try {
                return super.onLoadInBackground();
            } catch (final OperationCanceledExceptionCompat e) {
                Log.i("cancellation-signal", e.toString(), e);
                return null;
            }
        }
    }

    private static final Uri sContentUri = Uri.parse("content://com.example.cancellationsignal.support");

    private AsyncTaskLoaderCompat<Cursor> mLoader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Initialize the loader
        getSupportLoaderManager().initLoader(0, null, this);
    }
    
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mLoader = new ExampleCursorLoader(this, sContentUri, null, null, null, null);
    }

    public void cancelQuery() {
        // Cancel the query
        mLoader.cancelLoadInBackground();
    }
    
    ...
}
```


License
-------

    Copyright 2013 uPhyca Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


[1]: http://developer.android.com/reference/android/os/CancellationSignal.html
