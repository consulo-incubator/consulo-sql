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

import com.dci.intellij.dbn.common.thread.SimpleLaterInvocator;
import com.dci.intellij.dbn.common.util.ActionUtil;
import com.dci.intellij.dbn.editor.code.diff.DBSourceFileContent;
import com.dci.intellij.dbn.object.common.DBSchemaObject;
import com.dci.intellij.dbn.vfs.SourceCodeFile;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diff.DiffManager;
import com.intellij.openapi.diff.SimpleContent;
import com.intellij.openapi.diff.SimpleDiffRequest;
import com.intellij.openapi.project.Project;

public abstract class AbstractDiffAction extends AbstractSourceCodeEditorAction {
    public AbstractDiffAction(String text, String description, javax.swing.Icon icon) {
        super(text, description, icon);
    }

    protected void openDiffWindow(AnActionEvent e, final String referenceText, final String referenceTitle, final String windowTitle) {
        final SourceCodeFile virtualFile = getSourcecodeFile(e);
        final Project project = ActionUtil.getProject(e);
        if (virtualFile != null) {
            new SimpleLaterInvocator() {
                public void run() {
                    SimpleContent originalContent = new SimpleContent(referenceText, virtualFile.getFileType());
                    DBSourceFileContent changedContent = new DBSourceFileContent(project, virtualFile);

                    DBSchemaObject object = virtualFile.getObject();
                    String title =
                            object.getSchema().getName() + "." +
                                    object.getName() + " " +
                                    object.getTypeName() + " - " + windowTitle;
                    SimpleDiffRequest diffRequest = new SimpleDiffRequest(project, title);
                    diffRequest.setContents(originalContent, changedContent);
                    diffRequest.setContentTitles(referenceTitle + " ", "Your version ");

                    DiffManager.getInstance().getIdeaDiffTool().show(diffRequest);
                }
            }.start();
        }
    }
}

