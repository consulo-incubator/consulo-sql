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

import com.dci.intellij.dbn.common.options.setting.SettingsUtil;
import com.intellij.formatting.Indent;
import org.jdom.Element;

public enum IndentDefinition implements FormattingAttribute<Indent> {
    NORMAL       (new Loader(){Indent load() {return Indent.getNormalIndent();}}),
    CONTINUE     (new Loader(){Indent load() {return Indent.getContinuationIndent();}}),
    NONE         (new Loader(){Indent load() {return Indent.getNoneIndent();}}),
    ABSOLUTE_NONE(new Loader(){Indent load() {return Indent.getAbsoluteNoneIndent();}});

    private Indent value;
    private Loader<Indent> loader;

    private IndentDefinition(Loader<Indent> loader) {
        this.loader = loader;
    }

    public Indent getValue() {
        if (value == null && loader != null) {
            value = loader.load();
            loader = null;
        }
        return value;
    }

    public static IndentDefinition get(Element element) {
        return SettingsUtil.getEnumAttribute(element, "formatting-indent", IndentDefinition.class);
    }
}
