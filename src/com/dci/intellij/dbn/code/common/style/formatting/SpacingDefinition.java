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

import com.dci.intellij.dbn.code.common.style.presets.CodeStylePreset;
import com.dci.intellij.dbn.common.options.setting.SettingsUtil;
import com.intellij.formatting.Spacing;
import org.jdom.Element;

public enum SpacingDefinition implements FormattingAttribute<Spacing>{
    NO_SPACE  (new Loader(){Spacing load(){return CodeStylePreset.SPACING_NO_SPACE;}}),
    ONE_SPACE (new Loader(){Spacing load(){return CodeStylePreset.SPACING_ONE_SPACE;}}),

    LINE_BREAK (new Loader(){Spacing load(){return CodeStylePreset.SPACING_LINE_BREAK;}}),
    ONE_LINE  (new Loader(){Spacing load(){return CodeStylePreset.SPACING_ONE_LINE;}}),

    MIN_LINE_BREAK (new Loader(){Spacing load(){return CodeStylePreset.SPACING_MIN_LINE_BREAK;}}),
    MIN_ONE_LINE  (new Loader(){Spacing load(){return CodeStylePreset.SPACING_MIN_ONE_LINE;}}),
    ;

    private Spacing value;
    private Loader<Spacing> loader;

    private SpacingDefinition(Loader<Spacing> loader) {
        this.loader = loader;
    }

    public Spacing getValue() {
        if (value == null && loader != null) {
            value = loader.load();
            loader = null;
        }
        return value;
    }

    public static SpacingDefinition get(Element element, boolean before) {
        return before ?
                SettingsUtil.getEnumAttribute(element, "formatting-spacing-before", SpacingDefinition.class) :
                SettingsUtil.getEnumAttribute(element, "formatting-spacing-after", SpacingDefinition.class);
    }
}
