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

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.dialogs.FileDialog;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author berni3
 */
public class FileDialogExample {

    public static void main(String[] args) throws IOException {

        final LaternaDialogTemplate laternaDialogTemplate = new LaternaDialogTemplate() {
            @Override
            protected void setupComponents() {
                new FileDialogExample().showDialog(textGUI);
            }
        };

        laternaDialogTemplate.launch();
    }

    void showDialog(MultiWindowTextGUI textGUI) {
        final TerminalSize dialogSize = new TerminalSize(40, 15);

        final FileDialog​ dd = new FileDialog​(
                "title", "description", "Select",
                dialogSize,
                true, //boolean showHiddenDirs, 
                null //File selectedObject
        );

        final File result = dd.showDialog(textGUI);
        System.out.printf("%s result %s%n", FileDialogExample.class.getName(), result);
    }

}
