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

package com.dci.intellij.dbn.execution.method.ui;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.common.ui.dialog.DBNDialog;
import com.dci.intellij.dbn.execution.method.MethodExecutionInput;
import org.jetbrains.annotations.Nullable;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import java.awt.event.ActionEvent;

public class MethodExecutionDialog extends DBNDialog {
    private MethodExecutionForm mainComponent;
    private boolean debug;

    public MethodExecutionDialog(MethodExecutionInput executionInput, boolean debug) {
        super(executionInput.getMethod().getProject(), (debug ? "Debug" : "Execute") + " Method", true);
        this.debug = debug;
        setModal(true);
        setResizable(true);
        mainComponent = new MethodExecutionForm(executionInput, true, debug);
        init();
    }

    protected String getDimensionServiceKey() {
        return "DBNavigator.MethodExecution";
    }

    protected final Action[] createActions() {
        return new Action[]{
                new ExecuteAction(),
                getCancelAction(),
                getHelpAction()
        };
    }

    @Override
    protected void doOKAction() {
        super.doOKAction();
    }

    private class ExecuteAction extends AbstractAction {
        public ExecuteAction() {
            super(debug ? "Debug" : "Execute",
                   debug ? Icons.METHOD_EXECUTION_DEBUG : Icons.METHOD_EXECUTION_RUN);
            //putValue(DEFAULT_ACTION, Boolean.FALSE);
        }

        public void actionPerformed(ActionEvent e) {
            mainComponent.updateExecutionInput();
            doOKAction();
        }
    }

    @Nullable
    protected JComponent createCenterPanel() {
        return mainComponent.getComponent();
    }

}
