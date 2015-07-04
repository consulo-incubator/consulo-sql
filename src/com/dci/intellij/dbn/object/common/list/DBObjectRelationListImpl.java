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

package com.dci.intellij.dbn.object.common.list;

import com.dci.intellij.dbn.common.content.DynamicContentImpl;
import com.dci.intellij.dbn.common.content.dependency.ContentDependencyAdapter;
import com.dci.intellij.dbn.common.content.loader.DynamicContentLoader;
import com.dci.intellij.dbn.common.filter.Filter;
import com.dci.intellij.dbn.connection.GenericDatabaseElement;
import com.dci.intellij.dbn.object.common.DBObject;
import com.dci.intellij.dbn.object.common.DBObjectRelationType;
import com.dci.intellij.dbn.object.filter.name.ObjectNameFilterSettings;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DBObjectRelationListImpl<T extends DBObjectRelation> extends DynamicContentImpl<T> implements DBObjectRelationList<T>{
    private DBObjectRelationType objectRelationType;
    private String name;

    public DBObjectRelationListImpl(DBObjectRelationType type, GenericDatabaseElement parent, String name, DynamicContentLoader<T> loader, ContentDependencyAdapter dependencyAdapter) {
        super(parent, loader, dependencyAdapter, false);
        this.objectRelationType = type;
        this.name = name;
    }

    @NotNull
    public List<T> getObjectRelations() {
        return getElements();
    }

    public Filter getFilter() {
        ObjectNameFilterSettings nameFilterSettings = getConnectionHandler().getSettings().getFilterSettings().getObjectNameFilterSettings();
        return nameFilterSettings.getFilter(objectRelationType);
    }

    public DBObjectRelationType getObjectRelationType() {
        return objectRelationType;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    public List<DBObjectRelation> getRelationBySourceName(String sourceName) {
        List<DBObjectRelation> objectRelations = new ArrayList<DBObjectRelation>();
        for (DBObjectRelation objectRelation : getElements()) {
            if (objectRelation.getSourceObject().getName().equals(sourceName)) {
                objectRelations.add(objectRelation);
            }
        }
        return objectRelations;
    }

    public List<DBObjectRelation> getRelationByTargetName(String targetName) {
        List<DBObjectRelation> objectRelations = new ArrayList<DBObjectRelation>();
        for (DBObjectRelation objectRelation : getElements()) {
            if (objectRelation.getTargetObject().getName().equals(targetName)) {
                objectRelations.add(objectRelation);
            }
        }
        return objectRelations;
    }


    /*********************************************************
     *                   DynamicContent                      *
     *********************************************************/

    public Project getProject() {
        return getParent().getProject();
    }

   public String getContentDescription() {
        if (getParent() instanceof DBObject) {
            DBObject object = (DBObject) getParent();
            return getName() + " of " + object.getQualifiedNameWithType();
        }
        return getName() + " from " + getConnectionHandler().getName() ;
    }

    public void notifyChangeListeners() {}
}
