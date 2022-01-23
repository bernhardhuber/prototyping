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
package org.huberb.prototyping.lanterna.examples.apps;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.dialogs.DirectoryDialog;
import com.googlecode.lanterna.gui2.dialogs.FileDialog;
import com.googlecode.lanterna.gui2.dialogs.ListSelectDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog2;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialog;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Supplier;
import org.huberb.prototyping.lanterna.AppContext;
import org.huberb.prototyping.lanterna.LanternaDialogTemplate;
import org.huberb.prototyping.lanterna.examples.dialogs.CheckListDialog;
import org.huberb.prototyping.lanterna.examples.dialogs.DialogsBuilders;
import org.huberb.prototyping.lanterna.examples.dialogs.ItemLabelWrappings.ItemLabel;
import org.huberb.prototyping.lanterna.examples.dialogs.RadioListDialog;

/**
 *
 * @author berni3
 */
public class DialogsBuildersMain extends LanternaDialogTemplate {

    private AppContext appContext = new AppContext("dialogsbuildersmain");

    public static void main(String[] args) throws Exception {
        final DialogsBuildersMain dialogsBuilderMain = new DialogsBuildersMain();
        dialogsBuilderMain.launch();
    }

    public DialogsBuildersMain() {
        super("dialogsbuildersmain");
    }

    @Override
    protected void setupComponents() {
        final MultiWindowTextGUI textGUI = getTextGUI();
        showDialog(textGUI);
    }

    //---
    void showDialog(MultiWindowTextGUI textGUI) {
        showMsgbox(textGUI);
        showYesno(textGUI);
        showInputbox(textGUI);
        showPasswordbox(textGUI);
        showMenu(textGUI);
        showChecklist(textGUI);
        showRadiolist(textGUI);
        showDselect(textGUI);
        showFselect(textGUI);
        showMsgbox(textGUI);
    }

    Supplier<String> descriptionText = () -> String.format("description%n%s", formatApplicationContext());

    //---
    void showMsgbox(MultiWindowTextGUI textGUI) {
        {
            final DialogsBuilders db = new DialogsBuilders();
            final MessageDialog2 md = db.msgbox(
                    "showMsgbox",
                    descriptionText.get())
                    .build();
            final MessageDialogButton result = md.showDialog(textGUI);
            this.appContext.storeResult("showMsgbox.result", result);
        }
    }

    void showYesno(MultiWindowTextGUI textGUI) {
        {
            final DialogsBuilders db = new DialogsBuilders();
            final MessageDialog2 md = db.yesno(
                    "showYesno",
                    descriptionText.get())
                    .build();
            final MessageDialogButton result = md.showDialog(textGUI);
            this.appContext.storeResult("showYesno.result", result);
        }
    }

    void showInputbox(MultiWindowTextGUI textGUI) {
        {
            final DialogsBuilders db = new DialogsBuilders();
            final TextInputDialog md = db.inputbox(
                    "showInputbox",
                    descriptionText.get(),
                    "init",
                    1, 40)
                    .build();
            final String result = md.showDialog(textGUI);
            this.appContext.storeResult("showInputbox.result", result);
        }
    }

    void showPasswordbox(MultiWindowTextGUI textGUI) {
        {
            final DialogsBuilders db = new DialogsBuilders();
            final TextInputDialog md = db.passwordbox(
                    "showPasswordbox",
                    descriptionText.get(),
                    "init",
                    1, 40)
                    .build();
            final String result = md.showDialog(textGUI);
            this.appContext.storeResult("showPasswordbox.result", result);
        }
    }

    void showMenu(MultiWindowTextGUI textGUI) {
        final List<ItemLabel> itemLabelList = Arrays.asList(
                new ItemLabel("label1Menu", "item1Menu"),
                new ItemLabel("label2Menu", "item2Menu", true),
                new ItemLabel("label3Menu", "item3Menu")
        );
        {
            final DialogsBuilders db = new DialogsBuilders();
            final ListSelectDialog<ItemLabel> cld = db.menu(
                    "showMenu",
                    descriptionText.get(),
                    15, 40,
                    itemLabelList)
                    .build();
            final ItemLabel result = cld.showDialog(textGUI);
            this.appContext.storeResult("showMenu.result", result);
        }
    }

    void showChecklist(MultiWindowTextGUI textGUI) {
        final List<ItemLabel> itemLabelList = Arrays.asList(
                new ItemLabel("label1Checkbox", "item1Checkbox"),
                new ItemLabel("label2Checkbox", "item2Checkbox", true),
                new ItemLabel("label3Checkbox", "item3Checkbox")
        );
        {
            final DialogsBuilders db = new DialogsBuilders();
            final CheckListDialog<ItemLabel> cld = db.checklist(
                    "showCheckList",
                    descriptionText.get(),
                    15, 40,
                    itemLabelList)
                    .build();
            final List<ItemLabel> result = cld.showDialog(textGUI);
            this.appContext.storeResult("showCheckList.result", result);
        }
    }

    void showRadiolist(MultiWindowTextGUI textGUI) {
        final List<ItemLabel> itemLabelList = Arrays.asList(
                new ItemLabel("label1Radiobox", "item1Radiobox", false),
                new ItemLabel("label2Radiobox", "item2Radiobox", true),
                new ItemLabel("label3Radiobox", "item3Radiobox")
        );
        {
            final DialogsBuilders db = new DialogsBuilders();
            final RadioListDialog<ItemLabel> rld = db.radiolist(
                    "showRadioList",
                    descriptionText.get(), 15, 40,
                    itemLabelList)
                    .build();
            final ItemLabel result = rld.showDialog(textGUI);
            this.appContext.storeResult("showRadioList.result", result);
        }
    }

    void showFselect(MultiWindowTextGUI textGUI) {
        final DialogsBuilders db = new DialogsBuilders();
        File selectedFile = null;
        FileDialog fd = db.fselect(
                "showFselect",
                descriptionText.get(),
                15, 40,
                selectedFile)
                .build();
        File result = fd.showDialog(textGUI);
        this.appContext.storeResult("showFselect.result", result);
    }

    void showDselect(MultiWindowTextGUI textGUI) {
        final DialogsBuilders db = new DialogsBuilders();
        File selectedFile = null;
        DirectoryDialog fd = db.dselect(
                "showDselect",
                descriptionText.get(),
                15, 40,
                selectedFile)
                .build();
        File result = fd.showDialog(textGUI);
        this.appContext.storeResult("showDselect.result", result);
    }

    private String formatApplicationContext() {
        final StringBuilder sb = new StringBuilder();
        sb.append(String.format("AppName: %s%n", this.appContext.getAppName()));
        this.appContext.getM().entrySet().stream()
                .sorted((Entry<String, Object> arg0, Entry<String, Object> arg1) -> arg0.getKey().compareTo(arg1.getKey()))
                .forEach((Entry<String, Object> arg0)
                        -> sb
                        .append(String.format("%s: %s%n", arg0.getKey(), arg0.getValue()))
                );

        return sb.toString();
    }

}