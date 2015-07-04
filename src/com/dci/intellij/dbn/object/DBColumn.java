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

import com.dci.intellij.dbn.data.type.DBDataType;
import com.dci.intellij.dbn.object.common.DBObject;

import java.util.List;

public interface DBColumn extends DBObject {
    DBDataType getDataType();
    boolean isPrimaryKey();
    boolean isSinglePrimaryKey();
    boolean isForeignKey();
    boolean isUniqueKey();
    boolean isNullable();
    boolean isHidden();
    DBDataset getDataset();
    int getPosition();

    DBColumn getForeignKeyColumn();
    List<DBColumn> getReferencingColumns();  // foreign key columns referencing to this
    List<DBIndex> getIndexes();
    List<DBConstraint> getConstraints();

    int getConstraintPosition(DBConstraint constraint);
    DBConstraint getConstraintForPosition(int position);


}

