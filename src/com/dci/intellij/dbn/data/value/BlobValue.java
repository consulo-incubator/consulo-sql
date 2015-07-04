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

package com.dci.intellij.dbn.data.value;

import com.dci.intellij.dbn.common.util.CommonUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BlobValue implements LazyLoadedValue {
    private Blob blob;

    public BlobValue(Blob blob) {
        this.blob = blob;
    }

    public void updateValue(ResultSet resultSet, int columnIndex, String value) throws SQLException {
        if (blob == null) {
            value = CommonUtil.nvl(value, "");
            resultSet.updateBlob(columnIndex, new ByteArrayInputStream(value.getBytes()));
            //resultSet.updateBinaryStream(columnIndex, new ByteArrayInputStream(value.getBytes()));
            blob = resultSet.getBlob(columnIndex);
        } else {
            if (blob.length() > value.length()) {
                blob.truncate(value.length());
            }

            blob.setBytes(1, value.getBytes());
            resultSet.updateBlob(columnIndex, blob);
            //resultSet.updateBinaryStream(columnIndex, new ByteArrayInputStream(value.getBytes()));
        }
    }

    public String loadValue() throws SQLException {
        return loadValue(0);
    }

    @Override
    public String loadValue(int maxSize) throws SQLException {
        if (blob == null) {
            return null;
        } else {
            try {
                int size = (int) (maxSize == 0 ? blob.length() : Math.min(maxSize, blob.length()));
                byte[] buffer = new byte[size];
                blob.getBinaryStream().read(buffer, 0, size);
                return new String(buffer);
            } catch (IOException e) {
                throw new SQLException("Could not read value from BLOB.");
            }

        }
    }

    @Override
    public long size() throws SQLException {
        return blob == null ? 0 : blob.length();
    }

    public String getDisplayValue() {
        /*try {
            return "[BLOB] " + size() + "";
        } catch (SQLException e) {
            return "[BLOB]";
        }*/

        return "[BLOB]";
    }
}
