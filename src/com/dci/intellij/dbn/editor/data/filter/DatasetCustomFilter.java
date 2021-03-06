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

package com.dci.intellij.dbn.editor.data.filter;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.common.options.ui.ConfigurationEditorForm;
import com.dci.intellij.dbn.data.sorting.SingleColumnSortingState;
import com.dci.intellij.dbn.editor.data.filter.ui.DatasetCustomFilterForm;
import com.dci.intellij.dbn.object.DBDataset;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.openapi.util.text.StringUtil;
import org.jdom.CDATA;
import org.jdom.Element;

import javax.swing.Icon;

public class DatasetCustomFilter extends DatasetFilterImpl {
    private String condition;

    protected DatasetCustomFilter(DatasetFilterGroup parent, String name) {
        super(parent, name, DatasetFilterType.CUSTOM);
    }

    public void generateName() {}

    public String getVolatileName() {
        ConfigurationEditorForm configurationEditorForm = getSettingsEditor();
        if (configurationEditorForm != null) {
            DatasetCustomFilterForm customFilterForm = (DatasetCustomFilterForm) configurationEditorForm;
            return customFilterForm.getFilterName();
        }
        return super.getDisplayName();
    }

    public boolean isIgnored() {
        return false;
    }

    public Icon getIcon() {
        return getError() == null ?
                Icons.DATASET_FILTER_CUSTOM :
                Icons.DATASET_FILTER_CUSTOM_ERR;
    }

    public String createSelectStatement(DBDataset dataset, SingleColumnSortingState sortingState) {
        setError(null);
        StringBuilder buffer = new StringBuilder();
        DatasetFilterUtil.createSimpleSelectStatement(dataset, buffer);
        buffer.append(" where ");
        buffer.append(condition);
        DatasetFilterUtil.addOrderByClause(dataset, buffer, sortingState);
        DatasetFilterUtil.addForUpdateClause(dataset, buffer);
        return buffer.toString();
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    /*****************************************************
     *                   Configuration                   *
     *****************************************************/
    public ConfigurationEditorForm createConfigurationEditor() {
        DBDataset dataset = lookupDataset();
        return dataset == null ? null : new DatasetCustomFilterForm(dataset, this);
    }

    public void readConfiguration(Element element) throws InvalidDataException {
        super.readConfiguration(element);
        Element conditionElement = element.getChild("condition");
        if (conditionElement.getContentSize() > 0) {
            CDATA cdata = (CDATA) conditionElement.getContent(0);
            condition = cdata.getText();
            condition = StringUtil.replace(condition, "<br>", "\n");
            condition = StringUtil.replace(condition, "<sp>", "  ");
        }
    }

    public void writeConfiguration(Element element) throws WriteExternalException {
        super.writeConfiguration(element);
        element.setAttribute("type", "custom");
        Element conditionElement = new Element("condition");
        element.addContent(conditionElement);
        if (this.condition != null) {
            String condition = StringUtil.replace(this.condition, "\n", "<br>");
            condition = StringUtil.replace(condition, "  ", "<sp>");
            CDATA cdata = new CDATA(condition);
            conditionElement.setContent(cdata);
        }
    }

}
