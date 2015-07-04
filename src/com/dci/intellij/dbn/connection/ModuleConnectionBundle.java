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

package com.dci.intellij.dbn.connection;

import javax.swing.Icon;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import com.dci.intellij.dbn.common.Icons;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleComponent;
import com.intellij.openapi.options.Configurable;

public class ModuleConnectionBundle extends ConnectionBundle implements ModuleComponent, Configurable {

    private Module module;
    public ModuleConnectionBundle(Module module) {
        super(module == null ? null : module.getProject());
        this.module = module;
    }

    public static ModuleConnectionBundle getInstance(Module module) {
        return module.getComponent(ModuleConnectionBundle.class);
    }

    public Module getModule() {
        return module;
    }

    public Icon getIcon(int flags) {
        return AllIcons.Nodes.Module;
    }

    /***************************************
    *            Configurable              *
    ****************************************/
    @NotNull
    @Override
    public String getId() {
        return "DBNavigator.Module.ConnectionBundle";
    }

    @Nls
    public String getDisplayName() {
        return "DB Connections";
    }

    public Icon getIcon() {
        return Icons.CONNECTIONS;
    }

    /***************************************
    *            ModuleComponent           *
    ****************************************/
    @NotNull
    @NonNls
    public String getComponentName() {
        return "DBNavigator.Module.ConnectionManager";
    }

    public void projectOpened() {}
    public void projectClosed() {}
    public void moduleAdded() {}
    public void initComponent() {}
    public void disposeComponent() {
        dispose();
    }


    @Override
    public String toString() {
        return "ModuleConnectionBundle[" + getModule().getName() + "]";
    }

    public int compareTo(Object o) {
        if (o instanceof ModuleConnectionBundle) {
            ModuleConnectionBundle connectionManager = (ModuleConnectionBundle) o;
            return getModule().getName().compareTo(connectionManager.getModule().getName());
        }
        return -1;
    }
}
