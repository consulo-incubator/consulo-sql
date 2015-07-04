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

package com.dci.intellij.dbn.editor.data.sorting.action;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.data.sorting.SortDirection;
import com.dci.intellij.dbn.data.sorting.SortingInstruction;
import com.dci.intellij.dbn.editor.data.sorting.ui.DatasetSortingColumnForm;
import com.dci.intellij.dbn.object.DBColumn;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;

import javax.swing.Icon;

public class ChangeSortingDirectionAction extends DumbAwareAction {
    private DatasetSortingColumnForm form;

    public ChangeSortingDirectionAction(DatasetSortingColumnForm form) {
        this.form = form;
    }

    public void update(AnActionEvent e) {
        SortingInstruction<DBColumn> sortingInstruction = form.getSortingInstruction();
        SortDirection direction = sortingInstruction.getDirection();
        Icon icon =
            direction == SortDirection.ASCENDING ? Icons.DATA_SORTING_ASC :
            direction == SortDirection.DESCENDING ? Icons.DATA_SORTING_DESC : null;
        e.getPresentation().setIcon(icon);
        e.getPresentation().setText("Change sorting direction");
    }

    public void actionPerformed(AnActionEvent e) {
        form.getSortingInstruction().switchDirection();
    }

}
