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

import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteStatement;
import android.database.sqlite.SQLiteTransactionListener;
import android.util.Pair;

class SQLiteDatabaseWrapper {

    final SQLiteDatabase mUnderlying;

    SQLiteDatabaseWrapper(SQLiteDatabase underlying) {
        mUnderlying = underlying;
    }

    public final void acquireReference() {
        mUnderlying.acquireReference();
    }

    public final void beginTransaction() {
        mUnderlying.beginTransaction();
    }

    public final void beginTransactionNonExclusive() {
        mUnderlying.beginTransactionNonExclusive();
    }

    public final void beginTransactionWithListener(SQLiteTransactionListener transactionListener) {
        mUnderlying.beginTransactionWithListener(transactionListener);
    }

    public final void beginTransactionWithListenerNonExclusive(SQLiteTransactionListener transactionListener) {
        mUnderlying.beginTransactionWithListenerNonExclusive(transactionListener);
    }

    public final void close() {
        mUnderlying.close();
    }

    public final SQLiteStatement compileStatement(String sql) throws SQLException {
        return mUnderlying.compileStatement(sql);
    }

    public final int delete(String table, String whereClause, String[] whereArgs) {
        return mUnderlying.delete(table, whereClause, whereArgs);
    }

    public final void disableWriteAheadLogging() {
        mUnderlying.disableWriteAheadLogging();
    }

    public final boolean enableWriteAheadLogging() {
        return mUnderlying.enableWriteAheadLogging();
    }

    public final void endTransaction() {
        mUnderlying.endTransaction();
    }

    public final boolean equals(Object o) {
        return mUnderlying.equals(o);
    }

    public final void execSQL(String sql, Object[] bindArgs) throws SQLException {
        mUnderlying.execSQL(sql, bindArgs);
    }

    public final void execSQL(String sql) throws SQLException {
        mUnderlying.execSQL(sql);
    }

    public final List<Pair<String, String>> getAttachedDbs() {
        return mUnderlying.getAttachedDbs();
    }

    public final long getMaximumSize() {
        return mUnderlying.getMaximumSize();
    }

    public final long getPageSize() {
        return mUnderlying.getPageSize();
    }

    public final String getPath() {
        return mUnderlying.getPath();
    }

    @Deprecated
    public final Map<String, String> getSyncedTables() {
        return mUnderlying.getSyncedTables();
    }

    public final int getVersion() {
        return mUnderlying.getVersion();
    }

    public int hashCode() {
        return mUnderlying.hashCode();
    }

    public final boolean inTransaction() {
        return mUnderlying.inTransaction();
    }

    public final long insert(String table, String nullColumnHack, ContentValues values) {
        return mUnderlying.insert(table, nullColumnHack, values);
    }

    public final long insertOrThrow(String table, String nullColumnHack, ContentValues values) throws SQLException {
        return mUnderlying.insertOrThrow(table, nullColumnHack, values);
    }

    public final long insertWithOnConflict(String table, String nullColumnHack, ContentValues initialValues, int conflictAlgorithm) {
        return mUnderlying.insertWithOnConflict(table, nullColumnHack, initialValues, conflictAlgorithm);
    }

    public final boolean isDatabaseIntegrityOk() {
        return mUnderlying.isDatabaseIntegrityOk();
    }

    public final boolean isDbLockedByCurrentThread() {
        return mUnderlying.isDbLockedByCurrentThread();
    }

    @Deprecated
    public final boolean isDbLockedByOtherThreads() {
        return mUnderlying.isDbLockedByOtherThreads();
    }

    public final boolean isOpen() {
        return mUnderlying.isOpen();
    }

    public final boolean isReadOnly() {
        return mUnderlying.isReadOnly();
    }

    public final boolean isWriteAheadLoggingEnabled() {
        return mUnderlying.isWriteAheadLoggingEnabled();
    }

    @Deprecated
    public final void markTableSyncable(String table, String foreignKey, String updateTable) {
        mUnderlying.markTableSyncable(table, foreignKey, updateTable);
    }

    @Deprecated
    public final void markTableSyncable(String table, String deletedTable) {
        mUnderlying.markTableSyncable(table, deletedTable);
    }

    public final boolean needUpgrade(int newVersion) {
        return mUnderlying.needUpgrade(newVersion);
    }

    //    public final Cursor query(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit, CancellationSignal cancellationSignal) {
    //        return mUnderlying.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit, cancellationSignal);
    //    }

    public final Cursor query(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return mUnderlying.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    public final Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return mUnderlying.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    public final Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return mUnderlying.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    //    public final Cursor queryWithFactory(CursorFactory cursorFactory, boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit, CancellationSignal cancellationSignal) {
    //        return mUnderlying.queryWithFactory(cursorFactory, distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit, cancellationSignal);
    //    }

    public final Cursor queryWithFactory(CursorFactory cursorFactory, boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return mUnderlying.queryWithFactory(cursorFactory, distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    //    public final Cursor rawQuery(String sql, String[] selectionArgs, CancellationSignal cancellationSignal) {
    //        return mUnderlying.rawQuery(sql, selectionArgs, cancellationSignal);
    //    }

    public final Cursor rawQuery(String sql, String[] selectionArgs) {
        return mUnderlying.rawQuery(sql, selectionArgs);
    }

    //    public final Cursor rawQueryWithFactory(CursorFactory cursorFactory, String sql, String[] selectionArgs, String editTable, CancellationSignal cancellationSignal) {
    //        return mUnderlying.rawQueryWithFactory(cursorFactory, sql, selectionArgs, editTable, cancellationSignal);
    //    }

    public final Cursor rawQueryWithFactory(CursorFactory cursorFactory, String sql, String[] selectionArgs, String editTable) {
        return mUnderlying.rawQueryWithFactory(cursorFactory, sql, selectionArgs, editTable);
    }

    public final void releaseReference() {
        mUnderlying.releaseReference();
    }

    @Deprecated
    public final void releaseReferenceFromContainer() {
        mUnderlying.releaseReferenceFromContainer();
    }

    public final long replace(String table, String nullColumnHack, ContentValues initialValues) {
        return mUnderlying.replace(table, nullColumnHack, initialValues);
    }

    public final long replaceOrThrow(String table, String nullColumnHack, ContentValues initialValues) throws SQLException {
        return mUnderlying.replaceOrThrow(table, nullColumnHack, initialValues);
    }

    public final void setForeignKeyConstraintsEnabled(boolean enable) {
        mUnderlying.setForeignKeyConstraintsEnabled(enable);
    }

    public final void setLocale(Locale locale) {
        mUnderlying.setLocale(locale);
    }

    @Deprecated
    public final void setLockingEnabled(boolean lockingEnabled) {
        mUnderlying.setLockingEnabled(lockingEnabled);
    }

    public final void setMaxSqlCacheSize(int cacheSize) {
        mUnderlying.setMaxSqlCacheSize(cacheSize);
    }

    public final long setMaximumSize(long numBytes) {
        return mUnderlying.setMaximumSize(numBytes);
    }

    public final void setPageSize(long numBytes) {
        mUnderlying.setPageSize(numBytes);
    }

    public final void setTransactionSuccessful() {
        mUnderlying.setTransactionSuccessful();
    }

    public final void setVersion(int version) {
        mUnderlying.setVersion(version);
    }

    public String toString() {
        return mUnderlying.toString();
    }

    public final int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        return mUnderlying.update(table, values, whereClause, whereArgs);
    }

    public final int updateWithOnConflict(String table, ContentValues values, String whereClause, String[] whereArgs, int conflictAlgorithm) {
        return mUnderlying.updateWithOnConflict(table, values, whereClause, whereArgs, conflictAlgorithm);
    }

    @Deprecated
    public final boolean yieldIfContended() {
        return mUnderlying.yieldIfContended();
    }

    public final boolean yieldIfContendedSafely() {
        return mUnderlying.yieldIfContendedSafely();
    }

    public final boolean yieldIfContendedSafely(long sleepAfterYieldDelay) {
        return mUnderlying.yieldIfContendedSafely(sleepAfterYieldDelay);
    }
}
