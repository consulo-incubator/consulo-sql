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

package com.dci.intellij.dbn.object.properties;

import com.dci.intellij.dbn.data.type.DBDataType;
import com.dci.intellij.dbn.object.DBType;
import com.intellij.pom.Navigatable;

import javax.swing.*;

public class DBDataTypePresentableProperty extends PresentableProperty{
    private DBDataType dataType;

    public DBDataTypePresentableProperty(DBDataType dataType) {
        this.dataType = dataType;
    }

    public String getName() {
        return "Data type";
    }

    public String getValue() {
        return dataType.getQualifiedName();
    }

    public Icon getIcon() {
        DBType declaredType = dataType.getDeclaredType();
        return declaredType == null ? null : declaredType.getIcon();
    }

    @Override
    public Navigatable getNavigatable() {
        return dataType.getDeclaredType();
    }
}
