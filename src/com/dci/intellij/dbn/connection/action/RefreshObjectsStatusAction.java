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

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;

public class RefreshObjectsStatusAction extends DumbAwareAction {

    private ConnectionHandler connectionHandler;

    public RefreshObjectsStatusAction(ConnectionHandler connectionHandler) {
        super("Refresh objects status", "", Icons.ACTION_REFRESH);
        this.connectionHandler = connectionHandler;
    }

    public void actionPerformed(AnActionEvent anActionEvent) {
        connectionHandler.getObjectBundle().refreshObjectsStatus();
    }
}
