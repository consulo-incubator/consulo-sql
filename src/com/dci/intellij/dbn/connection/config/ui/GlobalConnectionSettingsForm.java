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

package com.dci.intellij.dbn.connection.config.ui;

import com.dci.intellij.dbn.browser.ui.ModuleListCellRenderer;
import com.dci.intellij.dbn.common.options.ui.ConfigurationEditorForm;
import com.dci.intellij.dbn.connection.ConnectionBundle;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.connection.GlobalConnectionSettings;
import com.dci.intellij.dbn.connection.ModuleConnectionBundle;
import com.dci.intellij.dbn.connection.ProjectConnectionBundle;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashSet;
import java.util.Set;

public class GlobalConnectionSettingsForm extends ConfigurationEditorForm<GlobalConnectionSettings> implements ItemListener {
    private Set<String> cachedPanelIds = new HashSet<String>();

    private JPanel mainPanel;
    private JPanel connectionsPanel;
    private JComboBox modulesComboBox;

    public GlobalConnectionSettingsForm(GlobalConnectionSettings connectionSettings) {
        super(connectionSettings);
        Project project = connectionSettings.getProject();

        updateModulesChooser(project);
        modulesComboBox.setRenderer(new ModuleListCellRenderer());
        modulesComboBox.addItemListener(this);
        switchSettingsPanel(ProjectConnectionBundle.getInstance(project));
    }

    public JPanel getComponent() {
        return mainPanel;
    }

    public void applyChanges() throws ConfigurationException {
        Project project = getConfiguration().getProject();
        ProjectConnectionBundle projectConnectionManager = ProjectConnectionBundle.getInstance(project);
        projectConnectionManager.apply();

        ModuleManager moduleManager = ModuleManager.getInstance(project);
        Module[] modules = moduleManager.getModules();
        for (Module module : modules) {
            ModuleConnectionBundle.getInstance(module).apply();
        }
    }

    public void resetChanges() {
        Project project = getConfiguration().getProject();
        ProjectConnectionBundle projectConnectionManager = ProjectConnectionBundle.getInstance(project);
        projectConnectionManager.reset();

        ModuleManager moduleManager = ModuleManager.getInstance(project);
        Module[] modules = moduleManager.getModules();
        for (Module module : modules) {
            ModuleConnectionBundle.getInstance(module).reset();
        }
    }

    private void updateModulesChooser(Project project) {
        modulesComboBox.addItem(ProjectConnectionBundle.getInstance(project));

        Module[] modules = ModuleManager.getInstance(project).getModules();
        for (Module module : modules) {
            modulesComboBox.addItem(ModuleConnectionBundle.getInstance(module));
        }
    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            ConnectionBundle connectionBundle = (ConnectionBundle) e.getItem();
            switchSettingsPanel(connectionBundle);
        }
    }

    private void switchSettingsPanel(ConnectionBundle connectionBundle) {
        String id = connectionBundle.toString();
        if (!cachedPanelIds.contains(id)) {
            JComponent connectionsSetupPanel = connectionBundle.createComponent();
            connectionsPanel.add(connectionsSetupPanel, id);
            cachedPanelIds.add(id);
        }
        CardLayout cardLayout = (CardLayout) connectionsPanel.getLayout();
        cardLayout.show(connectionsPanel, id);
    }

    public void focusConnectionSettings(ConnectionHandler connectionHandler) {
        ConnectionBundle connectionBundle = connectionHandler.getConnectionBundle();
        modulesComboBox.setSelectedItem(connectionBundle);
        ConnectionBundleSettingsForm settingsEditor = connectionBundle.getSettingsEditor();
        if (settingsEditor != null) {
            settingsEditor.selectConnection(connectionHandler);
        }
    }
}
