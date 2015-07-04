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
import com.dci.intellij.dbn.editor.data.options.DataEditorValueListPopupSettings;
import com.intellij.openapi.options.ConfigurationException;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DatatEditorValueListPopupSettingsForm extends ConfigurationEditorForm<DataEditorValueListPopupSettings> {
    private JTextField elementCountThresholdTextBox;
    private JTextField dataLengthThresholdTextBox;
    private JCheckBox activeForPrimaryKeysCheckBox;
    private JPanel mainPanel;

    public DatatEditorValueListPopupSettingsForm(DataEditorValueListPopupSettings settings) {
        super(settings);
        updateBorderTitleForeground(mainPanel);
        resetChanges();
        registerComponent(elementCountThresholdTextBox);
        registerComponent(activeForPrimaryKeysCheckBox);
    }

    public JPanel getComponent() {
        return mainPanel;
    }

    public void applyChanges() throws ConfigurationException {
        DataEditorValueListPopupSettings settings = getConfiguration();
        settings.setActiveForPrimaryKeyColumns(activeForPrimaryKeysCheckBox.isSelected());
        settings.setElementCountThreshold(ConfigurationEditorUtil.validateIntegerInputValue(elementCountThresholdTextBox, "Element count threshold", 0, 10000, null));
        settings.setDataLengthThreshold(ConfigurationEditorUtil.validateIntegerInputValue(dataLengthThresholdTextBox, "Data length threshold", 0, 1000, null));
    }

    public void resetChanges() {
        DataEditorValueListPopupSettings settings = getConfiguration();
        activeForPrimaryKeysCheckBox.setSelected(settings.isActiveForPrimaryKeyColumns());
        elementCountThresholdTextBox.setText(Integer.toString(settings.getElementCountThreshold()));
        dataLengthThresholdTextBox.setText(Integer.toString(settings.getDataLengthThreshold()));
    }
}
