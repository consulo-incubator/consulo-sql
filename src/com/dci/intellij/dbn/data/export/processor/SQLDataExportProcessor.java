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

package com.dci.intellij.dbn.data.export.processor;

import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.data.export.DataExportException;
import com.dci.intellij.dbn.data.export.DataExportFormat;
import com.dci.intellij.dbn.data.export.DataExportInstructions;
import com.dci.intellij.dbn.data.export.DataExportModel;
import com.dci.intellij.dbn.data.type.BasicDataType;
import com.intellij.openapi.util.text.StringUtil;

import java.util.Date;


public class SQLDataExportProcessor extends DataExportProcessor{
    protected DataExportFormat getFormat() {
        return DataExportFormat.SQL;
    }

    @Override
    public String getFileExtension() {
        return "sql";
    }

    @Override
    public String adjustFileName(String fileName) {
        if (!fileName.contains(".sql")) {
            fileName = fileName + ".sql";
        }
        return fileName;
    }

    public boolean canCreateHeader() {
        return false;
    }

    public boolean canExportToClipboard() {
        return true;
    }

    public boolean canQuoteValues() {
        return false;
    }


    public void performExport(DataExportModel model, DataExportInstructions instructions, ConnectionHandler connectionHandler) throws DataExportException {
        StringBuilder buffer = new StringBuilder();
        for (int rowIndex=0; rowIndex < model.getRowCount(); rowIndex++) {
            buffer.append("insert into ");
            buffer.append(model.getTableName());
            buffer.append(" (");

            int realColumnIndex = 0;
            for (int columnIndex=0; columnIndex < model.getColumnCount(); columnIndex++){
                BasicDataType basicDataType = model.getBasicDataType(columnIndex);
                if (basicDataType == BasicDataType.LITERAL ||
                        basicDataType == BasicDataType.NUMERIC ||
                        basicDataType == BasicDataType.DATE_TIME) {
                    if (realColumnIndex > 0) buffer.append(", ");
                    buffer.append(model.getColumnName(columnIndex));
                    realColumnIndex++;
                }
            }
            buffer.append(") values (");

            realColumnIndex = 0;
            for (int columnIndex=0; columnIndex < model.getColumnCount(); columnIndex++){
                BasicDataType basicDataType = model.getBasicDataType(columnIndex);
                if (basicDataType == BasicDataType.LITERAL ||
                        basicDataType == BasicDataType.NUMERIC ||
                        basicDataType == BasicDataType.DATE_TIME) {
                    if (columnIndex > 0) buffer.append(", ");
                    Object object = model.getValue(rowIndex, columnIndex);
                    String value = object == null ? null : object.toString();
                    if (value == null) {
                        buffer.append("null");
                    } else {
                        if (basicDataType == BasicDataType.LITERAL) {
                            buffer.append("'");
                            value = StringUtil.replace(value, "'", "''");
                            buffer.append(value);
                            buffer.append("'");
                        } else if (basicDataType == BasicDataType.NUMERIC) {
                            buffer.append(value);
                        } else if (basicDataType == BasicDataType.DATE_TIME) {
                            Date date = (Date) object;
                            String dateString = connectionHandler.getInterfaceProvider().getMetadataInterface().createDateString(date);
                            buffer.append(dateString);
                        }
                    }
                    realColumnIndex++;
                }
            }

            buffer.append(");\n\n");
        }
        writeContent(instructions, buffer.toString());
    }
}
