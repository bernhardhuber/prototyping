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
package org.huberb.prototyping.lanterna.examples.dialogs;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.dialogs.DirectoryDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.FileDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.ListSelectDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder2;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;
import java.io.File;
import java.util.List;
import java.util.Optional;
import org.huberb.prototyping.lanterna.examples.dialogs.ItemLabelWrappings.ItemLabel;

/**
 * A builder for dialogues.
 *
 * @author berni3
 */
public class DialogsBuilders {

    private String title;

    public DialogsBuilders title(String title) {
        this.title = title;
        return this;
    }

    public MessageDialogBuilder2 msgbox(String description) {
        return msgbox(this.title, description);

    }

    public MessageDialogBuilder2 msgbox(String description, List<MessageDialogButton> buttonLists) {
        final MessageDialogBuilder2 b = new MessageDialogBuilder2()
                .setTitle(this.title)
                .setDescription(description)
                .addButtons(buttonLists);
        return b;

    }

    public MessageDialogBuilder2 msgbox(String title, String description) {
        final MessageDialogBuilder2 b = new MessageDialogBuilder2()
                .setTitle(title)
                .setDescription(description)
                .addButton(MessageDialogButton.OK);
        return b;
    }

    public MessageDialogBuilder2 yesno(String description) {
        return yesno(this.title, description);
    }

    public MessageDialogBuilder2 yesno(String title, String description) {
        final MessageDialogBuilder2 b = new MessageDialogBuilder2()
                .setTitle(title)
                .setText(description)
                .addButton(MessageDialogButton.Yes)
                .addButton(MessageDialogButton.No);
        return b;
    }

    public TextInputDialogBuilder inputbox(String description, String init, int h, int w) {
        return inputbox(this.title, description, init, h, w);
    }

    public TextInputDialogBuilder inputbox(String title, String description, String init, int h, int w) {
        final String initNormalized = Optional.ofNullable(init)
                .map((s) -> s.trim())
                .orElse("");
        final int wNormalized = Math.min(w, 1);
        final int hNormalized = Math.min(h, 1);
        final TextInputDialogBuilder b = new TextInputDialogBuilder()
                .setTitle(title)
                .setDescription(description)
                .setInitialContent(init)
                .setPasswordInput(false)
                .setTextBoxSize(new TerminalSize(w, h));
        return b;
    }

    public TextInputDialogBuilder passwordbox(String title, String description, String init, int h, int w) {
        final TextInputDialogBuilder b = new TextInputDialogBuilder()
                .setTitle(title)
                .setDescription(description)
                .setInitialContent(init)
                .setPasswordInput(true)
                .setTextBoxSize(new TerminalSize(w, h));
        return b;
    }

    public TextBoxDialogBuilder textbox(String title, String description, String content, int h, int w) {
        final TextBoxDialogBuilder b = new TextBoxDialogBuilder()
                .setTitle(title)
                .setDescription(description)
                .setContent(content)
                .setListBoxSize(new TerminalSize(w, h));
        return b;
    }

    public ListSelectDialogBuilder<ItemLabel> menu(String description, int h, int w, List<ItemLabel> l) {
        return menu(this.title, description, h, w, l);
    }

    public ListSelectDialogBuilder<ItemLabel> menu(String title, String description, int h, int w, List<ItemLabel> l) {
        ListSelectDialogBuilder<ItemLabel> b = new ListSelectDialogBuilder<ItemLabel>()
                .setTitle(title)
                .setDescription(description)
                .addListItems(l.toArray(ItemLabel[]::new))
                .setListBoxSize(new TerminalSize(w, h));
        return b;
    }

    public MenuListDialogBuilder<ItemLabel> menu2(String description, int h, int w, List<ItemLabel> l) {
        return menu2(this.title, description, h, w, l);
    }

    public MenuListDialogBuilder<ItemLabel> menu2(String title, String description, int h, int w, List<ItemLabel> l) {
        MenuListDialogBuilder<ItemLabel> b = new MenuListDialogBuilder<ItemLabel>()
                .setTitle(title)
                .setDescription(description)
                .addListItems(l.toArray(ItemLabel[]::new))
                .setListBoxSize(new TerminalSize(w, h));
        return b;
    }

    public CheckListDialogBuilder<ItemLabel> checklist(String description, int h, int w, List<ItemLabel> l) {
        return checklist(this.title, description, h, w, l);
    }

    public CheckListDialogBuilder<ItemLabel> checklist(String title, String description, int h, int w, List<ItemLabel> l) {
        final CheckListDialogBuilder<ItemLabel> b = new CheckListDialogBuilder<ItemLabel>()
                .setTitle(title)
                .setDescription(description)
                .addListItems(l)
                .setListBoxSize(new TerminalSize(w, h));
        return b;
    }

    public RadioListDialogBuilder<ItemLabel> radiolist(String description, int h, int w, List<ItemLabel> l) {
        return radiolist(this.title, description, h, w, l);
    }

    public RadioListDialogBuilder<ItemLabel> radiolist(String title, String description, int h, int w, List<ItemLabel> l) {
        final RadioListDialogBuilder<ItemLabel> b = new RadioListDialogBuilder<ItemLabel>()
                .setTitle(title)
                .setDescription(description)
                .addListItems(l)
                .setListBoxSize(new TerminalSize(w, h));
        return b;
    }

    public DirectoryDialogBuilder dselect(String description, int h, int w, File selectedDir) {
        return dselect(this.title, description, h, w, selectedDir);
    }

    public DirectoryDialogBuilder dselect(String title, String description, int h, int w, File selectedDir) {
        final DirectoryDialogBuilder b = new DirectoryDialogBuilder()
                .setTitle(title)
                .setDescription(description)
                .setSelectedDirectory(selectedDir)
                .setSuggestedSize(new TerminalSize(w, h));
        return b;
    }

    public FileDialogBuilder fselect(String description, int h, int w, File selectedFile) {
        return fselect(this.title, description, h, w, selectedFile);
    }

    public FileDialogBuilder fselect(String title, String description, int h, int w, File selectedFile) {
        final FileDialogBuilder b = new FileDialogBuilder()
                .setTitle(title)
                .setDescription(description)
                .setSelectedFile(selectedFile)
                .setSuggestedSize(new TerminalSize(w, h));
        return b;
    }

    void gauge() {
        //TODO
    }
}
