
package com.uphyca.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.Toast;

import com.example.cancellationsignal.support.R;
import com.uphyca.support.v4.content.AsyncTaskLoaderCompat;
import com.uphyca.support.v4.content.CursorLoaderCompat;
import com.uphyca.support.v4.os.OperationCanceledExceptionCompat;

public class ExampleActivity extends FragmentActivity implements LoaderCallbacks<Cursor>, Runnable {

    private static final Uri sContentUri = Uri.parse("content://com.example.cancellationsignal.support");

    private final Handler mHandler;
    private AsyncTaskLoaderCompat<Cursor> mLoader;

    public ExampleActivity() {
        mHandler = new Handler();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final class SampleDataLoader extends AsyncTask<Integer, Void, Void> {

            @Override
            protected Void doInBackground(Integer... params) {

                ExampleSQLiteOpenHelper helper = new ExampleSQLiteOpenHelper(getApplicationContext());
                SQLiteDatabase db = helper.getWritableDatabase();

                Cursor c = db.rawQuery("select count(_id) from example", null);
                if (c.moveToFirst()) {
                    if (c.getInt(0) > 0) {
                        return null;
                    }
                }

                showToast("Loading sample data...");

                db.beginTransaction();

                ContentValues values = new ContentValues();
                int count = params[0];
                for (int i = 0; i < count; ++i) {
                    values.put("name", "name" + Integer.toString(i));
                    db.insert("example", null, values);
                    if (i % 10000 == 0) {
                        db.setTransactionSuccessful();
                        db.endTransaction();
                        db.beginTransaction();
                    }
                }

                db.setTransactionSuccessful();
                db.endTransaction();

                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                dispatchSampleDataLoaded();
            }
        }

        new SampleDataLoader().execute(100000);
    }

    public void dispatchSampleDataLoaded() {
        showToast("Execute query");
        getSupportLoaderManager().initLoader(0, null, this);
    }

    private final class ExampleCursorLoader extends CursorLoaderCompat {

        public ExampleCursorLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
            super(context, uri, projection, selection, selectionArgs, sortOrder);
        }

        @Override
        protected Cursor onLoadInBackground() {
            try {
                return super.onLoadInBackground();
            } catch (final OperationCanceledExceptionCompat e) {
                Log.i("cancellation-signal", e.toString(), e);
                showToast("Load cancelled - " + e.toString());
                return null;
            }
        }
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        try {
            return mLoader = new ExampleCursorLoader(this, sContentUri, null, "name like ?", new String[] {
                "%ame%"
            }, "name");
        } finally {
            mHandler.postDelayed(this, 100L);
        }
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            showToast("Load finished");
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public void run() {
        mLoader.cancelLoadInBackground();
    }

    private void showToast(final String text) {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT)
                     .show();
            }
        });
    }
}
