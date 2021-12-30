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
package org.huberb.prototyping.lanterna.examples0;

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
import java.util.regex.Pattern;

/**
 *
 * @author berni3
 * @see
 * <a href="https://github.com/mabe02/lanterna/blob/master/docs/examples/gui/basic_form_submission.md">Calculator</a>
 */
public class Calculator01 {

    public static void main(String[] args) throws IOException {
        // Setup terminal and screen layers
        try (final Terminal terminal = new DefaultTerminalFactory().createTerminal()) {
            try (final Screen screen = new TerminalScreen(terminal)) {
                screen.startScreen();

                // Create panel to hold components
                final Panel panel = new Panel();
                panel.setLayoutManager(new GridLayout(2));

                final Label lblOutput = new Label("");

                panel.addComponent(new Label("Num 1"));
                final TextBox txtNum1 = new TextBox().setValidationPattern(Pattern.compile("[0-9]*")).addTo(panel);

                panel.addComponent(new Label("Num 2"));
                final TextBox txtNum2 = new TextBox().setValidationPattern(Pattern.compile("[0-9]*")).addTo(panel);

                panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));
                new Button("Add!", new Runnable() {
                    @Override
                    public void run() {
                        int num1 = Integer.parseInt(txtNum1.getText());
                        int num2 = Integer.parseInt(txtNum2.getText());
                        lblOutput.setText(Integer.toString(num1 + num2));
                    }
                }).addTo(panel);

                panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));
                panel.addComponent(lblOutput);

                // Create window to hold the panel
                final BasicWindow window = new BasicWindow();
                window.setComponent(panel);

                // Create gui and start gui
                final MultiWindowTextGUI gui = new MultiWindowTextGUI(screen,
                        new DefaultWindowManager(),
                        new EmptySpace(TextColor.ANSI.BLUE)
                );
                gui.addWindowAndWait(window);
            }
        }
    }
}
