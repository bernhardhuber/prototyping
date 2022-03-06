/*
 * Copyright 2022 berni3.
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
package com.googlecode.lanterna.gui2.dialogs;

import com.googlecode.lanterna.gui2.Window;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author berni3
 */
public class MessageDialogBuilder2 extends AbstractDialogBuilder<MessageDialogBuilder2, MessageDialog2> {

    private final List<MessageDialogButton> buttons;

    /**
     * Default constructor
     */
    public MessageDialogBuilder2() {
        super("MessageDialog");
        this.description = "Text";
        this.buttons = new ArrayList<>();
        this.extraWindowHints = new HashSet<>();
        this.extraWindowHints.add(Window.Hint.CENTERED);
    }

    @Override
    protected MessageDialogBuilder2 self() {
        return this;
    }

    @Override
    protected MessageDialog2 buildDialog() {
        MessageDialog2 messageDialog = new MessageDialog2(
                title,
                description,
                buttons.toArray(MessageDialogButton[]::new)
        );
        messageDialog.setHints(extraWindowHints);
        return messageDialog;
    }

    /**
     * Sets the main text of the {@code MessageDialog}
     *
     * @param text Main text of the {@code MessageDialog}
     * @return Itself
     */
    public MessageDialogBuilder2 setText(String text) {
        if (text == null) {
            text = "";
        }
        this.setDescription(text);
        return this;
    }

    public String getText() {
        return this.getDescription();
    }

    /**
     * Adds a button to the dialog
     *
     * @param button Button to add to the dialog
     * @return Itself
     */
    public MessageDialogBuilder2 addButton(MessageDialogButton button) {
        if (button != null) {
            buttons.add(button);
        }
        return this;
    }

    /**
     * Add a buttons to the dialog
     *
     * @param buttonList a list of Button to add to the dialog
     * @return Itself
     */
    public MessageDialogBuilder2 addButtons(List<MessageDialogButton> buttonList) {
        buttonList.forEach((b) -> addButton(b));
        return this;
    }

    public List<MessageDialogButton> getButtons() {
        return new ArrayList<>(this.buttons);
    }

}
