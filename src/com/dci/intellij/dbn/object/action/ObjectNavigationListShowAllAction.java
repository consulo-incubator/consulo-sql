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

package com.dci.intellij.dbn.object.action;

import com.dci.intellij.dbn.browser.DatabaseBrowserManager;
import com.dci.intellij.dbn.browser.ui.DatabaseBrowserTree;
import com.dci.intellij.dbn.object.common.DBObject;
import com.dci.intellij.dbn.object.common.list.DBObjectNavigationList;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;

public class ObjectNavigationListShowAllAction extends AnAction {
    private DBObjectNavigationList navigationList;
    private DBObject parentObject;

    public ObjectNavigationListShowAllAction(DBObject parentObject, DBObjectNavigationList navigationList) {
        super("Show all...");
        this.parentObject = parentObject;
        this.navigationList = navigationList;
    }

    public void actionPerformed(AnActionEvent e) {
        ObjectNavigationListActionGroup navigationListActionGroup =
                new ObjectNavigationListActionGroup(parentObject, navigationList, true);

        ListPopup popup = JBPopupFactory.getInstance().createActionGroupPopup(
                "Dependencies",
                navigationListActionGroup,
                e.getDataContext(),
                JBPopupFactory.ActionSelectionAid.SPEEDSEARCH,
                true, null, 10);

        Project project = e.getData(PlatformDataKeys.PROJECT);
        DatabaseBrowserManager browserManager = DatabaseBrowserManager.getInstance(project);
        DatabaseBrowserTree activeBrowserTree = browserManager.getActiveBrowserTree();
        if (activeBrowserTree != null) {
            popup.showInCenterOf(activeBrowserTree);
        }
        //popup.show(DatabaseBrowserComponent.getInstance(project).getBrowserPanel().getTree());
    }
}
