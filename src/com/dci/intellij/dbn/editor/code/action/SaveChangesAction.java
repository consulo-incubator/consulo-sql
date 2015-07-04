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
import com.dci.intellij.dbn.common.compatibility.CompatibilityUtil;
import com.dci.intellij.dbn.common.thread.WriteActionRunner;
import com.dci.intellij.dbn.common.util.ActionUtil;
import com.dci.intellij.dbn.editor.DBContentType;
import com.dci.intellij.dbn.editor.code.SourceCodeEditorManager;
import com.dci.intellij.dbn.object.common.DBSchemaObject;
import com.dci.intellij.dbn.object.common.status.DBObjectStatus;
import com.dci.intellij.dbn.vfs.SourceCodeFile;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;

public class SaveChangesAction extends AbstractSourceCodeEditorAction {
    public SaveChangesAction() {
        super("", null, Icons.CODE_EDITOR_SAVE);
    }

    public void actionPerformed(final AnActionEvent e) {
        final Project project = ActionUtil.getProject(e);
        final Editor editor = getEditor(e);
        final SourceCodeFile virtualFile = getSourcecodeFile(e);

        new WriteActionRunner() {
            public void run() {
                Document document = editor.getDocument();
                CompatibilityUtil.stripDocumentTrailingSpaces(project, document);
                SourceCodeEditorManager.getInstance(project).updateSourceToDatabase(editor, virtualFile);
            }
        }.start();
    }

    public void update(AnActionEvent e) {
        SourceCodeFile virtualFile = getSourcecodeFile(e);
        Presentation presentation = e.getPresentation();
        if (virtualFile == null) {
            presentation.setEnabled(false);
        } else {
            String text =
                    virtualFile.getContentType() == DBContentType.CODE_SPEC ? "Save spec" :
                            virtualFile.getContentType() == DBContentType.CODE_BODY ? "Save body" : "Save";

            DBSchemaObject object = virtualFile.getObject();
            boolean isSaving = object.getStatus().is(DBObjectStatus.SAVING);
            presentation.setEnabled(!isSaving && virtualFile.isModified());
            presentation.setText(text);
        }
    }
}
