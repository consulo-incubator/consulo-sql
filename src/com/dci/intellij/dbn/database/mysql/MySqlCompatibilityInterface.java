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

package com.dci.intellij.dbn.database.mysql;

import com.dci.intellij.dbn.database.DatabaseCompatibilityInterface;
import com.dci.intellij.dbn.database.DatabaseFeature;
import com.dci.intellij.dbn.database.DatabaseObjectTypeId;

public class MySqlCompatibilityInterface extends DatabaseCompatibilityInterface {

    public boolean supportsObjectType(DatabaseObjectTypeId objectTypeId) {
        return
            objectTypeId == DatabaseObjectTypeId.CHARSET ||
            objectTypeId == DatabaseObjectTypeId.USER ||
            objectTypeId == DatabaseObjectTypeId.SCHEMA ||
            objectTypeId == DatabaseObjectTypeId.TABLE ||
            objectTypeId == DatabaseObjectTypeId.VIEW ||
            objectTypeId == DatabaseObjectTypeId.COLUMN ||
            objectTypeId == DatabaseObjectTypeId.CONSTRAINT ||
            objectTypeId == DatabaseObjectTypeId.INDEX ||
            objectTypeId == DatabaseObjectTypeId.TRIGGER ||
            objectTypeId == DatabaseObjectTypeId.FUNCTION ||
            objectTypeId == DatabaseObjectTypeId.PROCEDURE ||
            objectTypeId == DatabaseObjectTypeId.ARGUMENT ||
            objectTypeId == DatabaseObjectTypeId.PRIVILEGE ||
            objectTypeId == DatabaseObjectTypeId.GRANTED_PRIVILEGE;
    }

    public boolean supportsFeature(DatabaseFeature feature) {
        switch (feature) {
            case OBJECT_INVALIDATION: return false;
            case OBJECT_DEPENDENCIES: return false;
            case OBJECT_REPLACING: return false;
            case OBJECT_DDL_EXTRACTION: return false;
            case OBJECT_DISABLING: return false;
            case AUTHID_METHOD_EXECUTION: return false;
            case FUNCTION_OUT_ARGUMENTS: return false;
            case DEBUGGING: return false;
            default: return false;
        }
    }

    public boolean supportsInvalidObjects() {
        return false;
    }

    public boolean supportsReplacingObjects() {
        return false;
    }

    public char getIdentifierQuotes() {
        return '`';
    }
}
