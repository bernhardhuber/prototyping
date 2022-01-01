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

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialog;
import java.io.IOException;
import java.math.BigInteger;

/**
 *
 * @author berni3
 */
public class TextInputDialogExample {

    public static void main(String[] args) throws IOException {

        final LaternaDialogTemplate laternaDialogTemplate = new LaternaDialogTemplate() {
            @Override
            protected void setupComponents() {
                new TextInputDialogExample().showDialog(textGUI);
            }
        };

        laternaDialogTemplate.launch();
    }

    void showDialog(MultiWindowTextGUI textGUI) {
        {
            final String result = TextInputDialog.showDialog(textGUI, "title", "description", "initialContent");
            System.out.printf("%s showDialog result %s%n", TextInputDialog.class.getName(), result);
        }
        {
            final BigInteger result = TextInputDialog.showNumberDialog(textGUI, "title", "description", "0");
            System.out.printf("%s showNumberDialog result %s%n", TextInputDialog.class.getName(), result);
        }
        {
            final String result = TextInputDialog.showPasswordDialog(textGUI, "title", "description", "initialContent");
            System.out.printf("%s showPasswordDialog result %s%n", TextInputDialog.class.getName(), result);
        }
    }
}
