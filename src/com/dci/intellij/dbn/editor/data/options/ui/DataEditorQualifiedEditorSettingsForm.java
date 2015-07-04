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
import com.dci.intellij.dbn.common.ui.list.CheckBoxList;
import com.dci.intellij.dbn.editor.data.options.DataEditorQualifiedEditorSettings;
import com.intellij.openapi.options.ConfigurationException;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class DataEditorQualifiedEditorSettingsForm extends ConfigurationEditorForm<DataEditorQualifiedEditorSettings> {
    private JPanel mainPanel;
    private JScrollPane listScrollPane;
    private JTextField textLengthThresholdTextField;
    private CheckBoxList checkBoxList;

    public DataEditorQualifiedEditorSettingsForm(DataEditorQualifiedEditorSettings settings) {
        super(settings);
        checkBoxList = new CheckBoxList(settings.getContentTypes());
        listScrollPane.setViewportView(checkBoxList);
        updateBorderTitleForeground(mainPanel);
        resetChanges();

        registerComponent(textLengthThresholdTextField);
        registerComponent(checkBoxList);
    }

    @Override
    public void applyChanges() throws ConfigurationException {
        DataEditorQualifiedEditorSettings settings = getConfiguration();
        checkBoxList.applyChanges();
        settings.setTextLengthThreshold(ConfigurationEditorUtil.
                validateIntegerInputValue(
                        textLengthThresholdTextField,
                        "Text Length Threshold", 0, 999999999, null));
    }

    @Override
    public void resetChanges() {
        DataEditorQualifiedEditorSettings settings = getConfiguration();
        textLengthThresholdTextField.setText(Integer.toString(settings.getTextLengthThreshold()));
    }

    public JComponent getComponent() {
        return mainPanel;
    }
}
