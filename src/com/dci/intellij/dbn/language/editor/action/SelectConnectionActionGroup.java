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

package com.dci.intellij.dbn.language.editor.action;

import com.dci.intellij.dbn.connection.ConnectionBundle;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.connection.ConnectionManager;
import com.dci.intellij.dbn.connection.ProjectConnectionBundle;
import com.dci.intellij.dbn.options.action.OpenSettingsDialogAction;
import com.intellij.openapi.actionSystem.Anchor;
import com.intellij.openapi.actionSystem.Constraints;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;

public class SelectConnectionActionGroup extends DefaultActionGroup {
    private static SelectConnectionAction NO_CONNECTION = new SelectConnectionAction(null);

    public SelectConnectionActionGroup(Project project) {
        add(new OpenSettingsDialogAction(), new Constraints(Anchor.FIRST, null));
        addSeparator();
        add(NO_CONNECTION);

        ProjectConnectionBundle projectConnectionBundle = ProjectConnectionBundle.getInstance(project);
        for (ConnectionHandler virtualConnectionHandler : projectConnectionBundle.getVirtualConnections()) {
            SelectConnectionAction connectionAction = new SelectConnectionAction(virtualConnectionHandler);
            add(connectionAction);
        }

        ConnectionManager connectionManager = ConnectionManager.getInstance(project);
        for (ConnectionBundle connectionBundle : connectionManager.getConnectionBundles()) {
            if (connectionBundle.getConnectionHandlers().size() > 0) {
                addSeparator();
                for (ConnectionHandler connectionHandler : connectionBundle.getConnectionHandlers()) {
                    SelectConnectionAction connectionAction = new SelectConnectionAction(connectionHandler);
                    add(connectionAction);
                }
            }
        }
    }
}
