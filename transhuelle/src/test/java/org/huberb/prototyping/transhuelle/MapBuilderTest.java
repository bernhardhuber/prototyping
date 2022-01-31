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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.huberb.prototyping.transhuelle.Supports.MapBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

/**
 *
 * @author berni3
 */
public class MapBuilderTest {

    @Test
    public void given_building_a_map_K1V1_K2V2_K3_V3_then_the_map_shall_contain_K1V1_K2V2_K3V3() {
        MapBuilder<String, String> instance = new MapBuilder<>();
        instance
                .kv("K1", "V1")
                .kv("K2", "V2")
                .kv("K3", "V3");
        final Map<String, String> m = instance.build();
        assertEquals(3, m.size());

        for (Arguments arg : Arrays.asList(
                Arguments.of("K1", "V1"),
                Arguments.of("K2", "V2"),
                Arguments.of("K3", "V3"))) {

            final String k = (String) arg.get()[0];
            final String v = (String) arg.get()[1];
            assertEquals(v, m.get(k));

        }

        final List<String> l = m.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map((e) -> "" + e.getKey() + ":" + e.getValue())
                .collect(Collectors.toList());

        assertEquals(3, l.size());
        assertEquals("[K1:V1, K2:V2, K3:V3]", l.toString());
    }
}
