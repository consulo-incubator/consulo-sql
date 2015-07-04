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

package com.dci.intellij.dbn.common.content;

import com.dci.intellij.dbn.common.content.dependency.BasicDependencyAdapter;
import com.dci.intellij.dbn.common.content.dependency.ContentDependencyAdapter;
import com.dci.intellij.dbn.common.content.loader.DynamicContentLoader;
import com.dci.intellij.dbn.common.filter.Filter;
import com.dci.intellij.dbn.connection.GenericDatabaseElement;
import com.intellij.openapi.project.Project;

public class SimpleDynamicContent<T extends DynamicContentElement> extends DynamicContentImpl<T> {
    private static ContentDependencyAdapter DEPENDENCY_ADAPTER = new BasicDependencyAdapter(null);

    public SimpleDynamicContent(GenericDatabaseElement parent, DynamicContentLoader<T> loader, boolean indexed) {
        super(parent, loader, DEPENDENCY_ADAPTER, indexed);
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public void notifyChangeListeners() {

    }

    public Project getProject() {
        return null;
    }

    public String getContentDescription() {
        return null;
    }

    public String getName() {
        return null;
    }
}
