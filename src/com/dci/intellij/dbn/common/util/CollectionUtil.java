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

package com.dci.intellij.dbn.common.util;

import java.util.Collection;
import java.util.Map;

public class CollectionUtil {
    public static <T extends Cloneable<T>> void cloneCollectionElements(Collection<T> source, Collection<T> target) {
        for (T cloneable : source) {
            T clone = cloneable.clone();
            target.add(clone);
        }
    }

    public static void clearCollection(Collection collection) {
        if (collection != null) {
            collection.clear();
        }
    }

    public static void clearMap(Map map) {
        if (map != null) {
            map.clear();
        }
    }

}
