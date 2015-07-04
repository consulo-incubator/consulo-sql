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

package com.dci.intellij.dbn.browser.options.ui;

import com.dci.intellij.dbn.browser.options.BrowserDisplayMode;
import com.dci.intellij.dbn.browser.options.DatabaseBrowserGeneralSettings;
import com.dci.intellij.dbn.browser.options.ObjectDisplaySettingsListener;
import com.dci.intellij.dbn.common.event.EventManager;
import com.dci.intellij.dbn.common.options.ui.ConfigurationEditorForm;
import com.dci.intellij.dbn.common.options.ui.ConfigurationEditorUtil;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class DatabaseBrowserGeneralSettingsForm extends ConfigurationEditorForm<DatabaseBrowserGeneralSettings> {
    private JPanel mainPanel;
    private JRadioButton simpleRadioButton;
    private JRadioButton tabbedRadioButton;
    private JTextField navigationHistorySizeTextField;
    private JCheckBox showObjectDetailsCheckBox;


    public DatabaseBrowserGeneralSettingsForm(DatabaseBrowserGeneralSettings configuration) {
        super(configuration);
        updateBorderTitleForeground(mainPanel);
        resetChanges();

        registerComponent(simpleRadioButton);
        registerComponent(tabbedRadioButton);
        registerComponent(showObjectDetailsCheckBox);
    }

    public JComponent getComponent() {
        return mainPanel;
    }

    public void applyChanges() throws ConfigurationException {
        DatabaseBrowserGeneralSettings configuration = getConfiguration();
        boolean repaintTree = configuration.isModified();
        
        BrowserDisplayMode displayMode =
                simpleRadioButton.isSelected() ? BrowserDisplayMode.SIMPLE :
                tabbedRadioButton.isSelected() ? BrowserDisplayMode.TABBED :
                BrowserDisplayMode.SIMPLE;
        configuration.setDisplayMode(displayMode);

        ConfigurationEditorUtil.validateIntegerInputValue(navigationHistorySizeTextField, "Navigation history size", 0, 1000, "");
        configuration.getNavigationHistorySize().applyChanges(navigationHistorySizeTextField);
        configuration.getShowObjectDetails().applyChanges(showObjectDetailsCheckBox);
        
        if (repaintTree) {
            Project project = configuration.getProject();
            ObjectDisplaySettingsListener listener = EventManager.notify(project, ObjectDisplaySettingsListener.TOPIC);
            listener.displayDetailsChanged();
        }
        
    }

    public void resetChanges() {
        DatabaseBrowserGeneralSettings configuration = getConfiguration();
        BrowserDisplayMode displayMode = configuration.getDisplayMode();
        if (displayMode == BrowserDisplayMode.SIMPLE) simpleRadioButton.setSelected(true); else
        if (displayMode == BrowserDisplayMode.TABBED) tabbedRadioButton.setSelected(true);

        configuration.getNavigationHistorySize().resetChanges(navigationHistorySizeTextField);
        configuration.getShowObjectDetails().resetChanges(showObjectDetailsCheckBox);
    }
}
