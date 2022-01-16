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

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.dialogs.ListSelectDialog;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import org.huberb.prototyping.lanterna.LanternaDialogTemplate;
import org.huberb.prototyping.lanterna.LanternaLauncher;

/**
 *
 * @author berni3
 */
public class ListSelectDialogExample extends LanternaDialogTemplate {

    public static void main(String[] args) throws Exception {
        LanternaLauncher.launchWithClass(ListSelectDialogExample.class, args);

    }

    public ListSelectDialogExample() {
        super(ListSelectDialogExample.class.getName());
    }

    @Override
    protected void setupComponents() {
        showDialog(this.getTextGUI());
    }

    void showDialog(MultiWindowTextGUI textGUI) {
        final Supplier<List<String>> itemSupp = () -> {
            return Arrays.asList(
                    "item1",
                    "item2",
                    "item3",
                    "item4",
                    "item5"
            );
        };

        final List<String> itemList = itemSupp.get();
        final String[] items = itemList.toArray(new String[itemList.size()]);

        final TerminalSize dialogSize = new TerminalSize(40, 15);
        final String result = ListSelectDialog.showDialog(textGUI,
                this.getClass().getName(),
                "description",
                dialogSize,
                items);

        System.out.printf("%s result %s%n", ListSelectDialogExample.class.getName(), result);
    }
}
