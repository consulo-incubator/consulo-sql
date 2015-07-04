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

package com.dci.intellij.dbn.connection;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class ConnectionInfo {
    private String productName;
    private String productVersion;
    private String driverName;
    private String driverVersion;
    private String url;
    private String userName;

    public ConnectionInfo(DatabaseMetaData metaData) throws SQLException {
        productName = metaData.getDatabaseProductName();
        productVersion = metaData.getDatabaseProductVersion();
        int index = productVersion.indexOf('\n');
        productVersion = index > -1 ? productVersion.substring(0, index) : productVersion;
        driverName = metaData.getDriverName();
        driverVersion = metaData.getDriverVersion();
        url = metaData.getURL();
        userName = metaData.getUserName();
    }

    public String toString() {
        return  "Product name:\t" + productName + '\n' +
                "Product version:\t" + productVersion + '\n' +
                "Driver name:\t\t" + driverName + '\n' +
                "Driver version:\t" + driverVersion + '\n'+
                "URL:\t\t" + url + '\n' +
                "User name:\t\t" + userName;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductVersion() {
        return productVersion;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getDriverVersion() {
        return driverVersion;
    }

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }
}
