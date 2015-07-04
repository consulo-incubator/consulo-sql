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

package com.dci.intellij.dbn.editor.data.filter;

import java.util.Date;

public enum ConditionOperator {
    EQUAL("=", false),
    NOT_EQUAL("!=", false),
    LESS("<", false),
    GREATER(">", false),
    LESS_EQUAL("<=", false),
    GREATER_EQUAL(">=", false),
    LIKE("like", false),
    NOT_LIKE("not like", false),
    BETWEEN("between", false),
    IN("in", false, "(", ")"),
    NOT_IN("not in", false, "(", ")"),
    IS_NULL("is null", true),
    IS_NOT_NULL("is not null", true);

    public static final ConditionOperator[] ALL_CONDITION_OPERATORS = new ConditionOperator[] {
            EQUAL,
            NOT_EQUAL,
            LESS,
            GREATER,
            LESS_EQUAL,
            GREATER_EQUAL,
            LIKE,
            NOT_LIKE,
            BETWEEN,
            IN,
            NOT_IN,
            IS_NULL,
            IS_NOT_NULL};

    public static final ConditionOperator[] NUMBER_CONDITION_OPERATORS = new ConditionOperator[] {
            EQUAL,
            NOT_EQUAL,
            LESS,
            GREATER,
            LESS_EQUAL,
            GREATER_EQUAL,
            BETWEEN,
            IN,
            NOT_IN,
            IS_NULL,
            IS_NOT_NULL};

    public static final ConditionOperator[] DATE_CONDITION_OPERATORS = new ConditionOperator[] {
            EQUAL,
            NOT_EQUAL,
            LESS,
            GREATER,
            LESS_EQUAL,
            GREATER_EQUAL,
            BETWEEN,
            IN,
            NOT_IN,
            IS_NULL,
            IS_NOT_NULL};

    public static final ConditionOperator[] STRING_CONDITION_OPERATORS = new ConditionOperator[] {
            EQUAL,
            NOT_EQUAL,
            LIKE,
            NOT_LIKE,
            IN,
            NOT_IN,
            IS_NULL,
            IS_NOT_NULL};


    public static ConditionOperator[] getConditionOperators (Class dataTypeClass) {
        if (String.class.isAssignableFrom(dataTypeClass)) {
            return STRING_CONDITION_OPERATORS;
        }

        if (Number.class.isAssignableFrom(dataTypeClass)) {
            return NUMBER_CONDITION_OPERATORS;
        }

        if (Date.class.isAssignableFrom(dataTypeClass)) {
            return DATE_CONDITION_OPERATORS;
        }

        return ALL_CONDITION_OPERATORS;
    }

    private String text;
    private boolean isFinal;
    private String valuePrefix;
    private String valuePostfix;

    ConditionOperator(String text, boolean isFinal) {
        this.text = text;
        this.isFinal = isFinal;
    }

    ConditionOperator(String text, boolean isFinal, String valuePrefix, String valuePostfix) {
        this.text = text;
        this.isFinal = isFinal;
        this.valuePrefix = valuePrefix;
        this.valuePostfix = valuePostfix;

    }

    public String getText() {
        return text;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public String getValuePostfix() {
        return valuePostfix;
    }

    public String getValuePrefix() {
        return valuePrefix;
    }

    public String toString() {
        return text;
    }
}
