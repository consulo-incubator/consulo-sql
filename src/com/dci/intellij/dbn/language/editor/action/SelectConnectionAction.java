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

package com.dci.intellij.dbn.language.editor.action;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.common.util.ActionUtil;
import com.dci.intellij.dbn.common.util.NamingUtil;
import com.dci.intellij.dbn.connection.ConnectionBundle;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.connection.ModuleConnectionBundle;
import com.dci.intellij.dbn.connection.mapping.FileConnectionMappingManager;
import com.dci.intellij.dbn.language.common.DBLanguageFileType;
import com.dci.intellij.dbn.vfs.DatabaseEditableObjectFile;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

public class SelectConnectionAction extends DumbAwareAction {
    private final ConnectionHandler connectionHandler;

    public SelectConnectionAction(ConnectionHandler connectionHandler) {
        super(connectionHandler == null ? "No Connection" : NamingUtil.enhanceUnderscoresForDisplay(connectionHandler.getQualifiedName()), null,
                connectionHandler == null ? Icons.SPACE : connectionHandler.getIcon());
        this.connectionHandler = connectionHandler;
    }

    public void actionPerformed(AnActionEvent e) {
        Project project = ActionUtil.getProject(e);
        FileConnectionMappingManager.getInstance(project).selectActiveConnectionForEditor(e.getPlace(), connectionHandler);
    }

    public void update(AnActionEvent e) {
        Presentation presentation = e.getPresentation();
        boolean enabled = true;
        Project project = ActionUtil.getProject(e);
        VirtualFile[] selectedFiles = FileEditorManager.getInstance(project).getSelectedFiles();
        if (selectedFiles.length == 1) {
            VirtualFile virtualFile = selectedFiles[0];
            if (virtualFile instanceof DatabaseEditableObjectFile) {
                enabled = false;
            } else {
                if (virtualFile.getFileType() instanceof DBLanguageFileType) {
                    if (connectionHandler == null) {
                        enabled = true;
                    } else {
                        ConnectionBundle connectionBundle = connectionHandler.getConnectionBundle();
                        if (connectionBundle instanceof ModuleConnectionBundle) {
                            Module currentModule = ModuleUtil.findModuleForFile(virtualFile, project);
                            Module connectionModule = ((ModuleConnectionBundle) connectionBundle).getModule();
                            enabled = connectionModule == currentModule;
                        }
                    }
                } else {
                    enabled = false;
                }
            }
        }

        presentation.setEnabled(enabled);

    }
}
