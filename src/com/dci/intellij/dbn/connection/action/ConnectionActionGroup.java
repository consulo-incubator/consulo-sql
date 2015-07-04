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

package com.dci.intellij.dbn.connection.action;

import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.connection.transaction.action.ShowUncommittedChangesAction;
import com.dci.intellij.dbn.connection.transaction.action.ToggleAutoCommitAction;
import com.dci.intellij.dbn.connection.transaction.action.TransactionCommitAction;
import com.dci.intellij.dbn.connection.transaction.action.TransactionRollbackAction;
import com.intellij.openapi.actionSystem.DefaultActionGroup;

public class ConnectionActionGroup extends DefaultActionGroup {

    public ConnectionActionGroup(ConnectionHandler connectionHandler) {
        //add(new ConnectAction(connectionHandler));
        add(new TransactionCommitAction(connectionHandler));
        add(new TransactionRollbackAction(connectionHandler));
        add(new ToggleAutoCommitAction(connectionHandler));
        add(new ShowUncommittedChangesAction(connectionHandler));
        add(new OpenSQLConsoleAction(connectionHandler));
        addSeparator();
        add(new ShowDatabaseInformationAction(connectionHandler));
        add(new DisconnectAction(connectionHandler));
        add(new TestConnectivityAction(connectionHandler));
        addSeparator();
        add(new OpenConnectionSettingsAction(connectionHandler));
    }
}
