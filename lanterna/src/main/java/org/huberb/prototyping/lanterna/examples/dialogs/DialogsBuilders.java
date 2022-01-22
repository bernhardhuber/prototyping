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
import org.huberb.prototyping.lanterna.examples.dialogs.ItemLabelWrappings.ItemLabel;

/**
 *
 * @author berni3
 */
public class DialogsBuilders {

    public MessageDialogBuilder2 msgbox(String title, String description) {
        final MessageDialogBuilder2 b = new MessageDialogBuilder2()
                .setTitle(title)
                .setDescription(description)
                .addButton(MessageDialogButton.OK);
        return b;
    }

    public MessageDialogBuilder2 yesno(String title, String description) {
        final MessageDialogBuilder2 b = new MessageDialogBuilder2()
                .setTitle(title)
                .setText(description)
                .addButton(MessageDialogButton.Yes)
                .addButton(MessageDialogButton.No);
        return b;
    }

    public TextInputDialogBuilder inputbox(String title, String description, String init, int h, int w) {
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

    public ListSelectDialogBuilder<ItemLabel> menu(String title, String description, int h, int w, List<ItemLabel> l) {
        ListSelectDialogBuilder<ItemLabel> b = new ListSelectDialogBuilder<ItemLabel>()
                .setTitle(title)
                .setDescription(description)
                .addListItems(l.toArray(ItemLabel[]::new))
                .setListBoxSize(new TerminalSize(w, h));
        return b;
    }

    public CheckListDialogBuilder<ItemLabel> checklist(String title, String description, int h, int w, List<ItemLabel> l) {
        final CheckListDialogBuilder<ItemLabel> b = new CheckListDialogBuilder<ItemLabel>()
                .setTitle(title)
                .setDescription(description)
                .addListItems(l)
                .setListBoxSize(new TerminalSize(w, h));
        return b;
    }

    public RadioListDialogBuilder<ItemLabel> radiolist(String title, String description, int h, int w, List<ItemLabel> l) {
        final RadioListDialogBuilder<ItemLabel> b = new RadioListDialogBuilder<ItemLabel>()
                .setTitle(title)
                .setDescription(description)
                .addListItems(l)
                .setListBoxSize(new TerminalSize(w, h));
        return b;
    }

    public DirectoryDialogBuilder dselect(String title, String description, int h, int w, File selectedDir) {
        final DirectoryDialogBuilder b = new DirectoryDialogBuilder()
                .setTitle(title)
                .setDescription(description)
                .setSelectedDirectory(selectedDir)
                .setSuggestedSize(new TerminalSize(w, h));
        return b;
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
