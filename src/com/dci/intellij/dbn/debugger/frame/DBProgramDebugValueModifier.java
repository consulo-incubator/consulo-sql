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

package com.dci.intellij.dbn.debugger.frame;

import com.dci.intellij.dbn.database.common.debug.BasicOperationInfo;
import com.dci.intellij.dbn.debugger.DBProgramDebugProcess;
import com.intellij.xdebugger.frame.XValueModifier;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class DBProgramDebugValueModifier extends XValueModifier {
    private DBProgramDebugValue value;

    public DBProgramDebugValueModifier(DBProgramDebugValue value) {
        this.value = value;
    }

    @Override
    public void setValue(@NotNull String expression, @NotNull XModificationCallback callback) {
        DBProgramDebugProcess debugProcess = value.getDebugProcess();
        try {
            BasicOperationInfo operationInfo = debugProcess.getDebuggerInterface().setVariableValue(
                    value.getVariableName(),
                    0,
                    expression,
                    debugProcess.getDebugConnection());

            if (operationInfo.getError() != null) {
                callback.errorOccurred("Could not change value. " + operationInfo.getError());
            } else {
                callback.valueModified();
            }
        } catch (SQLException e) {
            callback.errorOccurred(e.getMessage());
        }
    }
}
