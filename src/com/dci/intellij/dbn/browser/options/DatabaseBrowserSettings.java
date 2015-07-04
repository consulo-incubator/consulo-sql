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

package com.dci.intellij.dbn.browser.options;

import com.dci.intellij.dbn.browser.options.ui.DatabaseBrowserSettingsForm;
import com.dci.intellij.dbn.common.options.CompositeProjectConfiguration;
import com.dci.intellij.dbn.common.options.Configuration;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

public class DatabaseBrowserSettings extends CompositeProjectConfiguration<DatabaseBrowserSettingsForm> {
    private DatabaseBrowserGeneralSettings generalSettings;
    private DatabaseBrowserFilterSettings filterSettings;

    public DatabaseBrowserSettings(Project project) {
        super(project);
        filterSettings = new DatabaseBrowserFilterSettings(project);
        generalSettings = new DatabaseBrowserGeneralSettings(project);
    }

    @Override
    public DatabaseBrowserSettingsForm createConfigurationEditor() {
        return new DatabaseBrowserSettingsForm(this);
    }

    public static DatabaseBrowserSettings getInstance(Project project) {
        return getGlobalProjectSettings(project).getBrowserSettings();
    }

    @NotNull
    @Override
    public String getId() {
        return "DBNavigator.Project.DatabaseBrowserSettings";
    }

    public String getDisplayName() {
        return "Database Browser";
    }

    public String getHelpTopic() {
        return "browserSettings";
    }

    /*********************************************************
     *                        Custom                         *
     *********************************************************/

    public DatabaseBrowserGeneralSettings getGeneralSettings() {
        return generalSettings;
    }

    public DatabaseBrowserFilterSettings getFilterSettings() {
        return filterSettings;
    }

    /*********************************************************
     *                     Configuration                     *
     *********************************************************/

    @Override
    protected Configuration[] createConfigurations() {
        return new Configuration[] {generalSettings, filterSettings};
    }

    @Override
    public String getConfigElementName() {
        return "browser-settings";
    }

    public void readConfiguration(Element element) throws InvalidDataException {
        readConfiguration(element, generalSettings);
        readConfiguration(element, filterSettings);
    }

    public void writeConfiguration(Element element) throws WriteExternalException {
        writeConfiguration(element, generalSettings);
        writeConfiguration(element, filterSettings);
    }

}
