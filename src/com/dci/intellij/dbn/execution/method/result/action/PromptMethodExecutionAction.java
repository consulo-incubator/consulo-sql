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

package com.dci.intellij.dbn.execution.method.result.action;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.execution.method.MethodExecutionInput;
import com.dci.intellij.dbn.execution.method.MethodExecutionManager;
import com.dci.intellij.dbn.execution.method.result.MethodExecutionResult;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

public class PromptMethodExecutionAction extends MethodExecutionResultAction {
    public PromptMethodExecutionAction(MethodExecutionResult executionResult) {
        super(executionResult, "Open execution dialog", Icons.METHOD_EXECUTION_DIALOG);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = executionResult.getProject();
        MethodExecutionManager executionManager = MethodExecutionManager.getInstance(project);
        MethodExecutionInput executionInput = executionResult.getExecutionInput();
        if (executionManager.promptExecutionDialog(executionInput, false)) {
            executionManager.execute(executionInput);
        }
    }

    @Override
    public void update(AnActionEvent e) {
        e.getPresentation().setEnabled(
                !executionResult.isDebug() &&
                !executionResult.getExecutionInput().isExecuting());
    }    
}