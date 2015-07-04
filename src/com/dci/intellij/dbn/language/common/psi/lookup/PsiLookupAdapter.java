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

package com.dci.intellij.dbn.language.common.psi.lookup;

import com.dci.intellij.dbn.language.common.psi.BasePsiElement;
import com.dci.intellij.dbn.language.common.psi.IdentifierPsiElement;

import java.util.Set;

public abstract class PsiLookupAdapter {
    public abstract boolean matches(BasePsiElement element);

    public abstract boolean accepts(BasePsiElement element);

    public final BasePsiElement findInParentScopeOf(final IdentifierPsiElement source) {
        //System.out.println(this);
        LookupScopeVisitor finder = new LookupScopeVisitor() {
            protected BasePsiElement performLookup(BasePsiElement scope) {
                BasePsiElement result = scope.lookupPsiElement(PsiLookupAdapter.this, 10);
                return result == null || result == source ? null : result;
            }
        };
        return finder.visit(source);
    }

    public final BasePsiElement findInScope(BasePsiElement scope) {
        return scope.lookupPsiElement(this, 100);
    }

    public final BasePsiElement findInElement(BasePsiElement element) {
        return element.lookupPsiElement(this, 100);
    }


    public final Set<BasePsiElement> collectInParentScopeOf(BasePsiElement source) {
        return collectInParentScopeOf(source, null);
    }


    public final Set<BasePsiElement> collectInParentScopeOf(BasePsiElement source, Set<BasePsiElement> bucket) {
        CollectScopeVisitor collector = new CollectScopeVisitor() {
            protected Set<BasePsiElement> performCollect(BasePsiElement scope) {
                return scope.collectPsiElements(PsiLookupAdapter.this, getResult(), 1);
            }

        };
        collector.setResult(bucket);
        return collector.visit(source);
    }

    public final Set<BasePsiElement> collectInScope(BasePsiElement scope, Set<BasePsiElement> bucket) {
        if (!scope.isScopeBoundary()) scope = scope.getEnclosingScopePsiElement();
        return scope.collectPsiElements(this, bucket, 100);
    }

    public final Set<BasePsiElement> collectInElement(BasePsiElement element, Set<BasePsiElement> bucket) {
        return element.collectPsiElements(this, bucket, 100);
    }
}
