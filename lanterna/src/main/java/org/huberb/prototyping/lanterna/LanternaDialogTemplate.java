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
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.ClasspathLocationStrategy;
import org.apache.commons.configuration2.io.CombinedLocationStrategy;
import org.apache.commons.configuration2.io.FileLocationStrategy;
import org.apache.commons.configuration2.io.FileSystemLocationStrategy;
import org.apache.commons.configuration2.io.HomeDirectoryLocationStrategy;
import org.apache.commons.configuration2.io.ProvidedURLLocationStrategy;

/**
 *
 * @author berni3
 */
public abstract class LanternaDialogTemplate {

    final Terminal terminal;
    final Screen screen;
    final MultiWindowTextGUI textGUI;

    final String appName;
    private FileBasedConfigurationBuilder<FileBasedConfiguration> builder;
    protected Configuration config;

    protected LanternaDialogTemplate(String appName) throws RuntimeException {
        this.appName = appName;
        try {
            setupApacheConfiguration();

            final DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
            setupDefaultTerminalFactoryFromConfig(defaultTerminalFactory);

            this.terminal = defaultTerminalFactory.createTerminal();
            this.screen = new TerminalScreen(terminal);
//            this.textGUI = new MultiWindowTextGUI(screen,
//                    new DefaultWindowManager(),
//                    new EmptySpace(TextColor.ANSI.BLUE)
//            );
            this.textGUI = setupMultiWindowTextGUIFromConfig();
        } catch (IOException ioex) {
            throw new RuntimeException("Cannot create lanterna terminal/screen/textGUI", ioex);
        } catch (ConfigurationException confex) {
            throw new RuntimeException("Cannot load apache configuration", confex);
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

    public void launch() throws IOException, ConfigurationException {
        try (this.terminal) {
            try (this.screen) {
                screen.startScreen();
                // Create gui and start gui
                setupComponents();
            }
        } finally {
            builder.save();
        }
    }

    void setupApacheConfiguration() throws ConfigurationException, IOException {
        final String filename = appName + ".properties";
        final Parameters params = new Parameters();

        new File(".lanterna", filename).createNewFile();

        final List<FileLocationStrategy> subs = Arrays.asList(
                new ProvidedURLLocationStrategy(),
                new FileSystemLocationStrategy(),
                new HomeDirectoryLocationStrategy(true),
                new ClasspathLocationStrategy());
        final FileLocationStrategy strategy = new CombinedLocationStrategy(subs);

        builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(params.fileBased()
                        //.setLocationStrategy(strategy)
                        .setBasePath(".lanterna")
                        .setFileName(filename)
                );

//        builder.getFileHandler().setFileLocator(
//                FileLocatorUtils.fileLocator()
//                        .basePath(".lanterna")
//                        .fileName(filename)
//                        .locationStrategy(new ProvidedURLLocationStrategy())
//                        .create()
//        );
        config = builder.getConfiguration();

    }

    void setupDefaultTerminalFactoryFromConfig(DefaultTerminalFactory defaultTerminalFactory) throws IOException {
        //---
        defaultTerminalFactory.setAutoOpenTerminalEmulatorWindow(
                config.getBoolean("autoOpenTerminalEmulatorWindow", true));

        defaultTerminalFactory.setForceTextTerminal(
                config.getBoolean("forceTextTerminal", false));

        defaultTerminalFactory.setPreferTerminalEmulator(
                config.getBoolean("preferTerminalEmulator", false));

        defaultTerminalFactory.setForceAWTOverSwing(
                config.getBoolean("forceAWTOverSwing", false));

        defaultTerminalFactory.setTerminalEmulatorTitle(
                config.getString("terminalEmulatorTitle", appName));

        final int initialTerminalSizeColumns = config.getInt("initialTerminalSizeColumns", 80);
        final int initialTerminalSizeRows = config.getInt("initialTerminalSizeRows", 25);
        defaultTerminalFactory.setInitialTerminalSize(new TerminalSize(initialTerminalSizeColumns, initialTerminalSizeRows));
    }

    MultiWindowTextGUI setupMultiWindowTextGUIFromConfig() {
        final TextColor.ANSI backgroundColor = config.get(TextColor.ANSI.class, "backgroundColor", TextColor.ANSI.BLUE);
        MultiWindowTextGUI textGUI = new MultiWindowTextGUI(screen,
                new DefaultWindowManager(),
                new EmptySpace(backgroundColor));

        final String themeName = config.getString("themeName", "default");

        textGUI.setTheme(LanternaThemes.getRegisteredTheme(themeName));
        return textGUI;
    }

    protected abstract void setupComponents();

}
