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

package com.dci.intellij.dbn.browser.cache;

import com.dci.intellij.dbn.connection.ConnectionBundle;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.adapters.XML4JDOMAdapter;
import org.jdom.input.DOMBuilder;
import org.jdom.output.XMLOutputter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class BrowserCacheComponent {
    public static final String FILE_EXTENSION = ".dbi";
    private final File file;
    private final ConnectionBundle connectionBundle;

    public BrowserCacheComponent(File file, ConnectionBundle connectionBundle) {
        this.file = file;
        this.connectionBundle = connectionBundle;
    }

    public void read() {
        try {
            if (file.exists()) {

                InputStream inputStream = new FileInputStream(file);
                Document document = new DOMBuilder().build(new XML4JDOMAdapter().getDocument(inputStream, false));
                Element root = document.getRootElement();
                Element fileConnectionMappings = root.getChild("file-connection-mapings");

                //connectionBundle.jdomRead(root);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write() {
        try {
            OutputStream outputStream = new FileOutputStream(file);
            Element root = new Element("Connections");
            //connectionBundle.jdomWrite(root);
            new XMLOutputter().output(root, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
