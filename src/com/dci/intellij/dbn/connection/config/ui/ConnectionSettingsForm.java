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

import com.dci.intellij.dbn.common.options.ui.CompositeConfigurationEditorForm;
import com.dci.intellij.dbn.common.ui.tab.TabbedPane;
import com.dci.intellij.dbn.common.ui.tab.TabbedPaneUtil;
import com.dci.intellij.dbn.connection.config.ConnectionDatabaseSettings;
import com.dci.intellij.dbn.connection.config.ConnectionDetailSettings;
import com.dci.intellij.dbn.connection.config.ConnectionFilterSettings;
import com.dci.intellij.dbn.connection.config.ConnectionSettings;
import com.intellij.ui.tabs.TabInfo;

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class ConnectionSettingsForm extends CompositeConfigurationEditorForm<ConnectionSettings> {
    private JPanel mainPanel;
    private TabbedPane configTabbedPane;

    public ConnectionSettingsForm(ConnectionSettings connectionSettings) {
        super(connectionSettings);
        ConnectionDatabaseSettings databaseSettings = connectionSettings.getDatabaseSettings();
        configTabbedPane = new TabbedPane(databaseSettings.getProject());
        mainPanel.add(configTabbedPane, BorderLayout.CENTER);

        TabInfo connectionTabInfo = new TabInfo(databaseSettings.createComponent());
        connectionTabInfo.setText("Connection");
        configTabbedPane.addTab(connectionTabInfo);

        ConnectionDetailSettings detailSettings = connectionSettings.getDetailSettings();
        TabInfo detailsTabInfo = new TabInfo(detailSettings.createComponent());
        detailsTabInfo.setText("Details");
        configTabbedPane.addTab(detailsTabInfo);

        ConnectionFilterSettings filterSettings = connectionSettings.getFilterSettings();
        TabInfo filtersTabInfo = new TabInfo(filterSettings.createComponent());
        filtersTabInfo.setText("Filters");
        configTabbedPane.addTab(filtersTabInfo);

        String connectionId = connectionSettings.getId();
        ConnectionDetailSettingsForm detailSettingsForm = detailSettings.getSettingsEditor();
        ConnectionFilterSettingsForm filterSettingsForm = filterSettings.getSettingsEditor();
        GenericDatabaseSettingsForm databaseSettingsForm = databaseSettings.getSettingsEditor();

        filterSettingsForm.setConnectionId(connectionId);
        databaseSettingsForm.setConnectionId(connectionId);
        detailSettingsForm.setConnectionId(connectionId);

        databaseSettingsForm.notifyPresentationChanges();
        detailSettingsForm.notifyPresentationChanges();
    }

    public void selectTab(String tabName) {
        TabbedPaneUtil.selectTab(configTabbedPane, tabName);        
    }
    
    public String getSelectedTabName() {
        return TabbedPaneUtil.getSelectedTabName(configTabbedPane);
    }

    public JComponent getComponent() {
        return mainPanel;
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
