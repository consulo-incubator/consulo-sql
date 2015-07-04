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

import com.dci.intellij.dbn.browser.options.ui.DatabaseBrowserFilterSettingsForm;
import com.dci.intellij.dbn.common.options.CompositeProjectConfiguration;
import com.dci.intellij.dbn.common.options.Configuration;
import com.dci.intellij.dbn.object.filter.type.ObjectTypeFilterSettings;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;

public class DatabaseBrowserFilterSettings extends CompositeProjectConfiguration<DatabaseBrowserFilterSettingsForm> {
    private ObjectTypeFilterSettings objectTypeFilterSettings;

    public DatabaseBrowserFilterSettings(Project project) {
        super(project);
        objectTypeFilterSettings = new ObjectTypeFilterSettings(project, null);
    }

    @Override
    public DatabaseBrowserFilterSettingsForm createConfigurationEditor() {
        return new DatabaseBrowserFilterSettingsForm(this);
    }

    @Override
    public String getConfigElementName() {
        return "filters";
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

    public ObjectTypeFilterSettings getObjectTypeFilterSettings() {
        return objectTypeFilterSettings;
    }

    /*********************************************************
     *                     Configuration                     *
     *********************************************************/

    @Override
    protected Configuration[] createConfigurations() {
        return new Configuration[] {objectTypeFilterSettings};
    }

    public void readConfiguration(Element element) throws InvalidDataException {
        readConfiguration(element, objectTypeFilterSettings);
    }

    public void writeConfiguration(Element element) throws WriteExternalException {
        writeConfiguration(element, objectTypeFilterSettings);
    }
}
