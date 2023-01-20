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

import org.huberb.prototyping.beandata.javafaker.Maps.NamedMap;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class NamedMapTest {

    @Test
    public void given_names_AB_and_AValue1_is_defined_then_AValue1_is_stored() {
        final NamedMap nm = new NamedMap("A", "B");
        assertEquals(0, nm.size());
        nm.put("A", "AValue1");
        assertAll(
                () -> assertEquals("AValue1", nm.get("A")),
                () -> assertEquals(null, nm.get("B")),
                () -> assertEquals(null, nm.get("C"))
        );
    }

    @Test
    public void given_names_AB_and_ABValue1_are_defined_then_ABValue1_are_stored() {
        final NamedMap nm = new NamedMap("A", "B");
        assertEquals(0, nm.size());
        nm.put("A", "AValue1");
        nm.put("B", "BValue1");
        assertAll(
                () -> assertEquals("AValue1", nm.get("A")),
                () -> assertEquals("BValue1", nm.get("B")),
                () -> assertEquals(null, nm.get("C"))
        );
    }

    @Test
    public void given_names_dups_then_dups_are_ignored() {
        final NamedMap nm = new NamedMap("A", "B", "A");
        assertEquals(0, nm.size());
        nm.put("A", "AValue1");
        nm.put("B", "BValue1");
        assertAll(
                () -> assertEquals("AValue1", nm.get("A")),
                () -> assertEquals("BValue1", nm.get("B")),
                () -> assertEquals(null, nm.get("C"))
        );
    }

    @Test
    public void given_some_names_and_map_is_cleared_then_map_is_empty() {
        final NamedMap nm = new NamedMap("A", "B", "A");
        assertEquals(0, nm.size());
        nm.put("A", "AValue1");
        nm.put("B", "BValue1");
        assertAll(
                () -> assertEquals("AValue1", nm.get("A")),
                () -> assertEquals("BValue1", nm.get("B")),
                () -> assertEquals(null, nm.get("C")),
                () -> assertEquals(2, nm.size())
        );
        nm.clear();
        assertAll(
                () -> assertEquals(null, nm.get("A")),
                () -> assertEquals(null, nm.get("B")),
                () -> assertEquals(null, nm.get("C")),
                () -> assertEquals(0, nm.size())
        );
    }
}
