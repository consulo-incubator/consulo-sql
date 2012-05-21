package com.dci.intellij.dbn.common.util;

import com.dci.intellij.dbn.common.editor.document.OverrideReadonlyFragmentModificationHandler;
import com.dci.intellij.dbn.common.thread.CommandWriteActionRunner;
import com.dci.intellij.dbn.language.common.psi.PsiUtil;
import com.intellij.codeInsight.daemon.DaemonCodeAnalyzer;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.RangeMarker;
import com.intellij.openapi.editor.colors.ColorKey;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;

public class DocumentUtil {


    public static void touchDocument(final Editor editor) {
        new CommandWriteActionRunner(editor.getProject()) {
            public void run() {
                // touch the editor to trigger parsing and highlighting
                Document document = editor.getDocument();
                int length = document.getTextLength();
                document.insertString(length, " ");
                document.deleteString(length, length + 1);
                refreshEditorAnnotations(editor.getProject());
            }
        }.start();

/*        if (connectionHandler != null) {
            DBLanguageSyntaxHighlighter syntaxHighlighter = connectionHandler.getLanguageDialect(SQLLanguage.INSTANCE).getSyntaxHighlighter();
            EditorHighlighter editorHighlighter = HighlighterFactory.createHighlighter(syntaxHighlighter, editor.getColorsScheme());
            ((EditorEx) editor).setHighlighter(editorHighlighter);
        }
        refreshEditorAnnotations(editor.getProject());*/
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
