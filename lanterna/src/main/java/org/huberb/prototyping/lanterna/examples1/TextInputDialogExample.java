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
import java.math.BigInteger;
import org.huberb.prototyping.lanterna.AbstractLanternaApplicationTemplate;
import org.huberb.prototyping.lanterna.LanternaLauncher;

/**
 *
 * @author berni3
 */
public class TextInputDialogExample extends AbstractLanternaApplicationTemplate {

    public static void main(String[] args) throws Exception {
        LanternaLauncher.launchWithClass(TextInputDialogExample.class, args);
    }

    public TextInputDialogExample() {
        super(TextInputDialogExample.class.getName());
    }

    @Override
    protected void setupComponents() {
        showDialog(this.getTextGUI());
    }

    void showDialog(MultiWindowTextGUI textGUI) {
        {
            final String result = TextInputDialog.showDialog(
                    textGUI,
                    this.getClass().getName(),
                    "description",
                    "initialContent");
            System.out.printf("%s showDialog result %s%n", TextInputDialog.class.getName(), result);
        }
        {
            final BigInteger result = TextInputDialog.showNumberDialog(
                    textGUI,
                    this.getClass().getName(),
                    "description",
                    "0");
            System.out.printf("%s showNumberDialog result %s%n", TextInputDialog.class.getName(), result);
        }
        {
            final String result = TextInputDialog.showPasswordDialog(
                    textGUI,
                    this.getClass().getName(),
                    "description",
                    "initialContent"
            );
            System.out.printf("%s showPasswordDialog result %s%n", TextInputDialog.class.getName(), result);
        }
    }
}
