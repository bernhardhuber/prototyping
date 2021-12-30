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
package org.huberb.prototyping.laterna.examples.dialogs;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TerminalTextUtils;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.CheckBoxList;
import com.googlecode.lanterna.gui2.CheckBoxList.Listener;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LocalizedString;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author pi
 * @param <T>
 */
public class CheckListDialog<T> extends DialogWindow {

    private final Set<T> resultSelected;
    private T result;

    public CheckListDialog(
            String title,
            String description,
            TerminalSize listBoxPreferredSize,
            List<T> content) {

        super(title);
        this.result = null;
        this.resultSelected = new HashSet<>();
        if (content.isEmpty()) {
            throw new IllegalStateException("CheckListDialog needs at least one item");
        }

        final CheckBoxList<T> checkBoxList = new CheckBoxList<>();
        for (final T item : content) {
            checkBoxList.addItem(item);
        }
        checkBoxList.addListener(new Listener() {
            @Override
            public void onStatusChanged(int itemIndex, boolean checked) {
                if (checked) {
                    resultSelected.add(content.get(itemIndex));
                } else {
                    resultSelected.remove(content.get(itemIndex));
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
        checkBoxList.setLayoutData(
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
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER, true, false))
        );
        buttonPanel.addComponent(new Button(LocalizedString.Cancel.toString(), this::onCancel));
        buttonPanel.addTo(mainPanel);

        setComponent(mainPanel);
    }

    private void onOK() {
        close();
    }

    private void onCancel() {
        this.result = null;
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
        result = null;
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
    public static <T> List<T> showDialog(WindowBasedTextGUI textGUI,
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
    public static <T> List<T> showDialog(WindowBasedTextGUI textGUI,
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
    public static <T> List<T> showDialog(WindowBasedTextGUI textGUI,
            String title, String description,
            TerminalSize listBoxSize,
            T... items) {
        final CheckListDialog<T> checkListDialog = new CheckListDialogBuilder<T>()
                .setTitle(title)
                .setDescription(description)
                .setListBoxSize(listBoxSize)
                .addListItems(items)
                .build();
        return checkListDialog.showDialog(textGUI);
    }

}
