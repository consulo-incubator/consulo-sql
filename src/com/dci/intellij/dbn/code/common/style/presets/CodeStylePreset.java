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

package com.dci.intellij.dbn.code.common.style.presets;

import com.dci.intellij.dbn.language.common.psi.BasePsiElement;
import com.intellij.formatting.Spacing;
import com.intellij.formatting.Wrap;
import com.intellij.formatting.WrapType;
import com.intellij.psi.codeStyle.CodeStyleSettings;

public interface CodeStylePreset {
    Wrap WRAP_NONE = Wrap.createWrap(WrapType.NONE, false);
    Wrap WRAP_NORMAL = Wrap.createWrap(WrapType.NORMAL, true);
    Wrap WRAP_ALWAYS = Wrap.createWrap(WrapType.ALWAYS, true);
    Wrap WRAP_IF_LONG = Wrap.createWrap(WrapType.CHOP_DOWN_IF_LONG, true);

    Spacing SPACING_NO_SPACE = Spacing.createSpacing(0, 0, 0, false, 0);
    Spacing SPACING_ONE_SPACE = Spacing.createSpacing(1, 1, 0, false, 0);

    Spacing SPACING_LINE_BREAK = Spacing.createSpacing(0, Integer.MAX_VALUE, 1, true, 0);
    Spacing SPACING_MIN_LINE_BREAK = Spacing.createSpacing(0, Integer.MAX_VALUE, 1, true, 4);

    Spacing SPACING_ONE_LINE = Spacing.createSpacing(0, Integer.MAX_VALUE, 2, true, 1);
    Spacing SPACING_MIN_ONE_LINE = Spacing.createSpacing(0, Integer.MAX_VALUE, 2, true, 4);


    String getId();
    String getName();
    boolean accepts(BasePsiElement psiElement);
    Wrap getWrap(BasePsiElement psiElement, CodeStyleSettings settings);
    Spacing getSpacing(BasePsiElement psiElement, CodeStyleSettings settings);
}
