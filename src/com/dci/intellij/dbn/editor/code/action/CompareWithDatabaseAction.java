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

package com.dci.intellij.dbn.editor.code.action;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.common.util.MessageUtil;
import com.dci.intellij.dbn.object.common.DBSchemaObject;
import com.dci.intellij.dbn.vfs.SourceCodeFile;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;

import java.sql.SQLException;

public class CompareWithDatabaseAction extends AbstractDiffAction {
    public CompareWithDatabaseAction() {
        super("Compare with database", null, Icons.CODE_EDITOR_DIFF_DB);
    }

    public void actionPerformed(AnActionEvent e) {
        SourceCodeFile virtualFile = getSourcecodeFile(e);
        if (virtualFile == null) {
            e.getPresentation().setEnabled(false);
        } else {
            Editor editor = getEditor(e);
            String content = editor.getDocument().getText();
            virtualFile.setContent(content);
            DBSchemaObject object = virtualFile.getObject();
            try {
                String referenceText = object.loadCodeFromDatabase(virtualFile.getContentType());
                openDiffWindow(e, referenceText, "Database version", "Local version vs. database version");
            } catch (SQLException e1) {
                MessageUtil.showErrorDialog(
                        "Could not load sourcecode for " +
                        object.getQualifiedNameWithType() + " from database.", e1);
            }


        }
    }

    public void update(AnActionEvent e) {
        Editor editor = getEditor(e);
        e.getPresentation().setText("Compare with database");
        e.getPresentation().setEnabled(editor != null);
    }
}
