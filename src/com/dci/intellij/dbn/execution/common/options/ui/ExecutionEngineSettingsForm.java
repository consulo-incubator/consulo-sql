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

package com.dci.intellij.dbn.execution.common.options.ui;

import com.dci.intellij.dbn.common.options.ui.CompositeConfigurationEditorForm;
import com.dci.intellij.dbn.execution.common.options.ExecutionEngineSettings;

import javax.swing.*;
import java.awt.*;

public class ExecutionEngineSettingsForm extends CompositeConfigurationEditorForm<ExecutionEngineSettings> {
    private JPanel mainPanel;
    private JPanel queryExecutionPanel;
    private JPanel compilerPanel;

    public ExecutionEngineSettingsForm(ExecutionEngineSettings settings) {
        super(settings);
        queryExecutionPanel.add(settings.getStatementExecutionSettings().createComponent(), BorderLayout.CENTER);
        compilerPanel.add(settings.getCompilerSettings().createComponent(), BorderLayout.CENTER);
    }

    public JPanel getComponent() {
        return mainPanel;
    }
}
