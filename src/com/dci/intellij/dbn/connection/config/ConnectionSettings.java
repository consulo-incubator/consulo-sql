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

package com.dci.intellij.dbn.connection.config;

import com.dci.intellij.dbn.common.options.CompositeConfiguration;
import com.dci.intellij.dbn.common.options.Configuration;
import com.dci.intellij.dbn.connection.ConnectionBundle;
import com.dci.intellij.dbn.connection.config.ui.ConnectionSettingsForm;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

public class ConnectionSettings extends CompositeConfiguration<ConnectionSettingsForm> {
    private ConnectionDatabaseSettings databaseSettings;
    private ConnectionDetailSettings detailSettings;
    private ConnectionFilterSettings filterSettings;

    public ConnectionSettings(ConnectionBundle connectionBundle) {
        Project project = connectionBundle.getProject();
        databaseSettings = new GenericConnectionDatabaseSettings(connectionBundle);
        detailSettings = new ConnectionDetailSettings(project);
        filterSettings = new ConnectionFilterSettings(project);
    }

    public ConnectionSettings(GenericConnectionDatabaseSettings databaseSettings, ConnectionDetailSettings detailSettings, ConnectionFilterSettings filterSettings) {
        this.databaseSettings = databaseSettings;
        this.detailSettings = detailSettings;
        this.filterSettings = filterSettings;
    }

    public ConnectionDatabaseSettings getDatabaseSettings() {
        return databaseSettings;
    }

    public ConnectionDetailSettings getDetailSettings() {
        return detailSettings;
    }

    public ConnectionFilterSettings getFilterSettings() {
        return filterSettings;
    }

    @Override
    protected Configuration[] createConfigurations() {
        return new Configuration[] {databaseSettings, detailSettings, filterSettings};
    }

    @Override
    protected ConnectionSettingsForm createConfigurationEditor() {
        return new ConnectionSettingsForm(this);
    }

    @NotNull
    @Override
    public String getId() {
        return databaseSettings.getId();
    }

    public ConnectionSettings clone() {
        try {
            Element connectionElement = new Element("Connection");
            writeConfiguration(connectionElement);
            ConnectionSettings clone = new ConnectionSettings(databaseSettings.getConnectionBundle());
            clone.readConfiguration(connectionElement);
            clone.getDatabaseSettings().setConnectivityStatus(databaseSettings.getConnectivityStatus());
            clone.getDatabaseSettings().generateNewId();
            return clone;
        } catch (Exception e) {
        }
        return null;
    }


    /*********************************************************
     *                     Configuration                     *
     *********************************************************/
    public void readConfiguration(Element element) throws InvalidDataException {
        readConfiguration(element, detailSettings);
        readConfiguration(element, filterSettings);
        databaseSettings.readConfiguration(element);
    }

    public void writeConfiguration(Element element) throws WriteExternalException {
        writeConfiguration(element, detailSettings);
        writeConfiguration(element, filterSettings);
        databaseSettings.writeConfiguration(element);
    }
}
