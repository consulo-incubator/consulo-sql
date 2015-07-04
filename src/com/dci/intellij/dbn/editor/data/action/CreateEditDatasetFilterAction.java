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
import com.dci.intellij.dbn.editor.data.filter.DatasetFilter;
import com.dci.intellij.dbn.editor.data.filter.DatasetFilterManager;
import com.dci.intellij.dbn.editor.data.filter.DatasetFilterType;
import com.dci.intellij.dbn.editor.data.options.DataEditorSettings;
import com.dci.intellij.dbn.object.DBDataset;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;

public class CreateEditDatasetFilterAction extends AbstractDataEditorAction {
    public CreateEditDatasetFilterAction() {
        super("Create / Edit Filter", Icons.DATASET_FILTER_NEW);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        DatasetEditor datasetEditor = getDatasetEditor(e);
        if (datasetEditor != null) {
            DBDataset dataset = datasetEditor.getDataset();

            DatasetFilterManager filterManager = DatasetFilterManager.getInstance(dataset.getProject());
            DatasetFilter activeFilter = filterManager.getActiveFilter(dataset);
            if (activeFilter == null || activeFilter.getFilterType() == DatasetFilterType.NONE) {
                DataEditorSettings settings = DataEditorSettings.getInstance(dataset.getProject());
                DatasetFilterType filterType = settings.getFilterSettings().getDefaultFilterType();
                if (filterType == null || filterType == DatasetFilterType.NONE) {
                    filterType = DatasetFilterType.BASIC;
                }


                filterManager.openFiltersDialog(dataset, false, true, filterType);
            }
            else {
                filterManager.openFiltersDialog(dataset, false, false,DatasetFilterType.NONE);
            }
        }
    }

    public void update(AnActionEvent e) {
        Presentation presentation = e.getPresentation();

        DatasetEditor datasetEditor = getDatasetEditor(e);
        if (datasetEditor == null || !datasetEditor.getActiveConnection().isConnected()) {
            presentation.setEnabled(false);
        } else {
            DBDataset dataset = datasetEditor.getDataset();
            boolean enabled =
                !datasetEditor.isInserting() &&
                !datasetEditor.isLoading();

            presentation.setEnabled(enabled);

            DatasetFilterManager filterManager = DatasetFilterManager.getInstance(dataset.getProject());
            DatasetFilter activeFilter = filterManager.getActiveFilter(dataset);
            if (activeFilter == null || activeFilter.getFilterType() == DatasetFilterType.NONE) {
                presentation.setText("Create filter");
                presentation.setIcon(Icons.DATASET_FILTER_NEW);
            } else {
                presentation.setText("Edit filter");
                presentation.setIcon(Icons.DATASET_FILTER_EDIT);
            }
        }
    }
}
