/*
 * Copyright 2012-2014 Dan Cioca
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dci.intellij.dbn.object.impl;

import com.dci.intellij.dbn.browser.model.BrowserTreeNode;
import com.dci.intellij.dbn.browser.ui.HtmlToolTipBuilder;
import com.dci.intellij.dbn.editor.DBContentType;
import com.dci.intellij.dbn.object.DBNestedTable;
import com.dci.intellij.dbn.object.DBNestedTableColumn;
import com.dci.intellij.dbn.object.DBTable;
import com.dci.intellij.dbn.object.DBType;
import com.dci.intellij.dbn.object.common.DBObjectImpl;
import com.dci.intellij.dbn.object.common.DBObjectType;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBNestedTableImpl extends DBObjectImpl implements DBNestedTable {
    private List<DBNestedTableColumn> columns;
    private DBType type;

    public DBNestedTableImpl(DBTable parent, ResultSet resultSet) throws SQLException {
        super(parent, DBContentType.NONE, resultSet);

    }

    @Override
    protected void initObject(ResultSet resultSet) throws SQLException {
        name = resultSet.getString("NESTED_TABLE_NAME");

        String typeOwner = resultSet.getString("DATA_TYPE_OWNER");
        String typeName = resultSet.getString("DATA_TYPE_NAME");
        type = getConnectionHandler().getObjectBundle().getSchema(typeOwner).getType(typeName);
        // todo !!!
    }

    public DBObjectType getObjectType() {
        return DBObjectType.NESTED_TABLE;
    }

    public List<DBNestedTableColumn> getColumns() {
        if (columns == null) {
            columns = new ArrayList<DBNestedTableColumn>();
            //todo
        }
        return columns;
    }

    public DBNestedTableColumn getColumn(String name) {
        return (DBNestedTableColumn) getObjectByName(getColumns(), name);
    }

    public DBTable getTable() {
        return (DBTable) getParentObject();
    }

    public DBType getType() {
        type = (DBType) type.getUndisposedElement();
        return type;
    }

    public void buildToolTip(HtmlToolTipBuilder ttb) {
        ttb.append(true, getObjectType().getName(), true);
        ttb.createEmptyRow();
        super.buildToolTip(ttb);
    }

    @Override
    public void dispose() {
        super.dispose();
        type = null;
    }

    /*********************************************************
     *                     TreeElement                       *
     *********************************************************/

    public boolean isLeafTreeElement() {
        return true;
    }

    @NotNull
    public List<BrowserTreeNode> buildAllPossibleTreeChildren() {
        return BrowserTreeNode.EMPTY_LIST;
        //return getColumns();
    }
}
