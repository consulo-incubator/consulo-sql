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

import com.dci.intellij.dbn.language.common.ParseException;
import com.dci.intellij.dbn.language.common.TokenType;
import com.dci.intellij.dbn.language.common.element.IdentifierElementType;
import com.dci.intellij.dbn.language.common.element.path.ParsePathNode;
import com.intellij.lang.PsiBuilder;

public class IdentifierElementTypeParser extends AbstractElementTypeParser<IdentifierElementType> {
    public IdentifierElementTypeParser(IdentifierElementType elementType) {
        super(elementType);
    }

    public ParseResult parse(ParsePathNode parentNode, PsiBuilder builder, boolean optional, int depth, long timestamp) throws ParseException {
        logBegin(builder, optional, depth);
        TokenType tokenType = (TokenType) builder.getTokenType();
        if (tokenType != null && !tokenType.isChameleon()){
            if (tokenType.isIdentifier()) {
                PsiBuilder.Marker marker = builder.mark();
                builder.advanceLexer();
                return stepOut(builder, marker, depth, ParseResultType.FULL_MATCH, 1, null);
            }
            else if (getElementType().isDefinition() || isSuppressibleReservedWord(tokenType, parentNode)) {
                    PsiBuilder.Marker marker = builder.mark();
                    builder.advanceLexer();
                    return stepOut(builder, marker, depth, ParseResultType.FULL_MATCH, 1, null);
            }
        }
        return stepOut(builder, null, depth, ParseResultType.NO_MATCH, 0, null);
    }  
}