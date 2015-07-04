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

package com.dci.intellij.dbn.debugger;

import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.intellij.xdebugger.XDebugProcess;
import com.intellij.xdebugger.XDebugProcessStarter;
import com.intellij.xdebugger.XDebugSession;
import org.jetbrains.annotations.NotNull;

public class DBProgramDebugProcessStarter extends XDebugProcessStarter {
    private ConnectionHandler connectionHandler;

    public DBProgramDebugProcessStarter(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    @NotNull
    @Override
    public XDebugProcess start(@NotNull XDebugSession session) {
        return new DBProgramDebugProcess(session, connectionHandler);
    }

}
