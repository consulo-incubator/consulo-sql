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
import com.dci.intellij.dbn.object.common.DBSchemaObject;
import com.intellij.openapi.actionSystem.AnAction;

import java.util.List;

public class ReferencedObjectsListShowAction extends ObjectListShowAction {
    public ReferencedObjectsListShowAction(DBSchemaObject object) {
        super("Referenced objects", object);
    }

    public List<DBObject> getObjectList() {
        return ((DBSchemaObject) sourceObject).getReferencedObjects();
    }

    public String getTitle() {
        return "Objects referenced by " + sourceObject.getQualifiedNameWithType();
    }

    public String getEmptyListMessage() {
        return "No referenced objects found for " + sourceObject.getQualifiedNameWithType();
    }


    public String getListName() {
       return "referenced objects";
   }

    @Override
    protected AnAction createObjectAction(DBObject object) {
        return new NavigateToObjectAction(this.sourceObject, object);
    }

}