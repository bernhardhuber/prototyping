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
package org.huberb.prototyping.lanterna;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.bundle.LanternaThemes;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;

/**
 *
 * @author berni3
 */
public abstract class AbstractLanternaApplicationTemplate {

    final Terminal terminal;
    final Screen screen;
    final MultiWindowTextGUI textGUI;

    final String appName;

    protected AbstractLanternaApplicationTemplate(String appName) throws RuntimeException {
        this.appName = appName;
        try {
            final DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
            final SetupFactory setupFactory = new SetupFactory(this.appName);
            setupFactory.setupDefaultTerminalFactoryFromConfig(defaultTerminalFactory);

            this.terminal = defaultTerminalFactory.createTerminal();
            this.screen = new TerminalScreen(terminal);
            this.textGUI = setupFactory.setupMultiWindowTextGUIFromConfig(screen);
        } catch (IOException ioex) {
            throw new RuntimeException("Cannot create lanterna terminal/screen/textGUI", ioex);
        }
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public Screen getScreen() {
        return screen;
    }

    public MultiWindowTextGUI getTextGUI() {
        return textGUI;
    }

    public void launch() throws IOException {
        try (this.terminal) {
            try (this.screen) {
                screen.startScreen();
                // Create gui and start gui
                setupComponents();
            }
        } finally {
        }
    }

    protected abstract void setupComponents();

    static class SetupFactory {

        final String appName;
        final Config config;

        SetupFactory(String appName) {
            this.config = new Config();
            this.appName = appName;
        }

        protected void setupDefaultTerminalFactoryFromConfig(DefaultTerminalFactory defaultTerminalFactory) throws IOException {
            //---
            defaultTerminalFactory.setAutoOpenTerminalEmulatorWindow(config.getBoolean("autoOpenTerminalEmulatorWindow", true));
            defaultTerminalFactory.setForceTextTerminal(config.getBoolean("forceTextTerminal", false));
            defaultTerminalFactory.setPreferTerminalEmulator(config.getBoolean("preferTerminalEmulator", false));
            defaultTerminalFactory.setForceAWTOverSwing(config.getBoolean("forceAWTOverSwing", false));
            defaultTerminalFactory.setTerminalEmulatorTitle(config.getString("terminalEmulatorTitle", appName));

            final int initialTerminalSizeColumns = config.getInt("initialTerminalSizeColumns", 80);
            final int initialTerminalSizeRows = config.getInt("initialTerminalSizeRows", 25);
            defaultTerminalFactory.setInitialTerminalSize(new TerminalSize(initialTerminalSizeColumns, initialTerminalSizeRows));
        }

        protected MultiWindowTextGUI setupMultiWindowTextGUIFromConfig(Screen screen) {
            final TextColor.ANSI backgroundColor = config.get(TextColor.ANSI.class, "backgroundColor", TextColor.ANSI.BLUE);
            final MultiWindowTextGUI textGUI = new MultiWindowTextGUI(screen,
                    new DefaultWindowManager(),
                    new EmptySpace(backgroundColor));

            final String themeName = config.getString("themeName", "default");

            textGUI.setTheme(LanternaThemes.getRegisteredTheme(themeName));
            return textGUI;
        }

        static class Config {

            private boolean getBoolean(String key, boolean defaultValue) {
                return defaultValue;
            }

            private String getString(String key, String defaultValue) {
                return defaultValue;
            }

            private int getInt(String key, int defaultValue) {
                return defaultValue;
            }

            private <T> T get(Class<T> aClass, String key, T defaultValue) {
                return defaultValue;
            }

        }
    }
}
