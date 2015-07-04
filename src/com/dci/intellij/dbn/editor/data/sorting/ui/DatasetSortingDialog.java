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

package com.dci.intellij.dbn.editor.data.sorting.ui;

import com.dci.intellij.dbn.common.ui.dialog.DBNDialog;
import com.dci.intellij.dbn.editor.data.sorting.DatasetSortingState;
import com.dci.intellij.dbn.object.DBDataset;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Action;
import javax.swing.JComponent;

public class DatasetSortingDialog extends DBNDialog {
    private DatasetSortingForm sortingForm;

    public DatasetSortingDialog(DBDataset dataset, DatasetSortingState sortingState) {
        super(dataset.getProject(), "Sorting", true);
        setModal(true);
        setResizable(true);
        sortingForm = new DatasetSortingForm(dataset, sortingState);
        getCancelAction().putValue(Action.NAME, "Close");
        init();
    }

    protected String getDimensionServiceKey() {
        return "DBNavigator.DatasetSorting";
    }

    @NotNull
    protected final Action[] createActions() {
        return new Action[]{
                getOKAction(),
                getCancelAction(),
                getHelpAction()
        };
    }

    @Override
    protected void doOKAction() {
        super.doOKAction();
    }

    @Nullable
    protected JComponent createCenterPanel() {
        return sortingForm.getComponent();
    }

    @Override
    protected void dispose() {
        super.dispose();
        sortingForm.dispose();
    }
}
