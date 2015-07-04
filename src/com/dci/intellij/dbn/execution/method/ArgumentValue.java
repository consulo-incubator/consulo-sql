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

package com.dci.intellij.dbn.execution.method;

import com.dci.intellij.dbn.object.DBArgument;
import com.dci.intellij.dbn.object.DBTypeAttribute;

import java.sql.ResultSet;

public class ArgumentValue {
    private DBArgument argument;
    private DBTypeAttribute attribute;
    private Object value;

    public ArgumentValue(DBArgument argument, DBTypeAttribute attribute, Object value) {
        this.argument = argument;
        this.attribute = attribute;
        this.value = value;
    }

    public ArgumentValue(DBArgument argument, Object value) {
        this.argument = argument;
        this.value = value;
    }

    public DBArgument getArgument() {
        return argument;
    }

    public DBTypeAttribute getAttribute() {
        return attribute;
    }

    public String getName() {
        return
            attribute == null ?
            argument.getName() :
            argument.getName() + "." + attribute.getName();        
    }

    public Object getValue() {
        return value;
    }

    public boolean isCursor() {
        return value instanceof ResultSet;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String toString() {
        return argument.getName() + " = " + value;
    }
}
