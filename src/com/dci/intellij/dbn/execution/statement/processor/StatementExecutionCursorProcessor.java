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

package com.dci.intellij.dbn.execution.statement.processor;

import com.dci.intellij.dbn.common.message.MessageType;
import com.dci.intellij.dbn.execution.statement.StatementExecutionInput;
import com.dci.intellij.dbn.execution.statement.result.StatementExecutionCursorResult;
import com.dci.intellij.dbn.execution.statement.result.StatementExecutionResult;
import com.dci.intellij.dbn.language.common.DBLanguageFile;
import com.dci.intellij.dbn.language.common.psi.ExecutablePsiElement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatementExecutionCursorProcessor extends StatementExecutionBasicProcessor {

    public StatementExecutionCursorProcessor(ExecutablePsiElement psiElement, int index) {
        super(psiElement, index);
    }

    public StatementExecutionCursorProcessor(DBLanguageFile file, String sqlStatement, int index) {
        super(file, sqlStatement,  index);
    }

    protected StatementExecutionResult createExecutionResult(Statement statement, StatementExecutionInput executionInput) throws SQLException {
        ResultSet resultSet = statement.getResultSet();
        if (resultSet == null) {
            statement.close();

            StatementExecutionResult executionResult = new StatementExecutionCursorResult(getResultName(), executionInput);
            executionResult.updateExecutionMessage(MessageType.INFO, getStatementName() + " executed successfully.");
            return executionResult;
        } else {
            if (executionResult == null) {
                return new StatementExecutionCursorResult(getResultName(), executionInput, resultSet);
            } else {
                // if executionResult exists, just update it with the new resultSet data
                if (executionResult instanceof StatementExecutionCursorResult){
                    StatementExecutionCursorResult executionCursorResult = (StatementExecutionCursorResult) executionResult;
                    executionCursorResult.setExecutionInput(executionInput);
                    executionCursorResult.loadResultSet(resultSet);
                    return executionResult;
                } else {
                    return new StatementExecutionCursorResult(getResultName(), executionInput, resultSet);
                }
            }
        }
    }

    public void reset() {
        super.reset();
    }


    public void setIndex(int index) {
        this.index = index;
    }

    public boolean canExecute() {
        return executionResult == null ||
                executionResult.getExecutionStatus() == StatementExecutionResult.STATUS_ERROR ||
                executionResult.getExecutionInput().isObsolete() || isDirty();
    }

    public void navigateToResult() {
        if (executionResult instanceof StatementExecutionCursorResult) {
            StatementExecutionCursorResult executionCursorResult = (StatementExecutionCursorResult) executionResult;
            executionCursorResult.navigateToResult();
        }

    }
}
