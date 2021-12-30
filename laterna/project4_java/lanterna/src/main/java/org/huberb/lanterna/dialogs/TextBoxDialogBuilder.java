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
package org.huberb.lanterna.dialogs;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.dialogs.AbstractDialogBuilder;

/**
 *
 * @author pi
 */
public class TextBoxDialogBuilder extends AbstractDialogBuilder<TextBoxDialogBuilder, TextBoxDialog> {

    private String content;
    private TerminalSize listBoxSize;

    public TextBoxDialogBuilder() {
        super("TextBoxDialog");
        this.listBoxSize = null;
        this.content = "";
        this.description = "";
    }

    @Override
    protected TextBoxDialogBuilder self() {
        return this;
    }

    @Override
    protected TextBoxDialog buildDialog() {
        return new TextBoxDialog(
                title,
                description,
                this.listBoxSize,
                this.content);
    }

    /**
     * Sets the size of the list box in the dialog, scrollbars will be used if
     * there is not enough space to draw all items. If set to {@code null}, the
     * dialog will ask for enough space to be able to draw all items.
     *
     * @param listBoxSize Size of the list box in the dialog
     * @return Itself
     */
    public TextBoxDialogBuilder setListBoxSize(TerminalSize listBoxSize) {
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
     * @param content
     * @return Itself
     */
    public TextBoxDialogBuilder setContent(String content) {
        this.content = content;
        return this;
    }

}
