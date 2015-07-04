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

package com.dci.intellij.dbn.code.psql.style.options.ui;

import com.dci.intellij.dbn.code.common.style.options.CodeStyleCustomSettings;
import com.dci.intellij.dbn.code.psql.style.options.PSQLCodeStyleSettings;
import com.dci.intellij.dbn.common.options.ui.CompositeConfigurationEditorForm;

import javax.swing.JPanel;
import java.awt.BorderLayout;

public class PSQLCodeStyleSettingsEditorForm extends CompositeConfigurationEditorForm<CodeStyleCustomSettings> {
    private JPanel mainPanel;
    private JPanel casePanel;
    private JPanel previewPanel;
    private JPanel attributesPanel;

    public PSQLCodeStyleSettingsEditorForm(PSQLCodeStyleSettings settings) {
        super(settings);
        casePanel.add(settings.getCaseSettings().createComponent(), BorderLayout.CENTER);
        attributesPanel.add(settings.getFormattingSettings().createComponent(), BorderLayout.CENTER);
        updateBorderTitleForeground(previewPanel);
    }

    public JPanel getComponent() {
        return mainPanel;
    }
}