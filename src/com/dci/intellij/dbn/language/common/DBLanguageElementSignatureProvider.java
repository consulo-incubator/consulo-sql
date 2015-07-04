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

package com.dci.intellij.dbn.language.common;

import com.dci.intellij.dbn.language.common.psi.BasePsiElement;
import com.intellij.codeInsight.folding.impl.ElementSignatureProvider;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.StringTokenizer;

public class DBLanguageElementSignatureProvider implements ElementSignatureProvider {
    public String getSignature(@NotNull PsiElement psiElement) {
        if (psiElement.getContainingFile() instanceof DBLanguageFile) {
            TextRange textRange = psiElement.getTextRange();
            String offsets = textRange.getStartOffset() + "#" + textRange.getEndOffset();
            if (psiElement instanceof BasePsiElement) {
                BasePsiElement basePsiElement = (BasePsiElement) psiElement;
                return basePsiElement.getElementType().getId() + "#" + offsets;
            }

            if (psiElement instanceof PsiComment) {
                return "comment#" + offsets;
            }
        }
        return null;
    }

    public PsiElement restoreBySignature(@NotNull PsiFile psifile, @NotNull String signature, @Nullable StringBuilder processingInfoStorage) {
        if (psifile instanceof DBLanguageFile) {
            StringTokenizer tokenizer = new StringTokenizer(signature, "#");
            String id = tokenizer.nextToken();
            int startOffset = Integer.parseInt(tokenizer.nextToken());
            int endOffset = Integer.parseInt(tokenizer.nextToken());

            PsiElement psiElement = psifile.findElementAt(startOffset);
            if (psiElement instanceof PsiComment) {
                if (id.equals("comment") && endOffset == startOffset + psiElement.getTextLength()) {
                    return psiElement;
                }
            }

            while (psiElement != null) {
                int elementStartOffset = psiElement.getTextOffset();
                int elementEndOffset = elementStartOffset + psiElement.getTextLength();
                if (elementStartOffset < startOffset || elementEndOffset > endOffset) {
                    break;
                }
                if (psiElement instanceof BasePsiElement) {
                    BasePsiElement basePsiElement = (BasePsiElement) psiElement;
                    if (basePsiElement.getElementType().getId().equals(id) &&
                            elementStartOffset == startOffset &&
                            elementEndOffset == endOffset) {
                        return basePsiElement;
                    }
                }
                psiElement = psiElement.getParent();
            }
        }
        return null;
    }

    public PsiElement restoreBySignature(PsiFile psifile, String signature) {
        return restoreBySignature(psifile, signature, null);

    }
}
