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

import java.util.Map;
import java.util.Set;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQueryBuilder;

class SQLiteQueryBuilderWrapper {

    final SQLiteQueryBuilder mUnderlying;

    public SQLiteQueryBuilderWrapper(SQLiteQueryBuilder underlying) {
        mUnderlying = underlying;
    }

    public void appendWhere(CharSequence inWhere) {
        mUnderlying.appendWhere(inWhere);
    }

    public void appendWhereEscapeString(String inWhere) {
        mUnderlying.appendWhereEscapeString(inWhere);
    }

    public String buildQuery(String[] projectionIn, String selection, String groupBy, String having, String sortOrder, String limit) {
        return mUnderlying.buildQuery(projectionIn, selection, groupBy, having, sortOrder, limit);
    }

    @Deprecated
    public String buildQuery(String[] projectionIn, String selection, String[] selectionArgs, String groupBy, String having, String sortOrder, String limit) {
        return mUnderlying.buildQuery(projectionIn, selection, selectionArgs, groupBy, having, sortOrder, limit);
    }

    public String buildUnionQuery(String[] subQueries, String sortOrder, String limit) {
        return mUnderlying.buildUnionQuery(subQueries, sortOrder, limit);
    }

    public String buildUnionSubQuery(String typeDiscriminatorColumn, String[] unionColumns, Set<String> columnsPresentInTable, int computedColumnsOffset, String typeDiscriminatorValue, String selection, String groupBy, String having) {
        return mUnderlying.buildUnionSubQuery(typeDiscriminatorColumn, unionColumns, columnsPresentInTable, computedColumnsOffset, typeDiscriminatorValue, selection, groupBy, having);
    }

    @Deprecated
    public String buildUnionSubQuery(String typeDiscriminatorColumn, String[] unionColumns, Set<String> columnsPresentInTable, int computedColumnsOffset, String typeDiscriminatorValue, String selection, String[] selectionArgs, String groupBy, String having) {
        return mUnderlying.buildUnionSubQuery(typeDiscriminatorColumn, unionColumns, columnsPresentInTable, computedColumnsOffset, typeDiscriminatorValue, selection, selectionArgs, groupBy, having);
    }

    public boolean equals(Object o) {
        return mUnderlying.equals(o);
    }

    public String getTables() {
        return mUnderlying.getTables();
    }

    public int hashCode() {
        return mUnderlying.hashCode();
    }

    //    public Cursor query(SQLiteDatabase db, String[] projectionIn, String selection, String[] selectionArgs, String groupBy, String having, String sortOrder, String limit, CancellationSignal cancellationSignal) {
    //        return mUnderlying.query(db, projectionIn, selection, selectionArgs, groupBy, having, sortOrder, limit, cancellationSignal);
    //    }
    //
    public Cursor query(SQLiteDatabaseCompat db, String[] projectionIn, String selection, String[] selectionArgs, String groupBy, String having, String sortOrder, String limit) {
        return mUnderlying.query(db.mUnderlying, projectionIn, selection, selectionArgs, groupBy, having, sortOrder, limit);
    }

    public Cursor query(SQLiteDatabaseCompat db, String[] projectionIn, String selection, String[] selectionArgs, String groupBy, String having, String sortOrder) {
        return mUnderlying.query(db.mUnderlying, projectionIn, selection, selectionArgs, groupBy, having, sortOrder);
    }

    public void setCursorFactory(CursorFactory factory) {
        mUnderlying.setCursorFactory(factory);
    }

    public void setDistinct(boolean distinct) {
        mUnderlying.setDistinct(distinct);
    }

    public void setProjectionMap(Map<String, String> columnMap) {
        mUnderlying.setProjectionMap(columnMap);
    }

    public void setStrict(boolean flag) {
        mUnderlying.setStrict(flag);
    }

    public void setTables(String inTables) {
        mUnderlying.setTables(inTables);
    }

    public String toString() {
        return mUnderlying.toString();
    }
}
