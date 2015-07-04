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

package com.dci.intellij.dbn.object.filter.name;

public enum ConditionOperator {
    EQUAL("equal", false),
    NOT_EQUAL("not equal", false),
    LIKE("like", true),
    NOT_LIKE("not like", true);

    private String text;
    private boolean allowsWildcards;

    ConditionOperator(String text, boolean allowsWildcards) {
        this.text = text;
        this.allowsWildcards = allowsWildcards;
    }

    public String getText() {
        return text;
    }

    public boolean allowsWildcards() {
        return allowsWildcards;
    }

    @Override
    public String toString() {
        return text;
    }
}
