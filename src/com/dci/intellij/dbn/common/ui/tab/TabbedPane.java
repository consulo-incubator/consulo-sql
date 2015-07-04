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

package com.dci.intellij.dbn.common.ui.tab;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.ui.tabs.TabInfo;
import com.intellij.ui.tabs.impl.JBTabsImpl;
import org.jetbrains.annotations.NotNull;

import javax.swing.JComponent;

public class TabbedPane extends JBTabsImpl {
    public TabbedPane(@NotNull Project project) {
        super(project);
    }

    public void select(JComponent component, boolean requestFocus) {
        TabInfo tabInfo = findInfo(component);
        if (tabInfo != null) {
            select(tabInfo, requestFocus);
        }
    }

    @Override
    public void dispose() {
        for (TabInfo tabInfo : getTabs()) {
            Object object = tabInfo.getObject();
            if (object instanceof Disposable) {
                Disposable disposable = (Disposable) object;
                disposable.dispose();
            }
        }
        super.dispose();
    }
}
