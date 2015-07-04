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

package com.dci.intellij.dbn.language.common.element.util;

import gnu.trove.THashSet;

import java.util.ArrayList;
import java.util.Set;
import java.util.StringTokenizer;

public class ElementTypeAttributesBundle {
    public static final ElementTypeAttributesBundle EMPTY = new ElementTypeAttributesBundle();

    private Set<ElementTypeAttribute> attributes = ElementTypeAttribute.EMPTY_LIST;

    public ElementTypeAttributesBundle(String definition) throws ElementTypeDefinitionException {
        StringTokenizer tokenizer = new StringTokenizer(definition, ",");
        while (tokenizer.hasMoreTokens()) {
            String attributeName = tokenizer.nextToken().trim();
            boolean found = false;
            for (ElementTypeAttribute attribute : ElementTypeAttribute.values()) {
                if (attribute.getName().equals(attributeName)) {
                    if (attributes == ElementTypeAttribute.EMPTY_LIST)
                        attributes = new THashSet<ElementTypeAttribute>();
                    attributes.add(attribute);
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new ElementTypeDefinitionException("Invalid element type attribute '" + attributeName + "'");
            }
        }
    }

    private ElementTypeAttributesBundle() {}

    public boolean is(ElementTypeAttribute attribute) {
        return attributes.contains(attribute);
    }

    @Override
    public String toString() {
        return new ArrayList(attributes).toString();
    }
}
