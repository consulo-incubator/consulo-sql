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

package com.dci.intellij.dbn.ddl.options;

import com.dci.intellij.dbn.common.options.CompositeProjectConfiguration;
import com.dci.intellij.dbn.common.options.Configuration;
import com.dci.intellij.dbn.ddl.options.ui.DDFileSettingsForm;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class DDLFileSettings extends CompositeProjectConfiguration<DDFileSettingsForm> {
    private DDLFileExtensionSettings extensionSettings;
    private DDLFileGeneralSettings generalSettings;

    public DDLFileSettings(Project project) {
        super(project);
        extensionSettings = new DDLFileExtensionSettings(project);
        generalSettings = new DDLFileGeneralSettings();
    }

    public static DDLFileSettings getInstance(Project project) {
        return getGlobalProjectSettings(project).getDdlFileSettings();
    }

    public DDLFileExtensionSettings getExtensionSettings() {
        return extensionSettings;
    }

    public DDLFileGeneralSettings getGeneralSettings() {
        return generalSettings;
    }

    @NotNull
    @Override
    public String getId() {
        return "DBNavigator.Project.DDLFileSettings";
    }

    public String getDisplayName() {
        return "DDL Files";
    }

    public String getHelpTopic() {
        return "ddlFileSettings";
    }
    /********************************************************
    *                     Configuration                     *
    *********************************************************/
    public DDFileSettingsForm createConfigurationEditor() {
        return new DDFileSettingsForm(this);
    }

    @Override
    public String getConfigElementName() {
        return "ddl-file-settings";
    }

    protected Configuration[] createConfigurations() {
        return new Configuration[] {
                extensionSettings,
                generalSettings};
    }
}
