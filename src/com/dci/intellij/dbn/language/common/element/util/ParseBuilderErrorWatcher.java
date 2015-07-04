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

package com.dci.intellij.dbn.language.common.element.util;

public class ParseBuilderErrorWatcher {
    private static final ThreadLocal<Integer> offset = new ThreadLocal<Integer>();
    private static final ThreadLocal<Long> timestamp = new ThreadLocal<Long>();

    public static boolean show(int offset, long timestamp) {
        boolean show =
                ParseBuilderErrorWatcher.offset.get() == null ||
                ParseBuilderErrorWatcher.offset.get() != offset ||
                ParseBuilderErrorWatcher.timestamp.get() == null ||
                ParseBuilderErrorWatcher.timestamp.get() != timestamp;
        if (show) {
            ParseBuilderErrorWatcher.offset.set(offset);
            ParseBuilderErrorWatcher.timestamp.set(timestamp);
        }
        return show;
    }
}
