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

import java.util.HashMap;
import java.util.Map;
import org.huberb.prototyping.beandata.javafaker.Maps.FixedKeyMap;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class FixedKeyMapTest {

    @Test
    public void testPut() {
        final FixedKeyMap<String> fixedKeyMap = new FixedKeyMap<String>("K1", "K2");
        fixedKeyMap.put("K1", "k1Value1");
        fixedKeyMap.put("K2", "k2Value1");
        fixedKeyMap.put("K3", "k3Value1");

        assertAll(
                () -> assertEquals(2, fixedKeyMap.size()),
                () -> assertEquals("k1Value1", fixedKeyMap.get("K1")),
                () -> assertEquals("k2Value1", fixedKeyMap.get("K2")),
                () -> assertEquals(null, fixedKeyMap.get("K3"))
        );
    }

    @Test
    public void testPutAll() {
        final FixedKeyMap<String> fixedKeyMap = new FixedKeyMap<String>("K1", "K2");
        Map<String, String> m = new HashMap<>();
        m.put("K1", "k1Value1");
        m.put("K2", "k2Value1");
        m.put("K3", "k3Value1");

        fixedKeyMap.putAll(m);

        assertAll(
                () -> assertEquals(2, fixedKeyMap.size()),
                () -> assertEquals("k1Value1", fixedKeyMap.get("K1")),
                () -> assertEquals("k2Value1", fixedKeyMap.get("K2")),
                () -> assertEquals(null, fixedKeyMap.get("K3"))
        );
    }

    @Test
    public void testPutIfAbset() {
        final FixedKeyMap<String> fixedKeyMap = new FixedKeyMap<String>("K1", "K2");
        fixedKeyMap.putIfAbsent("K1", "k1Value1");
        fixedKeyMap.putIfAbsent("K2", "k2Value1");
        fixedKeyMap.putIfAbsent("K3", "k3Value1");

        assertAll(
                () -> assertEquals(2, fixedKeyMap.size()),
                () -> assertEquals("k1Value1", fixedKeyMap.get("K1")),
                () -> assertEquals("k2Value1", fixedKeyMap.get("K2")),
                () -> assertEquals(null, fixedKeyMap.get("K3"))
        );
    }

    @Test
    public void testPutNotRestricted() {
        final FixedKeyMap<String> fixedKeyMap = new FixedKeyMap<String>(true, "K1", "K2");
        fixedKeyMap.put("K1", "k1Value1");
        fixedKeyMap.put("K2", "k2Value1");
        fixedKeyMap.put("K3", "k3Value1");

        assertAll(
                () -> assertEquals(2, fixedKeyMap.size()),
                () -> assertEquals("k1Value1", fixedKeyMap.get("K1")),
                () -> assertEquals("k2Value1", fixedKeyMap.get("K2")),
                () -> assertEquals(null, fixedKeyMap.get("K3"))
        );

        fixedKeyMap.setRestricted(false);
        fixedKeyMap.put("K1", "k1Value2");
        fixedKeyMap.put("K2", "k2Value2");
        fixedKeyMap.put("K3", "k3Value2");
        assertAll(
                () -> assertEquals(3, fixedKeyMap.size()),
                () -> assertEquals("k1Value2", fixedKeyMap.get("K1")),
                () -> assertEquals("k2Value2", fixedKeyMap.get("K2")),
                () -> assertEquals("k3Value2", fixedKeyMap.get("K3"))
        );
    }
}
