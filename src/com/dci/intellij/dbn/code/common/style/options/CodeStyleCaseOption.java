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

package com.dci.intellij.dbn.code.common.style.options;

import com.dci.intellij.dbn.common.options.PersistentConfiguration;
import com.dci.intellij.dbn.common.util.NamingUtil;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;

public class CodeStyleCaseOption implements PersistentConfiguration {
    private String name;
    private CodeStyleCase styleCase;

    public CodeStyleCaseOption(String id, CodeStyleCase styleCase) {
        this.name = id;
        this.styleCase = styleCase;
    }

    public CodeStyleCaseOption() {
    }

    public String getName() {
        return name;
    }

    public CodeStyleCase getStyleCase() {
        return styleCase;
    }

    public void setStyleCase(CodeStyleCase styleCase) {
        this.styleCase = styleCase;
    }

    public String changeCase(String string) {
        if (string != null) {
            switch (styleCase) {
                case UPPER: return string.toUpperCase();
                case LOWER: return string.toLowerCase();
                case CAPITALIZED: return NamingUtil.capitalize(string);
                case PRESERVE: return string;
            }
        }
        return string;
    }

    /*********************************************************
     *                   JDOMExternalizable                  *
     *********************************************************/
    public void readConfiguration(Element element) throws InvalidDataException {
        name = element.getAttributeValue("name");
        String style = element.getAttributeValue("value");
        styleCase =
                style.equals("upper") ? CodeStyleCase.UPPER :
                style.equals("lower") ? CodeStyleCase.LOWER :
                style.equals("capitalized") ? CodeStyleCase.CAPITALIZED :
                style.equals("preserve") ? CodeStyleCase.PRESERVE : CodeStyleCase.PRESERVE;
    }

    public void writeConfiguration(Element element) throws WriteExternalException {
        String value =
                styleCase == CodeStyleCase.UPPER ? "upper" :
                styleCase == CodeStyleCase.LOWER ? "lower" :
                styleCase == CodeStyleCase.CAPITALIZED ? "capitalized" :
                styleCase == CodeStyleCase.PRESERVE ? "preserve" :  null;

        element.setAttribute("name", name);
        element.setAttribute("value", value);
    }
}
