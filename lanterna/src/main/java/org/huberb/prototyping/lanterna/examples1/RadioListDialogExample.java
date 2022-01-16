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
import org.huberb.prototyping.lanterna.LanternaDialogTemplate;
import org.huberb.prototyping.lanterna.LanternaLauncher;
import org.huberb.prototyping.lanterna.examples.dialogs.ItemLabelWrappings.ItemLabel;
import org.huberb.prototyping.lanterna.examples.dialogs.RadioListDialog;

/**
 *
 * @author berni3
 */
public class RadioListDialogExample extends LanternaDialogTemplate {

    public static void main(String[] args) throws Exception {
        LanternaLauncher.launchWithClass(RadioListDialogExample.class, args);
    }

    public RadioListDialogExample() {
        super(RadioListDialogExample.class.getName());
    }

    @Override
    protected void setupComponents() {
        showDialog(this.getTextGUI());
    }

    void showDialog(MultiWindowTextGUI textGUI) {
        final List<ItemLabel> itemLabelList = Arrays.asList(
                new ItemLabel("rb1Label", "rb1Value"),
                new ItemLabel("rb2Label", "rb2Value"),
                new ItemLabel("rb3Label", "rb3Value"),
                new ItemLabel("rb4Label", "rb4Value"),
                new ItemLabel("rb5Label", "rb5Value")
        );
        final ItemLabel[] items = itemLabelList.toArray(ItemLabel[]::new);
        final ItemLabel result = RadioListDialog.showDialog(
                textGUI,
                this.getClass().getName(),
                "description",
                items
        );
        System.out.printf("%s result %s%n", RadioListDialogExample.class.getName(), result.getItem());
    }
}
