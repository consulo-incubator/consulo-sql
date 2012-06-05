package com.dci.intellij.dbn.object.common;

import com.dci.intellij.dbn.browser.model.BrowserTreeElement;
import com.dci.intellij.dbn.code.common.lookup.LookupValueProvider;
import com.dci.intellij.dbn.common.content.DynamicContentElement;
import com.dci.intellij.dbn.common.environment.EnvironmentType;
import com.dci.intellij.dbn.language.common.DBLanguage;
import com.dci.intellij.dbn.language.common.DBLanguageDialect;
import com.dci.intellij.dbn.object.DBUser;
import com.dci.intellij.dbn.object.common.list.DBObjectListContainer;
import com.dci.intellij.dbn.object.common.list.DBObjectNavigationList;
import com.dci.intellij.dbn.object.common.list.DBObjectRelationListContainer;
import com.dci.intellij.dbn.object.common.operation.DBOperationExecutor;
import com.dci.intellij.dbn.object.common.property.DBObjectProperties;
import com.dci.intellij.dbn.object.properties.PresentableProperty;
import com.dci.intellij.dbn.vfs.DatabaseObjectFile;
import com.intellij.psi.PsiNamedElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface DBObject extends BrowserTreeElement, PsiNamedElement, DynamicContentElement, LookupValueProvider {
    List<DBObject> EMPTY_LIST = new ArrayList<DBObject>();

    DBObjectType getObjectType();
    boolean isOfType(DBObjectType objectType);

    DBLanguageDialect getLanguageDialect(DBLanguage language);
    
    DBObjectAttribute[] getObjectAttributes();
    DBObjectAttribute getNameAttribute();

    @NotNull
    String getName();
    String getQuotedName(boolean quoteAlways);
    String getQualifiedName();
    String getQualifiedNameWithType();
    String getQualifiedNameWithConnectionId();
    String getNavigationTooltipText();
    String getTypeName();
    Icon getIcon();
    Icon getOriginalIcon();

    DBUser getOwner();
    DBObject getParentObject();

    @Nullable
    DBObject getDefaultNavigationObject();
    List<DBObject> getChildObjects(DBObjectType objectType);
    DBObject getChildObject(DBObjectType objectType, String name, boolean lookupHidden);
    DBObject getChildObject(String name, boolean lookupHidden);

    List<DBObjectNavigationList> getNavigationLists();

    DBObjectListContainer getChildObjects();
    DBObjectRelationListContainer getChildObjectRelations();
    String extractDDL() throws SQLException;

    DBObject getUndisposedElement();

    DBObjectProperties getProperties();
    DBOperationExecutor getOperationExecutor();

    @NotNull
    DatabaseObjectFile getVirtualFile();
    List<PresentableProperty> getPresentableProperties();
    EnvironmentType getEnvironmentType();
}
