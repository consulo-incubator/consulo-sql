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

package com.dci.intellij.dbn.object.common.list.loader;

import com.dci.intellij.dbn.common.content.DynamicContent;
import com.dci.intellij.dbn.common.content.DynamicContentElement;
import com.dci.intellij.dbn.common.content.loader.DynamicSubcontentCustomLoader;
import com.dci.intellij.dbn.object.common.DBObject;
import com.dci.intellij.dbn.object.common.list.DBObjectList;
import com.dci.intellij.dbn.object.common.list.DBObjectRelation;

public class DBObjectListFromRelationListLoader<T extends DynamicContentElement> extends DynamicSubcontentCustomLoader<T> {
    public T resolveElement(DynamicContent<T> dynamicContent, DynamicContentElement sourceElement) {
        DBObjectList objectList = (DBObjectList) dynamicContent;
        DBObjectRelation objectRelation = (DBObjectRelation) sourceElement;
        DBObject object = (DBObject) objectList.getTreeParent();

        if (object.equals(objectRelation.getSourceObject())) {
            return (T) objectRelation.getTargetObject();
        }
        if (object.equals(objectRelation.getTargetObject())) {
            return (T) objectRelation.getSourceObject();
        }

        return null;
    }
}
