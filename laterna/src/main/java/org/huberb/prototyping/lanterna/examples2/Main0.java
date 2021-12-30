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

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;

/**
 *
 * @author pi
 */
public class Main0 {

    public static void main(String[] args) throws IOException {
        // Setup terminal and screen layers
        try (Terminal terminal = new DefaultTerminalFactory().createTerminal()) {
            try (Screen screen = new TerminalScreen(terminal)) {
                screen.startScreen();

                // Create window to hold the panel
                BasicWindow window = new BasicWindow();
                // Create panel to hold components
                Panel panel = new Panel();
                panel.setLayoutManager(new GridLayout(2));

                panel.addComponent(new Label("Forename"));
                panel.addComponent(new TextBox());

                panel.addComponent(new Label("Surname"));
                panel.addComponent(new TextBox());

                panel.addComponent(new EmptySpace(new TerminalSize(0, 0))); // Empty space underneath labels
                panel.addComponent(new Button("Submit"));

                panel.addComponent(new EmptySpace(new TerminalSize(0, 0))); // Empty space underneath labels
                panel.addComponent(new Button("Close", new Runnable() {
                    @Override
                    public void run() {
                        window.close();
                    }
                }));

                window.setComponent(panel);

                // Create gui and start gui
                MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));

                gui.addWindowAndWait(window);
            }
        }
    }
}
