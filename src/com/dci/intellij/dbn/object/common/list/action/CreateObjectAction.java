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

package com.dci.intellij.dbn.object.common.list.action;

import com.dci.intellij.dbn.object.DBSchema;
import com.dci.intellij.dbn.object.common.list.DBObjectList;
import com.dci.intellij.dbn.object.factory.DatabaseObjectFactory;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class CreateObjectAction extends AnAction {

    private DBObjectList objectList;

    public CreateObjectAction(DBObjectList objectList) {
        super("New " + objectList.getObjectType().getName());
        this.objectList = objectList;
    }

    public void actionPerformed(AnActionEvent anActionEvent) {
        DBSchema schema = (DBSchema) objectList.getParent();
        DatabaseObjectFactory factory =
                DatabaseObjectFactory.getInstance(schema.getProject());
        factory.openFactoryInputDialog(schema, objectList.getObjectType());
    }
}