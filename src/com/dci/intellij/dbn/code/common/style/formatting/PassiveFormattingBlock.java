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

package com.dci.intellij.dbn.code.common.style.formatting;

import com.intellij.formatting.Block;
import com.intellij.formatting.Wrap;
import com.intellij.formatting.Indent;
import com.intellij.formatting.Alignment;
import com.intellij.formatting.Spacing;
import com.intellij.formatting.ChildAttributes;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.dci.intellij.dbn.code.common.style.presets.CodeStylePreset;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.ArrayList;

public class PassiveFormattingBlock implements Block {
    private static final  List<Block> EMPTY_LIST = new ArrayList<Block>(0);
    private PsiElement psiElement;

    public PassiveFormattingBlock(PsiElement psiElement) {
        this.psiElement = psiElement;
    }

    @NotNull
    public TextRange getTextRange() {
        return psiElement.getTextRange();
    }

    @NotNull
    public List<Block> getSubBlocks() {
        return EMPTY_LIST;
    }

    public Wrap getWrap() {
        return CodeStylePreset.WRAP_NONE;
    }

    public Indent getIndent() {
        return Indent.getNoneIndent();
    }

    public Alignment getAlignment() {
        return null;
    }

    public Spacing getSpacing(Block child1, Block child2) {
        return null;
    }

    @NotNull
    public ChildAttributes getChildAttributes(int newChildIndex) {
        return new ChildAttributes(Indent.getNoneIndent(), Alignment.createAlignment());
    }

    public boolean isIncomplete() {
        return false;
    }

    public boolean isLeaf() {
        return true;
    }
}
