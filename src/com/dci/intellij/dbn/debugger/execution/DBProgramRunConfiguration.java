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

import gnu.trove.THashSet;

import java.util.HashSet;
import java.util.Set;

import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import com.dci.intellij.dbn.common.options.setting.SettingsUtil;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.database.DatabaseCompatibilityInterface;
import com.dci.intellij.dbn.database.DatabaseFeature;
import com.dci.intellij.dbn.execution.method.MethodExecutionInput;
import com.dci.intellij.dbn.execution.method.MethodExecutionManager;
import com.dci.intellij.dbn.object.DBMethod;
import com.dci.intellij.dbn.object.identifier.DBMethodIdentifier;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.LocatableConfiguration;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunConfigurationBase;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.configurations.RuntimeConfigurationError;
import com.intellij.execution.configurations.RuntimeConfigurationException;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;

public class DBProgramRunConfiguration extends RunConfigurationBase implements LocatableConfiguration {
    private MethodExecutionInput executionInput;
    private Set<MethodExecutionInput> methodSelectionHistory = new THashSet<MethodExecutionInput>();
    private DBProgramRunConfigurationEditor configurationEditor;
    private boolean compileDependencies = true;
    private boolean isGeneratedName = true;

    public DBProgramRunConfiguration(Project project, ConfigurationFactory factory, String name) {
        super(project, factory, name);
    }

    public DBProgramRunConfigurationEditor getConfigurationEditor() {
        if (configurationEditor == null )
            configurationEditor = new DBProgramRunConfigurationEditor(this);
        return configurationEditor;
    }

    public boolean isCompileDependencies() {
        return compileDependencies;
    }

    public void setCompileDependencies(boolean compileDependencies) {
        this.compileDependencies = compileDependencies;
    }

    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment env) throws ExecutionException {
        return new DBProgramRunProfileState();
    }

    public Set<MethodExecutionInput> getMethodSelectionHistory() {
        return methodSelectionHistory;
    }

    public void checkConfiguration() throws RuntimeConfigurationException {
        if (executionInput == null) {
            throw new RuntimeConfigurationError("No method selected.");
        }

        if (getMethod() == null) {
            throw new RuntimeConfigurationError(
                    "Method " + executionInput.getMethodIdentifier().getQualifiedMethodName() + " could not be resolved. " +
                    "The database connection is down or method has been dropped.");
        }

        ConnectionHandler connectionHandler = getMethod().getConnectionHandler();
        DatabaseCompatibilityInterface compatibilityInterface = connectionHandler.getInterfaceProvider().getCompatibilityInterface();
        if (!compatibilityInterface.supportsFeature(DatabaseFeature.DEBUGGING)){
            throw new RuntimeConfigurationError(
                    "Debugging is not supported for " + connectionHandler.getDatabaseType().getDisplayName() +" databases.");
        }
    }

    public DBMethod getMethod() {
        return executionInput == null ? null : executionInput.getMethod();
    }

    public boolean isGeneratedName() {
        return isGeneratedName;
    }

    public String suggestedName() {
        return createSuggestedName();
    }

    public String createSuggestedName() {
        if (executionInput == null) {
            return "<unnamed>";
        } else {
            return executionInput.getMethodIdentifier().getPath();
        }
    }

    public MethodExecutionInput getExecutionInput() {
        return executionInput;
    }

    public void setExecutionInput(MethodExecutionInput executionInput) {
        if (this.executionInput != null && !this.executionInput.equals(executionInput)) {
            methodSelectionHistory.add(this.executionInput);
        }
        this.executionInput = executionInput;
        getConfigurationEditor().setExecutionInput(executionInput);
    }


    @Override
    public void writeExternal(Element element) throws WriteExternalException {
        super.writeExternal(element);
        SettingsUtil.setBoolean(element, "compile-dependencies", compileDependencies);
        if (executionInput != null) {
            Element methodIdentifierElement = new Element("method-identifier");
            executionInput.getMethodIdentifier().writeConfiguration(methodIdentifierElement);
            element.addContent(methodIdentifierElement);

            Element methodIdentifierHistoryElement = new Element("method-identifier-history");
            for (MethodExecutionInput executionInput : methodSelectionHistory) {
                methodIdentifierElement = new Element("method-identifier");
                executionInput.getMethodIdentifier().writeConfiguration(methodIdentifierElement);
                methodIdentifierHistoryElement.addContent(methodIdentifierElement);
            }
            element.addContent(methodIdentifierHistoryElement);
        }
    }

    @Override
    public void readExternal(Element element) throws InvalidDataException {
        super.readExternal(element);
        compileDependencies = SettingsUtil.getBoolean(element, "compile-dependencies", true);
        MethodExecutionManager executionManager = MethodExecutionManager.getInstance(getProject());
        Element methodIdentifierElement = element.getChild("method-identifier");
        if (methodIdentifierElement != null) {
            DBMethodIdentifier methodIdentifier = new DBMethodIdentifier();
            methodIdentifier.readConfiguration(methodIdentifierElement);

            executionInput = executionManager.getExecutionInput(methodIdentifier);
        }

        Element methodIdentifierHistoryElement = element.getChild("method-identifier-history");
        if (methodIdentifierHistoryElement != null) {
            for (Object o : methodIdentifierHistoryElement.getChildren()) {
                methodIdentifierElement = (Element) o;
                DBMethodIdentifier methodIdentifier = new DBMethodIdentifier();
                methodIdentifier.readConfiguration(methodIdentifierElement);

                MethodExecutionInput executionInput = executionManager.getExecutionInput(methodIdentifier);
                methodSelectionHistory.add(executionInput);
            }
        }
    }

    @Override
    public RunConfiguration clone() {
        DBProgramRunConfiguration runConfiguration = (DBProgramRunConfiguration) super.clone();
        runConfiguration.configurationEditor = null;
        runConfiguration.executionInput = executionInput == null ? null : executionInput.clone();
        runConfiguration.methodSelectionHistory = new HashSet<MethodExecutionInput>(methodSelectionHistory);
        return runConfiguration;
    }
}
