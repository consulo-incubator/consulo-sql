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

package com.dci.intellij.dbn.common.util;

import com.dci.intellij.dbn.common.editor.document.OverrideReadonlyFragmentModificationHandler;
import com.dci.intellij.dbn.common.thread.CommandWriteActionRunner;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.language.common.SqlLikeLanguage;
import com.dci.intellij.dbn.language.common.DBLanguageFile;
import com.dci.intellij.dbn.language.common.DBLanguageSyntaxHighlighter;
import com.dci.intellij.dbn.language.common.psi.PsiUtil;
import com.intellij.codeInsight.daemon.DaemonCodeAnalyzer;
import com.intellij.ide.highlighter.HighlighterFactory;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.RangeMarker;
import com.intellij.openapi.editor.colors.ColorKey;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.highlighter.EditorHighlighter;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;

public class DocumentUtil {


    public static void touchDocument(final Editor editor) {
        final Document document = editor.getDocument();

        // restart highlighting
        PsiFile file = DocumentUtil.getFile(editor);
        if (file instanceof DBLanguageFile) {
            DBLanguageFile dbLanguageFile = (DBLanguageFile) file;
            SqlLikeLanguage sqlLikeLanguage = (SqlLikeLanguage) dbLanguageFile.getLanguage();
			ConnectionHandler connectionHandler = dbLanguageFile.getActiveConnection();
			DBLanguageSyntaxHighlighter syntaxHighlighter = connectionHandler == null ?
					sqlLikeLanguage.getFirstVersion().getSyntaxHighlighter() :
					connectionHandler.getLanguageDialect(sqlLikeLanguage).getSyntaxHighlighter();

			EditorHighlighter editorHighlighter = HighlighterFactory.createHighlighter(syntaxHighlighter, editor.getColorsScheme());
			((EditorEx) editor).setHighlighter(editorHighlighter);
		}

        new CommandWriteActionRunner(editor.getProject()) {
            public void run() {
                // touch the editor to trigger parsing

                int length = document.getTextLength();
                document.insertString(length, " ");
                document.deleteString(length, length + 1);
                refreshEditorAnnotations(editor.getProject());
            }
        }.start();

        //refreshEditorAnnotations(editor.getProject());
    }


    public static void refreshEditorAnnotations(Project project) {
        DaemonCodeAnalyzer.getInstance(project).restart();
    }

    public static Document getDocument(PsiFile file) {
        return PsiDocumentManager.getInstance(file.getProject()).getDocument(file);
    }

    public static PsiFile getFile(Editor editor) {
        Project project = editor.getProject();
        return project == null ? null : PsiUtil.getPsiFile(project, editor.getDocument());
    }

    public static void createGuardedBlock(Document document, String reason, boolean highlight) {
        createGuardedBlock(document, 0, document.getTextLength(), reason);
        if (!highlight) {
            Editor[] editors = EditorFactory.getInstance().getEditors(document);
            for (Editor editor : editors) {
                ColorKey key = ColorKey.find("READONLY_FRAGMENT_BACKGROUND");
                EditorColorsScheme scheme = editor.getColorsScheme();
                scheme.setColor(key, scheme.getDefaultBackground());
            }
        }
    }

    public static void createGuardedBlock(Document document, int startOffset, int endOffset, String reason) {
        RangeMarker rangeMarker = document.createGuardedBlock(startOffset, endOffset);
        rangeMarker.setGreedyToLeft(true);
        rangeMarker.setGreedyToRight(true);
        document.putUserData(OverrideReadonlyFragmentModificationHandler.GUARDED_BLOCK_REASON, reason);
    }

    public static void removeGuardedBlock(Document document) {
        removeGuardedBlock(document, 0, document.getTextLength());
    }

    public static void removeGuardedBlock(Document document, int startOffset, int endOffset) {
        RangeMarker rangeMarker = document.getRangeGuard(startOffset, endOffset);
        document.removeGuardedBlock(rangeMarker);
        document.putUserData(OverrideReadonlyFragmentModificationHandler.GUARDED_BLOCK_REASON, null);
    }

    public static VirtualFile getVirtualFile(Editor editor) {
        return FileDocumentManager.getInstance().getFile(editor.getDocument());
    }

    public static Document getDocument(VirtualFile virtualFile) {
        return FileDocumentManager.getInstance().getDocument(virtualFile);
    }
}
