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

package com.dci.intellij.dbn.data.export.ui;

import com.dci.intellij.dbn.common.Constants;
import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.common.ui.DBNForm;
import com.dci.intellij.dbn.common.ui.DBNFormImpl;
import com.dci.intellij.dbn.common.ui.DBNHeaderForm;
import com.dci.intellij.dbn.common.util.MessageUtil;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.data.export.DataExportFormat;
import com.dci.intellij.dbn.data.export.DataExportInstructions;
import com.dci.intellij.dbn.data.export.processor.DataExportProcessor;
import com.dci.intellij.dbn.object.DBTable;
import com.dci.intellij.dbn.object.common.DBObject;
import com.dci.intellij.dbn.object.common.DBSchemaObject;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ExportDataForm extends DBNFormImpl implements DBNForm {
    private static final FileChooserDescriptor DIRECTORY_FILE_DESCRIPTOR = new FileChooserDescriptor(false, true, false, false, false, false);

    private JPanel mainPanel;
    private JRadioButton scopeGlobalRadioButton;
    private JRadioButton scopeSelectionRadioButton;
    private JRadioButton formatSQLRadioButton;
    private JRadioButton formatExcelRadioButton;
    private JRadioButton formatCSVRadioButton;
    private JRadioButton formatCustomRadioButton;
    private JRadioButton formatHTMLRadioButton;
    private JRadioButton formatXMLRadioButton;

    private JTextField valueSeparatorTextField;
    private JRadioButton destinationClipboardRadioButton;
    private JRadioButton destinationFileRadioButton;
    private JTextField fileNameTextField;
    private TextFieldWithBrowseButton fileLocationTextField;
    private JCheckBox createHeaderCheckBox;
    private JCheckBox quoteValuesCheckBox;
    private JCheckBox quoteAllValuesCheckBox;
    private JPanel headerPanel;
    private JPanel scopePanel;
    private JPanel formatPanel;
    private JPanel destinationPanel;
    private JPanel optionsPanel;

    private DBObject sourceObject;
    private DataExportInstructions instructions;

    public ExportDataForm(DataExportInstructions instructions, boolean hasSelection, @NotNull ConnectionHandler connectionHandler, @Nullable DBObject sourceObject) {
        this.sourceObject = sourceObject;
        this.instructions = instructions;
        updateBorderTitleForeground(scopePanel);
        updateBorderTitleForeground(formatPanel);
        updateBorderTitleForeground(destinationPanel);
        updateBorderTitleForeground(optionsPanel);

        scopeGlobalRadioButton.addActionListener(actionListener);
        scopeSelectionRadioButton.addActionListener(actionListener);
        formatSQLRadioButton.addActionListener(actionListener);
        formatHTMLRadioButton.addActionListener(actionListener);
        formatXMLRadioButton.addActionListener(actionListener);
        formatExcelRadioButton.addActionListener(actionListener);
        formatCSVRadioButton.addActionListener(actionListener);
        formatCustomRadioButton.addActionListener(actionListener);
        destinationClipboardRadioButton.addActionListener(actionListener);
        destinationFileRadioButton.addActionListener(actionListener);

        scopeSelectionRadioButton.setEnabled(hasSelection);
        scopeSelectionRadioButton.setSelected(hasSelection);
        scopeGlobalRadioButton.setSelected(!hasSelection);

        formatSQLRadioButton.setEnabled(sourceObject instanceof DBTable);

        if (formatSQLRadioButton.isEnabled()) {
            formatSQLRadioButton.setSelected(instructions.getFormat() == DataExportFormat.SQL);
        }

        formatExcelRadioButton.setSelected(instructions.getFormat() == DataExportFormat.EXCEL);
        formatHTMLRadioButton.setSelected(instructions.getFormat() == DataExportFormat.HTML);
        formatXMLRadioButton.setSelected(instructions.getFormat() == DataExportFormat.XML);
        formatCSVRadioButton.setSelected(instructions.getFormat() == DataExportFormat.CSV);
        formatCustomRadioButton.setSelected(instructions.getFormat() == DataExportFormat.CUSTOM);

        valueSeparatorTextField.setText(instructions.getValueSeparator());
        createHeaderCheckBox.setSelected(instructions.createHeader());


        DataExportInstructions.Destination destination = instructions.getDestination();
        if (destinationClipboardRadioButton.isEnabled()) {
            destinationClipboardRadioButton.setSelected(destination == DataExportInstructions.Destination.CLIPBOARD);
            destinationFileRadioButton.setSelected(destination == DataExportInstructions.Destination.FILE);
        } else {
            destinationFileRadioButton.setSelected(true);
        }

        //fileNameTextField.setText(instructions.getFileName());
        fileLocationTextField.setText(instructions.getFileLocation());

        Project project = connectionHandler.getProject();
        fileLocationTextField.addBrowseFolderListener(
                "Select directory",
                "Select destination directory for the exported file", project, DIRECTORY_FILE_DESCRIPTOR);
        
        enableDisableFields();

        String headerTitle;
        Icon headerIcon;
        Color headerBackground = UIUtil.getPanelBackground();
        if (getEnvironmentSettings(project).getVisibilitySettings().getDialogHeaders().value()) {
            headerBackground = connectionHandler.getEnvironmentType().getColor();
        }
        if (sourceObject != null) {
            headerTitle = sourceObject instanceof DBSchemaObject ? sourceObject.getQualifiedName() : sourceObject.getName();
            headerIcon = sourceObject.getIcon();
        } else {
            headerIcon = Icons.DBO_TABLE;
            headerTitle = instructions.getBaseName();
        }
        DBNHeaderForm headerComponent = new DBNHeaderForm(headerTitle, headerIcon, headerBackground);
        headerPanel.add(headerComponent.getComponent());
    }

    public JPanel getComponent() {
        return mainPanel;
    }

    public DataExportInstructions getExportInstructions() {
        instructions.setScope(scopeSelectionRadioButton.isSelected() ?
                DataExportInstructions.Scope.SELECTION  :
                DataExportInstructions.Scope.GLOBAL);
        instructions.setCreateHeader(createHeaderCheckBox.isSelected());
        instructions.quoteValuesContainingSeparator(quoteValuesCheckBox.isSelected());
        instructions.setQuoteAllValues(quoteAllValuesCheckBox.isSelected());
        instructions.setValueSeparator(valueSeparatorTextField.isEnabled() ? valueSeparatorTextField.getText().trim() : null);
        if (destinationFileRadioButton.isSelected()) {
            instructions.setFileName(fileNameTextField.getText());
            instructions.setFileLocation(fileLocationTextField.getText());
        }

        instructions.setDestination(destinationClipboardRadioButton.isSelected() ?
                DataExportInstructions.Destination.CLIPBOARD :
                DataExportInstructions.Destination.FILE);

        instructions.setFormat(getFormat());
        return instructions;
    }

    private DataExportFormat getFormat() {
        return
            formatSQLRadioButton.isSelected() ? DataExportFormat.SQL :
            formatExcelRadioButton.isSelected() ? DataExportFormat.EXCEL :
            formatHTMLRadioButton.isSelected() ? DataExportFormat.HTML :
            formatXMLRadioButton.isSelected() ? DataExportFormat.XML :
            formatCSVRadioButton.isSelected() ? DataExportFormat.CSV :
            formatCustomRadioButton.isSelected() ? DataExportFormat.CUSTOM : null;
    }

    public boolean validateEntries() {
        boolean validValueSeparator = valueSeparatorTextField.getText().trim().length() > 0;
        boolean validFileName = fileNameTextField.getText().trim().length() > 0;
        boolean validFileLocation = fileLocationTextField.getText().trim().length() > 0;
        StringBuilder buffer = new StringBuilder();
        if (valueSeparatorTextField.isEnabled()) {
            if (!validValueSeparator)  buffer.append("Value Separator");
        }
        if (fileNameTextField.isEnabled()) {
            if (!validFileName)  {
                if (buffer.length() > 0) buffer.append(", ");
                buffer.append("File Name");
            }
            if (!validFileLocation) {
                if (buffer.length() > 0) buffer.append(", ");
                buffer.append("File Location");
            }
        }

        if (buffer.length() > 0) {
            buffer.insert(0, "Please provide values for: ");
            MessageUtil.showErrorDialog(buffer.toString(), "Required input");
            return false;
        }

        if (destinationFileRadioButton.isSelected()) {
            File file = getExportInstructions().getFile();
            if (file.exists()) {
                int response = Messages.showYesNoDialog(
                        "File " + file.getPath() + " already exists. Overwrite?", Constants.DBN_TITLE_PREFIX + "Warning",
                        Messages.getQuestionIcon());
                return response == DialogWrapper.OK_EXIT_CODE;
            }
        }

        return true;
    }

    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            enableDisableFields();
        }
    };

    private void enableDisableFields() {
        DataExportProcessor processor = DataExportProcessor.getExportProcessor(getExportInstructions().getFormat());

        boolean canCreateHeader = processor != null && processor.canCreateHeader();
        boolean canQuoteValues = processor != null && processor.canQuoteValues();
        boolean canExportToClipboard = processor != null && processor.canExportToClipboard();

        destinationClipboardRadioButton.setEnabled(canExportToClipboard);
        quoteValuesCheckBox.setEnabled(canQuoteValues);
        quoteAllValuesCheckBox.setEnabled(canQuoteValues);
        createHeaderCheckBox.setEnabled(canCreateHeader);

        if (!destinationClipboardRadioButton.isEnabled() && destinationClipboardRadioButton.isSelected()) {
            destinationFileRadioButton.setSelected(true);
        }

        valueSeparatorTextField.setEnabled(formatCustomRadioButton.isSelected());
        fileNameTextField.setEnabled(destinationFileRadioButton.isSelected());
        fileLocationTextField.setEnabled(destinationFileRadioButton.isSelected());

        String fileNameBase = sourceObject == null ? instructions.getBaseName() : sourceObject.getName();
        if (fileNameBase != null && processor != null) {
            String fileName = fileNameBase + "." + processor.getFileExtension();
            fileNameTextField.setText(fileName);
        }
    }

    public void dispose() {
        super.dispose();
        sourceObject = null;
    }
}
