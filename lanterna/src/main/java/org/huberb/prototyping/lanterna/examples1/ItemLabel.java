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

import java.util.Objects;

/**
 *
 * @author berni3
 */
public class ItemLabel<T> {

    final String label;
    final T item;

    public ItemLabel(String label, T item) {
        this.item = item;
        this.label = label;
    }

    public static <T> ItemLabel<T> create(String label, T item) {
        return new ItemLabel<>(label, item);
    }

    public String getLabel() {
        return label;
    }

    public T getItem() {
        return item;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.label);
        hash = 19 * hash + Objects.hashCode(this.item);
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
        final ItemLabel<?> other = (ItemLabel<?>) obj;
        return true;
    }

    @Override
    public String toString() {
        return this.label;
    }

}
