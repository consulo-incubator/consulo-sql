package com.dci.intellij.dbn.data.editor.ui;

import com.dci.intellij.dbn.common.Icons;
import com.intellij.openapi.project.Project;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.Document;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class TextFieldWithPopup extends JPanel implements DataEditorComponent {
    private JTextField textField;
    private JButton button;

    private List<TextFieldPopupProviderForm> popupProviders = new ArrayList<TextFieldPopupProviderForm>();
    private UserValueHolder userValueHolder;
    private boolean showsButton;
    private Project project;

    public TextFieldWithPopup(Project project) {
        super(new BorderLayout(2, 0));
        this.project = project;
        setBounds(0, 0, 0, 0);

        textField = new JTextField();
        textField.setMargin(new Insets(1, 3, 1, 1));
        add(textField, BorderLayout.CENTER);

        button = new JButton(Icons.DATA_EDITOR_BROWSE);
        button.setFocusable(false);
        button.addActionListener(actionListener);
        button.setMargin(new Insets(0, 4, 0, 4));
        button.setVisible(false);
        button.setBackground(getBackground());
        add(button, BorderLayout.EAST);
        textField.setPreferredSize(new Dimension(150, -1));
        textField.addKeyListener(keyListener);
        textField.addFocusListener(focusListener);

        customizeButton(button);
        customizeTextField(textField);
    }

    public Project getProject() {
        return project;
    }

    public void setEditable(boolean editable){
        textField.setEditable(editable);
    }
                                                                                  
    public void setUserValueHolder(UserValueHolder userValueHolder) {
        this.userValueHolder = userValueHolder;
    }

    private void updateButtonToolTip() {
        if (popupProviders.size() == 1) {
            TextFieldPopupProviderForm popupProvider = getDefaultPopupProvider();
            String toolTipText = "Open " + popupProvider.getDescription();
            String keyShortcutDescription = popupProvider.getKeyShortcutDescription();
            if (keyShortcutDescription != null) {
                toolTipText += " (" + keyShortcutDescription + ")";
            }
            button.setToolTipText(toolTipText);
        }
    }

    public void customizeTextField(JTextField textField) {}
    public void customizeButton(JButton button) {}

    public boolean isSelected() {
        Document document = textField.getDocument();
        return document.getLength() > 0 &&
               textField.getSelectionStart() == 0 &&
               textField.getSelectionEnd() == document.getLength();
    }

    public void clearSelection() {
        if (isSelected()) {
            textField.setSelectionStart(0);
            textField.setSelectionEnd(0);
            textField.setCaretPosition(0);
        }
    }

    public JTextField getTextField() {
        return textField;
    }

    @Override
    public String getText() {
        return textField.getText();
    }

    @Override
    public void setText(String text) {
        textField.setText(text);
    }

    public JButton getButton() {
        return button;
    }

    @Override
    public void setEnabled(boolean enabled) {
        //textField.setEnabled(enabled);
        textField.setEditable(enabled);
        button.setVisible(showsButton && enabled);
    }

    /******************************************************
     *                    PopupProviders                  *
     ******************************************************/
    public void createValuesListPopup(List<String> valuesList, boolean useDynamicFiltering) {
        ValuesListPopupProviderForm listPopupProviderForm = new ValuesListPopupProviderForm(this, valuesList, useDynamicFiltering);
        popupProviders.add(listPopupProviderForm);
        updateButtonToolTip();
    }

    public void createValuesListPopup(ListPopupValuesProvider valuesProvider, boolean useDynamicFiltering) {
        ValuesListPopupProviderForm listPopupProvider = new ValuesListPopupProviderForm(this, valuesProvider, useDynamicFiltering);
        popupProviders.add(listPopupProvider);
        updateButtonToolTip();
    }

    public void createTextAreaPopup(boolean autoPopup) {
        TextEditorPopupProviderForm popupProvider = new TextEditorPopupProviderForm(this, autoPopup);
        popupProviders.add(popupProvider);
        updateButtonToolTip();
        showsButton = true;
        button.setVisible(true);
    }

    public void createCalendarPopup(boolean autoPopup) {
        CalendarPopupProviderForm popupProvider = new CalendarPopupProviderForm(this, autoPopup);
        popupProviders.add(popupProvider);
        updateButtonToolTip();
        showsButton = true;
        button.setVisible(true);
    }

    public void setPopupEnabled(TextFieldPopupType popupType, boolean enabled) {
        for (TextFieldPopupProviderForm popupProvider : popupProviders) {
            if (popupProvider.getPopupType() == popupType) {
                popupProvider.setEnabled(enabled);
                if (popupProvider == getDefaultPopupProvider()) {
                    button.setVisible(enabled);
                }
                break;
            }
        }
    }

    public void disposeActivePopup() {
        TextFieldPopupProviderForm popupProvider = getActivePopupProvider();
        if ( popupProvider != null) {
             popupProvider.disposePopup();
        }
    }

    public TextFieldPopupProviderForm getAutoPopupProvider() {
        for (TextFieldPopupProviderForm popupProvider : popupProviders) {
            if (popupProvider.isAutoPopup()) {
                return popupProvider;
            }
        }
        return null;
    }

    public TextFieldPopupProviderForm getDefaultPopupProvider() {
        return popupProviders.get(0);
    }

    public TextFieldPopupProviderForm getActivePopupProvider() {
        for (TextFieldPopupProviderForm popupProvider : popupProviders) {
            if (popupProvider.isShowingPopup()) {
                return popupProvider;
            }
        }
        return null;
    }

    public TextFieldPopupProviderForm getPopupProvider(KeyEvent keyEvent) {
        for (TextFieldPopupProviderForm popupProvider : popupProviders) {
            if (popupProvider.matchesKeyEvent(keyEvent)) {
                return popupProvider;
            }
        }
        return null;
    }

    /********************************************************
     *                    FocusListener                     *
     ********************************************************/
    private FocusListener focusListener = new FocusAdapter() {
        @Override
        public void focusLost(FocusEvent focusEvent) {
            TextFieldPopupProviderForm popupProvider = getActivePopupProvider();
            if (popupProvider != null) {
                popupProvider.handleFocusLostEvent(focusEvent);
            }
        }
    };

    /********************************************************
     *                      KeyListener                     *
     ********************************************************/
    private KeyListener keyListener = new KeyAdapter() {
        public void keyPressed(KeyEvent keyEvent) {
            TextFieldPopupProviderForm popupProvider = getActivePopupProvider();
            if (popupProvider != null) {
                popupProvider.handleKeyPressedEvent(keyEvent);

            } else {
                popupProvider = getPopupProvider(keyEvent);
                if (popupProvider != null && popupProvider.isEnabled()) {
                    disposeActivePopup();
                    popupProvider.showPopup();
                }
            }
        }

        public void keyReleased(KeyEvent keyEvent) {
            TextFieldPopupProviderForm popupProviderForm = getActivePopupProvider();
            if (popupProviderForm != null) {
                popupProviderForm.handleKeyReleasedEvent(keyEvent);

            }
        }
    };
    /********************************************************
     *                    ActionListener                    *
     ********************************************************/
    private ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            TextFieldPopupProviderForm defaultPopupProvider = getDefaultPopupProvider();
            TextFieldPopupProviderForm popupProvider = getActivePopupProvider();
            if (popupProvider == null || popupProvider != defaultPopupProvider) {
                disposeActivePopup();
                defaultPopupProvider.showPopup();
            }
        }
    };

    public UserValueHolder getUserValueHolder() {
        return userValueHolder;
    }
}
