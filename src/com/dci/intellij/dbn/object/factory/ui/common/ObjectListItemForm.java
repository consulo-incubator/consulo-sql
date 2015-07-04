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

package com.dci.intellij.dbn.object.factory.ui.common;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.common.ui.DBNForm;
import com.dci.intellij.dbn.common.ui.DBNFormImpl;
import com.dci.intellij.dbn.common.util.ActionUtil;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import javax.swing.JPanel;
import java.awt.BorderLayout;

public class ObjectListItemForm extends DBNFormImpl implements DBNForm {
    private JPanel mainPanel;
    private JPanel removeActionPanel;
    private JPanel objectDetailsComponent;

    private ObjectListForm parent;
    private ObjectFactoryInputForm inputForm;

    public ObjectListItemForm(ObjectListForm parent, ObjectFactoryInputForm inputForm) {
        this.parent = parent;
        this.inputForm = inputForm;
        ActionToolbar actionToolbar = ActionUtil.createActionToolbar(
                "DBNavigator.ObjectFactory.AddElement", true,
                new RemoveObjectAction());
        removeActionPanel.add(actionToolbar.getComponent(), BorderLayout.NORTH);

    }

    public JPanel getComponent(){
        return mainPanel;
    }

    private void createUIComponents() {
        objectDetailsComponent = inputForm.getComponent();
    }

    public void dispose() {
        super.dispose();
        parent = null;
        inputForm = null;
    }


    public class RemoveObjectAction extends AnAction {
        public RemoveObjectAction() {
            super("Remove " + parent.getObjectType(), null, Icons.ACTION_DELETE);
        }

        public void actionPerformed(AnActionEvent e) {
            parent.removeObjectPanel(ObjectListItemForm.this);
        }
    }

    public ObjectFactoryInputForm getObjectDetailsPanel() {
        return inputForm;
    }
}
