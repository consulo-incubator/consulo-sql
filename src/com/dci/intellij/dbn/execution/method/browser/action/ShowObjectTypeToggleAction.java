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

package com.dci.intellij.dbn.execution.method.browser.action;

import com.dci.intellij.dbn.execution.method.browser.ui.MethodExecutionBrowserForm;
import com.dci.intellij.dbn.object.common.DBObjectType;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;

public class ShowObjectTypeToggleAction extends ToggleAction {
    private MethodExecutionBrowserForm browserComponent;
    private DBObjectType objectType;

    public ShowObjectTypeToggleAction(MethodExecutionBrowserForm browserComponent, DBObjectType objectType) {
        super("Show " + objectType.getListName(), null, objectType.getIcon());
        this.objectType = objectType;
        this.browserComponent = browserComponent;
    }

    @Override
    public boolean isSelected(AnActionEvent e) {
        return browserComponent.getSettings().getObjectVisibility(objectType);
    }

    @Override
    public void setSelected(AnActionEvent e, boolean state) {
        browserComponent.setObjectsVisible(objectType, state);
    }
}
