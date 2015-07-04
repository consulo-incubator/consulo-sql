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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseUtil {
    public static void processMouseEvent(MouseEvent e, MouseListener listener) {
        int id = e.getID();
        switch (id) {
            case MouseEvent.MOUSE_PRESSED:
                listener.mousePressed(e);
                break;
            case MouseEvent.MOUSE_RELEASED:
                listener.mouseReleased(e);
                break;
            case MouseEvent.MOUSE_CLICKED:
                listener.mouseClicked(e);
                break;
            case MouseEvent.MOUSE_EXITED:
                listener.mouseExited(e);
                break;
            case MouseEvent.MOUSE_ENTERED:
                listener.mouseEntered(e);
                break;
        }
    }

    public static boolean isNavigationEvent(MouseEvent event) {
        int button = event.getButton();
        return button == MouseEvent.BUTTON2 || (event.isControlDown() && button == MouseEvent.BUTTON1);
    }
}
