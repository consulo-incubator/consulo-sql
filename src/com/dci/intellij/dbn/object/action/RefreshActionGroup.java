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

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.database.DatabaseCompatibilityInterface;
import com.dci.intellij.dbn.database.DatabaseFeature;
import com.dci.intellij.dbn.object.common.DBObject;
import com.dci.intellij.dbn.object.common.DBSchemaObject;
import com.dci.intellij.dbn.object.common.list.DBObjectList;
import com.intellij.openapi.actionSystem.DefaultActionGroup;

public class RefreshActionGroup  extends DefaultActionGroup {
    public RefreshActionGroup(DBObject object) {
        super("Refresh", true);
        getTemplatePresentation().setIcon(Icons.ACTION_REFRESH);
        DBObjectList objectList = (DBObjectList) object.getTreeParent();
        add(new ReloadObjectsAction(objectList));
        if (object instanceof DBSchemaObject &&
                DatabaseCompatibilityInterface.getInstance(object).supportsFeature(DatabaseFeature.OBJECT_INVALIDATION)) {
            add(new RefreshObjectsStatusAction(object.getConnectionHandler()));
        }
    }
}
