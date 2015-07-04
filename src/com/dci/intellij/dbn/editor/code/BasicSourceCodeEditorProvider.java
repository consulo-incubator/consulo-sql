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

package com.dci.intellij.dbn.editor.code;

import com.dci.intellij.dbn.common.editor.BasicTextEditor;
import com.dci.intellij.dbn.common.editor.BasicTextEditorProvider;
import com.dci.intellij.dbn.common.util.ActionUtil;
import com.dci.intellij.dbn.common.util.EditorUtil;
import com.dci.intellij.dbn.editor.DBContentType;
import com.dci.intellij.dbn.vfs.DatabaseEditableObjectFile;
import com.dci.intellij.dbn.vfs.SourceCodeFile;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;
import java.awt.BorderLayout;

public abstract class BasicSourceCodeEditorProvider extends BasicTextEditorProvider implements DumbAware {
    @NotNull
    public FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile file) {
        DatabaseEditableObjectFile databaseFile;

        if (file instanceof SourceCodeFile) {
            SourceCodeFile sourceCodeFile = (SourceCodeFile) file;
            databaseFile = sourceCodeFile.getDatabaseFile();
        } else {
            databaseFile = (DatabaseEditableObjectFile) file;
        }

        SourceCodeFile sourceCodeFile = getSourceCodeFile(databaseFile);

        boolean isMainEditor = sourceCodeFile.getContentType() == databaseFile.getMainContentType();

/*
        BasicTextEditor openEditor = lookupExistingEditor(project, databaseFile);
        if (openEditor != null) return openEditor;
*/

        String editorName = getName();
        BasicTextEditor textEditor = isMainEditor ?
                new SourceCodeMainEditor(project, sourceCodeFile, editorName) :
                new SourceCodeEditor(project, sourceCodeFile, editorName);

        updateEditorActions(textEditor);
        Document document = textEditor.getEditor().getDocument();

        int documentTracking = document.hashCode();
        if (document.hashCode() != sourceCodeFile.getDocumentHashCode()) {
            document.addDocumentListener(sourceCodeFile);
            sourceCodeFile.setDocumentHashCode(documentTracking);
        }

        Icon icon = getIcon();
        if (icon != null) {
            updateTabIcon(databaseFile, textEditor, icon);
        }


        return textEditor;
    }

    private BasicTextEditor lookupExistingEditor(Project project, DatabaseEditableObjectFile databaseFile) {
        FileEditor[] fileEditors = FileEditorManager.getInstance(project).getEditors(databaseFile);
        if (fileEditors.length > 0) {
            for (FileEditor fileEditor : fileEditors) {
                if (fileEditor instanceof SourceCodeEditor) {
                    SourceCodeEditor sourceCodeEditor = (SourceCodeEditor) fileEditor;
                    if (sourceCodeEditor.getVirtualFile().getContentType() == getContentType()) {
                        return sourceCodeEditor;
                    }
                }
            }
        }
        return null;
    }

    private SourceCodeFile getSourceCodeFile(DatabaseEditableObjectFile databaseFile) {
        return (SourceCodeFile) databaseFile.getContentFile(getContentType());
    }

    public abstract DBContentType getContentType();

    public abstract String getName();

    public abstract Icon getIcon();

    private void updateEditorActions(BasicTextEditor fileEditor) {
        Editor editor = fileEditor.getEditor();
        String actionPlace = EditorUtil.getEditorActionPlace(fileEditor);
        ActionToolbar actionToolbar = ActionUtil.createActionToolbar(actionPlace, true, "DBNavigator.ActionGroup.SourceEditor");
        //FileEditorManager.getInstance(editor.getProject()).addTopComponent(fileEditor, actionToolbar.getComponent());
        editor.getComponent().getParent().add(actionToolbar.getComponent(), BorderLayout.NORTH);
    }

    public void disposeEditor(@NotNull FileEditor editor) {
        SourceCodeEditor sourceEditor = (SourceCodeEditor) editor;
        sourceEditor.dispose();
    }

}
