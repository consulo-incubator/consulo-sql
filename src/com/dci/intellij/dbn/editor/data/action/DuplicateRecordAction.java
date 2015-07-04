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
import com.dci.intellij.dbn.editor.data.ui.table.DatasetEditorTable;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;

public class DuplicateRecordAction extends AbstractDataEditorAction {

    public DuplicateRecordAction() {
        super("Duplicate record", Icons.DATA_EDITOR_DUPLICATE_RECORD);
    }

    public void actionPerformed(AnActionEvent e) {
        DatasetEditor datasetEditor = getDatasetEditor(e);
        if (datasetEditor != null) {
            datasetEditor.duplicateRecord();
        }
    }

    public void update(AnActionEvent e) {
        Presentation presentation = e.getPresentation();
        presentation.setText("Duplicate record");
        DatasetEditor datasetEditor = getDatasetEditor(e);

        if (datasetEditor == null ||!datasetEditor.getActiveConnection().isConnected()) {
            presentation.setEnabled(false);
        } else {
            presentation.setEnabled(true);
            presentation.setVisible(!datasetEditor.isReadonlyData());
            if (datasetEditor.isInserting() || datasetEditor.isLoading() || datasetEditor.isReadonly()) {
                presentation.setEnabled(false);
            } else {
                DatasetEditorTable editorTable = datasetEditor.getEditorTable();
                int[] selectedrows =
                        editorTable == null ? null :
                        editorTable.getSelectedRows();
                presentation.setEnabled(selectedrows != null && selectedrows.length == 1 && selectedrows[0] < editorTable.getModel().getSize());
            }
        }
    }
}