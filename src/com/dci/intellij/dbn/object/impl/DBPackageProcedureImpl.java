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

import com.dci.intellij.dbn.editor.DBContentType;
import com.dci.intellij.dbn.object.DBPackage;
import com.dci.intellij.dbn.object.DBPackageProcedure;
import com.dci.intellij.dbn.object.DBProgram;
import com.dci.intellij.dbn.object.common.DBObjectType;
import com.dci.intellij.dbn.object.common.property.DBObjectProperty;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBPackageProcedureImpl extends DBProcedureImpl implements DBPackageProcedure {
    private int overload;

    public DBPackageProcedureImpl(DBPackage packagee, ResultSet resultSet) throws SQLException {
        super(packagee, resultSet);
    }

    @Override
    protected void initObject(ResultSet resultSet) throws SQLException {
        name = resultSet.getString("PROCEDURE_NAME");
        overload = resultSet.getInt("OVERLOAD");
    }

    @Override
    public void initStatus(ResultSet resultSet) throws SQLException {}

    @Override
    public void initProperties() {
        getProperties().set(DBObjectProperty.NAVIGABLE);
    }

    public DBPackage getPackage() {
        return (DBPackage) getParentObject();
    }

    @Override
    public DBProgram getProgram() {
        return getPackage();
    }


    public int getOverload() {
        return overload;
    }

    public boolean isEmbedded() {
        return true;
    }

    @Override
    public String getPresentableTextDetails() {
        return getOverload() > 0 ? " - " + getOverload() : "";
    }

    @Override
    public DBObjectType getObjectType() {
        return DBObjectType.PACKAGE_PROCEDURE;
    }

    public void executeUpdateDDL(DBContentType contentType, String oldCode, String newCode) throws SQLException {}

    @Override
    public void dispose() {
        super.dispose();
        //packagee = null;
    }
}