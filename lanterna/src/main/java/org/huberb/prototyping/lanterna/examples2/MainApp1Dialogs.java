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
package org.huberb.prototyping.lanterna.examples2;

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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.huberb.prototyping.lanterna.AppContext;
import org.huberb.prototyping.lanterna.examples2.DialogWindowCreators.DialogWindowCreatorParameter;
import org.huberb.prototyping.lanterna.examples2.DialogWindowCreators.DialogWindowFactory;
import org.huberb.prototyping.lanterna.examples2.DialogWindowCreators.Mode;

/**
 *
 * @author pi
 */
public class MainApp1Dialogs {

    private final String[] args;

    //---
    public static void main(String[] args) throws IOException {
        MainApp1Dialogs mainMessageDialog = new MainApp1Dialogs(args);
        mainMessageDialog.process();
    }

    //---
    public MainApp1Dialogs(String[] args) {
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

        protected Terminal createTerminal() throws IOException {
            final Terminal terminal = new DefaultTerminalFactory().createTerminal();
            //final Terminal terminal = new DefaultTerminalFactory().createTerminalEmulator();
            return terminal;
        }

        protected abstract void doWork(Screen screen);
    }

    static abstract class DialogWindowHandler {

        protected AppContext appContext;
        protected DialogWindowCreatorParameter dialogWindowCreatorParameter;

        public DialogWindowHandler() {
        }

        public DialogWindowHandler(AppContext appContext, DialogWindowCreatorParameter dialogWindowCreatorParameter) {
            this.appContext = appContext;
            this.dialogWindowCreatorParameter = dialogWindowCreatorParameter;
        }

        abstract void action(Object mdb);
    }

    static class MenuDialogWindowHandler extends DialogWindowHandler {

        MenuDialogWindowHandler(AppContext appContext) {
            super(appContext,
                    new DialogWindowCreatorParameter(Mode.listSelectDialog,
                            "Menu", "Select",
                            Arrays.asList("M1", "M2", "M3"),
                            ""
                    ));
        }

        @Override
        void action(Object mdbObject) {
            final String mdb = (String) mdbObject;
            if ("M1".equals(mdb)) {
                appContext.setAppName("M1");
            } else if ("M2".equals(mdb)) {
                appContext.setAppName("M2");
            } else if ("M3".equals(mdb)) {
                appContext.setAppName("M3");
            } else {
                appContext.setAppName("menuDialogCreatorParameter");
            }
        }
    }

    static class MessageDialogDialogWindowHandler extends DialogWindowHandler {

        public MessageDialogDialogWindowHandler(AppContext appContext) {
            this.appContext = appContext;
            final DialogWindowCreatorParameter dialogWindowCreatorParameter
                    = new DialogWindowCreatorParameter(Mode.messageDialog,
                            "Message", "Select",
                            Collections.emptyList(),
                            "m1Message"
                    );
            this.dialogWindowCreatorParameter = dialogWindowCreatorParameter;
        }

        @Override
        void action(Object mdb) {
            appContext.setAppName("menuDialogCreatorParameter");
        }
    }

    static class RadioListDialogDialogWindowHandler extends DialogWindowHandler {

        public RadioListDialogDialogWindowHandler(AppContext appContext) {
            this.appContext = appContext;
            final DialogWindowCreatorParameter dialogWindowCreatorParameter
                    = new DialogWindowCreatorParameter(Mode.radioListDialog,
                            "RadioList", "Select",
                            Arrays.asList("m2RB1", "m2RB2", "m2RB3"),
                            ""
                    );
            this.dialogWindowCreatorParameter = dialogWindowCreatorParameter;
        }

        @Override
        void action(Object mdb) {
            appContext.setAppName("menuDialogCreatorParameter");
        }

    }

    static class CheckListDialogDialogWindowHandler extends DialogWindowHandler {

        public CheckListDialogDialogWindowHandler(AppContext appContext) {
            this.appContext = appContext;
            final DialogWindowCreatorParameter dialogWindowCreatorParameter
                    = new DialogWindowCreatorParameter(Mode.checkListDialog,
                            "CheckList", "Select",
                            Arrays.asList("m3CB1", "m3CB2", "m3CB3"),
                            ""
                    );
            this.dialogWindowCreatorParameter = dialogWindowCreatorParameter;
        }

        @Override
        void action(Object mdb) {
            appContext.setAppName("menuDialogCreatorParameter");
        }
    }

    static class TerminalScreeenTemplateImpl extends TerminalScreeenTemplate {

        private final AppContext appContext;
        private final Map<String, DialogWindowHandler> m;
        //---

        public TerminalScreeenTemplateImpl() {
            this.appContext = new AppContext("menuDialogCreatorParameter");
            this.m = new HashMap<>();
            this.m.put("menuDialogCreatorParameter", new MenuDialogWindowHandler(this.appContext));
            this.m.put("M1", new MessageDialogDialogWindowHandler(this.appContext));
            this.m.put("M2", new RadioListDialogDialogWindowHandler(this.appContext));
            this.m.put("M3", new CheckListDialogDialogWindowHandler(this.appContext));
        }

        @Override
        protected void doWork(Screen screen) {

            final WindowBasedTextGUI windowBasedTextGUI = createWindowBasedTextGUI(screen);

            for (;;) {
                //--- start showDialog
                if (this.appContext == null || this.appContext.getAppName() == null) {
                    return;
                }
                final String currentDialog = this.appContext.getAppName();
                final DialogWindowHandler dwh = this.m.getOrDefault(currentDialog, null);
                if (dwh == null) {
                    return;
                }
                final DialogWindow dialogWindow = new DialogWindowFactory().createDialogWindow(dwh.dialogWindowCreatorParameter);
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
                            + "***%n", "???", mdb);
                    MessageDialog.showMessageDialog(windowBasedTextGUI, "Result", resultMessage, MessageDialogButton.Close);
                    //System.out.println();
                }
                //--- end showDialog
                if (mdb == null) {
                    return;
                } else {
                    dwh.action(mdb);
                }
            }
        }

        protected WindowBasedTextGUI createWindowBasedTextGUI(Screen screen) {
            // Create gui and start gui
            //final MultiWindowTextGUI gui = new MultiWindowTextGUI(screen,
            //  new DefaultWindowManager(),
            //  new EmptySpace(TextColor.ANSI.BLUE));
            final MultiWindowTextGUI multiWindowTextGUI = new MultiWindowTextGUI(screen);

            //final String themeName = ThemeSuppliers.DefaultThemes.conquerorTheme.themeName();
            final String themeName = new ThemeSuppliers().retrieveRandomTheme();
            final Theme theme = LanternaThemes.getRegisteredTheme(themeName);
            multiWindowTextGUI.setTheme(theme);
            final WindowBasedTextGUI windowBasedTextGUI = multiWindowTextGUI;
            return windowBasedTextGUI;
        }
    }
}
