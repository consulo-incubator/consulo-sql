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

package com.dci.intellij.dbn.object.factory;

import com.dci.intellij.dbn.object.common.DBObjectType;

import java.util.List;

public abstract class ObjectFactoryInput {
    private String objectName;
    private DBObjectType objectType;
    private ObjectFactoryInput parent;
    private int index;

    protected ObjectFactoryInput(String objectName, DBObjectType objectType, ObjectFactoryInput parent, int index) {
        this.objectName = objectName == null ? "" : objectName.trim();
        this.objectType = objectType;
        this.parent = parent;
        this.index = index;
    }

    public String getObjectName() {
        return objectName;
    }

    public DBObjectType getObjectType() {
        return objectType;
    }

    public int getIndex() {
        return index;
    }

    public ObjectFactoryInput getParent() {
        return parent;
    }

    public abstract void validate(List<String> errors);
}
