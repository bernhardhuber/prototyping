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

import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class FakerDatesCollectionCsvJsonTest {

    @Test
    public void hello() {
        final FakerDatesCollectionCsvJson newClass = new FakerDatesCollectionCsvJson();
        System.out.printf("collections: %s%n", newClass.firstnameCollection());
        System.out.printf("json: %s%n", newClass.json());
        System.out.printf("csv: %s%n", newClass.csv());
        System.out.printf("dates: %s%n", newClass.dateCollection());
    }

}
