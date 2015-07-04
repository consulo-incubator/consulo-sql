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
import com.dci.intellij.dbn.editor.DBContentType;
import com.dci.intellij.dbn.object.DBNestedTable;
import com.dci.intellij.dbn.object.DBNestedTableColumn;
import com.dci.intellij.dbn.object.common.DBObjectImpl;
import com.dci.intellij.dbn.object.common.DBObjectType;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DBNestedTableColumnImpl extends DBObjectImpl implements DBNestedTableColumn {

    public DBNestedTableColumnImpl(DBNestedTable parent, ResultSet resultSet) throws SQLException {
        super(parent, DBContentType.NONE, resultSet);
        // todo !!!
    }

    @Override
    protected void initObject(ResultSet resultSet) throws SQLException {
    }

    public DBObjectType getObjectType() {
        return DBObjectType.NESTED_TABLE_COLUMN;
    }

    public DBNestedTable getNestedTable() {
        return (DBNestedTable) getParentObject();
    }

    @Override
    public void dispose() {
        super.dispose();
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
    }
}
