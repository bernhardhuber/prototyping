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
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import net.datafaker.Address;
import net.datafaker.FakeCollection;
import net.datafaker.Faker;
import net.datafaker.Name;
import net.datafaker.fileformats.Csv.Column;
import net.datafaker.fileformats.Format;

/**
 *
 * @author berni3
 */
class FakerDatesCollectionCsvJson {

    List<String> dateCollection() {
        final Faker faker = new Faker();
        final List<String> result = Arrays.asList(
                faker.date().future(1, TimeUnit.HOURS, "YYYY MM.dd HH:mm:ss"),
                faker.date().past(1, TimeUnit.HOURS, "YYYY-MM-dd HH:mm:ss"),
                faker.date().birthday(1, 99, "YYYY/MM/dd"));
        return result;
    }

    List<String> firstnameCollection() {
        final Faker faker = Faker.instance();
        final FakeCollection<String> fc = new FakeCollection.Builder<String>()
                .suppliers(() -> faker.name().firstName())
                .minLen(5)
                .maxLen(5)
                .build();
        return fc.get();
    }

    String csv() {
        final Faker faker = Faker.instance(Locale.GERMAN);
        final String result = Format.toCsv(
                Column.of("fn", () -> faker.name().firstName()),
                Column.of("ln", () -> faker.name().lastName()))
                .separator(";")
                .limit(5)
                .build()
                .get();
        return result;
    }

    String csvDog() {
        final Faker faker = Faker.instance(Locale.GERMAN);
        final String result = Format.toCsv(new FakeCollection.Builder<String>()
                .faker(faker)
                .suppliers(() -> faker.dog().name())
                .maxLen(5)
                .minLen(5)
                .build()
        ).headers(() -> "A", () -> "B", () -> "C")
                .columns(() -> faker.dog().name(), () -> faker.dog().breed(), () -> faker.dog().size())
                .build()
                .get();
        return result;
    }

    String json() {
        final Faker faker = Faker.instance();
        final String result = Format.toJson()
                .set("fn", () -> faker.name().firstName())
                .set("ln", () -> faker.name().lastName())
                .set("strings", () -> "X")
                .build()
                .generate();
        return result;
    }

    String json2() {
        final Faker faker = Faker.instance();
        final String json = Format.toJson(
                new FakeCollection.Builder<Name>()
                        .suppliers(() -> faker.name())
                        .maxLen(2)
                        .minLen(2)
                        .build())
                .set("firstName", Name::firstName)
                .set("lastName", Name::lastName)
                .set("address", Format.toJson(
                        new FakeCollection.Builder<Address>()
                                .suppliers(() -> faker.address())
                                .maxLen(1)
                                .minLen(1)
                                .build())
                        .set("country", Address::country)
                        .set("city", Address::city)
                        .set("zipcode", Address::zipCode)
                        .set("streetAddress", Address::streetAddress)
                        .build())
                .set("phones", t -> {
                    return new FakeCollection.Builder<String>()
                            .suppliers(() -> faker.phoneNumber().phoneNumber())
                            .maxLen(3)
                            .build();
                })
                .build()
                .generate();

        return json;
    }

}
