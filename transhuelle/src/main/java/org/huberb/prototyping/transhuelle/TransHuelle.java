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
import java.util.function.Predicate;
import java.util.logging.Logger;
import org.huberb.prototyping.transhuelle.Supports.MapBuilder;
import org.huberb.prototyping.transhuelle.Supports.SetBuilder;
import static org.huberb.prototyping.transhuelle.TransHuelle.Data.newSetBuilderVs;

/**
 *
 * @author berni3
 */
public class TransHuelle {

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

        public Data(String name, List<Map<String, Set<String>>> groupsInList, List<Map<String, Set<String>>> groupsMergedList) {
            this.name = name;
            this.groupsInList = groupsInList;
            this.groupsMergedList = groupsMergedList;
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

    static Set<String> newSetBuilderVs(String... vs) {
        final Set<String> result = new SetBuilder<String>().vs(vs).build();
        return result;
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
     * Implementation of algorithm merging groups
     */
    static class Algorithm {

        private static final Logger logger = Logger.getLogger(Algorithm.class.getName());

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
                // eg. ingname = "S1", ingmembers = [ "A1" , "A2" ]
                final String ingname = groupsInElement.get(Data.kName).iterator().next();
                final Set<String> ingmembers = groupsInElement.get(Data.kGroup);
                //---
                final List<Map<String, Object>> opList = new ArrayList<>();

                // try to find ingname, ingmembers in out.groupsMergedList
                if (out.groupsMergedList.isEmpty()) {
                    // merged list is empty insert [ ingname, ingmembers ]
                    final Map<String, Object> opInsert = new MapBuilder<String, Object>()
                            .kv("op", "insert")
                            .kv("ingname", ingname)
                            .kv("ingmembers", ingmembers)
                            .build();
                    opList.add(opInsert);
                } else {
                    // find a match for [ingname, ingmembers] in groupsMergedList
                    int matchCount = 0;
                    for (final Map<String, Set<String>> groupsMergedElement : out.groupsMergedList) {
                        final Set<String> outnames = groupsMergedElement.get(Data.kName);
                        final Set<String> outgmembers = groupsMergedElement.get(Data.kGroup);

                        final boolean match = ingmembers.stream().anyMatch((theIngmember) -> outgmembers.contains(theIngmember));
                        if (match) {
                            // Thers is already a group containing ingmember
                            if (matchCount == 0) {
                                // this the first match merge ingname, ingmember
                                final Map<String, Object> opMerge = new MapBuilder<String, Object>()
                                        .kv("op", "merge")
                                        .kv("ingname", newSetBuilderVs(ingname))
                                        .kv("ingmembers", ingmembers)
                                        .kv("outnames", outnames)
                                        .kv("outgmembers", outgmembers)
                                        .build();
                                opList.add(opMerge);
                            } else {
                                // This is another match
                                final Map<String, Object> opMergeMatchCount0 = opList.get(0);
                                // merge current match to first merge
                                final Map<String, Object> opMerge = new MapBuilder<String, Object>()
                                        .kv("op", "merge")
                                        .kv("ingname", outnames)
                                        .kv("ingmembers", outgmembers)
                                        .kv("outnames", opMergeMatchCount0.get("outnames"))
                                        .kv("outgmembers", opMergeMatchCount0.get("outgmembers"))
                                        .build();
                                opList.add(opMerge);

                                // delete current match
                                final Map<String, Object> opDelete = new MapBuilder<String, Object>()
                                        .kv("op", "delete")
                                        .kv("ingname", newSetBuilderVs(ingname))
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
                logger.info(String.format("Process opList: %s", opList));
                processOpList(out, opList);
            }
            final long endTime = System.currentTimeMillis();
            logger.info(String.format("evaluate start %d ms, end %d ms, duration %d ms",
                    startTime, endTime,
                    (endTime - startTime))
            );
            return out;
        }

        /**
         * Process opList
         *
         * @param out
         * @param opList
         */
        void processOpList(Data out, final List<Map<String, Object>> opList) {
            logger.info(String.format("Process opList: %s", opList));
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
                            .kv(Data.kName, new HashSet<>(Arrays.asList(op_ingname)))
                            .kv(Data.kGroup, new HashSet<>(op_ingmembers))
                            .build()
                    );
                } else if ("delete".equals(op)) {
                    // delete matched
                    final Set<String> op_ingname = (Set<String>) opElement.get("ingname");
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
    }

}
