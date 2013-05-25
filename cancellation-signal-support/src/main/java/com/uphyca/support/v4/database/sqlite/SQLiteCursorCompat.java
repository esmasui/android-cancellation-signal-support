/*
 * Copyright (C) 2013 uPhyca Inc. 
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

package com.uphyca.support.v4.database.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;

import com.uphyca.support.v4.os.ExceptionConverter;

public class SQLiteCursorCompat extends CursorWrapper {

    public static Cursor newInstance(Cursor cursor) {
        return new SQLiteCursorCompat(cursor);
    }

    private SQLiteCursorCompat(Cursor cursor) {
        super(cursor);
    }

    @Override
    public void close() {
        try {
            super.close();
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {
        try {
            super.copyStringToBuffer(columnIndex, buffer);
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public void deactivate() {
        try {
            super.deactivate();
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public byte[] getBlob(int columnIndex) {
        try {
            return super.getBlob(columnIndex);
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public int getColumnCount() {
        try {
            return super.getColumnCount();
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public int getColumnIndex(String columnName) {
        try {
            return super.getColumnIndex(columnName);
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
        try {
            return super.getColumnIndexOrThrow(columnName);
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        try {
            return super.getColumnName(columnIndex);
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public String[] getColumnNames() {
        try {
            return super.getColumnNames();
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public int getCount() {
        try {
            return super.getCount();
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public double getDouble(int columnIndex) {
        try {
            return super.getDouble(columnIndex);
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public Bundle getExtras() {
        try {
            return super.getExtras();
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public float getFloat(int columnIndex) {
        try {
            return super.getFloat(columnIndex);
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public int getInt(int columnIndex) {
        try {
            return super.getInt(columnIndex);
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public long getLong(int columnIndex) {
        try {
            return super.getLong(columnIndex);
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public int getPosition() {
        try {
            return super.getPosition();
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public short getShort(int columnIndex) {
        try {
            return super.getShort(columnIndex);
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public String getString(int columnIndex) {
        try {
            return super.getString(columnIndex);
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @SuppressLint("NewApi")
    @Override
    public int getType(int columnIndex) {
        try {
            return super.getType(columnIndex);
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public boolean getWantsAllOnMoveCalls() {
        try {
            return super.getWantsAllOnMoveCalls();
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @SuppressLint("NewApi")
    @Override
    public Cursor getWrappedCursor() {
        try {
            return super.getWrappedCursor();
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public boolean isAfterLast() {
        try {
            return super.isAfterLast();
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public boolean isBeforeFirst() {
        try {
            return super.isBeforeFirst();
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public boolean isClosed() {
        try {
            return super.isClosed();
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public boolean isFirst() {
        try {
            return super.isFirst();
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public boolean isLast() {
        try {
            return super.isLast();
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public boolean isNull(int columnIndex) {
        try {
            return super.isNull(columnIndex);
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public boolean move(int offset) {
        try {
            return super.move(offset);
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public boolean moveToFirst() {
        try {
            return super.moveToFirst();
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public boolean moveToLast() {
        try {
            return super.moveToLast();
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public boolean moveToNext() {
        try {
            return super.moveToNext();
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public boolean moveToPosition(int position) {
        try {
            return super.moveToPosition(position);
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public boolean moveToPrevious() {
        try {
            return super.moveToPrevious();
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public void registerContentObserver(ContentObserver observer) {
        try {
            super.registerContentObserver(observer);
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        try {
            super.registerDataSetObserver(observer);
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public boolean requery() {
        try {
            return super.requery();
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public Bundle respond(Bundle extras) {
        try {
            return super.respond(extras);
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public void setNotificationUri(ContentResolver cr, Uri uri) {
        try {
            super.setNotificationUri(cr, uri);
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public void unregisterContentObserver(ContentObserver observer) {
        try {
            super.unregisterContentObserver(observer);
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        try {
            super.unregisterDataSetObserver(observer);
        } catch (RuntimeException e) {
            throw ExceptionConverter.convertException(e);
        }
    }
}
