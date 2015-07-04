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

package com.dci.intellij.dbn.object.action;

import com.dci.intellij.dbn.editor.data.filter.global.DataDependencyPath;
import com.dci.intellij.dbn.editor.data.filter.global.DataDependencyPathBuilder;
import com.dci.intellij.dbn.object.DBTable;
import com.dci.intellij.dbn.object.common.DBObject;
import com.dci.intellij.dbn.object.common.DBObjectType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class TestAction extends AnAction {
    private DBObject object;
    public TestAction(DBObject object) {
        super("Test", "Test", null);
        this.object = object;
        setDefaultIcon(true);
    }

    public void actionPerformed(AnActionEvent e) {
        new Thread() {
            public void run() {
                if (object instanceof DBTable) {
                    DBTable table = (DBTable) object;
                    DBTable target = (DBTable) table.getSchema().getChildObject(DBObjectType.TABLE, "ALLOCATIONS", false);
                    DataDependencyPath[] shortestPath = new DataDependencyPath[1];
                    DataDependencyPathBuilder.buildDependencyPath(null, table.getColumns().get(0), target.getColumns().get(0), shortestPath);
                    System.out.println();
                }
            }
        }.start();
    }
}