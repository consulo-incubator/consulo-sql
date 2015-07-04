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

package com.dci.intellij.dbn.common;

public class TimeUtil {
    public static int ONE_SECOND = 1000;
    public static int ONE_MINUTE = 1000*60;
    public static int FIVE_MINUTES = ONE_MINUTE * 1; // *5

    public static int getMinutes(int seconds) {
        return seconds / 60;
    }

    public static int getSeconds(int minutes) {
        return minutes * 60;
    }

}
