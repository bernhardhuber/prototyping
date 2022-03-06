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

import com.fizzed.blaze.Contexts;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog2;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import java.io.IOException;
import org.huberb.prototyping.lanterna.AbstractLanternaApplicationTemplate;
import org.huberb.prototyping.lanterna.AppContext;
import org.huberb.prototyping.lanterna.examples.apps.DialogsBuildersMain;
import org.huberb.prototyping.lanterna.examples.dialogs.DialogsBuilders;

/**
 *
 * @author berni3
 */
public class blaze_tasks_1 {

    public int main() {
        System.out.println("main task");
        return 0;
    }

    public int dialogsbuildersmain() throws Exception {
        final String[] args = new String[0];
        DialogsBuildersMain.main(args);
        return 0;
    }

    public int yesno() throws IOException {
        final BlazeLanternaApplication blazeLanternaApplication = new BlazeLanternaApplication("Blaze YesNo");
        blazeLanternaApplication.launch();
        Contexts.logger().info(String.format("appContext %s", blazeLanternaApplication.appContext.getM()));

        return 0;
    }

    static class BlazeLanternaApplication extends AbstractLanternaApplicationTemplate {

        final AppContext appContext;

        public BlazeLanternaApplication(String appName) throws RuntimeException {
            super(appName);
            appContext = new AppContext(appName);
        }

        @Override
        protected void setupComponents() {
            final MultiWindowTextGUI textGUI = getTextGUI();
            dialogs(textGUI);
        }

        void dialogs(MultiWindowTextGUI textGUI) {
            final String description = "YesNoDescription";
            final MessageDialog2 md2 = new DialogsBuilders()
                    .title("YesNoTitle")
                    .yesno(description).build();
            final MessageDialogButton mdb = md2.showDialog(textGUI);
            appContext.getM().put("yesno", mdb);
        }
    }
}
