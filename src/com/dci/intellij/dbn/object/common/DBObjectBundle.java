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

package com.dci.intellij.dbn.object.common;

import com.dci.intellij.dbn.browser.model.BrowserTreeNode;
import com.dci.intellij.dbn.common.lookup.ConsumerStoppedException;
import com.dci.intellij.dbn.common.lookup.LookupConsumer;
import com.dci.intellij.dbn.data.type.DBNativeDataType;
import com.dci.intellij.dbn.database.DatabaseObjectIdentifier;
import com.dci.intellij.dbn.object.DBCharset;
import com.dci.intellij.dbn.object.DBPrivilege;
import com.dci.intellij.dbn.object.DBRole;
import com.dci.intellij.dbn.object.DBSchema;
import com.dci.intellij.dbn.object.DBUser;
import com.dci.intellij.dbn.object.common.list.DBObjectListContainer;
import com.intellij.openapi.Disposable;

import java.util.List;

public interface DBObjectBundle extends BrowserTreeNode, Disposable {
    List<DBSchema> getSchemas();
    List<DBUser> getUsers();
    List<DBRole> getRoles();
    List<DBPrivilege> getPrivileges();
    List<DBCharset> getCharsets();
    List<DBNativeDataType> getNativeDataTypes();
    DBNativeDataType getNativeDataType(String name);

    DBSchema getSchema(String name);
    DBSchema getPublicSchema();
    DBSchema getUserSchema();
    DBUser getUser(String name);
    DBRole getRole(String name);
    DBPrivilege getPrivilege(String name);
    DBCharset getCharset(String name);


    DBObject getObject(DatabaseObjectIdentifier objectIdentifier);
    DBObject getObject(DBObjectType objectType, String name);
    void lookupObjectsOfType(LookupConsumer consumer, DBObjectType objectType) throws ConsumerStoppedException;
    void lookupChildObjectsOfType(LookupConsumer consumer, DBObject parent, DBObjectType objectType, ObjectTypeFilter filter, DBSchema currentSchema) throws ConsumerStoppedException;
    void refreshObjectsStatus();

    DBObjectListContainer getObjectListContainer();
    boolean isValid();
}
