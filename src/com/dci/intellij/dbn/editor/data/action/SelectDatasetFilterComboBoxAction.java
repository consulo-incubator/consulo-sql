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
import com.dci.intellij.dbn.common.ui.DBNComboBoxAction;
import com.dci.intellij.dbn.common.util.NamingUtil;
import com.dci.intellij.dbn.editor.data.DatasetEditor;
import com.dci.intellij.dbn.editor.data.filter.DatasetFilter;
import com.dci.intellij.dbn.editor.data.filter.DatasetFilterGroup;
import com.dci.intellij.dbn.editor.data.filter.DatasetFilterManager;
import com.dci.intellij.dbn.object.DBDataset;
import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.JComponent;

public class SelectDatasetFilterComboBoxAction extends DBNComboBoxAction {
    public SelectDatasetFilterComboBoxAction() {
        Presentation presentation = getTemplatePresentation();
        presentation.setText("No Filter");
        presentation.setIcon(Icons.DATASET_FILTER_EMPTY);
    }

    @Override
    public JComponent createCustomComponent(Presentation presentation) {
        return super.createCustomComponent(presentation);
    }



    @NotNull
    protected DefaultActionGroup createPopupActionGroup(JComponent button) {
        DataContext dataContext = DataManager.getInstance().getDataContext(button);
        Project project = (Project) dataContext.getData(PlatformDataKeys.PROJECT.getName());

        DefaultActionGroup actionGroup = new DefaultActionGroup();
        DatasetEditor datasetEditor = AbstractDataEditorAction.getActiveDatasetEditor(project);
        if (datasetEditor != null) {
            DBDataset dataset = datasetEditor.getDataset();
            OpenFilterSettingsAction openFilterSettingsAction = new OpenFilterSettingsAction(datasetEditor);
            openFilterSettingsAction.setInjectedContext(true);
            actionGroup.add(openFilterSettingsAction);
            actionGroup.addSeparator();
            actionGroup.add(new SelectDatasetFilterAction(dataset, DatasetFilterManager.EMPTY_FILTER));
            actionGroup.addSeparator();

            DatasetFilterManager filterManager = DatasetFilterManager.getInstance(dataset.getProject());
            DatasetFilterGroup filterGroup = filterManager.getFilterGroup(dataset);
            for (DatasetFilter filter : filterGroup.getFilters()) {
                actionGroup.add(new SelectDatasetFilterAction(dataset, filter));
            }
        }
        return actionGroup;
    }

    @Override
    public void update(AnActionEvent e) {
        DatasetEditor datasetEditor = AbstractDataEditorAction.getDatasetEditor(e);

        Presentation presentation = e.getPresentation();
        boolean enabled =
                datasetEditor != null &&
                datasetEditor.getActiveConnection().isConnected() &&
                !datasetEditor.isInserting() &&
                !datasetEditor.isLoading();
        if (datasetEditor != null) {
            DBDataset dataset = datasetEditor.getDataset();

            DatasetFilterManager filterManager = DatasetFilterManager.getInstance(dataset.getProject());
            DatasetFilter activeFilter = filterManager.getActiveFilter(dataset);

            if (activeFilter == null) {
                presentation.setText("No Filter");
                presentation.setIcon(Icons.DATASET_FILTER_EMPTY);
            } else {
                //e.getPresentation().setText(activeFilter.getName());
                presentation.setText(NamingUtil.enhanceNameForDisplay(activeFilter.getName()));
                presentation.setIcon(activeFilter.getIcon());
            }
        }

        //if (!enabled) presentation.setIcon(null);
        presentation.setEnabled(enabled);
    }
}
