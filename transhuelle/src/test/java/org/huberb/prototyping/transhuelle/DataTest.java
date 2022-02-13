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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import org.huberb.prototyping.transhuelle.Supports.MapBuilder;
import org.huberb.prototyping.transhuelle.TransHuelle.Data;
import static org.huberb.prototyping.transhuelle.TransHuelle.Data.newSetBuilderVs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author berni3
 */
public class DataTest {

    @ParameterizedTest
    @MethodSource("given_an_empty_data")
    public void given_an_empty_data_then_verify_it() {
        final Data instance = new Data("X");
        assertEquals("X", instance.name);
        assertEquals(0, instance.groupsInList.size());
        assertEquals(0, instance.groupsMergedList.size());
    }

    public static Stream<Data> given_an_empty_data() {
        final Stream<Data> result = Stream.of(
                new Data("X"),
                new Data("X",
                        new ArrayList<>(),
                        new ArrayList<>()
                )
        );
        return result;
    }

    @Test
    public void xxx() {
        final Data instance = new Data("X");
        final List<Map<String, Set<String>>> groupsInList = new ArrayList<>();
        groupsInList.add(new MapBuilder<String, Set<String>>()
                .kv(TransHuelle.Data.kName, newSetBuilderVs("S1"))
                .kv(TransHuelle.Data.kGroup, newSetBuilderVs("A1", "A2"))
                .build());
        groupsInList.add(new MapBuilder<String, Set<String>>()
                .kv(TransHuelle.Data.kName, new HashSet<>(Arrays.asList("S2")))
                .kv(TransHuelle.Data.kGroup, new HashSet<>(Arrays.asList("A2", "A3")))
                .build());
        groupsInList.add(new MapBuilder<String, Set<String>>()
                .kv(TransHuelle.Data.kName, new HashSet<>(Arrays.asList("S3")))
                .kv(TransHuelle.Data.kGroup, new HashSet<>(Arrays.asList("A4")))
                .build());
        instance.groupsInList.addAll(groupsInList);

        //---
        final List<Map<String, Set<String>>> groupsMergedList = new ArrayList<>();
        groupsInList.add(new MapBuilder<String, Set<String>>()
                .kv(TransHuelle.Data.kName, newSetBuilderVs("S1", "S2"))
                .kv(TransHuelle.Data.kGroup, newSetBuilderVs("A1", "A2", "A2", "A3"))
                .build());
        groupsInList.add(new MapBuilder<String, Set<String>>()
                .kv(TransHuelle.Data.kName, new HashSet<>(Arrays.asList("S3")))
                .kv(TransHuelle.Data.kGroup, new HashSet<>(Arrays.asList("A4")))
                .build());
        instance.groupsMergedList.addAll(groupsMergedList);
        {
            final String expectedToString = "Data {\n"
                    + "name=X\n"
                    + "groupsInList=[{name=[S1], group=[A1, A2]}, {name=[S2], group=[A2, A3]}, {name=[S3], group=[A4]}],\n"
                    + "groupsMergedList=[]\n"
                    + "}";
            assertEquals(cleanWs(expectedToString), cleanWs(instance.toString()));
        }
        {
            final String expectedToMap = "{groupsInList=["
                    + "{name=[S1], group=[A1, A2]}, "
                    + "{name=[S2], group=[A2, A3]}, "
                    + "{name=[S3], group=[A4]}], "
                    + "name=X, "
                    + "groupsMergedList=[]}";
            assertEquals(cleanWs(expectedToMap), cleanWs(instance.toMap().toString()));
        }
    }

    String cleanWs(String s) {
        final String cleanWs = s.replaceAll("[ \r\n\t]", "");
        return cleanWs;
    }
}
