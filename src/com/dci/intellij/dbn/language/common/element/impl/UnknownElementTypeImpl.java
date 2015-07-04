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

import com.dci.intellij.dbn.language.common.element.ElementTypeBundle;
import com.dci.intellij.dbn.language.common.element.UnknownElementType;
import com.dci.intellij.dbn.language.common.element.lookup.UnknownElementTypeLookupCache;
import com.dci.intellij.dbn.language.common.element.parser.UnknownElementTypeParser;
import com.dci.intellij.dbn.language.common.psi.UnknownPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;

public class UnknownElementTypeImpl extends AbstractElementType implements UnknownElementType {

    public UnknownElementTypeImpl(ElementTypeBundle bundle) {
        super(bundle, null, "UNKNOWN", "Unidentified element type.");
    }

    @Override
    public UnknownElementTypeLookupCache createLookupCache() {
        return new UnknownElementTypeLookupCache(this);
    }

    @Override
    public UnknownElementTypeParser createParser() {
        return new UnknownElementTypeParser(this);
    }

    public boolean isLeaf() {
        return true;
    }

    public String getDebugName() {
        return getId();
    }

    public PsiElement createPsiElement(ASTNode astNode) {
        return new UnknownPsiElement(astNode, this);
    }

}
