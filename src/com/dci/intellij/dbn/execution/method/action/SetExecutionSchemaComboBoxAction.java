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

package com.dci.intellij.dbn.execution.method.action;

import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.execution.method.MethodExecutionInput;
import com.dci.intellij.dbn.object.DBSchema;
import com.dci.intellij.dbn.common.util.NamingUtil;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.actionSystem.ex.ComboBoxAction;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class SetExecutionSchemaComboBoxAction extends ComboBoxAction {
    private MethodExecutionInput executionInput;

    public SetExecutionSchemaComboBoxAction(MethodExecutionInput executionInput) {
        this.executionInput = executionInput;
        DBSchema schema = executionInput.getExecutionSchema();
        if (schema != null) {
            Presentation presentation = getTemplatePresentation();
            presentation.setText(NamingUtil.enhanceUnderscoresForDisplay(schema.getName()));
            presentation.setIcon(schema.getIcon());
        }
    }

    @NotNull
    protected DefaultActionGroup createPopupActionGroup(JComponent jComponent) {
        ConnectionHandler connectionHandler = executionInput.getConnectionHandler();
        DefaultActionGroup actionGroup = new DefaultActionGroup();
        for (DBSchema schema : connectionHandler.getObjectBundle().getSchemas()){
            actionGroup.add(new SetExecutionSchemaAction(executionInput, schema));
        }
        return actionGroup;
    }

    public void update(AnActionEvent e) {
        DBSchema schema = executionInput.getExecutionSchema();
        Presentation presentation = e.getPresentation();
        presentation.setText(NamingUtil.enhanceUnderscoresForDisplay(schema.getName()));
        presentation.setIcon(schema.getIcon());

    }
 }