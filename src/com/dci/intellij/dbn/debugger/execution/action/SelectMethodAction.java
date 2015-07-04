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

package com.dci.intellij.dbn.debugger.execution.action;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.common.util.NamingUtil;
import com.dci.intellij.dbn.debugger.execution.DBProgramRunConfiguration;
import com.dci.intellij.dbn.execution.method.MethodExecutionInput;
import com.dci.intellij.dbn.object.DBMethod;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;

public class SelectMethodAction extends AbstractSelectMethodAction{
    private MethodExecutionInput executionInput;

    public SelectMethodAction(MethodExecutionInput executionInput, DBProgramRunConfiguration configuration) {
        super("", null, configuration);
        this.executionInput = executionInput;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        getConfiguration().setExecutionInput(executionInput);
    }

    @Override
    public void update(AnActionEvent e) {
        Presentation presentation = e.getPresentation();
        DBMethod method = executionInput.getMethod();
        if (method == null) {
            presentation.setIcon(Icons.DBO_METHOD);
        } else {
            presentation.setIcon(method.getOriginalIcon());
        }
        presentation.setText(NamingUtil.enhanceNameForDisplay(executionInput.getMethodIdentifier().getPath()));
    }
}
