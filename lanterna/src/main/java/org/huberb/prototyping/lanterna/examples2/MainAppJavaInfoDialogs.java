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
import java.io.Serializable;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import org.huberb.prototyping.lanterna.AppContext;
import org.huberb.prototyping.lanterna.examples2.DialogWindowCreators.DialogWindowBuilder;
import org.huberb.prototyping.lanterna.examples2.DialogWindowCreators.Mode;

/**
 *
 * @author pi
 */
public class MainAppJavaInfoDialogs implements Callable<Integer> {

    private final String[] args;

    //---
    public static void main(String[] args) throws Exception {
        MainAppJavaInfoDialogs mainMessageDialog = new MainAppJavaInfoDialogs(args);
        int exitCode = mainMessageDialog.call();
        System.exit(exitCode);
    }

    //---
    public MainAppJavaInfoDialogs(String[] args) {
        this.args = args;
    }

    /**
     * Main entry.
     *
     * @return
     * @throws IOException
     */
    @Override
    public Integer call() throws Exception { // your business logic goes here...

        //---
        final TerminalScreeenTemplate terminalScreeenTemplate = new TerminalScreeenTemplateImpl();
        terminalScreeenTemplate.startScreen();
        return 0;
    }

    //---
    static abstract class TerminalScreeenTemplate {

        public void startScreen() throws IOException {
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

    static abstract class DialogWindowHandler implements Serializable {

        protected final AppContext appContext;

        public DialogWindowHandler() {
            this(null);
        }

        public DialogWindowHandler(AppContext appContext) {
            this.appContext = appContext;
        }

        abstract DialogWindow createDialogWindow();

        abstract void action(Object mdb);
    }

    static class MenuDialogWindowHandler extends DialogWindowHandler {

        public static final String MENU_DIALOG_CREATOR_PARAMETER = "menuDialogCreatorParameter";

        public static final String SYSTEM_PROPERTIES_DIALOG = "systemPropertiesDialog";
        public static final String SYSTEM_ENV_DIALOG = "systemEnvDialog";
        public static final String LOCALES_DIALOG = "localesDialog";
        public static final String SECURITY_PROVIDERS_DIALOG = "securityProvidersDialog";

        private final DialogWindowBuilder dialogWindowBuilder;

        MenuDialogWindowHandler(AppContext appContext) {
            super(appContext);
            this.dialogWindowBuilder = new DialogWindowBuilder()
                    .mode(Mode.listSelectDialog)
                    .title("Menu")
                    .description("Select")
                    .listStringParams(Arrays.asList("SystemProperties", "SystemEnv", "Locales", "SecurityProviders")
                    );
        }

        @Override
        DialogWindow createDialogWindow() {
            return this.dialogWindowBuilder.build();
        }

        @Override
        void action(Object mdbObject) {
            final String mdb = (String) mdbObject;
            if ("SystemProperties".equals(mdb)) {
                appContext.setAppName(SYSTEM_PROPERTIES_DIALOG);
            } else if ("SystemEnv".equals(mdb)) {
                appContext.setAppName(SYSTEM_ENV_DIALOG);
            } else if ("Locales".equals(mdb)) {
                appContext.setAppName(LOCALES_DIALOG);
            } else if ("SecurityProviders".equals(mdb)) {
                appContext.setAppName(SECURITY_PROVIDERS_DIALOG);
            } else {
                appContext.setAppName(MENU_DIALOG_CREATOR_PARAMETER);
            }
        }

    }

    static class SystemPropertiesDialogWindowHandler extends DialogWindowHandler {

        public SystemPropertiesDialogWindowHandler(AppContext appContext) {
            super(appContext);
        }

        @Override
        DialogWindow createDialogWindow() {
            final List<String> l = new ArrayList<>();
            System.getProperties().entrySet().forEach((entry) -> {
                l.add(String.format("%s: %s\n", entry.getKey(), entry.getValue()));
            });
            Collections.sort(l);
            final String message = l.toString();

            final DialogWindow dw = new DialogWindowBuilder()
                    .mode(Mode.textBoxDialog)
                    .title("SystemProperties")
                    .description("SystemProperties")
                    .message(message)
                    .build();
            return dw;
        }

        @Override
        void action(Object mdb) {
            appContext.setAppName(MenuDialogWindowHandler.MENU_DIALOG_CREATOR_PARAMETER);
        }
    }

    static class SystemEnvDialogWindowHandler extends DialogWindowHandler {

        public SystemEnvDialogWindowHandler(AppContext appContext) {
            super(appContext);
        }

        @Override
        DialogWindow createDialogWindow() {
            final List<String> l = new ArrayList<>();
            System.getenv().entrySet().forEach((entry) -> {
                l.add(String.format("%s: %s\n", entry.getKey(), entry.getValue()));
            });
            Collections.sort(l);
            final String message = l.toString();

            final DialogWindow dw = new DialogWindowBuilder()
                    .mode(Mode.textBoxDialog)
                    .title("SystemEnv")
                    .description("SystemEnv")
                    .message(message)
                    .build();
            return dw;
        }

        @Override
        void action(Object mdb) {
            appContext.setAppName(MenuDialogWindowHandler.MENU_DIALOG_CREATOR_PARAMETER);
        }
    }

    static class LocalesDialogWindowHandler extends DialogWindowHandler {

        public LocalesDialogWindowHandler(AppContext appContext) {
            super(appContext);
        }

        @Override
        DialogWindow createDialogWindow() {

            final List<String> l = new ArrayList<>();

            Arrays.asList(Locale.getAvailableLocales()).forEach((locale) -> {
                l.add(String.format("%s\n", locale.toString()));
            });
            Collections.sort(l);
            final String message = l.toString();

            final DialogWindow dw = new DialogWindowBuilder()
                    .mode(Mode.textBoxDialog)
                    .title("Locales")
                    .description("Locales")
                    .message(message)
                    .build();
            return dw;
        }

        @Override
        void action(Object mdb) {
            appContext.setAppName(MenuDialogWindowHandler.MENU_DIALOG_CREATOR_PARAMETER);
        }
    }

    static class SecurityProvidersDialogWindowHandler extends DialogWindowHandler {

        public SecurityProvidersDialogWindowHandler(AppContext appContext) {
            super(appContext);
        }

        @Override
        DialogWindow createDialogWindow() {

            final List<String> l = new ArrayList<>();
            Arrays.asList(Security.getProviders()).forEach((provider) -> {
                l.add(String.format("%s: %s\n", provider.getName(), provider.getServices()));
            });
            Collections.sort(l);
            final String message = l.toString();

            final DialogWindow dw = new DialogWindowBuilder()
                    .mode(Mode.textBoxDialog)
                    .title("SecurityProviders")
                    .description("SecurityProviders")
                    .message(message)
                    .build();
            return dw;
        }

        @Override
        void action(Object mdb) {
            appContext.setAppName(MenuDialogWindowHandler.MENU_DIALOG_CREATOR_PARAMETER);
        }
    }

    static class TerminalScreeenTemplateImpl extends TerminalScreeenTemplate {

        private final AppContext appContext;

        //---
        public TerminalScreeenTemplateImpl() {
            this.appContext = new AppContext(MenuDialogWindowHandler.MENU_DIALOG_CREATOR_PARAMETER);
            final Map<String, DialogWindowHandler> m = new HashMap<>();
            m.put(MenuDialogWindowHandler.MENU_DIALOG_CREATOR_PARAMETER, new MenuDialogWindowHandler(this.appContext));
            m.put(MenuDialogWindowHandler.SYSTEM_PROPERTIES_DIALOG, new SystemPropertiesDialogWindowHandler(this.appContext));
            m.put(MenuDialogWindowHandler.SYSTEM_ENV_DIALOG, new SystemEnvDialogWindowHandler(this.appContext));
            m.put(MenuDialogWindowHandler.LOCALES_DIALOG, new LocalesDialogWindowHandler(this.appContext));
            m.put(MenuDialogWindowHandler.SECURITY_PROVIDERS_DIALOG, new SecurityProvidersDialogWindowHandler(this.appContext));
            this.appContext.getM().putAll(m);
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
                final DialogWindowHandler dwh = (DialogWindowHandler) this.appContext.getM().getOrDefault(currentDialog, null);
                if (dwh == null) {
                    return;
                }
                final DialogWindow dialogWindow = dwh.createDialogWindow();
                if (dialogWindow == null) {
                    return;
                }

                final Object mdb = dialogWindow.showDialog(windowBasedTextGUI);
                if (1 == 0) {
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
            final MultiWindowTextGUI gui = new MultiWindowTextGUI(screen);

            //final String themeName = ThemeSuppliers.DefaultThemes.conquerorTheme.themeName();
            final String themeName = new ThemeSuppliers().retrieveRandomTheme();
            final Theme theme = LanternaThemes.getRegisteredTheme(themeName);
            gui.setTheme(theme);
            final WindowBasedTextGUI windowBasedTextGUI = gui;
            return windowBasedTextGUI;
        }

    }

}
