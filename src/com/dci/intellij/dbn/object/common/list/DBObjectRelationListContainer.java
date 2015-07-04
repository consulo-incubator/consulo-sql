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

import com.dci.intellij.dbn.common.content.dependency.ContentDependencyAdapter;
import com.dci.intellij.dbn.common.content.dependency.MultipleContentDependencyAdapter;
import com.dci.intellij.dbn.common.content.dependency.SubcontentDependencyAdapterImpl;
import com.dci.intellij.dbn.common.content.loader.DynamicContentLoader;
import com.dci.intellij.dbn.common.dispose.DisposeUtil;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.connection.GenericDatabaseElement;
import com.dci.intellij.dbn.database.DatabaseCompatibilityInterface;
import com.dci.intellij.dbn.object.common.DBObject;
import com.dci.intellij.dbn.object.common.DBObjectRelationType;
import com.intellij.openapi.Disposable;

import java.util.ArrayList;
import java.util.List;

public class DBObjectRelationListContainer implements Disposable {
    private GenericDatabaseElement owner;
    private List<DBObjectRelationList> objectRelationLists;

    public DBObjectRelationListContainer(GenericDatabaseElement owner) {
        this.owner = owner;
    }

    public List<DBObjectRelationList> getObjectRelationLists() {
        return objectRelationLists;
    }

    private boolean isSupported(DBObjectRelationType objectRelationType) {
        ConnectionHandler connectionHandler = owner.getConnectionHandler();
        DatabaseCompatibilityInterface compatibilityInterface = DatabaseCompatibilityInterface.getInstance(connectionHandler);
        return connectionHandler == null ||
                (compatibilityInterface.supportsObjectType(objectRelationType.getSourceType().getTypeId()) &&
                 compatibilityInterface.supportsObjectType(objectRelationType.getTargetType().getTypeId()));
    }

    public DBObjectRelationList getObjectRelationList(DBObjectRelationType objectRelationType) {
        if (objectRelationLists != null) {
            for (DBObjectRelationList objectRelationList : objectRelationLists) {
                if (objectRelationList.getObjectRelationType() == objectRelationType) {
                    return objectRelationList;
                }
            }
        }
        return null;
    }

    public DBObjectRelationList createObjectRelationList(
            DBObjectRelationType type,
            GenericDatabaseElement parent,
            String name,
            DynamicContentLoader loader,
            DBObjectList ... sourceContents) {
        if (isSupported(type)) {
            ContentDependencyAdapter dependencyAdapter = new MultipleContentDependencyAdapter(parent.getConnectionHandler(), sourceContents);
            return createObjectRelationList(type, parent, name, loader, dependencyAdapter);
        }
        return null;
    }

    public DBObjectRelationList createSubcontentObjectRelationList(
            DBObjectRelationType relationType,
            GenericDatabaseElement parent,
            String name,
            DynamicContentLoader loader,
            DBObject sourceContentObject) {
        if (isSupported(relationType)) {
            ContentDependencyAdapter dependencyAdapter = new SubcontentDependencyAdapterImpl(sourceContentObject, relationType);
            return createObjectRelationList(relationType, parent, name, loader, dependencyAdapter);
        }
        return null;
    }


    private DBObjectRelationList createObjectRelationList(
            DBObjectRelationType type,
            GenericDatabaseElement parent,
            String name,
            DynamicContentLoader loader,
            ContentDependencyAdapter dependencyAdapter) {
        if (isSupported(type)) {
            DBObjectRelationList objectRelationList = new DBObjectRelationListImpl(type, parent, name, loader, dependencyAdapter);
            if (objectRelationLists == null) objectRelationLists = new ArrayList<DBObjectRelationList>();
            objectRelationLists.add(objectRelationList);
            return objectRelationList;
        }
        return null;
    }

    public void dispose() {
        DisposeUtil.disposeCollection(objectRelationLists);
        owner = null;
    }

    public void reload(boolean recursive) {
        for (DBObjectRelationList objectRelationList : objectRelationLists) {
            objectRelationList.reload(recursive);
        }        
    }
}
