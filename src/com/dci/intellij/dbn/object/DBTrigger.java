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

package com.dci.intellij.dbn.object;

import com.dci.intellij.dbn.object.common.DBSchemaObject;

public interface DBTrigger extends DBSchemaObject {
    TriggerType TRIGGER_TYPE_BEFORE     = new TriggerType(0, "before");
    TriggerType TRIGGER_TYPE_AFTER      = new TriggerType(1, "after");
    TriggerType TRIGGER_TYPE_INSTEAD_OF = new TriggerType(2, "instead of");
    TriggerType TRIGGER_TYPE_UNKNOWN    = new TriggerType(3, "unknown");

    TriggeringEvent TRIGGERING_EVENT_INSERT   = new TriggeringEvent(0, "insert");
    TriggeringEvent TRIGGERING_EVENT_UPDATE   = new TriggeringEvent(1, "update");
    TriggeringEvent TRIGGERING_EVENT_DELETE   = new TriggeringEvent(2, "delete");
    TriggeringEvent TRIGGERING_EVENT_TRUNCATE = new TriggeringEvent(3, "truncate");
    TriggeringEvent TRIGGERING_EVENT_DROP     = new TriggeringEvent(4, "drop");
    TriggeringEvent TRIGGERING_EVENT_UNKNOWN  = new TriggeringEvent(5, "unknown");

    DBDataset getDataset();
    boolean isForEachRow();
    TriggerType getTriggerType();
    TriggeringEvent[] getTriggeringEvents();

    class TriggerType {
        private int id;
        private String name;

        public TriggerType(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    class TriggeringEvent {
        private int id;
        private String name;

        public TriggeringEvent(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}