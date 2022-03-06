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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import org.huberb.prototyping.lanterna.AbstractLanternaApplicationTemplate;
import org.huberb.prototyping.lanterna.examples.apps.JBakeInitProjectDirMain.Context.Widgets;
import org.huberb.prototyping.lanterna.examples.dialogs.ItemLabelWrappings.ItemLabel;
import org.huberb.prototyping.lanterna.examples.dialogs.RadioListDialog;

/**
 *
 * @author berni3
 */
public class JBakeInitProjectDirMain extends AbstractLanternaApplicationTemplate {

    public static void main(String[] args) throws Exception {
        final JBakeInitProjectDirMain jbakeInitProjectDirMain = new JBakeInitProjectDirMain();
        jbakeInitProjectDirMain.launch();
    }

    private final Context context;

    public JBakeInitProjectDirMain() throws IOException {
        super("jbakeinitprojectdir");
        this.context = new Context();
    }

    @Override
    protected void setupComponents() {
        final MultiWindowTextGUI textGUI = getTextGUI();
        showDialog(textGUI);
    }

    void showDialog(MultiWindowTextGUI textGUI) {
        showDialogJbakeDir(textGUI);
        showDialogProjectDir(textGUI);
        showDialogDirectoryName(textGUI);
        showDialogTemplateType(textGUI);
        validateAndProcessInput();
        showDialogSummary(textGUI);
        if (MessageDialogButton.OK.equals(this.context.retrieveResult(Widgets.summary))) {
            System.out.printf("TODO run jbakeInitProject command%n");
        }
    }

    void showDialogJbakeDir(MultiWindowTextGUI textGUI) {
        final TerminalSize dialogSize = new TerminalSize(40, 15);

        final DirectoryDialog​ dd = new DirectoryDialog​(
                "JBake Install Directory",
                "description:\n" + formatApplicationContext(),
                "Select",
                dialogSize,
                true, //boolean showHiddenDirs,
                null //File selectedObject
        );

        final File result = dd.showDialog(textGUI);
        System.out.printf("%s result %s%n", JBakeInitProjectDirMain.class.getName(), result);
        this.context.storeResult(Widgets.jbakeDir, result);
    }

    void showDialogProjectDir(MultiWindowTextGUI textGUI) {
        final TerminalSize dialogSize = new TerminalSize(40, 15);

        final DirectoryDialog​ dd = new DirectoryDialog​(
                "JBake Project Directory",
                "description:\n" + formatApplicationContext(),
                "Select",
                dialogSize,
                true, //boolean showHiddenDirs,
                null //File selectedObject
        );

        final File result = dd.showDialog(textGUI);
        System.out.printf("%s result %s%n", JBakeInitProjectDirMain.class.getName(), result);
        this.context.storeResult(Widgets.projectDir, result);
    }

    void showDialogDirectoryName(MultiWindowTextGUI textGUI) {
        final String result = TextInputDialog.showDialog(
                textGUI,
                "JBake Project Directory",
                "description:\n" + formatApplicationContext(),
                "jbake-project");
        System.out.printf("%s showDialog result %s%n", TextInputDialog.class.getName(), result);
        this.context.storeResult(Widgets.projectName, result);
    }

    void showDialogTemplateType(MultiWindowTextGUI textGUI) {
        final List<ItemLabel> itemLabelList = Arrays.asList(
                new ItemLabel("freemarker", "freemarker", true),
                new ItemLabel("groovy", "groovy"),
                new ItemLabel("groovy-mte", "groovy-mte"),
                new ItemLabel("thymeleaf", "thymeleaf"),
                new ItemLabel("jade", "jade")
        );
        final ItemLabel[] items = itemLabelList.toArray(ItemLabel[]::new);
        final ItemLabel result = Optional.ofNullable(
                RadioListDialog.showDialog(
                        textGUI,
                        "JBake Project Template Type",
                        "description:\n" + formatApplicationContext(),
                        items
                )).orElseGet(() -> {
            return new ItemLabel(null, null);
        });
        System.out.printf("%s result %s%n", JBakeInitProjectDirMain.class.getName(), result.getItem());
        this.context.storeResult(Widgets.templateType, result);
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
        System.out.printf("%s result %s%n", JBakeInitProjectDirMain.class.getName(), result);
        this.context.storeResult(Widgets.summary, result);
    }

    String formatApplicationContext() {
        final StringBuilder sb = new StringBuilder();

        final Comparator<Map.Entry<String, Object>> c
                = (Entry<String, Object> arg0, Entry<String, Object> arg1)
                -> arg0.getKey().compareTo(arg1.getKey());
        this.context.getM().entrySet().stream()
                .sorted(c)
                .forEach((e) -> {
                    sb.append(String.format("%s: %s%n", e.getKey(), e.getValue()));
                });
        return sb.toString();
    }

    //---
    void validateAndProcessInput() {
        final File projectDirFile = (File) this.context.retrieveResult(Widgets.projectDir);

        //this.appContext.put("projectDirFile.exists", projectDirFile.exists());
        final String projectName = (String) this.context.retrieveResult(Widgets.projectName);
        final File fullProjectName = new File(projectDirFile, projectName);
        //this.appContext.put("fullProjectName.exists", fullProjectName.exists());

        //this.appContext.put("fullProjectName.assets.exists", new File(fullProjectName, "assets").exists());
        //this.appContext.put("fullProjectName.content.exists", new File(fullProjectName, "content").exists());
        //this.appContext.put("fullProjectName.templates.exists", new File(fullProjectName, "templates").exists());
    }

    static class Context {

        final Map<String, Object> m;

        enum Widgets {
            jbakeDir(File.class),
            projectDir(File.class),
            projectName(String.class),
            templateType(String.class),
            summary(String.class);

            Class<?> resultClass;

            Widgets(Class<?> resultClass) {
                this.resultClass = resultClass;
            }

            String defaultKeyValue() {
                String theName = name();
                String defaultValueKey = theName + ".defaultValue";
                return defaultValueKey;
            }

            String resultKeyValue() {
                String theName = name();
                String resultValueKey = theName + ".resultValue";
                return resultValueKey;
            }

            Map<String, Object> createMapTemplate() {
                Map<String, Object> m = new HashMap<>();
                String defaultValueKey = defaultKeyValue();
                String resultValueKey = resultKeyValue();
                m.put(defaultValueKey, null);
                m.put(resultValueKey, null);
                return m;
            }

            void storeResult(Map<String, Object> m, Object result) {
                String resultValueKey = resultKeyValue();
                m.put(resultValueKey, result);
            }

            Object retrieveResult(Map<String, Object> m) {
                String resultValueKey = resultKeyValue();
                return m.get(resultValueKey);
            }
        }

        Context() {
            this.m = new HashMap<>();
            Arrays.asList(Widgets.values())
                    .stream()
                    .forEach((w) -> this.m.putAll(w.createMapTemplate()));
        }

        void setUpWidgetInM() {
            this.m.put("jbakeDir", "File.class, jbakeDir.defaultValue=null, jbakeDir.resultValue=null");
        }

        public Map<String, Object> getM() {
            return this.m;
        }

        void storeResult(Widgets w, Object result) {
            w.storeResult(m, result);
        }

        Object retrieveResult(Widgets w) {
            return w.retrieveResult(m);
        }
    }
}
