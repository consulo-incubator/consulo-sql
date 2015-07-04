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
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;

public class DataFindAction extends AbstractDataEditorAction {
    public DataFindAction() {
        super("Find...", Icons.ACTION_FIND);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        DatasetEditor datasetEditor = getDatasetEditor(e);
        if (datasetEditor != null) {
            datasetEditor.showSearchHeader();
/*
            FindModel findModel = findManager.getFindInFileModel();

            findManager.showFindDialog(findModel, new Runnable() {
                @Override
                public void run() {
                    datasetEditor.getEditorForm().showSearchPanel();
                }
            });
*/

        }
    }

    public void update(AnActionEvent e) {
        DatasetEditor datasetEditor = getDatasetEditor(e);

        Presentation presentation = e.getPresentation();
        presentation.setText("Find data...");

        if (datasetEditor == null) {
            presentation.setEnabled(false);
        } else {
            presentation.setEnabled(true);
            if (datasetEditor.isInserting() || datasetEditor.isLoading()) {
                presentation.setEnabled(false);
            }
        }

    }
}
