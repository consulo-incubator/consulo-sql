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

import com.dci.intellij.dbn.code.common.style.formatting.FormattingDefinition;
import com.dci.intellij.dbn.common.util.StringUtil;
import com.dci.intellij.dbn.language.common.element.ElementType;
import com.dci.intellij.dbn.language.common.element.ElementTypeBundle;
import com.dci.intellij.dbn.language.common.element.TokenElementType;
import com.dci.intellij.dbn.language.common.element.WrapperElementType;
import com.dci.intellij.dbn.language.common.element.WrapperElementTypeTemplate;
import com.dci.intellij.dbn.language.common.element.lookup.WrapperElementTypeLookupCache;
import com.dci.intellij.dbn.language.common.element.parser.WrapperElementTypeParser;
import com.dci.intellij.dbn.language.common.element.util.ElementTypeDefinitionException;
import com.dci.intellij.dbn.language.common.psi.SequencePsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import org.jdom.Element;

import java.util.List;

public class WrapperElementTypeImpl extends AbstractElementType implements WrapperElementType {
    private TokenElementType beginTokenElement;
    private TokenElementType endTokenElement;
    private ElementType wrappedElement;
    private boolean isWrappingOptional;

    public WrapperElementTypeImpl(ElementTypeBundle bundle, ElementType parent, String id, Element def) throws ElementTypeDefinitionException {
        super(bundle, parent, id, def);
    }

    @Override
    protected void loadDefinition(Element def) throws ElementTypeDefinitionException {
        super.loadDefinition(def);
        ElementTypeBundle bundle = getElementBundle();
        String templateId = def.getAttributeValue("template");
        if (StringUtil.isEmpty(templateId)) {
            String startTokenId = def.getAttributeValue("begin-token");
            String endTokenId = def.getAttributeValue("end-token");

            beginTokenElement = new TokenElementTypeImpl(bundle, this, startTokenId, "begin-token");
            endTokenElement = new TokenElementTypeImpl(bundle, this, endTokenId, "end-token");
        } else {
            WrapperElementTypeTemplate template = WrapperElementTypeTemplate.valueOf(templateId);
            beginTokenElement = new TokenElementTypeImpl(bundle, this, template.getBeginToken(), "begin-token");
            endTokenElement = new TokenElementTypeImpl(bundle, this, template.getEndToken(), "end-token");

            if (template.isBlock()) {
                beginTokenElement.setDefaultFormatting(FormattingDefinition.LINE_BREAK_AFTER);
                endTokenElement.setDefaultFormatting(FormattingDefinition.LINE_BREAK_BEFORE);
                setDefaultFormatting(FormattingDefinition.LINE_BREAK_BEFORE);
            }

        }

        List children = def.getChildren();
        if (children.size() != 1) {
            throw new ElementTypeDefinitionException(
                    "Invalid wrapper definition. " +
                    "Element should contain exact one child of type 'one-of', 'sequence', 'element', 'token'");
        }
        Element child = (Element) children.get(0);
        String type = child.getName();
        wrappedElement = bundle.resolveElementDefinition(child, type, this);

        isWrappingOptional = Boolean.parseBoolean(def.getAttributeValue("wrapping-optional"));

        //getLookupCache().registerFirstLeaf(beginTokenElement, isOptional);
    }

    @Override
    public WrapperElementTypeLookupCache createLookupCache() {
        return new WrapperElementTypeLookupCache(this);
    }

    @Override
    public WrapperElementTypeParser createParser() {
        return new WrapperElementTypeParser(this);
    }

    public boolean isLeaf() {
        return false;
    }

    public TokenElementType getBeginTokenElement() {
        return beginTokenElement;
    }

    public TokenElementType getEndTokenElement() {
        return endTokenElement;
    }

    public ElementType getWrappedElement() {
        return wrappedElement;
    }

    public boolean isWrappingOptional() {
        return isWrappingOptional;
    }

    public String getDebugName() {
        return "wrapper (" + getId() + ")";
    }
    public PsiElement createPsiElement(ASTNode astNode) {
        return new SequencePsiElement(astNode, this);
    }
}
