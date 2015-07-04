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

package com.dci.intellij.dbn.language.common.navigation;

import com.dci.intellij.dbn.editor.code.SourceCodeEditorManager;
import com.dci.intellij.dbn.language.common.psi.BasePsiElement;
import com.dci.intellij.dbn.object.common.DBObject;
import com.dci.intellij.dbn.object.common.DBSchemaObject;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import javax.swing.Icon;

public abstract class NavigationAction extends AnAction {
    private BasePsiElement navigationElement;
    private DBObject parentObject;

    protected NavigationAction(String text, Icon icon, DBObject parentObject, BasePsiElement navigationElement) {
        super(text, null, icon);
        this.parentObject = parentObject;
        this.navigationElement = navigationElement;
    }

    public DBObject getParentObject() {
        return parentObject;
    }

    public void actionPerformed(AnActionEvent e) {
        navigate();
    }

    public void navigate() {
        if (parentObject != null) {
            SourceCodeEditorManager codeEditorManager = SourceCodeEditorManager.getInstance(parentObject.getProject());
            codeEditorManager.navigateToObject((DBSchemaObject) parentObject, navigationElement);
        } else {
            navigationElement.navigate(true);
        }
    }
}
