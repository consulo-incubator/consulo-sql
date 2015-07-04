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

import com.dci.intellij.dbn.debugger.DatabaseDebuggerManager;
import com.dci.intellij.dbn.execution.method.MethodExecutionInput;
import com.dci.intellij.dbn.execution.method.MethodExecutionManager;
import com.dci.intellij.dbn.object.DBMethod;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;

public class DBProgramRunConfigurationFactory extends ConfigurationFactory {
    protected DBProgramRunConfigurationFactory(@org.jetbrains.annotations.NotNull ConfigurationType type) {
        super(type);
    }

    @Override
    public Icon getIcon(@NotNull RunConfiguration configuration) {
        DBProgramRunConfiguration runConfiguration = (DBProgramRunConfiguration) configuration;
        DBMethod method = runConfiguration.getMethod();
        return method == null ? super.getIcon() : method.getIcon();
    }

    @Override
    public RunConfiguration createTemplateConfiguration(Project project) {
        return new DBProgramRunConfiguration(project, this, "");
    }

    public DBProgramRunConfiguration createConfiguration(DBMethod method) {
        String name = DatabaseDebuggerManager.createConfigurationName(method);
        DBProgramRunConfiguration runConfiguration = new DBProgramRunConfiguration(method.getProject(), this, name);
        MethodExecutionManager executionManager = MethodExecutionManager.getInstance(method.getProject());
        MethodExecutionInput executionInput = executionManager.getExecutionInput(method);
        runConfiguration.setExecutionInput(executionInput);
        return runConfiguration;
    }

}
