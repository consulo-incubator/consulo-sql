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

package com.dci.intellij.dbn.language.common.element.lookup;

import com.dci.intellij.dbn.language.common.SharedTokenTypeBundle;
import com.dci.intellij.dbn.language.common.TokenType;
import com.dci.intellij.dbn.language.common.element.ElementType;
import com.dci.intellij.dbn.language.common.element.ElementTypeBundle;
import com.dci.intellij.dbn.language.common.element.IdentifierElementType;
import com.dci.intellij.dbn.language.common.element.IterationElementType;
import com.dci.intellij.dbn.language.common.element.LeafElementType;
import com.dci.intellij.dbn.language.common.element.QualifiedIdentifierElementType;
import com.dci.intellij.dbn.language.common.element.SequenceElementType;
import com.dci.intellij.dbn.language.common.element.TokenElementType;
import com.dci.intellij.dbn.language.common.element.util.IdentifierRole;
import com.dci.intellij.dbn.language.common.element.util.IdentifierType;
import com.dci.intellij.dbn.object.common.DBObjectType;
import gnu.trove.THashMap;
import gnu.trove.THashSet;

import java.util.Map;
import java.util.Set;

public abstract class AbstractElementTypeLookupCache<T extends ElementType> implements ElementTypeLookupCache<T> {
    private T elementType;

    //protected Set<IdentifierCacheElement> identifierTypes;
    protected Map<DBObjectType, Map<IdentifierType, Set<IdentifierRole>>> identifierTypes;
    protected Set<DBObjectType> virtualObjects;
    protected Set<LeafElementType> allPossibleLeafs = new THashSet<LeafElementType>();
    protected Set<LeafElementType> firstPossibleLeafs = new THashSet<LeafElementType>();
    protected Set<LeafElementType> firstRequiredLeafs = new THashSet<LeafElementType>();
    protected Set<TokenType> allPossibleTokens = new THashSet<TokenType>();
    protected Set<TokenType> firstPossibleTokens = new THashSet<TokenType>();
    protected Set<TokenType> firstRequiredTokens = new THashSet<TokenType>();
    private Map<TokenType, Boolean> landmarkTokens;
    private Boolean startsWithIdentifier;

    private Set<TokenType> nextPossibleTokens;
    private Set<TokenType> nextRequiredTokens;


    public AbstractElementTypeLookupCache(T elementType) {
        this.elementType = elementType;
        if (!elementType.isLeaf()) {
            landmarkTokens = new THashMap<TokenType, Boolean>();
        }
    }

    public void init() {}

    public T getElementType() {
        return elementType;
    }

    private ElementTypeBundle getBundle() {
        return elementType.getElementBundle();
    }


    public boolean containsToken(TokenType tokenType) {
        return allPossibleTokens != null && allPossibleTokens.contains(tokenType);
    }

    public boolean containsLeaf(LeafElementType leafElementType) {
        return leafElementType == getElementType() || (allPossibleLeafs != null && allPossibleLeafs.contains(leafElementType));
    }

    public boolean containsVirtualObject(DBObjectType objectType) {
        return virtualObjects != null && virtualObjects.contains(objectType);
    }

    public boolean containsIdentifier(DBObjectType objectType, IdentifierType identifierType, IdentifierRole identifierRole) {
        if (identifierTypes != null) {
            Map<IdentifierType, Set<IdentifierRole>> identifierTypeMap = identifierTypes.get(objectType);
            if (identifierTypeMap != null) {
                Set<IdentifierRole> identifierRoleSet = identifierTypeMap.get(identifierType);
                if (identifierRoleSet != null) {
                    return identifierRole == IdentifierRole.ALL || identifierRoleSet.contains(identifierRole);
                }
            }
        }

        DBObjectType genericType = objectType.getGenericType() != objectType ? objectType.getGenericType() : null;
        while (genericType != null) {
            if (containsIdentifier(genericType, identifierType, identifierRole)) return true;
            genericType = genericType.getGenericType() != genericType ? genericType.getGenericType() : null;
        }
        return false;
    }

    public boolean containsIdentifier(DBObjectType objectType, IdentifierType identifierType) {
        return containsIdentifier(objectType, identifierType, IdentifierRole.ALL);
    }

    private void addIdentifier(DBObjectType objectType, IdentifierType identifierType, IdentifierRole identifierRole){
        if (identifierTypes == null) {
            identifierTypes = new THashMap<DBObjectType, Map<IdentifierType, Set<IdentifierRole>>>();
        }

        Map<IdentifierType, Set<IdentifierRole>> identifierTypeMap = identifierTypes.get(objectType);
        if (identifierTypeMap == null) {
            identifierTypeMap = new THashMap<IdentifierType, Set<IdentifierRole>>();
            identifierTypes.put(objectType, identifierTypeMap);
        }

        Set<IdentifierRole> identifierRoleSet = identifierTypeMap.get(identifierType);
        if (identifierRoleSet == null) {
            identifierRoleSet = new THashSet<IdentifierRole>();
            identifierTypeMap.put(identifierType, identifierRoleSet);
        }
        identifierRoleSet.add(identifierRole);

        for (DBObjectType inheritingObjectType : objectType.getInheritingTypes()) {
            addIdentifier(inheritingObjectType, identifierType, identifierRole);
        }

    }

    public boolean containsIdentifier(IdentifierElementType identifierElementType) {
        return containsIdentifier(
                identifierElementType.getObjectType(),
                identifierElementType.getIdentifierType(),
                identifierElementType.isReference() ? IdentifierRole.REFERENCE : IdentifierRole.DEFINITION);
    }

    public Set<LeafElementType> getFirstPossibleLeafs() {
        return firstPossibleLeafs;
    }

    public Set<TokenType> getFirstPossibleTokens() {
        return firstPossibleTokens;
    }

    public Set<LeafElementType> getFirstRequiredLeafs() {
        return firstRequiredLeafs;
    }

    public Set<TokenType> getFirstRequiredTokens() {
        return firstRequiredTokens;
    }

    public boolean canStartWithLeaf(LeafElementType leafElementType) {
        return firstPossibleLeafs.contains(leafElementType);
    }

    public boolean canStartWithToken(TokenType tokenType) {
        return firstPossibleTokens.contains(tokenType);
    }

    public boolean shouldStartWithLeaf(LeafElementType leafElementType) {
        return firstRequiredLeafs.contains(leafElementType);
    }

    public boolean shouldStartWithToken(TokenType tokenType) {
        return firstRequiredTokens.contains(tokenType);
    }

    public void registerLeaf(LeafElementType leaf, ElementType pathChild) {
        boolean initAllElements = !containsLeaf(leaf);
        boolean isFirstPossibleElements = isFirstPossibleLeaf(leaf, pathChild);
        boolean isFirstRequiredLeaf = isFirstRequiredLeaf(leaf, pathChild);

        // register first possible leafs
        ElementTypeLookupCache lookupCache = leaf.getLookupCache();
        if (isFirstPossibleElements) {
            firstPossibleLeafs.add(leaf);
            firstPossibleTokens.addAll(lookupCache.getFirstPossibleTokens());
        }

        // register first required leafs
        if (isFirstRequiredLeaf) {
            firstRequiredLeafs.add(leaf);
            firstRequiredTokens.addAll(lookupCache.getFirstRequiredTokens());
        }

        if (initAllElements) {
            // register all possible leafs
            allPossibleLeafs.add(leaf);

            // register all possible tokens
            if (leaf instanceof IdentifierElementType) {
                SharedTokenTypeBundle sharedTokenTypes = getElementType().getLanguage().getSharedTokenTypes();
                allPossibleTokens.add(sharedTokenTypes.getIdentifier());
                allPossibleTokens.add(sharedTokenTypes.getQuotedIdentifier());
            } else {
                allPossibleTokens.add(leaf.getTokenType());
            }

            // register identifiers
            if (leaf instanceof IdentifierElementType) {
                IdentifierElementType identifierElementType = (IdentifierElementType) leaf;
                if (!containsIdentifier(identifierElementType)) {
                    addIdentifier(
                            identifierElementType.getObjectType(),
                            identifierElementType.getIdentifierType(),
                            identifierElementType.isReference() ? IdentifierRole.REFERENCE : IdentifierRole.DEFINITION);
                }

            }

        }

        if (isFirstPossibleElements || isFirstRequiredLeaf || initAllElements) {
            // walk the tree up
            registerLeafInParent(leaf);
        }
    }

    protected void registerLeafInParent(LeafElementType leaf) {
        ElementType parent = getElementType().getParent();
        if (parent != null) {
            parent.getLookupCache().registerLeaf(leaf, getElementType());
        }
    }

    public void registerVirtualObject(DBObjectType objectType) {
        if (virtualObjects == null) {
            virtualObjects = new THashSet<DBObjectType>();
        }
        virtualObjects.add(objectType);
        ElementType parent = getElementType().getParent();
        if (parent != null) {
            parent.getLookupCache().registerVirtualObject(objectType);
        }

    }

    public synchronized boolean containsLandmarkToken(TokenType tokenType) {
        if (getElementType().isLeaf()) return containsToken(tokenType);

        Boolean value = landmarkTokens.get(tokenType);
        if (value == null) {
            value = containsLandmarkToken(tokenType, null);
            landmarkTokens.put(tokenType, value);
        }
        return value;
    }


    public synchronized boolean startsWithIdentifier() {
        if (startsWithIdentifier == null) {
            startsWithIdentifier =  startsWithIdentifier(null);
        }
        return startsWithIdentifier;
    }

    public boolean containsIdentifiers() {
        return containsToken(getBundle().getTokenTypeBundle().getIdentifier());
    }

    /**
     * This method returns all possible tokens (optional or not) which may follow current element.
     *
     * NOTE: to be used only for limited scope, since the tree walk-up
     * is done only until first named element is hit.
     * (named elements do not have parents)
     */
    public Set<TokenType> getNextPossibleTokens() {
        if (nextPossibleTokens == null) {
            nextPossibleTokens = new THashSet<TokenType>();
            ElementType elementType = getElementType();
            ElementType parentElementType = elementType.getParent();
            while (parentElementType != null) {
                if (parentElementType instanceof SequenceElementType) {
                    SequenceElementType sequenceElementType = (SequenceElementType) parentElementType;
                    int elementsCount = sequenceElementType.getElementTypes().length;
                    int index = sequenceElementType.indexOf(elementType, 0);

                    for (int i = index + 1; i < elementsCount; i++) {
                        ElementType next = sequenceElementType.getElementTypes()[i];
                        nextPossibleTokens.addAll(next.getLookupCache().getFirstPossibleTokens());
                        if (!sequenceElementType.isOptional(i)) {
                            parentElementType = null;
                            break;
                        }
                    }
                } else if (parentElementType instanceof IterationElementType) {
                    IterationElementType iteration = (IterationElementType) parentElementType;
                    TokenElementType[] separatorTokens = iteration.getSeparatorTokens();
                    if (separatorTokens != null) {
                        for (TokenElementType separatorToken : separatorTokens) {
                            nextPossibleTokens.add(separatorToken.getTokenType());
                        }
                    }
                }
                if (parentElementType != null) {
                    elementType = parentElementType;
                    parentElementType = elementType.getParent();
                }
            }
        }
        return nextPossibleTokens;
    }

    /**
     * This method returns all required tokens which may follow current element.
     *
     * NOTE: to be used only for limited scope, since the tree walk-up
     * is done only until first named element is hit.
     * (named elements do not have parents)
     */
    public Set<TokenType> getNextRequiredTokens() {
        if (nextRequiredTokens == null) {
            nextRequiredTokens = new THashSet<TokenType>();
            ElementType elementType = getElementType();
            ElementType parentElementType = elementType.getParent();
            while (parentElementType != null) {
                if (parentElementType instanceof SequenceElementType) {
                    SequenceElementType sequence = (SequenceElementType) parentElementType;
                    int elementsCount = sequence.getElementTypes().length;
                    int index = sequence.indexOf(elementType, 0);

                    for (int i = index + 1; i < elementsCount; i++) {
                        if (!sequence.isOptional(i)) {
                            ElementType next = sequence.getElementTypes()[i];
                            nextRequiredTokens.addAll(next.getLookupCache().getFirstPossibleTokens());
                            parentElementType = null;
                            break;
                        }
                    }
                } else if (parentElementType instanceof IterationElementType) {
                    IterationElementType iteration = (IterationElementType) parentElementType;
                    TokenElementType[] separatorTokens = iteration.getSeparatorTokens();
                    if (separatorTokens == null) {
                        nextRequiredTokens.addAll(iteration.getLookupCache().getFirstPossibleTokens());
                    } else {
                        for (TokenElementType separatorToken : separatorTokens) {
                            nextRequiredTokens.add(separatorToken.getTokenType());
                        }
                    }
                } else if (parentElementType instanceof QualifiedIdentifierElementType){
                    QualifiedIdentifierElementType qualifiedIdentifier = (QualifiedIdentifierElementType) parentElementType;
                    for (LeafElementType[] variant : qualifiedIdentifier.getVariants()) {
                        for (int i=0; i<variant.length; i++) {
                            if (variant[i] == elementType && i < variant.length-1) {
                                nextRequiredTokens.add(qualifiedIdentifier.getSeparatorToken().getTokenType());
                                parentElementType = null;
                                break;
                            }
                        }
                    }
                }
                if (parentElementType != null) {
                    elementType = parentElementType;
                    parentElementType = elementType.getParent();
                }
            }
        }
        return nextRequiredTokens;
    }
}
