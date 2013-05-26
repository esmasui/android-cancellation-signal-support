
package com.uphyca.support.v4.database.sqlite;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;

import com.uphyca.support.v4.os.OperationCanceledExceptionCompat;

public class CancellingCursor extends CursorWrapper {

    private boolean mCancelled;

    public CancellingCursor(Cursor cursor) {
        super(cursor);
    }

    @Override
    public Cursor getWrappedCursor() {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }
        return super.getWrappedCursor();
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    protected void finalize() throws Throwable {
        if (!isClosed()) {
            close();
        }
        super.finalize();
    }

    @Override
    public boolean isClosed() {
        return super.isClosed();
    }

    @Override
    public int getCount() {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.getCount();
    }

    @Override
    public void deactivate() {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        super.deactivate();
    }

    @Override
    public boolean moveToFirst() {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.moveToFirst();
    }

    @Override
    public int getColumnCount() {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.getColumnCount();
    }

    @Override
    public int getColumnIndex(String columnName) {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.getColumnIndex(columnName);
    }

    @Override
    public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.getColumnIndexOrThrow(columnName);
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.getColumnName(columnIndex);
    }

    @Override
    public String[] getColumnNames() {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.getColumnNames();
    }

    @Override
    public double getDouble(int columnIndex) {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.getDouble(columnIndex);
    }

    @Override
    public Bundle getExtras() {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.getExtras();
    }

    @Override
    public float getFloat(int columnIndex) {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.getFloat(columnIndex);
    }

    @Override
    public int getInt(int columnIndex) {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.getInt(columnIndex);
    }

    @Override
    public long getLong(int columnIndex) {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.getLong(columnIndex);
    }

    @Override
    public short getShort(int columnIndex) {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.getShort(columnIndex);
    }

    @Override
    public String getString(int columnIndex) {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.getString(columnIndex);
    }

    @Override
    public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        super.copyStringToBuffer(columnIndex, buffer);
    }

    @Override
    public byte[] getBlob(int columnIndex) {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.getBlob(columnIndex);
    }

    @Override
    public boolean getWantsAllOnMoveCalls() {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.getWantsAllOnMoveCalls();
    }

    @Override
    public boolean isAfterLast() {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.isAfterLast();
    }

    @Override
    public boolean isBeforeFirst() {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.isBeforeFirst();
    }

    @Override
    public boolean isFirst() {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.isFirst();
    }

    @Override
    public boolean isLast() {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.isLast();
    }

    @Override
    public int getType(int columnIndex) {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.getType(columnIndex);
    }

    @Override
    public boolean isNull(int columnIndex) {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.isNull(columnIndex);
    }

    @Override
    public boolean moveToLast() {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.moveToLast();
    }

    @Override
    public boolean move(int offset) {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.move(offset);
    }

    @Override
    public boolean moveToPosition(int position) {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.moveToPosition(position);
    }

    @Override
    public boolean moveToNext() {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.moveToNext();
    }

    @Override
    public int getPosition() {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.getPosition();
    }

    @Override
    public boolean moveToPrevious() {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.moveToPrevious();
    }

    @Override
    public void registerContentObserver(ContentObserver observer) {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        super.registerContentObserver(observer);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        super.registerDataSetObserver(observer);
    }

    @Override
    public boolean requery() {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.requery();
    }

    @Override
    public Bundle respond(Bundle extras) {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        return super.respond(extras);
    }

    @Override
    public void setNotificationUri(ContentResolver cr, Uri uri) {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        super.setNotificationUri(cr, uri);
    }

    @Override
    public void unregisterContentObserver(ContentObserver observer) {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        super.unregisterContentObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (mCancelled) {
            throw new OperationCanceledExceptionCompat();
        }

        super.unregisterDataSetObserver(observer);
    }

    public void setCancelled() {
        mCancelled = true;
    }
}
