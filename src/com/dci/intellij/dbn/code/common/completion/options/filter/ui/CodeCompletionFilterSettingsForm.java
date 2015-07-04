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

package com.dci.intellij.dbn.code.common.completion.options.filter.ui;

import com.dci.intellij.dbn.code.common.completion.options.filter.CodeCompletionFilterSettings;
import com.dci.intellij.dbn.common.options.ui.ConfigurationEditorForm;
import com.intellij.openapi.options.ConfigurationException;

import javax.swing.*;
import java.awt.*;

public class CodeCompletionFilterSettingsForm extends ConfigurationEditorForm<CodeCompletionFilterSettings> {
    private JPanel mainPanel;
    private CodeCompletionFilterTree tree;
    private CodeCompletionFilterTreeModel treeModel;

    public CodeCompletionFilterSettingsForm(CodeCompletionFilterSettings codeCompletionFilterSettings) {
        super(codeCompletionFilterSettings);
        treeModel = new CodeCompletionFilterTreeModel(codeCompletionFilterSettings);
        tree = new CodeCompletionFilterTree(treeModel);
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(tree, BorderLayout.CENTER);
    }

    public JPanel getComponent() {
        return mainPanel;
    }

    public void applyChanges() throws ConfigurationException {
        treeModel.applyChanges();
    }

    public void resetChanges() {
        treeModel.resetChanges();
    }
}
