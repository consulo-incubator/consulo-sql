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

package com.dci.intellij.dbn.object.common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.dci.intellij.dbn.browser.model.BrowserTreeNode;
import com.dci.intellij.dbn.code.common.lookup.LookupValueProvider;
import com.dci.intellij.dbn.common.content.DynamicContentElement;
import com.dci.intellij.dbn.common.environment.EnvironmentType;
import com.dci.intellij.dbn.language.common.SqlLikeLanguage;
import com.dci.intellij.dbn.language.common.SqlLikeLanguageVersion;
import com.dci.intellij.dbn.object.DBSchema;
import com.dci.intellij.dbn.object.DBUser;
import com.dci.intellij.dbn.object.common.list.DBObjectListContainer;
import com.dci.intellij.dbn.object.common.list.DBObjectNavigationList;
import com.dci.intellij.dbn.object.common.list.DBObjectRelationListContainer;
import com.dci.intellij.dbn.object.common.operation.DBOperationExecutor;
import com.dci.intellij.dbn.object.common.property.DBObjectProperties;
import com.dci.intellij.dbn.object.identifier.DBObjectIdentifier;
import com.dci.intellij.dbn.object.properties.PresentableProperty;
import com.dci.intellij.dbn.vfs.DatabaseObjectFile;
import com.intellij.psi.PsiNamedElement;

public interface DBObject extends BrowserTreeNode, PsiNamedElement, DynamicContentElement, LookupValueProvider {
    List<DBObject> EMPTY_LIST = new ArrayList<DBObject>();

    DBObjectType getObjectType();
    boolean isOfType(DBObjectType objectType);

	SqlLikeLanguageVersion<?> getLanguageDialect(SqlLikeLanguage language);
    
    DBObjectAttribute[] getObjectAttributes();
    DBObjectAttribute getNameAttribute();

    @NotNull
    String getName();
    String getQuotedName(boolean quoteAlways);
    boolean needsNameQuoting();
    String getQualifiedName();
    String getQualifiedNameWithType();
    String getQualifiedNameWithConnectionId();
    String getNavigationTooltipText();
    String getTypeName();
    Icon getIcon();
    Icon getOriginalIcon();

    DBUser getOwner();
    DBSchema getSchema();
    DBObject getParentObject();
    DBObjectBundle getObjectBundle();

    @Nullable
    DBObject getDefaultNavigationObject();
    List<DBObject> getChildObjects(DBObjectType objectType);
    DBObject getChildObject(DBObjectType objectType, String name, boolean lookupHidden);
    DBObject getChildObject(String name, boolean lookupHidden);

    List<DBObjectNavigationList> getNavigationLists();

    @Nullable
    DBObjectListContainer getChildObjects();

    @Nullable
    DBObjectRelationListContainer getChildObjectRelations();
    String extractDDL() throws SQLException;

    DBObject getUndisposedElement();

    DBObjectProperties getProperties();
    DBOperationExecutor getOperationExecutor();

    @NotNull
    DatabaseObjectFile getVirtualFile();
    List<PresentableProperty> getPresentableProperties();
    EnvironmentType getEnvironmentType();

    DBObjectIdentifier getIdentifier();
}
