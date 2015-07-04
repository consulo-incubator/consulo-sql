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

package com.dci.intellij.dbn.data.export;

import com.dci.intellij.dbn.data.type.BasicDataType;
import com.intellij.openapi.project.Project;

public interface DataExportModel {
    String getTableName();
    int getColumnCount();
    int getRowCount();
    Object getValue(int rowIndex, int columnIndex);
    String getColumnName(int columnIndex);
    BasicDataType getBasicDataType(int columnIndex);
    Project getProject();
}
