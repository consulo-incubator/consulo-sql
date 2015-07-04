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

package com.dci.intellij.dbn.object.common;

import com.dci.intellij.dbn.common.content.DynamicContentType;

public enum DBObjectRelationType implements DynamicContentType {
    CONSTRAINT_COLUMN(DBObjectType.CONSTRAINT, DBObjectType.COLUMN),
    INDEX_COLUMN(DBObjectType.INDEX, DBObjectType.COLUMN),
    USER_ROLE(DBObjectType.USER, DBObjectType.GRANTED_ROLE),
    USER_PRIVILEGE(DBObjectType.USER, DBObjectType.GRANTED_PRIVILEGE),
    ROLE_PRIVILEGE(DBObjectType.ROLE, DBObjectType.GRANTED_PRIVILEGE),
    ROLE_ROLE(DBObjectType.ROLE, DBObjectType.GRANTED_ROLE); 

    private DBObjectType sourceType;
    private DBObjectType targetType;

    DBObjectRelationType(DBObjectType sourceType, DBObjectType targetType) {
        this.sourceType = sourceType;
        this.targetType = targetType;
    }

    public DBObjectType getSourceType() {
        return sourceType;
    }

    public DBObjectType getTargetType() {
        return targetType;
    }
}
