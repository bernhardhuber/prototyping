/*
 * Copyright 2021 pi.
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
package org.huberb.lanterna;

import com.googlecode.lanterna.bundle.LanternaThemes;
import com.googlecode.lanterna.graphics.Theme;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import org.huberb.lanterna.DialogWindowCreators.DialogWindowCreatorParameter;
import org.huberb.lanterna.DialogWindowCreators.DialogWindowFactory;
import org.huberb.lanterna.DialogWindowCreators.Mode;

/**
 *
 * @author pi
 */
public class MainMessageDialog {

    private final String[] args;

    //---
    public static void main(String[] args) throws IOException {
        MainMessageDialog mainMessageDialog = new MainMessageDialog(args);
        mainMessageDialog.process();
    }

    //---
    public MainMessageDialog(String[] args) {
        this.args = args;
    }

    /**
     * Main entry.
     *
     * @throws IOException
     */
    void process() throws IOException {

        //---
        final TerminalScreeenTemplate terminalScreeenTemplate = new TerminalScreeenTemplateImpl();
        terminalScreeenTemplate.startScreen();
    }

    //---
    static abstract class TerminalScreeenTemplate {

        void startScreen() throws IOException {
            final Terminal terminal = createTerminal();
            try (final Terminal aTerminal = terminal) {
                try (final Screen screen = new TerminalScreen(aTerminal)) {
                    screen.startScreen();

                    doWork(screen);
                }
            }
        }

        protected Terminal createTerminal() {
            //final Terminal terminal = new DefaultTerminalFactory().createTerminal();
            final Terminal terminal = new DefaultTerminalFactory().createTerminalEmulator();
            return terminal;
        }

        protected abstract void doWork(Screen screen);
    }

    static class TerminalScreeenTemplateImpl extends TerminalScreeenTemplate {

        public TerminalScreeenTemplateImpl() {
        }

        @Override
        protected void doWork(Screen screen) {
            final Object[] xx = createWindowBasedTextGUI(screen);
            final WindowBasedTextGUI windowBasedTextGUI = (WindowBasedTextGUI) xx[0];
            final String themeName = (String) xx[1];

            final OneDialowWindowCreator oneDialowWindowCreator = new OneDialowWindowCreator();
            for (;;) {
                //--- start showDialog
                final DialogWindow dialogWindow = oneDialowWindowCreator.createADialogWindow();
                if (dialogWindow == null) {
                    return;
                }
                final Object mdb = dialogWindow.showDialog(windowBasedTextGUI);
                {
                    // handle mdb
                    final String resultMessage = String.format(
                            "***%n"
                            + "theme: %s%n"
                            + "mdb: %s%n"
                            + "***%n", themeName, mdb);
                    MessageDialog.showMessageDialog(windowBasedTextGUI, "Result", resultMessage, MessageDialogButton.Close);
                    //System.out.println();
                }
                //--- end showDialog
                if (mdb == null) {
                    return;
                }
            }
        }

        protected Object[] createWindowBasedTextGUI(Screen screen) {
            // Create gui and start gui
            //final MultiWindowTextGUI gui = new MultiWindowTextGUI(screen,
            //  new DefaultWindowManager(),
            //  new EmptySpace(TextColor.ANSI.BLUE));
            final MultiWindowTextGUI gui = new MultiWindowTextGUI(screen);

            final String themeName = new ThemeSuppliers().retrieveRandomTheme();
            final Theme theme = LanternaThemes.getRegisteredTheme(themeName);
            gui.setTheme(theme);
            final WindowBasedTextGUI windowBasedTextGUI = gui;
            return new Object[]{windowBasedTextGUI, themeName};
        }

        //---
        static class OneDialowWindowCreator {

            private String title = "Title";
            private String description = "Description";
            private String message = "Message";
            private List<String> items = Arrays.asList("Item1", "Item2", "Item3", "Item4");

            private final Supplier<Mode> supplierMode = () -> {
                final Random random = new Random();
                final Mode[] modeValues = Mode.values();
                final int rValue = random.nextInt(modeValues.length);
                return modeValues[rValue];
            };

            private final BiFunction<Mode, String, String> functionModeTitle = (Mode mode, String title) -> {
                final String modeTitle = String.format("%s :: %s", title, mode);
                return modeTitle;
            };

            DialogWindow createADialogWindow() {
                //final Mode mode = Mode.menuListDialog;
                final Mode mode = supplierMode.get();
                final String modeTitle = this.functionModeTitle.apply(mode, title);

                final DialogWindowCreatorParameter dialogCreatorParameter = new DialogWindowCreatorParameter(mode,
                        modeTitle, description,
                        items,
                        message
                );
                final DialogWindow dialogWindow = new DialogWindowFactory().createDialogWindow(dialogCreatorParameter);
                return dialogWindow;
            }

        }
    }

}
