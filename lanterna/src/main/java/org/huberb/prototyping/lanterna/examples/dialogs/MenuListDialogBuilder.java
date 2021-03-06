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
import com.googlecode.lanterna.gui2.dialogs.AbstractDialogBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author pi
 * @param <T>
 */
public class MenuListDialogBuilder<T>
        extends AbstractDialogBuilder<MenuListDialogBuilder<T>, MenuListDialog<T>> {

    private final List<T> content;
    private TerminalSize listBoxSize;

    /**
     * Default constructor
     */
    public MenuListDialogBuilder() {
        super("MenuListDialog");
        this.listBoxSize = null;
        this.content = new ArrayList<>();
    }

    @Override
    protected MenuListDialogBuilder<T> self() {
        return this;
    }

    @Override
    protected MenuListDialog<T> buildDialog() {
        return new MenuListDialog<>(
                title,
                description,
                listBoxSize,
                content);
    }

    /**
     * Sets the size of the list box in the dialog, scrollbars will be used if
     * there is not enough space to draw all items. If set to {@code null}, the
     * dialog will ask for enough space to be able to draw all items.
     *
     * @param listBoxSize Size of the list box in the dialog
     * @return Itself
     */
    public MenuListDialogBuilder<T> setListBoxSize(TerminalSize listBoxSize) {
        this.listBoxSize = listBoxSize;
        return this;
    }

    /**
     * Size of the list box in the dialog or {@code null} if the dialog will ask
     * for enough space to draw all items
     *
     * @return Size of the list box in the dialog or {@code null} if the dialog
     * will ask for enough space to draw all items
     */
    public TerminalSize getListBoxSize() {
        return listBoxSize;
    }

    /**
     * Adds an item to the list box at the end
     *
     * @param item Item to add to the list box
     * @return Itself
     */
    public MenuListDialogBuilder<T> addListItem(T item) {
        this.content.add(item);
        return this;
    }

    /**
     * Adds a list of items to the list box at the end, in the order they are
     * passed in
     *
     * @param items Items to add to the list box
     * @return Itself
     */
    @SafeVarargs
    public final MenuListDialogBuilder<T> addListItems(T... items) {
        this.content.addAll(Arrays.asList(items));
        return this;
    }

    /**
     * Returns a copy of the list of items in the list box
     *
     * @return Copy of the list of items in the list box
     */
    public List<T> getListItems() {
        return new ArrayList<>(content);
    }

}
