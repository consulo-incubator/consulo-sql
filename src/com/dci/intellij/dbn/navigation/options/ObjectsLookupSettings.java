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

package com.dci.intellij.dbn.navigation.options;

import com.dci.intellij.dbn.common.options.ProjectConfiguration;
import com.dci.intellij.dbn.common.options.setting.BooleanSetting;
import com.dci.intellij.dbn.common.ui.list.Selectable;
import com.dci.intellij.dbn.navigation.options.ui.ObjectsLookupSettingsForm;
import com.dci.intellij.dbn.object.common.DBObjectType;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import gnu.trove.THashSet;
import org.jdom.Element;

import javax.swing.Icon;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ObjectsLookupSettings extends ProjectConfiguration<ObjectsLookupSettingsForm> {
    private List<ObjectTypeEntry> lookupObjectTypes;
    private Set<DBObjectType> fastLookupObjectTypes;
    private BooleanSetting forceDatabaseLoad = new BooleanSetting("force-database-load", false);
    private BooleanSetting promptConnectionSelection = new BooleanSetting("prompt-connection-selection", true);

    public ObjectsLookupSettings(Project project) {
        super(project);
    }

    @Override
    public ObjectsLookupSettingsForm createConfigurationEditor() {
        return new ObjectsLookupSettingsForm(this);
    }

    @Override
    public void apply() throws ConfigurationException {
        super.apply();
        fastLookupObjectTypes = null;
    }

    public boolean isEnabled(DBObjectType objectType) {
        if (fastLookupObjectTypes == null) {
            fastLookupObjectTypes = new THashSet<DBObjectType>();
            for (ObjectTypeEntry objectTypeEntry : getLookupObjectTypes()) {
                if (objectTypeEntry.isSelected()) {
                    fastLookupObjectTypes.add(objectTypeEntry.getObjectType());
                }
            }
        }
        return fastLookupObjectTypes.contains(objectType);
    }

    public BooleanSetting getForceDatabaseLoad() {
        return forceDatabaseLoad;
    }

    public BooleanSetting getPromptConnectionSelection() {
        return promptConnectionSelection;
    }

    private ObjectTypeEntry getObjectTypeEntry(DBObjectType objectType) {
        for (ObjectTypeEntry objectTypeEntry : getLookupObjectTypes()) {
            DBObjectType visibleObjectType = objectTypeEntry.getObjectType();
            if (visibleObjectType == objectType || objectType.isInheriting(visibleObjectType)) {
                return objectTypeEntry;
            }
        }
        return null;
    }

    public synchronized List<ObjectTypeEntry> getLookupObjectTypes() {
        if (lookupObjectTypes == null) {
            lookupObjectTypes = new ArrayList<ObjectTypeEntry>();
            lookupObjectTypes.add(new ObjectTypeEntry(DBObjectType.SCHEMA, true));
            lookupObjectTypes.add(new ObjectTypeEntry(DBObjectType.USER, false));
            lookupObjectTypes.add(new ObjectTypeEntry(DBObjectType.ROLE, false));
            lookupObjectTypes.add(new ObjectTypeEntry(DBObjectType.PRIVILEGE, false));
            lookupObjectTypes.add(new ObjectTypeEntry(DBObjectType.CHARSET, false));
            lookupObjectTypes.add(new ObjectTypeEntry(DBObjectType.TABLE, true));
            lookupObjectTypes.add(new ObjectTypeEntry(DBObjectType.VIEW, true));
            lookupObjectTypes.add(new ObjectTypeEntry(DBObjectType.MATERIALIZED_VIEW, true));
            lookupObjectTypes.add(new ObjectTypeEntry(DBObjectType.NESTED_TABLE, false));
            lookupObjectTypes.add(new ObjectTypeEntry(DBObjectType.COLUMN, false));
            lookupObjectTypes.add(new ObjectTypeEntry(DBObjectType.INDEX, true));
            lookupObjectTypes.add(new ObjectTypeEntry(DBObjectType.CONSTRAINT, true));
            lookupObjectTypes.add(new ObjectTypeEntry(DBObjectType.TRIGGER, true));
            lookupObjectTypes.add(new ObjectTypeEntry(DBObjectType.SYNONYM, false));
            lookupObjectTypes.add(new ObjectTypeEntry(DBObjectType.SEQUENCE, true));
            lookupObjectTypes.add(new ObjectTypeEntry(DBObjectType.PROCEDURE, true));
            lookupObjectTypes.add(new ObjectTypeEntry(DBObjectType.FUNCTION, true));
            lookupObjectTypes.add(new ObjectTypeEntry(DBObjectType.PACKAGE, true));
            lookupObjectTypes.add(new ObjectTypeEntry(DBObjectType.TYPE, true));
            lookupObjectTypes.add(new ObjectTypeEntry(DBObjectType.TYPE_ATTRIBUTE, false));
            lookupObjectTypes.add(new ObjectTypeEntry(DBObjectType.ARGUMENT, false));

            lookupObjectTypes.add(new ObjectTypeEntry(DBObjectType.DIMENSION, false));
            lookupObjectTypes.add(new ObjectTypeEntry(DBObjectType.CLUSTER, false));
            lookupObjectTypes.add(new ObjectTypeEntry(DBObjectType.DBLINK, true));
        }
        return lookupObjectTypes;
    }

    /****************************************************
     *                   Configuration                  *
     ****************************************************/
    @Override
    public String getConfigElementName() {
        return "lookup-filters";
    }

    public void readConfiguration(Element element) throws InvalidDataException {
        Element visibleObjectsElement = element.getChild("lookup-objects");
        for (Object o : visibleObjectsElement.getChildren()) {
            Element child = (Element) o;
            String typeName = child.getAttributeValue("name");
            DBObjectType objectType = DBObjectType.getObjectType(typeName);
            if (objectType != null) {
                boolean enabled = Boolean.parseBoolean(child.getAttributeValue("enabled"));
                ObjectTypeEntry objectTypeEntry = getObjectTypeEntry(objectType);
                objectTypeEntry.setSelected(enabled);
            }
        }
        forceDatabaseLoad.readConfiguration(element);
        promptConnectionSelection.readConfiguration(element);
    }

    public void writeConfiguration(Element element) throws WriteExternalException {
        Element visibleObjectsElement = new Element("lookup-objects");
        element.addContent(visibleObjectsElement);

        for (ObjectTypeEntry objectTypeEntry : getLookupObjectTypes()) {
            Element child = new Element("object-type");
            child.setAttribute("name", objectTypeEntry.getName());
            child.setAttribute("enabled", Boolean.toString(objectTypeEntry.isSelected()));
            visibleObjectsElement.addContent(child);
        }
        forceDatabaseLoad.writeConfiguration(element);
        promptConnectionSelection.writeConfiguration(element);
    }
    
    private class ObjectTypeEntry implements Selectable {
        private DBObjectType objectType;
        private boolean enabled = true;

        private ObjectTypeEntry(DBObjectType objectType) {
            this.objectType = objectType;
        }

        private ObjectTypeEntry(DBObjectType objectType, boolean enabled) {
            this.objectType = objectType;
            this.enabled = enabled;
        }

        public DBObjectType getObjectType() {
            return objectType;
        }

        public Icon getIcon() {
            return objectType.getIcon();
        }

        public String getName() {
            return objectType.getName().toUpperCase();
        }

        public String getError() {
            return null;
        }

        public boolean isSelected() {
            return enabled;
        }

        public boolean isMasterSelected() {
            return true;
        }

        public void setSelected(boolean enabled) {
            this.enabled = enabled;
        }
    }    
}
