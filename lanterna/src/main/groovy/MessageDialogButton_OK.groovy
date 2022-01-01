/*
 * Copyright 2022 berni3.
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

// load lanterna
@Grab('com.googlecode.lanterna:lanterna:3.1.1')
@GrabConfig(systemClassLoader=true)

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import java.io.IOException;

DefaultTerminalFactory dtf = new DefaultTerminalFactory();
dtf.preferTerminalEmulator = true

try (final Terminal terminal = dtf.createTerminal()) {
    try (final Screen screen = new TerminalScreen(terminal)) {
        screen.startScreen();
        
        final MultiWindowTextGUI textGUI = new MultiWindowTextGUI(screen, 
            new DefaultWindowManager(), 
            new EmptySpace(TextColor.ANSI.BLUE)
        );

        final String text = '''Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque vel diam purus.
Curabitur ut nisi lacus.
Nam id nisl quam. Donec a lorem sit amet libero pretium vulputate vel ut purus.
Suspendisse leo arcu,
mattis et imperdiet luctus, pulvinar vitae mi. Quisque fermentum sollicitudin feugiat.

Mauris nec leo
ligula. Vestibulum tristique odio ut risus ultricies a hendrerit quam iaculis.
Duis tempor elit sit amet
ligula vehicula et iaculis sem placerat. Fusce dictum, metus at volutpat lacinia, elit massa auctor risus,
id auctor arcu enim eu augue. Donec ultrices turpis in mi imperdiet ac venenatis sapien sodales.
In consequat imperdiet nunc quis bibendum. Nulla semper, erat quis ornare tristique, lectus massa posuere
libero, ut vehicula lectus nunc ut lorem.
Aliquam erat volutpat.''';
        
        final MessageDialogButton[] buttons = new MessageDialogButton[]{
            MessageDialogButton.OK
        };
        final MessageDialogButton result = MessageDialog.showMessageDialog(textGUI, "title", text, buttons);
        System.out.printf("%s result %s%n", this.class.getName(), result);        
    }
}

return 0
