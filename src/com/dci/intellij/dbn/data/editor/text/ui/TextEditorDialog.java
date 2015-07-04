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

package com.dci.intellij.dbn.data.editor.text.ui;

import com.dci.intellij.dbn.common.ui.dialog.DBNDialog;
import com.dci.intellij.dbn.common.util.MessageUtil;
import com.dci.intellij.dbn.data.editor.text.TextEditorAdapter;
import com.dci.intellij.dbn.data.editor.ui.UserValueHolder;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

import javax.swing.Action;
import javax.swing.JComponent;
import java.sql.SQLException;

public class TextEditorDialog extends DBNDialog implements DocumentListener {
    private TextEditorForm mainForm;

    private TextEditorDialog(Project project, TextEditorAdapter textEditorAdapter) throws SQLException {
        super(project, "Edit LOB content (column " + textEditorAdapter.getUserValueHolder().getName() + ")", true);
        UserValueHolder userValueHolder = textEditorAdapter.getUserValueHolder();
        mainForm = new TextEditorForm(this, userValueHolder, textEditorAdapter);
        getCancelAction().putValue(Action.NAME, "Close");
        getOKAction().setEnabled(false);
        setModal(true);
        init();
    }

    protected String getDimensionServiceKey() {
        return "DBNavigator.LOBDataEditor";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return mainForm.getEditorComponent();
    }

    public static void show(Project project, TextEditorAdapter textEditorAdapter) {
        try {
            TextEditorDialog dialog = new TextEditorDialog(project, textEditorAdapter);
            dialog.show();
        } catch (SQLException e) {
            MessageUtil.showErrorDialog("Could not load LOB content from database.", e);
        }
    }

    protected final Action[] createActions() {
        return new Action[]{
                getOKAction(),
                getCancelAction(),
                getHelpAction()
        };
    }

    @Override
    protected void doOKAction() {
        super.doOKAction();
        try {
            mainForm.writeUserValue();
        } catch (SQLException e) {
            MessageUtil.showErrorDialog("Could not write LOB content to database.", e);
        }
    }

    @Nullable
    protected JComponent createCenterPanel() {
        return mainForm.getComponent();
    }

    public void beforeDocumentChange(DocumentEvent event) {

    }

    public void documentChanged(DocumentEvent event) {
        getCancelAction().putValue(Action.NAME, "Cancel");
        getOKAction().setEnabled(true);
    }

    @Override
    protected void dispose() {
        super.dispose();
        mainForm.dispose();
    }
}
