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

package com.dci.intellij.dbn.code.common.lookup;

import com.dci.intellij.dbn.code.common.completion.CodeCompletionContext;
import com.dci.intellij.dbn.code.common.completion.CodeCompletionLookupConsumer;
import com.dci.intellij.dbn.code.common.completion.options.sorting.CodeCompletionSortingSettings;
import com.intellij.openapi.Disposable;

import javax.swing.Icon;

public abstract class LookupItemFactory implements Disposable {
    public DBLookupItem createLookupItem(Object source, CodeCompletionLookupConsumer consumer) {
        CodeCompletionContext context = consumer.getContext();

        CharSequence text = getText(context);
        if (text != null) {
            Icon icon = getIcon();

            String textHint = getTextHint();
            boolean bold = isBold();

            DBLookupItem lookupItem;
            CodeCompletionSortingSettings sortingSettings = context.getCodeCompletionSettings().getSortingSettings();
            if (sortingSettings.isEnabled()) {
                int sortingIndex = sortingSettings.getSortingIndexFor(this);
                lookupItem = new DBLookupItem(source, icon, text.toString(), textHint, bold, sortingIndex);
            } else {
                lookupItem = new DBLookupItem(source, icon, text.toString(), textHint, bold);
            }
            context.getResult().addElement(lookupItem);
            return lookupItem;
        }

        return null;
    }


    public abstract boolean isBold();

    public abstract CharSequence getText(CodeCompletionContext completionContext);

    public abstract String getTextHint();

    public abstract Icon getIcon();
}
