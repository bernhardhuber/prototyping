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
import com.googlecode.lanterna.gui2.dialogs.ActionListDialog;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import org.huberb.prototyping.lanterna.AbstractLanternaApplicationTemplate;
import org.huberb.prototyping.lanterna.LanternaLauncher;

/**
 *
 * @author berni3
 */
public class ActionListDialogExample extends AbstractLanternaApplicationTemplate {

    public static void main(String[] args) throws Exception {
        LanternaLauncher.launchWithClass(ActionListDialogExample.class, args);
    }

    public ActionListDialogExample() {
        super(ActionListDialogExample.class.getName());
    }

    @Override
    protected void setupComponents() {
        showDialog(this.getTextGUI());
    }

    void showDialog(MultiWindowTextGUI textGUI) {
        final Supplier<  List<Runnable>> itemsSupplier = () -> {
            final List<Runnable> itemsList = Arrays.asList(
                    new RunnableItem("actionItem1"),
                    new RunnableItem("actionItem2"),
                    new RunnableItem("actionItem3"),
                    new RunnableItem("actionItem4"),
                    new RunnableItem("actionItem5")
            );
            return itemsList;
        };
        final List<Runnable> itemsList = itemsSupplier.get();
        final Runnable[] items = itemsList.toArray(new Runnable[itemsList.size()]);
        ActionListDialog.showDialog(
                textGUI,
                this.getClass().getName(),
                "description",
                items);

    }

    static class RunnableItem implements Runnable {

        final String itemName;

        public RunnableItem(String itemName) {
            this.itemName = itemName;
        }

        @Override
        public void run() {
            System.out.printf("action run %s%n", this.itemName);
        }

        @Override
        public String toString() {
            return this.itemName;
        }
    }

}
