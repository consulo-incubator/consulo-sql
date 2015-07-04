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

package com.dci.intellij.dbn.common.options.setting;

import com.dci.intellij.dbn.common.util.CommonUtil;
import com.intellij.openapi.options.ConfigurationException;

public abstract class Setting<T, E> {
    private T value;
    private String name;

    protected Setting(String configName, T value) {
        this.name = configName;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public T value() {
        return value;
    }

    public boolean setValue(T value) {
        boolean response = !CommonUtil.safeEqual(this.value, value);
        this.value = value;
        return response;
    }

    @Override
    public String toString() {
        return "[" + getClass().getSimpleName() + "] " + name + " = " + value;
    }

    public abstract boolean applyChanges(E component) throws ConfigurationException;

    public abstract void resetChanges(E component);
}
