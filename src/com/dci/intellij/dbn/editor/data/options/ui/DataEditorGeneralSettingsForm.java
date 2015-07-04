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

package com.dci.intellij.dbn.editor.data.options.ui;

import com.dci.intellij.dbn.common.options.ui.ConfigurationEditorForm;
import com.dci.intellij.dbn.common.options.ui.ConfigurationEditorUtil;
import com.dci.intellij.dbn.editor.data.options.DataEditorGeneralSettings;
import com.intellij.openapi.options.ConfigurationException;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DataEditorGeneralSettingsForm extends ConfigurationEditorForm<DataEditorGeneralSettings> {
    private JPanel mainPanel;
    private JTextField fetchBlockSizeTextField;
    private JTextField fetchTimeoutTextField;
    private JCheckBox trimWhitespacesCheckBox;
    private JCheckBox convertEmptyToNullCheckBox;
    private JCheckBox selectContentOnEditCheckBox;
    private JCheckBox largeValuePreviewActiveCheckBox;

    public DataEditorGeneralSettingsForm(DataEditorGeneralSettings settings) {
        super(settings);
        updateBorderTitleForeground(mainPanel);
        resetChanges();

        registerComponent(trimWhitespacesCheckBox);
        registerComponent(convertEmptyToNullCheckBox);
        registerComponent(fetchBlockSizeTextField);
        registerComponent(fetchTimeoutTextField);
        registerComponent(selectContentOnEditCheckBox);
        registerComponent(largeValuePreviewActiveCheckBox);
    }

    public JPanel getComponent() {
        return mainPanel;
    }

    public void applyChanges() throws ConfigurationException {
        ConfigurationEditorUtil.validateIntegerInputValue(fetchBlockSizeTextField, "Fetch block size", 1, 10000, null);
        ConfigurationEditorUtil.validateIntegerInputValue(fetchTimeoutTextField, "Fetch timeout", 0, 300, "\nUse value 0 for no timeout");

        DataEditorGeneralSettings settings = getConfiguration();
        settings.getFetchBlockSize().applyChanges(fetchBlockSizeTextField);
        settings.getFetchTimeout().applyChanges(fetchTimeoutTextField);
        settings.getTrimWhitespaces().applyChanges(trimWhitespacesCheckBox);
        settings.getConvertEmptyStringsToNull().applyChanges(convertEmptyToNullCheckBox);
        settings.getSelectContentOnCellEdit().applyChanges(selectContentOnEditCheckBox);
        settings.getLargeValuePreviewActive().applyChanges(largeValuePreviewActiveCheckBox);
    }

    public void resetChanges() {
        DataEditorGeneralSettings settings = getConfiguration();
        settings.getFetchBlockSize().resetChanges(fetchBlockSizeTextField);
        settings.getFetchTimeout().resetChanges(fetchTimeoutTextField);
        settings.getTrimWhitespaces().resetChanges(trimWhitespacesCheckBox);
        settings.getConvertEmptyStringsToNull().resetChanges(convertEmptyToNullCheckBox);
        settings.getSelectContentOnCellEdit().resetChanges(selectContentOnEditCheckBox);
        settings.getLargeValuePreviewActive().resetChanges(largeValuePreviewActiveCheckBox);    }
}
