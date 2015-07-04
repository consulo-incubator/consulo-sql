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
import com.dci.intellij.dbn.editor.data.filter.DatasetFilterType;
import com.dci.intellij.dbn.editor.data.options.DataEditorFilterSettings;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.SimpleTextAttributes;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataEditorFilterSettingsForm extends ConfigurationEditorForm<DataEditorFilterSettings> {
    private JPanel mainPanel;
    private JCheckBox promptFilterDialogCheckBox;
    private JComboBox defaultFilterTypeComboBox;

    public DataEditorFilterSettingsForm(DataEditorFilterSettings settings) {
        super(settings);
        defaultFilterTypeComboBox.addItem(DatasetFilterType.NONE);
        defaultFilterTypeComboBox.addItem(DatasetFilterType.BASIC);
        defaultFilterTypeComboBox.addItem(DatasetFilterType.CUSTOM);
        defaultFilterTypeComboBox.setRenderer(new ColoredListCellRenderer() {
            protected void customizeCellRenderer(JList list, Object value, int index, boolean selected, boolean hasFocus) {
                DatasetFilterType filterType = (DatasetFilterType) value;
                setIcon(filterType.getIcon());
                append(filterType.name(), SimpleTextAttributes.REGULAR_ATTRIBUTES);
            }
        });
        updateBorderTitleForeground(mainPanel);
        resetChanges();
        defaultFilterTypeComboBox.setEnabled(promptFilterDialogCheckBox.isSelected());
        registerComponent(promptFilterDialogCheckBox);
        registerComponent(defaultFilterTypeComboBox);
    }

    @Override
    protected ActionListener createActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getConfiguration().setModified(true);
                defaultFilterTypeComboBox.setEnabled(promptFilterDialogCheckBox.isSelected());
            }
        };
    }

    public JPanel getComponent() {
        return mainPanel;
    }

    public void applyChanges() throws ConfigurationException {
        DataEditorFilterSettings settings = getConfiguration();
        settings.setPromptFilterDialog(promptFilterDialogCheckBox.isSelected());
        settings.setDefaultFilterType((DatasetFilterType) defaultFilterTypeComboBox.getSelectedItem());
    }


    public void resetChanges() {
        DataEditorFilterSettings settings = getConfiguration();
        promptFilterDialogCheckBox.setSelected(settings.isPromptFilterDialog());
        defaultFilterTypeComboBox.setSelectedItem(settings.getDefaultFilterType());
    }
}
