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

package com.dci.intellij.dbn.language.common;

public enum TokenTypeIdentifier {
    UNKNOWN("unknown"),
    KEYWORD("keyword"),
    FUNCTION("function"),
    PARAMETER("parameter"),
    DATATYPE("datatype"),
    EXCEPTION("exception"),
    OPERATOR("operator"),
    CHARACTER("character"),
    IDENTIFIER("identifier"),
    CHAMELEON("chameleon");

    private String name;
    TokenTypeIdentifier(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static TokenTypeIdentifier getIdentifier(String typeName) {
        for (TokenTypeIdentifier identifier : TokenTypeIdentifier.values()) {
            if (identifier.getName().equals(typeName)) return identifier;
        }
        return UNKNOWN;
    }
}
