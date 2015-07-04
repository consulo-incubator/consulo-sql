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

package com.dci.intellij.dbn.language.common.element;

import gnu.trove.THashMap;
import gnu.trove.THashSet;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import com.dci.intellij.dbn.language.common.SqlLikeLanguage;
import com.dci.intellij.dbn.language.common.SqlLikeLanguageVersion;
import com.dci.intellij.dbn.language.common.TokenTypeBundle;
import com.dci.intellij.dbn.language.common.element.impl.*;
import com.dci.intellij.dbn.language.common.element.util.ElementTypeAttribute;
import com.dci.intellij.dbn.language.common.element.util.ElementTypeDefinition;
import com.dci.intellij.dbn.language.common.element.util.ElementTypeDefinitionException;
import com.dci.intellij.dbn.object.common.DBObjectType;
import com.intellij.openapi.diagnostic.Logger;

public class ElementTypeBundle {
    private final Logger log = Logger.getInstance(getClass().getName());
	private final SqlLikeLanguage language;
	private final SqlLikeLanguageVersion<? extends SqlLikeLanguage> languageVersion;
	private TokenTypeBundle tokenTypeBundle;
    private UnknownElementType unknownElementType;
    private NamedElementType rootElementType;

    private Set<ElementType> complexElementTypes = new THashSet<ElementType>();
    private Set<LeafElementType> leafElementTypes = new THashSet<LeafElementType>();
    private Set<WrapperElementType> wrapperElementTypes = new THashSet<WrapperElementType>();
    private Set<OneOfElementType> oneOfElementTypes = new THashSet<OneOfElementType>();
    private final Map<String, NamedElementType> namedElementTypes = new THashMap<String, NamedElementType>();
    private final Set<ElementType> virtualObjectElementTypes = new THashSet<ElementType>();

    private boolean rewriteIndexes;

    /** @deprecated */
    private Properties lookupCacheIndex;

    private int idCursor;

    public ElementTypeBundle(SqlLikeLanguage language, SqlLikeLanguageVersion<? extends SqlLikeLanguage> languageVersion, TokenTypeBundle tokenTypeBundle, Document elementTypesDef, Properties lookupCacheIndex) {
        this.language = language;
		this.languageVersion = languageVersion;
        this.tokenTypeBundle = tokenTypeBundle;
        this.lookupCacheIndex = lookupCacheIndex;
        try {
            Element root = elementTypesDef.getRootElement();
            for (Object o : root.getChildren()) {
                Element child = (Element) o;
                createNamedElementType(child);
            }

            NamedElementType unknown = namedElementTypes.get("custom_undefined");
            for(NamedElementType namedElementType : namedElementTypes.values()){
                if (!namedElementType.isDefinitionLoaded()) {
                    namedElementType.update(unknown);
                    //log.info("ERROR: element '" + namedElementType.getId() + "' not defined.");
                    System.out.println("DEBUG - [" + languageVersion + "] undefined element type: " + namedElementType.getId());
/*
                    if (DatabaseNavigator.getInstance().isDebugModeEnabled()) {
                        System.out.println("WARNING - [" + getLanguageDialect().getID() + "] undefined element type: " + namedElementType.getId());
                    }
*/
                }
            }

            for (LeafElementType leafElementType: leafElementTypes) {
                leafElementType.registerLeaf();
            }

            for (WrapperElementType wrapperElementType : wrapperElementTypes) {
                wrapperElementType.getBeginTokenElement().registerLeaf();
                wrapperElementType.getEndTokenElement().registerLeaf();
            }

            for (ElementType virtualObjectElementType : virtualObjectElementTypes) {
                virtualObjectElementType.registerVirtualObject(virtualObjectElementType.getVirtualObjectType());
            }

            if (rewriteIndexes) {
                lookupCacheIndex.clear();
                StringWriter stringWriter = new StringWriter();
                new XMLOutputter().output(elementTypesDef, stringWriter);

                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                String data = stringWriter.getBuffer().toString();
                System.out.println(data);
                clipboard.setContents(new StringSelection(data), null);

            } else {
            }

            //warnAmbiguousBranches();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Set<ElementType> getComplexElementTypes() {
        return complexElementTypes;
    }

    public TokenTypeBundle getTokenTypeBundle() {
        return tokenTypeBundle;
    }

    private void warnAmbiguousBranches() {
        for (OneOfElementType oneOfElementType : oneOfElementTypes) {
            oneOfElementType.warnAmbiguousBranches();
        }
    }


    public void markIndexesDirty() {
        this.rewriteIndexes = true;
    }

    public boolean isRewriteIndexes() {
        return rewriteIndexes;
    }

    private void createNamedElementType(Element def) throws ElementTypeDefinitionException {
        String id = determineMandatoryAttribute(def, "id", "Invalid definition of named element type.");
        log.debug("Updating complex element definition '" + id + "'");
        NamedElementType elementType = getNamedElementType(id, null);
        elementType.loadDefinition(def);
        if (elementType.is(ElementTypeAttribute.ROOT)) {
            if (rootElementType == null) {
                rootElementType = elementType;
            } else {
                throw new ElementTypeDefinitionException("Duplicate root definition");
            }
        }
    }


    public static String determineMandatoryAttribute(Element def, String attribute, String message) throws ElementTypeDefinitionException {
        String value = def.getAttributeValue(attribute);
        if (value == null) {
            throw new ElementTypeDefinitionException(message + "Missing '" + attribute + "' attribute.");
        }
        return value;
    }

    public ElementType resolveElementDefinition(Element def, String type, ElementType parent) throws ElementTypeDefinitionException {
        ElementType result;
        if (ElementTypeDefinition.SEQUENCE.is(type)){
            result = new SequenceElementTypeImpl(this, parent, createId(), def);
            log.debug("Created sequence element definition");
        } else if (ElementTypeDefinition.BLOCK.is(type)) {
            result = new BlockElementTypeImpl(this, parent, createId(), def);
            log.debug("Created iteration element definition");
        } else if (ElementTypeDefinition.ITERATION.is(type)) {
            result = new IterationElementTypeImpl(this, parent, createId(), def);
            log.debug("Created iteration element definition");
        } else if (ElementTypeDefinition.ONE_OF.is(type)) {
            result =  new OneOfElementTypeImpl(this, parent, createId(), def);
            oneOfElementTypes.add((OneOfElementType) result);
            log.debug("Created one-of element definition");
        } else if (ElementTypeDefinition.QUALIFIED_IDENTIFIER.is(type)) {
            result =  new QualifiedIdentifierElementTypeImpl(this, parent, createId(), def);
            log.debug("Created qualified identifier element definition");
        } else if (ElementTypeDefinition.WRAPPER.is(type)) {
            result = new WrapperElementTypeImpl(this, parent, createId(), def);
            wrapperElementTypes.add((WrapperElementType) result);
            log.debug("Created wrapper element definition");
        } else if (ElementTypeDefinition.ELEMENT.is(type)) {
            String id = determineMandatoryAttribute(def, "ref-id", "Invalid reference to element.");
            result = getNamedElementType(id, parent);
        } else if (ElementTypeDefinition.TOKEN.is(type)) {
            result = new TokenElementTypeImpl(this, parent, createId(), def);
        } else if (
                ElementTypeDefinition.OBJECT_DEF.is(type) ||
                ElementTypeDefinition.OBJECT_REF.is(type) ||
                ElementTypeDefinition.ALIAS_DEF.is(type) ||
                ElementTypeDefinition.ALIAS_REF.is(type) ||
                ElementTypeDefinition.VARIABLE_DEF.is(type) ||
                ElementTypeDefinition.VARIABLE_REF.is(type)) {
            result = new IdentifierElementTypeImpl(this, parent, createId(), def);
        } else if (ElementTypeDefinition.EXEC_VARIABLE.is(type)) {
            result = new ExecVariableElementTypeImpl(this, parent, createId(), def);            
        }  else {
            throw new ElementTypeDefinitionException("Could not resolve element definition '" + type + "'");
        }
        if (result instanceof LeafElementType)
            leafElementTypes.add((LeafElementType) result); else
            complexElementTypes.add(result);

        if (result.isVirtualObject()) {
            virtualObjectElementTypes.add(result);
        }

        return result;
    }


    public static DBObjectType resolveObjectType(String name) throws ElementTypeDefinitionException {
        DBObjectType objectType = DBObjectType.getObjectType(name);
        if (objectType == null)
            throw new ElementTypeDefinitionException("Invalid object type '" + name + "'");
        return objectType;
    }


    /*protected synchronized TokenElementType getTokenElementType(String id) {
        TokenElementType elementType = tokenElementTypes.get(id);
        if (elementType == null) {
            elementType = new TokenElementType(this, id);
            tokenElementTypes.put(id, elementType);
            log.info("Created token element objectType '" + id + "'");
        }
        return elementType;
    }*/

    protected synchronized NamedElementType getNamedElementType(String id, ElementType parent) {
        NamedElementType elementType = namedElementTypes.get(id);
        if (elementType == null) {
            elementType = new NamedElementTypeImpl(this, id);
            namedElementTypes.put(id, elementType);
            log.debug("Created named element type '" + id + "'");
        }
        if (parent != null) elementType.addParent(parent);
        return elementType;
    }

    public NamedElementType getRootElementType() {
        return rootElementType;
    }

    public NamedElementType getNamedElementType(String id) {
        return namedElementTypes.get(id);
    }

    public UnknownElementType getUnknownElementType() {
        if (unknownElementType == null) {
            unknownElementType = new UnknownElementTypeImpl(this);
        }
        return unknownElementType;
    }

    public String createId() {
        String id = Integer.toString(idCursor++);
        StringBuilder buffer = new StringBuilder();
        while (buffer.length() + id.length() < 9) {
            buffer.append('0');
        }
        buffer.append(id);
        return buffer.toString();
    }

	public SqlLikeLanguage getLanguage()
	{
		return language;
	}

	public SqlLikeLanguageVersion<? extends SqlLikeLanguage> getLanguageVersion()
	{
		return languageVersion;
	}
}
