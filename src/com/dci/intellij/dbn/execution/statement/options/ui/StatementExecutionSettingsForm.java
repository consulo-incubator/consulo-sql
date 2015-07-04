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

package com.dci.intellij.dbn.execution.statement.options.ui;

import com.dci.intellij.dbn.common.options.ui.ConfigurationEditorForm;
import com.dci.intellij.dbn.common.options.ui.ConfigurationEditorUtil;
import com.dci.intellij.dbn.execution.statement.options.StatementExecutionSettings;
import com.intellij.openapi.options.ConfigurationException;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StatementExecutionSettingsForm extends ConfigurationEditorForm<StatementExecutionSettings> {
    private JPanel mainPanel;
    private JTextField fetchBlockSizeTextField;
    private JTextField executionTimeoutTextField;
    private JCheckBox focusResultCheckBox;

    public StatementExecutionSettingsForm(StatementExecutionSettings settings) {
        super(settings);
        updateBorderTitleForeground(mainPanel);

        resetChanges();
        registerComponent(fetchBlockSizeTextField);
        registerComponent(executionTimeoutTextField);
        registerComponent(focusResultCheckBox);
    }

    public JPanel getComponent() {
        return mainPanel;
    }

    public void applyChanges() throws ConfigurationException {
        StatementExecutionSettings settings = getConfiguration();
        settings.setResultSetFetchBlockSize(ConfigurationEditorUtil.validateIntegerInputValue(fetchBlockSizeTextField, "Fetch block size", 1, 10000, null));
        settings.setExecutionTimeout(ConfigurationEditorUtil.validateIntegerInputValue(executionTimeoutTextField, "Execution timeout", 0, 300, "\nUse value 0 for no timeout"));
        settings.setFocusResult(focusResultCheckBox.isSelected());
    }

    public void resetChanges() {
        StatementExecutionSettings settings = getConfiguration();
        fetchBlockSizeTextField.setText(Integer.toString(settings.getResultSetFetchBlockSize()));
        executionTimeoutTextField.setText(Integer.toString(settings.getExecutionTimeout()));
        focusResultCheckBox.setSelected(settings.isFocusResult());
    }
}
