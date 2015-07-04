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

package com.dci.intellij.dbn.database;

import com.dci.intellij.dbn.database.common.debug.*;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseDebuggerInterface {

    DebuggerSessionInfo initializeSession(Connection connection) throws SQLException;

    void enableDebugging(Connection connection) throws SQLException;

    void disableDebugging(Connection connection) throws SQLException;

    void attachSession(String sessionId, Connection connection) throws SQLException;

    void detachSession(Connection connection) throws SQLException;

    DebuggerRuntimeInfo synchronizeSession(Connection connection) throws SQLException;

    BreakpointInfo addBreakpoint(String programOwner, String programName, String programType, int line, Connection connection) throws SQLException;

    BreakpointOperationInfo removeBreakpoint(int breakpointId, Connection connection) throws SQLException;

    BreakpointOperationInfo enableBreakpoint(int breakpointId, Connection connection) throws SQLException;

    BreakpointOperationInfo disableBreakpoint(int breakpointId, Connection connection) throws SQLException;

    DebuggerRuntimeInfo stepOver(Connection connection) throws SQLException;

    DebuggerRuntimeInfo stepInto(Connection connection) throws SQLException;

    DebuggerRuntimeInfo stepOut(Connection connection) throws SQLException;

    DebuggerRuntimeInfo runToPosition(String programOwner, String programName, String programType, int line, Connection connection) throws SQLException;

    DebuggerRuntimeInfo stopExecution(Connection connection) throws SQLException;

    DebuggerRuntimeInfo resumeExecution(Connection connection) throws SQLException;

    DebuggerRuntimeInfo getRuntimeInfo(Connection connection) throws SQLException;

    ExecutionStatusInfo getExecutionStatusInfo(Connection connection) throws SQLException;

    VariableInfo getVariableInfo(String variableName, Integer frameNumber, Connection connection) throws SQLException;

    BasicOperationInfo setVariableValue(String variableName, Integer frameNumber, String value, Connection connection) throws SQLException;

    ExecutionBacktraceInfo getExecutionBacktraceInfo(Connection connection) throws SQLException;

    String[] getRequiredPrivilegeNames();

}
