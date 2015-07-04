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

package com.dci.intellij.dbn.language.sql;

import com.dci.intellij.dbn.common.util.NamingUtil;
import com.dci.intellij.dbn.language.common.psi.BasePsiElement;
import com.dci.intellij.dbn.language.common.psi.ChameleonPsiElement;
import com.dci.intellij.dbn.language.common.psi.ExecutablePsiElement;
import com.dci.intellij.dbn.language.common.psi.RootPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilder;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.lang.folding.LanguageFolding;
import com.intellij.openapi.editor.Document;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SQLFoldingBuilder implements FoldingBuilder {

    @NotNull
    public FoldingDescriptor[] buildFoldRegions(@NotNull ASTNode node, @NotNull Document document) {
        List<FoldingDescriptor> descriptors = new ArrayList<FoldingDescriptor>();

        PsiElement child = node.getPsi().getFirstChild();
        while (child != null) {
            if (child instanceof RootPsiElement) {
                RootPsiElement rootPsiElement = (RootPsiElement) child;
                /*FoldingDescriptor rootFoldingDescriptor = new FoldingDescriptor(
                            rootPsiElement.getAstNode(),
                            rootPsiElement.getTextRange()); 
                foldingDescriptors.add(rootFoldingDescriptor);*/

                for (ExecutablePsiElement executablePsiElement : rootPsiElement.getExecutablePsiElements()) {
                    FoldingDescriptor foldingDescriptor = new FoldingDescriptor(
                            executablePsiElement.getNode(),
                            executablePsiElement.getTextRange());
                    descriptors.add(foldingDescriptor);
                }
            } else if (child instanceof ChameleonPsiElement) {
                ChameleonPsiElement chameleonPsiElement = (ChameleonPsiElement) child;
                FoldingDescriptor foldingDescriptor = new FoldingDescriptor(
                        chameleonPsiElement.getNode(),
                        chameleonPsiElement.getTextRange());
                descriptors.add(foldingDescriptor);


                FoldingBuilder foldingBuilder = LanguageFolding.INSTANCE.forLanguage(chameleonPsiElement.getLanguage());
                FoldingDescriptor[] nestedDescriptors = foldingBuilder.buildFoldRegions(chameleonPsiElement.getNode(), document);
                descriptors.addAll(Arrays.asList(nestedDescriptors));

            }
            child = child.getNextSibling();
        }
        return descriptors.toArray(new FoldingDescriptor[descriptors.size()]);
    }

    public String getPlaceholderText(@NotNull ASTNode node) {
        BasePsiElement basePsiElement = (BasePsiElement) node.getPsi();
        Set<BasePsiElement> subjects = new HashSet<BasePsiElement>();
        basePsiElement.collectSubjectPsiElements(subjects);
        StringBuilder buffer = new StringBuilder(basePsiElement.getElementType().getDescription());
        if (subjects.size() > 0) {
            buffer.append(" (");
            buffer.append(NamingUtil.createNamesList(subjects, 3));
            buffer.append(")");
        }
        return buffer.toString();
    }

    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return false;
    }

}
