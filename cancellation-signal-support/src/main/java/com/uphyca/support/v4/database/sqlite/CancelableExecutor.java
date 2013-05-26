
package com.uphyca.support.v4.database.sqlite;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.database.Cursor;
import android.util.Log;

import com.uphyca.support.v4.os.CancellationSignalCompat;
import com.uphyca.support.v4.os.OperationCanceledExceptionCompat;

abstract class CancelableExecutor {

    private static final ExecutorService sExec = Executors.newCachedThreadPool();

    public static interface CancellableQueryTask {
        Cursor query(SQLiteDatabaseCompat db);
    }

    public static final Cursor query(final SQLiteDatabaseCompat db, final CancellableQueryTask executor, CancellationSignalCompat cancellationSignal) {

        if (cancellationSignal == null) {
            return executor.query(db);
        }

        final CountDownLatch latch = new CountDownLatch(1);
        final CancellingCursor[] cursorHolder = new CancellingCursor[1];
        final Object[] cancelled = new Object[1];

        cancellationSignal.setOnCancelListener(new CancellationSignalCompat.OnCancelListener() {
            @Override
            public void onCancel() {
                Log.e("SQLiteLog", "statement aborts");
                cancelled[0] = new Object();
                if (cursorHolder[0] != null) {
                    cursorHolder[0].setCancelled();
                    cursorHolder[0].close();
                }
                latch.countDown();
            }
        });

        sExec.execute(new Runnable() {
            @Override
            public void run() {
                CancellingCursor c = new CancellingCursor(executor.query(db));
                Log.e("SQLiteLog", "query executed");
                if (cancelled[0] != null) {
                    c.setCancelled();
                    c.close();
                }
                cursorHolder[0] = c;
                latch.countDown();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
        }

        if (cancelled[0] != null) {
            OperationCanceledExceptionCompat e = new OperationCanceledExceptionCompat();
            e.fillInStackTrace();
            throw e;
        }

        return cursorHolder[0];
    }
}
