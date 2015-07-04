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

package com.dci.intellij.dbn.language.common.element.path;

import com.dci.intellij.dbn.language.common.element.ElementType;
import com.dci.intellij.dbn.language.common.element.SequenceElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.impl.source.tree.FileElement;

public class ASTPathNode implements PathNode{
    private ASTNode astNode;

    public ASTPathNode(ASTNode astNode) {
        this.astNode = astNode;
    }

    public PathNode getParent() {
        ASTNode treeParent = astNode.getTreeParent();
        if (treeParent != null && !(treeParent instanceof FileElement)) {
            return new ASTPathNode(treeParent);
        }
        return null;
    }

    public int getIndexInParent() {
        ASTNode parentAstNode = astNode.getTreeParent();
        if (parentAstNode.getElementType() instanceof SequenceElementType) {
            SequenceElementType sequenceElementType = (SequenceElementType) parentAstNode.getElementType();
            int index = 0;
            ASTNode child = parentAstNode.getFirstChildNode();
            while (child != null) {
                if (astNode == child) {
                    break;
                }
                index++;
                child = child.getTreeNext();
                if (child instanceof PsiWhiteSpace){
                    child = child.getTreeNext();
                }
            }
            return sequenceElementType.indexOf((ElementType) astNode.getElementType(), index);
        }
        return 0;
    }

    public ElementType getElementType() {
        return (ElementType) astNode.getElementType();
    }

    public PathNode getRootPathNode() {
        return null;
    }

    public boolean isRecursive() {
        return false; 
    }

    public boolean isRecursive(ElementType elementType) {
        return false;
    }

    @Override
    public void detach() {

    }
}
