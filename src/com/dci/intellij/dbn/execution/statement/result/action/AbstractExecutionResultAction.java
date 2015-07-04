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

package com.dci.intellij.dbn.execution.statement.result.action;

import com.dci.intellij.dbn.common.action.DBNDataKeys;
import com.dci.intellij.dbn.common.util.ActionUtil;
import com.dci.intellij.dbn.execution.ExecutionManager;
import com.dci.intellij.dbn.execution.ExecutionResult;
import com.dci.intellij.dbn.execution.statement.result.StatementExecutionCursorResult;
import com.dci.intellij.dbn.execution.statement.result.StatementExecutionResult;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;

import javax.swing.Icon;

public abstract class AbstractExecutionResultAction extends DumbAwareAction {
    protected AbstractExecutionResultAction(String text, Icon icon) {
        super(text, null, icon);
    }

    public StatementExecutionCursorResult getExecutionResult(AnActionEvent e) {
        Project project = ActionUtil.getProject(e);
        if (project != null) {
            StatementExecutionResult result = e.getData(DBNDataKeys.STATEMENT_EXECUTION_RESULT);
            if (result == null) {
                ExecutionManager executionManager = ExecutionManager.getInstance(project);
                ExecutionResult executionResult = executionManager.getSelectedExecutionResult();
                if (executionResult instanceof StatementExecutionCursorResult) {
                    return (StatementExecutionCursorResult) executionResult;
                }

            } else if (result instanceof StatementExecutionCursorResult) {
                return (StatementExecutionCursorResult) result;
            }
        }
        return null;
    }

    @Override
    public void update(AnActionEvent e) {
        StatementExecutionCursorResult executionResult = getExecutionResult(e);
        e.getPresentation().setEnabled(executionResult != null);
    }

}
