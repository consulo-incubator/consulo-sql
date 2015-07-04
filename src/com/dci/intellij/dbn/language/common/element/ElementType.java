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

import javax.swing.Icon;

import com.dci.intellij.dbn.code.common.style.formatting.FormattingDefinition;
import com.dci.intellij.dbn.language.common.SqlLikeLanguage;
import com.dci.intellij.dbn.language.common.SqlLikeLanguageVersion;
import com.dci.intellij.dbn.language.common.element.lookup.ElementTypeLookupCache;
import com.dci.intellij.dbn.language.common.element.parser.ElementTypeParser;
import com.dci.intellij.dbn.language.common.element.util.ElementTypeAttribute;
import com.dci.intellij.dbn.language.common.element.util.ElementTypeAttributesBundle;
import com.dci.intellij.dbn.object.common.DBObjectType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;

public interface ElementType {

    String getId();

    String getDescription();

    String getDebugName();

    Icon getIcon();

    ElementType getParent();

    SqlLikeLanguage getLanguage();

    SqlLikeLanguageVersion<?> getLanguageVersion();

    ElementTypeLookupCache getLookupCache();

    ElementTypeParser getParser();

    boolean is(ElementTypeAttribute attribute);

    boolean isLeaf();

    boolean isVirtualObject();

    boolean isVirtualObjectInsideLookup();    

    DBObjectType getVirtualObjectType();

    PsiElement createPsiElement(ASTNode astNode);

    ElementTypeBundle getElementBundle();

    void registerVirtualObject(DBObjectType objectType);

    FormattingDefinition getFormatting();

    void setDefaultFormatting(FormattingDefinition defaults);

    ElementTypeAttributesBundle getAttributes();
}
