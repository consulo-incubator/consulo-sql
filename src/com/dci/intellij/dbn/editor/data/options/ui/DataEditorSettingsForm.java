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

package com.dci.intellij.dbn.editor.data.options.ui;

import com.dci.intellij.dbn.common.options.ui.CompositeConfigurationEditorForm;
import com.dci.intellij.dbn.editor.data.options.DataEditorSettings;

import javax.swing.JPanel;
import java.awt.BorderLayout;

public class DataEditorSettingsForm extends CompositeConfigurationEditorForm<DataEditorSettings> {
    private JPanel mainPanel;
    private JPanel textEditorAutopopupPanel;
    private JPanel generalPanel;
    private JPanel filtersPanel;
    private JPanel valuesListPopupPanel;
    private JPanel lobContentTypesPanel;
    private JPanel recordNavigationPanel;

    public DataEditorSettingsForm(DataEditorSettings settings) {
        super(settings);
        textEditorAutopopupPanel.add(settings.getPopupSettings().createComponent(), BorderLayout.CENTER);
        valuesListPopupPanel.add(settings.getValueListPopupSettings().createComponent(), BorderLayout.CENTER);
        generalPanel.add(settings.getGeneralSettings().createComponent(), BorderLayout.CENTER);
        filtersPanel.add(settings.getFilterSettings().createComponent(), BorderLayout.CENTER);
        lobContentTypesPanel.add(settings.getQualifiedEditorSettings().createComponent(), BorderLayout.CENTER);
        recordNavigationPanel.add(settings.getRecordNavigationSettings().createComponent(), BorderLayout.CENTER);
        resetChanges();
    }


    public JPanel getComponent() {
        return mainPanel;
    }
}
