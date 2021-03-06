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
import com.intellij.openapi.Disposable;

public interface ContentDependencyAdapter extends Disposable {

    /**
     * This method is called every time the content is being queried.
     * Typical implementation would be to check if dependencies have changed.
     */
    boolean shouldLoad();

    /**
     * This method is typically called when the dynamic content is dirty and
     * the system tries to reload it.
     * e.g. one basic condition for reloading dirty content is valid connectivity
     */
    boolean shouldLoadIfDirty();

    boolean hasDirtyDependencies();

    /**
     * This operation is triggered before loading the dynamic content is started.
     * It can be implemented by the adapters to load non-weak dependencies for example.
     */
    void beforeLoad();

    /**
     * This operation is triggered after the loading of the dynamic content.
     */
    void afterLoad();

    void beforeReload(DynamicContent dynamicContent);

    void afterReload(DynamicContent dynamicContent);

    boolean isSourceContentLoaded();

    boolean isSubContent();
}
