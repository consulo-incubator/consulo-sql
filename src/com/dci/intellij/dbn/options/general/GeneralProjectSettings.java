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

package com.dci.intellij.dbn.options.general;

import com.dci.intellij.dbn.common.environment.options.EnvironmentSettings;
import com.dci.intellij.dbn.common.locale.options.RegionalSettings;
import com.dci.intellij.dbn.common.options.CompositeProjectConfiguration;
import com.dci.intellij.dbn.common.options.Configuration;
import com.dci.intellij.dbn.options.general.ui.GeneralProjectSettingsForm;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class GeneralProjectSettings extends CompositeProjectConfiguration<GeneralProjectSettingsForm> {
    private RegionalSettings regionalSettings;
    private EnvironmentSettings environmentSettings;

    public GeneralProjectSettings(Project project) {
        super(project);
        regionalSettings = new RegionalSettings();
        environmentSettings = new EnvironmentSettings(project);
    }

    public static GeneralProjectSettings getInstance(Project project) {
        return getGlobalProjectSettings(project).getGeneralSettings();
    }

    @NotNull
    @Override
    public String getId() {
        return "DBNavigator.Project.GeneralSettings";
    }

    public String getDisplayName() {
        return "General";
    }

    /*********************************************************
    *                        Custom                         *
    *********************************************************/
    public RegionalSettings getRegionalSettings() {
        return regionalSettings;
    }

    public EnvironmentSettings getEnvironmentSettings() {
        return environmentSettings;
    }

    /*********************************************************
     *                      Configuration                    *
     *********************************************************/
    public GeneralProjectSettingsForm createConfigurationEditor() {
        return new GeneralProjectSettingsForm(this);
    }

    @Override
    public String getConfigElementName() {
        return "general-settings";
    }

    protected Configuration[] createConfigurations() {
        return new Configuration[] {regionalSettings, environmentSettings};
    }

}
