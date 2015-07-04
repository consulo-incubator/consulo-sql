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

package com.dci.intellij.dbn.editor.data.filter.global;

import com.dci.intellij.dbn.object.DBColumn;

import java.util.ArrayList;
import java.util.List;

public class DataDependencyPath {
    private List<DBColumn> pathElements = new ArrayList<DBColumn>();

    private DataDependencyPath() {}

    public DataDependencyPath(DBColumn startElement) {
        addPathElement(startElement);
    }

    public void addPathElement(DBColumn pathElement) {
        pathElements.add(pathElement);
    }

    public boolean isRecursiveElement(DBColumn element) {
        for (DBColumn pathElement : pathElements ) {
            if (pathElement.getDataset() == element.getDataset()) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return pathElements.size();
    }

    protected Object clone() {
        DataDependencyPath clone = new DataDependencyPath();
        clone.pathElements.addAll(pathElements);
        return clone; 
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        DBColumn lastElement = pathElements.get(pathElements.size() - 1);
        for (DBColumn pathElement : pathElements) {
            buffer.append(pathElement.getDataset().getName());
            buffer.append(".");
            buffer.append(pathElement.getName());
            if (lastElement != pathElement) {
                buffer.append(" > ");
            }
        }
        return buffer.toString();
    }
}
