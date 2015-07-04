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


import com.dci.intellij.dbn.data.find.DataSearchComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.CustomShortcutSet;
import com.intellij.openapi.actionSystem.Shortcut;

import javax.swing.JComponent;
import java.util.Set;

public abstract class DataSearchHeaderAction extends AnAction {
    private DataSearchComponent searchComponent;

    protected static void registerShortcutsToComponent(Set<Shortcut> shortcuts, AnAction action, JComponent component) {
        CustomShortcutSet shortcutSet = new CustomShortcutSet(shortcuts.toArray(new Shortcut[shortcuts.size()]));
        action.registerCustomShortcutSet(shortcutSet, component);
    }

    public DataSearchComponent getSearchComponent() {
        return searchComponent;
    }

    protected DataSearchHeaderAction(DataSearchComponent searchComponent) {
        this.searchComponent = searchComponent;
    }
}

