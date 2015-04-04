package com.dci.intellij.dbn.language.common.element.impl;

import javax.swing.Icon;

import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.dci.intellij.dbn.code.common.style.formatting.FormattingDefinition;
import com.dci.intellij.dbn.code.common.style.formatting.FormattingDefinitionFactory;
import com.dci.intellij.dbn.code.common.style.formatting.IndentDefinition;
import com.dci.intellij.dbn.code.common.style.formatting.SpacingDefinition;
import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.common.util.StringUtil;
import com.dci.intellij.dbn.language.common.SqlLikeLanguage;
import com.dci.intellij.dbn.language.common.SqlLikeLanguageVersion;
import com.dci.intellij.dbn.language.common.element.ElementType;
import com.dci.intellij.dbn.language.common.element.ElementTypeBundle;
import com.dci.intellij.dbn.language.common.element.lookup.ElementTypeLookupCache;
import com.dci.intellij.dbn.language.common.element.parser.ElementTypeParser;
import com.dci.intellij.dbn.language.common.element.util.ElementTypeAttribute;
import com.dci.intellij.dbn.language.common.element.util.ElementTypeAttributesBundle;
import com.dci.intellij.dbn.language.common.element.util.ElementTypeDefinitionException;
import com.dci.intellij.dbn.language.common.element.util.ElementTypeLogger;
import com.dci.intellij.dbn.object.common.DBObjectType;
import com.intellij.psi.tree.IElementType;

public abstract class AbstractElementType extends IElementType implements ElementType {
    private static final FormattingDefinition STATEMENT_FORMATTING = new FormattingDefinition(null, IndentDefinition.NORMAL, SpacingDefinition.MIN_LINE_BREAK, null);

    private String id;
    private String description;
    private Icon icon;
    private FormattingDefinition formatting;
    private ElementTypeLookupCache lookupCache;
    private ElementTypeParser parser;
    private ElementTypeBundle bundle;
    private ElementType parent;


    private DBObjectType virtualObjectType;
    private boolean isVirtualObjectInsideLookup;
    private ElementTypeAttributesBundle attributes = ElementTypeAttributesBundle.EMPTY;
    private ElementTypeLogger logger = new ElementTypeLogger(this);
	private SqlLikeLanguageVersion<?> myLanguageVersion;

    public AbstractElementType(ElementTypeBundle bundle, ElementType parent, String id, @Nullable String description) {
        super(id, bundle.getLanguage(), false);
        this.id = id;
		myLanguageVersion = bundle.getLanguageVersion();
        this.description = description;
        this.bundle = bundle;
        this.parent = parent;
        this.lookupCache = createLookupCache();
        this.parser = createParser();
    }

    public AbstractElementType(ElementTypeBundle bundle, ElementType parent, String id, Element def) throws ElementTypeDefinitionException {
        super(id, bundle.getLanguage(), false);
        this.id = def.getAttributeValue("id");
        if (!id.equals(this.id)) {
            this.id = id;
            def.setAttribute("id", this.id);
            bundle.markIndexesDirty();
        }
		myLanguageVersion = bundle.getLanguageVersion();
        this.bundle = bundle;
        this.parent = parent;
        this.lookupCache = createLookupCache();
        this.parser = createParser();
        loadDefinition(def);
    }

    protected abstract ElementTypeLookupCache createLookupCache();

    protected abstract ElementTypeParser createParser();

    @Override
    public void setDefaultFormatting(FormattingDefinition defaultFormatting) {
        formatting = FormattingDefinitionFactory.mergeDefinitions(formatting, defaultFormatting);
    }

    protected void loadDefinition(Element def) throws ElementTypeDefinitionException {
        String attributesString = def.getAttributeValue("attributes");
        if (StringUtil.isNotEmptyOrSpaces(attributesString)) {
            attributes =  new ElementTypeAttributesBundle(attributesString);
        }

        isVirtualObjectInsideLookup = Boolean.parseBoolean(def.getAttributeValue("virtual-object-inside-lookup"));
        String objectTypeName = def.getAttributeValue("virtual-object");
        if (objectTypeName != null) {
            virtualObjectType = ElementTypeBundle.resolveObjectType(objectTypeName);
        }
        formatting = FormattingDefinitionFactory.loadDefinition(def);
        if (is(ElementTypeAttribute.STATEMENT)) {
            setDefaultFormatting(STATEMENT_FORMATTING);
        }

        String iconKey = def.getAttributeValue("icon");
        if (iconKey != null)  icon = Icons.getIcon(iconKey);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Icon getIcon() {
        return icon;
    }

    @Override
    public ElementType getParent() {
        return parent;
    }

    @Override
    public ElementTypeLookupCache getLookupCache() {
        return lookupCache;
    }

    @Override
    public @NotNull ElementTypeParser getParser() {
        return parser;
    }

    public ElementTypeLogger getLogger() {
        return logger;
    }

    @Override
    public boolean is(ElementTypeAttribute attribute) {
        return attributes.is(attribute);
    }

    @Override
    public ElementTypeAttributesBundle getAttributes() {
        return attributes;
    }

    @Override
    public FormattingDefinition getFormatting() {
        return formatting;
    }

    @Override
    @NotNull
    public SqlLikeLanguage getLanguage() {
        return (SqlLikeLanguage) super.getLanguage();
    }

    @Override
    public SqlLikeLanguageVersion<? extends SqlLikeLanguage> getLanguageVersion() {
        return myLanguageVersion;
    }

    @Override
    public ElementTypeBundle getElementBundle() {
        return bundle;
    }

    @Override
    public String toString() {
        return getId();
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public void registerVirtualObject(DBObjectType objectType) {
        getLookupCache().registerVirtualObject(objectType);
    }

    /*********************************************************
     *                  Virtual Object                       *
     *********************************************************/
    @Override
    public boolean isVirtualObject() {
        return virtualObjectType != null;
    }

    @Override
    public DBObjectType getVirtualObjectType() {
        return virtualObjectType;
    }

    @Override
    public boolean isVirtualObjectInsideLookup() {
        return isVirtualObjectInsideLookup;
    }
}
