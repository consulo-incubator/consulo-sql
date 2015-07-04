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

package com.dci.intellij.dbn.object.factory.ui.common;

import com.dci.intellij.dbn.common.ui.dialog.DBNDialog;
import com.dci.intellij.dbn.object.factory.DatabaseObjectFactory;
import com.dci.intellij.dbn.object.factory.ObjectFactoryInput;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

import javax.swing.JComponent;

public class ObjectFactoryInputDialog extends DBNDialog {
    private ObjectFactoryInputForm inputForm;

    public ObjectFactoryInputDialog(ObjectFactoryInputForm inputForm) {
        super(inputForm.getConnectionHandler().getProject(),
                "Create " + inputForm.getObjectType().getName(), true);
        this.inputForm = inputForm;
        setModal(true);
        setResizable(true);
        init();
    }

    protected String getDimensionServiceKey() {
        return "DBNavigator.ObjectFactoryInput";
    }

    @Nullable
    protected JComponent createCenterPanel() {
        return inputForm.getComponent();
    }

    public void doOKAction() {
        Project project = inputForm.getConnectionHandler().getProject();
        DatabaseObjectFactory factory = DatabaseObjectFactory.getInstance(project);
        ObjectFactoryInput factoryInput = inputForm.createFactoryInput(null);
        if (factory.createObject(factoryInput)) {
            super.doOKAction();
        }
    }

    public void doCancelAction() {
        super.doCancelAction();
    }

}
