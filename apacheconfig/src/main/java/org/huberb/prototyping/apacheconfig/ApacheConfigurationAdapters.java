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
package org.huberb.prototyping.apacheconfig;

import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.DirectoryDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.FileDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.ListSelectDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder2;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.apache.commons.configuration2.AbstractConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.huberb.prototyping.lanterna.examples.dialogs.CheckListDialogBuilder;
import org.huberb.prototyping.lanterna.examples.dialogs.ItemLabelWrappings.ItemLabel;

/**
 *
 * @author berni3
 */
public class ApacheConfigurationAdapters {

    final BiFunction<Configuration, String, File> createFileFromConfig = (ac, k) -> {
        File f = Optional.ofNullable(ac.getString(k, null))
                .map((v) -> new File(v))
                .orElse(null);
        return f;
    };
    final Function<File, String> mapFileToString = (f) -> {
        return Optional.ofNullable(f).map((fopt -> fopt.toString())).orElse("");
    };

    static interface DialogBuilderConfigurationFactory<B> {

        void createConfiguration(B b, Configuration config);

        B createBuilder(Configuration config);
    }

    /**
     *
     */
    final DialogBuilderConfigurationFactory<DirectoryDialogBuilder> ddbcFactory
            = new DialogBuilderConfigurationFactory<DirectoryDialogBuilder>() {
        @Override
        public void createConfiguration(DirectoryDialogBuilder b, Configuration config) {
            config.setProperty("actionLabel", b.getActionLabel());
            config.setProperty("description", b.getDescription());
            config.setProperty("showHiddenDirs", false);
            config.setProperty("selectedObject", mapFileToString.apply(b.getSelectedDirectory()));
            config.setProperty("title", b.getTitle());
        }

        @Override
        public DirectoryDialogBuilder createBuilder(Configuration config) {
            final DirectoryDialogBuilder b = new DirectoryDialogBuilder()
                    .setActionLabel(config.getString("actionLabel", "Select"))
                    .setDescription(config.getString("description"))
                    .setSelectedDirectory(createFileFromConfig.apply(config, "selectedObject"))
                    .setTitle(config.getString("title"));
            b.setShowHiddenDirectories(config.getBoolean("showHiddenDirs", false));

            return b;
        }
    };

    /**
     *
     */
    final DialogBuilderConfigurationFactory<FileDialogBuilder> fdbcFactory = new DialogBuilderConfigurationFactory<FileDialogBuilder>() {
        @Override
        public void createConfiguration(FileDialogBuilder b, Configuration config) {
            config.setProperty("actionLabel", b.getActionLabel());
            config.setProperty("description", b.getDescription());
            config.setProperty("showHiddenDirs", false);
            config.setProperty("selectedFile", mapFileToString.apply(b.getSelectedFile()));
            config.setProperty("title", b.getTitle());
        }

        @Override
        public FileDialogBuilder createBuilder(Configuration config) {
            final FileDialogBuilder b = new FileDialogBuilder()
                    .setActionLabel(config.getString("actionLabel", "Select"))
                    .setDescription(config.getString("description"))
                    .setSelectedFile(createFileFromConfig.apply(config, "selectedFile"))
                    .setTitle(config.getString("title"));
            b.setShowHiddenDirectories(config.getBoolean("showHiddenDirs", false));

            return b;
        }
    };

    ActionListDialogBuilder createActionListDialogBuilder(AbstractConfiguration config) {
        final ActionListDialogBuilder b = new ActionListDialogBuilder()
                .setCanCancel(config.getBoolean("canCancel", false))
                .setCloseAutomaticallyOnAction(true)
                .setDescription(config.getString("description"))
                .setTitle(config.getString("title"));
        // TODO actions?
        return b;
    }

    final DialogBuilderConfigurationFactory<ListSelectDialogBuilder> lsdbFactory = new DialogBuilderConfigurationFactory<ListSelectDialogBuilder>() {
        @Override
        public void createConfiguration(ListSelectDialogBuilder b, Configuration config) {
            config.setProperty("canCanel", b.isCanCancel());
            config.setProperty("description", b.getDescription());
            config.setProperty("showHiddenDirs", false);
            config.setProperty("title", b.getTitle());
            final List<String> itemsAsList = b.getListItems();
            itemsAsList.forEach((s) -> config.addProperty("item", s));
        }

        @Override
        public ListSelectDialogBuilder createBuilder(Configuration config) {
            final ListSelectDialogBuilder<String> b = new ListSelectDialogBuilder<String>()
                    .setCanCancel(config.getBoolean("canCancel", false))
                    .setDescription(config.getString("description"))
                    .setTitle(config.getString("title"));

            final List<String> itemsAsList = Optional.ofNullable(config.getList(String.class, "items")).orElse(new ArrayList<String>());
            itemsAsList.forEach((s) -> {
                b.addListItem(s);
            });
            if (b.getListItems().isEmpty()) {
                b.addListItem("item");
            }
            return b;
        }
    };

    final DialogBuilderConfigurationFactory<MessageDialogBuilder2> mdbFactory = new DialogBuilderConfigurationFactory<MessageDialogBuilder2>() {
        @Override
        public void createConfiguration(MessageDialogBuilder2 b, Configuration config) {
            config.setProperty("text", b.getDescription());
            config.setProperty("title", b.getTitle());
            final List<MessageDialogButton> buttonsAsList = b.getButtons();
            buttonsAsList.forEach((mdb) -> config.addProperty("dialogButtons", mdb.name()));
        }

        @Override
        public MessageDialogBuilder2 createBuilder(Configuration config) {
            final MessageDialogBuilder2 b = new MessageDialogBuilder2()
                    .setText(config.getString("text"))
                    .setTitle(config.getString("title"));

            final List<String> xxx = Optional.ofNullable(config.getList(String.class, "dialogButtons")).orElse(new ArrayList<String>());
            xxx.forEach((s) -> {
                MessageDialogButton mdb = MessageDialogButton.valueOf(s);
                b.addButton(mdb);
            });
            return b;
        }
    };

    final DialogBuilderConfigurationFactory<TextInputDialogBuilder> tibFactory = new DialogBuilderConfigurationFactory<TextInputDialogBuilder>() {
        @Override
        public void createConfiguration(TextInputDialogBuilder b, Configuration config) {
            config.setProperty("description", b.getDescription());
            config.setProperty("initialContent", b.getInitialContent());
            config.setProperty("passwordInput", false);
            config.setProperty("title", b.getTitle());
        }

        @Override
        public TextInputDialogBuilder createBuilder(Configuration config) {
            TextInputDialogBuilder b = new TextInputDialogBuilder()
                    .setDescription(config.getString("description"))
                    .setInitialContent(config.getString("initialContent", ""))
                    .setPasswordInput(false)
                    .setTitle(config.getString("title"));
            return b;
        }

    };

    final DialogBuilderConfigurationFactory<TextInputDialogBuilder> tipbFactory = new DialogBuilderConfigurationFactory<TextInputDialogBuilder>() {
        @Override
        public void createConfiguration(TextInputDialogBuilder b, Configuration config) {
            config.setProperty("description", b.getDescription());
            config.setProperty("initialContent", b.getInitialContent());
            config.setProperty("passwordInput", true);
            config.setProperty("title", b.getTitle());
        }

        @Override
        public TextInputDialogBuilder createBuilder(Configuration config) {
            TextInputDialogBuilder b = new TextInputDialogBuilder()
                    .setDescription(config.getString("description"))
                    .setInitialContent(config.getString("initialContent", ""))
                    .setPasswordInput(true)
                    .setTitle(config.getString("title"));
            return b;
        }

    };

    final DialogBuilderConfigurationFactory<CheckListDialogBuilder<ItemLabel>> chlbFactory = new DialogBuilderConfigurationFactory<CheckListDialogBuilder<ItemLabel>>() {
        @Override
        public void createConfiguration(CheckListDialogBuilder<ItemLabel> b, Configuration config) {
            config.setProperty("description", b.getDescription());
            config.setProperty("title", b.getTitle());
            final List<ItemLabel> buttonsAsList = b.getListItems();
            buttonsAsList.forEach((il) -> {
                config.addProperty("items", il.getItem());
                config.addProperty("labels", il.getLabel());
            });
        }

        @Override
        public CheckListDialogBuilder<ItemLabel> createBuilder(Configuration config) {
            CheckListDialogBuilder<ItemLabel> b = new CheckListDialogBuilder<ItemLabel>()
                    .setDescription(config.getString("description"))
                    .setTitle(config.getString("title"));
            final List<String> items = Optional.ofNullable(config.getList(String.class, "items")).orElse(Collections.emptyList());
            final List<String> labels = Optional.ofNullable(config.getList(String.class, "labels")).orElse(Collections.emptyList());
            for (int i = 0; i < items.size(); i++) {
                final String item = items.get(i);
                final String label = labels.get(i);
                final ItemLabel il = new ItemLabel(item, label);
                b.addListItem(il);
            }
            if (b.getListItems().isEmpty()) {
                final ItemLabel il = new ItemLabel("", "");
                b.addListItem(il);
            }
            return b;
        }

    };

    Configuration retriveSubset(AbstractConfiguration config, String configSubSet) {
        final Configuration builderSubset = config.subset(configSubSet);
        return builderSubset;
    }

}
