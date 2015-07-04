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

import com.dci.intellij.dbn.common.thread.SimpleLaterInvocator;
import com.dci.intellij.dbn.common.util.ActionUtil;
import com.dci.intellij.dbn.common.util.MessageUtil;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.connection.mapping.FileConnectionMappingManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;

import javax.swing.Icon;

public abstract class TransactionEditorAction extends DumbAwareAction {
    protected TransactionEditorAction(String text, String description, Icon icon) {
        super(text, description, icon);
    }

    public void update(AnActionEvent e) {
        Project project = ActionUtil.getProject(e);
        boolean enabled = false;
        if (project != null) {
            FileConnectionMappingManager connectionMappingManager = FileConnectionMappingManager.getInstance(project);
            ConnectionHandler activeConnection = connectionMappingManager.lookupActiveConnectionForEditor(e.getPlace());
            enabled = activeConnection != null && activeConnection.hasUncommittedChanges();
        }
        e.getPresentation().setEnabled(enabled);
    }

    protected void showErrorDialog(final String message) {
        new SimpleLaterInvocator() {
            public void run() {
                MessageUtil.showErrorDialog(message);
            }
        }.start();
    }

    protected ConnectionHandler getConnectionHandler(Project project, String actionPlace) {
        if (project != null) {
            FileConnectionMappingManager connectionMappingManager = FileConnectionMappingManager.getInstance(project);
            return connectionMappingManager.lookupActiveConnectionForEditor(actionPlace);
        }
        return null;
    }
}
