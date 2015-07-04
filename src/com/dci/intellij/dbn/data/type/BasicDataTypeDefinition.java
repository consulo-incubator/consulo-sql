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

package com.dci.intellij.dbn.data.type;

import java.util.List;

public class BasicDataTypeDefinition implements DataTypeDefinition {
    private BasicDataType basicDataType;
    private String name;
    private Class typeClass;
    private int sqlType;


    public BasicDataTypeDefinition(String name, Class typeClass, int sqlType, BasicDataType basicDataType, List<DataTypeDefinition> bundle) {
        this.name = name;
        this.typeClass = typeClass;
        this.sqlType = sqlType;
        this.basicDataType = basicDataType;
        bundle.add(this);
    }

    public String getName() {
        return name;
    }

    public Class getTypeClass() {
        return typeClass;
    }

    public int getSqlType() {
        return sqlType;
    }

    public BasicDataType getBasicDataType() {
        return basicDataType;
    }

    @Override
    public String toString() {
        return name;
    }

    public Object convert(Object object) {
        return object;
    }
}