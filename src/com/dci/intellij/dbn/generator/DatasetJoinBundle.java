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

package com.dci.intellij.dbn.generator;

import com.dci.intellij.dbn.object.DBDataset;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DatasetJoinBundle {
    private List<DatasetJoin> joins = new ArrayList<DatasetJoin>();

    public DatasetJoinBundle(Set<DBDataset> datasets, boolean lenient) {
        for (DBDataset dataset1 : datasets) {
            for (DBDataset dataset2 : datasets) {
                if (!dataset1.equals(dataset2)) {
                    createJoin(dataset1, dataset2, lenient);
                }
            }
        }
    }

    private void createJoin(DBDataset dataset1, DBDataset dataset2, boolean lenient) {
        if (!contains(dataset1, dataset2)) {
            DatasetJoin datasetJoin = new DatasetJoin(dataset1, dataset2, lenient);
            if (!datasetJoin.isEmpty()) {
                joins.add(datasetJoin);
            }
        }
    }

    protected boolean contains(DBDataset... datasets) {
        for (DatasetJoin datasetJoin : joins) {
            if (datasetJoin.contains(datasets)) return true;
        }
        return false;
    }

    public List<DatasetJoin> getJoins() {
        return joins;
    }

    public boolean isEmpty() {
        for (DatasetJoin join : joins) {
            if (!join.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
