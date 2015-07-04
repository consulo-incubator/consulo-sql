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

package com.dci.intellij.dbn.editor.data.action;

import com.dci.intellij.dbn.common.util.NamingUtil;
import com.dci.intellij.dbn.editor.data.filter.DatasetFilterInput;
import com.dci.intellij.dbn.object.DBColumn;
import com.dci.intellij.dbn.object.action.ObjectListShowAction;
import com.dci.intellij.dbn.object.common.DBObject;
import com.intellij.openapi.actionSystem.AnAction;

import java.util.ArrayList;
import java.util.List;

public class ShowReferencingRecordsAction extends ObjectListShowAction{
    private Object columnValue;

    public ShowReferencingRecordsAction(DBColumn column, Object columnValue) {
        super("Show referencing records...", column);
        this.columnValue = columnValue;
    }


    @Override
    public List<DBObject> getObjectList() {
        DBColumn column = (DBColumn) sourceObject;
        return new ArrayList<DBObject>(column.getReferencingColumns());
    }

    @Override
    public String getTitle() {
        return "Referencing datasets";
    }

    @Override
    public String getEmptyListMessage() {
        return "No referencing records found";
    }

    @Override
    public String getListName() {
        return "Referencing records";
    }

    @Override
    protected AnAction createObjectAction(DBObject object) {
        DBColumn column = (DBColumn) object;
        DatasetFilterInput filterInput = new DatasetFilterInput(column.getDataset());
        filterInput.setColumnValue(column, columnValue);
        String actionText = NamingUtil.enhanceNameForDisplay(column.getDataset().getName() + " - " + column.getName() + "");
        return new ShowRecordsAction(actionText, filterInput) {};
    }
}
