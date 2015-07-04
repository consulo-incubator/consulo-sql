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

import com.dci.intellij.dbn.object.common.DBObject;
import com.dci.intellij.dbn.object.common.list.DBObjectNavigationList;
import com.intellij.openapi.actionSystem.DefaultActionGroup;

import java.util.List;

public class ObjectNavigationListActionGroup extends DefaultActionGroup {
    public static final int MAX_ITEMS = 30;
    private DBObjectNavigationList navigationList;
    private DBObject parentObject;
    private boolean showFullList;

    public ObjectNavigationListActionGroup(DBObject parentObject, DBObjectNavigationList navigationList, boolean showFullList) {
        super(navigationList.getName(), true);
        this.parentObject = parentObject;
        this.navigationList = navigationList;
        this.showFullList = showFullList;

        if (navigationList.getObject() != null) {
            add(new NavigateToObjectAction(parentObject, navigationList.getObject()));
        } else {
            List<DBObject> objects = getObjects();
            int itemsCount = showFullList ? (objects == null ? 0 : objects.size()) : MAX_ITEMS;
            buildNavigationActions(itemsCount);
        }
    }

    public DBObject getParentObject() {
        return parentObject;
    }

    private List<DBObject> getObjects() {
        List<DBObject> objects = navigationList.getObjects();
        if (objects == null) objects = navigationList.getObjectsProvider().getObjects();
        return objects;
    }

    private void buildNavigationActions(int length) {
        List<DBObject> objects = getObjects();
        if (objects != null) {
            for (int i=0; i<length; i++) {
                if (i == objects.size()) {
                    return;
                }
                DBObject object = objects.get(i);
                add(new NavigateToObjectAction(parentObject, object));
            }
            if (!showFullList && objects.size() > MAX_ITEMS) {
                add(new ObjectNavigationListShowAllAction(parentObject, navigationList));
            }
        }
    }
}
