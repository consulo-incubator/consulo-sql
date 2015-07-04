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

import com.dci.intellij.dbn.object.common.DBObject;
import com.dci.intellij.dbn.object.common.list.DBObjectNavigationList;
import com.intellij.openapi.actionSystem.AnAction;

import java.util.List;

public class ObjectLazyNavigationListAction extends ObjectListShowAction {
    private DBObjectNavigationList navigationList;
    private DBObject parentObject;

    public ObjectLazyNavigationListAction(DBObject parentObject, DBObjectNavigationList navigationList) {
        super(navigationList.getName() + "...", parentObject);
        this.navigationList = navigationList;
        this.parentObject = parentObject;
    }

    public List<DBObject> getObjectList() {
        List<DBObject> objects = navigationList.getObjects();
        if (objects == null) objects = navigationList.getObjectsProvider().getObjects();
        return objects;
    }

    public String getTitle() {
        return navigationList.getName();
    }

    public String getEmptyListMessage() {
        return "No " + navigationList.getName() + " found";
    }

    public String getListName() {
        return navigationList.getName();
    }

/*    @Override
    public void actionPerformed(final AnActionEvent e) {
        new BackgroundTask(parentObject.getProject(), "Loading " + navigationList.getName(), false, true) {
            @Override
            public void execute(@NotNull ProgressIndicator progressIndicator) {
                final ObjectNavigationListActionGroup linksActionGroup =
                        new ObjectNavigationListActionGroup(parentObject, navigationList, true);

                new SimpleLaterInvocator() {
                    public void run() {
                        ListPopup popup = JBPopupFactory.getInstance().createActionGroupPopup(
                                navigationList.getName(),
                                linksActionGroup,
                                e.getDataContext(),
                                JBPopupFactory.ActionSelectionAid.SPEEDSEARCH,
                                true, null, 10);

                        Project project = ActionUtil.getProject();
                        popup.showInCenterOf(DatabaseBrowserManager.getInstance(project).getBrowserPanel().getTree());
                    }
                }.start();
            }
        }.start();
    }*/

    protected AnAction createObjectAction(DBObject object) {
        return new NavigateToObjectAction(parentObject, object);
    }
}
