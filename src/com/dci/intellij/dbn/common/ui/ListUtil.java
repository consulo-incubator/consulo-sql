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

package com.dci.intellij.dbn.common.ui;

import com.dci.intellij.dbn.common.LoggerFactory;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ui.SelectFromListDialog;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.List;
import java.util.Set;

public class ListUtil {
    private static final Logger LOGGER = LoggerFactory.createLogger();

    public static void notifyListDataListeners(Object source, Set<ListDataListener> listDataListeners, int fromIndex, int toIndex, int eventType) {
        try {
            ListDataEvent event = new ListDataEvent(source, eventType, fromIndex, toIndex);
            for (ListDataListener listener : listDataListeners) {
                switch (eventType) {
                    case ListDataEvent.INTERVAL_ADDED:   listener.intervalAdded(event); break;
                    case ListDataEvent.INTERVAL_REMOVED: listener.intervalRemoved(event); break;
                    case ListDataEvent.CONTENTS_CHANGED: listener.contentsChanged(event); break;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error notifying list model listeners", e);
        }
    }

    public static final SelectFromListDialog.ToStringAspect BASIC_TO_STRING_ASPECT = new SelectFromListDialog.ToStringAspect() {
        public String getToStirng(Object obj) {
            return obj.toString();
        }
    };
    
    public static <T> T getLast(List<T> list) {
        return list == null || list.size() == 0 ? null : list.get(list.size() - 1);
    }

    public static <T> T getFirst(List<T> list) {
        return list == null || list.size() == 0 ? null : list.get(0);
    }
}
