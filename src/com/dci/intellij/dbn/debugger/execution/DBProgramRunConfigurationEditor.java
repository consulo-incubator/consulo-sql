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

package com.dci.intellij.dbn.debugger.execution;

import com.dci.intellij.dbn.debugger.execution.ui.DBProgramRunConfigurationEditorForm;
import com.dci.intellij.dbn.execution.method.MethodExecutionInput;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import org.jetbrains.annotations.NotNull;

import javax.swing.JComponent;

public class DBProgramRunConfigurationEditor extends SettingsEditor<DBProgramRunConfiguration> {
    private DBProgramRunConfigurationEditorForm configurationEditorComponent;
    private DBProgramRunConfiguration configuration;

    public DBProgramRunConfigurationEditor(DBProgramRunConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void resetEditorFrom(DBProgramRunConfiguration configuration) {
        configurationEditorComponent.readConfiguration(configuration);
    }

    @Override
    protected void applyEditorTo(DBProgramRunConfiguration configuration) throws ConfigurationException {
        configurationEditorComponent.writeConfiguration(configuration);
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        configurationEditorComponent = new DBProgramRunConfigurationEditorForm(configuration);
        return configurationEditorComponent.getComponent();
    }

    @Override
    protected void disposeEditor() {
        configurationEditorComponent.dispose();
        configuration = null;
    }

    public void setExecutionInput(MethodExecutionInput executionInput) {
        if (configurationEditorComponent != null) {
            configurationEditorComponent.setExecutionInput(executionInput, true);
        }
    }
}
