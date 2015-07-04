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

package com.dci.intellij.dbn.database.oracle;

import com.dci.intellij.dbn.database.DatabaseCompatibilityInterface;
import com.dci.intellij.dbn.database.DatabaseFeature;
import com.dci.intellij.dbn.database.DatabaseObjectTypeId;

public class OracleCompatibilityInterface extends DatabaseCompatibilityInterface {

    public boolean supportsObjectType(DatabaseObjectTypeId objectTypeId) {
        return objectTypeId != DatabaseObjectTypeId.CHARSET;
    }

    public boolean supportsFeature(DatabaseFeature feature) {
        switch (feature) {
            case OBJECT_INVALIDATION: return true;
            case OBJECT_DEPENDENCIES: return true;
            case OBJECT_REPLACING: return true;
            case OBJECT_DDL_EXTRACTION: return true;
            case OBJECT_DISABLING: return true;
            case AUTHID_METHOD_EXECUTION: return true;
            case FUNCTION_OUT_ARGUMENTS: return true;
            case DEBUGGING: return true;
            default: return false;
        }
    }

    public char getIdentifierQuotes() {
        return '"';
    }
}