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

package com.dci.intellij.dbn.execution.method.browser.action;

import com.dci.intellij.dbn.common.ui.DBNComboBoxAction;
import com.dci.intellij.dbn.common.util.ActionUtil;
import com.dci.intellij.dbn.common.util.NamingUtil;
import com.dci.intellij.dbn.connection.ConnectionBundle;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.connection.ConnectionManager;
import com.dci.intellij.dbn.connection.ProjectConnectionBundle;
import com.dci.intellij.dbn.execution.method.browser.ui.MethodExecutionBrowserForm;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;
import javax.swing.JComponent;

public class SelectConnectionComboBoxAction extends DBNComboBoxAction {
    MethodExecutionBrowserForm browserComponent;

    public SelectConnectionComboBoxAction(MethodExecutionBrowserForm browserComponent) {
        this.browserComponent = browserComponent;
    }

    @NotNull
    protected DefaultActionGroup createPopupActionGroup(JComponent component) {
        DefaultActionGroup actionGroup = new DefaultActionGroup();
        Project project = ActionUtil.getProject(component);
        ProjectConnectionBundle projectConnectionManager = ProjectConnectionBundle.getInstance(project);
        for (ConnectionHandler virtualConnectionHandler : projectConnectionManager.getVirtualConnections()) {
            SelectConnectionAction connectionAction = new SelectConnectionAction(browserComponent, virtualConnectionHandler);
            actionGroup.add(connectionAction);
        }

        ConnectionManager connectionManager = ConnectionManager.getInstance(project);
        for (ConnectionBundle connectionBundle : connectionManager.getConnectionBundles()) {
            if (connectionBundle.getConnectionHandlers().size() > 0) {
                actionGroup.addSeparator();
                for (ConnectionHandler connectionHandler : connectionBundle.getConnectionHandlers()) {
                    SelectConnectionAction connectionAction = new SelectConnectionAction(browserComponent, connectionHandler);
                    actionGroup.add(connectionAction);
                }
            }
        }

        return actionGroup;
    }

    public synchronized void update(AnActionEvent e) {
        Presentation presentation = e.getPresentation();
        String text = "Select connection";
        Icon icon = null;

        ConnectionHandler connectionHandler = browserComponent.getSettings().getConnectionHandler();
        if (connectionHandler != null) {
            text = NamingUtil.enhanceUnderscoresForDisplay(connectionHandler.getQualifiedName());
            icon = connectionHandler.getIcon();
        }

        presentation.setText(text);
        presentation.setIcon(icon);
    }
 }