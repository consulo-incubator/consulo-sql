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

package com.dci.intellij.dbn.database.oracle;

import com.dci.intellij.dbn.database.DatabaseMessageParserInterface;
import com.dci.intellij.dbn.database.DatabaseObjectIdentifier;
import com.dci.intellij.dbn.database.common.DatabaseObjectIdentifierImpl;
import com.dci.intellij.dbn.object.common.DBObjectType;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.StringTokenizer;

public class OracleMessageParserInterface implements DatabaseMessageParserInterface {
    @Nullable
    public DatabaseObjectIdentifier identifyObject(String message) {
        if (message.startsWith("ORA-01400")) return identifyColumn(message);
        if (message.startsWith("ORA-12899")) return identifyColumn(message);
        if (message.startsWith("ORA-00001")) return identifyConstraint(message);
        if (message.startsWith("ORA-02291")) return identifyConstraint(message);
        if (message.startsWith("ORA-02290")) return identifyConstraint(message);
        if (message.startsWith("ORA-04098")) return identifyTrigger(message);
        return null;
    }

    public boolean isTimeoutException(SQLException e) {
        return e.getErrorCode() == 1013;
    }

    public boolean isModelException(SQLException e) {
        return e.getErrorCode() == 942;
    }

    private DatabaseObjectIdentifier identifyColumn(String message) {
        int startOffset = message.indexOf("\"");
        int endOffset = message.lastIndexOf("\"");
        StringTokenizer tokenizer = new StringTokenizer(message.substring(startOffset, endOffset + 1), ".");
        DBObjectType[] objectTypeId = new DBObjectType[]{DBObjectType.SCHEMA, DBObjectType.DATASET, DBObjectType.COLUMN};
        String[] objectName = new String[objectTypeId.length];
        objectName[0] = trimQuotes(tokenizer.nextToken());
        objectName[1] = trimQuotes(tokenizer.nextToken());
        objectName[2] = trimQuotes(tokenizer.nextToken());
        return new DatabaseObjectIdentifierImpl(objectTypeId, objectName);
    }

    private DatabaseObjectIdentifier identifyConstraint(String message) {
        int startOffset = message.indexOf("(");
        int endOffset = message.lastIndexOf(")");
        StringTokenizer tokenizer = new StringTokenizer(message.substring(startOffset + 1, endOffset), ".");
        DBObjectType[] objectType = new DBObjectType[]{DBObjectType.SCHEMA, DBObjectType.CONSTRAINT};
        String[] objectName = new String[objectType.length];
        objectName[0] = trimQuotes(tokenizer.nextToken());
        objectName[1] = trimQuotes(tokenizer.nextToken());
        return new DatabaseObjectIdentifierImpl(objectType, objectName);
    }

    private DatabaseObjectIdentifier identifyTrigger(String message) {
        int startOffset = message.indexOf("'");
        int endOffset = message.lastIndexOf("'");
        StringTokenizer tokenizer = new StringTokenizer(message.substring(startOffset + 1, endOffset), ".");
        DBObjectType[] objectType = new DBObjectType[]{DBObjectType.SCHEMA, DBObjectType.TRIGGER};
        String[] objectName = new String[objectType.length];
        objectName[0] = trimQuotes(tokenizer.nextToken());
        objectName[1] = trimQuotes(tokenizer.nextToken());
        return new DatabaseObjectIdentifierImpl(objectType, objectName);
    }

    private String trimQuotes(String string) {
        if (string.charAt(0) == '"' && string.charAt(string.length()-1) == '"'){
            return string.substring(1, string.length()-1);
        } else {
            return string;
        }
    }
}
