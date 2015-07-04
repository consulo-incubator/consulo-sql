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

package com.dci.intellij.dbn.execution.statement;

import com.dci.intellij.dbn.common.message.MessageType;
import com.dci.intellij.dbn.execution.common.message.ConsoleMessage;
import com.dci.intellij.dbn.execution.statement.result.StatementExecutionResult;
import com.intellij.openapi.vfs.VirtualFile;

public class StatementExecutionMessage extends ConsoleMessage {
    private String causeMessage;
    private boolean isOrphan;
    private StatementExecutionResult executionResult;

    public StatementExecutionMessage(StatementExecutionResult executionResult, String message, String causeMessage, MessageType messageType) {
        super(messageType, message);
        this.executionResult = executionResult;
        this.causeMessage = causeMessage;
    }

    public StatementExecutionResult getExecutionResult() {
        return executionResult;
    }

    public VirtualFile getVirtualFile() {
        return executionResult.getExecutionProcessor().getFile().getVirtualFile();
    }

    public boolean isOrphan() {
        if (!isOrphan) {
            isOrphan = executionResult.getExecutionInput().isObsolete();
        }
        return isOrphan;
    }

    public String getCauseMessage() {
        return causeMessage;
    }

    public void createStatementViewer() {
        
    }

    public void dispose() {
        executionResult.getExecutionProcessor().reset();
        executionResult = null;
    }

    public void navigateToEditor() {
        executionResult.getExecutionProcessor().navigateToEditor(false);
    }
}
