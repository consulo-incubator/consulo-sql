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

import com.dci.intellij.dbn.editor.DBContentType;
import com.dci.intellij.dbn.object.DBMethod;
import com.dci.intellij.dbn.object.DBProgram;
import com.dci.intellij.dbn.object.common.DBSchemaObject;
import com.intellij.openapi.actionSystem.DefaultActionGroup;

public class ExecuteActionGroup extends DefaultActionGroup {

    public ExecuteActionGroup(DBSchemaObject object) {
        super("Execute", true);
        if (object.getContentType() == DBContentType.CODE_SPEC_AND_BODY) {
            add(new RunProgramMethodAction((DBProgram) object));
            add(new DebugProgramMethodAction((DBProgram) object));
        } else {
            add(new RunMethodAction((DBMethod) object));
            add(new DebugMethodAction((DBMethod) object));
        }
    }
}