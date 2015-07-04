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

package com.dci.intellij.dbn.execution.method.result.ui;

import com.dci.intellij.dbn.common.ui.DBNForm;
import com.dci.intellij.dbn.common.ui.DBNFormImpl;
import com.dci.intellij.dbn.common.util.ActionUtil;
import com.dci.intellij.dbn.data.model.resultSet.ResultSetDataModel;
import com.dci.intellij.dbn.data.record.RecordViewInfo;
import com.dci.intellij.dbn.data.ui.table.resultSet.ResultSetTable;
import com.dci.intellij.dbn.execution.method.result.MethodExecutionResult;
import com.dci.intellij.dbn.execution.method.result.action.CursorResultExportAction;
import com.dci.intellij.dbn.execution.method.result.action.CursorResultFetchNextRecordsAction;
import com.dci.intellij.dbn.execution.method.result.action.CursorResultViewRecordAction;
import com.dci.intellij.dbn.object.DBArgument;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.util.ui.UIUtil;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Dimension;

public class MethodExecutionCursorResultForm extends DBNFormImpl implements DBNForm {
    private JPanel actionsPanel;
    private JScrollPane resultScrollPane;
    private JPanel mainPanel;
    private JPanel resultPanel;

    public MethodExecutionCursorResultForm(MethodExecutionResult executionResult, DBArgument argument) {
        ResultSetDataModel dataModel = executionResult.getTableModel(argument);
        RecordViewInfo recordViewInfo = new RecordViewInfo(
                executionResult.getResultName(),
                executionResult.getResultIcon());

        ResultSetTable resultTable = new ResultSetTable(dataModel, true, recordViewInfo);
        resultTable.setPreferredScrollableViewportSize(new Dimension(500, -1));

        resultPanel.setBorder(IdeBorderFactory.createBorder());
        resultScrollPane.setViewportView(resultTable);
        resultScrollPane.setRowHeaderView(resultTable.getTableGutter());
        resultScrollPane.getViewport().setBackground(resultTable.getBackground());

        JPanel panel = new JPanel();
        panel.setBorder(UIUtil.getTableHeaderCellBorder());
        resultScrollPane.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, panel);

        ActionToolbar actionToolbar = ActionUtil.createActionToolbar("", true,
                new CursorResultFetchNextRecordsAction(executionResult, resultTable),
                new CursorResultViewRecordAction(resultTable),
                ActionUtil.SEPARATOR,
                new CursorResultExportAction(resultTable, argument));

        actionsPanel.add(actionToolbar.getComponent());
    }

    public JPanel getComponent() {
        return mainPanel;
    }

    public void dispose() {
        super.dispose();
    }
}
