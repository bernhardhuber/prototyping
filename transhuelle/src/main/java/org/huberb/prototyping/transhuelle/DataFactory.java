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
import org.huberb.prototyping.transhuelle.Supports.MapBuilder;
import org.huberb.prototyping.transhuelle.TransHuelle.Data;
import static org.huberb.prototyping.transhuelle.TransHuelle.Data.newSetBuilderVs;

/**
 * Simple factory generating {@link Data} samples.
 */
class DataFactory {

    Data createDataSample1() {
        final TransHuelle.Data data = new TransHuelle.Data("dataSample1 merge+insert");
        final List<Map<String, Set<String>>> arg0 = new ArrayList<>();
        arg0.add(new MapBuilder<String, Set<String>>()
                .kv(TransHuelle.Data.kName, newSetBuilderVs("S1"))
                .kv(TransHuelle.Data.kGroup, newSetBuilderVs("A1", "A2"))
                .build());
        arg0.add(new MapBuilder<String, Set<String>>()
                .kv(TransHuelle.Data.kName, new HashSet<>(Arrays.asList("S2")))
                .kv(TransHuelle.Data.kGroup, new HashSet<>(Arrays.asList("A2", "A3")))
                .build());
        arg0.add(new MapBuilder<String, Set<String>>()
                .kv(TransHuelle.Data.kName, new HashSet<>(Arrays.asList("S3")))
                .kv(TransHuelle.Data.kGroup, new HashSet<>(Arrays.asList("A4")))
                .build());
        data.groupsInList.addAll(arg0);
        return data;
    }

    Data createDataSample2() {
        final TransHuelle.Data data = new TransHuelle.Data("dataSample2 merge+insert+merge");
        final List<Map<String, Set<String>>> arg0 = new ArrayList<>();
        arg0.add(new MapBuilder<String, Set<String>>()
                .kv(TransHuelle.Data.kName, new HashSet<>(Arrays.asList("S1")))
                .kv(TransHuelle.Data.kGroup, new HashSet<>(Arrays.asList("A1", "A2")))
                .build());
        arg0.add(new MapBuilder<String, Set<String>>()
                .kv(TransHuelle.Data.kName, new HashSet<>(Arrays.asList("S2")))
                .kv(TransHuelle.Data.kGroup, new HashSet<>(Arrays.asList("A2", "A3")))
                .build());
        arg0.add(new MapBuilder<String, Set<String>>()
                .kv(TransHuelle.Data.kName, new HashSet<>(Arrays.asList("S3")))
                .kv(TransHuelle.Data.kGroup, new HashSet<>(Arrays.asList("A4")))
                .build());
        arg0.add(new MapBuilder<String, Set<String>>()
                .kv(TransHuelle.Data.kName, new HashSet<>(Arrays.asList("S4")))
                .kv(TransHuelle.Data.kGroup, new HashSet<>(Arrays.asList("A1", "A4")))
                .build());
        data.groupsInList.addAll(arg0);
        return data;
    }

    Data createDataSample3() {
        final TransHuelle.Data data = new TransHuelle.Data("dataSample3 no-merge-only-insert");
        final List<Map<String, Set<String>>> arg0 = new ArrayList<>();
        for (int i = 0; i < 10; i += 2) {
            arg0.add(new MapBuilder<String, Set<String>>()
                    .kv(TransHuelle.Data.kName, new HashSet<>(Arrays.asList("S" + i)))
                    .kv(TransHuelle.Data.kGroup, new HashSet<>(Arrays.asList("A" + i, "A" + (i + 1))))
                    .build());
        }
        data.groupsInList.addAll(arg0);
        return data;
    }

    Data createDataSample4() {
        final TransHuelle.Data data = new TransHuelle.Data("dataSample4 merge-alleven-insert-allodd");
        final List<Map<String, Set<String>>> arg0 = new ArrayList<>();
        for (int i = 0; i < 10; i += 1) {
            if (i % 2 == 0) {
                arg0.add(new MapBuilder<String, Set<String>>()
                        .kv(TransHuelle.Data.kName, new HashSet<>(Arrays.asList("S" + i)))
                        .kv(TransHuelle.Data.kGroup, new HashSet<>(Arrays.asList("A" + i, "A" + (i + 2))))
                        .build());
            } else {
                arg0.add(new MapBuilder<String, Set<String>>()
                        .kv(TransHuelle.Data.kName, new HashSet<>(Arrays.asList("S" + i)))
                        .kv(TransHuelle.Data.kGroup, new HashSet<>(Arrays.asList("A" + i)))
                        .build());
            }
        }
        data.groupsInList.addAll(arg0);
        return data;
    }

    Data createDataSample5() {
        final TransHuelle.Data data = new TransHuelle.Data("dataSample5 merge+insert+merge");
        final List<Map<String, Set<String>>> arg0 = new ArrayList<>();
        arg0.add(new MapBuilder<String, Set<String>>()
                .kv(TransHuelle.Data.kName, new HashSet<>(Arrays.asList("S1")))
                .kv(TransHuelle.Data.kGroup, new HashSet<>(Arrays.asList("A1", "A2")))
                .build());
        arg0.add(new MapBuilder<String, Set<String>>()
                .kv(TransHuelle.Data.kName, new HashSet<>(Arrays.asList("S2")))
                .kv(TransHuelle.Data.kGroup, new HashSet<>(Arrays.asList("A2", "A3")))
                .build());
        arg0.add(new MapBuilder<String, Set<String>>()
                .kv(TransHuelle.Data.kName, new HashSet<>(Arrays.asList("S3")))
                .kv(TransHuelle.Data.kGroup, new HashSet<>(Arrays.asList("A4")))
                .build());
        arg0.add(new MapBuilder<String, Set<String>>()
                .kv(TransHuelle.Data.kName, new HashSet<>(Arrays.asList("S4")))
                .kv(TransHuelle.Data.kGroup, new HashSet<>(Arrays.asList("A5")))
                .build());
        arg0.add(new MapBuilder<String, Set<String>>()
                .kv(TransHuelle.Data.kName, new HashSet<>(Arrays.asList("S5")))
                .kv(TransHuelle.Data.kGroup, new HashSet<>(Arrays.asList("A4", "A6")))
                .build());
        arg0.add(new MapBuilder<String, Set<String>>()
                .kv(TransHuelle.Data.kName, new HashSet<>(Arrays.asList("S6")))
                .kv(TransHuelle.Data.kGroup, new HashSet<>(Arrays.asList("A5", "A7")))
                .build());
        arg0.add(new MapBuilder<String, Set<String>>()
                .kv(TransHuelle.Data.kName, new HashSet<>(Arrays.asList("S7")))
                .kv(TransHuelle.Data.kGroup, new HashSet<>(Arrays.asList("A1", "A6", "A7")))
                .build());
        data.groupsInList.addAll(arg0);
        return data;
    }

}
