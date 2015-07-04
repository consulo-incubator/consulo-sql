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

package com.dci.intellij.dbn.code.common.completion.options.sorting.action;

import com.dci.intellij.dbn.code.common.completion.options.sorting.CodeCompletionSortingSettings;
import com.dci.intellij.dbn.common.Icons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.ui.ListUtil;

import javax.swing.JList;

public class MoveUpAction extends AnAction {
    private CodeCompletionSortingSettings settings;
    private JList list;

    public MoveUpAction(JList list, CodeCompletionSortingSettings settings)  {
        super("Move Up", null, Icons.ACTION_MOVE_UP);
        this.list = list;
        this.settings = settings;
    }

    public void update(AnActionEvent e) {
        int[] indices = list.getSelectedIndices();
        boolean enabled =
                list.isEnabled() &&
                indices.length > 0 &&
                indices[0] > 0;
        e.getPresentation().setEnabled(enabled);
    }

    public void actionPerformed(AnActionEvent e) {
        ListUtil.moveSelectedItemsUp(list);
        settings.setModified(true);
    }
}
