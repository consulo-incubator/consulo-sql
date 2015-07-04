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

package com.dci.intellij.dbn.object.common.status;

public enum DBObjectStatus {
    PRESENT(false, true),
    ENABLED(true, true),
    VALID(true, true),
    DEBUG(true, true),
    COMPILING(false, false),
    SAVING(false, false);

    private boolean propagable;
    private boolean defaultValue;

    DBObjectStatus(boolean propagable, boolean defaultValue) {
        this.propagable = propagable;
        this.defaultValue = defaultValue;
    }

    public boolean isPropagable() {
        return propagable;
    }

    public boolean getDefaultValue() {
        return defaultValue;
    }
}
