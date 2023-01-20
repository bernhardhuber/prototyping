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
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import net.datafaker.Faker;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class MyFakerContextTest {

    @Test
    public void test_evaluate() {
        final Faker faker = new Faker();
        final Supplier<Faker> supplierFaker = () -> faker;

        final Function<Faker, String> fPersonFirstName = (f) -> f.name().firstName();
        final Function<Faker, String> fPersonLastName = (f) -> f.name().lastName();

        assertNotNull(MyFakerContext.evaluate(supplierFaker, (f) -> f.name().fullName()));
        assertNotNull(MyFakerContext.evaluate(supplierFaker, fPersonLastName));
        assertNotNull(MyFakerContext.evaluate(supplierFaker, (f) -> f.address().fullAddress()));
    }

    @Test
    public void test_evaluateList1_Names() {
        final Faker faker_de_AT = new Faker(Locale.forLanguageTag("de-AT"));
        final Supplier<Faker> supplierFaker_de_AT = () -> faker_de_AT;

        final Function<Faker, String> fPersonFirstName = (f) -> f.name().firstName();
        final Function<Faker, String> fPersonLastName = (f) -> f.name().lastName();

        final List<String> lFirstName = MyFakerContext.evaluateList1(supplierFaker_de_AT, 5, fPersonFirstName);
        System.out.printf("lFirstName %s%n", lFirstName);
        assertEquals(5, lFirstName.size());
        assertAll(
                () -> assertTrue(lFirstName.stream().allMatch((s) -> s.matches("[A-ZÄÖÜ][a-zäöüß]+")), "" + lFirstName)
        );

        final List<String> lLastName = MyFakerContext.evaluateList1(supplierFaker_de_AT, 5, fPersonLastName);
        System.out.printf("lLastName %s%n", lLastName);
        assertEquals(5, lLastName.size());
        assertAll(
                () -> assertTrue(lLastName.stream().allMatch((s) -> s.matches("[A-ZÄÖÜ][a-zäöüß]+")), "" + lLastName)
        );
    }

    @Test
    public void test_evaluateList1_Animals() {
        final Faker faker_de = new Faker(Locale.forLanguageTag("de"));
        final Supplier<Faker> supplierFaker_de = () -> faker_de;
        final Function<Faker, String> fAnimalName = (f) -> f.animal().name();

        final List<String> lAnimalName = MyFakerContext.evaluateList1(supplierFaker_de, 7, fAnimalName);
        System.out.printf("lAnimalName %s%n", lAnimalName);

        assertEquals(7, lAnimalName.size());
        assertAll(
                () -> assertTrue(lAnimalName.stream().allMatch((s) -> s.matches("[A-ZÄÖÜ][a-zäöüß]+")), "" + lAnimalName)
        );
    }

    static class MyFakerContext {

        static <T> T evaluate(Supplier<Faker> supp, Function<Faker, T> f) {
            T result = f.apply(supp.get());
            return result;
        }

        static <T> List<T> evaluateList1(Supplier<Faker> supp, int n, Function<Faker, T> f) {
            final List<T> result = new ArrayList<>();
            for (int i = 0; i < n; i += 1) {
                final T t = evaluate(supp, f);
                result.add(t);
            }
            return result;
        }

        static <T> List<T> evaluateList2(Supplier<Faker> supp, List<Function<Faker, T>> fList) {
            final List<T> result = new ArrayList<>();
            for (int i = 0; i < fList.size(); i += 1) {
                final Function<Faker, T> f = fList.get(i);
                final T t = evaluate(supp, f);
                result.add(t);
            }
            return result;
        }

        static <T> Map<String, T> evaluateMap1(Supplier<Faker> supp, Map<String, Function<Faker, T>> fMap) {
            final Map<String, T> result = new HashMap<>();
            for (Map.Entry<String, Function<Faker, T>> e : fMap.entrySet()) {
                final String k = e.getKey();
                final Function<Faker, T> f = e.getValue();
                final T t = evaluate(supp, f);
                result.put(k, t);
            }
            return result;
        }
    }
}
