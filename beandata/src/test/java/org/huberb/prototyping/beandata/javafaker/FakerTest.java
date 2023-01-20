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
import java.util.Locale;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class FakerTest {

    @Test
    @org.junit.jupiter.api.RepeatedTest(value = 5)
    public void test_evaluateList1_Names() {
        final Faker faker_de = new Faker(Locale.forLanguageTag("de"));

        final String nameWithMiddle = faker_de.name().nameWithMiddle();
        final String name = faker_de.name().name();
        final String firstName = faker_de.name().firstName();
        final String lastName = faker_de.name().lastName();
        final String fullName = faker_de.name().fullName();
        final String prefix = faker_de.name().prefix();
        final String suffix = faker_de.name().suffix();

        System.out.printf("names %s%n",
                Arrays.asList(
                        nameWithMiddle,
                        name,
                        firstName,
                        lastName,
                        fullName,
                        prefix,
                        suffix
                )
        );
    }

    @Test
    @org.junit.jupiter.api.RepeatedTest(value = 5)
    public void test_evaluateList1_Animals() {
        final Faker faker_de = new Faker(Locale.forLanguageTag("de"));
        final Faker faker_de_AT = new Faker(Locale.forLanguageTag("de-AT"));

        final String anmimal_de = faker_de.animal().name();
        final String anmimal_de_AT = faker_de_AT.animal().name();
        System.out.printf("names animal_de %s, animal_de_AT %s%n",
                anmimal_de,
                anmimal_de_AT);

        //        () -> assertTrue(lAnimalName.stream().allMatch((s) -> s.matches("[A-ZÄÖÜ][a-zäöüß]+")), "" + lAnimalName)
    }

    @Test
    public void hello() {
        final Faker faker_de = new Faker(Locale.forLanguageTag("de"));
        System.out.printf("lorem.sentence: %s%n",
                faker_de.lorem().sentence()
        );
    }
}
