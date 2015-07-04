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

package com.dci.intellij.dbn.connection.ui;

import com.dci.intellij.dbn.common.ui.dialog.DBNDialog;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.language.common.DBLanguageFile;
import com.dci.intellij.dbn.object.DBSchema;
import org.jetbrains.annotations.Nullable;

import javax.swing.JComponent;

public class SelectCurrentSchemaDialog extends DBNDialog {
    private DBLanguageFile file;
    private SelectCurrentSchemaForm selectCurrentSchemaForm;

    public SelectCurrentSchemaDialog(DBLanguageFile file) {
        super(file.getProject(), "Select Current Schema", true);
        this.file = file;
        ConnectionHandler activeConnection = file.getActiveConnection();
        selectCurrentSchemaForm = new SelectCurrentSchemaForm(file, activeConnection);
        selectCurrentSchemaForm.setHintMessage(null);
        init();
    }

    protected String getDimensionServiceKey() {
        return "DBNavigator.SelectSchema";
    }

    @Nullable
    protected JComponent createCenterPanel() {
        return selectCurrentSchemaForm.getComponent();
    }

    public JComponent getPreferredFocusedComponent() {
        return selectCurrentSchemaForm.getSchemasList();
    }

    protected void doOKAction() {
        DBSchema currentSchema = selectCurrentSchemaForm.getSelectedSchema();
        file.setCurrentSchema(currentSchema);
        super.doOKAction();
    }

}
