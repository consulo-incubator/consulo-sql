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

package com.dci.intellij.dbn.code.common.completion.options.ui;

import com.dci.intellij.dbn.code.common.completion.options.CodeCompletionSettings;
import com.dci.intellij.dbn.common.options.ui.CompositeConfigurationEditorForm;

import javax.swing.*;
import java.awt.*;

public class CodeCompletionSettingsForm extends CompositeConfigurationEditorForm<CodeCompletionSettings> {
    private JPanel mainPanel;
    private JPanel filterPanel;
    private JPanel sortingPanel;

    public CodeCompletionSettingsForm(CodeCompletionSettings codeCompletionSettings) {
        super(codeCompletionSettings);

        filterPanel.add(codeCompletionSettings.getFilterSettings().createComponent(), BorderLayout.CENTER);
        sortingPanel.add(codeCompletionSettings.getSortingSettings().createComponent(), BorderLayout.CENTER);
    }

    public JPanel getComponent() {
        return mainPanel;
    }
}
