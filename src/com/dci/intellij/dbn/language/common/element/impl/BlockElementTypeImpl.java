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

package com.dci.intellij.dbn.language.common.element.impl;

import com.dci.intellij.dbn.language.common.element.BlockElementType;
import com.dci.intellij.dbn.language.common.element.ElementType;
import com.dci.intellij.dbn.language.common.element.ElementTypeBundle;
import com.dci.intellij.dbn.language.common.element.lookup.BlockElementTypeLookupCache;
import com.dci.intellij.dbn.language.common.element.parser.BlockElementTypeParser;
import com.dci.intellij.dbn.language.common.element.util.ElementTypeDefinitionException;
import com.dci.intellij.dbn.language.common.psi.BlockPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import org.jdom.Element;

public class BlockElementTypeImpl extends SequenceElementTypeImpl implements BlockElementType {
    public static final int INDENT_NONE = 0;
    public static final int INDENT_NORMAL = 1;

    private int indent;

    public BlockElementTypeImpl(ElementTypeBundle bundle, ElementType parent, String id, Element def) throws ElementTypeDefinitionException {
        super(bundle, parent, id, def);
    }

    @Override
    public BlockElementTypeLookupCache createLookupCache() {
        return new BlockElementTypeLookupCache(this);
    }

    public BlockElementTypeParser createParser() {
        return new BlockElementTypeParser(this);
    }

    @Override
    protected void loadDefinition(Element def) throws ElementTypeDefinitionException {
        super.loadDefinition(def);
        String indentString = def.getAttributeValue("indent");
        if (indentString != null) {
            indent = "NORMAL".equals(indentString) ? INDENT_NORMAL : INDENT_NONE;
        }
    }

    public PsiElement createPsiElement(ASTNode astNode) {
        return new BlockPsiElement(astNode, this);
    }

    public String getDebugName() {
        return "block (" + getId() + ")";
    }


    public int getIndent() {
        return indent;
    }
}
