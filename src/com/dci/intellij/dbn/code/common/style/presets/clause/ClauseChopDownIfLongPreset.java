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

package com.dci.intellij.dbn.code.common.style.presets.clause;

import com.dci.intellij.dbn.language.common.psi.BasePsiElement;
import com.intellij.formatting.Spacing;
import com.intellij.formatting.Wrap;
import com.intellij.psi.codeStyle.CodeStyleSettings;

public class ClauseChopDownIfLongPreset extends ClauseAbstractPreset {
    public ClauseChopDownIfLongPreset() {
        super("chop_down_if_long", "Chop down if long");
    }

    public Wrap getWrap(BasePsiElement psiElement, CodeStyleSettings settings) {
        boolean shouldWrap = psiElement.approximateLength() > settings.RIGHT_MARGIN;
        return shouldWrap ? WRAP_ALWAYS : WRAP_NONE;

    }

    public Spacing getSpacing(BasePsiElement psiElement, CodeStyleSettings settings) {
        boolean shouldChopDown = psiElement.approximateLength() > settings.RIGHT_MARGIN;
        return getSpacing(psiElement, shouldChopDown);
    }
}