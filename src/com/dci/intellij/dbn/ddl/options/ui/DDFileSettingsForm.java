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

package com.dci.intellij.dbn.ddl.options.ui;

import com.dci.intellij.dbn.common.options.ui.CompositeConfigurationEditorForm;
import com.dci.intellij.dbn.ddl.options.DDLFileSettings;

import javax.swing.*;
import java.awt.*;


public class DDFileSettingsForm extends CompositeConfigurationEditorForm<DDLFileSettings> {
    private JPanel mainPanel;
    private JPanel extensionSettingsPanel;
    private JPanel generalSettingsPanel;

    public DDFileSettingsForm(DDLFileSettings settings) {
        super(settings);
        extensionSettingsPanel.add(settings.getExtensionSettings().createComponent(), BorderLayout.CENTER);
        generalSettingsPanel.add(settings.getGeneralSettings().createComponent(), BorderLayout.CENTER);
    }

    public JPanel getComponent() {
        return mainPanel;
    }
}
