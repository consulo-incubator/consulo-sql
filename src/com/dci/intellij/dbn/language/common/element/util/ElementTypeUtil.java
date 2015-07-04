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

import com.dci.intellij.dbn.language.common.element.ElementType;
import com.dci.intellij.dbn.language.common.element.NamedElementType;
import com.dci.intellij.dbn.language.common.element.path.PathNode;

public class ElementTypeUtil {
    public static ElementType getEnclosingElementType(PathNode pathNode, ElementTypeAttribute elementTypeAttribute) {
        PathNode parentNode = pathNode.getParent();
        while (parentNode != null) {
            ElementType elementType = parentNode.getElementType();
            if (elementType.is(elementTypeAttribute)) return elementType;
            parentNode = parentNode.getParent();
        }
        return null;
    }

    public static NamedElementType getEnclosingNamedElementType(PathNode pathNode) {
        PathNode parentNode = pathNode.getParent();
        while (parentNode != null) {
            ElementType elementType = parentNode.getElementType();
            if (elementType instanceof NamedElementType) return (NamedElementType) elementType;
            parentNode = parentNode.getParent();
        }
        return null;
    }
    
}
