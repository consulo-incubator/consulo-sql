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

import com.dci.intellij.dbn.object.DBColumn;
import com.dci.intellij.dbn.object.DBConstraint;
import com.dci.intellij.dbn.object.common.DBObjectRelationType;
import com.dci.intellij.dbn.object.common.list.DBObjectRelationImpl;

public class DBConstraintColumnRelation extends DBObjectRelationImpl<DBConstraint, DBColumn> {
    private int position;
    public DBConstraintColumnRelation(DBConstraint constraint, DBColumn column, int position) {
        super(DBObjectRelationType.CONSTRAINT_COLUMN, constraint, column);
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public DBConstraint getConstraint() {
        return getSourceObject();
    }

    public DBColumn getColumn() {
        return getTargetObject();
    }
}
