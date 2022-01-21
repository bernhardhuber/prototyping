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
package org.huberb.prototyping.lanterna.examples.dialogs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.huberb.prototyping.lanterna.examples.dialogs.ItemLabelWrappings.ItemLabel;

/**
 *
 * @author berni3
 */
public class Factories {

    enum DialogType {
        checkListDialog
    }

    public static class MapBuilder<K, V> {

        final Map<K, V> m;

        public MapBuilder() {
            this.m = new HashMap<>();
        }

        public MapBuilder<K, V> kv(K k, V v) {
            this.m.put(k, v);
            return this;
        }

        public Map<K, V> build() {
            return this.m;
        }
    }

    static Map<String, Object> dialogBeanMap() {
        Map<String, Object> result = new MapBuilder<String, Object>()
                .kv("title", "titleValue")
                .kv("description", "titleValue")
                .kv("dialogType", DialogType.checkListDialog)
                .kv("items", Arrays.asList(
                        new ItemLabel("cb1Label", "cb1Value"),
                        new ItemLabel("cb2Label", "cb2Value"),
                        new ItemLabel("cb3Label", "cb3Value")))
                .build();
        return result;
    }

    static class DialogBean {

        String title;
        String description;
        DialogType dialogType;
        List<String> content;
        List<ItemLabel> items;

        ItemLabel[] itemsAsArray() {
            final ItemLabel[] arr = new ItemLabel[items.size()];
            return items.toArray(arr);
        }
    }

    CheckListDialog createCheckListDialog(DialogBean dialogBean) {
        CheckListDialog instance = new CheckListDialogBuilder<ItemLabel>()
                .setTitle(dialogBean.title)
                .setDescription(dialogBean.description)
                .addListItems(dialogBean.itemsAsArray())
                .build();
        return instance;
    }
}
