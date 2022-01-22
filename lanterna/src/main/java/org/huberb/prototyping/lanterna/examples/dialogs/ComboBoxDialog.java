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
import com.googlecode.lanterna.TerminalTextUtils;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.ComboBox.Listener;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LocalizedString;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Panels;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.huberb.prototyping.lanterna.examples.dialogs.ItemLabelWrappings.IItemLabel;

/**
 *
 * @author pi
 * @param <T>
 */
public class ComboBoxDialog<T extends IItemLabel> extends DialogWindow {

    private final Set<T> resultSelected;

    public ComboBoxDialog(
            String title,
            String description,
            TerminalSize listBoxPreferredSize,
            List<T> content) {

        super(title);
        this.resultSelected = new HashSet<>();
        if (content.isEmpty()) {
            throw new IllegalStateException("CheckBoxDialog needs at least one item");
        }
        final ComboBox<T> comboBox = new ComboBox<>();
        comboBox.setPreferredSize(listBoxPreferredSize);
        for (final T item : content) {
            comboBox.addItem(item);
        }
        comboBox.addListener(new Listener() {

            @Override
            public void onSelectionChanged(int selectedIndex, int previousSelection, boolean changedByUserInteraction) {
                if (selectedIndex >= 0) {
                    resultSelected.add(content.get(selectedIndex));
                }
                if (previousSelection >= 0) {
                    resultSelected.remove(content.get(previousSelection));
                }

            }
        });

        final Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(
                new GridLayout(1)
                        .setLeftMarginSize(1)
                        .setRightMarginSize(1));
        if (description != null) {
            mainPanel.addComponent(new Label(description));
            mainPanel.addComponent(new EmptySpace(TerminalSize.ONE));
        }
        comboBox.setLayoutData(
                GridLayout.createLayoutData(
                        GridLayout.Alignment.FILL,
                        GridLayout.Alignment.CENTER,
                        true,
                        false))
                .addTo(mainPanel);
        mainPanel.addComponent(new EmptySpace(TerminalSize.ONE));

        // ButtonPanel
        final Button okButton = new Button(LocalizedString.OK.toString(), this::onOK);
        final Button cancelButton = new Button(LocalizedString.Cancel.toString(), this::onCancel);
        Panels.grid(2,
                okButton,
                cancelButton)
                .setLayoutData(
                        GridLayout.createLayoutData(
                                GridLayout.Alignment.END,
                                GridLayout.Alignment.CENTER,
                                false,
                                false,
                                2,
                                1))
                .addTo(mainPanel);

        setComponent(mainPanel);
    }

    private void onOK() {
        close();
    }

    private void onCancel() {
        this.resultSelected.clear();
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
    public List<T> showDialog(WindowBasedTextGUI textGUI) {
        super.showDialog(textGUI);
        return new ArrayList(this.resultSelected);
    }

    /**
     * Shortcut for quickly creating a new dialog
     *
     * @param textGUI Text GUI to add the dialog to
     * @param title Title of the dialog
     * @param description Description of the dialog
     * @param items Items in the dialog
     * @param <T> Type of items in the dialog
     * @return The selected item or {@code null} if cancelled
     */
    @SafeVarargs
    public static <T extends IItemLabel> List<T> showDialog(WindowBasedTextGUI textGUI,
            String title, String description,
            T... items) {
        return showDialog(textGUI, title, description, null, items);
    }

    /**
     * Shortcut for quickly creating a new dialog
     *
     * @param textGUI Text GUI to add the dialog to
     * @param title Title of the dialog
     * @param description Description of the dialog
     * @param listBoxHeight Maximum height of the list box, scrollbars will be
     * used if there are more items
     * @param items Items in the dialog
     * @param <T> Type of items in the dialog
     * @return The selected item or {@code null} if cancelled
     */
    @SafeVarargs
    public static <T extends IItemLabel> List<T> showDialog(WindowBasedTextGUI textGUI,
            String title, String description,
            int listBoxHeight,
            T... items) {
        int width = 0;
        for (T item : items) {
            width = Math.max(width, TerminalTextUtils.getColumnWidth(item.toString()));
        }
        width += 2;
        return showDialog(textGUI, title, description, new TerminalSize(width, listBoxHeight), items);
    }

    /**
     * Shortcut for quickly creating a new dialog
     *
     * @param textGUI Text GUI to add the dialog to
     * @param title Title of the dialog
     * @param description Description of the dialog
     * @param listBoxSize Maximum size of the list box, scrollbars will be used
     * if the items cannot fit
     * @param items Items in the dialog
     * @param <T> Type of items in the dialog
     * @return The selected item or {@code null} if cancelled
     */
    @SafeVarargs
    public static <T extends IItemLabel> List<T> showDialog(WindowBasedTextGUI textGUI,
            String title, String description,
            TerminalSize listBoxSize,
            T... items) {
        final ComboBoxDialog<T> comboBoxDialog = new ComboBoxDialogBuilder<T>()
                .setTitle(title)
                .setDescription(description)
                .setListBoxSize(listBoxSize)
                .addListItems(items)
                .build();
        return comboBoxDialog.showDialog(textGUI);
    }

}
