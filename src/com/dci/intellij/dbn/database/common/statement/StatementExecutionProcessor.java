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

package com.dci.intellij.dbn.database.common.statement;

import com.dci.intellij.dbn.common.LoggerFactory;
import com.dci.intellij.dbn.common.options.setting.SettingsUtil;
import com.dci.intellij.dbn.common.util.StringUtil;
import com.dci.intellij.dbn.connection.ConnectionUtil;
import com.dci.intellij.dbn.database.DatabaseInterfaceProvider;
import com.intellij.openapi.diagnostic.Logger;
import org.jdom.Element;
import org.jetbrains.annotations.Nullable;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StatementExecutionProcessor {
    private DatabaseInterfaceProvider interfaceProvider;
    private static final Logger LOGGER = LoggerFactory.createLogger();
    private String id;
    private boolean isQuery;
    private int timeout;
    private List<StatementDefinition> statementDefinitions = new ArrayList<StatementDefinition>();
    private SQLException lastException;
    public static final int DEFAULT_TIMEOUT = 30;

    public StatementExecutionProcessor(Element element, DatabaseInterfaceProvider interfaceProvider) {
        this.interfaceProvider = interfaceProvider;
        id = element.getAttributeValue("id");
        isQuery = Boolean.parseBoolean(element.getAttributeValue("is-query"));
        String timeoutS = element.getAttributeValue("timeout");
        timeout = StringUtil.isEmpty(timeoutS) ? DEFAULT_TIMEOUT : Integer.parseInt(timeoutS);
        if (element.getChildren().isEmpty()) {
            String statementText = element.getContent(0).getValue().trim();
            readStatements(statementText, null);
        } else {
            for (Object object : element.getChildren()) {
                Element child = (Element) object;
                String statementText = child.getContent(0).getValue().trim();
                String prefixes = child.getAttributeValue("prefixes");
                readStatements(statementText, prefixes);
            }
        }
    }

    private void readStatements(String statementText, String prefixes) {
        if (prefixes == null) {
            StatementDefinition statementDefinition = new StatementDefinition(statementText, null, false);
            statementDefinitions.add(statementDefinition);
        } else {
            StringTokenizer tokenizer = new StringTokenizer(prefixes, ",");
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken().trim();
                StatementDefinition statementDefinition = new StatementDefinition(statementText, token, tokenizer.hasMoreTokens());
                statementDefinitions.add(statementDefinition);
            }
        }
    }

    public DatabaseInterfaceProvider getInterfaceProvider() {
        return interfaceProvider;
    }

    public String getId() {
        return id;
    }

    public ResultSet executeQuery(Connection connection, Object... arguments) throws SQLException {
        return executeQuery(connection, false, arguments);
    }

    public ResultSet executeQuery(Connection connection, boolean forceExecution, Object... arguments) throws SQLException {
        SQLException exception = null;
        for (StatementDefinition statementDefinition : statementDefinitions) {
            try {
                return executeQuery(connection, forceExecution, SettingsUtil.isDebugEnabled, statementDefinition, arguments);
            } catch (SQLException e){
                exception = e;
            }
        }
        throw exception;
    }

    private ResultSet executeQuery(Connection connection, boolean forceExecution, boolean debug, StatementDefinition statementDefinition, Object... arguments) throws SQLException {
        if (forceExecution || statementDefinition.canExecute(connection)) {
            String statementText = statementDefinition.createStatement(arguments);
            if (debug) LOGGER.info("[DBN-INFO] Executing statement: " + statementText);

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(60);
            boolean executionSuccessful = true;
            try {
                statement.execute(statementText);
                if (isQuery) {
                    return statement.getResultSet();
                } else {
                    ConnectionUtil.closeStatement(statement);
                    return null;
                }
            } catch (SQLException exception) {
                executionSuccessful = false;

                if (debug) LOGGER.info("[DBN-ERROR] Error executing statement: " + statementText + "\n" + "Cause: " + exception.getMessage());
                if (interfaceProvider.getMessageParserInterface().isModelException(exception)) {
                    statementDefinition.setDisabled(true);
                    lastException = new SQLException("Model exception received while executing query '" + getId() +"'. " + exception.getMessage());
                } else {
                    lastException = new SQLException("Too many failed attempts of executing query '" + getId() +"'. " + exception.getMessage());
                }

                throw exception;
            } finally {
                statementDefinition.updateExecutionStatus(executionSuccessful);
            }
        } else {
            if (lastException == null) {
                throw new SQLException("Too many failed attempts of executing query '" + getId() + "'.");
            }
            throw lastException;
        }
    }

    public <T extends CallableStatementOutput> T executeCall(@Nullable Object[] arguments, @Nullable T outputReader, Connection connection) throws SQLException {
        SQLException exception = null;
        for (StatementDefinition statementDefinition : statementDefinitions) {
            try {
                return executeCall(statementDefinition, arguments, outputReader, connection, SettingsUtil.isDebugEnabled);
            } catch (SQLException e){
                exception = e;
            }
        }
        throw exception;
    }

    private <T extends CallableStatementOutput> T executeCall(StatementDefinition statementDefinition, @Nullable Object[] arguments, @Nullable T outputReader, Connection connection, boolean debug) throws SQLException {
        String statementText = statementDefinition.createStatement(arguments);
        if (debug) LOGGER.info("[DBN-INFO] Executing statement: " + statementText);

        CallableStatement callableStatement = connection.prepareCall (statementText);
        //callableStatement.setQueryTimeout(20);
        try {
            if (outputReader != null) outputReader.registerParameters(callableStatement);
            callableStatement.setQueryTimeout(timeout);
            callableStatement.execute();
            if (outputReader != null) outputReader.read(callableStatement);
            return outputReader;
        } catch (SQLException exception) {
            if (debug)
                LOGGER.info(
                        "[DBN-ERROR] Error executing statement: " + statementText +
                                "\nCause: " + exception.getMessage());

            throw exception;
        } finally {
            ConnectionUtil.closeStatement(callableStatement);
        }
    }
}
