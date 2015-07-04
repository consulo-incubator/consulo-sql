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

package com.dci.intellij.dbn.browser.cache;

import com.dci.intellij.dbn.connection.ProjectConnectionBundle;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class ProjectBrowserCacheComponent extends BrowserCacheComponent implements ProjectComponent {

    private ProjectBrowserCacheComponent(Project project) {
        super( ProjectBrowserCacheComponent.createProjectConfigFile(project),
               project.getComponent(ProjectConnectionBundle.class));
    }


    public static ProjectBrowserCacheComponent getInstance(Project project) {
        return project.getComponent(ProjectBrowserCacheComponent.class);
    }

    private static File createProjectConfigFile(Project project) {
        /*String projectFilePath = project.getProjectFilePath();
        if (projectFilePath != null) {
            int index = projectFilePath.lastIndexOf('.');
            if (index > -1) {
                String cacheFilePath = projectFilePath.substring(0, index) + FILE_EXTENSION;
                return new File(cacheFilePath);
            }
        }*/
        return null;
    }

    /***************************************
    *            ProjectComponent          *
    ****************************************/
    @NotNull
    public String getComponentName() {
        return "DBNavigator.Project.BrowserCacheComponent";
    }
    public void projectOpened() {}
    public void projectClosed() {}
    public void initComponent() {}
    public void disposeComponent() {}
}
