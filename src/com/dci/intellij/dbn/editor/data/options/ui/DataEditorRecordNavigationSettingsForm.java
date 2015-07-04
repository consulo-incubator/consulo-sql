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

import com.dci.intellij.dbn.common.options.ui.ConfigurationEditorForm;
import com.dci.intellij.dbn.data.record.navigation.RecordNavigationTarget;
import com.dci.intellij.dbn.editor.data.options.DataEditorRecordNavigationSettings;
import com.intellij.openapi.options.ConfigurationException;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class DataEditorRecordNavigationSettingsForm extends ConfigurationEditorForm<DataEditorRecordNavigationSettings> {
    private JPanel mainPanel;
    private JRadioButton viewerRadioButton;
    private JRadioButton editorRadioButton;
    private JRadioButton askRadioButton;


    public DataEditorRecordNavigationSettingsForm(DataEditorRecordNavigationSettings configuration) {
        super(configuration);
        updateBorderTitleForeground(mainPanel);
        resetChanges();

        registerComponent(viewerRadioButton);
        registerComponent(editorRadioButton);
        registerComponent(askRadioButton);
    }

    public JComponent getComponent() {
        return mainPanel;
    }

    public void applyChanges() throws ConfigurationException {
        DataEditorRecordNavigationSettings configuration = getConfiguration();

        RecordNavigationTarget navigationTarget =
                viewerRadioButton.isSelected() ? RecordNavigationTarget.VIEWER :
                editorRadioButton.isSelected() ? RecordNavigationTarget.EDITOR :
                askRadioButton.isSelected() ? RecordNavigationTarget.ASK :
                RecordNavigationTarget.VIEWER;
        configuration.setNavigationTarget(navigationTarget);
    }

    public void resetChanges() {
        DataEditorRecordNavigationSettings configuration = getConfiguration();
        RecordNavigationTarget navigationTarget = configuration.getNavigationTarget();
        if (navigationTarget == RecordNavigationTarget.VIEWER) viewerRadioButton.setSelected(true); else
        if (navigationTarget == RecordNavigationTarget.EDITOR) editorRadioButton.setSelected(true); else
        if (navigationTarget == RecordNavigationTarget.ASK) askRadioButton.setSelected(true);
    }
}
