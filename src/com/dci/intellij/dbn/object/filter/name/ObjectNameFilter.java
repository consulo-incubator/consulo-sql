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

package com.dci.intellij.dbn.object.filter.name;

import com.dci.intellij.dbn.object.common.DBObjectType;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;

public class ObjectNameFilter extends CompoundFilterCondition {
    private ObjectNameFilterSettings settings;
    private DBObjectType objectType;
    private int hashCode;


    public ObjectNameFilter(ObjectNameFilterSettings settings) {
        this.settings = settings;
    }

    public ObjectNameFilterSettings getSettings() {
        return settings;
    }

    public ObjectNameFilter(ObjectNameFilterSettings settings, DBObjectType objectType, ConditionOperator operator, String text) {
        this.settings = settings;
        this.objectType = objectType;
        addCondition(operator, text);
    }

    public ObjectNameFilter(ObjectNameFilterSettings settings, DBObjectType objectType, SimpleFilterCondition condition) {
        this.settings = settings;
        this.objectType = objectType;
        addCondition(condition);
    }

    @Override
    protected void cleanup() {
        super.cleanup();
        if (getConditions().isEmpty()) {
            settings.removeFilter(this);
        }
    }

    public DBObjectType getObjectType() {
        return objectType;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    /*********************************************************
     *                     Configuration                     *
     *********************************************************/
    @Override
    public void readConfiguration(Element element) throws InvalidDataException {
        super.readConfiguration(element);
        objectType = DBObjectType.getObjectType(element.getAttributeValue("object-type"));
        hashCode = toString().hashCode();
    }

    @Override
    public void writeConfiguration(Element element) throws WriteExternalException {
        super.writeConfiguration(element);
        element.setAttribute("object-type", objectType.getName());
    }
}
