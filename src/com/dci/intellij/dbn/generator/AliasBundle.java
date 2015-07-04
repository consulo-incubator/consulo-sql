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

package com.dci.intellij.dbn.generator;

import com.dci.intellij.dbn.common.util.NamingUtil;
import com.dci.intellij.dbn.object.common.DBObject;

import java.util.HashMap;
import java.util.Map;

public class AliasBundle {
    private Map<DBObject, String> aliases = new HashMap<DBObject, String>();

    public String getAlias(DBObject object) {
        String alias = aliases.get(object);
        if (alias == null) {
            alias = NamingUtil.createAliasName(object.getName());
            alias = getNextAvailable(alias);
            aliases.put(object, alias);
        }
        return alias;
    }

    private String getNextAvailable(String alias) {
        for (String availableAlias : aliases.values()) {
            if (alias.equals(availableAlias)) {
                alias = NamingUtil.getNextNumberedName(alias, false);
            }
        }
        return alias;
    }

}
