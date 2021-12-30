/*
 * Copyright 2021 pi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.huberb.prototyping.lanterna.examples.dialogs;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LocalizedString;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;

/**
 *
 * @author pi
 */
public class TextBoxDialog extends DialogWindow {

    private String result;

    public TextBoxDialog(
            String title,
            String description,
            TerminalSize listBoxPreferredSize,
            String message) {

        super(title);
        this.result = null;
        if (message.isEmpty()) {
            throw new IllegalStateException("TextBoxDialog needs a message");
        }

        final TextBox textBox = new TextBox(message);
        textBox.setReadOnly(true);

        final Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(
                new GridLayout(1)
                        .setLeftMarginSize(1)
                        .setRightMarginSize(1));
        if (description != null) {
            mainPanel.addComponent(new Label(description));
            mainPanel.addComponent(new EmptySpace(TerminalSize.ONE));
        }
        textBox.setLayoutData(
                GridLayout.createLayoutData(
                        GridLayout.Alignment.FILL,
                        GridLayout.Alignment.CENTER,
                        true,
                        false))
                .addTo(mainPanel);
        mainPanel.addComponent(new EmptySpace(TerminalSize.ONE));

        final Panel buttonPanel = new Panel();
        buttonPanel.setLayoutManager(new GridLayout(2).setHorizontalSpacing(1));
        buttonPanel.addComponent(new Button(LocalizedString.OK.toString(), this::onOK)
                .setLayoutData(
                        GridLayout.createLayoutData(
                                GridLayout.Alignment.CENTER,
                                GridLayout.Alignment.CENTER,
                                true,
                                false))
        );
        //buttonPanel.addComponent(new Button(LocalizedString.Cancel.toString(), this::onCancel));
        buttonPanel.addTo(mainPanel);

        setComponent(mainPanel);
    }

    private void onOK() {
        result = "OK";
        close();
    }

    /**
     * {@inheritDoc}
     *
     * @param textGUI Text GUI to add the dialog to
     * @return The item in the list that was selected or {@code null} if the
     * dialog was cancelled
     */
    @Override
    public String showDialog(WindowBasedTextGUI textGUI) {
        result = null;
        super.showDialog(textGUI);
        return result;
    }

    /**
     * Shortcut for quickly creating a new dialog
     *
     * @param textGUI Text GUI to add the dialog to
     * @param title Title of the dialog
     * @param description Description of the dialog
     * @param content
     * @return The selected item or {@code null} if cancelled
     */
    public static String showDialog(WindowBasedTextGUI textGUI,
            String title,
            String description,
            String content) {
        return showDialog(textGUI, title, description, null, content);
    }

    /**
     * Shortcut for quickly creating a new dialog
     *
     * @param textGUI Text GUI to add the dialog to
     * @param title Title of the dialog
     * @param description Description of the dialog
     * @param listBoxHeight Maximum height of the list box, scrollbars will be
     * used if there are more items
     * @param content
     * @return The selected item or {@code null} if cancelled
     */
    public static String showDialog(WindowBasedTextGUI textGUI,
            String title,
            String description,
            int listBoxHeight,
            String content) {
        int width = 40;
//        for (T item : items) {
//            width = Math.max(width, TerminalTextUtils.getColumnWidth(item.toString()));
//        }
//        width += 2;
        return showDialog(textGUI, title, description, new TerminalSize(width, listBoxHeight), content);
    }

    /**
     * Shortcut for quickly creating a new dialog
     *
     * @param textGUI Text GUI to add the dialog to
     * @param title Title of the dialog
     * @param description Description of the dialog
     * @param listBoxSize Maximum size of the list box, scrollbars will be used
     * if the items cannot fit
     * @param content
     * @return The selected item or {@code null} if cancelled
     */
    public static String showDialog(WindowBasedTextGUI textGUI,
            String title,
            String description,
            TerminalSize listBoxSize,
            String content) {
        final TextBoxDialog textBoxDialog = new TextBoxDialogBuilder()
                .setTitle(title)
                .setDescription(description)
                .setListBoxSize(listBoxSize)
                .setContent(content)
                .build();
        return textBoxDialog.showDialog(textGUI);
    }

}
