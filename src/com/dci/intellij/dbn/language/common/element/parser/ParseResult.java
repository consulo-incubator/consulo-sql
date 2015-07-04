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

package com.dci.intellij.dbn.language.common.element.parser;

public class ParseResult{
    private static final ParseResult NO_MATCH = new ParseResult(ParseResultType.NO_MATCH, 0);

    private ParseResultType type;
    private int matchedTokens;

    private ParseResult(ParseResultType type, int matchedTokens) {
        this.type = type;
        this.matchedTokens = matchedTokens;
    }

    public static ParseResult createFullMatchResult(int matchedTokens) {
        return new ParseResult(ParseResultType.FULL_MATCH, matchedTokens);
    }

    public static ParseResult createPartialMatchResult(int matchedTokens) {
        return new ParseResult(ParseResultType.PARTIAL_MATCH, matchedTokens);
    }

    public static ParseResult createNoMatchResult() {
        return NO_MATCH;
    }

    public boolean isNoMatch() {
        return type == ParseResultType.NO_MATCH;
    }

    public boolean isFullMatch() {
        return type == ParseResultType.FULL_MATCH;
    }

    public boolean isPartialMatch() {
        return type == ParseResultType.PARTIAL_MATCH;
    }


    public boolean isMatch() {
        return  isFullMatch() || isPartialMatch();
    }

    public ParseResultType getType() {
        return type;
    }

    public int getMatchedTokens() {
        return matchedTokens;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
