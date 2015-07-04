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

package com.dci.intellij.dbn.common.dispose;

import com.intellij.openapi.Disposable;

import java.util.Collection;
import java.util.Map;

public class DisposeUtil {
    public static void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public static void disposeCollection(Collection<? extends Disposable> collection) {
        if (collection != null) {
            for(Disposable disposable : collection) {
                dispose(disposable);
            }
            collection.clear();
        }
    }
    
    public static void disposeMap(Map<?,? extends Disposable> map) {
        if (map != null) {
            for (Disposable disposable : map.values()) {
                dispose(disposable);
            }
            map.clear();
        }
    }
}