

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
import com.googlecode.lanterna.gui2.dialogs.ListSelectDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog2;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.huberb.prototyping.lanterna.AppContext;
import org.huberb.prototyping.lanterna.ConsumerLanternaApplicationTemplate;
import org.huberb.prototyping.lanterna.ConsumerLanternaApplicationTemplate.LanternaConsumer;
import org.huberb.prototyping.lanterna.examples.dialogs.DialogsBuilders;
import org.huberb.prototyping.lanterna.examples.dialogs.ItemLabelWrappings.ItemLabel;

/**
 *
 * @author berni3
 */
public class blaze_tasks_2 {

    final AppContext appContext = new AppContext("blaze_tasks_2");

    public int main() {
        System.out.println("main task");
        return 0;
    }

    public int yesno() throws IOException {
        final ConsumerLanternaApplicationTemplate consumerLanternaApplicationTemplate = new ConsumerLanternaApplicationTemplate();

        // consumer for action handling
        final LanternaConsumer<MultiWindowTextGUI> action = (textGUI) -> {
            Contexts.logger().info(String.format("appContext %s", appContext.getM()));
            MessageDialogButton defaultMessageDialogButton = MessageDialogButton.No;
            Optional<MessageDialogButton> messageDialogButtonOptional = (Optional<MessageDialogButton>) appContext
                    .getM()
                    .getOrDefault("yesno", defaultMessageDialogButton);
            MessageDialogButton messageDialogButton = messageDialogButtonOptional.orElse(defaultMessageDialogButton);
            switch (messageDialogButton) {
                case Yes:
                    Contexts.logger().info(String.format("Handle Yes %s", messageDialogButton));
                    break;
                case No:
                    Contexts.logger().info(String.format("Handle No %s", messageDialogButton));
                    break;
                default:
                    Contexts.logger().info(String.format("Handle Unknown %s", messageDialogButton));
            }
        };
        // consumer for gui presentation
        final LanternaConsumer<MultiWindowTextGUI> gui = (textGUI) -> {
            final String description = "YesNoDescription";
            final MessageDialog2 md2 = new DialogsBuilders()
                    .title("YesNoTitle")
                    .yesno(description).build();
            final MessageDialogButton mdb = md2.showDialog(textGUI);
            appContext.getM().put("yesno", Optional.ofNullable(mdb));
        };
        // create the terminal
        final Terminal terminal = consumerLanternaApplicationTemplate.createTerminal();
        // use terminal to show yesno dialog
        // consume first gui, and then action consumer
        consumerLanternaApplicationTemplate.withCreatedMultiWindowTextGUI(
                terminal,
                gui.andThen(action)
        );

        return 0;
    }

    public int menu() throws IOException {
        final ConsumerLanternaApplicationTemplate consumerLanternaApplicationTemplate = new ConsumerLanternaApplicationTemplate();

        // consumer for action handling
        final LanternaConsumer<MultiWindowTextGUI> action = (textGUI) -> {
            Contexts.logger().info(String.format("appContext %s", appContext.getM()));
            final ItemLabel defaultItemLabel = new ItemLabel("", "");
            final Optional<ItemLabel> itemLableOptional = (Optional<ItemLabel>) appContext.getM().getOrDefault("menu", Optional.of(defaultItemLabel));
            final ItemLabel il = itemLableOptional.orElse(defaultItemLabel);
            switch (il.getItem()) {
                case "ItemMenu1":
                    Contexts.logger().info(String.format("Handle ItemMenu1 %s", il.toStringFormatted()));
                    break;
                case "ItemMenu2":
                    Contexts.logger().info(String.format("Handle ItemMenu2 %s", il.toStringFormatted()));
                    break;
                case "ItemMenu3":
                    Contexts.logger().info(String.format("Handle ItemMenu3 %s", il.toStringFormatted()));
                    break;
                default:
                    Contexts.logger().info(String.format("Handle unsupported ItemLabel %s", il.toStringFormatted()));
            }
        };

        // consumer for gui presentation
        final LanternaConsumer<MultiWindowTextGUI> gui = (textGUI) -> {
            final String description = "YesNoDescription";
            final List<ItemLabel> l = Arrays.asList(
                    new ItemLabel("Label-Menu1", "ItemMenu1"),
                    new ItemLabel("Label-Menu2", "ItemMenu2", true),
                    new ItemLabel("Label-Menu3", "ItemMenu3")
            );
            final ListSelectDialog<ItemLabel> listSelectDialogItemLabel = new DialogsBuilders()
                    .title("YesNoTitle")
                    .menu(description, description, 15, 40, l)
                    .build();
            final ItemLabel result = listSelectDialogItemLabel.showDialog(textGUI);
            appContext.getM().put("menu", Optional.ofNullable(result));
        };
        // create the terminal
        final Terminal terminal = consumerLanternaApplicationTemplate.createTerminal();
        // use terminal to show yesno dialog
        // consume first gui, and then action consumer
        consumerLanternaApplicationTemplate.withCreatedMultiWindowTextGUI(
                terminal,
                gui.andThen(action)
        );

        return 0;
    }

}
