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

package com.dci.intellij.dbn.common.list;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ReversedList<T> implements Iterable<T> {
    private final List<T> original;

    public ReversedList(List<T> original) {
        this.original = original;
    }

    public Iterator<T> iterator() {
        final ListIterator<T> i = original.listIterator(original.size());
        return new Iterator<T>() {
            public boolean hasNext() {
                return i.hasPrevious();
            }

            public T next() {
                return i.previous();
            }

            public void remove() {
                i.remove();
            }
        };
    }

    public static <T> ReversedList<T> get(List<T> original) {
        return new ReversedList<T>(original);
    }
}
