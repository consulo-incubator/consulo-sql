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

package com.dci.intellij.dbn.common.environment.options;

import com.dci.intellij.dbn.common.environment.EnvironmentType;
import com.dci.intellij.dbn.common.environment.EnvironmentTypeBundle;
import com.dci.intellij.dbn.common.environment.options.ui.EnvironmentSettingsForm;
import com.dci.intellij.dbn.common.options.ProjectConfiguration;
import com.dci.intellij.dbn.common.options.ui.ConfigurationEditorForm;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;

public class EnvironmentSettings extends ProjectConfiguration {
    private EnvironmentTypeBundle environmentTypes = new EnvironmentTypeBundle(EnvironmentTypeBundle.DEFAULT);
    private EnvironmentVisibilitySettings visibilitySettings = new EnvironmentVisibilitySettings();
    public EnvironmentSettings(Project project) {
        super(project);
    }

    @Override
    protected ConfigurationEditorForm createConfigurationEditor() {
        return new EnvironmentSettingsForm(this);
    }

    public EnvironmentTypeBundle getEnvironmentTypes() {
        return environmentTypes;
    }

    public EnvironmentType getEnvironmentType(String environmentTypeId) {
        return environmentTypes.getEnvironmentType(environmentTypeId);
    }

    public boolean setEnvironmentTypes(EnvironmentTypeBundle environmentTypes) {
        boolean changed = !this.environmentTypes.equals(environmentTypes);
        this.environmentTypes = new EnvironmentTypeBundle(environmentTypes);
        return changed;
    }

    public EnvironmentVisibilitySettings getVisibilitySettings() {
        return visibilitySettings;
    }

    public void setVisibilitySettings(EnvironmentVisibilitySettings visibilitySettings) {
        this.visibilitySettings = visibilitySettings;
    }

    @Override
    public String getConfigElementName() {
        return "environment";
    }

    @Override
    public void readConfiguration(Element element) throws InvalidDataException {
        Element environmentTypesElement = element.getChild("environment-types");
        if (environmentTypesElement != null) {
            environmentTypes.clear();
            for (Object o : environmentTypesElement.getChildren()) {
                Element environmentTypeElement = (Element) o;
                EnvironmentType environmentType = new EnvironmentType();
                environmentType.readConfiguration(environmentTypeElement);
                environmentTypes.add(environmentType);
            }
        }

        Element visibilitySettingsElement = element.getChild("visibility-settings");
        if (visibilitySettingsElement != null) {
            visibilitySettings.readConfiguration(visibilitySettingsElement);
        }
    }

    @Override
    public void writeConfiguration(Element element) throws WriteExternalException {
        Element environmentTypesElement = new Element("environment-types");
        element.addContent(environmentTypesElement);
        for (EnvironmentType environmentType : environmentTypes) {
            Element itemElement = new Element("environment-type");
            environmentType.writeConfiguration(itemElement);
            environmentTypesElement.addContent(itemElement);
        }

        Element visibilitySettingsElement = new Element("visibility-settings");
        element.addContent(visibilitySettingsElement);
        visibilitySettings.writeConfiguration(visibilitySettingsElement);
    }
}
