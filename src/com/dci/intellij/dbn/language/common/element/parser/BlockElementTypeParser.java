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
import com.dci.intellij.dbn.language.common.element.BlockElementType;
import com.dci.intellij.dbn.language.common.element.path.ParsePathNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;

public class BlockElementTypeParser extends SequenceElementTypeParser<BlockElementType>{
    public BlockElementTypeParser(BlockElementType elementType) {
        super(elementType);
    }

    public ParseResult parse(ParsePathNode parentNode, PsiBuilder builder, boolean optional, int depth, long timestamp) throws ParseException {
        PsiBuilder.Marker marker = builder.mark();
        ParseResult result = super.parse(parentNode, builder, optional, depth, timestamp);
        if (result.getType() == ParseResultType.NO_MATCH) {
            marker.drop();
        } else {
            marker.done((IElementType) getElementType());
        }
        return result.getType() == ParseResultType.NO_MATCH ?
                ParseResult.createNoMatchResult() :
                ParseResult.createFullMatchResult(result.getMatchedTokens());
    }
}