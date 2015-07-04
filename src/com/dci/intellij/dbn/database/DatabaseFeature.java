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

package com.dci.intellij.dbn.database;

public enum DatabaseFeature {
    OBJECT_REPLACING("Replacing existing objects via DDL"),
    OBJECT_DEPENDENCIES("Object dependencies"),
    OBJECT_DDL_EXTRACTION("Object DDL extraction"),
    OBJECT_INVALIDATION("Object invalidation"),
    OBJECT_DISABLING("Disabling objects"),
    AUTHID_METHOD_EXECUTION("AUDHID method execution (execution on different schema)"),
    FUNCTION_OUT_ARGUMENTS("OUT arguments for functions"),
    DEBUGGING("Program execution debugging");

    private String description;

    DatabaseFeature(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
