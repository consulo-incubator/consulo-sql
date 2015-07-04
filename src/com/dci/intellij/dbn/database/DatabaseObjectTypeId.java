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


public enum DatabaseObjectTypeId {
    ARGUMENT(),
    CATEGORY(),
    CHARSET(),
    CLUSTER(),
    COLUMN(),
    CONSTRAINT(),
    DATABASE(),
    DATASET(),
    DBLINK(),
    DIMENSION(),
    DIMENSION_ATTRIBUTE(),
    DIMENSION_HIERARCHY(),
    DIMENSION_LEVEL(),
    DISKGROUP(),
    DOMAIN(),
    FUNCTION(),
    GRANTED_PRIVILEGE(),
    GRANTED_ROLE(),
    INDEX(),
    INDEXTYPE(),
    JAVA_OBJECT(),
    LOB(),
    MATERIALIZED_VIEW(),
    METHOD(),
    MODEL(),
    NESTED_TABLE(),
    NESTED_TABLE_COLUMN(),
    OPERATOR(),
    OUTLINE(),
    PACKAGE(),
    PACKAGE_FUNCTION(),
    PACKAGE_PROCEDURE(),
    PACKAGE_TYPE(),
    PACKAGE_BODY(),
    PARTITION(),
    PRIVILEGE(),
    PROCEDURE(),
    PROFILE(),
    ROLLBACK_SEGMENT(),
    ROLE(),
    SCHEMA(),
    SEQUENCE(),
    SUBPARTITION(),
    SYNONYM(),
    TABLE(),
    TABLESPACE(),
    TRIGGER(),
    TYPE(),
    TYPE_ATTRIBUTE(),
    TYPE_FUNCTION(),
    TYPE_PROCEDURE(),
    USER(),
    VARRAY(),
    VARRAY_TYPE(),
    VIEW(),


    CURSOR(),
    PARAMETER(),
    LABEL(),
    SAVEPOINT(),
    EXCEPTION(),


    UNKNOWN(),
    NONE(),
    ANY;
}
