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

package com.dci.intellij.dbn.execution.method.result.ui;

import com.dci.intellij.dbn.common.ui.DBNFormImpl;
import com.dci.intellij.dbn.common.ui.tab.TabbedPane;
import com.dci.intellij.dbn.common.util.ActionUtil;
import com.dci.intellij.dbn.execution.common.result.ui.ExecutionResultForm;
import com.dci.intellij.dbn.execution.method.ArgumentValue;
import com.dci.intellij.dbn.execution.method.result.MethodExecutionResult;
import com.dci.intellij.dbn.execution.method.result.action.CloseExecutionResultAction;
import com.dci.intellij.dbn.execution.method.result.action.EditMethodAction;
import com.dci.intellij.dbn.execution.method.result.action.OpenSettingsAction;
import com.dci.intellij.dbn.execution.method.result.action.PromptMethodExecutionAction;
import com.dci.intellij.dbn.execution.method.result.action.StartMethodExecutionAction;
import com.dci.intellij.dbn.object.DBArgument;
import com.dci.intellij.dbn.object.DBMethod;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.ui.GuiUtils;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.tabs.TabInfo;
import com.intellij.util.ui.tree.TreeUtil;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import java.awt.BorderLayout;
import java.util.List;

public class MethodExecutionResultForm extends DBNFormImpl implements ExecutionResultForm<MethodExecutionResult> {
    private JPanel mainPanel;
    private JPanel actionsPanel;
    private JPanel statusPanel;
    private JLabel connectionLabel;
    private JLabel durationLabel;
    private JPanel outputCursorsPanel;
    private JTree argumentValuesTree;
    private JPanel argumentValuesPanel;
    private TabbedPane cursorOutputTabs;


    private MethodExecutionResult executionResult;

    public MethodExecutionResultForm(MethodExecutionResult executionResult) {
        this.executionResult = executionResult;
        cursorOutputTabs = new TabbedPane(executionResult.getProject());
        createActionsPanel(executionResult);
        updateCursorArgumentsPanel();

        outputCursorsPanel.add(cursorOutputTabs, BorderLayout.CENTER);

        argumentValuesPanel.setBorder(IdeBorderFactory.createBorder());
        updateStatusBarLabels();
        GuiUtils.replaceJSplitPaneWithIDEASplitter(mainPanel);
        TreeUtil.expand(argumentValuesTree, 2);
    }

    public void setExecutionResult(MethodExecutionResult executionResult) {
        if (this.executionResult != executionResult) {
            this.executionResult = executionResult;
            rebuild();
        }
    }

    public MethodExecutionResult getExecutionResult() {
        return executionResult;
    }

    public DBMethod getMethod() {
        return executionResult.getMethod();
    }

    public void rebuild() {
        updateArgumentValueTables();
        updateCursorArgumentsPanel();
        updateStatusBarLabels();
    }

    private void updateArgumentValueTables() {
        List<ArgumentValue> inputArgumentValues = executionResult.getExecutionInput().getArgumentValues();
        List<ArgumentValue> outputArgumentValues = executionResult.getArgumentValues();

        DBMethod method = executionResult.getMethod();
        ArgumentValuesTreeModel treeModel = new ArgumentValuesTreeModel(method, inputArgumentValues, outputArgumentValues);
        argumentValuesTree.setModel(treeModel);
        TreeUtil.expand(argumentValuesTree, 2);
    }

    private void updateCursorArgumentsPanel() {
        cursorOutputTabs.removeAllTabs();
        for (ArgumentValue argumentValue : executionResult.getArgumentValues()) {
            if (argumentValue.isCursor()) {
                DBArgument argument = argumentValue.getArgument();

                MethodExecutionCursorResultForm cursorResultComponent =
                        new MethodExecutionCursorResultForm(executionResult, argument);

                TabInfo tabInfo = new TabInfo(cursorResultComponent.getComponent());
                tabInfo.setText(argument.getName());
                tabInfo.setIcon(argument.getIcon());
                tabInfo.setObject(argument);
                cursorOutputTabs.addTab(tabInfo);
            }
        }
        cursorOutputTabs.repaint();
    }

    public void selectCursorOutput(DBArgument argument) {
        for (TabInfo tabInfo : cursorOutputTabs.getTabs()) {
            if (tabInfo.getObject().equals(argument)) {
                cursorOutputTabs.select(tabInfo, true);
                break;
            }
        }

    }

    private void updateStatusBarLabels() {
        connectionLabel.setIcon(executionResult.getConnectionHandler().getIcon());
        connectionLabel.setText(executionResult.getConnectionHandler().getName());

        durationLabel.setText(": " + executionResult.getExecutionDuration() + " ms");
    }



    private void createActionsPanel(MethodExecutionResult executionResult) {
        ActionToolbar actionToolbar = ActionUtil.createActionToolbar(
                "DBNavigator.MethodExecutionResult.Controls", false,
                new CloseExecutionResultAction(executionResult),
                new EditMethodAction(executionResult),
                new StartMethodExecutionAction(executionResult),
                new PromptMethodExecutionAction(executionResult),
                ActionUtil.SEPARATOR,
                new OpenSettingsAction());
        actionsPanel.add(actionToolbar.getComponent());
    }

    public JPanel getComponent() {
        return mainPanel;
    }

    public void dispose() {
        super.dispose();
        executionResult.dispose();
        executionResult = null;
    }

    private void createUIComponents() {
        List<ArgumentValue> inputArgumentValues = executionResult.getExecutionInput().getArgumentValues();
        List<ArgumentValue> outputArgumentValues = executionResult.getArgumentValues();
        argumentValuesTree = new ArgumentValuesTree(this, inputArgumentValues, outputArgumentValues);
    }
}
