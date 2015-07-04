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
import com.intellij.openapi.Disposable;

public class ContentDependency implements Disposable {
    private GenericDatabaseElement sourceContentOwner;
    private DynamicContentType sourceContentType;
    private DynamicContent sourceContent;
    protected long changeTimestamp;

    public ContentDependency(DynamicContent sourceContent) {
        this.sourceContent = sourceContent;
        reset();
    }

    public ContentDependency(GenericDatabaseElement sourceContentOwner, DynamicContentType sourceContentType) {
        this.sourceContentOwner = sourceContentOwner;
        this.sourceContentType = sourceContentType;
        reset();
    }

    public DynamicContent getSourceContent() {
        return sourceContent != null ?  sourceContent :
                sourceContentOwner.getDynamicContent(sourceContentType);
    }

    public void reset() {
        changeTimestamp = getSourceContent().getChangeTimestamp();
    }

    public boolean isDirty() {
        return changeTimestamp != getSourceContent().getChangeTimestamp();
    }

    public void dispose() {
        sourceContent = null;
        sourceContentOwner = null;
    }
}
