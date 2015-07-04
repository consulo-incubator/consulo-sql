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

package com.dci.intellij.dbn.connection.transaction.action;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.common.util.ActionUtil;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.connection.transaction.DatabaseTransactionManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;

public class TransactionCommitAction extends DumbAwareAction {
    private ConnectionHandler connectionHandler;

    public TransactionCommitAction(ConnectionHandler connectionHandler) {
        super("Commit", "Commit connection", Icons.CONNECTION_COMMIT);
        this.connectionHandler = connectionHandler;

    }

    public void actionPerformed(AnActionEvent e) {
        Project project = ActionUtil.getProject(e);
        DatabaseTransactionManager transactionManager = DatabaseTransactionManager.getInstance(project);
        transactionManager.commit(connectionHandler, false, false);
    }

    @Override
    public void update(AnActionEvent e) {
        e.getPresentation().setEnabled(connectionHandler.hasUncommittedChanges());
        e.getPresentation().setVisible(!connectionHandler.isAutoCommit());
    }
}
