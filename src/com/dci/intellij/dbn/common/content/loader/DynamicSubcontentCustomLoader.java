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

package com.dci.intellij.dbn.common.content.loader;

import com.dci.intellij.dbn.common.content.DynamicContent;
import com.dci.intellij.dbn.common.content.DynamicContentElement;
import com.dci.intellij.dbn.common.content.dependency.SubcontentDependencyAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class DynamicSubcontentCustomLoader<T extends DynamicContentElement> implements DynamicContentLoader<T> {
    public abstract T resolveElement(DynamicContent<T> dynamicContent, DynamicContentElement sourceElement);

    public void reloadContent(DynamicContent<T> dynamicContent) throws DynamicContentLoaderException {
        loadContent(dynamicContent);
    }

    public void loadContent(DynamicContent<T> dynamicContent) throws DynamicContentLoaderException {
        List<T> list = null;
        SubcontentDependencyAdapter dependencyAdapter = (SubcontentDependencyAdapter) dynamicContent.getDependencyAdapter();
        for (Object object : dependencyAdapter.getSourceContent().getElements()) {
            if (dynamicContent.isDisposed()) return;
            DynamicContentElement sourceElement = (DynamicContentElement) object;
            T element = resolveElement(dynamicContent, sourceElement);
            if (element != null && dynamicContent.accepts(element)) {
                if (list == null) list = new ArrayList<T>();
                list.add(element);
            }
        }
        dynamicContent.setElements(list);
    }
}
