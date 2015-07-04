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

package com.dci.intellij.dbn.browser.ui;

import com.dci.intellij.dbn.browser.model.BrowserTreeNode;
import com.dci.intellij.dbn.common.ui.DBNFormImpl;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

public abstract class DatabaseBrowserForm extends DBNFormImpl {
    private Project project;

    protected DatabaseBrowserForm(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    @Nullable
    public abstract DatabaseBrowserTree getBrowserTree();

    public abstract void selectElement(BrowserTreeNode treeNode, boolean requestFocus);

    public abstract void updateTree();

    public abstract void rebuild();

    @Override
    public void dispose() {
        project = null;
    }
}
