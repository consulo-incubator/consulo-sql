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

package com.dci.intellij.dbn.object.action;

import com.dci.intellij.dbn.common.Constants;
import com.dci.intellij.dbn.object.common.DBObject;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

public class ObjectPropertiesAction extends AnAction {
    private DBObject object;
    public ObjectPropertiesAction(DBObject object) {
        super("Properties");
        this.object = object;

    }

    public void actionPerformed(AnActionEvent event) {
        Messages.showInfoMessage("This feature is not implemented yet.", Constants.DBN_TITLE_PREFIX + "Not implemented!");
    }
}
