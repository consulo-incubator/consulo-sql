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

package com.dci.intellij.dbn.code.common.style.options;

import com.dci.intellij.dbn.code.common.style.options.ui.CodeStyleSettingsForm;
import com.dci.intellij.dbn.code.psql.style.options.PSQLCodeStyleSettings;
import com.dci.intellij.dbn.code.psql.style.options.PSQLCustomCodeStyleSettings;
import com.dci.intellij.dbn.code.sql.style.options.SQLCodeStyleSettings;
import com.dci.intellij.dbn.code.sql.style.options.SQLCustomCodeStyleSettings;
import com.dci.intellij.dbn.common.options.CompositeProjectConfiguration;
import com.dci.intellij.dbn.common.options.Configuration;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CodeStyleSettingsManager;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

public class ProjectCodeStyleSettings extends CompositeProjectConfiguration<CodeStyleSettingsForm> {
    public ProjectCodeStyleSettings(Project project){
        super(project);
    }

    public static ProjectCodeStyleSettings getInstance(Project project) {
        return getGlobalProjectSettings(project).getCodeStyleSettings();
    }

    @NotNull
    @Override
    public String getId() {
        return "DBNavigator.Project.CodeStyleSettings";
    }

    public String getDisplayName() {
        return "Code Style";
    }

    public CodeStyleSettingsForm createConfigurationEditor() {
        return new CodeStyleSettingsForm(this);
    }

    public SQLCodeStyleSettings getSQLCodeStyleSettings() {
        CodeStyleSettings codeStyleSettings = getCodeStyleSettings();

        SQLCustomCodeStyleSettings customCodeStyleSettings =
                codeStyleSettings.getCustomSettings(SQLCustomCodeStyleSettings.class);
        return customCodeStyleSettings.getCodeStyleSettings();
    }

    public PSQLCodeStyleSettings getPSQLCodeStyleSettings() {
        CodeStyleSettings codeStyleSettings = getCodeStyleSettings();

        PSQLCustomCodeStyleSettings customCodeStyleSettings =
                codeStyleSettings.getCustomSettings(PSQLCustomCodeStyleSettings.class);
        return customCodeStyleSettings.getCodeStyleSettings();
    }

    private CodeStyleSettings getCodeStyleSettings() {
        CodeStyleSettings codeStyleSettings;
        if (CodeStyleSettingsManager.getInstance().USE_PER_PROJECT_SETTINGS) {
            codeStyleSettings = CodeStyleSettingsManager.getSettings(getProject());
        } else {
            codeStyleSettings = CodeStyleSettingsManager.getInstance().getCurrentSettings();
        }
        return codeStyleSettings;
    }

    /*********************************************************
    *                     Configuration                      *
    *********************************************************/
    protected Configuration[] createConfigurations() {
        return new Configuration[] {
                getSQLCodeStyleSettings(),
                getPSQLCodeStyleSettings()};
    }

    public void readConfiguration(Element element) throws InvalidDataException {

    }

    public void writeConfiguration(Element element) throws WriteExternalException {

    }

}
