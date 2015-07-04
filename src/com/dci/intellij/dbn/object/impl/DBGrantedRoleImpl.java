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
import com.dci.intellij.dbn.editor.DBContentType;
import com.dci.intellij.dbn.object.DBGrantedRole;
import com.dci.intellij.dbn.object.DBRole;
import com.dci.intellij.dbn.object.DBRoleGrantee;
import com.dci.intellij.dbn.object.common.DBObject;
import com.dci.intellij.dbn.object.common.DBObjectImpl;
import com.dci.intellij.dbn.object.common.DBObjectType;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DBGrantedRoleImpl extends DBObjectImpl implements DBGrantedRole {
    private DBRole role;
    private boolean isAdminOption;
    private boolean isDefaultRole;

    public DBGrantedRoleImpl(DBRoleGrantee grantee, ResultSet resultSet) throws SQLException {
        super(grantee, DBContentType.NONE, resultSet);
    }

    @Override
    protected void initObject(ResultSet resultSet) throws SQLException {
        this.name = resultSet.getString("GRANTED_ROLE_NAME");
        this.role = getConnectionHandler().getObjectBundle().getRole(name);
        this.isAdminOption = resultSet.getString("IS_ADMIN_OPTION").equals("Y");
        this.isDefaultRole = resultSet.getString("IS_DEFAULT_ROLE").equals("Y");
    }

    public DBObjectType getObjectType() {
        return DBObjectType.GRANTED_ROLE;
    }

    public DBRoleGrantee getGrantee() {
        return (DBRoleGrantee) getParentObject();
    }

    public DBRole getRole() {
        return role;
    }

    public boolean isAdminOption() {
        return isAdminOption;
    }

    public boolean isDefaultRole() {
        return isDefaultRole;
    }

    @Override
    public DBObject getDefaultNavigationObject() {
        return role;
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
