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

package com.dci.intellij.dbn.execution.common.ui;

import com.intellij.openapi.actionSystem.*;
import com.intellij.ui.tabs.TabInfo;
import com.intellij.ui.tabs.impl.TabLabel;

public class ExecutionConsolePopupActionGroup extends DefaultActionGroup {
    private ExecutionConsoleForm executionConsoleForm;

    public ExecutionConsolePopupActionGroup(ExecutionConsoleForm executionConsoleForm) {
        this.executionConsoleForm = executionConsoleForm;
        add(close);
        add(closeAll);
        add(closeAllButThis);
    }

    private static TabInfo getTabInfo(AnActionEvent e) {
        DataContext dataContext = e.getDataContext();
        Object o = dataContext.getData(PlatformDataKeys.CONTEXT_COMPONENT.getName());
        if (o instanceof TabLabel) {
            TabLabel tabLabel = (TabLabel) o;
            return tabLabel.getInfo();
        }
        return null;
    }

    private AnAction close = new AnAction("Close") {
        @Override
        public void actionPerformed(AnActionEvent e) {
            TabInfo tabInfo = getTabInfo(e);
            if (tabInfo != null) {
                executionConsoleForm.removeTab(tabInfo);
            }
        }
    };

    private AnAction closeAll = new AnAction("Close All") {
        public void actionPerformed(AnActionEvent e) {
            executionConsoleForm.removeAllTabs();
        }
    };

    private AnAction closeAllButThis = new AnAction("Close All But This") {
        public void actionPerformed(AnActionEvent e) {
            TabInfo tabInfo = getTabInfo(e);
            if (tabInfo != null) {
                executionConsoleForm.removeAllExceptTab(tabInfo);
            }
        }
    };
}
