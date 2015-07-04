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

import java.util.Set;

public abstract class CollectScopeVisitor extends PsiScopeVisitor<Set<BasePsiElement>>{
    private Set<BasePsiElement> bucket;

    protected final boolean visitScope(BasePsiElement scope) {
        bucket = performCollect(scope);
        return false;
    }

    public void setResult(Set<BasePsiElement> bucket) {
        this.bucket = bucket;
    }

    public Set<BasePsiElement> getResult() {
        return bucket;
    }

    protected abstract Set<BasePsiElement> performCollect(BasePsiElement scope);
}
