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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/**
 * Supporting classes for building a Map {@link MapBuilder}, and a Set (@link
 * SetBuilder}.
 */
public class Supports {

    /**
     * Supports building a {@link Map}.
     */
    public static class MapBuilder<K, V> {

        final Map<K, V> m = new HashMap<>();

        /**
         * Add a key-value pair
         */
        public MapBuilder<K, V> kv(K k, V v) {
            this.m.put(k, v);
            return this;
        }

        /**
         * Build and return the {@link Map} instance.
         */
        public Map<K, V> build() {
            return this.m;
        }
    }

    /**
     * Support building a {@link Set}.
     *
     * @param <V>
     */
    public static class SetBuilder<V> {

        final Set<V> s = new HashSet<>();

        /**
         * Add a collection of elements
         */
        public SetBuilder<V> v(Collection<V> vc) {
            this.s.addAll(vc);
            return this;
        }

        /**
         * Add a single element
         */
        public SetBuilder<V> v(V v) {
            this.s.add(v);
            return this;
        }

        /**
         * Add an array of elements
         */
        public SetBuilder<V> vs(V... vs) {
            this.s.addAll(Arrays.asList(vs));
            return this;
        }

        /**
         * Build and return the {@link Set} instance.
         */
        public Set<V> build() {
            return this.s;
        }
    }

    public static class ListBuilder<V> {

        final List<V> l = new ArrayList<>();

        public ListBuilder<V> add(V v) {
            this.l.add(v);
            return this;
        }

        public ListBuilder<V> addAll(List<V> v) {
            this.l.addAll(v);
            return this;
        }

        public List<V> build() {
            return this.l;
        }

        public List<V> buildAsUnmodifiableList() {
            return Collections.unmodifiableList(this.l);
        }
    }

    public static class Pair<U, V> {

        final U u;
        final V v;

        public static <R, S, T> Pair<R, S> create(T t, Function<T, Pair<R, S>> f) {
            final Pair<R, S> result = f.apply(t);
            return result;
        }

        public Pair(U u, V v) {
            this.u = u;
            this.v = v;
        }

        public U getLeft() {
            return getU();
        }

        public U getU() {
            return u;
        }

        public V getRight() {
            return getV();
        }

        public V getV() {
            return v;
        }

        @Override
        public String toString() {
            return "Pair{" + "u=" + u + ", v=" + v + '}';
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 83 * hash + Objects.hashCode(this.u);
            hash = 83 * hash + Objects.hashCode(this.v);
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
            final Pair<?, ?> other = (Pair<?, ?>) obj;
            if (!Objects.equals(this.u, other.u)) {
                return false;
            }
            return Objects.equals(this.v, other.v);
        }

    }

    public static class PairUnary<U> {

        final U u;
        final U v;

        public static <R, T> PairUnary<R> create(T t, Function<T, PairUnary<R>> f) {
            final PairUnary<R> result = f.apply(t);
            return result;
        }

        public PairUnary(U u, U v) {
            this.u = u;
            this.v = v;
        }

        public U getLeft() {
            return getU();
        }

        public U getU() {
            return u;
        }

        public U getRight() {
            return getV();
        }

        public U getV() {
            return v;
        }

        @Override
        public String toString() {
            return "PairUnary{" + "u=" + u + ", v=" + v + '}';
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 97 * hash + Objects.hashCode(this.u);
            hash = 97 * hash + Objects.hashCode(this.v);
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
            final PairUnary<?> other = (PairUnary<?>) obj;
            if (!Objects.equals(this.u, other.u)) {
                return false;
            }
            return Objects.equals(this.v, other.v);
        }

    }
}
