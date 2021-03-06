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

package com.dci.intellij.dbn.editor.data.filter.ui;

import com.dci.intellij.dbn.common.ui.dialog.DBNDialog;
import com.dci.intellij.dbn.editor.data.DatasetEditorManager;
import com.dci.intellij.dbn.editor.data.filter.DatasetBasicFilter;
import com.dci.intellij.dbn.editor.data.filter.DatasetFilter;
import com.dci.intellij.dbn.editor.data.filter.DatasetFilterGroup;
import com.dci.intellij.dbn.editor.data.filter.DatasetFilterManager;
import com.dci.intellij.dbn.editor.data.filter.DatasetFilterType;
import com.dci.intellij.dbn.object.DBDataset;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import java.awt.event.ActionEvent;

public class DatasetFilterDialog extends DBNDialog {
    private boolean isAutomaticPrompt;
    private DBDataset dataset;
    private DatasetFilterForm mainForm;
    private DatasetFilterGroup filterGroup;

    public DatasetFilterDialog(DBDataset dataset, boolean isAutomaticPrompt, boolean createNewFilter, DatasetFilterType defaultFilterType) {
        super(dataset.getProject(), "Data Filters", true);
        construct(dataset, isAutomaticPrompt);
        if ((createNewFilter || filterGroup.getFilters().isEmpty()) && defaultFilterType != DatasetFilterType.NONE) {
            DatasetFilter filter =
                    defaultFilterType == DatasetFilterType.BASIC ? filterGroup.createBasicFilter(true) :
                    defaultFilterType == DatasetFilterType.CUSTOM ? filterGroup.createCustomFilter(true) : null;

            mainForm.getFilterList().setSelectedValue(filter, true);
        }
        init();
    }

    protected String getDimensionServiceKey() {
        return "DBNavigator.DatasetFilter";
    }

    public DatasetFilterDialog(DBDataset dataset, DatasetBasicFilter basicFilter) {
        super(dataset.getProject(), "Data filters", true);
        construct(dataset, false);
        mainForm.getFilterList().setSelectedValue(basicFilter, true);
        init();
    }

    private void construct(DBDataset dataset, boolean isAutomaticPrompt) {
        this.dataset = dataset;
        this.isAutomaticPrompt = isAutomaticPrompt;
        setModal(true);
        setResizable(true);
        DatasetFilterManager filterManager = DatasetFilterManager.getInstance(dataset.getProject());
        filterGroup = filterManager.getFilterGroup(dataset);
        mainForm = filterGroup.createConfigurationEditor();
    }

    public DatasetFilterGroup getFilterGroup() {
        return filterGroup;
    }

    @NotNull
    protected final Action[] createActions() {
        if (isAutomaticPrompt) {
            return new Action[]{
                    getOKAction(),
                    new NoFilterAction(),
                    getCancelAction(),
                    getHelpAction()
            };
        } else {
            return new Action[]{
                    getOKAction(),
                    getCancelAction(),
                    getHelpAction()
            };
        }
    }

    private class NoFilterAction extends AbstractAction {
        public NoFilterAction() {
            super("No Filter");
            //putValue(DEFAULT_ACTION, Boolean.FALSE);
        }

        public void actionPerformed(ActionEvent e) {
            doNoFilterAction();
        }
    }

    @Nullable
    protected JComponent createCenterPanel() {
        return mainForm.getComponent();
    }

    public void doOKAction() {
        Project project = dataset.getProject();
        try {
            mainForm.applyChanges();
            DatasetFilterManager filterManager = DatasetFilterManager.getInstance(project);
            DatasetFilter activeFilter = mainForm.getSelectedFilter();
            if (activeFilter == null) {
                activeFilter = DatasetFilterManager.EMPTY_FILTER;
            }
            filterManager.setActiveFilter(dataset, activeFilter);
            mainForm.dispose();
        } catch (ConfigurationException e) {
            e.printStackTrace(); 
        }
        super.doOKAction();
        if (!isAutomaticPrompt) DatasetEditorManager.getInstance(project).reloadEditorData(dataset);
    }

    public void doCancelAction() {
        mainForm.resetChanges();
        super.doCancelAction();
    }

    public void doNoFilterAction() {
        mainForm.resetChanges();
        mainForm.dispose();
        Project project = dataset.getProject();
        DatasetFilterManager filterManager = DatasetFilterManager.getInstance(project);
        DatasetFilter activeFilter = filterManager.getActiveFilter(dataset);
        if (activeFilter == null) {
            activeFilter = DatasetFilterManager.EMPTY_FILTER;
            filterManager.setActiveFilter(dataset, activeFilter);
        }
        close(OK_EXIT_CODE);
    }

    @Override
    protected void dispose() {
        super.dispose();
        mainForm.dispose();
    }
}
