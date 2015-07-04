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

package com.dci.intellij.dbn.data.sorting;

public class SingleColumnSortingState {
    private SortDirection direction = SortDirection.INDEFINITE;
    private String columnName = "";

    public SortDirection getDirection() {
        return direction;
    }

    public void setDirection(SortDirection direction) {
        this.direction = direction;
    }

    public void setDirectionAsString(String direction) {
        this.direction =
            "ASC".equals(direction) ? SortDirection.ASCENDING :
            "DESC".equals(direction) ? SortDirection.DESCENDING :
                    SortDirection.INDEFINITE;
    }


    public String getColumnName() {
        return columnName == null ? "" : columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public boolean isColumnName(String columnName) {
        return columnName.equals(this.columnName);
    }

    public boolean isDirection(SortDirection direction) {
        return this.direction == direction;
    }

    public String getDirectionAsString() {
        return
            direction == SortDirection.ASCENDING ? "ASC" :
            direction == SortDirection.DESCENDING ? "DESC" :
            "INDEFINITE";
    }

    public void swichDirection() {
        direction =
                direction == SortDirection.ASCENDING ? SortDirection.DESCENDING :
                direction == SortDirection.DESCENDING ? SortDirection.ASCENDING : SortDirection.ASCENDING;
    }

    public boolean isValid() {
        return
            direction != SortDirection.INDEFINITE &&
            columnName != null &&
            columnName.trim().length() > 0;
    }
}
