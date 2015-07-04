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
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.ui.ListUtil;

import javax.swing.JList;

public class MoveConnectionUpAction extends DumbAwareAction {
    private JList list;
    private ConnectionBundle connectionBundle;

    public MoveConnectionUpAction(JList list, ConnectionBundle connectionBundle) {
        super("Move selection up", null, Icons.ACTION_MOVE_UP);
        this.list = list;
        this.connectionBundle = connectionBundle;
    }

    public void actionPerformed(AnActionEvent e) {
        connectionBundle.setModified(true);
        ListUtil.moveSelectedItemsUp(list);
    }

    public void update(AnActionEvent e) {
        int length = list.getSelectedValues().length;
        boolean enabled = length > 0 && list.getMinSelectionIndex() > 0;
        e.getPresentation().setEnabled(enabled);
    }
}
