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
import com.dci.intellij.dbn.common.util.NamingUtil;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.execution.method.browser.ui.MethodExecutionBrowserForm;
import com.dci.intellij.dbn.object.DBSchema;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.Presentation;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;
import javax.swing.JComponent;

public class SelectSchemaComboBoxAction extends DBNComboBoxAction {
    MethodExecutionBrowserForm browserComponent;

    public SelectSchemaComboBoxAction(MethodExecutionBrowserForm browserComponent) {
        this.browserComponent = browserComponent;
    }

    @NotNull
    protected DefaultActionGroup createPopupActionGroup(JComponent jComponent) {
        DefaultActionGroup actionGroup = new DefaultActionGroup();
        ConnectionHandler connectionHandler = browserComponent.getSettings().getConnectionHandler();
        if (connectionHandler != null) {
            for (DBSchema schema : connectionHandler.getObjectBundle().getSchemas()) {
                SelectSchemaAction selectSchemaAction = new SelectSchemaAction(browserComponent, schema);
                actionGroup.add(selectSchemaAction);
            }
        }
        return actionGroup;
    }

    public synchronized void update(AnActionEvent e) {
        Presentation presentation = e.getPresentation();
        String text = "Schema";
        Icon icon = null;

        DBSchema schema = browserComponent.getSettings().getSchema();
        if (schema != null) {
            text = NamingUtil.enhanceUnderscoresForDisplay(schema.getName());
            icon = schema.getIcon();
        }

        presentation.setText(text);
        presentation.setIcon(icon);
    }
 }