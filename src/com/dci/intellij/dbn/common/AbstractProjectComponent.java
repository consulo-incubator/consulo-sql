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

package com.dci.intellij.dbn.common;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;

public abstract class AbstractProjectComponent implements ProjectComponent{
    private Project project;
    private boolean isDisposed = false;

    protected AbstractProjectComponent(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    public void projectOpened() {
    }

    public void projectClosed() {
    }

    public void initComponent() {
    }

    public boolean isDisposed() {
        return isDisposed;
    }

    public void disposeComponent() {
        isDisposed = true;
        project = null;
    }
}
