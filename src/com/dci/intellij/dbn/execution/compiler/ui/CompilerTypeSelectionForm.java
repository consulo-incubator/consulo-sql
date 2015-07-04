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

package com.dci.intellij.dbn.execution.compiler.ui;

import com.dci.intellij.dbn.common.ui.DBNForm;
import com.dci.intellij.dbn.common.ui.DBNFormImpl;
import com.dci.intellij.dbn.common.ui.DBNHeaderForm;
import com.dci.intellij.dbn.common.util.StringUtil;
import com.dci.intellij.dbn.object.common.DBSchemaObject;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;

public class CompilerTypeSelectionForm extends DBNFormImpl implements DBNForm {
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JCheckBox rememberSelectionCheckBox;
    private JTextArea hintTextArea;

    public CompilerTypeSelectionForm(@Nullable DBSchemaObject object) {
        if (object == null) {
            headerPanel.setVisible(false);
        } else {
            String headerTitle = object.getQualifiedName();
            Icon headerIcon = object.getIcon();
            Color headerBackground = UIUtil.getPanelBackground();
            if (getEnvironmentSettings(object.getProject()).getVisibilitySettings().getDialogHeaders().value()) {
                headerBackground = object.getEnvironmentType().getColor();
            }
            DBNHeaderForm headerForm = new DBNHeaderForm(
                    headerTitle,
                    headerIcon,
                    headerBackground);
            headerPanel.add(headerForm.getComponent(), BorderLayout.CENTER);
        }
        hintTextArea.setFont(mainPanel.getFont());
        hintTextArea.setBackground(mainPanel.getBackground());
        hintTextArea.setText(StringUtil.wrap(
                "The compile option type \"Debug\" enables you to use the selected object(s) in debugging activities (i.e. pause/trace execution). " +
                "For runtime performance reasons, it is recommended to use normal compile option, unless you plan to debug the selected element(s)." +
                "\"Keep current\" will carry over the existing compile type.\n\n" +
                "Please select your compile option.", 90, ": ,."));
    }

    protected boolean rememberSelection() {
        return rememberSelectionCheckBox.isSelected();
    }

    public JComponent getComponent() {
        return mainPanel;
    }

    public void dispose() {
        super.dispose();
    }
}
