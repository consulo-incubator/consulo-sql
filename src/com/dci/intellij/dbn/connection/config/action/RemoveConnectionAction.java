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

package com.dci.intellij.dbn.connection.config.action;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.connection.ConnectionBundle;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.ui.ListUtil;

import javax.swing.JList;

public class RemoveConnectionAction extends DumbAwareAction {
    private ConnectionBundle connectionBundle;
    private JList list;

    public RemoveConnectionAction(JList list, ConnectionBundle connectionBundle) {
        super("Remove connection", null, Icons.ACTION_REMOVE);
        this.list = list;
        this.connectionBundle = connectionBundle;
    }

    public void actionPerformed(AnActionEvent e) {
        if (confirmDelete(list.getSelectedValues())) {
            connectionBundle.setModified(true);
            ListUtil.removeSelectedItems(list);
/*
            int index = list.getMinSelectionIndex();
            connectionBundle.deleteConnections(list.getSelectedValues());
            int size = list.getModel().getSize();
            if (size > 0) list.setSelectedIndex(Math.min(size - 1, index));
*/
        }
    }

    public boolean confirmDelete(Object[] connections) {
        /*SettingsBundle settings = manager.getSettingsBundle();
        boolean confirm = settings.getBoolean(SettingsBundle.KEY_CONFIRM_ACTION_DELETE, true);
        if (confirm) {
            String title = connections.length == 1 ? "Delete configuration" : "Delete configurations";
            String message = connections.length == 1 ?
                    "Are you sure you want to delete the cofiguration " + connections[0] + "?" :
                    "Are you sure you want to delete the selected configurations?";

            VcsShowConfirmationOption confirmationOption = new VcsShowConfirmationOptionImpl(null, null, null, null, null);
            boolean delete = ConfirmationDialog.requestForConfirmation(confirmationOption, manager.getProject(), message, title, CONNECTION_SETUP_QUESTION_DIALOG);
            if (VcsShowConfirmationOption.Value.DO_ACTION_SILENTLY == confirmationOption.getValue()) {
                settings.setBoolean(SettingsBundle.KEY_CONFIRM_ACTION_DELETE, false);
            }
            return delete;
        } */
        return true;
    }

    public void update(AnActionEvent e) {
        int length = list.getSelectedValues().length;
        Presentation presentation = e.getPresentation();
        presentation.setEnabled(length > 0);
        presentation.setText(length > 1 ? "Remove connections" : "Remove connection");
    }
}
