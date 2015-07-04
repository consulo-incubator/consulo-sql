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

package com.dci.intellij.dbn.execution.method;

import com.dci.intellij.dbn.common.message.MessageType;
import com.dci.intellij.dbn.database.common.execution.MethodExecutionProcessor;
import com.dci.intellij.dbn.editor.DBContentType;
import com.dci.intellij.dbn.execution.common.message.ConsoleMessage;
import com.dci.intellij.dbn.vfs.DatabaseContentFile;
import com.dci.intellij.dbn.vfs.DatabaseEditableObjectFile;

public class MethodExecutionMessage extends ConsoleMessage {
    private MethodExecutionProcessor executionProcessor;
    private DatabaseEditableObjectFile databaseFile;
    private DatabaseContentFile contentFile;
    private DBContentType contentType;

    public MethodExecutionMessage(MethodExecutionProcessor executionProcessor, String message, MessageType messageType) {
        super(messageType, message);
        this.executionProcessor = executionProcessor;
    }

    public MethodExecutionProcessor getExecutionProcessor() {
        return executionProcessor;
    }


    public DatabaseEditableObjectFile getDatabaseFile() {
        if (databaseFile == null) {
            databaseFile = executionProcessor.getMethod().getVirtualFile();
        }
        return databaseFile;
    }

    public DatabaseContentFile getContentFile() {
        if (contentFile == null) {
            DatabaseEditableObjectFile databaseFile = getDatabaseFile();
            contentFile = databaseFile.getContentFile(contentType);
        }
        return contentFile;
    }

    public void dispose() {
        executionProcessor = null;
        databaseFile = null;
        contentFile = null;
    }
}
