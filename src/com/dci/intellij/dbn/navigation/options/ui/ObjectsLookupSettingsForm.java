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

package com.dci.intellij.dbn.navigation.options.ui;

import com.dci.intellij.dbn.common.options.ui.ConfigurationEditorForm;
import com.dci.intellij.dbn.common.ui.KeyUtil;
import com.dci.intellij.dbn.common.ui.list.CheckBoxList;
import com.dci.intellij.dbn.navigation.options.ObjectsLookupSettings;
import com.intellij.openapi.actionSystem.Shortcut;
import com.intellij.openapi.keymap.KeymapUtil;
import com.intellij.openapi.options.ConfigurationException;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

public class ObjectsLookupSettingsForm extends ConfigurationEditorForm<ObjectsLookupSettings> {
    private JPanel mainPanel;
    private JScrollPane lookupObjectsScrollPane;
    private JRadioButton loadRadioButton;
    private JRadioButton noLoadRadioButton;
    private JRadioButton promptRadioButton;
    private JRadioButton noPromptRadioButton;
    private CheckBoxList lookupObjectsList;

    public ObjectsLookupSettingsForm(ObjectsLookupSettings configuration) {
        super(configuration);
        Shortcut[] shortcuts = KeyUtil.getShortcuts("DBNavigator.Actions.Navigation.GotoDatabaseObject");
        TitledBorder border = (TitledBorder) mainPanel.getBorder();
        border.setTitle("Lookup Objects (" + KeymapUtil.getShortcutsText(shortcuts) + ")");
        updateBorderTitleForeground(mainPanel);

        lookupObjectsList = new CheckBoxList(configuration.getLookupObjectTypes());
        lookupObjectsScrollPane.setViewportView(lookupObjectsList);

        boolean databaseLoadActive = getConfiguration().getForceDatabaseLoad().value();
        loadRadioButton.setSelected(databaseLoadActive);
        noLoadRadioButton.setSelected(!databaseLoadActive);

        boolean promptConnectionSelectionActive = getConfiguration().getPromptConnectionSelection().value();
        promptRadioButton.setSelected(promptConnectionSelectionActive);
        noPromptRadioButton.setSelected(!promptConnectionSelectionActive);

        registerComponents(
                lookupObjectsList,
                promptRadioButton,
                noPromptRadioButton,
                loadRadioButton,
                noLoadRadioButton);
    }

    @Override
    public void applyChanges() throws ConfigurationException {
        lookupObjectsList.applyChanges();
        getConfiguration().getForceDatabaseLoad().applyChanges(loadRadioButton);
        getConfiguration().getPromptConnectionSelection().applyChanges(promptRadioButton);
    }

    @Override
    public void resetChanges() {
        getConfiguration().getForceDatabaseLoad().applyChanges(loadRadioButton);
        getConfiguration().getPromptConnectionSelection().applyChanges(promptRadioButton);
    }

    public JComponent getComponent() {
        return mainPanel;
    }
}
