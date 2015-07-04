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

import com.dci.intellij.dbn.browser.options.ui.DatabaseBrowserGeneralSettingsForm;
import com.dci.intellij.dbn.common.options.ProjectConfiguration;
import com.dci.intellij.dbn.common.options.setting.BooleanSetting;
import com.dci.intellij.dbn.common.options.setting.IntegerSetting;
import com.dci.intellij.dbn.common.options.setting.SettingsUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;

public class DatabaseBrowserGeneralSettings extends ProjectConfiguration<DatabaseBrowserGeneralSettingsForm> {
    private BrowserDisplayMode displayMode = BrowserDisplayMode.TABBED;
    private IntegerSetting navigationHistorySize = new IntegerSetting("navigation-history-size", 100);
    private BooleanSetting showObjectDetails = new BooleanSetting("show-object-details", false);

    public DatabaseBrowserGeneralSettings(Project project) {
        super(project);
    }

    @Override
    public DatabaseBrowserGeneralSettingsForm createConfigurationEditor() {
        return new DatabaseBrowserGeneralSettingsForm(this);
    }

    @Override
    public String getConfigElementName() {
        return "general";
    }

    public BrowserDisplayMode getDisplayMode() {
        return displayMode;
    }

    public void setDisplayMode(BrowserDisplayMode displayMode) {
        this.displayMode = displayMode;
    }

    public IntegerSetting getNavigationHistorySize() {
        return navigationHistorySize;
    }

    public BooleanSetting getShowObjectDetails() {
        return showObjectDetails;
    }

    public void readConfiguration(Element element) throws InvalidDataException {
        displayMode = SettingsUtil.getEnum(element, "display-mode", BrowserDisplayMode.TABBED);
        navigationHistorySize.readConfiguration(element);
        showObjectDetails.readConfiguration(element);
    }

    public void writeConfiguration(Element element) throws WriteExternalException {
        SettingsUtil.setEnum(element, "display-mode", displayMode);
        navigationHistorySize.writeConfiguration(element);
        showObjectDetails.writeConfiguration(element);
    }

}
