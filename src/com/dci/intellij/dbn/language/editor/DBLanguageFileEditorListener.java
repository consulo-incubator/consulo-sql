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

package com.dci.intellij.dbn.language.editor;

import com.dci.intellij.dbn.common.util.EditorUtil;
import com.dci.intellij.dbn.language.common.DBLanguageFileType;
import com.dci.intellij.dbn.language.editor.ui.DBLanguageFileEditorToolbarForm;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.awt.BorderLayout;

public class DBLanguageFileEditorListener implements FileEditorManagerListener{
    public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
        if (file.isInLocalFileSystem() && file.getFileType() instanceof DBLanguageFileType) {
            FileEditor fileEditor = source.getSelectedEditor(file);
            if (fileEditor != null) {
                String actionPlace = EditorUtil.getEditorActionPlace(fileEditor);
                DBLanguageFileEditorToolbarForm toolbarForm = new DBLanguageFileEditorToolbarForm(source.getProject(), file, actionPlace);
                fileEditor.getComponent().add(toolbarForm.getComponent(), BorderLayout.NORTH);
                fileEditor.putUserData(DBLanguageFileEditorToolbarForm.USER_DATA_KEY, toolbarForm);
            }
        }
    }

    public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
        if (file.isInLocalFileSystem() && file.getFileType() instanceof DBLanguageFileType) {
            FileEditor editor = source.getSelectedEditor(file);
            if (editor != null) {
                DBLanguageFileEditorToolbarForm toolbarForm = editor.getUserData(DBLanguageFileEditorToolbarForm.USER_DATA_KEY);
                if (toolbarForm != null) {
                    toolbarForm.dispose();
                }
            }
        }

    }

    public void selectionChanged(@NotNull FileEditorManagerEvent event) {
    }
}
