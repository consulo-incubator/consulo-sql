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

package com.dci.intellij.dbn.language.common.element;

public enum WrapperElementTypeTemplate {
    PARENTHESES("CHR_LEFT_PARENTHESIS", "CHR_RIGHT_PARENTHESIS", false),
    BRACKETS("CHR_LEFT_BRACKET", "CHR_RIGHT_BRACKET", false),
    BEGIN_END("KW_BEGIN", "KW_END", true);

    private String beginToken;
    private String endToken;
    private boolean block;

    private WrapperElementTypeTemplate(String beginToken, String endToken, boolean block) {
        this.beginToken = beginToken;
        this.endToken = endToken;
        this.block = block;
    }

    public String getBeginToken() {
        return beginToken;
    }

    public String getEndToken() {
        return endToken;
    }

    public boolean isBlock() {
        return block;
    }
}
