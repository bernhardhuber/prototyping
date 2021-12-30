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

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.huberb.prototyping.lanterna.examples.dialogs.RadioListDialog;

/**
 *
 * @author berni3
 */
public class RadioListDialogExample {

    public static void main(String[] args) throws IOException {

        final LaternaDialogTemplate laternaDialogTemplate = new LaternaDialogTemplate() {
            @Override
            protected void setupComponents() {
                final List<ItemLabel<String>> itemLabelList = Arrays.asList(
                        new ItemLabel<>("cb1Label", "cb1Value"),
                        new ItemLabel<>("cb2Label", "cb2Value"),
                        new ItemLabel<>("cb3Label", "cb3Value")
                );
                final ItemLabel[] items = itemLabelList.toArray(ItemLabel[]::new);
                final ItemLabel<String> result = RadioListDialog.showDialog(textGUI, "title", "description", items);
                System.out.printf("%s result %s%n", RadioListDialogExample.class.getName(), result.getItem());
            }
        };

        laternaDialogTemplate.launch();
    }
}
