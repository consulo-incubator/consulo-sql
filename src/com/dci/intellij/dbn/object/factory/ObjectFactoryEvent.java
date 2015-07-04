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

package com.dci.intellij.dbn.object.factory;

import com.dci.intellij.dbn.object.common.DBSchemaObject;

public class ObjectFactoryEvent {
    public static final int EVENT_TYPE_CREATE = 0;
    public static final int EVENT_TYPE_DROP = 1;

    private DBSchemaObject object;
    private int eventType;

    public ObjectFactoryEvent(DBSchemaObject object, int eventType) {
        this.object = object;
        this.eventType = eventType;
    }

    public DBSchemaObject getObject() {
        return object;
    }

    public int getEventType() {
        return eventType;
    }
}
