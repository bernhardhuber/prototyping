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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.huberb.prototyping.lanterna.AbstractLanternaApplicationTemplate;
import org.huberb.prototyping.lanterna.AppContext;
import org.huberb.prototyping.lanterna.examples.dialogs.CheckListDialog;
import org.huberb.prototyping.lanterna.examples.dialogs.DialogsBuilders;
import org.huberb.prototyping.lanterna.examples.dialogs.ItemLabelWrappings.ItemLabel;
import org.huberb.prototyping.lanterna.examples.dialogs.MenuListDialog;
import org.huberb.prototyping.lanterna.examples.dialogs.RadioListDialog;

/**
 *
 * @author berni3
 */
public class DialogsBuildersMain extends AbstractLanternaApplicationTemplate implements Callable<Integer> {

    private final AppContext appContext;

    public static void main(String[] args) throws Exception {
        final DialogsBuildersMain dialogsBuilderMain = new DialogsBuildersMain();
        int rc = dialogsBuilderMain.call();
        System.exit(rc);
    }

    public DialogsBuildersMain() {
        this("DialogsBuildersMain");
    }

    public DialogsBuildersMain(String name) {
        super(name);
        appContext = new AppContext(name);
    }

    //---
    @Override
    public Integer call() throws Exception {
        launch();
        return 0;
    }

    @Override
    protected void setupComponents() {
        final MultiWindowTextGUI textGUI = getTextGUI();
        showDialog(textGUI);
    }

    //---
    void showDialog(MultiWindowTextGUI textGUI) {
        this.appContext.storeResult("showMsgbox.result", showMsgbox(textGUI));

        this.appContext.storeResult("showYesno.result", showYesno(textGUI));
        this.appContext.storeResult("showInputbox.result", showInputbox(textGUI).orElse(""));
        this.appContext.storeResult("showPasswordbox.result", showPasswordbox(textGUI).orElse(""));
        this.appContext.storeResult("showMenu.result", showMenu(textGUI).orElse(ItemLabel.empty()));
        this.appContext.storeResult("showMenu2.result", showMenu2(textGUI).orElse(ItemLabel.empty()));
        this.appContext.storeResult("showCheckList.result", showChecklist(textGUI).orElse(Collections.emptyList()));
        this.appContext.storeResult("showRadioList.result", showRadiolist(textGUI).orElse(ItemLabel.empty()));
        this.appContext.storeResult("showFselect.result", showFselect(textGUI).orElse(null));
        this.appContext.storeResult("showDselect.result", showDselect(textGUI).orElse(null));
        showMsgbox(textGUI);
    }

    final Supplier<String> descriptionText = () -> String.format("description%n%s", formatApplicationContext());

    //---
    MessageDialogButton showMsgbox(MultiWindowTextGUI textGUI) {
        final DialogsBuilders db = new DialogsBuilders();
        final MessageDialog2 md = db.msgbox(
                "showMsgbox",
                descriptionText.get())
                .build();
        final MessageDialogButton result = md.showDialog(textGUI);
        //this.appContext.storeResult("showMsgbox.result", result);
        return result;
    }

    MessageDialogButton showYesno(MultiWindowTextGUI textGUI) {
        final DialogsBuilders db = new DialogsBuilders();
        final MessageDialog2 md = db.yesno(
                "showYesno",
                descriptionText.get())
                .build();
        final MessageDialogButton result = md.showDialog(textGUI);
        //this.appContext.storeResult("showYesno.result", result);
        return result;
    }

    Optional<String> showInputbox(MultiWindowTextGUI textGUI) {
        final DialogsBuilders db = new DialogsBuilders();
        final TextInputDialog md = db.inputbox(
                "showInputbox",
                descriptionText.get(),
                "init",
                1, 40)
                .build();
        final String result = md.showDialog(textGUI);
        //this.appContext.storeResult("showInputbox.result", result);
        return Optional.ofNullable(result);
    }

    Optional<String> showPasswordbox(MultiWindowTextGUI textGUI) {
        final DialogsBuilders db = new DialogsBuilders();
        final TextInputDialog md = db.passwordbox(
                "showPasswordbox",
                descriptionText.get(),
                "init",
                1, 40)
                .build();
        final String result = md.showDialog(textGUI);
        //this.appContext.storeResult("showPasswordbox.result", result);
        return Optional.ofNullable(result);
    }

    Optional<ItemLabel> showMenu(MultiWindowTextGUI textGUI) {
        final List<ItemLabel> itemLabelList = Arrays.asList(
                new ItemLabel("label Menu1 1", "itemMenu1"),
                new ItemLabel("label Menu1 2", "itemMenu2"),
                new ItemLabel("label Menu1 3", "itemMenu3")
        );
        final DialogsBuilders db = new DialogsBuilders();
        final ListSelectDialog<ItemLabel> listSelectDialogItemLabel = db.menu(
                "showMenu",
                descriptionText.get(),
                15, 40,
                itemLabelList)
                .build();
        final ItemLabel result = listSelectDialogItemLabel.showDialog(textGUI);
        //this.appContext.storeResult("showMenu.result", result);
        return Optional.ofNullable(result);
    }

    Optional<ItemLabel> showMenu2(MultiWindowTextGUI textGUI) {
        final List<ItemLabel> itemLabelList = Arrays.asList(
                new ItemLabel("label - Menu1", "itemMenu1"),
                new ItemLabel("label - Menu2", "itemMenu2"),
                new ItemLabel("label - Menu3", "itemMenu3")
        );
        final DialogsBuilders db = new DialogsBuilders();
        final MenuListDialog<ItemLabel> listSelectDialogItemLabel = db.menu2(
                "showMenu",
                descriptionText.get(),
                15, 40,
                itemLabelList)
                .build();
        final ItemLabel result = listSelectDialogItemLabel.showDialog(textGUI);
        //this.appContext.storeResult("showMenu.result", result);
        return Optional.ofNullable(result);
    }

    Optional<List<ItemLabel>> showChecklist(MultiWindowTextGUI textGUI) {
        final List<ItemLabel> itemLabelList = Arrays.asList(
                new ItemLabel("label1Checkbox", "item1Checkbox"),
                new ItemLabel("label2Checkbox", "item2Checkbox", true),
                new ItemLabel("label3Checkbox", "item3Checkbox")
        );
        final DialogsBuilders db = new DialogsBuilders();
        final CheckListDialog<ItemLabel> cld = db.checklist(
                "showCheckList",
                descriptionText.get(),
                15, 40,
                itemLabelList)
                .build();
        final List<ItemLabel> result = cld.showDialog(textGUI);
        //this.appContext.storeResult("showCheckList.result", result);
        return Optional.ofNullable(result);
    }

    Optional<ItemLabel> showRadiolist(MultiWindowTextGUI textGUI) {
        final List<ItemLabel> itemLabelList = Arrays.asList(
                new ItemLabel("label1Radiobox", "item1Radiobox", false),
                new ItemLabel("label2Radiobox", "item2Radiobox", true),
                new ItemLabel("label3Radiobox", "item3Radiobox")
        );
        final DialogsBuilders db = new DialogsBuilders();
        final RadioListDialog<ItemLabel> rld = db.radiolist(
                "showRadioList",
                descriptionText.get(), 15, 40,
                itemLabelList)
                .build();
        final ItemLabel result = rld.showDialog(textGUI);
        //this.appContext.storeResult("showRadioList.result", result);
        return Optional.ofNullable(result);
    }

    Optional<File> showFselect(MultiWindowTextGUI textGUI) {
        final DialogsBuilders db = new DialogsBuilders();
        File selectedFile = null;
        FileDialog fd = db.fselect(
                "showFselect",
                descriptionText.get(),
                15, 40,
                selectedFile)
                .build();
        File result = fd.showDialog(textGUI);
        //this.appContext.storeResult("showFselect.result", result);
        return Optional.ofNullable(result);
    }

    Optional<File> showDselect(MultiWindowTextGUI textGUI) {
        final DialogsBuilders db = new DialogsBuilders();
        File selectedFile = null;
        DirectoryDialog fd = db.dselect(
                "showDselect",
                descriptionText.get(),
                15, 40,
                selectedFile)
                .build();
        File result = fd.showDialog(textGUI);
        //this.appContext.storeResult("showDselect.result", result);
        return Optional.ofNullable(result);
    }

    private String formatApplicationContext() {
        // format list of map to String
        final String mFormatted = this.appContext.getM().entrySet().stream()
                .sorted(Comparator.comparing((e) -> e.getKey()))
                .map((e) -> String.format("%s: %s%n", e.getKey(), e.getValue()))
                .collect(Collectors.joining());
        // format appContext: name + m
        final String result = String.format("AppName: %s%n%s%n", this.appContext.getAppName(), mFormatted);
        return result;
    }

}
