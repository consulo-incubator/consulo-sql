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

import com.dci.intellij.dbn.common.util.NamingUtil;
import com.dci.intellij.dbn.object.common.DBObject;
import com.intellij.pom.Navigatable;

import javax.swing.*;

public class DBObjectPresentableProperty extends PresentableProperty{
    private DBObject object;
    private boolean qualified = false;
    private String name;


    public DBObjectPresentableProperty(String name, DBObject object, boolean qualified) {
        this.object = object;
        this.qualified = qualified;
        this.name = name;
    }

    public DBObjectPresentableProperty(DBObject object, boolean qualified) {
        this.object = object;
        this.qualified = qualified;
    }

    public DBObjectPresentableProperty(DBObject object) {
        this.object = object;
    }

    public String getName() {
        return name == null ? NamingUtil.capitalize(object.getTypeName()) : name;
    }

    public String getValue() {
        return qualified ? object.getQualifiedName() : object.getName();
    }

    public Icon getIcon() {
        return object.getIcon();
    }

    @Override
    public Navigatable getNavigatable() {
        return object;
    }
}
