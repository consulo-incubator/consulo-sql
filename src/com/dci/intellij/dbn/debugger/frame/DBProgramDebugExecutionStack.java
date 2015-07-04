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

import com.dci.intellij.dbn.database.common.debug.DebuggerRuntimeInfo;
import com.dci.intellij.dbn.debugger.DBProgramDebugProcess;
import com.intellij.xdebugger.frame.XExecutionStack;
import com.intellij.xdebugger.frame.XStackFrame;

import java.util.ArrayList;
import java.util.List;

public class DBProgramDebugExecutionStack extends XExecutionStack {
    private DBProgramDebugStackFrame topStackFrame;
    private DBProgramDebugProcess debugProcess;

    protected DBProgramDebugExecutionStack(DBProgramDebugProcess debugProcess) {
        super("method name", null);
        this.debugProcess = debugProcess;
        int frameNumber = debugProcess.getBacktraceInfo().getFrames().size() + 1;
        topStackFrame = new DBProgramDebugStackFrame(debugProcess, debugProcess.getRuntimeInfo(), frameNumber);

    }



    @Override
    public XStackFrame getTopFrame() {
        return topStackFrame;
    }

    @Override
    public void computeStackFrames(int firstFrameIndex, XStackFrameContainer container) {
        List<DBProgramDebugStackFrame> frames = new ArrayList<DBProgramDebugStackFrame>();
        int frameNumber = debugProcess.getBacktraceInfo().getFrames().size() + 1;
        for (DebuggerRuntimeInfo runtimeInfo : debugProcess.getBacktraceInfo().getFrames()) {
            if (!runtimeInfo.equals(debugProcess.getRuntimeInfo())) {
                DBProgramDebugStackFrame frame = new DBProgramDebugStackFrame(debugProcess, runtimeInfo, frameNumber);
                frames.add(frame);
            }
            frameNumber--;
        }
        container.addStackFrames(frames, true) ;
    }
}
