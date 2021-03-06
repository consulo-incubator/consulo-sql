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

package com.dci.intellij.dbn.language.editor.ui;

import com.dci.intellij.dbn.common.ui.AutoCommitLabel;
import com.dci.intellij.dbn.common.ui.DBNFormImpl;
import com.dci.intellij.dbn.common.util.ActionUtil;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.connection.mapping.FileConnectionMappingManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class DBLanguageFileEditorToolbarForm extends DBNFormImpl {
    public static final Key<DBLanguageFileEditorToolbarForm> USER_DATA_KEY = new Key<DBLanguageFileEditorToolbarForm>("fileEditorToolbarForm");
    private JPanel mainPanel;
    private JPanel actionsPanel;
    private AutoCommitLabel autoCommitLabel;

    public DBLanguageFileEditorToolbarForm(Project project, VirtualFile file, String actionPlace) {
        ActionToolbar actionToolbar = ActionUtil.createActionToolbar(actionPlace, true, "DBNavigator.ActionGroup.FileEditor");
        actionsPanel.add(actionToolbar.getComponent(), BorderLayout.CENTER);

        ConnectionHandler connectionHandler = FileConnectionMappingManager.getInstance(project).getActiveConnection(file);
        autoCommitLabel.setConnectionHandler(connectionHandler);
    }

    @Override
    public JComponent getComponent() {
        return mainPanel;
    }

    @Override
    public void dispose() {
        super.dispose();
        autoCommitLabel.dispose();
    }

    public AutoCommitLabel getAutoCommitLabel() {
        return autoCommitLabel;
    }
}
