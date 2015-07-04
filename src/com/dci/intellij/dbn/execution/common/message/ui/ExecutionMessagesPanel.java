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

package com.dci.intellij.dbn.execution.common.message.ui;

import com.dci.intellij.dbn.common.util.ActionUtil;
import com.dci.intellij.dbn.execution.common.message.action.CloseMessagesWindowAction;
import com.dci.intellij.dbn.execution.common.message.action.CollapseMessagesTreeAction;
import com.dci.intellij.dbn.execution.common.message.action.ExpandMessagesTreeAction;
import com.dci.intellij.dbn.execution.common.message.action.OpenSettingsAction;
import com.dci.intellij.dbn.execution.common.message.action.ViewExecutedStatementAction;
import com.dci.intellij.dbn.execution.common.message.ui.tree.MessagesTree;
import com.dci.intellij.dbn.execution.compiler.CompilerMessage;
import com.dci.intellij.dbn.execution.statement.StatementExecutionMessage;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.ui.IdeBorderFactory;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ExecutionMessagesPanel {
    private JPanel mainPanel;
    private JPanel actionsPanel;
    private JPanel statusPanel;
    private JScrollPane messagesScrollPane;
    private JPanel messagesPanel;

    private MessagesTree tMessages;

    public ExecutionMessagesPanel() {
        tMessages = new MessagesTree();
        messagesScrollPane.setViewportView(tMessages);
        messagesPanel.setBorder(IdeBorderFactory.createBorder());
        ActionToolbar actionToolbar = ActionUtil.createActionToolbar(
                "DBNavigator.ExecutionMessages.Controls", false,
                new CloseMessagesWindowAction(tMessages),
                new ViewExecutedStatementAction(tMessages),
                new ExpandMessagesTreeAction(tMessages),
                new CollapseMessagesTreeAction(tMessages),
                ActionUtil.SEPARATOR,
                new OpenSettingsAction(tMessages));
        actionsPanel.add(actionToolbar.getComponent());

    }

    public void addExecutionMessage(StatementExecutionMessage executionMessage, boolean focus) {
        tMessages.addExecutionMessage(executionMessage, focus);
    }

    public void addCompilerMessage(CompilerMessage compilerMessage, boolean focus) {
        tMessages.addCompilerMessage(compilerMessage, focus);
    }

    public void reset() {
        tMessages.reset();
    }

    public JComponent getComponent() {
        return mainPanel;
    }

    public void select(CompilerMessage compilerMessage) {
        tMessages.selectCompilerMessage(compilerMessage);
    }
}
