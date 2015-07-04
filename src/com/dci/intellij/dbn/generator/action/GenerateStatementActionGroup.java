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

package com.dci.intellij.dbn.generator.action;

import com.dci.intellij.dbn.browser.DatabaseBrowserManager;
import com.dci.intellij.dbn.database.DatabaseCompatibilityInterface;
import com.dci.intellij.dbn.database.DatabaseFeature;
import com.dci.intellij.dbn.object.DBColumn;
import com.dci.intellij.dbn.object.DBDataset;
import com.dci.intellij.dbn.object.DBSchema;
import com.dci.intellij.dbn.object.DBTable;
import com.dci.intellij.dbn.object.common.DBObject;
import com.dci.intellij.dbn.object.common.DBSchemaObject;
import com.intellij.openapi.actionSystem.DefaultActionGroup;

import java.util.List;

public class GenerateStatementActionGroup extends DefaultActionGroup {

    public GenerateStatementActionGroup(DBObject object) {
        super("Extract SQL Statement", true);
        if (object instanceof DBColumn || object instanceof DBDataset) {
            List<DBObject> selectedObjects = DatabaseBrowserManager.getInstance(object.getProject()).getSelectedObjects();
            add(new GenerateSelectStatementAction(selectedObjects));
        }

        if (object instanceof DBTable) {
            DBTable table = (DBTable) object;
            add(new GenerateInsertStatementAction(table));
        }

        DatabaseCompatibilityInterface compatibilityInterface = DatabaseCompatibilityInterface.getInstance(object);
        if (object instanceof DBSchemaObject &&
                object.getParentObject() instanceof DBSchema &&
                compatibilityInterface.supportsFeature(DatabaseFeature.OBJECT_DDL_EXTRACTION)) {
            if (getChildrenCount() > 1) {
                addSeparator();
            }
            add(new GenerateDDLStatementAction(object));
        }
    }
}