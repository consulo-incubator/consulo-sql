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

import com.dci.intellij.dbn.object.common.DBSchemaObject;

import java.util.List;

public interface DBConstraint extends DBSchemaObject {
    int DEFAULT = 0;
    int CHECK = 1;
    int PRIMARY_KEY = 2;
    int UNIQUE_KEY = 3;
    int FOREIGN_KEY = 4;
    int VIEW_CHECK = 5;
    int VIEW_READONLY = 6;
    
    int getConstraintType();
    boolean isPrimaryKey();
    boolean isForeignKey();
    boolean isUniqueKey();
    DBDataset getDataset();

    DBConstraint getForeignKeyConstraint();

    List<DBColumn> getColumns();
    int getColumnPosition(DBColumn constraint);
    DBColumn getColumnForPosition(int position);
}