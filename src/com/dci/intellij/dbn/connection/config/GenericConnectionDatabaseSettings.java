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

package com.dci.intellij.dbn.connection.config;

import com.dci.intellij.dbn.common.util.FileUtil;
import com.dci.intellij.dbn.common.util.StringUtil;
import com.dci.intellij.dbn.connection.ConnectionBundle;
import com.dci.intellij.dbn.connection.config.ui.GenericDatabaseSettingsForm;
import com.intellij.openapi.vfs.VirtualFile;
import org.jdom.Element;

import java.io.File;

public class GenericConnectionDatabaseSettings extends ConnectionDatabaseSettings {
    protected String driverLibrary;
    protected String driver;
    protected String databaseUrl;

    public GenericConnectionDatabaseSettings(ConnectionBundle connectionBundle) {
        super(connectionBundle.getProject(), connectionBundle);
    }

    public GenericDatabaseSettingsForm createConfigurationEditor() {
        return new GenericDatabaseSettingsForm(this);
    }

    public String getDriverLibrary() {
        return driverLibrary;
    }

    public void setDriverLibrary(String driverLibrary) {
        this.driverLibrary = driverLibrary;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public void updateHashCode() {
        hashCode = (name + driver + driverLibrary + databaseUrl + user + osAuthentication).hashCode();
    }

    public GenericConnectionDatabaseSettings clone() {
        Element connectionElement = new Element("Connection");
        writeConfiguration(connectionElement);
        GenericConnectionDatabaseSettings clone = new GenericConnectionDatabaseSettings(connectionBundle);
        clone.readConfiguration(connectionElement);
        clone.setConnectivityStatus(getConnectivityStatus());
        clone.generateNewId();
        return clone;
    }

    public String getConnectionDetails() {
        return "Name:\t"      + name + "\n" +
               "Description:\t" + description + "\n" +
               "URL:\t"       + databaseUrl + "\n" +
               "User:\t"      + user;
    }

   /*********************************************************
    *                   JDOMExternalizable                 *
    *********************************************************/
    public void readConfiguration(Element element) {
        driverLibrary = convertToAbsolutePath(element.getAttributeValue("driver-library"));
        driver = element.getAttributeValue("driver");
        databaseUrl = element.getAttributeValue("url");
        super.readConfiguration(element);
    }

    public void writeConfiguration(Element element) {
        element.setAttribute("driver-library", nvl(convertToRelativePath(driverLibrary)));
        element.setAttribute("driver",         nvl(driver));
        element.setAttribute("url",            nvl(databaseUrl));
        super.writeConfiguration(element);
    }

    private String convertToRelativePath(String path) {
        if (!StringUtil.isEmptyOrSpaces(path)) {
            VirtualFile baseDir = getProject().getBaseDir();
            if (baseDir != null) {
                File projectDir = new File(baseDir.getPath());
                String relativePath = com.intellij.openapi.util.io.FileUtil.getRelativePath(projectDir, new File(path));
                if (relativePath != null) {
                    if (relativePath.lastIndexOf(".." + File.separatorChar) < 1) {
                        return relativePath;
                    }
                }
            }
        }
        return path;
    }

    private String convertToAbsolutePath(String path) {
        if (!StringUtil.isEmptyOrSpaces(path)) {
            VirtualFile baseDir = getProject().getBaseDir();
            if (baseDir != null) {
                File projectDir = new File(baseDir.getPath());
                if (new File(path).isAbsolute()) {
                    return path;
                } else {
                    File file = FileUtil.createFileByRelativePath(projectDir, path);
                    return file == null ? null : file.getPath();
                }
            }
        }
        return path;
    }
}
