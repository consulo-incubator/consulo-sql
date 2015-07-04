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
import com.dci.intellij.dbn.common.content.DynamicContentType;
import com.dci.intellij.dbn.connection.GenericDatabaseElement;

public class SubcontentDependencyAdapterImpl extends BasicDependencyAdapter implements SubcontentDependencyAdapter {
    private ContentDependency contentDependency;
    private boolean isDisposed;

    public SubcontentDependencyAdapterImpl(GenericDatabaseElement sourceContentOwner, DynamicContentType sourceContentType) {
        super(sourceContentOwner.getConnectionHandler());
        contentDependency = new ContentDependency(sourceContentOwner, sourceContentType);
    }


    public DynamicContent getSourceContent() {
        return contentDependency.getSourceContent();
    }

    @Override
    public boolean shouldLoad() {
        DynamicContent sourceContent = contentDependency.getSourceContent();
        if (!isDisposed && sourceContent.shouldLoad()) {
            sourceContent.load();
        }
        // should reload if the source has been reloaded and is not dirty
        return !sourceContent.isDirty() && contentDependency.isDirty();
    }

    @Override
    public boolean shouldLoadIfDirty() {
        DynamicContent sourceContent = contentDependency.getSourceContent();

        boolean isLoadedAndNotDirty = isConnectionValid() && sourceContent.isLoaded() && !sourceContent.isDirty();
        boolean shouldReloadAndIsNotLoading = !sourceContent.isLoading() && sourceContent.shouldLoad();

        return isLoadedAndNotDirty || shouldReloadAndIsNotLoading;
    }

    public boolean hasDirtyDependencies() {
        DynamicContent sourceContent = contentDependency.getSourceContent();
        return sourceContent.isLoading() || sourceContent.isDirty();
    }

    @Override
    public void beforeLoad() {
        contentDependency.getSourceContent().load();
    }

    @Override
    public void afterLoad() {
        contentDependency.reset();
    }

    public void beforeReload(DynamicContent dynamicContent) {
        DynamicContent sourceContent = contentDependency.getSourceContent();
        sourceContent.getDependencyAdapter().beforeReload(sourceContent);
        sourceContent.removeElements(dynamicContent.getElements());
    }

    public void afterReload(DynamicContent dynamicContent) {
        DynamicContent sourceContent = contentDependency.getSourceContent();
        if (sourceContent.getClass().isAssignableFrom(dynamicContent.getClass())) {
            sourceContent.addElements(dynamicContent.getElements());
            sourceContent.getDependencyAdapter().afterReload(sourceContent);
            sourceContent.updateChangeTimestamp();
            contentDependency.reset();
        }
    }

    @Override
    public boolean isSourceContentLoaded() {
        return getSourceContent().isLoaded();
    }

    @Override
    public boolean isSubContent() {
        return true;
    }

    public void dispose() {
        if (!isDisposed) {
            isDisposed = true;
            contentDependency.dispose();
            contentDependency = null;
            super.dispose();
        }
    }


}
