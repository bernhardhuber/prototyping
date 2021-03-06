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
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import org.huberb.prototyping.lanterna.AbstractLanternaApplicationTemplate;
import org.huberb.prototyping.lanterna.LanternaLauncher;

/**
 *
 * @author berni3
 */
public class MessageDialogButtonExample extends AbstractLanternaApplicationTemplate {

    public static void main(String[] args) throws Exception {
        LanternaLauncher.launchWithClass(MessageDialogButtonExample.class, args);
    }

    public MessageDialogButtonExample() {
        super(MessageDialogButtonExample.class.getName());
    }

    @Override
    protected void setupComponents() {
        showDialog(this.getTextGUI());
    }

    void showDialog(MultiWindowTextGUI textGUI) {
        final String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque vel diam purus.\n"
                + "Curabitur ut nisi lacus.\n"
                + "Nam id nisl quam. Donec a lorem sit amet libero pretium vulputate vel ut purus.\n"
                + "Suspendisse leo arcu,\n"
                + "mattis et imperdiet luctus, pulvinar vitae mi. Quisque fermentum sollicitudin feugiat.\n"
                + "\n"
                + "Mauris nec leo\n"
                + "ligula. Vestibulum tristique odio ut risus ultricies a hendrerit quam iaculis.\n"
                + "Duis tempor elit sit amet\n"
                + "ligula vehicula et iaculis sem placerat. Fusce dictum, metus at volutpat lacinia, elit massa auctor risus,\n"
                + "id auctor arcu enim eu augue. Donec ultrices turpis in mi imperdiet ac venenatis sapien sodales.\n"
                + "In consequat imperdiet nunc quis bibendum. Nulla semper, erat quis ornare tristique, lectus massa posuere\n"
                + "libero, ut vehicula lectus nunc ut lorem.\n"
                + "Aliquam erat volutpat.\n"
                + "\n"
                + "";
        {
            final MessageDialogButton[] buttons = new MessageDialogButton[]{
                MessageDialogButton.OK
            };
            final MessageDialogButton result = MessageDialog.showMessageDialog(
                    textGUI,
                    this.getClass().getName(),
                    text,
                    buttons);
            System.out.printf("%s result %s%n", MessageDialogButtonExample.class.getName(), result);
        }
        {
            final MessageDialogButton[] buttons = new MessageDialogButton[]{
                MessageDialogButton.Yes,
                MessageDialogButton.No
            };
            final MessageDialogButton result = MessageDialog.showMessageDialog(
                    textGUI,
                    this.getClass().getName(),
                    text,
                    buttons);
            System.out.printf("%s result %s%n", MessageDialogButtonExample.class.getName(), result);
        }
        {
            final MessageDialogButton[] buttons = new MessageDialogButton[]{
                MessageDialogButton.Abort,
                MessageDialogButton.Cancel,
                MessageDialogButton.Close,
                MessageDialogButton.Continue,
                MessageDialogButton.Ignore,
                MessageDialogButton.No,
                MessageDialogButton.OK,
                MessageDialogButton.Retry,
                MessageDialogButton.Yes
            };
            final MessageDialogButton result = MessageDialog.showMessageDialog(
                    textGUI,
                    this.getClass().getName(),
                    text,
                    buttons);
            System.out.printf("%s result %s%n", MessageDialogButtonExample.class.getName(), result);
        }
    }
}
