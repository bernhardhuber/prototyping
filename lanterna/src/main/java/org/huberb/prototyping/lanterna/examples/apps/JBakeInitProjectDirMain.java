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
package org.huberb.prototyping.lanterna.examples.apps;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.dialogs.DirectoryDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialog;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.huberb.prototyping.lanterna.examples.dialogs.RadioListDialog;
import org.huberb.prototyping.lanterna.examples1.ApplicationContext;
import org.huberb.prototyping.lanterna.examples1.DirectoryDialogExample;
import org.huberb.prototyping.lanterna.examples1.ItemLabel;
import org.huberb.prototyping.lanterna.examples1.LaternaDialogTemplate;
import org.huberb.prototyping.lanterna.examples1.MessageDialogButtonExample;
import org.huberb.prototyping.lanterna.examples1.RadioListDialogExample;

/**
 *
 * @author berni3
 */
public class JBakeInitProjectDirMain {

    private final ApplicationContext appContext;

    public static void main(String[] args) throws IOException {
        final LaternaDialogTemplate laternaDialogTemplate = new LaternaDialogTemplate() {
            @Override
            protected void setupComponents() {
                final MultiWindowTextGUI textGUI = getTextGUI();
                new JBakeInitProjectDirMain().showDialog(textGUI);
            }
        };

        laternaDialogTemplate.launch();
    }

    public JBakeInitProjectDirMain() {
        this.appContext = new ApplicationContext();
    }

    void showDialog(MultiWindowTextGUI textGUI) {
        showDialogProjectDir(textGUI);
        showDialogDirectoryName(textGUI);
        showDialogTemplateType(textGUI);
        showDialogSummary(textGUI);
    }

    void showDialogDirectoryName(MultiWindowTextGUI textGUI) {
        final String result = TextInputDialog.showDialog(
                textGUI,
                "JBake Project Directory",
                "description: " + formatApplicationContext(),
                "jbake-project");
        System.out.printf("%s showDialog result %s%n", TextInputDialog.class.getName(), result);
        this.appContext.put("showDialogDirectoryName", result);
    }

    void showDialogProjectDir(MultiWindowTextGUI textGUI) {
        final TerminalSize dialogSize = new TerminalSize(40, 15);

        final DirectoryDialog​ dd = new DirectoryDialog​(
                "JBake Project Name",
                "description:\n " + formatApplicationContext(),
                "Select",
                dialogSize,
                true, //boolean showHiddenDirs,
                null //File selectedObject
        );

        final File result = dd.showDialog(textGUI);
        System.out.printf("%s result %s%n", DirectoryDialogExample.class.getName(), result);
        this.appContext.put("showDialogProjectDir", result);
    }

    void showDialogTemplateType(MultiWindowTextGUI textGUI) {
        final List<ItemLabel<String>> itemLabelList = Arrays.asList(
                new ItemLabel<>("freemarker", "freemarker"),
                new ItemLabel<>("groovy", "groovy"),
                new ItemLabel<>("groovy-mte", "groovy-mte"),
                new ItemLabel<>("thymeleaf", "thymeleaf"),
                new ItemLabel<>("jade", "jade")
        );
        final ItemLabel[] items = itemLabelList.toArray(ItemLabel[]::new);
        final ItemLabel<String> result = RadioListDialog.showDialog(
                textGUI,
                "JBake Project Template Type",
                "description:\n " + formatApplicationContext(),
                items
        );
        System.out.printf("%s result %s%n", RadioListDialogExample.class.getName(), result.getItem());
        this.appContext.put("showDialogTemplateType", result);
    }

    void showDialogSummary(MultiWindowTextGUI textGUI) {
        final String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque vel diam purus.\n"
                + "\n"
                + formatApplicationContext()
                + "";
        final MessageDialogButton[] buttons = new MessageDialogButton[]{
            MessageDialogButton.OK,
            MessageDialogButton.Cancel
        };
        final MessageDialogButton result = MessageDialog.showMessageDialog(
                textGUI,
                "JBake Project",
                text,
                buttons);
        System.out.printf("%s result %s%n", MessageDialogButtonExample.class.getName(), result);
        this.appContext.put("showDialogSummary", result);
    }

    String formatApplicationContext() {
        final StringBuilder sb = new StringBuilder();

        this.appContext
                .getM().forEach((k, v) -> {
                    sb.append(String.format("%s: %s%n", k, v));
                }
                );
        return sb.toString();
    }
}
