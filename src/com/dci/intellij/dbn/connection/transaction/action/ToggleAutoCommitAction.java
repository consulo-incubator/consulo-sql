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

import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.connection.transaction.DatabaseTransactionManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;

public class ToggleAutoCommitAction extends DumbAwareAction {
    private ConnectionHandler connectionHandler;

    public ToggleAutoCommitAction(ConnectionHandler connectionHandler) {
        super("Turn Auto-Commit ON/OFF");
        this.connectionHandler = connectionHandler;

    }

    public void actionPerformed(AnActionEvent e) {
        DatabaseTransactionManager transactionManager = DatabaseTransactionManager.getInstance(connectionHandler.getProject());
        transactionManager.toggleAutoCommit(connectionHandler);
    }

    @Override
    public void update(AnActionEvent e) {
        e.getPresentation().setText(
                connectionHandler.isAutoCommit() ?
                        "Turn Auto-Commit OFF" :
                        "Turn Auto-Commit ON");
    }
}
