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

import com.dci.intellij.dbn.code.common.lookup.LookupItemFactory;
import com.dci.intellij.dbn.common.lookup.ConsumerStoppedException;
import com.dci.intellij.dbn.common.lookup.LookupConsumer;
import com.dci.intellij.dbn.language.common.element.TokenElementType;
import com.dci.intellij.dbn.object.common.DBObject;

import java.util.Collection;

public class CodeCompletionLookupConsumer implements LookupConsumer {
    private CodeCompletionContext context;
    boolean addParenthesis;

    public CodeCompletionLookupConsumer(CodeCompletionContext context) {
        this.context = context;
    }

    @Override
    public void consume(Object object) throws ConsumerStoppedException {
        check();

        LookupItemFactory lookupItemFactory = null;
        if (object instanceof DBObject) {
            DBObject dbObject = (DBObject) object;
            lookupItemFactory = dbObject.getLookupItemFactory(context.getLanguage());

        } else if (object instanceof TokenElementType) {
            TokenElementType tokenElementType = (TokenElementType) object;
            lookupItemFactory = tokenElementType.getLookupItemFactory(context.getLanguage());
        }

        if (lookupItemFactory != null) {
            lookupItemFactory.createLookupItem(object, this);
        }

    }

    public void consume(Collection objects) throws ConsumerStoppedException {
        check();
        for (Object object : objects) {
            consume(object);
        }
    }

    public void setAddParenthesis(boolean addParenthesis) {
        this.addParenthesis = addParenthesis;
    }

    @Override
    public void check() throws ConsumerStoppedException {
        if (context.getResult().isStopped()) {
            throw new ConsumerStoppedException();
        }
    }

    public CodeCompletionContext getContext() {
        return context;
    }

    public boolean isAddParenthesis() {
        return addParenthesis;
    }
}
