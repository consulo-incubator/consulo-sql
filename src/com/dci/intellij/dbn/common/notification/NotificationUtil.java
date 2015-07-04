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

package com.dci.intellij.dbn.common.notification;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationListener;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.event.HyperlinkEvent;
import java.text.MessageFormat;

public class NotificationUtil {

    public static void sendInfoNotification(Project project, String title, String message, String ... args) {
        sendNotification(project, NotificationType.INFORMATION, title, message, args);
    }

    public static void sendWarningNotification(Project project, String title, String message, String ... args) {
        sendNotification(project, NotificationType.WARNING, title, message, args);
    }

    public static void sendErrorNotification(Project project, String title, String message, String ... args) {
        sendNotification(project, NotificationType.ERROR, title, message, args);
    }

    public static void sendNotification(Project project, NotificationType type, String title, String message, String ... args) {
        final NotificationListener listener = new NotificationListener() {
            public void hyperlinkUpdate(@NotNull Notification notification, @NotNull HyperlinkEvent event) {
                notification.expire();
            }
        };

        message = MessageFormat.format(message, args);
        Notification notification = new Notification("Database Navigator", title, message, type);
        Notifications.Bus.notify(notification, project);
    }
}
