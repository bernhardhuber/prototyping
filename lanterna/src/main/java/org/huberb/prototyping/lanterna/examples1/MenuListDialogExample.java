/*
 * Copyright 2021 berni3.
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
package org.huberb.prototyping.lanterna.examples1;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import java.util.Arrays;
import java.util.List;
import org.huberb.prototyping.lanterna.ItemLabel;
import org.huberb.prototyping.lanterna.LanternaLauncher;
import org.huberb.prototyping.lanterna.LanternaDialogTemplate;
import org.huberb.prototyping.lanterna.examples.dialogs.MenuListDialog;

/**
 *
 * @author berni3
 */
public class MenuListDialogExample extends LanternaDialogTemplate {

    public static void main(String[] args) throws Exception {
        LanternaLauncher.launchWithClass(MenuListDialogExample.class, args);

    }

    @Override
    protected void setupComponents() {
        showDialog(this.getTextGUI());
    }

    void showDialog(MultiWindowTextGUI textGUI) {
        final List<ItemLabel<String>> itemLabelList = Arrays.asList(
                new ItemLabel<>("mn1Label", "mn1Value"),
                new ItemLabel<>("mn2Label", "mn2Value"),
                new ItemLabel<>("mn3Label", "mn3Value"),
                new ItemLabel<>("mn4Label", "mn4Value"),
                new ItemLabel<>("mn5Label", "mn5Value")
        );
        final ItemLabel[] items = itemLabelList.toArray(ItemLabel[]::new);
        final ItemLabel<String> result = MenuListDialog.showDialog(
                textGUI,
                this.getClass().getName(),
                "description",
                items);
        System.out.printf("%s result %s%n", MenuListDialogExample.class.getName(), result.getItem());
    }
}
