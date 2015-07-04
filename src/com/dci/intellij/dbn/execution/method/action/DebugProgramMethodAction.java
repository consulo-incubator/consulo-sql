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

package com.dci.intellij.dbn.execution.method.action;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.object.DBMethod;
import com.dci.intellij.dbn.object.DBProgram;
import com.dci.intellij.dbn.object.action.ObjectListShowAction;
import com.dci.intellij.dbn.object.common.DBObject;
import com.intellij.openapi.actionSystem.AnAction;

import java.util.ArrayList;
import java.util.List;

public class DebugProgramMethodAction extends ObjectListShowAction {
    public DebugProgramMethodAction(DBProgram program) {
        super("Debug...", program);
        getTemplatePresentation().setIcon(Icons.METHOD_EXECUTION_DEBUG);
    }

    public List<DBObject> getObjectList() {
        DBProgram program = (DBProgram) sourceObject;
        List objects = new ArrayList();
        objects.addAll(program.getProcedures());
        objects.addAll(program.getFunctions());
        return objects;
    }

    public String getTitle() {
        return "Select method to debug";
    }

    public String getEmptyListMessage() {
        DBProgram program = (DBProgram) sourceObject;
        return "The " + program.getQualifiedNameWithType() + " has no methods.";
    }

     public String getListName() {
        return "executable elements";
    }

    protected AnAction createObjectAction(DBObject object) {
        return new DebugMethodAction((DBProgram) this.sourceObject, (DBMethod) object);
    }
}