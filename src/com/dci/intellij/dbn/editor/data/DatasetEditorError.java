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

package com.dci.intellij.dbn.editor.data;

import com.dci.intellij.dbn.common.util.CommonUtil;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.database.DatabaseMessageParserInterface;
import com.dci.intellij.dbn.database.DatabaseObjectIdentifier;
import com.dci.intellij.dbn.object.common.DBObject;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class DatasetEditorError {
    private boolean isDirty;
    private boolean isNotified;
    private String message;
    private DBObject messageObject;
    private Set<ChangeListener> changeListeners = new HashSet<ChangeListener>();

    public DatasetEditorError(ConnectionHandler connectionHandler, Exception exception) {
        this.message = exception.getMessage();
        if (exception instanceof SQLException) {
            DatabaseMessageParserInterface messageParserInterface = connectionHandler.getInterfaceProvider().getMessageParserInterface();
            DatabaseObjectIdentifier objectIdentifier = messageParserInterface.identifyObject(exception.getMessage());
            if (objectIdentifier != null) {
                messageObject = connectionHandler.getObjectBundle().getObject(objectIdentifier);
            }
        }
    }
    public DatasetEditorError(String message, DBObject messageObject) {
        this.message = message;
        this.messageObject = messageObject;
    }

    public void addChangeListener(ChangeListener changeListener) {
        changeListeners.add(changeListener);
    }

    public void removeChangeListener(ChangeListener changeListener) {
        changeListeners.remove(changeListener);
    }

    public String getMessage() {
        return message;
    }

    public DBObject getMessageObject() {
        return messageObject;
    }


    public void markDirty() {
        isDirty = true;
        ChangeEvent changeEvent = new ChangeEvent(this);
        for (ChangeListener changeListener: changeListeners) {
            changeListener.stateChanged(changeEvent);
        }
    }

    public boolean isDirty() {
        return isDirty;
    }

    public boolean isNotified() {
        return isNotified;
    }

    public void setNotified(boolean notified) {
        isNotified = notified;
    }

    public int hashCode() {
        return message.hashCode();
    }

    public boolean equals(Object obj) {
        if(obj instanceof DatasetEditorError) {
            DatasetEditorError error = (DatasetEditorError) obj;
            return CommonUtil.safeEqual(error.getMessage(), getMessage()) &&
                   CommonUtil.safeEqual(error.getMessageObject(), getMessageObject());
        }

        return false;
    }
}
