
package com.uphyca.android;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.widget.Toast;

import com.example.cancellationsignal.support.R;
import com.uphyca.support.v4.content.AsyncTaskLoaderCompat;
import com.uphyca.support.v4.content.CursorLoaderCompat;
import com.uphyca.support.v4.os.OperationCanceledExceptionCompat;

public class ExampleActivity extends FragmentActivity implements LoaderCallbacks<Cursor>, Runnable {

    private Handler mHandler = new Handler();

    /**
     * Called when the activity is first created.
     * 
     * @param savedInstanceState If the activity is being re-initialized after
     *            previously being shut down then this Bundle contains the data it most
     *            recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        run();
    }

    @Override
    protected void onPause() {
        mHandler.removeCallbacks(this);
        super.onPause();
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {
            "name"
        };
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = null;
        mLoader = new CursorLoaderCompat(this, ExampleContentProvider.sContentUri, projection, selection, selectionArgs, sortOrder) {
            @Override
            protected Cursor onLoadInBackground() {
                try {
                    return super.onLoadInBackground();
                } catch (final OperationCanceledExceptionCompat e) {
                    e.printStackTrace();
                    mHandler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT)
                                 .show();
                            getSupportLoaderManager().restartLoader(0, null, ExampleActivity.this);
                        }
                    });
                    return null;
                }
            }
        };
        mLoader.setUpdateThrottle(1000L);
        return mLoader;
    }

    private AsyncTaskLoaderCompat<Cursor> mLoader;

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public void run() {
        getContentResolver().notifyChange(ExampleContentProvider.sContentUri, null);
        if (mLoader != null) {
            mLoader.cancelLoadInBackground();
        }
        mHandler.postDelayed(this, 1000L);
    }
}
