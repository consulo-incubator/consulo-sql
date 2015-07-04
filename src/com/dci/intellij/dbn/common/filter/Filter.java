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

package com.dci.intellij.dbn.common.filter;

import java.util.List;

public abstract class Filter<T> {
    public static final Filter NO_FILTER = new Filter() {
        @Override
        public boolean accepts(Object object) {
            return true;
        }

        @Override
        public boolean acceptsAll(List objects) {
            return true;
        }
    };

    public abstract boolean accepts(T object);
    public boolean acceptsAll(List<T> objects) {
        for (T object : objects) {
            if (!accepts(object)) return false;
        }
        return true;
    }
}
