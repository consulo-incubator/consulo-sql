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

package com.dci.intellij.dbn.editor.data.action;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.editor.data.DatasetEditor;
import com.dci.intellij.dbn.editor.data.filter.DatasetFilterManager;
import com.dci.intellij.dbn.editor.data.filter.DatasetFilterType;
import com.dci.intellij.dbn.object.DBDataset;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;

public class OpenFilterSettingsAction extends DumbAwareAction {
    private DatasetEditor datasetEditor;
    public OpenFilterSettingsAction(DatasetEditor datasetEditor) {
        super("Manage Filters...", null, Icons.ACTION_EDIT);
        this.datasetEditor = datasetEditor;
    }

    public void actionPerformed(AnActionEvent e) {
        if (datasetEditor != null) {
            DBDataset dataset = datasetEditor.getDataset();
            DatasetFilterManager.getInstance(dataset.getProject()).openFiltersDialog(dataset, false, false, DatasetFilterType.NONE);
        }
    }

    public void update(AnActionEvent e) {
        boolean enabled =
                datasetEditor != null &&
                datasetEditor.getEditorTable() != null &&
                !datasetEditor.isInserting() ;
        e.getPresentation().setEnabled(enabled);

    }
}
