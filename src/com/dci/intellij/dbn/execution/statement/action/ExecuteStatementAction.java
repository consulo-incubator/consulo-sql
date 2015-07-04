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

package com.dci.intellij.dbn.execution.statement.action;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.common.util.ActionUtil;
import com.dci.intellij.dbn.common.util.DocumentUtil;
import com.dci.intellij.dbn.common.util.EditorUtil;
import com.dci.intellij.dbn.execution.statement.StatementExecutionManager;
import com.dci.intellij.dbn.language.common.DBLanguageFile;
import com.dci.intellij.dbn.language.common.psi.PsiUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;

public class ExecuteStatementAction extends AnAction {
    public void actionPerformed(AnActionEvent e) {
        Project project = ActionUtil.getProject(e);
        Editor editor = EditorUtil.getSelectedEditor(project);
        StatementExecutionManager.getInstance(project).executeSelectedStatement(editor);
        DocumentUtil.refreshEditorAnnotations(project);
    }

    public void update(AnActionEvent e) {
        Presentation presentation = e.getPresentation();
        presentation.setEnabled(isEnabled(e));
        presentation.setIcon(Icons.STMT_EXECUTION_RUN);
        presentation.setText("Execute statement");
    }

    private boolean isEnabled(AnActionEvent e) {
        Project project = ActionUtil.getProject(e);
        if (project == null) return false;

        Editor editor = EditorUtil.getSelectedEditor(project);
        if (editor == null) return false;

        PsiFile psiFile = PsiUtil.getPsiFile(project, editor.getDocument());
        return psiFile instanceof DBLanguageFile;
    }
}
