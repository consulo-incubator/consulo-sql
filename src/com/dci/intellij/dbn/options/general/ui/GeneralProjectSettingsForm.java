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

package com.dci.intellij.dbn.options.general.ui;

import com.dci.intellij.dbn.DatabaseNavigator;
import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.common.options.ui.CompositeConfigurationEditorForm;
import com.dci.intellij.dbn.options.general.GeneralProjectSettings;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GeneralProjectSettingsForm extends CompositeConfigurationEditorForm<GeneralProjectSettings> {
    private JPanel mainPanel;
    private JLabel debugInfoLabel;
    private JCheckBox enableDebugCheckBox;
    private JPanel localeSettingsPanel;
    private JPanel environmentSettingsPanel;

    public GeneralProjectSettingsForm(GeneralProjectSettings generalSettings) {
        super(generalSettings);
        debugInfoLabel.setIcon(Icons.COMMON_WARNING);
        debugInfoLabel.setText("NOTE: Active debug mode may considerably slow down your system.");
        resetChanges();

        registerComponent(enableDebugCheckBox);

        localeSettingsPanel.add(generalSettings.getRegionalSettings().createComponent(), BorderLayout.CENTER);
        environmentSettingsPanel.add(generalSettings.getEnvironmentSettings().createComponent(), BorderLayout.CENTER);
    }

    protected ActionListener createActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getConfiguration().setModified(true);
                debugInfoLabel.setVisible(enableDebugCheckBox.isSelected());
            }
        };
    }

    public JPanel getComponent() {
        return mainPanel;
    }

    public void applyChanges() {
        DatabaseNavigator.getInstance().setDebugModeEnabled(enableDebugCheckBox.isSelected());
    }

    public void resetChanges() {
        enableDebugCheckBox.setSelected(DatabaseNavigator.getInstance().isDebugModeEnabled());
        debugInfoLabel.setVisible(enableDebugCheckBox.isSelected());
    }
}
