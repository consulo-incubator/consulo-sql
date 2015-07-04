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

package com.dci.intellij.dbn.execution.common.message.action;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.execution.statement.result.ui.StatementViewerPopup;
import com.dci.intellij.dbn.execution.statement.result.StatementExecutionResult;
import com.dci.intellij.dbn.execution.common.message.ui.tree.StatementExecutionMessageNode;
import com.dci.intellij.dbn.execution.common.message.ui.tree.MessagesTree;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import java.awt.Component;

public class ViewExecutedStatementAction extends ExecutionMessagesAction {
    public ViewExecutedStatementAction(MessagesTree messagesTree) {
        super(messagesTree, "View SQL statement", Icons.EXEC_RESULT_VIEW_STATEMENT);
    }

    public void actionPerformed(AnActionEvent e) {
        getMessagesTree().grabFocus();
        StatementExecutionMessageNode execMessageNode = (StatementExecutionMessageNode) getMessagesTree().getSelectionPath().getLastPathComponent();
        StatementExecutionResult executionResult = execMessageNode.getExecutionMessage().getExecutionResult();
        StatementViewerPopup statementViewer = new StatementViewerPopup(executionResult);
        statementViewer.show((Component) e.getInputEvent().getSource());
    }

    public void update(AnActionEvent e) {
        boolean enabled =
                getMessagesTree().getSelectionPath() != null &&
                 getMessagesTree().getSelectionPath().getLastPathComponent() instanceof StatementExecutionMessageNode;
        Presentation presentation = e.getPresentation();
        presentation.setEnabled(enabled);
    }
}