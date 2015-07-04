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
import com.dci.intellij.dbn.connection.config.ui.ConnectionListModel;
import com.dci.intellij.dbn.data.sorting.SortDirection;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.DumbAwareAction;

import javax.swing.Icon;
import javax.swing.JList;

public class SortConnectionsAction extends DumbAwareAction {
    private SortDirection currentSortDirection = SortDirection.ASCENDING;
    private ConnectionBundle connectionBundle;
    private JList list;

    public SortConnectionsAction(JList list, ConnectionBundle connectionBundle) {
        this.list = list;
        this.connectionBundle = connectionBundle;
    }

    public void actionPerformed(AnActionEvent anActionEvent) {
        currentSortDirection = currentSortDirection == SortDirection.ASCENDING ?
                SortDirection.DESCENDING :
                SortDirection.ASCENDING;

        if (list.getModel().getSize() > 0) {
            Object selectedValue = list.getSelectedValue();
            connectionBundle.setModified(true);
            ConnectionListModel model = (ConnectionListModel) list.getModel();
            model.sort(currentSortDirection);
            list.setSelectedValue(selectedValue, true);
        }
    }

    public void update(AnActionEvent e) {
        Icon icon;
        String text;
        if (currentSortDirection != SortDirection.ASCENDING) {
            icon = Icons.ACTION_SORT_ASC;
            text = "Sort list ascending";
        } else {
            icon = Icons.ACTION_SORT_DESC;
            text = "Sort list descending";
        }
        Presentation presentation = e.getPresentation();
        presentation.setIcon(icon);
        presentation.setText(text);
    }
}
