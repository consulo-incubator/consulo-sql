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

package com.dci.intellij.dbn.data.find;

import com.dci.intellij.dbn.data.model.DataModelCell;
import com.intellij.openapi.Disposable;

public class DataSearchResultMatch implements Disposable {
    private DataModelCell cell;
    private int startOffset;
    private int endOffset;

    public DataSearchResultMatch(DataModelCell cell, int startOffset, int endOffset) {
        this.cell = cell;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
    }

    public DataModelCell getCell() {
        return cell;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }

    @Override
    public void dispose() {
        cell = null;
    }
}
