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

package com.dci.intellij.dbn.object.impl;

import com.dci.intellij.dbn.browser.model.BrowserTreeNode;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.editor.DBContentType;
import com.dci.intellij.dbn.object.DBPrivilege;
import com.dci.intellij.dbn.object.DBRole;
import com.dci.intellij.dbn.object.DBUser;
import com.dci.intellij.dbn.object.common.DBObjectImpl;
import com.dci.intellij.dbn.object.common.DBObjectType;
import com.dci.intellij.dbn.object.common.list.DBObjectNavigationList;
import com.dci.intellij.dbn.object.common.list.DBObjectNavigationListImpl;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBPrivilegeImpl extends DBObjectImpl implements DBPrivilege {
    public DBPrivilegeImpl(ConnectionHandler connectionHandler, ResultSet resultSet) throws SQLException {
        super(connectionHandler.getObjectBundle(), DBContentType.NONE, resultSet);
    }

    @Override
    protected void initObject(ResultSet resultSet) throws SQLException {
        name = resultSet.getString("PRIVILEGE_NAME");
    }

    public DBObjectType getObjectType() {
        return DBObjectType.PRIVILEGE;
    }

    public List<DBUser> getUserGrantees() {
        List<DBUser> grantees = new ArrayList<DBUser>();
        for (DBUser user : getConnectionHandler().getObjectBundle().getUsers()) {
            if (user.hasPrivilege(this)) {
                grantees.add(user);
            }
        }
        return grantees;
    }

    public List<DBRole> getRoleGrantees() {
        List<DBRole> grantees = new ArrayList<DBRole>();
        for (DBRole role : getConnectionHandler().getObjectBundle().getRoles()) {
            if (role.hasPrivilege(this)) {
                grantees.add(role);
            }
        }
        return grantees;
    }

    protected List<DBObjectNavigationList> createNavigationLists() {
        List<DBObjectNavigationList> navigationLists = new ArrayList<DBObjectNavigationList>();
        navigationLists.add(new DBObjectNavigationListImpl<DBUser>("User grantees", getUserGrantees()));
        if (getConnectionHandler().getInterfaceProvider().getCompatibilityInterface().supportsObjectType(DBObjectType.ROLE.getTypeId())) {
            navigationLists.add(new DBObjectNavigationListImpl<DBRole>("Role grantees", getRoleGrantees()));    
        }
        return navigationLists;
    }

    /*********************************************************
     *                     TreeElement                       *
     *********************************************************/
    public boolean isLeafTreeElement() {
        return true;
    }


    @NotNull
    public List<BrowserTreeNode> buildAllPossibleTreeChildren() {
        return BrowserTreeNode.EMPTY_LIST;
    }
}
