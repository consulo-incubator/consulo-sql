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

package com.dci.intellij.dbn.object.impl;

import com.dci.intellij.dbn.browser.model.BrowserTreeNode;
import com.dci.intellij.dbn.browser.ui.HtmlToolTipBuilder;
import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.data.type.DBDataType;
import com.dci.intellij.dbn.editor.DBContentType;
import com.dci.intellij.dbn.object.DBArgument;
import com.dci.intellij.dbn.object.DBFunction;
import com.dci.intellij.dbn.object.DBMethod;
import com.dci.intellij.dbn.object.DBType;
import com.dci.intellij.dbn.object.common.DBObjectImpl;
import com.dci.intellij.dbn.object.common.DBObjectType;
import com.dci.intellij.dbn.object.common.list.DBObjectNavigationList;
import com.dci.intellij.dbn.object.common.list.DBObjectNavigationListImpl;
import com.dci.intellij.dbn.object.identifier.DBArgumentIdentifier;
import com.dci.intellij.dbn.object.identifier.DBObjectIdentifier;
import com.dci.intellij.dbn.object.properties.DBDataTypePresentableProperty;
import com.dci.intellij.dbn.object.properties.PresentableProperty;
import com.dci.intellij.dbn.object.properties.SimplePresentableProperty;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBArgumentImpl extends DBObjectImpl implements DBArgument {
    private DBDataType dataType;
    private int overload;
    private int position;
    private int sequence;
    private boolean input;
    private boolean output;

    public DBArgumentImpl(DBMethod method, ResultSet resultSet) throws SQLException {
        super(method, DBContentType.NONE, resultSet);
    }

    @Override
    protected void initObject(ResultSet resultSet) throws SQLException {
        overload = resultSet.getInt("OVERLOAD");
        position = resultSet.getInt("POSITION");
        sequence = resultSet.getInt("SEQUENCE");
        String inOut = resultSet.getString("IN_OUT");
        if (inOut != null) {
            input = inOut.contains("IN");
            output = inOut.contains("OUT");

        }
        name = resultSet.getString("ARGUMENT_NAME");
        if (name == null) name = position == 0 ? "return" : "[unnamed]";

        dataType = new DBDataType(this, resultSet);
        if (getParentObject() instanceof DBFunction) {
            position++;
        }
    }

    @Override
    protected DBObjectIdentifier createIdentifier() {
        return new DBArgumentIdentifier(this);
    }

    public DBDataType getDataType() {
        return dataType;
    }

    public DBMethod getMethod() {
        return (DBMethod) getParentObject();
    }

    public int getOverload() {
        return overload;
    }

    public int getPosition() {
        return position;
    }

    public int getSequence() {
        return sequence;
    }

    public boolean isInput() {
        return input;
    }

    public boolean isOutput() {
        return output;
    }

    public boolean isLeafTreeElement() {
        return true;
    }

    @NotNull
    public List<BrowserTreeNode> buildAllPossibleTreeChildren() {
        return BrowserTreeNode.EMPTY_LIST;
    }

    public void buildToolTip(HtmlToolTipBuilder ttb) {
        ttb.append(true, getObjectType().getName(), true);
        ttb.append(false, " - ", true);
        ttb.append(false, dataType.getQualifiedName(), true);
        String inOut = input && output ? "IN / OUT" : input ? "IN" : "OUT";
        ttb.append(true, inOut, true);
        ttb.createEmptyRow();
        super.buildToolTip(ttb);
    }

    @Override
    public String getPresentableTextConditionalDetails() {
        return getDataType().getQualifiedName();
    }

    @Override
    public List<PresentableProperty> getPresentableProperties() {
        List<PresentableProperty> properties = super.getPresentableProperties();
        properties.add(0, new DBDataTypePresentableProperty(dataType));
        properties.add(0, new SimplePresentableProperty("Argument type", input && output ? "IN / OUT" : input ? "IN" : "OUT"));
        return properties;
    }

    @Override
    protected List<DBObjectNavigationList> createNavigationLists() {
        if (dataType.isDeclared()) {
            List<DBObjectNavigationList> objectNavigationLists = new ArrayList<DBObjectNavigationList>();
            objectNavigationLists.add(new DBObjectNavigationListImpl<DBType>("Type", dataType.getDeclaredType()));
            return objectNavigationLists;
        }
        return null;
    }

    @Override
    public Icon getIcon() {
        return isInput() && isOutput() ? Icons.DBO_ARGUMENT_IN_OUT :
               isInput() ? Icons.DBO_ARGUMENT_IN :
               isOutput() ? Icons.DBO_ARGUMENT_OUT : Icons.DBO_ARGUMENT;
    }

    public DBObjectType getObjectType() {
        return DBObjectType.ARGUMENT;
    }

    public int compareTo(@NotNull Object o) {
        if (o instanceof DBArgument) {
            DBArgument argument = (DBArgument) o;
            DBMethod thisMethod = getMethod();
            DBMethod thatMethod = argument.getMethod();
            if (thisMethod.equals(thatMethod)) {
                return getPosition() - argument.getPosition();
            } else {
                return thisMethod.compareTo(thatMethod);
            }
        }
        return super.compareTo(o);
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            DBArgument argument = (DBArgument) obj;
            return getOverload() == argument.getOverload() &&
                    getPosition() == argument.getPosition();
        }
        return false;
    }

}
