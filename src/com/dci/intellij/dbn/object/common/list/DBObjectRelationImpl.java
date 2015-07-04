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

import com.dci.intellij.dbn.common.content.DynamicContent;
import com.dci.intellij.dbn.object.common.DBObject;
import com.dci.intellij.dbn.object.common.DBObjectRelationType;
import org.jetbrains.annotations.NotNull;

public abstract class DBObjectRelationImpl<S extends DBObject, T extends DBObject> implements DBObjectRelation<S, T> {

    private DBObjectRelationType objectRelationType;
    private boolean isDisposed = false;
    private S sourceObject;
    private T targetObject;
    private DynamicContent ownerContent;

    public DBObjectRelationImpl(DBObjectRelationType objectRelationType, S sourceObject, T targetObject) {
        this.objectRelationType = objectRelationType;
        assert sourceObject.getObjectType() == objectRelationType.getSourceType();
        assert targetObject.getObjectType() == objectRelationType.getTargetType();
        this.sourceObject = sourceObject;
        this.targetObject = targetObject;
    }



    public DBObjectRelationType getObjectRelationType() {
        return objectRelationType;
    }

    public S getSourceObject() {
        return sourceObject;
    }

    public void setSourceObject(S sourceObject) {
        this.sourceObject = sourceObject;
    }

    public T getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(T targetObject) {
        this.targetObject = targetObject;
    }

    public String toString() {
        return sourceObject.getQualifiedNameWithType() + " => " + targetObject.getQualifiedNameWithType();
    }

    /*********************************************************
    *               DynamicContentElement                   *
    *********************************************************/
    public DynamicContent getOwnerContent() {
        return ownerContent;
    }

    public void setOwnerContent(DynamicContent ownerContent) {
        this.ownerContent = ownerContent;
    }

    public boolean isDisposed() {
        return isDisposed;
    }

    public String getName() {
        return null;
    }

    public String getDescription() {
        return null;
    }

    public void dispose() {
        isDisposed = true;
        sourceObject = null;
        targetObject = null;
        ownerContent = null;
    }

    public void reload() {
    }

    public int compareTo(@NotNull Object o) {
        DBObjectRelationImpl remote = (DBObjectRelationImpl) o;
        return sourceObject.compareTo(remote.getSourceObject());
    }

}
