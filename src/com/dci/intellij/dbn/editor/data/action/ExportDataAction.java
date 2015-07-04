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
import com.dci.intellij.dbn.data.export.ui.ExportDataDialog;
import com.dci.intellij.dbn.editor.data.DatasetEditor;
import com.dci.intellij.dbn.object.DBDataset;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;

public class ExportDataAction extends AbstractDataEditorAction {

    public ExportDataAction() {
        super("Export Data", Icons.DATA_EXPORT);
    }

    public void actionPerformed(AnActionEvent e) {
        DatasetEditor datasetEditor = getDatasetEditor(e);

        if (datasetEditor != null) {
            DBDataset dataset = datasetEditor.getDataset();
            ExportDataDialog dialog = new ExportDataDialog(datasetEditor.getEditorTable(), dataset);
            dialog.show();
        }
    }

    public void update(AnActionEvent e) {
        DatasetEditor datasetEditor = getDatasetEditor(e);

        Presentation presentation = e.getPresentation();
        presentation.setText("Export Data");

        boolean enabled =
                datasetEditor != null &&
                datasetEditor.getEditorTable() != null &&
                !datasetEditor.isInserting();
        presentation.setEnabled(enabled);

    }
}
