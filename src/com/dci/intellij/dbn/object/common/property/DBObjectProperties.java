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

package com.dci.intellij.dbn.object.common.property;

import gnu.trove.THashSet;

import java.util.Set;

public class DBObjectProperties {
    private Set<DBObjectProperty> properties;

    public boolean is(DBObjectProperty property) {
        return properties != null && properties.contains(property);
    }

    public void set(DBObjectProperty property) {
        if (properties == null) {
            properties = new THashSet<DBObjectProperty>();
        }
        properties.add(property);
    }

    public void unset(DBObjectProperty property) {
        if (properties != null) {
            properties.remove(property);
            if (properties.isEmpty()) {
                properties = null;
            }
        }
    }
}
