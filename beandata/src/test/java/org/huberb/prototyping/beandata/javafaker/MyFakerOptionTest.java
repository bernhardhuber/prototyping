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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.datafaker.Faker;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class MyFakerOptionTest {

    final Faker faker = new Faker();

    @Test
    public void hello1() {
        for (String expression : Arrays.asList(
                //                "#{regexify '(a|b){2,3}'}",
                //                "#{regexify '\\.\\*\\?\\+'}",
                //                "#{bothify '????','false'}",
                //                "#{Name.first_name} #{Name.first_name} #{Name.last_name}",
                "#{number.number_between '1','10'}")) {

            String result = faker.expression(expression);
            System.out.printf("expression %s, result %s%n", expression, result);
        }
    }

    @Test
    public void give_random_int_0_5_then_value_is_between_0_5() {
        final int n = faker.number().numberBetween(0, 5);
        assertTrue(0 <= n && n <= 5, "" + n);
    }

    @Test
    public void testFakerOptions() {
        List<String> l = Arrays.asList("A1", "A2", "A3");
        assertTrue(l.contains(faker.options().option("A1", "A2", "A3")));
        assertTrue(l.contains(faker.options().nextElement(l)));
    }

    @Test()
    public void given_empty_options_list_then_IllegalArgumentException_is_thrown() {
        IllegalArgumentException theException = assertThrows(IllegalArgumentException.class,
                () -> {
                    assertEquals("", faker.options().nextElement(Arrays.asList()));
                });
        assertEquals("bound must be positive", theException.getMessage());
    }

    @Test
    public void given_a_non_empty_list_then_a_value_is_picked() {

        final MyFakerOption fakerOption = new MyFakerOption(faker);
        final List<String> l = Arrays.asList("A1", "A2", "A3");
        final String result = fakerOption.selectFrom(l);
        assertAll(
                () -> {
                    assertTrue(false
                            || result.contains("A1")
                            || result.contains("A2")
                            || result.contains("A3"));
                }
        );
    }

    @Test
    public void given_an_empty_list_then_null_value_is_picked() {
        final MyFakerOption fakerOption = new MyFakerOption(faker);
        final List<String> l = Collections.emptyList();
        final String result = fakerOption.selectFrom(l);
        assertNull(result);
    }

    @Test
    public void given_a_non_empty_list_of_string_then_a_value_is_picked() {
        final MyFakerOption fakerOption = new MyFakerOption(faker);
        final List<String> l = Arrays.asList("A1", "A2", "A3");
        final String result = fakerOption.selectStringFrom(l);
        assertAll(
                () -> {
                    assertTrue(false
                            || result.contains("A1")
                            || result.contains("A2")
                            || result.contains("A3"));
                }
        );
    }

    @Test
    public void given_an_empty_list_of_string_then_empty_string_picked() {
        final MyFakerOption fakerOption = new MyFakerOption(faker);
        final List<String> l = Collections.emptyList();
        final String result = fakerOption.selectStringFrom(l);
        assertEquals("", result, "" + result);
    }

    @Test
    public void given_int_value_then_value_is_between_0_int_value_minus1() {
        final MyFakerOption fakerOption = new MyFakerOption(faker);

        assertAll(
                () -> assertTrue(0 == fakerOption.optionIndex(-1)),
                () -> assertTrue(0 == fakerOption.optionIndex(0)),
                () -> assertTrue(0 == fakerOption.optionIndex(1)),
                () -> {
                    int result = fakerOption.optionIndex(2);
                    assertTrue(result >= 0 && result < 2, "" + result);
                },
                () -> {
                    int result = fakerOption.optionIndex(10);
                    assertTrue(result >= 0 && result < 10, "" + result);
                },
                () -> assertTrue(0 == fakerOption.optionIndex(1))
        );
    }

    static class MyFakerOption {

        private final Faker faker;

        public MyFakerOption(Faker faker) {
            this.faker = faker;
        }

        public String selectStringFrom(List<String> l) {
            final String result;
            final int lsize = l.size();
            final int index = optionIndex(lsize);
            if (index >= 0 && index < lsize) {
                result = l.get(index);
            } else {
                result = "";
            }
            return result;
        }

        public <T> T selectFrom(List<T> l) {
            final T result;
            final int lsize = l.size();
            final int index = optionIndex(lsize);
            if (index >= 0 && index < lsize) {
                result = l.get(index);
            } else {
                result = null;
            }
            return result;
        }

        public int optionIndex(int length) {
            int lengthNormalized = Math.max(0, length - 1);
            final int index = faker.random().nextInt(0, lengthNormalized);
            return index;
        }
    }
}
