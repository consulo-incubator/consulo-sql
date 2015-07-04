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

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.execution.statement.result.StatementExecutionCursorResult;
import com.dci.intellij.dbn.execution.statement.result.ui.StatementViewerPopup;
import com.intellij.openapi.actionSystem.AnActionEvent;

import java.awt.Component;

public class ExecutionResultViewStatementAction extends AbstractExecutionResultAction {
    public ExecutionResultViewStatementAction() {
        super("View SQL statement", Icons.FILE_BLOCK_SQL);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        StatementExecutionCursorResult executionResult = getExecutionResult(e);
        if (executionResult != null) {
            StatementViewerPopup statementViewer = new StatementViewerPopup(executionResult);
            statementViewer.show((Component) e.getInputEvent().getSource());
        }
    }

    @Override
    public void update(AnActionEvent e) {
        super.update(e);
        e.getPresentation().setText("View SQL statement");
    }
}
