package com.dci.intellij.dbn.code.common.style.options.ui;

import com.dci.intellij.dbn.code.common.style.options.ProjectCodeStyleSettings;
import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.common.options.Configuration;
import com.dci.intellij.dbn.common.options.ui.CompositeConfigurationEditorForm;
import com.dci.intellij.dbn.common.ui.tab.TabbedPane;
import com.intellij.ui.tabs.TabInfo;

import javax.swing.*;
import java.awt.*;

public class CodeStyleSettingsForm extends CompositeConfigurationEditorForm<ProjectCodeStyleSettings> {
    private JPanel mainPanel;
    private TabbedPane languageTabs;

    public CodeStyleSettingsForm(ProjectCodeStyleSettings settings) {
        super(settings);
        languageTabs = new TabbedPane(settings.getProject());
        languageTabs.setAdjustBorders(false);
        mainPanel.add(languageTabs, BorderLayout.CENTER);

        addSettingsPanel(getConfiguration().getSQLCodeStyleSettings(), Icons.SQL_FILE);
        addSettingsPanel(getConfiguration().getPSQLCodeStyleSettings(), Icons.PLSQL_FILE);
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