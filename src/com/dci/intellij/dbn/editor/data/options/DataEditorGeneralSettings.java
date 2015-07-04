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
import com.dci.intellij.dbn.common.options.setting.BooleanSetting;
import com.dci.intellij.dbn.common.options.setting.IntegerSetting;
import com.dci.intellij.dbn.editor.data.options.ui.DataEditorGeneralSettingsForm;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;

public class DataEditorGeneralSettings extends Configuration<DataEditorGeneralSettingsForm> {
    private IntegerSetting fetchBlockSize = new IntegerSetting("fetch-block-size", 100);
    private IntegerSetting fetchTimeout = new IntegerSetting("fetch-timeout", 30);
    private BooleanSetting trimWhitespaces = new BooleanSetting("trim-whitespaces", true);
    private BooleanSetting convertEmptyStringsToNull = new BooleanSetting("convert-empty-strings-to-null", true);
    private BooleanSetting selectContentOnCellEdit = new BooleanSetting("select-content-on-cell-edit", true);
    private BooleanSetting largeValuePreviewActive = new BooleanSetting("large-value-preview-active", true);

    public String getDisplayName() {
        return "Data editor general settings";
    }

    public String getHelpTopic() {
        return "dataEditor";
    }

    /*********************************************************
    *                       Settings                        *
    *********************************************************/

    public IntegerSetting getFetchBlockSize() {
        return fetchBlockSize;
    }

    public IntegerSetting getFetchTimeout() {
        return fetchTimeout;
    }

    public BooleanSetting getTrimWhitespaces() {
        return trimWhitespaces;
    }

    public BooleanSetting getConvertEmptyStringsToNull() {
        return convertEmptyStringsToNull;
    }

    public BooleanSetting getSelectContentOnCellEdit() {
        return selectContentOnCellEdit;
    }

    public BooleanSetting getLargeValuePreviewActive() {
        return largeValuePreviewActive;
    }

    /****************************************************
     *                   Configuration                  *
     ****************************************************/
    public DataEditorGeneralSettingsForm createConfigurationEditor() {
        return new DataEditorGeneralSettingsForm(this);
    }

    @Override
    public String getConfigElementName() {
        return "general";
    }

    public void readConfiguration(Element element) throws InvalidDataException {
        fetchBlockSize.readConfiguration(element);
        fetchTimeout.readConfiguration(element);
        trimWhitespaces.readConfiguration(element);
        convertEmptyStringsToNull.readConfiguration(element);
        selectContentOnCellEdit.readConfiguration(element);
        largeValuePreviewActive.readConfiguration(element);

    }

    public void writeConfiguration(Element element) throws WriteExternalException {
        fetchBlockSize.writeConfiguration(element);
        fetchTimeout.writeConfiguration(element);
        trimWhitespaces.writeConfiguration(element);
        convertEmptyStringsToNull.writeConfiguration(element);
        selectContentOnCellEdit.writeConfiguration(element);
        largeValuePreviewActive.writeConfiguration(element);    }
}
