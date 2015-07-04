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

import com.dci.intellij.dbn.language.common.element.ElementType;
import com.dci.intellij.dbn.language.common.element.ElementTypeBundle;
import com.dci.intellij.dbn.language.common.element.ExecVariableElementType;
import com.dci.intellij.dbn.language.common.element.LeafElementType;
import com.dci.intellij.dbn.language.common.element.lookup.ExecVariableElementTypeLookupCache;
import com.dci.intellij.dbn.language.common.element.parser.ExecVariableElementTypeParser;
import com.dci.intellij.dbn.language.common.element.util.ElementTypeDefinitionException;
import com.dci.intellij.dbn.language.common.psi.ExecVariablePsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import org.jdom.Element;


public class ExecVariableElementTypeImpl extends LeafElementTypeImpl implements ExecVariableElementType {

    public ExecVariableElementTypeImpl(ElementTypeBundle bundle, ElementType parent, String id, Element def) throws ElementTypeDefinitionException {
        super(bundle, parent, id, def);
        setTokenType(bundle.getTokenTypeBundle().getVariable());
    }

    public ExecVariableElementTypeLookupCache createLookupCache() {
        return new ExecVariableElementTypeLookupCache(this);
    }

    public ExecVariableElementTypeParser createParser() {
        return new ExecVariableElementTypeParser(this);
    }

    protected void loadDefinition(Element def) throws ElementTypeDefinitionException {
        super.loadDefinition(def);
    }

    public PsiElement createPsiElement(ASTNode astNode) {
        return new ExecVariablePsiElement(astNode, this);
    }

    public String getDebugName() {
        return "variable (" + getId() + ")";
    }

    public String toString() {
        return "variable (" + getId() + ")";
    }

    public boolean isSameAs(LeafElementType elementType) {
        return elementType instanceof ExecVariableElementType;
    }

    public boolean isIdentifier() {
        return true;
    }
}
