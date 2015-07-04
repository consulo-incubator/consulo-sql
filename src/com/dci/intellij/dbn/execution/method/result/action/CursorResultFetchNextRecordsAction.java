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
import com.dci.intellij.dbn.common.util.MessageUtil;
import com.dci.intellij.dbn.data.model.resultSet.ResultSetDataModel;
import com.dci.intellij.dbn.data.ui.table.resultSet.ResultSetTable;
import com.dci.intellij.dbn.execution.common.options.ExecutionEngineSettings;
import com.dci.intellij.dbn.execution.method.result.MethodExecutionResult;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;

import java.sql.SQLException;

public class CursorResultFetchNextRecordsAction extends DumbAwareAction {
    private ResultSetTable table;
    private MethodExecutionResult executionResult;
    public CursorResultFetchNextRecordsAction(MethodExecutionResult executionResult, ResultSetTable table) {
        super("Fetch next records", null, Icons.EXEC_RESULT_RESUME);
        this.table = table;
        this.executionResult = executionResult;
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        try {
            ResultSetDataModel model = table.getModel();
            if (!model.isResultSetExhausted()) {
                ExecutionEngineSettings settings = ExecutionEngineSettings.getInstance(executionResult.getProject());
                int fetchBlockSize = settings.getStatementExecutionSettings().getResultSetFetchBlockSize();

                model.fetchNextRecords(fetchBlockSize, false);
            }

        } catch (SQLException e) {
            MessageUtil.showErrorDialog("Could not perform operation.", e);
        }

    }

    @Override
    public void update(AnActionEvent e) {
        ResultSetDataModel model = (ResultSetDataModel) table.getModel();
        boolean enabled = !model.isResultSetExhausted();
        e.getPresentation().setEnabled(enabled);
    }
}
