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

package com.dci.intellij.dbn.execution.statement.options;

import com.dci.intellij.dbn.common.options.Configuration;
import com.dci.intellij.dbn.common.options.setting.SettingsUtil;
import com.dci.intellij.dbn.common.options.ui.ConfigurationEditorForm;
import com.dci.intellij.dbn.execution.statement.options.ui.StatementExecutionSettingsForm;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;

public class StatementExecutionSettings extends Configuration{
    private int resultSetFetchBlockSize = 100;
    private int executionTimeout = 20;
    private boolean focusResult = false;

    public String getDisplayName() {
        return "Data editor general settings";
    }

    public String getHelpTopic() {
        return "executionEngine";
    }

    /*********************************************************
    *                       Settings                        *
    *********************************************************/

    public int getResultSetFetchBlockSize() {
        return resultSetFetchBlockSize;
    }

    public void setResultSetFetchBlockSize(int resultSetFetchBlockSize) {
        this.resultSetFetchBlockSize = resultSetFetchBlockSize;
    }

    public int getExecutionTimeout() {
        return executionTimeout;
    }

    public void setExecutionTimeout(int executionTimeout) {
        this.executionTimeout = executionTimeout;
    }

    public void setFocusResult(boolean focusResult) {
        this.focusResult = focusResult;
    }

    public boolean isFocusResult() {
        return focusResult;
    }

    /****************************************************
     *                   Configuration                  *
     ****************************************************/
    public ConfigurationEditorForm createConfigurationEditor() {
        return new StatementExecutionSettingsForm(this);
    }

    @Override
    public String getConfigElementName() {
        return "statement-execution";
    }

    public void readConfiguration(Element element) throws InvalidDataException {
        resultSetFetchBlockSize = SettingsUtil.getInteger(element, "fetch-block-size", resultSetFetchBlockSize);
        executionTimeout = SettingsUtil.getInteger(element, "execution-timeout", executionTimeout);
        focusResult = SettingsUtil.getBoolean(element, "focus-result", focusResult);

    }

    public void writeConfiguration(Element element) throws WriteExternalException {
        SettingsUtil.setInteger(element, "fetch-block-size", resultSetFetchBlockSize);
        SettingsUtil.setInteger(element, "execution-timeout", executionTimeout);
        SettingsUtil.setBoolean(element, "focus-result", focusResult);
    }
}
