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

public class SortingInstruction<T> {
    private T column;
    private SortDirection direction;

    public SortingInstruction(T column, SortDirection direction) {
        this.column = column;
        this.direction = direction;
    }

    public T getColumn() {
        return column;
    }

    public void setColumn(T column) {
        this.column = column;
    }

    public SortDirection getDirection() {
        return direction;
    }

    public void setDirection(SortDirection direction) {
        this.direction = direction;
    }

    public void switchDirection() {
        if (direction == SortDirection.ASCENDING) {
            direction = SortDirection.DESCENDING;
        } else if (direction == SortDirection.DESCENDING) {
            direction = SortDirection.ASCENDING;
        }
    }

    public SortingInstruction<T> clone() {
        return new SortingInstruction<T>(column, direction);
    }
}
