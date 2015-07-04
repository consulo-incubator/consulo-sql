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

import java.awt.Color;

public class DBNColor extends Color {
    private int regularRgb;
    private int darkRgb;

    public DBNColor(int regularRgb, int darkRgb) {
        super(isDark() ? darkRgb : regularRgb);
        this.regularRgb = regularRgb;
        this.darkRgb = darkRgb;
    }

    public DBNColor(Color regular, Color dark) {
        super(isDark() ? dark.getRGB() : regular.getRGB(), (isDark() ? dark : regular).getAlpha() != 255);
        this.regularRgb = regular.getRGB();
        this.darkRgb = dark.getRGB();
    }

    public DBNColor set(Color color) {
        return isDark() ?
                new DBNColor(regularRgb, color.getRGB()) :
                new DBNColor(color.getRGB(), darkRgb);
    }

    private static boolean isDark() {
        return GUIUtil.isDarkLookAndFeel();
    }

    public int getRegularRgb() {
        return regularRgb;
    }

    public int getDarkRgb() {
        return darkRgb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        DBNColor dbnColor = (DBNColor) o;

        if (darkRgb != dbnColor.darkRgb) return false;
        if (regularRgb != dbnColor.regularRgb) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + regularRgb;
        result = 31 * result + darkRgb;
        return result;
    }
}
