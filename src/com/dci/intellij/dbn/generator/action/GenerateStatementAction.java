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

package com.dci.intellij.dbn.generator.action;

import com.dci.intellij.dbn.common.thread.BackgroundTask;
import com.dci.intellij.dbn.common.thread.CommandWriteActionRunner;
import com.dci.intellij.dbn.common.thread.SimpleLaterInvocator;
import com.dci.intellij.dbn.common.util.ActionUtil;
import com.dci.intellij.dbn.common.util.EditorUtil;
import com.dci.intellij.dbn.common.util.MessageUtil;
import com.dci.intellij.dbn.generator.StatementGeneratorResult;
import com.dci.intellij.dbn.language.common.psi.PsiUtil;
import com.dci.intellij.dbn.language.sql.SQLFileType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public abstract class GenerateStatementAction extends AnAction {
    public GenerateStatementAction(String text) {
        super(text);
    }

    public final void actionPerformed(AnActionEvent event) {
        final Project project = ActionUtil.getProject(event);

        new BackgroundTask(project, "Extracting select statement", false, true) {
            protected void execute(@NotNull ProgressIndicator progressIndicator) {
                initProgressIndicator(progressIndicator, true);
                StatementGeneratorResult result = generateStatement(project);
                if (result.getMessages().hasErrors()) {
                    MessageUtil.showErrorDialog(result.getMessages(), "Error generating statement");
                } else {
                    pasteStatement(result, project);
                }
            }
        }.start();
    }

    private void pasteStatement(final StatementGeneratorResult result, final Project project) {
        new SimpleLaterInvocator() {
            @Override
            public void run() {
                Editor editor = EditorUtil.getSelectedEditor(project, SQLFileType.INSTANCE);
                if (editor != null)
                    pasteToEditor(editor, result); else
                    pasteToClipboard(result);
            }
        }.start();
    }

    private void pasteToClipboard(StatementGeneratorResult result) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(result.getStatement()), null);
        MessageUtil.showInfoMessage("SQL statement exported to clipboard.", "Statement extracted");
    }

    private void pasteToEditor(final Editor editor, final StatementGeneratorResult generatorResult) {
        new CommandWriteActionRunner(editor.getProject(), "Extract statement") {
            @Override
            public void run() {
                String statement = generatorResult.getStatement();
                PsiUtil.moveCaretOutsideExecutable(editor);
                int offset = EditorModificationUtil.insertStringAtCaret(editor, statement + "\n\n", false, true);
                offset = offset - statement.length() - 2;
                /*editor.getMarkupModel().addRangeHighlighter(offset, offset + statement.length(),
                        HighlighterLayer.SELECTION,
                        EditorColorsManager.getInstance().getGlobalScheme().getAttributes(EditorColors.SEARCH_RESULT_ATTRIBUTES),
                        HighlighterTargetArea.EXACT_RANGE);*/
                editor.getSelectionModel().setSelection(offset, offset + statement.length());
                editor.getCaretModel().moveToOffset(offset);
            }
        }.start();
    }

    protected abstract StatementGeneratorResult generateStatement(Project project);
}
