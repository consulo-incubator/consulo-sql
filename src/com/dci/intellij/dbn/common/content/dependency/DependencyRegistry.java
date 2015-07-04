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
import gnu.trove.THashMap;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DependencyRegistry {
    private Map<String, Set<DynamicContent>> registry = new THashMap<String, Set<DynamicContent>>();

    public void registerDependency(String dependencyKey, DynamicContent dynamicContent){
        Set<DynamicContent> dynamicContents = registry.get(dependencyKey);
        if (dynamicContents == null) {
            dynamicContents = new HashSet<DynamicContent>();
            registry.put(dependencyKey, dynamicContents);
        }
        dynamicContents.add(dynamicContent);
    }

    public void markContentsDirty(String dependencyKey) {
        Set<DynamicContent> dynamicContents = registry.get(dependencyKey);
        for (DynamicContent dynamicContent : dynamicContents) {
            dynamicContent.setDirty(true);
        }
    }
}
