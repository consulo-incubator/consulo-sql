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

package com.dci.intellij.dbn.data.record.ui;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.common.ui.dialog.DBNDialog;
import com.dci.intellij.dbn.data.record.DatasetRecord;
import com.dci.intellij.dbn.editor.data.DatasetEditorManager;
import org.jetbrains.annotations.Nullable;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import java.awt.event.ActionEvent;

public class RecordViewerDialog extends DBNDialog {
    private RecordViewerForm editorForm;
    private DatasetRecord record;

    public RecordViewerDialog(DatasetRecord record) {
        super(record.getDataset().getProject(), "View Record", true);
        this.record = record; 
        setModal(false);
        setResizable(true);
        editorForm = new RecordViewerForm(record);
        getCancelAction().putValue(Action.NAME, "Close");
        init();
    }


    protected String getDimensionServiceKey() {
        return "DBNavigator.DataRecordViewer";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return editorForm.getPreferredFocusComponent();
    }

    protected final Action[] createActions() {
        return new Action[]{
                new OpenInEditorAction(),
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
        return editorForm.getComponent();
    }

    private class OpenInEditorAction extends AbstractAction {
        public OpenInEditorAction() {
            super("Open In Editor", Icons.OBEJCT_EDIT_DATA);
        }

        public void actionPerformed(ActionEvent e) {
            DatasetEditorManager datasetEditorManager = DatasetEditorManager.getInstance(record.getDataset().getProject());
            datasetEditorManager.openDataEditor(record.getFilterInput());
            doCancelAction();
        }
    }

    @Override
    protected void dispose() {
        super.dispose();
        editorForm.dispose();
        editorForm = null;
    }
}
