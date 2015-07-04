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

package com.dci.intellij.dbn.object;

import com.dci.intellij.dbn.object.common.DBObject;

import java.util.List;

public interface DBSchema extends DBObject {
    boolean isPublicSchema();
    boolean isUserSchema();
    boolean isSystemSchema();
    List<DBDataset> getDatasets();
    List<DBTable> getTables();
    List<DBView> getViews();
    List<DBMaterializedView> getMaterializedViews();
    List<DBIndex> getIndexes();
    List<DBSynonym> getSynonyms();
    List<DBSequence> getSequences();
    List<DBProcedure> getProcedures();
    List<DBFunction> getFunctions();
    List<DBPackage> getPackages();
    List<DBTrigger> getTriggers();
    List<DBType> getTypes();
    List<DBDimension> getDimensions();
    List<DBCluster> getClusters();
    List<DBDatabaseLink> getDatabaseLinks();

    DBDataset getDataset(String name);
    DBTable getTable(String name);
    DBView getView(String name);
    DBMaterializedView getMaterializedView(String name);
    DBIndex getIndex(String name);
    DBType getType(String name);
    DBPackage getPackage(String name);
    DBProgram getProgram(String name);
    DBMethod getMethod(String name, String type);
    DBMethod getMethod(String name);
    DBProcedure getProcedure(String name);
    DBFunction getFunction(String name);
    DBCluster getCluster(String name);
    DBDatabaseLink getDatabaseLink(String name);

    void refreshObjectsStatus();
}
