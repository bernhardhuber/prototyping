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
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.huberb.prototyping.lanterna.examples.dialogs.CheckListDialog;

/**
 *
 * @author berni3
 */
public class CheckListDialogExample {

    public static void main(String[] args) throws IOException {

        final LaternaDialogTemplate laternaDialogTemplate = new LaternaDialogTemplate() {
            @Override
            protected void setupComponents() {
                new CheckListDialogExample().showDialog(this.textGUI);
            }
        };

        laternaDialogTemplate.launch();
    }

    void showDialog(MultiWindowTextGUI textGUI) {
        final List<ItemLabel<String>> itemLabelList = Arrays.asList(
                new ItemLabel<>("cb1Label", "cb1Value"),
                new ItemLabel<>("cb2Label", "cb2Value"),
                new ItemLabel<>("cb3Label", "cb3Value"),
                new ItemLabel<>("cb4Label", "cb4Value"),
                new ItemLabel<>("cb5Label", "cb5Value")
        );
        final ItemLabel[] items = itemLabelList.toArray(ItemLabel[]::new);
        final List<ItemLabel<String>> result = CheckListDialog.showDialog(
                textGUI,
                this.getClass().getName(),
                "description",
                items
        );
        System.out.printf("%s result %s%n", CheckListDialogExample.class.getName(),
                result.stream()
                        .map((il) -> il.item)
                        .collect(Collectors.toList())
        );

    }
}
