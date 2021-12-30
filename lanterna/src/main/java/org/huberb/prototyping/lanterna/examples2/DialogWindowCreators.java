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
package org.huberb.prototyping.lanterna.examples2;

import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import com.googlecode.lanterna.gui2.dialogs.DirectoryDialog;
import com.googlecode.lanterna.gui2.dialogs.DirectoryDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.FileDialog;
import com.googlecode.lanterna.gui2.dialogs.FileDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.ListSelectDialog;
import com.googlecode.lanterna.gui2.dialogs.ListSelectDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialog;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import org.huberb.prototyping.lanterna.examples.dialogs.CheckListDialog;
import org.huberb.prototyping.lanterna.examples.dialogs.CheckListDialogBuilder;
import org.huberb.prototyping.lanterna.examples.dialogs.MenuListDialog;
import org.huberb.prototyping.lanterna.examples.dialogs.MenuListDialogBuilder;
import org.huberb.prototyping.lanterna.examples.dialogs.RadioListDialog;
import org.huberb.prototyping.lanterna.examples.dialogs.RadioListDialogBuilder;
import org.huberb.prototyping.lanterna.examples.dialogs.TextBoxDialog;
import org.huberb.prototyping.lanterna.examples.dialogs.TextBoxDialogBuilder;

/**
 *
 * @author pi
 */
class DialogWindowCreators {

    enum Mode {
        directoryDialog,
        fileDialog,
        listSelectDialog,
        messageDialog,
        textInputDialog,
        //---
        checkListDialog,
        menuListDialog,
        radioListDialog,
        textBoxDialog
    }

    static class DialogWindowCreatorParameter implements Serializable {

        private static final long serialVersionUID = 20210416L;

        private final Mode mode;

        private final Supplier<String> title;
        private final Supplier<String> description;
        private final Supplier<List<String>> listStringParams;
        private final Supplier<String> message;

        public DialogWindowCreatorParameter(Mode mode,
                String title, String description,
                List<String> listStringParams,
                String message) {
            this.mode = mode;
            this.title = () -> title;
            this.description = () -> description;
            this.listStringParams = () -> listStringParams;
            this.message = () -> message;
        }

        public DialogWindowCreatorParameter(Mode mode,
                Supplier<String> title,
                Supplier<String> description,
                Supplier<List<String>> listStringParams,
                Supplier<String> message) {
            this.mode = mode;
            this.title = title;
            this.description = description;
            this.listStringParams = listStringParams;
            this.message = message;
        }
    }

    static class DialogWindowCreatorParameterBuilder {

        Mode mode;
        Supplier<String> titleSupplier;
        Supplier<String> descriptionSupplier;
        Supplier<List<String>> listStringParamsSupplier;
        Supplier<String> messageSupplier;

        DialogWindowCreatorParameterBuilder dialogWindowCreatorParameter(DialogWindowCreatorParameter dwcp) {
            this.mode = dwcp.mode;
            this.titleSupplier = dwcp.title;
            this.descriptionSupplier = dwcp.description;
            this.listStringParamsSupplier = dwcp.listStringParams;
            this.messageSupplier = dwcp.message;
            return this;
        }

        DialogWindowCreatorParameterBuilder mode(Mode mode) {
            this.mode = mode;
            return this;
        }

        DialogWindowCreatorParameterBuilder title(String title) {
            this.titleSupplier = () -> title;
            return this;
        }

        DialogWindowCreatorParameterBuilder description(String description) {
            this.descriptionSupplier = () -> description;
            return this;
        }

        DialogWindowCreatorParameterBuilder listStringParams(List<String> listStringParams) {
            this.listStringParamsSupplier = () -> listStringParams;
            return this;
        }

        DialogWindowCreatorParameterBuilder message(String message) {
            this.messageSupplier = () -> message;
            return this;
        }

        DialogWindowCreatorParameter build() {
            return new DialogWindowCreatorParameter(this.mode,
                    this.titleSupplier, this.descriptionSupplier,
                    this.listStringParamsSupplier, this.messageSupplier
            );
        }
    }

    static class DialogWindowFactory {

        /**
         * Create a {@link DialogWindow}.
         *
         * @param dialogWindowCreatorParameter
         * @return
         */
        DialogWindow createDialogWindow(DialogWindowCreatorParameter dialogWindowCreatorParameter) {
            final Mode mode = dialogWindowCreatorParameter.mode;
            final String title = dialogWindowCreatorParameter.title.get();
            final String description = dialogWindowCreatorParameter.description.get();
            final String message = dialogWindowCreatorParameter.message.get();
            final List<String> listStringParams = dialogWindowCreatorParameter.listStringParams.get();
            final DialogWindow dialogWindow;
            //---
            if (mode == Mode.directoryDialog) {
                final DirectoryDialog directoryDialog = new DirectoryDialogBuilder()
                        .setTitle(title).setDescription(description).build();
                dialogWindow = directoryDialog;
            } else if (mode == Mode.fileDialog) {
                final FileDialog fileDialog = new FileDialogBuilder()
                        .setTitle(title)
                        .setDescription(description)
                        .build();
                dialogWindow = fileDialog;
            } else if (mode == Mode.messageDialog) {
                final MessageDialog messageDialog = new MessageDialogBuilder()
                        .setTitle(title)
                        .setText(message)
                        .addButton(MessageDialogButton.OK)
                        .addButton(MessageDialogButton.Cancel)
                        .build();
                dialogWindow = messageDialog;
            } else if (mode == Mode.textInputDialog) {
                final TextInputDialog textInputDialog = new TextInputDialogBuilder()
                        .setTitle(title)
                        .setDescription(description).build();
                dialogWindow = textInputDialog;
            } else if (mode == Mode.listSelectDialog) {
                final ListSelectDialogBuilder<String> listSelectDialogBuilder = new ListSelectDialogBuilder<String>()
                        .setTitle(title)
                        .setDescription(description);
                listStringParams.forEach((String s) -> listSelectDialogBuilder.addListItem(s));
                final ListSelectDialog<String> listSelectDialog = listSelectDialogBuilder.build();
                dialogWindow = listSelectDialog;
            } else if (mode == Mode.checkListDialog) {
                final CheckListDialogBuilder<String> checkListDialogBuilder = new CheckListDialogBuilder<String>()
                        .setTitle(title)
                        .setDescription(description);
                listStringParams.forEach((String s) -> checkListDialogBuilder.addListItem(s));
                final CheckListDialog<String> checkListDialog = checkListDialogBuilder.build();
                dialogWindow = checkListDialog;
            } else if (mode == Mode.menuListDialog) {
                final MenuListDialogBuilder<String> menuListDialogBuilder = new MenuListDialogBuilder<String>()
                        .setTitle(title)
                        .setDescription(description);
                listStringParams.forEach((String s) -> menuListDialogBuilder.addListItem(s));
                final MenuListDialog<String> menuListDialog = menuListDialogBuilder.build();
                dialogWindow = menuListDialog;
            } else if (mode == Mode.radioListDialog) {
                final RadioListDialogBuilder<String> radioListDialogBuilder = new RadioListDialogBuilder<String>()
                        .setTitle(title)
                        .setDescription(description);
                listStringParams.forEach((String s) -> radioListDialogBuilder.addListItem(s));
                final RadioListDialog<String> radioListDialog = radioListDialogBuilder.build();
                dialogWindow = radioListDialog;
            } else if (mode == Mode.textBoxDialog) {
                final TextBoxDialogBuilder textBoxDialogBuilder = new TextBoxDialogBuilder()
                        .setTitle(title)
                        .setDescription(description)
                        .setContent(message);
                final TextBoxDialog textBoxDialog = textBoxDialogBuilder.build();
                dialogWindow = textBoxDialog;
            } else {
                dialogWindow = null;
            }
            return dialogWindow;
        }
    }

    static class DialogWindowBuilder {

        private Mode mode;

        private Supplier<String> titleSupplier;
        private Supplier<String> descriptionSupplier;
        private Supplier<List<String>> listStringParamsSupplier;
        private Supplier<String> messageSupplier;

        public DialogWindowBuilder() {
            this.mode = null;
            titleSupplier = () -> "";
            descriptionSupplier = () -> "";
            listStringParamsSupplier = () -> Collections.emptyList();
            messageSupplier = () -> "";
        }

        DialogWindowBuilder dialogWindowCreatorParameter(DialogWindowCreatorParameter dwcp) {
            this.mode = dwcp.mode;
            this.titleSupplier = dwcp.title;
            this.descriptionSupplier = dwcp.description;
            this.listStringParamsSupplier = dwcp.listStringParams;
            this.messageSupplier = dwcp.message;
            return this;
        }

        DialogWindowBuilder mode(Mode mode) {
            this.mode = mode;
            return this;
        }

        DialogWindowBuilder title(String title) {
            this.titleSupplier = () -> title;
            return this;
        }

        DialogWindowBuilder description(String description) {
            this.descriptionSupplier = () -> description;
            return this;
        }

        DialogWindowBuilder listStringParams(List<String> listStringParams) {
            this.listStringParamsSupplier = () -> listStringParams;
            return this;
        }

        DialogWindowBuilder message(String message) {
            this.messageSupplier = () -> message;
            return this;
        }

        DialogWindow build() {
            final DialogWindowCreatorParameter dialogWindowCreatorParameter
                    = new DialogWindowCreatorParameterBuilder()
                            .mode(this.mode)
                            .title(this.titleSupplier.get())
                            .description(this.descriptionSupplier.get())
                            .message(this.messageSupplier.get())
                            .listStringParams(this.listStringParamsSupplier.get())
                            .build();
            final DialogWindowFactory dialogWindowFactory = new DialogWindowFactory();
            final DialogWindow dialogWindow = dialogWindowFactory.createDialogWindow(dialogWindowCreatorParameter);
            return dialogWindow;
        }

        DialogWindow _build() {
            final Mode mode = this.mode;
            final String title = this.titleSupplier.get();
            final String description = this.descriptionSupplier.get();
            final String message = this.messageSupplier.get();
            final List<String> listStringParams = this.listStringParamsSupplier.get();
            final DialogWindow dialogWindow;
            //---
            if (mode == Mode.directoryDialog) {
                final DirectoryDialog directoryDialog = new DirectoryDialogBuilder()
                        .setTitle(title).setDescription(description).build();
                dialogWindow = directoryDialog;
            } else if (mode == Mode.fileDialog) {
                final FileDialog fileDialog = new FileDialogBuilder()
                        .setTitle(title)
                        .setDescription(description)
                        .build();
                dialogWindow = fileDialog;
            } else if (mode == Mode.messageDialog) {
                final MessageDialog messageDialog = new MessageDialogBuilder()
                        .setTitle(title)
                        .setText(message)
                        .addButton(MessageDialogButton.OK)
                        //.addButton(MessageDialogButton.Cancel)                        
                        .build();
                dialogWindow = messageDialog;
            } else if (mode == Mode.textInputDialog) {
                final TextInputDialog textInputDialog = new TextInputDialogBuilder()
                        .setTitle(title)
                        .setDescription(description).build();
                dialogWindow = textInputDialog;
            } else if (mode == Mode.listSelectDialog) {
                final ListSelectDialogBuilder<String> listSelectDialogBuilder = new ListSelectDialogBuilder<String>()
                        .setTitle(title)
                        .setDescription(description);
                listStringParams.forEach((String s) -> listSelectDialogBuilder.addListItem(s));
                final ListSelectDialog<String> listSelectDialog = listSelectDialogBuilder.build();
                dialogWindow = listSelectDialog;
            } else if (mode == Mode.checkListDialog) {
                final CheckListDialogBuilder<String> checkListDialogBuilder = new CheckListDialogBuilder<String>()
                        .setTitle(title)
                        .setDescription(description);
                listStringParams.forEach((String s) -> checkListDialogBuilder.addListItem(s));
                final CheckListDialog<String> checkListDialog = checkListDialogBuilder.build();
                dialogWindow = checkListDialog;
            } else if (mode == Mode.menuListDialog) {
                final MenuListDialogBuilder<String> menuListDialogBuilder = new MenuListDialogBuilder<String>()
                        .setTitle(title)
                        .setDescription(description);
                listStringParams.forEach((String s) -> menuListDialogBuilder.addListItem(s));
                final MenuListDialog<String> menuListDialog = menuListDialogBuilder.build();
                dialogWindow = menuListDialog;
            } else if (mode == Mode.radioListDialog) {
                final RadioListDialogBuilder<String> radioListDialogBuilder = new RadioListDialogBuilder<String>()
                        .setTitle(title)
                        .setDescription(description);
                listStringParams.forEach((String s) -> radioListDialogBuilder.addListItem(s));
                final RadioListDialog<String> radioListDialog = radioListDialogBuilder.build();
                dialogWindow = radioListDialog;
            } else if (mode == Mode.textBoxDialog) {
                final TextBoxDialogBuilder textBoxDialogBuilder = new TextBoxDialogBuilder()
                        .setTitle(title)
                        .setDescription(description)
                        .setContent(message);
                final TextBoxDialog textBoxDialog = textBoxDialogBuilder.build();
                dialogWindow = textBoxDialog;
            } else {
                dialogWindow = null;
            }
            return dialogWindow;
        }
    }
}
