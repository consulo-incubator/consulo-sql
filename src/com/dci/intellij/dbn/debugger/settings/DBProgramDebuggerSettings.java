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

package com.dci.intellij.dbn.debugger.settings;

import com.intellij.openapi.options.Configurable;
import com.intellij.xdebugger.settings.XDebuggerSettings;
import org.jetbrains.annotations.NotNull;

public class DBProgramDebuggerSettings extends XDebuggerSettings<DBProgramDebuggerState> {
    DBProgramDebuggerState state = new DBProgramDebuggerState();

    protected DBProgramDebuggerSettings() {
        super("db-program");
    }

    @NotNull
    @Override
    public Configurable createConfigurable() {
        return new DBProgramDebuggerConfigurable();
    }

    public DBProgramDebuggerState getState() {
        return state;
    }

    public void loadState(DBProgramDebuggerState state) {
        this.state = state;
    }

}
