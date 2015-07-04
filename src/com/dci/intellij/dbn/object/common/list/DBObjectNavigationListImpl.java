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

package com.dci.intellij.dbn.object.common.list;

import com.dci.intellij.dbn.object.common.DBObject;

import java.util.List;

public class DBObjectNavigationListImpl<T extends DBObject> implements DBObjectNavigationList {

    private String name;
    private T object;
    private List<T> objects;
    private ObjectListProvider<T> objectsProvider;

    public DBObjectNavigationListImpl(String name, T object) {
        this.name = name;
        this.object = object;
    }

    public DBObjectNavigationListImpl(String name, List<T> objects) {
        this.name = name;
        this.objects = objects;
    }

    public DBObjectNavigationListImpl(String name, ObjectListProvider<T> objectsProvider) {
        this.name = name;
        this.objectsProvider = objectsProvider;
    }

    public String getName() {
        return name;
    }

    public T getObject() {
        return object;
    }

    public List<T> getObjects() {
        return objects;
    }

    public ObjectListProvider<T> getObjectsProvider() {
        return objectsProvider;
    }

    public boolean isLazy() {
        return objectsProvider != null;
    }
}
