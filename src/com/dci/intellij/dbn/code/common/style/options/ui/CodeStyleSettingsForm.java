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

package com.dci.intellij.dbn.code.common.style.options.ui;

import com.dci.intellij.dbn.code.common.style.options.ProjectCodeStyleSettings;
import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.common.options.Configuration;
import com.dci.intellij.dbn.common.options.ui.CompositeConfigurationEditorForm;
import com.dci.intellij.dbn.common.ui.tab.TabbedPane;
import com.intellij.ui.tabs.TabInfo;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class CodeStyleSettingsForm extends CompositeConfigurationEditorForm<ProjectCodeStyleSettings> {
    private JPanel mainPanel;
    private TabbedPane languageTabs;

    public CodeStyleSettingsForm(ProjectCodeStyleSettings settings) {
        super(settings);
        languageTabs = new TabbedPane(settings.getProject());
        //languageTabs.setAdjustBorders(false);
        mainPanel.add(languageTabs, BorderLayout.CENTER);
        updateBorderTitleForeground(mainPanel);
        addSettingsPanel(getConfiguration().getSQLCodeStyleSettings(), Icons.FILE_SQL);
        addSettingsPanel(getConfiguration().getPSQLCodeStyleSettings(), Icons.FILE_PLSQL);
    }

    private void addSettingsPanel(Configuration configuration, Icon icon) {
        JComponent component = configuration.createComponent();
        TabInfo tabInfo = new TabInfo(component);
        tabInfo.setText(configuration.getDisplayName());
        tabInfo.setObject(configuration);
        tabInfo.setIcon(icon);
        languageTabs.addTab(tabInfo);
    }


    public JPanel getComponent() {
        return mainPanel;
    }
}
