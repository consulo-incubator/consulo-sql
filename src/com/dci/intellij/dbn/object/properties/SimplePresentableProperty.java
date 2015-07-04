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

import com.intellij.pom.Navigatable;

import javax.swing.*;

public class SimplePresentableProperty extends PresentableProperty{
    private String name;
    private String value;
    private Icon icon;

    public SimplePresentableProperty(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public SimplePresentableProperty(String name, String value, Icon icon) {
        this.name = name;
        this.value = value;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Icon getIcon() {
        return icon;
    }

    public Navigatable getNavigatable() {
        return null;
    }
}
