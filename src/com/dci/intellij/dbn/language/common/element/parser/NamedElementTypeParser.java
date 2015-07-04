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
import com.dci.intellij.dbn.language.common.element.NamedElementType;
import com.dci.intellij.dbn.language.common.element.path.ParsePathNode;
import com.dci.intellij.dbn.language.common.element.path.PathNode;
import com.intellij.lang.PsiBuilder;

public class NamedElementTypeParser extends SequenceElementTypeParser<NamedElementType>{
    public NamedElementTypeParser(NamedElementType elementType) {
        super(elementType);
    }

    public ParseResult parse(ParsePathNode parentNode, PsiBuilder builder, boolean optional, int depth, long timestamp) throws ParseException {
        if (isRecursive(parentNode, builder.getCurrentOffset(), 2)) {
            return ParseResult.createNoMatchResult();
        }
        return super.parse(parentNode, builder, optional, depth, timestamp);
    }

    protected boolean isRecursive(ParsePathNode parseNode, int builderOffset, int iterations){
        while (parseNode != null &&  iterations > 0) {
            if (parseNode.getElementType() == getElementType() &&
                    parseNode.getStartOffset() == builderOffset) {
                //return true;
                iterations--;
            }
            parseNode = parseNode.getParent();
        }
        return iterations == 0;
        //return false;
    }

    private int countRecurences(PathNode node) {
        PathNode parent = node;
        int count = 0;
        while(parent != null) {
            if (parent.getElementType() == getElementType()) {
                count++;
            }
            parent = parent.getParent();
        }
        return count;
    }
}