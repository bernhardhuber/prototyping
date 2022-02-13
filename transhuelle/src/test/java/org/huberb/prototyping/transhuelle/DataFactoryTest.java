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

import org.huberb.prototyping.transhuelle.TransHuelle.Data;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class DataFactoryTest {

    @Test
    public void test_createDataSample1() {
        final DataFactory dataFactory = new DataFactory();
        final Data data = dataFactory.createDataSample1();
        assertEquals(3, data.groupsInList.size());
        assertEquals(0, data.groupsMergedList.size());
    }

    @Test
    public void test_createDataSample2() {
        final DataFactory dataFactory = new DataFactory();
        final Data data = dataFactory.createDataSample2();
        assertEquals(4, data.groupsInList.size());
        assertEquals(0, data.groupsMergedList.size());
    }

    @Test
    public void test_createDataSample3() {
        final DataFactory dataFactory = new DataFactory();
        final Data data = dataFactory.createDataSample3();
        assertEquals(5, data.groupsInList.size());
        assertEquals(0, data.groupsMergedList.size());
    }

    @Test
    public void test_createDataSample4() {
        final DataFactory dataFactory = new DataFactory();
        final Data data = dataFactory.createDataSample4();
        assertEquals(10, data.groupsInList.size());
        assertEquals(0, data.groupsMergedList.size());
    }

    @Test
    public void test_createDataSample5() {
        final DataFactory dataFactory = new DataFactory();
        final Data data = dataFactory.createDataSample5();
        assertEquals(7, data.groupsInList.size());
        assertEquals(0, data.groupsMergedList.size());
    }
}
