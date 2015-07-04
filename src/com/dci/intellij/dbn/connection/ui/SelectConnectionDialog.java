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
import com.dci.intellij.dbn.common.util.DocumentUtil;
import com.dci.intellij.dbn.common.util.EditorUtil;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.language.common.DBLanguageFile;
import com.dci.intellij.dbn.object.DBSchema;
import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.Nullable;

import javax.swing.JComponent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SelectConnectionDialog extends DBNDialog implements ListSelectionListener{
    private DBLanguageFile file;
    private SelectConnectionForm selectConnectionForm;

    public SelectConnectionDialog(DBLanguageFile file) {
        super(file.getProject(), "Select Connection", true);
        this.file = file;
        selectConnectionForm = new SelectConnectionForm(file);
        selectConnectionForm.addListSelectionListener(this);
        getOKAction().setEnabled(selectConnectionForm.isValidSelection());
        init();
    }

    protected String getDimensionServiceKey() {
        return "DBNavigator.SelectConnection";
    }

    @Nullable
    protected JComponent createCenterPanel() {
        return selectConnectionForm.getComponent();
    }

    public JComponent getPreferredFocusedComponent() {
        return selectConnectionForm.getConnectionsList();
    }

    protected void doOKAction() {
        ConnectionHandler activeConnection = selectConnectionForm.getSelectedConnection();
        DBSchema currentSchema = selectConnectionForm.getSelectedSchema();
        file.setActiveConnection(activeConnection);
        file.setCurrentSchema(currentSchema);
        Editor editor = EditorUtil.getSelectedEditor(file.getProject());
        DocumentUtil.touchDocument(editor);
        super.doOKAction();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        getOKAction().setEnabled(selectConnectionForm.isValidSelection());    
    }
}
