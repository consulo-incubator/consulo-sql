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

package com.dci.intellij.dbn.language.common.element.util;

import gnu.trove.THashSet;

import java.util.Set;

public enum ElementTypeAttribute {
    
    ROOT("ROOT", "Executable statement"),
    EXECUTABLE("EXECUTABLE", "Executable statement"),
    TRANSACTIONAL("TRANSACTIONAL", "Transactional statement"),
    QUERY("QUERY", "Query statement"),
    DATA_DEFINITION("DATA_DEFINITION", "Data definition statement"),
    DATA_MANIPULATION("DATA_MANIPULATION", "Data manipulation statement"),
    TRANSACTION_CONTROL("TRANSACTION_CONTROL", "Transaction control statement"),
    OBJECT_SPECIFICATION("OBJECT_SPECIFICATION", "Object specification"),
    DECLARATION("DECLARATION", "Declaration"),
    OBJECT_DECLARATION("OBJECT_DECLARATION", "Object definition"),
    SUBJECT("SUBJECT", "Statement subject"),
    STATEMENT("STATEMENT", "Statement"),
    CLAUSE("CLAUSE", "Statement clause"),
    STRUCTURE("STRUCTURE", "Structure view element"),
    SCOPE_ISOLATION("SCOPE_ISOLATION", "Scope isolation"),
    SCOPE_DEMARCATION("SCOPE_DEMARCATION", "Scope demarcation"),
    FOLDABLE_BLOCK("FOLDABLE_BLOCK", "Foldable block"),
    DDL_STATEMENT("DDL_STATEMENT", "DDL statement"),
    EXECUTABLE_CODE("EXECUTABLE_CODE", "Executable code"),
    BREAKPOINT_POSITION("BREAKPOINT_POSITION", "Default breakpoint position");

    public static final Set<ElementTypeAttribute> EMPTY_LIST = new THashSet<ElementTypeAttribute>(0);

    private String name;
    private String description;

    ElementTypeAttribute(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name;
    }
}
