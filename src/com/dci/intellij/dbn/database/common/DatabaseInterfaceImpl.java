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

package com.dci.intellij.dbn.database.common;

import com.dci.intellij.dbn.common.util.CommonUtil;
import com.dci.intellij.dbn.database.DatabaseInterfaceProvider;
import com.dci.intellij.dbn.database.common.statement.CallableStatementOutput;
import com.dci.intellij.dbn.database.common.statement.StatementExecutionProcessor;
import org.jdom.Document;
import org.jdom.Element;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseInterfaceImpl {
    protected Map<String, StatementExecutionProcessor> processors = new HashMap<String, StatementExecutionProcessor>();

    public DatabaseInterfaceImpl(String fileName, DatabaseInterfaceProvider provider) {
        Document document = CommonUtil.loadXmlFile(getClass(), fileName);
        Element root = document.getRootElement();
        for (Object child : root.getChildren()) {
            Element element = (Element) child;
            StatementExecutionProcessor executionProcessor = new StatementExecutionProcessor(element, provider);
            String id = executionProcessor.getId();
            processors.put(id, executionProcessor);
        }
    }

    protected ResultSet executeQuery(Connection connection, String loaderId, @Nullable Object... arguments) throws SQLException {
        return executeQuery(connection, false, loaderId, arguments);
    }

    protected ResultSet executeQuery(Connection connection, boolean forceExecution, String loaderId, @Nullable Object... arguments) throws SQLException {
        StatementExecutionProcessor executionProcessor = processors.get(loaderId);
        return executionProcessor.executeQuery(connection, forceExecution, arguments);
    }

    protected <T extends CallableStatementOutput> T executeCall(Connection connection, @Nullable T outputReader, String loaderId, @Nullable Object... arguments) throws SQLException {
        StatementExecutionProcessor executionProcessor = processors.get(loaderId);
        return executionProcessor.executeCall(arguments, outputReader, connection);
    }
}
