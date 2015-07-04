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

package com.dci.intellij.dbn.common.ui;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.Insets;

public interface Borders {
    Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);
    Border EMPTY_BORDER = new EmptyBorder(EMPTY_INSETS);
}
