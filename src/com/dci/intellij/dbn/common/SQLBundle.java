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

import com.intellij.CommonBundle;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.PropertyKey;

import java.util.ResourceBundle;

public class SQLBundle {
  @NonNls private static final String COM_INTELLIJ_LANG_SQL_BUNDLE = "com.dci.intellij.dbn.common.SQLBundle";
  private static final ResourceBundle ourBundle = ResourceBundle.getBundle(COM_INTELLIJ_LANG_SQL_BUNDLE);

  private  SQLBundle(){}

  public static String message(@PropertyKey(resourceBundle = "com.dci.intellij.dbn.common.SQLBundle") String key, Object... params) {
    return CommonBundle.message(ourBundle, key, params);
  }
}
