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

package com.dci.intellij.dbn.database;

import com.dci.intellij.dbn.object.DBMethod;
import com.dci.intellij.dbn.object.DBTrigger;
import com.dci.intellij.dbn.object.factory.MethodFactoryInput;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseDDLInterface {
    int getEditorHeaderEndOffset(DatabaseObjectTypeId objectTypeId, String objectName, String sourceContent);

    boolean includesTypeAndNameInSourceContent(DatabaseObjectTypeId objectTypeId);

    String createTriggerEditorHeader(DBTrigger trigger);

    String createMethodEditorHeader(DBMethod method);

    /*********************************************************
     *                   CREATE statements                   *
     *********************************************************/
    void createView(String viewName, String code, Connection connection) throws SQLException;

    void createMethod(MethodFactoryInput methodFactoryInput, Connection connection) throws SQLException;

    void createObject(String code, Connection connection) throws SQLException;

    /*********************************************************
     *                   CHANGE statements                   *
     *********************************************************/
    void updateView(String viewName, String oldCode, String newCode, Connection connection) throws SQLException;

    void updateObject(String objectName, String objectType, String oldCode, String newCode, Connection connection) throws SQLException;

   /*********************************************************
    *                   DROP statements                     *
    *********************************************************/
    void dropObject(String objectType, String objectName, Connection connection) throws SQLException;

   /*********************************************************
    *                   RENAME statements                     *
    *********************************************************/

}
