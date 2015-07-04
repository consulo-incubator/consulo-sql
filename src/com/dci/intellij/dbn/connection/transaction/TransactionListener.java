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

package com.dci.intellij.dbn.connection.transaction;

import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.intellij.util.messages.Topic;

import java.util.EventListener;

public interface TransactionListener extends EventListener{
    Topic<TransactionListener> TOPIC = Topic.create("Transaction event fired", TransactionListener.class);

    /**
     * This is typically called before the connection has been operationally committed
     * @param connectionHandler
     * @param action
     */
    void beforeAction(ConnectionHandler connectionHandler, TransactionAction action);

    /**
     * This is typically called after the connection has been operationally committed
     * @param connectionHandler indicates if the commit operation was successful or not
     * @param succeeded indicates if the commit operation was successful or not
     */
    void afterAction(ConnectionHandler connectionHandler, TransactionAction action, boolean succeeded);

}
