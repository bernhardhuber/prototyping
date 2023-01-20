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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import net.datafaker.Faker;
import org.huberb.prototyping.beandata.javafaker.Fakers.FakerContext;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class FakerContextTest {

    @Test
    public void testAccept() {
        final Map<String, String> m = new HashMap<>();
        final FakerContext fakerContext1 = new FakerContext(new Faker());
        fakerContext1
                .accept((f) -> m.put("fn1", f.name().firstName()))
                .accept((f) -> m.put("ln1", f.name().lastName()));
        assertAll(
                () -> assertNotNull(m.get("fn1")),
                () -> assertNotNull(m.get("ln1")),
                () -> assertNull(m.get("xxx"))
        );
    }

    @Test
    public void testAccept2() {
        final Map<String, String> m = new HashMap<>();
        final FakerContext fakerContext = new FakerContext(new Faker());
        final Function<Faker, String> fnFunction = (f) -> f.name().firstName();
        final Function<Faker, String> lnFunction = (f) -> f.name().lastName();

        fakerContext
                .acceptFromFunction((v) -> m.put("fn2", v), fnFunction)
                .acceptFromFunction((v) -> m.put("ln2", v), lnFunction);

        assertAll(
                () -> assertNotNull(m.get("fn2")),
                () -> assertNotNull(m.get("ln2")),
                () -> assertNull(m.get("xxx"))
        );
    }

    @Test
    public void testAccept3() {
        final Map<String, String> m = new HashMap<>();
        final FakerContext fakerContext = new FakerContext(new Faker());
        final Faker faker = fakerContext.faker();
        final Supplier<String> fnSupplier = () -> faker.name().firstName();
        final Supplier<String> lnSupplier = () -> faker.name().lastName();
        fakerContext
                .acceptFromSupplier((v) -> m.put("fn3", v), fnSupplier)
                .acceptFromSupplier((v) -> m.put("ln3", v), lnSupplier);

        assertAll(
                () -> assertNotNull(m.get("fn3")),
                () -> assertNotNull(m.get("ln3")),
                () -> assertNull(m.get("xxx"))
        );
    }

    @Test
    public void testAccept4() {
        final Map<String, String> m = new HashMap<>();
        final FakerContext fakerContext = new FakerContext(new Faker());
        final Faker faker = fakerContext.faker();
        fakerContext
                .acceptFromValue((v) -> m.put("fn4", v), faker.name().firstName())
                .acceptFromValue((v) -> m.put("ln4", v), faker.name().lastName());

        assertAll(
                () -> assertNotNull(m.get("fn4")),
                () -> assertNotNull(m.get("ln4")),
                () -> assertNull(m.get("xxx"))
        );
    }

    @Test
    public void hello() {
        final FakerContext fakerContext = new FakerContext(new Faker());
        final List<Map<String, String>> l = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final Map<String, String> m = new HashMap<>();
            fakerContext
                    .acceptFromFunction((v) -> m.put("fn", v), (f) -> f.name().firstName())
                    .acceptFromFunction((v) -> m.put("ln", v), (f) -> f.name().lastName());
            l.add(m);
        }
        String message = "" + l;
        assertAll(
                () -> assertEquals(10, l.size(), message),
                () -> assertEquals(10, l.stream().filter((m) -> m.containsKey("fn") && m.containsKey("ln")).count(), message),
                () -> assertEquals(0, l.stream().filter((m) -> m.containsKey("xx")).count(), message)
        );
    }
}
