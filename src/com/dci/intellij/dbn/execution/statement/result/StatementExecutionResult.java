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

package com.dci.intellij.dbn.execution.statement.result;

import com.dci.intellij.dbn.common.message.MessageType;
import com.dci.intellij.dbn.execution.ExecutionResult;
import com.dci.intellij.dbn.execution.statement.StatementExecutionInput;
import com.dci.intellij.dbn.execution.statement.StatementExecutionMessage;
import com.dci.intellij.dbn.execution.statement.processor.StatementExecutionProcessor;
import com.dci.intellij.dbn.execution.statement.result.ui.StatementViewerPopup;
import com.intellij.openapi.Disposable;

public interface StatementExecutionResult extends ExecutionResult, Disposable {
    int STATUS_SUCCESS = 0;
    int STATUS_ERROR = 1;

    boolean isOrphan();
    StatementExecutionProcessor getExecutionProcessor();
    StatementExecutionMessage getExecutionMessage();
    StatementExecutionInput getExecutionInput();

    int getExecutionStatus();

    void setExecutionStatus(int executionStatus);
    void updateExecutionMessage(MessageType messageType, String message, String causeMessage);
    void updateExecutionMessage(MessageType messageType, String message);
    void clearExecutionMessage();

    void navigateToEditor(boolean requestFocus);

    StatementViewerPopup getStatementViewerPopup();
    void setStatementViewerPopup(StatementViewerPopup statementViewerPopup);
}
