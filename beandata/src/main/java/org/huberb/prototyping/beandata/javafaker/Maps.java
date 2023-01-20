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
package org.huberb.prototyping.beandata.javafaker;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author berni3
 */
public class Maps {

    /**
     * A map with an restricted set of keys.
     */
    public static class FixedKeyMap<V> extends HashMap<String, V> {

        private final String[] keys;
        private boolean restricted = true;

        public FixedKeyMap(String... keys) {
            this(true, keys);
        }

        public FixedKeyMap(boolean restricted, String... keys) {
            super(3);
            // copy names to internal names-array
            final int namesLength = keys.length;
            this.keys = Arrays.copyOf(keys, namesLength);
            this.restricted = restricted;
        }

        public boolean isRestricted() {
            return this.restricted;
        }

        public void setRestricted(boolean restricted) {
            this.restricted = restricted;
        }

        @Override
        public V put(String key, V value) {
            final V result;
            if (restricted) {
                final boolean keyInNames = Arrays.stream(this.keys).anyMatch((s) -> key.equals(s));
                if (keyInNames) {
                    result = super.put(key, value);
                } else {
                    result = null;
                }
            } else {
                result = super.put(key, value);
            }
            return result;
        }

        @Override
        public void putAll(Map<? extends String, ? extends V> m) {
            for (Entry<? extends String, ? extends V> e : m.entrySet()) {
                this.put(e.getKey(), e.getValue());
            }
        }

        @Override
        public V putIfAbsent(String key, V value) {
            final V result;
            if (restricted) {
                final boolean keyInNames = Arrays.stream(this.keys).anyMatch((s) -> key.equals(s));
                if (keyInNames) {
                    result = super.putIfAbsent(key, value);
                } else {
                    result = null;
                }
            } else {
                result = super.putIfAbsent(key, value);
            }
            return result;
        }

    }

    /**
     * A map with an restricted set of keys.
     *
     * @author berni3
     */
    static class NamedMap<V> extends AbstractMap<String, V> {

        private final String[] names;
        private final V[] values;

        public NamedMap(String... names) {
            int namesLength = names.length;
            final Map<String, Integer> m = new HashMap<>();
            for (int i = 0; i < namesLength; i++) {
                m.putIfAbsent(names[i], i);
            }
            final int uniqueNamesLength = m.size();

            this.names = new String[uniqueNamesLength];
            for (Entry<String, Integer> e : m.entrySet()) {
                final String k = e.getKey();
                final int v = e.getValue();
                this.names[v] = k;
            }
            this.values = (V[]) new Object[uniqueNamesLength];
        }

        @Override
        public int size() {
            int result = 0;
            for (int i = 0; i < values.length; i++) {
                if (values[i] != null) {
                    result += 1;
                }
            }
            return result;
        }

        @Override
        public void clear() {
            for (int i = 0; i < values.length; i++) {
                values[i] = null;
            }
        }

        @Override
        public Set<Entry<String, V>> entrySet() {
            final Set<Entry<String, V>> result = new HashSet<>();
            for (int i = 0; i < this.names.length; i++) {
                final String key = this.names[i];
                final V value = this.values[i];
                final Entry<String, V> e = new SimpleEntry<>(key, value);
                result.add(e);
            }
            return result;
        }

        class NamedMapIterator implements Iterator<Entry<String, V>> {

            int currentIndex = 0;

            NamedMapIterator() {
                currentIndex = nextIndexFrom(0);
            }

            // find index with values[index]!=null
            private int nextIndexFrom(int offset) {
                int nextIndex = NamedMap.this.size();
                for (int i = offset; i < NamedMap.this.size(); i++) {
                    if (NamedMap.this.values[i] != null) {
                        nextIndex = i;
                        break;
                    }
                }
                return nextIndex;
            }

            @Override
            public boolean hasNext() {
                return currentIndex < NamedMap.this.size();
            }

            @Override
            public Entry<String, V> next() {
                final String key = NamedMap.this.names[currentIndex];
                final V value = NamedMap.this.values[currentIndex];
                final Entry<String, V> e = new SimpleEntry<>(key, value);
                currentIndex = nextIndexFrom(currentIndex + 1);
                return e;
            }
        }

        class NamedMapEntrySet extends AbstractSet<Map.Entry<String, V>> {

            @Override
            public Iterator<Entry<String, V>> iterator() {
                return new NamedMapIterator();
            }

            @Override
            public int size() {
                return names.length;
            }

            @Override
            public void clear() {
                NamedMap.this.clear();
            }

        }

        @Override
        public V put(String key, V value) {
            int offset = -1;
            for (int i = 0; i < this.names.length; i++) {
                if (this.names[i].equals(key)) {
                    offset = i;
                    break;
                }
            }
            final V oldValue;
            if (offset != -1) {
                oldValue = this.values[offset];
                this.values[offset] = value;
            } else {
                oldValue = null;
            }
            return oldValue;
        }
    }

}
