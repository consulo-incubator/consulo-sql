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

import com.intellij.openapi.ui.Splitter;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;

public class GUIUtil{
    public static final Font REGULAR_FONT = com.intellij.util.ui.UIUtil.getLabelFont();
    public static final Font BOLD_FONT = new Font(REGULAR_FONT.getName(), Font.BOLD, REGULAR_FONT.getSize());
    public static final String DARK_LAF_NAME = "Darcula";
    public static final Border CONTAINER_BORDER = new LineBorder(com.intellij.util.ui.UIUtil.getBorderColor());

    public static void updateSplitterProportion(final JComponent root, final float proportion) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (root instanceof Splitter) {
                    Splitter splitter = (Splitter) root;
                    splitter.setProportion(proportion);
                } else {
                    Component[] components = root.getComponents();
                    for (Component component : components) {
                        if (component instanceof JComponent) {
                            updateSplitterProportion((JComponent) component, proportion);
                        }
                    }
                }
            }
        });

    }
    
    public static Point getRelativeMouseLocation(Component component) {
        PointerInfo pointerInfo = MouseInfo.getPointerInfo();
        if (pointerInfo == null) {
            return new Point();
        } else {
            Point mouseLocation = pointerInfo.getLocation();
            return getRelativeLocation(mouseLocation, component);
        }
    }
    
    public static Point getRelativeLocation(Point locationOnScreen, Component component) {
        Point componentLocation = component.getLocationOnScreen();
        Point relativeLocation = locationOnScreen.getLocation();
        relativeLocation.move(
                (int) (locationOnScreen.getX() - componentLocation.getX()), 
                (int) (locationOnScreen.getY() - componentLocation.getY()));
        return relativeLocation;
    }

    public static boolean isChildOf(Component component, Component child) {
        Component parent = child == null ? null : child.getParent();
        while (parent != null) {
            if (parent == component) {
                return true;
            }
            parent = parent.getParent();
        }
        return false;
    }

    public static boolean isFocused(Component component, boolean recoursive) {
        if (component.isFocusOwner()) return true;
        if (recoursive && component instanceof JComponent) {
            JComponent parentComponent = (JComponent) component;
            for (Component childComponent : parentComponent.getComponents()) {
                if (isFocused(childComponent, recoursive)) {
                    return true;
                }
            }
        }
        return false;
    }


    public static boolean isDarkLookAndFeel() {
        return UIManager.getLookAndFeel().getName().contains(DARK_LAF_NAME);
    }

    public static boolean supportsDarkLookAndFeel() {
        if (isDarkLookAndFeel()) return true;
        for (UIManager.LookAndFeelInfo lookAndFeelInfo : UIManager.getInstalledLookAndFeels()) {
            if (lookAndFeelInfo.getName().contains(DARK_LAF_NAME)) return true;
        }
        return false;
    }

    public static void updateBorderTitleForeground(JPanel panel) {
        Border border = panel.getBorder();
        if (border instanceof TitledBorder) {
            TitledBorder titledBorder = (TitledBorder) border;
            //titledBorder.setTitleColor(com.intellij.util.ui.GUIUtil.getLabelForeground());
            titledBorder.setTitleColor(new DBNColor(new Color(-12029286), new Color(-10058060)));
        }
    }

}
