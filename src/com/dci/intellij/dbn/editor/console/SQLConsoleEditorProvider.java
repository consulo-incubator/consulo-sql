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

package com.dci.intellij.dbn.editor.console;

import com.dci.intellij.dbn.common.editor.BasicTextEditorProvider;
import com.dci.intellij.dbn.common.util.DocumentUtil;
import com.dci.intellij.dbn.editor.console.ui.SQLConsoleEditorToolbarForm;
import com.dci.intellij.dbn.vfs.SQLConsoleFile;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jdom.Element;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.awt.BorderLayout;


public class SQLConsoleEditorProvider extends BasicTextEditorProvider {

    public boolean accept(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        return virtualFile instanceof SQLConsoleFile;
    }

    @NotNull
    public FileEditorState readState(@NotNull Element sourceElement, @NotNull Project project, @NotNull VirtualFile virtualFile) {
        SQLConsoleEditorState editorState = new SQLConsoleEditorState();
        Document document = DocumentUtil.getDocument(virtualFile);
        editorState.readState(sourceElement, project, document);
        return editorState;
    }

    public void writeState(@NotNull FileEditorState state, @NotNull Project project, @NotNull Element targetElement) {
        if (state instanceof SQLConsoleEditorState) {
            SQLConsoleEditorState editorState = (SQLConsoleEditorState) state;
            editorState.writeState(targetElement, project);
        }
    }

    @NotNull
    public FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile file) {
        SQLConsoleEditor editor = new SQLConsoleEditor(project, (SQLConsoleFile) file, "SQL Console");
        SQLConsoleEditorToolbarForm toolbarForm = new SQLConsoleEditorToolbarForm(editor);
        editor.getComponent().add(toolbarForm.getComponent(), BorderLayout.NORTH);
        return editor;
    }

    public void disposeEditor(@NotNull FileEditor editor) {
        editor.dispose();
    }

    @NotNull
    public FileEditorPolicy getPolicy() {
        return FileEditorPolicy.HIDE_DEFAULT_EDITOR;
    }

    @NotNull
    @NonNls
    public String getEditorTypeId() {
        return "SQLConsole";
    }

    /*********************************************************
     *                ApplicationComponent                   *
     *********************************************************/

    @NonNls
    @NotNull
    public String getComponentName() {
        return "DBNavigator.SQLConsoleEditorProvider";
    }

}
