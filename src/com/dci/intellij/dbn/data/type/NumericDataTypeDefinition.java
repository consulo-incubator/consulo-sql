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

import java.lang.reflect.Constructor;
import java.util.List;

public class NumericDataTypeDefinition extends BasicDataTypeDefinition {
    private Constructor constructor;
    public NumericDataTypeDefinition(String name, Class typeClass, int sqlType, BasicDataType basicDataType, List<DataTypeDefinition> bundle) {
        super(name, typeClass, sqlType, basicDataType, bundle);
        try {
            constructor = typeClass.getConstructor(String.class);
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    @Override
    public Object convert(Object object) {
        assert object instanceof Number;

        Number number = (Number) object;
        if (object.getClass().equals(getTypeClass())) {
            return object;
        }
        try {
            return constructor.newInstance(number.toString());
        } catch (Throwable e) {
            e.printStackTrace();
            return object;
        }
    }
}
