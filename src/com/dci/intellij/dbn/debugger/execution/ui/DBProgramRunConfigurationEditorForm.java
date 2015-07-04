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

package com.dci.intellij.dbn.debugger.execution.ui;

import com.dci.intellij.dbn.common.dispose.DisposeUtil;
import com.dci.intellij.dbn.common.ui.DBNForm;
import com.dci.intellij.dbn.common.ui.DBNFormImpl;
import com.dci.intellij.dbn.debugger.execution.DBProgramRunConfiguration;
import com.dci.intellij.dbn.debugger.execution.action.OpenMethodBrowserAction;
import com.dci.intellij.dbn.debugger.execution.action.OpenMethodHistoryAction;
import com.dci.intellij.dbn.debugger.execution.action.SelectMethodAction;
import com.dci.intellij.dbn.execution.method.MethodExecutionInput;
import com.dci.intellij.dbn.execution.method.ui.MethodExecutionForm;
import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DBProgramRunConfigurationEditorForm extends DBNFormImpl implements DBNForm {
    private JPanel methodPanel;
    private JPanel mainPanel;
    private JPanel methodArgumentsPanel;
    private JCheckBox compileDependenciesCheckBox;
    private JLabel methodLabel;
    private JButton browseButton;
    private JLabel connectionLabel;

    private MethodExecutionForm methodExecutionForm;
    private MethodExecutionInput executionInput;

    private OpenMethodHistoryAction historyAction;
    private OpenMethodBrowserAction browserAction;

    public DBProgramRunConfigurationEditorForm(final DBProgramRunConfiguration configuration) {
        historyAction = new OpenMethodHistoryAction(configuration);
        browserAction = new OpenMethodBrowserAction(configuration);

        browseButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                DefaultActionGroup actionGroup = new DefaultActionGroup();
                actionGroup.add(historyAction);
                actionGroup.add(browserAction);
                if (configuration.getMethodSelectionHistory().size() > 0) {
                    actionGroup.addSeparator();
                    for (MethodExecutionInput methodExecutionInput : configuration.getMethodSelectionHistory()) {
                        if (!methodExecutionInput.equals(configuration.getExecutionInput())) {
                            actionGroup.add(new SelectMethodAction(methodExecutionInput, configuration));
                        }
                    }
                }

                ListPopup popup = JBPopupFactory.getInstance().createActionGroupPopup(
                        "Select method",
                        actionGroup,
                        DataManager.getInstance().getDataContext(getComponent()),
                        JBPopupFactory.ActionSelectionAid.SPEEDSEARCH,
                        true, null, 10);

                Point locationOnScreen = browseButton.getLocationOnScreen();
                Point location = new Point(
                        (int) (locationOnScreen.getX()),
                        (int) locationOnScreen.getY() + browseButton.getHeight());
                popup.showInScreenCoordinates(browseButton, location);
            }
        });
    }

    public JPanel getComponent() {
        return mainPanel;
    }

    public MethodExecutionInput getExecutionInput() {
        return executionInput;
    }

    public void writeConfiguration(DBProgramRunConfiguration configuration) {
        if (methodExecutionForm != null) {
            methodExecutionForm.setExecutionInput(configuration.getExecutionInput());
            methodExecutionForm.updateExecutionInput();
        }
        configuration.setCompileDependencies(compileDependenciesCheckBox.isSelected());
        //selectMethodAction.setConfiguration(configuration);
    }

    public void readConfiguration(DBProgramRunConfiguration configuration) {
        setExecutionInput(configuration.getExecutionInput(), false);
        compileDependenciesCheckBox.setSelected(configuration.isCompileDependencies());
    }

    public void setExecutionInput(MethodExecutionInput executionInput, boolean touchForm) {
        this.executionInput = executionInput;
        methodArgumentsPanel.removeAll();
        if (executionInput != null && executionInput.getMethod() != null) {
            methodExecutionForm = new MethodExecutionForm(executionInput, false, true);
            methodArgumentsPanel.add(methodExecutionForm.getComponent(), BorderLayout.CENTER);
            if (touchForm) methodExecutionForm.touch();
            methodLabel.setIcon(executionInput.getMethod().getOriginalIcon());
        } else {
            methodLabel.setIcon(null);
        }
        methodLabel.setText(executionInput == null ? "No method selected" : executionInput.getMethodIdentifier().getPath());


        mainPanel.updateUI();
    }

    public void dispose() {
        super.dispose();
        DisposeUtil.dispose(methodExecutionForm);
        methodExecutionForm = null;
        executionInput = null;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
