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

package com.dci.intellij.dbn.navigation.psi;

import com.dci.intellij.dbn.common.dispose.DisposeUtil;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.object.common.DBObject;
import com.dci.intellij.dbn.object.common.list.DBObjectList;
import com.dci.intellij.dbn.object.identifier.DBObjectIdentifier;
import com.intellij.openapi.Disposable;
import gnu.trove.THashMap;

import java.util.Map;

public class NavigationPsiCache implements Disposable {
    private Map<DBObjectIdentifier, DBObjectPsiFile> objectPsiFiles = new THashMap<DBObjectIdentifier, DBObjectPsiFile>();
    private Map<DBObjectIdentifier, DBObjectPsiDirectory> objectPsiDirectories = new THashMap<DBObjectIdentifier, DBObjectPsiDirectory>();
    private Map<DBObjectList, DBObjectListPsiDirectory> objectListPsiDirectories = new THashMap<DBObjectList, DBObjectListPsiDirectory>();
    private DBConnectionPsiDirectory connectionPsiDirectory;

    public NavigationPsiCache(ConnectionHandler connectionHandler) {
        connectionPsiDirectory = new DBConnectionPsiDirectory(connectionHandler);
    }

    public DBConnectionPsiDirectory getConnectionPsiDirectory() {
        return connectionPsiDirectory;
    }

    private synchronized DBObjectPsiFile lookupPsiFile(DBObject object) {
        DBObjectIdentifier identifier = object.getIdentifier();
        DBObjectPsiFile psiFile = objectPsiFiles.get(identifier);
        if (psiFile == null) {
            psiFile = new DBObjectPsiFile(object);
            objectPsiFiles.put(identifier, psiFile);
        }

        return psiFile;
    }

    private synchronized DBObjectPsiDirectory lookupPsiDirectory(DBObject object) {
        DBObjectIdentifier identifier = object.getIdentifier();
        DBObjectPsiDirectory psiDirectory = objectPsiDirectories.get(identifier);
        if (psiDirectory == null) {
            psiDirectory = new DBObjectPsiDirectory(object);
            objectPsiDirectories.put(identifier, psiDirectory);
        }

        return psiDirectory;
    }
    
    private synchronized DBObjectListPsiDirectory lookupPsiDirectory(DBObjectList objectList) {
        DBObjectListPsiDirectory psiDirectory = objectListPsiDirectories.get(objectList);
        if (psiDirectory == null) {
            psiDirectory = new DBObjectListPsiDirectory(objectList);
            objectListPsiDirectories.put(objectList, psiDirectory);
        }

        return psiDirectory;
    }
    
    
    public static DBObjectPsiFile getPsiFile(DBObject object) {
        return object == null ? null :
                object.getConnectionHandler().getPsiCache().lookupPsiFile(object);
    }

    public static DBObjectPsiDirectory getPsiDirectory(DBObject object) {
        return object == null ? null :
                object.getConnectionHandler().getPsiCache().lookupPsiDirectory(object);
    }
    
    public static DBObjectListPsiDirectory getPsiDirectory(DBObjectList objectList) {
        return objectList == null ? null :
                objectList.getConnectionHandler().getPsiCache().lookupPsiDirectory(objectList);
    }

    public static DBConnectionPsiDirectory getPsiDirectory(ConnectionHandler connectionHandler) {
        return connectionHandler.getPsiCache().getConnectionPsiDirectory();
    }

    @Override
    public void dispose() {
        DisposeUtil.dispose(connectionPsiDirectory);
        DisposeUtil.disposeMap(objectListPsiDirectories);
        DisposeUtil.disposeMap(objectPsiDirectories);
        DisposeUtil.disposeMap(objectPsiFiles);
    }
}
