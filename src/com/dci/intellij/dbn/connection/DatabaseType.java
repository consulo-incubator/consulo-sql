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

package com.dci.intellij.dbn.connection;

public enum DatabaseType {
    ORACLE("ORACLE", "Oracle"),
    MYSQL("MYSQL", "MySQL"),
    UNKNOWN("UNKNOWN", "Unknown Database");

    private String name;
    private String displayName;

    DatabaseType(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static DatabaseType get(String name) {
        for (DatabaseType databaseType : values()) {
            if (name.equalsIgnoreCase(databaseType.getName())) return databaseType;
        }
        return null;
    }
}
