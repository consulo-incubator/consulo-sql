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

package com.dci.intellij.dbn.data.export.action;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.data.export.ui.ExportDataDialog;
import com.dci.intellij.dbn.data.ui.table.resultSet.ResultSetTable;
import com.dci.intellij.dbn.object.DBDataset;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class ExportDataAction extends AnAction {
    private ResultSetTable table;
    private DBDataset dataset;

    public ExportDataAction(ResultSetTable table, DBDataset dataset) {
        super("Export Data", null, Icons.DATA_EXPORT);
        this.table = table;
        this.dataset = dataset;
    }

    public void actionPerformed(AnActionEvent e) {
        ExportDataDialog dialog = new ExportDataDialog(table, dataset);
        dialog.show();
    }
}
