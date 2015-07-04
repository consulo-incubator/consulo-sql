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
import com.intellij.psi.PsiElement;

public abstract class PsiScopeVisitor<T> {
    public final T visit(BasePsiElement element) {
        BasePsiElement scope = element.getEnclosingScopePsiElement();
        while (scope!= null) {
            boolean breakTreeWalk = visitScope(scope);
            if (breakTreeWalk || scope.isScopeIsolation()) break;

            // LOOKUP
            PsiElement parent = scope.getParent();
            if (parent instanceof BasePsiElement) {
                BasePsiElement basePsiElement = (BasePsiElement) parent;
                scope = basePsiElement.getEnclosingScopePsiElement();

            } else {
                scope = null;
            }
        }

        return getResult();
    }

    public abstract void setResult(T result);

    public abstract T getResult();

    protected abstract boolean visitScope(BasePsiElement scope);
}
