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

import com.dci.intellij.dbn.common.options.ui.ConfigurationEditorForm;
import com.dci.intellij.dbn.ddl.options.DDLFileGeneralSettings;
import com.intellij.openapi.options.ConfigurationException;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DDLFileGeneralSettingsForm extends ConfigurationEditorForm<DDLFileGeneralSettings> {
    private JPanel mainPanel;
    private JTextField statementPostfixTextField;
    private JCheckBox lookupDDLFilesCheckBox;
    private JCheckBox createDDLFileCheckBox;

    public DDLFileGeneralSettingsForm(DDLFileGeneralSettings settings) {
        super(settings);
        updateBorderTitleForeground(mainPanel);
        resetChanges();
        registerComponent(statementPostfixTextField);
        registerComponent(lookupDDLFilesCheckBox);
        registerComponent(createDDLFileCheckBox);
    }

    public JPanel getComponent() {
        return mainPanel;
    }

    public void applyChanges() throws ConfigurationException {
        DDLFileGeneralSettings settings = getConfiguration();
        settings.getStatementPostfix().applyChanges(statementPostfixTextField);
        settings.getLookupDDLFilesEnabled().applyChanges(lookupDDLFilesCheckBox);
        settings.getCreateDDLFilesEnabled().applyChanges(createDDLFileCheckBox);
    }

    public void resetChanges() {
        DDLFileGeneralSettings settings = getConfiguration();
        settings.getStatementPostfix().resetChanges(statementPostfixTextField);
        settings.getLookupDDLFilesEnabled().resetChanges(lookupDDLFilesCheckBox);
        settings.getCreateDDLFilesEnabled().resetChanges(createDDLFileCheckBox);
    }
}
