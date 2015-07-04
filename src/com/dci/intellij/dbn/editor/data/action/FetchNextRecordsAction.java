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
import com.dci.intellij.dbn.common.util.ActionUtil;
import com.dci.intellij.dbn.editor.data.DatasetEditor;
import com.dci.intellij.dbn.editor.data.options.DataEditorSettings;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.Project;

public class FetchNextRecordsAction extends AbstractDataEditorAction {

    public FetchNextRecordsAction() {
        super("Fetch next records", Icons.DATA_EDITOR_FETCH_NEXT_RECORDS);
    }

    public void actionPerformed(AnActionEvent e) {
        DatasetEditor datasetEditor = getDatasetEditor(e);
        if (datasetEditor != null) {
            DataEditorSettings settings = DataEditorSettings.getInstance(datasetEditor.getProject());
            datasetEditor.fetchNextRecords(settings.getGeneralSettings().getFetchBlockSize().value());
        }
    }

    public void update(AnActionEvent e) {
        Presentation presentation = e.getPresentation();
        presentation.setText("Fetch next records");

        DatasetEditor datasetEditor = getDatasetEditor(e);
        Project project = ActionUtil.getProject(e);
        if (project != null) {
            DataEditorSettings settings = DataEditorSettings.getInstance(project);
            presentation.setText("Fetch next " + settings.getGeneralSettings().getFetchBlockSize().value() + " records");
        }
        boolean enabled =
                datasetEditor != null &&
                datasetEditor.getEditorTable() != null &&
                datasetEditor.getActiveConnection().isConnected() &&
                !datasetEditor.isInserting() &&
                !datasetEditor.isLoading() &&        
                !datasetEditor.getEditorTable().getModel().isResultSetExhausted();
        presentation.setEnabled(enabled);

    }
}