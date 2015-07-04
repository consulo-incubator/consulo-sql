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

import com.dci.intellij.dbn.browser.options.DatabaseBrowserSettings;
import com.dci.intellij.dbn.common.options.ui.CompositeConfigurationEditorForm;

import javax.swing.*;
import java.awt.*;


public class DatabaseBrowserSettingsForm extends CompositeConfigurationEditorForm<DatabaseBrowserSettings> {
    private JPanel mainPanel;
    private JPanel generalSettingsPanel;
    private JPanel filterSettingsPanel;

    public DatabaseBrowserSettingsForm(DatabaseBrowserSettings settings) {
        super(settings);
        generalSettingsPanel.add(settings.getGeneralSettings().createComponent(), BorderLayout.CENTER);
        filterSettingsPanel.add(settings.getFilterSettings().createComponent(), BorderLayout.CENTER);
    }

    public JComponent getComponent() {
        return mainPanel;
    }
}
