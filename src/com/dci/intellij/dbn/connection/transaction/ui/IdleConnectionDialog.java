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

package com.dci.intellij.dbn.connection.transaction.ui;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.common.TimeUtil;
import com.dci.intellij.dbn.common.ui.dialog.DialogWithTimeout;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.connection.transaction.DatabaseTransactionManager;
import com.dci.intellij.dbn.connection.transaction.TransactionAction;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import java.awt.event.ActionEvent;

public class IdleConnectionDialog extends DialogWithTimeout {
    private IdleConnectionDialogForm idleConnectionDialogForm;
    private ConnectionHandler connectionHandler;

    public IdleConnectionDialog(ConnectionHandler connectionHandler) {
        super(connectionHandler.getProject(), "Idle connection", true, TimeUtil.getSeconds(5));
        this.connectionHandler = connectionHandler;
        idleConnectionDialogForm = new IdleConnectionDialogForm(connectionHandler, 5);
        setModal(false);
        init();
    }


    @Override
    protected JComponent createContentComponent() {
        return idleConnectionDialogForm.getComponent();
    }

    @Override
    public void doDefaultAction() {
        rollback();
    }

    @Override
    protected void doOKAction() {
        ConnectionHandler handler = connectionHandler;
        super.doOKAction();
        handler.getConnectionStatus().setResolvingIdleStatus(false);
    }

    @Override
    public void doCancelAction() {
        ping();
    }

    protected String getDimensionServiceKey() {
        return "DBNavigator.IdleConnectionDialog";
    }

    @NotNull
    protected final Action[] createActions() {
        return new Action[]{
                new CommitAction(),
                new RollbackAction(),
                new KeepAliveAction(),
                getHelpAction()
        };
    }

    private class CommitAction extends AbstractAction {
        public CommitAction() {
            super("Commit", Icons.CONNECTION_COMMIT);
        }

        public void actionPerformed(ActionEvent e) {
            commit();
        }
    }

    private class RollbackAction extends AbstractAction {
        public RollbackAction() {
            super("Rollback", Icons.CONNECTION_ROLLBACK);
        }
        public void actionPerformed(ActionEvent e) {
            rollback();
        }

    }
    private class KeepAliveAction extends AbstractAction {
        public KeepAliveAction() {
            super("Keep Alive");
        }

        public void actionPerformed(ActionEvent e) {
            ping();
        }
    }

    private void commit() {
        ConnectionHandler handler = connectionHandler;
        DatabaseTransactionManager transactionManager = getTransactionManager();
        doOKAction();
        transactionManager.execute(handler, true, TransactionAction.COMMIT, TransactionAction.DISCONNECT_IDLE);
    }

    private void rollback() {
        ConnectionHandler handler = connectionHandler;
        DatabaseTransactionManager transactionManager = getTransactionManager();
        doOKAction();
        transactionManager.execute(handler, true, TransactionAction.ROLLBACK_IDLE, TransactionAction.DISCONNECT_IDLE);
    }

    private void ping() {
        ConnectionHandler handler = connectionHandler;
        DatabaseTransactionManager transactionManager = getTransactionManager();
        doOKAction();
        transactionManager.execute(handler, true, TransactionAction.PING);
    }

    private DatabaseTransactionManager getTransactionManager() {
        Project project = getProject();
        return DatabaseTransactionManager.getInstance(project);
    }

    @Override
    protected void dispose() {
        super.dispose();
        connectionHandler = null;
    }
}
