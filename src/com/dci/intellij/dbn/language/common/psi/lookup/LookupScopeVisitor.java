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

public abstract class LookupScopeVisitor extends PsiScopeVisitor<BasePsiElement> {
    private BasePsiElement result;

    protected final boolean visitScope(BasePsiElement scope) {
        result = performLookup(scope);
        return result != null;
    }

    public BasePsiElement getResult() {
        return result;
    }

    @Override
    public void setResult(BasePsiElement result) {
        this.result = result;
    }

    protected abstract BasePsiElement performLookup(BasePsiElement scope);
}
