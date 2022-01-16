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

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author berni3
 */
public class ItemLabelWrappings {

    public interface IItemLabel<T> {

        boolean isSelected();

        String getLabel();

        T getItem();
    }

    public static class ItemLabel implements IItemLabel<String>, Serializable {

        private static final long serialVersionUID = 20220116L;

        private final boolean selected;
        private final String label;
        private final String item;

        public ItemLabel(String label, String item) {
            this(label, item, false, true);
        }

        public ItemLabel(String label, String item, boolean selected, boolean enabled) {
            this.label = label;
            this.item = item;
            this.selected = selected;
        }

        @Override
        public boolean isSelected() {
            return this.selected;
        }

        @Override
        public String getLabel() {
            return this.label;
        }

        @Override
        public String getItem() {
            return this.item;
        }

        @Override
        public String toString() {
            return this.label;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 53 * hash + Objects.hashCode(this.label);
            hash = 53 * hash + Objects.hashCode(this.item);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final ItemLabel other = (ItemLabel) obj;
            if (!Objects.equals(this.label, other.label)) {
                return false;
            }
            return Objects.equals(this.item, other.item);
        }

    }
}
