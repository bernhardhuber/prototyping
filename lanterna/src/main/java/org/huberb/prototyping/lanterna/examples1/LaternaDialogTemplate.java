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

import com.googlecode.lanterna.TextColor;
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
public abstract class LaternaDialogTemplate {

    final Terminal terminal;
    final Screen screen;
    final MultiWindowTextGUI textGUI;

    public LaternaDialogTemplate() throws IOException {
        this.terminal = new DefaultTerminalFactory().createTerminal();
        this.screen = new TerminalScreen(terminal);
        this.textGUI = new MultiWindowTextGUI(screen,
                new DefaultWindowManager(),
                new EmptySpace(TextColor.ANSI.BLUE)
        );
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
        }
    }

    protected abstract void setupComponents();

}
