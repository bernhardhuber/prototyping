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

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialog;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 *
 * @author berni3
 */
public class ActionListDialogExample {

    public static void main(String[] args) throws IOException {

        final Supplier<  List<Runnable>> itemsSupplier = () -> {
            final List<Runnable> itemsList = Arrays.asList(
                    new RunnableItem("item1"),
                    new RunnableItem("item2"),
                    new RunnableItem("item3")
            );
            return itemsList;
        };

        final LaternaDialogTemplate laternaDialogTemplate = new LaternaDialogTemplate() {
            @Override
            protected void setupComponents() {
                final List<Runnable> itemsList = itemsSupplier.get();
                final Runnable[] items = itemsList.toArray(new Runnable[itemsList.size()]);
                ActionListDialog.showDialog(textGUI, ActionListDialogExample.class.getName(), "description", items);
            }
        };

        laternaDialogTemplate.launch();
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

    public static abstract class LaternaDialogTemplate {

        final Terminal terminal;
        final Screen screen;
        MultiWindowTextGUI textGUI;

        public LaternaDialogTemplate() throws IOException {
            this.terminal = new DefaultTerminalFactory().createTerminal();
            this.screen = new TerminalScreen(terminal);
        }

        public void launch() throws IOException {
            try (this.terminal) {
                try (this.screen) {
                    screen.startScreen();

                    // Create gui and start gui
                    this.textGUI = new MultiWindowTextGUI(screen,
                            new DefaultWindowManager(),
                            new EmptySpace(TextColor.ANSI.BLUE)
                    );
                    //
                    setupComponents();
                }
            }
        }

        protected abstract void setupComponents();
    }
}
