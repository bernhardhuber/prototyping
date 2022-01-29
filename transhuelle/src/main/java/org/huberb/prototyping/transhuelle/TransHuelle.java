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
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import org.huberb.prototyping.transhuelle.TransHuelle.Supports.MapBuilder;
import org.huberb.prototyping.transhuelle.TransHuelle.Supports.SetBuilder;

/**
 *
 * @author berni3
 */
public class TransHuelle {

    public static void main(String[] args) {
        final List<Data> dinList = Arrays.asList(
                //                new DataFactory().createDataSample1(),
                //                new DataFactory().createDataSample2(),
                //                new DataFactory().createDataSample3(),
                //                new DataFactory().createDataSample4(),
                new DataFactory().createDataSample5()
        );
        for (int i = 0; i < dinList.size(); i++) {
            final Data din = dinList.get(i);
            System.out.println(String.format(">>>%n%s %d%n"
                    + "din: %s%n",
                    TransHuelle.class, i,
                    din.toString()
            ));
            final Data dout = new Algorithm().evaluate(din);
            System.out.println(String.format("%n%s %d%n"
                    + "din: %s%n"
                    + "dout: %s%n"
                    + "<<<%n",
                    TransHuelle.class, i,
                    din.toString(), dout.toString()
            ));
        }
    }

    /**
     * Wrapper for input, and out aka merged-data
     */
    static class Data {

        static final String kName = "name";
        static final String kGroup = "group";
        /**
         * name of data
         */
        final String name;
        /**
         * input list of "name": group-name, "group": group-members
         */
        final List<Map<String, Set<String>>> groupsInList;
        /**
         * merged list of "name": group-names, "group": group-members
         */
        final List<Map<String, Set<String>>> groupsMergedList;

        /**
         * Create empty data
         */
        public Data(String name) {
            this(name, new ArrayList<>(), new ArrayList<>());
        }

        public Data(String name, List<Map<String, Set<String>>> groupsIn, List<Map<String, Set<String>>> groupsMerged) {
            this.name = name;
            this.groupsInList = groupsIn;
            this.groupsMergedList = groupsMerged;
        }

        @Override
        public String toString() {
            final String toString = String.format("Data {%n"
                    + "name=%s%n"
                    + "groupsInList=%s,%n"
                    + "groupsMergedList=%s%n"
                    + "}",
                    this.name,
                    this.groupsInList, this.groupsMergedList
            );
            return toString;
        }

        public Map<String, Object> toMap() {
            final Map<String, Object> result = new MapBuilder<String, Object>()
                    .kv("name", this.name)
                    .kv("groupsInList", this.groupsInList)
                    .kv("groupsMergedList", this.groupsMergedList)
                    .build();
            return result;
        }
    }

    /**
     * Simple factory generating Data samples.
     */
    static class DataFactory {

        Data createDataSample1() {
            final Data data = new Data("dataSample1 merge+insert");
            final List<Map<String, Set<String>>> arg0 = new ArrayList<>();
            arg0.add(new MapBuilder<String, Set<String>>()
                    .kv(Data.kName, SetBuilder.newSetBuilderVs("S1"))
                    .kv(Data.kGroup, SetBuilder.newSetBuilderVs("A1", "A2"))
                    .build());
            arg0.add(new MapBuilder<String, Set<String>>()
                    .kv(Data.kName, new HashSet<>(Arrays.asList("S2")))
                    .kv(Data.kGroup, new HashSet<>(Arrays.asList("A2", "A3")))
                    .build());
            arg0.add(new MapBuilder<String, Set<String>>()
                    .kv(Data.kName, new HashSet<>(Arrays.asList("S3")))
                    .kv(Data.kGroup, new HashSet<>(Arrays.asList("A4")))
                    .build());
            data.groupsInList.addAll(arg0);
            return data;
        }

        Data createDataSample2() {
            final Data data = new Data("dataSample2 merge+insert+merge");
            final List<Map<String, Set<String>>> arg0 = new ArrayList<>();
            arg0.add(new MapBuilder<String, Set<String>>()
                    .kv(Data.kName, new HashSet<>(Arrays.asList("S1")))
                    .kv(Data.kGroup, new HashSet<>(Arrays.asList("A1", "A2")))
                    .build());
            arg0.add(new MapBuilder<String, Set<String>>()
                    .kv(Data.kName, new HashSet<>(Arrays.asList("S2")))
                    .kv(Data.kGroup, new HashSet<>(Arrays.asList("A2", "A3")))
                    .build());
            arg0.add(new MapBuilder<String, Set<String>>()
                    .kv(Data.kName, new HashSet<>(Arrays.asList("S3")))
                    .kv(Data.kGroup, new HashSet<>(Arrays.asList("A4")))
                    .build());
            arg0.add(new MapBuilder<String, Set<String>>()
                    .kv(Data.kName, new HashSet<>(Arrays.asList("S4")))
                    .kv(Data.kGroup, new HashSet<>(Arrays.asList("A1", "A4")))
                    .build());
            data.groupsInList.addAll(arg0);
            return data;
        }

        Data createDataSample3() {
            final Data data = new Data("dataSample3 no-merge-only-insert");
            final List<Map<String, Set<String>>> arg0 = new ArrayList<>();
            for (int i = 0; i < 10; i += 2) {
                arg0.add(new MapBuilder<String, Set<String>>()
                        .kv(Data.kName, new HashSet<>(Arrays.asList("S" + i)))
                        .kv(Data.kGroup, new HashSet<>(Arrays.asList("A" + i, "A" + (i + 1))))
                        .build());
            }
            data.groupsInList.addAll(arg0);
            return data;
        }

        Data createDataSample4() {
            final Data data = new Data("dataSample4 merge-alleven-insert-allodd");
            final List<Map<String, Set<String>>> arg0 = new ArrayList<>();
            for (int i = 0; i < 10; i += 1) {
                if (i % 2 == 0) {
                    arg0.add(new MapBuilder<String, Set<String>>()
                            .kv(Data.kName, new HashSet<>(Arrays.asList("S" + i)))
                            .kv(Data.kGroup, new HashSet<>(Arrays.asList("A" + i, "A" + (i + 2))))
                            .build());
                } else {
                    arg0.add(new MapBuilder<String, Set<String>>()
                            .kv(Data.kName, new HashSet<>(Arrays.asList("S" + i)))
                            .kv(Data.kGroup, new HashSet<>(Arrays.asList("A" + i)))
                            .build());
                }
            }
            data.groupsInList.addAll(arg0);
            return data;
        }

        Data createDataSample5() {
            final Data data = new Data("dataSample5 merge+insert+merge");
            final List<Map<String, Set<String>>> arg0 = new ArrayList<>();
            arg0.add(new MapBuilder<String, Set<String>>()
                    .kv(Data.kName, new HashSet<>(Arrays.asList("S1")))
                    .kv(Data.kGroup, new HashSet<>(Arrays.asList("A1", "A2")))
                    .build());
            arg0.add(new MapBuilder<String, Set<String>>()
                    .kv(Data.kName, new HashSet<>(Arrays.asList("S2")))
                    .kv(Data.kGroup, new HashSet<>(Arrays.asList("A2", "A3")))
                    .build());
            arg0.add(new MapBuilder<String, Set<String>>()
                    .kv(Data.kName, new HashSet<>(Arrays.asList("S3")))
                    .kv(Data.kGroup, new HashSet<>(Arrays.asList("A4")))
                    .build());
            arg0.add(new MapBuilder<String, Set<String>>()
                    .kv(Data.kName, new HashSet<>(Arrays.asList("S4")))
                    .kv(Data.kGroup, new HashSet<>(Arrays.asList("A5")))
                    .build());
            arg0.add(new MapBuilder<String, Set<String>>()
                    .kv(Data.kName, new HashSet<>(Arrays.asList("S5")))
                    .kv(Data.kGroup, new HashSet<>(Arrays.asList("A4", "A6")))
                    .build());
            arg0.add(new MapBuilder<String, Set<String>>()
                    .kv(Data.kName, new HashSet<>(Arrays.asList("S6")))
                    .kv(Data.kGroup, new HashSet<>(Arrays.asList("A5", "A7")))
                    .build());
            arg0.add(new MapBuilder<String, Set<String>>()
                    .kv(Data.kName, new HashSet<>(Arrays.asList("S7")))
                    .kv(Data.kGroup, new HashSet<>(Arrays.asList("A1", "A6", "A7")))
                    .build());
            data.groupsInList.addAll(arg0);
            return data;
        }
    }

    /**
     * Implementation of algorithm merging groups
     */
    static class Algorithm {

        /**
         *
         * @param in
         * @return
         */
        Data evaluate(final Data in) {
            final long startTime = System.currentTimeMillis();
            final Data out = new Data(in.name);
            out.groupsInList.addAll(in.groupsInList);

            //---
            for (final Map<String, Set<String>> groupsInElement : out.groupsInList) {
                // Consider ingname, and ingmembers
                final String ingname = groupsInElement.get(Data.kName).iterator().next();
                final Set<String> ingmembers = groupsInElement.get(Data.kGroup);
                //---
                final List<Map<String, Object>> opList = new ArrayList<>();
                if (out.groupsMergedList.isEmpty()) {
                    // merged list empty insert [ ingname, ingmembers ]
                    final Map<String, Object> opInsert = new MapBuilder<String, Object>()
                            .kv("op", "insert")
                            .kv("ingname", ingname)
                            .kv("ingmembers", ingmembers)
                            .build();
                    opList.add(opInsert);
                } else {
                    // find a match for [ingname, ingmembers] in mergedList
                    int matchCount = 0;
                    for (final Map<String, Set<String>> groupsMergedElement : out.groupsMergedList) {
                        final Set<String> outnames = groupsMergedElement.get(Data.kName);
                        final Set<String> outgmembers = groupsMergedElement.get(Data.kGroup);

                        final boolean match = ingmembers.stream().anyMatch((ingmember) -> outgmembers.contains(ingmember));
                        if (match) {
                            // Thers is already a group containing ingmember
                            if (matchCount == 0) {
                                // this the first match merge ingname, ingmember
                                final Map<String, Object> opMerge = new MapBuilder<String, Object>()
                                        .kv("op", "merge")
                                        .kv("ingname", SetBuilder.newSetBuilderVs(ingname))
                                        .kv("ingmembers", ingmembers)
                                        .kv("outnames", outnames)
                                        .kv("outgmembers", outgmembers)
                                        .build();
                                opList.add(opMerge);
                            } else {
                                // This is another match
                                // we have alreay merged
                                final Map<String, Object> opMergeMatchCount0 = opList.get(0);
                                final Map<String, Object> opMerge = new MapBuilder<String, Object>()
                                        .kv("op", "merge")
                                        .kv("ingname", outnames)
                                        .kv("ingmembers", outgmembers)
                                        .kv("outnames", opMergeMatchCount0.get("outnames"))
                                        .kv("outgmembers", opMergeMatchCount0.get("outgmembers"))
                                        .build();
                                opList.add(opMerge);

                                // 
                                final Map<String, Object> opDelete = new MapBuilder<String, Object>()
                                        .kv("op", "delete")
                                        .kv("ingname", SetBuilder.newSetBuilderVs(ingname))
                                        .kv("ingmembers", ingmembers)
                                        .kv("outnames", outnames)
                                        .kv("outgmembers", outgmembers)
                                        .build();
                                opList.add(opDelete);
                            }
                            matchCount += 1;
                        }
                    }
                    // PostProcess matchCount=0
                    // no matching insert ingname, ingmembers into merged-result
                    if (matchCount == 0) {
                        final Map<String, Object> opInsert = new MapBuilder<String, Object>()
                                .kv("op", "insert")
                                .kv("ingname", ingname)
                                .kv("ingmembers", ingmembers)
                                .build();
                        opList.add(opInsert);
                    }
                }
                // Process opList
                System.out.println(String.format("Process opList: %s", opList));
                for (final Map<String, Object> opElement : opList) {

                    final String op = (String) opElement.getOrDefault("op", "-");
                    if ("merge".equals(op)) {
                        // can merge
                        final Set<String> op_ingname = (Set<String>) opElement.get("ingname");
                        final Set<String> op_ingmembers = (Set<String>) opElement.get("ingmembers");
                        final Set<String> op_outnames = (Set<String>) opElement.get("outnames");
                        final Set<String> op_outgmembers = (Set<String>) opElement.get("outgmembers");

                        op_outnames.addAll(op_ingname);
                        op_outgmembers.addAll(op_ingmembers);
                    } else if ("insert".equals(op)) {
                        // add in to out
                        final String op_ingname = (String) opElement.get("ingname");
                        final Set<String> op_ingmembers = (Set<String>) opElement.get("ingmembers");

                        out.groupsMergedList.add(new MapBuilder<String, Set<String>>()
                                .kv(Data.kName, new HashSet<String>(Arrays.asList(op_ingname)))
                                .kv(Data.kGroup, new HashSet<>(op_ingmembers))
                                .build()
                        );
                    } else if ("delete".equals(op)) {
                        // delete matched
                        final  Set<String>  op_ingname = (Set<String>) opElement.get("ingname");
                        final Set<String> op_ingmembers = (Set<String>) opElement.get("ingmembers");
                        final Set<String> op_outnames = (Set<String>) opElement.get("outnames");
                        final Set<String> op_outgmembers = (Set<String>) opElement.get("outgmembers");
                        final Predicate<Map<String, Set<String>>> p = (m
                                -> m.get(Data.kName) == op_outnames
                                && m.get(Data.kGroup) == op_outgmembers);
                        out.groupsMergedList.removeIf(p);
                    }
                }
            }
            final long endTime = System.currentTimeMillis();
            System.out.println(String.format("evaluate start %d ms, end %d ms, duration %d ms",
                    startTime, endTime,
                    (endTime - startTime))
            );
            return out;
        }
    }

    /**
     * Supporting classes
     */
    static class Supports {

        /**
         * Supports building a Map
         */
        static class MapBuilder<K, V> {

            final Map<K, V> m = new LinkedHashMap<>();

            MapBuilder<K, V> kv(K k, V v) {
                this.m.put(k, v);
                return this;
            }

            Map<K, V> build() {
                return this.m;
            }

        }

        static class SetBuilder<V> {

            static Set<String> newSetBuilderVs(String... vs) {
                final Set<String> result = new SetBuilder<String>()
                        .vs(vs)
                        .build();
                return result;
            }

            final Set<V> s = new LinkedHashSet<>();

            SetBuilder<V> v(Collection<V> vc) {
                this.s.addAll(vc);
                return this;
            }

            SetBuilder<V> v(V v) {
                this.s.add(v);
                return this;
            }

            SetBuilder<V> vs(V... vs) {
                for (int i = 0; i < vs.length; i++) {
                    this.s.add(vs[i]);
                }
                return this;
            }

            Set<V> build() {
                return this.s;
            }
        }

    }
}
