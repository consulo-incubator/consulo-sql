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

import java.sql.SQLException;

import javax.swing.Icon;

import org.jetbrains.annotations.NotNull;
import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.common.util.StringUtil;
import com.dci.intellij.dbn.database.common.debug.VariableInfo;
import com.dci.intellij.dbn.debugger.DBProgramDebugProcess;
import com.intellij.xdebugger.frame.XNamedValue;
import com.intellij.xdebugger.frame.XValueModifier;
import com.intellij.xdebugger.frame.XValueNode;
import com.intellij.xdebugger.frame.XValuePlace;

public class DBProgramDebugValue extends XNamedValue implements Comparable<DBProgramDebugValue>{
    private DBProgramDebugValueModifier modifier;
    private DBProgramDebugProcess debugProcess;
    private String textPresentation;
    private String variableName;
    private String errorMessage;
    private Icon icon;
    private int frameIndex;

    public DBProgramDebugValue(DBProgramDebugProcess debugProcess, String variableName, Icon icon, int frameIndex) {
		super(variableName);
		this.variableName = variableName;
        this.debugProcess = debugProcess;
        this.icon = icon == null ? Icons.DBO_VARIABLE : icon;
        this.frameIndex = frameIndex;
        try {
            VariableInfo variableInfo = debugProcess.getDebuggerInterface().getVariableInfo(
                    variableName.toUpperCase(), frameIndex,
                    debugProcess.getDebugConnection());
            textPresentation = variableInfo.getValue();
            errorMessage = variableInfo.getError();
            
            if (textPresentation == null) {
                textPresentation = "null";
            } else {
                if (!StringUtil.isNumber(textPresentation)) {
                    textPresentation = '"' + textPresentation + '"';
                }
            }

            if (errorMessage != null) {
                errorMessage = errorMessage.toLowerCase();
            }
        } catch (SQLException e) {
            textPresentation = "";
            errorMessage = e.getMessage();
        }
    }

    public DBProgramDebugProcess getDebugProcess() {
        return debugProcess;
    }

    public String getVariableName() {
        return variableName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

	@Override
	public void computePresentation(@NotNull XValueNode xValueNode, @NotNull XValuePlace xValuePlace)
	{
		xValueNode.setPresentation(icon, errorMessage, textPresentation, false);
	}

	@Override
    public XValueModifier getModifier() {
        if (modifier == null) modifier = new DBProgramDebugValueModifier(this);
        return modifier;
    }

    public int compareTo(@NotNull DBProgramDebugValue remote) {
        return variableName.compareTo(remote.variableName);
    }
}
