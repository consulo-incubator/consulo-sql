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

package com.dci.intellij.dbn.data.find.action;

import com.dci.intellij.dbn.common.ui.DBNCheckboxAction;
import com.dci.intellij.dbn.data.find.DataSearchComponent;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.DumbAware;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

public abstract class DataSearchHeaderToggleAction extends DBNCheckboxAction implements DumbAware {

    @Override
    public boolean displayTextInToolbar() {
        return true;
    }

    public DataSearchComponent getEditorSearchComponent() {
        return searchComponent;
    }

    @Override
    public void update(AnActionEvent e) {
        super.update(e);
    }

    @Override
    public JComponent createCustomComponent(Presentation presentation) {
        final JComponent customComponent = super.createCustomComponent(presentation);
        if (customComponent instanceof JCheckBox) {
            JCheckBox checkBox = (JCheckBox) customComponent;
            checkBox.setFocusable(false);
            checkBox.setOpaque(false);
        }
        return customComponent;
    }

    private DataSearchComponent searchComponent;

    protected DataSearchHeaderToggleAction(DataSearchComponent searchComponent, String text) {
        super(text);
        this.searchComponent = searchComponent;
    }
}
