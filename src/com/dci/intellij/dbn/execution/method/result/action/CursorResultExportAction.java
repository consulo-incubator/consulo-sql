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

package com.dci.intellij.dbn.execution.method.result.action;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.data.export.ui.ExportDataDialog;
import com.dci.intellij.dbn.data.ui.table.resultSet.ResultSetTable;
import com.dci.intellij.dbn.object.DBArgument;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;

public class CursorResultExportAction extends DumbAwareAction {
    private ResultSetTable table;
    private DBArgument cursorArgument;
    public CursorResultExportAction(ResultSetTable table, DBArgument cursorArgument) {
        super("Export Data", null, Icons.DATA_EXPORT);
        this.cursorArgument = cursorArgument;
        this.table = table;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        ExportDataDialog dialog = new ExportDataDialog(table, cursorArgument);
        dialog.show();
    }
}
