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

package com.dci.intellij.dbn.execution.compiler;

import com.dci.intellij.dbn.common.Icons;

import javax.swing.Icon;

public enum CompileType {
    NORMAL("Normal", Icons.OBEJCT_COMPILE),
    DEBUG("Debug", Icons.OBEJCT_COMPILE_DEBUG),
    KEEP("Keep existing", null/*Icons.OBEJCT_COMPILE_KEEP*/),
    ASK("Ask before compilation", null/*Icons.OBEJCT_COMPILE_ASK*/);

    private String displayName;
    private Icon icon;

    CompileType(String displayName, Icon icon) {
        this.displayName = displayName;
        this.icon = icon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Icon getIcon() {
        return icon;
    }

    public static CompileType get(String name) {
        for (CompileType compileType : CompileType.values()) {
            if (compileType.getDisplayName().equals(name) || compileType.name().equals(name)) {
                return compileType;
            }
        }
        return null;
    }}
