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

package com.dci.intellij.dbn.execution.common.options;

import com.dci.intellij.dbn.common.options.CompositeProjectConfiguration;
import com.dci.intellij.dbn.common.options.Configuration;
import com.dci.intellij.dbn.execution.common.options.ui.ExecutionEngineSettingsForm;
import com.dci.intellij.dbn.execution.compiler.options.CompilerSettings;
import com.dci.intellij.dbn.execution.statement.options.StatementExecutionSettings;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class ExecutionEngineSettings extends CompositeProjectConfiguration<ExecutionEngineSettingsForm> {
    private StatementExecutionSettings statementExecutionSettings = new StatementExecutionSettings();
    private CompilerSettings compilerSettings = new CompilerSettings();
    public ExecutionEngineSettings(Project project) {
        super(project);
    }

    public static ExecutionEngineSettings getInstance(Project project) {
        return getGlobalProjectSettings(project).getExecutionEngineSettings();
    }

    @NotNull
    @Override
    public String getId() {
        return "DBNavigator.Project.ExecutionEngineSettings";
    }

    public String getDisplayName() {
        return "Execution Engine";
    }

    public String getHelpTopic() {
        return "executionEngine";
        
    }

    /*********************************************************
     *                        Custom                         *
     *********************************************************/
    public StatementExecutionSettings getStatementExecutionSettings() {
        return statementExecutionSettings;
    }

    public CompilerSettings getCompilerSettings() {
        return compilerSettings;
    }

    /*********************************************************
     *                    Configuration                      *
     *********************************************************/
    public ExecutionEngineSettingsForm createConfigurationEditor() {
        return new ExecutionEngineSettingsForm(this);
    }

    @Override
    public String getConfigElementName() {
        return "execution-engine-settings";
    }

    protected Configuration[] createConfigurations() {
        return new Configuration[] {
                statementExecutionSettings,
                compilerSettings};
    }
}
