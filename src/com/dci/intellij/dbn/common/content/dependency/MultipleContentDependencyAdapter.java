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

package com.dci.intellij.dbn.common.content.dependency;

import com.dci.intellij.dbn.common.content.DynamicContent;
import com.dci.intellij.dbn.common.dispose.DisposeUtil;
import com.dci.intellij.dbn.connection.ConnectionHandler;

import java.util.HashSet;
import java.util.Set;

public class MultipleContentDependencyAdapter extends BasicDependencyAdapter implements ContentDependencyAdapter {
    private Set<ContentDependency> dependencies = new HashSet<ContentDependency>();
    private boolean isDisposed;

    public MultipleContentDependencyAdapter(ConnectionHandler connectionHandler, DynamicContent... sourceContents) {
        super(connectionHandler);
        for (DynamicContent sourceContent : sourceContents) {
            dependencies.add(new ContentDependency(sourceContent));
        }
    }

    public boolean shouldLoad() {
        // should reload if at least one dependency has been reloaded and is not dirty
        for (ContentDependency dependency : dependencies) {
            if (dependency.isDirty() && !dependency.getSourceContent().isDirty()) {
                return true;
            }
        }
        return false;
    }

    public boolean shouldLoadIfDirty() {
        if (isConnectionValid()) {
            for (ContentDependency dependency : dependencies) {
                DynamicContent dynamicContent = dependency.getSourceContent();
                if (!dynamicContent.isLoaded() || dynamicContent.isDirty()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean hasDirtyDependencies() {
        for (ContentDependency dependency : dependencies) {
            if (dependency.getSourceContent().isDirty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void beforeLoad() {
        // assuming all dependencies are hard, load them first
        for (ContentDependency dependency : dependencies) {
            dependency.getSourceContent().load();
        }
    }

    @Override
    public void afterLoad() {
        for (ContentDependency dependency : dependencies) {
            dependency.reset();
        }
    }

    @Override
    public void beforeReload(DynamicContent dynamicContent) {
        beforeLoad();
    }

    @Override
    public void afterReload(DynamicContent dynamicContent) {
        afterLoad();
    }

    @Override
    public void dispose() {
        if (!isDisposed) {
            isDisposed = true;
            DisposeUtil.disposeCollection(dependencies);
            super.dispose();
        }
    }
}
