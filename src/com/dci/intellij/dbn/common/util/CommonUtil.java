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

package com.dci.intellij.dbn.common.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import org.jdom.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.openapi.util.JDOMUtil;

public class CommonUtil {

    public static boolean isPluginCall() {
        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            if (stackTraceElement.getClassName().contains(".dbn.")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCalledThrough(Class clazz) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            if (clazz.getName().equals(stackTraceElement.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static double getProgressPercentage(int is, int should) {
        BigDecimal fraction = new BigDecimal(is).divide(new BigDecimal(should), 6, BigDecimal.ROUND_HALF_UP);
        return fraction.doubleValue();
    }

    @NotNull
    public static <T> T nvl(@Nullable T value, @NotNull T defaultValue) {
        return value == null ? defaultValue : value;
    }

    public static <T> T nvln(@Nullable T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    public static String nullIfEmpty(String string) {
        if (string != null) {
            string = string.trim();
            if (string.length() == 0) {
                string = null;
            }
        }
        return string;
    }

    private static final Object NULL_OBJECT = new Object();

    public static boolean safeEqual(@Nullable Object value1, @Nullable Object value2) {
        return nvl(value1, "").equals(nvl(value2, ""));
    }

    public static Document loadXmlFile(Class clazz, String name) {
        InputStream inputStream = clazz.getResourceAsStream(name);
        return createXMLDocument(inputStream);
    }

    public static Properties loadProperties(Class clazz, String name) {
        InputStream inputStream = clazz.getResourceAsStream(name);
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (Exception e) {
        }
        return properties;
    }

    public static Document createXMLDocument(InputStream inputStream) {
        try {
            return JDOMUtil.loadDocument(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String readFile(File file) throws IOException {
        Reader in = new FileReader(file);
        StringBuilder buffer = new StringBuilder();
        int i;
        while ((i = in.read()) != -1) buffer.append((char) i);
        in.close();
        return buffer.toString();
    }

    public static String readInputStream(InputStream inputStream) throws IOException {
        Reader in = new InputStreamReader(inputStream);
        StringBuilder buffer = new StringBuilder();
        int i;
        while ((i = in.read()) != -1) buffer.append((char) i);
        in.close();
        return buffer.toString();
    }

    public static Set<String> commaSeparatedTokensToSet(String commaSeparated) {
        Set<String> set = new HashSet<String>();
        StringTokenizer stringTokenizer = new StringTokenizer(commaSeparated, ",");
        while (stringTokenizer.hasMoreTokens()) {
            set.add(stringTokenizer.nextToken());
        }
        return set;
    }
}
