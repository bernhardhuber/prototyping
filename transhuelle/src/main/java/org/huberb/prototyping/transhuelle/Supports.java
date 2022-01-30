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
package org.huberb.prototyping.transhuelle;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Supporting classes for building a Map {@link MapBuilder}, and a Set (@link
 * SetBuilder}.
 */
class Supports {

    /**
     * Supports building a {@link Map}.
     */
    static class MapBuilder<K, V> {

        final Map<K, V> m = new HashMap<>();

        /**
         * Add a key-value pair
         */
        MapBuilder<K, V> kv(K k, V v) {
            this.m.put(k, v);
            return this;
        }

        /**
         * Build and return the {@link Map} instance.
         */
        Map<K, V> build() {
            return this.m;
        }
    }

    /**
     * Support building a {@link Set}.
     *
     * @param <V>
     */
    static class SetBuilder<V> {

        final Set<V> s = new HashSet<>();

        /**
         * Add a collection of elements
         */
        SetBuilder<V> v(Collection<V> vc) {
            this.s.addAll(vc);
            return this;
        }

        /**
         * Add a single element
         */
        SetBuilder<V> v(V v) {
            this.s.add(v);
            return this;
        }

        /**
         * Add an array of elements
         */
        SetBuilder<V> vs(V... vs) {
            this.s.addAll(Arrays.asList(vs));
            return this;
        }

        /**
         * Build and return the {@link Set} instance.
         */
        Set<V> build() {
            return this.s;
        }
    }

}
