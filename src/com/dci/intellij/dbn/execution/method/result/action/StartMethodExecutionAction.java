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
import com.dci.intellij.dbn.execution.method.MethodExecutionManager;
import com.dci.intellij.dbn.execution.method.result.MethodExecutionResult;
import com.intellij.openapi.actionSystem.AnActionEvent;
           
public class StartMethodExecutionAction extends MethodExecutionResultAction {
    public StartMethodExecutionAction(MethodExecutionResult executionResult) {
        super(executionResult, "Execute again", Icons.METHOD_EXECUTION_RERUN);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        MethodExecutionManager executionManager = MethodExecutionManager.getInstance(executionResult.getProject());
        executionManager.execute(executionResult.getExecutionInput());
    }

    @Override
    public void update(AnActionEvent e) {
        e.getPresentation().setEnabled(
                !executionResult.isDebug() &&
                !executionResult.getExecutionInput().isExecuting());
    }
}