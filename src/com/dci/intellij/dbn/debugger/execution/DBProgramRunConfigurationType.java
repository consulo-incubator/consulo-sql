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

import com.dci.intellij.dbn.common.Icons;
import com.intellij.execution.LocatableConfigurationType;
import com.intellij.execution.Location;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class DBProgramRunConfigurationType implements ConfigurationType, LocatableConfigurationType {
    private ConfigurationFactory[] configurationFactories = new ConfigurationFactory[]{new DBProgramRunConfigurationFactory(this)};


    public String getDisplayName() {
        return "DB-Program";
    }

    public String getConfigurationTypeDescription() {
        return null;
    }

    public Icon getIcon() {
        return Icons.EXEC_CONFIG;
    }

    @NotNull
    public String getId() {
        return "DBProgramDebugSession";
    }

    public ConfigurationFactory[] getConfigurationFactories() {
        return configurationFactories;
    }

    public DBProgramRunConfigurationFactory getConfigurationFactory() {
        return (DBProgramRunConfigurationFactory) configurationFactories[0];
    }


    public RunnerAndConfigurationSettings createConfigurationByLocation(Location location) {
        return null;
    }

    public boolean isConfigurationByLocation(RunConfiguration configuration, Location location) {
        return false;
    }
}
