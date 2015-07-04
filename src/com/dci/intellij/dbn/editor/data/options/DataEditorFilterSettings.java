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

package com.dci.intellij.dbn.editor.data.options;

import com.dci.intellij.dbn.common.options.Configuration;
import com.dci.intellij.dbn.common.options.setting.SettingsUtil;
import com.dci.intellij.dbn.editor.data.filter.DatasetFilterType;
import com.dci.intellij.dbn.editor.data.options.ui.DataEditorFilterSettingsForm;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;

public class DataEditorFilterSettings extends Configuration<DataEditorFilterSettingsForm> {
    private boolean promptFilterDialog = true;
    private DatasetFilterType defaultFilterType = DatasetFilterType.BASIC;

    public String getDisplayName() {
        return "Data editor filters settings";
    }

    public String getHelpTopic() {
        return "dataEditor";
    }

    /*********************************************************
     *                       Custom                          *
     *********************************************************/

    public boolean isPromptFilterDialog() {
        return promptFilterDialog;
    }

    public void setPromptFilterDialog(boolean promptFilterDialog) {
        this.promptFilterDialog = promptFilterDialog;
    }

    public DatasetFilterType getDefaultFilterType() {
        return defaultFilterType;
    }

    public void setDefaultFilterType(DatasetFilterType defaultFilterType) {
        this.defaultFilterType = defaultFilterType;
    }

    /****************************************************
     *                   Configuration                  *
     ****************************************************/
    public DataEditorFilterSettingsForm createConfigurationEditor() {
        return new DataEditorFilterSettingsForm(this);
    }

    @Override
    public String getConfigElementName() {
        return "filters";
    }

    public void readConfiguration(Element element) throws InvalidDataException {
        promptFilterDialog = SettingsUtil.getBoolean(element, "prompt-filter-dialog", promptFilterDialog);
        defaultFilterType = DatasetFilterType.get(SettingsUtil.getString(element, "default-filter-type", defaultFilterType.name()));
    }

    public void writeConfiguration(Element element) throws WriteExternalException {
        SettingsUtil.setBoolean(element, "prompt-filter-dialog", promptFilterDialog);
        SettingsUtil.setString(element, "default-filter-type", defaultFilterType.name());
    }
}
