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

package com.dci.intellij.dbn.code.common.completion;

import com.dci.intellij.dbn.code.common.lookup.DBLookupItem;
import com.dci.intellij.dbn.language.common.element.TokenElementType;
import com.dci.intellij.dbn.language.common.psi.LeafPsiElement;
import com.dci.intellij.dbn.language.common.psi.PsiUtil;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;

public class BasicInsertHandler implements InsertHandler<DBLookupItem> {
    public static final BasicInsertHandler INSTANCE = new BasicInsertHandler();

    public void handleInsert(InsertionContext insertionContext, DBLookupItem lookupElement) {
        char completionChar = insertionContext.getCompletionChar();

        Object lookupElementObject = lookupElement.getObject();
        if (lookupElementObject instanceof TokenElementType) {
            TokenElementType tokenElementType = (TokenElementType) lookupElementObject;
            if(tokenElementType.getTokenType().isReservedWord()) {
                Editor editor = insertionContext.getEditor();
                CaretModel caretModel = editor.getCaretModel();

                LeafPsiElement leafPsiElement = PsiUtil.lookupLeafAtOffset(insertionContext.getFile(), caretModel.getOffset());
                if (leafPsiElement == null || leafPsiElement.getTextOffset() != caretModel.getOffset()) {
                    caretModel.moveCaretRelatively(1, 0, false, false, false);
                    return;
                }
            }
        }

        if (completionChar == ' ' || completionChar == '\t' || completionChar == '\u0000') {
            Editor editor = insertionContext.getEditor();
            CaretModel caretModel = editor.getCaretModel();
            caretModel.moveCaretRelatively(1, 0, false, false, false);
        }


    }

    protected boolean shouldInsertCharacter(char chr) {
        return chr != '\t' && chr != '\n' && chr!='\u0000';
    }
}
