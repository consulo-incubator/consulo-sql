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
import com.dci.intellij.dbn.editor.data.sorting.ui.DatasetSortingColumnForm;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;

public class DeleteSortingCriteriaAction extends DumbAwareAction {
    private DatasetSortingColumnForm form;

    public DeleteSortingCriteriaAction(DatasetSortingColumnForm form) {
        this.form = form;
    }

    public void update(AnActionEvent e) {
        e.getPresentation().setIcon(Icons.ACTION_DELETE);
        e.getPresentation().setText("Remove sorting criteria");
    }

    public void actionPerformed(AnActionEvent e) {
        form.remove();
    }

}
