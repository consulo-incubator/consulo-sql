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

import com.dci.intellij.dbn.object.common.DBObject;
import com.dci.intellij.dbn.common.util.NamingUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class NavigateToObjectAction extends AnAction {
    private DBObject object;

    public NavigateToObjectAction(DBObject object) {
        super(NamingUtil.enhanceUnderscoresForDisplay(object.getName()), null, object.getIcon());
        this.object = object;
    }

    public NavigateToObjectAction(DBObject sourceObject, DBObject object) {
        super(NamingUtil.enhanceUnderscoresForDisplay(
                    sourceObject != object.getParentObject() ?
                            object.getQualifiedName() :
                            object.getName()),
                object.getTypeName(),
                object.getIcon(0));
        this.object = object;
    }

    public void actionPerformed(AnActionEvent event) {
        object.navigate(true);
    }
}
