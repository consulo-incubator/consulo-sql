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
import com.dci.intellij.dbn.common.util.NamingUtil;
import com.dci.intellij.dbn.debugger.DatabaseDebuggerManager;
import com.dci.intellij.dbn.object.DBMethod;
import com.dci.intellij.dbn.object.DBProgram;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;

public class DebugMethodAction extends DumbAwareAction {
    private DBMethod method;
    public DebugMethodAction(DBMethod method) {
        super("Debug...", "", Icons.METHOD_EXECUTION_DEBUG);
        this.method = method;
    }

    public DebugMethodAction(DBProgram program, DBMethod method) {
        super(NamingUtil.enhanceUnderscoresForDisplay(method.getName()), "", method.getIcon());
        this.method = method;
    }

    public void actionPerformed(AnActionEvent e) {
        DatabaseDebuggerManager executionManager = DatabaseDebuggerManager.getInstance(method.getProject());
        executionManager.createDebugConfiguration(method, e.getDataContext());
    }
}
