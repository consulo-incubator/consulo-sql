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

import com.dci.intellij.dbn.common.Constants;
import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.editor.data.DatasetEditor;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.ui.Messages;

public class ImportDataAction extends AbstractDataEditorAction {

    public ImportDataAction() {
        super("Import Data", Icons.DATA_IMPORT);
    }

    public void actionPerformed(AnActionEvent e) {
        Messages.showInfoMessage("Data import is not implemented yet.", Constants.DBN_TITLE_PREFIX + "Not implemented");
    }

    public void update(AnActionEvent e) {
        Presentation presentation = e.getPresentation();
        presentation.setText("Import Data");
        DatasetEditor datasetEditor = getDatasetEditor(e);
        if (datasetEditor == null) {
            presentation.setEnabled(false);
        } else {
            presentation.setVisible(!datasetEditor.isReadonlyData());
            boolean enabled =
                    datasetEditor.getEditorTable() != null &&
                    datasetEditor.getActiveConnection().isConnected() &&
                    !datasetEditor.isReadonly() &&
                    !datasetEditor.isInserting();
            presentation.setEnabled(enabled);
        }
    }
}