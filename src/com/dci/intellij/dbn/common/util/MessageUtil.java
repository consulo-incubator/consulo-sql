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

import com.dci.intellij.dbn.common.Constants;
import com.dci.intellij.dbn.common.message.Message;
import com.dci.intellij.dbn.common.message.MessageBundle;
import com.dci.intellij.dbn.common.thread.ConditionalLaterInvocator;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.Nullable;

public class MessageUtil {
    public static void showErrorDialog(MessageBundle messages, String title) {
        StringBuilder buffer = new StringBuilder();
        for (Message message : messages.getErrorMessages()) {
            buffer.append(message.getText());
            buffer.append("\n");
        }
        showErrorDialog(buffer.toString(), title);
    }

    public static void showErrorDialog(String message, Exception exception) {
        showErrorDialog(message, exception, null);
    }

    public static void showErrorDialog(String message, String title) {
        showErrorDialog(message, null, title);
    }

    public static void showErrorDialog(String message) {
        showErrorDialog(message, null, null);
    }

    public static void showErrorDialog(final String message, @Nullable final Exception exception, @Nullable final String title) {
        new ConditionalLaterInvocator() {
            public void run() {
                String localMessage = message;
                String localTitle = title;
                if (exception != null) {
                    //String className = NamingUtil.getClassName(exception.getClass());
                    //message = message + "\nCause: [" + className + "] " + exception.getMessage();
                    localMessage = localMessage + "\n" + exception.getMessage();
                }
                if (localTitle == null) localTitle = "Error";
                Messages.showErrorDialog(localMessage, Constants.DBN_TITLE_PREFIX + "" + localTitle);
            }
        }.start();
    }

    public static void showInfoMessage(final String message, final String title) {
        new ConditionalLaterInvocator() {
            @Override
            public void run() {
                Messages.showInfoMessage(message, Constants.DBN_TITLE_PREFIX + title);
            }
        }.start();
    }


}
