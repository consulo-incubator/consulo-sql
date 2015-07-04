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
import com.dci.intellij.dbn.object.DBProgram;
import com.dci.intellij.dbn.object.DBType;
import com.dci.intellij.dbn.object.DBTypeProcedure;
import com.dci.intellij.dbn.object.common.DBObjectType;
import com.dci.intellij.dbn.object.common.property.DBObjectProperty;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBTypeProcedureImpl extends DBProcedureImpl implements DBTypeProcedure {
    private int overload;

    public DBTypeProcedureImpl(DBType type, ResultSet resultSet) throws SQLException {
        super(type, resultSet);
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

    public DBType getType() {
        return (DBType) getParentObject();
    }

    @Override
    public DBProgram getProgram() {
        return getType();
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
        return DBObjectType.TYPE_PROCEDURE;
    }

    public void executeUpdateDDL(DBContentType contentType, String oldCode, String newCode) throws SQLException {}
}