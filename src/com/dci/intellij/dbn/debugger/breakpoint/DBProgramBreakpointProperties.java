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

package com.dci.intellij.dbn.debugger.breakpoint;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.xdebugger.breakpoints.XBreakpointProperties;

public class DBProgramBreakpointProperties extends XBreakpointProperties<DBProgramBreakpointState> {
    private VirtualFile file;
    private int line;
    private DBProgramBreakpointState state = new DBProgramBreakpointState(true);

    public DBProgramBreakpointProperties(VirtualFile file, int line) {
        this.file = file;
        this.line = line;
    }

    public VirtualFile getFile() {
        return file;
    }

    public int getLine() {
        return line;
    }


    public DBProgramBreakpointState getState() {
        return state;
    }

    public void loadState(DBProgramBreakpointState state) {
        this.state = state;
    }
}
