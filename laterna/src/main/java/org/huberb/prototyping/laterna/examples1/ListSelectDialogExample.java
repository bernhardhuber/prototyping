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
package org.huberb.prototyping.laterna.examples1;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.dialogs.ListSelectDialog;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import org.huberb.prototyping.laterna.examples1.ActionListDialogExample.LaternaDialogTemplate;

/**
 *
 * @author berni3
 */
public class ListSelectDialogExample {

    public static void main(String[] args) throws IOException {

        final Supplier<List<String>> itemSupp = () -> {
            return Arrays.asList("item1", "item2", "item3");
        };

        final LaternaDialogTemplate laternaDialogTemplate = new LaternaDialogTemplate() {
            @Override
            protected void setupComponents() {
                final List<String> itemList = itemSupp.get();
                final String[] items = itemList.toArray(new String[itemList.size()]);

                final TerminalSize dialogSize = new TerminalSize(40, 15);
                final String result = ListSelectDialog.showDialog(textGUI, "title", "description", dialogSize, items);

                System.out.printf("%s result %s%n", ListSelectDialogExample.class.getName(), result);
            }
        };

        laternaDialogTemplate.launch();
    }
}
