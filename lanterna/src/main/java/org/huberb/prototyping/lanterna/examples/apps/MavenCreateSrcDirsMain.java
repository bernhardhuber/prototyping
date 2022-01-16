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
import java.io.File;
import java.util.Arrays;
import java.util.List;
import org.huberb.prototyping.lanterna.LanternaDialogTemplate;
import org.huberb.prototyping.lanterna.examples.dialogs.CheckListDialog;
import org.huberb.prototyping.lanterna.examples.dialogs.ItemLabelWrappings.ItemLabel;

/**
 *
 * @author berni3
 */
public class MavenCreateSrcDirsMain extends LanternaDialogTemplate {

    public static void main(String[] args) throws Exception {
        final MavenCreateSrcDirsMain mavenCreateSrcTestDirsMain = new MavenCreateSrcDirsMain();
        mavenCreateSrcTestDirsMain.launch();
    }

    public MavenCreateSrcDirsMain() {
        super("mavencreatesrcdirs");
    }

    @Override
    protected void setupComponents() {
        final MultiWindowTextGUI textGUI = getTextGUI();
        showDialog(textGUI);
    }

    void showDialog(MultiWindowTextGUI textGUI) {
        showDialogMavenProjectDirectory(textGUI);
        showDialogCreateDirectories(textGUI);
    }

    void showDialogMavenProjectDirectory(MultiWindowTextGUI textGUI) {
        final TerminalSize dialogSize = new TerminalSize(40, 15);

        final DirectoryDialog​ dd = new DirectoryDialog​(
                "Maven Project Directory",
                "description:\n" + formatApplicationContext(),
                "Select",
                dialogSize,
                true, //boolean showHiddenDirs,
                null //File selectedObject
        );

        final File result = dd.showDialog(textGUI);
        System.out.printf("%s result %s%n", MavenCreateSrcDirsMain.class.getName(), result);
        //this.context.storeResult(Widgets.jbakeDir, result);
        this.config.setProperty("showDialogMavenProjectDirectory.result", result.getPath());
    }

    void showDialogCreateDirectories(MultiWindowTextGUI textGUI) {
        final List<ItemLabel> itemLabelList = Arrays.asList(
                new ItemLabel("src", "src", false),
                new ItemLabel("src/main", "src_main"),
                new ItemLabel("src/main/java", "src_main_java"),
                new ItemLabel("src/main/resources", "src_main_resources"),
                new ItemLabel("src/test", "src_test"),
                new ItemLabel("src/test/java", "src_test_java"),
                new ItemLabel("src/test/resources", "src_test_resources")
        );
        final ItemLabel[] items = itemLabelList.toArray(ItemLabel[]::new);
        final List<ItemLabel> result
                = CheckListDialog.showDialog(
                        textGUI,
                        "Maven Project Create Directories",
                        "description:\n" + formatApplicationContext(),
                        items
                );
        System.out.printf("%s result %s%n", MavenCreateSrcDirsMain.class.getName(), result);
        //this.context.storeResult(Widgets.templateType, result);
        this.config.setProperty("showDialogCreateDirectories.result", result);
    }

    private String formatApplicationContext() {
        return "";
    }

}
