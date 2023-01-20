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

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.datafaker.Faker;

/**
 *
 * @author berni3
 */
public class Fakers {

    static class FakerContext {

        private final Faker faker;

        public FakerContext(Faker faker) {
            this.faker = faker;
        }

        Faker faker() {
            return this.faker;
        }

        FakerContext accept(Consumer<Faker> c) {
            c.accept(this.faker);
            return this;
        }

        <V> FakerContext acceptFromFunction(Consumer<V> c, Function<Faker, V> f) {
            final V v = f.apply(this.faker);
            c.accept(v);
            return this;
        }

        <V> FakerContext acceptFromSupplier(Consumer<V> c, Supplier<V> v) {
            c.accept(v.get());
            return this;
        }

        <V> FakerContext acceptFromValue(Consumer<V> c, V v) {
            c.accept(v);
            return this;
        }

    }

}
