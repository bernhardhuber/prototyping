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
import org.huberb.prototyping.transhuelle.TransHuelle.Algorithm2.OpRecordProcessor.OpRecord;
import org.huberb.prototyping.transhuelle.TransHuelle.Algorithm2.OpRecordProcessor.OpRecordDelete;
import org.huberb.prototyping.transhuelle.TransHuelle.Algorithm2.OpRecordProcessor.OpRecordInsert;
import org.huberb.prototyping.transhuelle.TransHuelle.Algorithm2.OpRecordProcessor.OpRecordMerge;
import static org.huberb.prototyping.transhuelle.TransHuelle.Data.newSetBuilderVs;

/**
 *
 * @author berni3
 */
public class TransHuelle {

    /**
     * Wrapper for in-, and out-data (aka merged-data).
     */
    static class Data {

        /**
         * Map key name define the name of a group
         */
        static final String kName = "name";
        /**
         * Map key name defining the members of a group
         */
        static final String kGroup = "group";
        /**
         * name of this data
         */
        final String name;
        /**
         * Input list of "name": group-name, "group": group-members
         */
        final List<Map<String, Set<String>>> groupsInList;
        /**
         * Merged list of "name": group-names, "group": group-members
         */
        final List<Map<String, Set<String>>> groupsMergedList;

        /**
         * Create empty data
         */
        public Data(String name) {
            this(name, new ArrayList<>(), new ArrayList<>());
        }

        public Data(String name,
                List<Map<String, Set<String>>> groupsInList,
                List<Map<String, Set<String>>> groupsMergedList) {
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
            final Set<String> result = new SetBuilder<String>()
                    .vs(vs)
                    .build();
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

    static interface IAlgorithm {

        /**
         * Process in data, and return processed data.
         * <p>
         * The in data is not changed.
         * <p>
         * The returned data has merged in-group-names, and in-groups.
         *
         * @param in
         * @return
         */
        Data evaluate(final Data in);
    }

    /**
     * Implementation of algorithm merging groups.
     */
    static class Algorithm1 implements IAlgorithm {

        private static final Logger logger = Logger.getLogger(Algorithm1.class.getName());

        private static final String DELETE_OP = "delete";
        private static final String MERGE_OP = "merge";
        private static final String INSERT_OP = "insert";

        /**
         *
         * @param in
         * @return
         */
        @Override
        public Data evaluate(final Data in) {
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
                                    .kv("op", MERGE_OP)
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
                                    .kv("op", MERGE_OP)
                                    .kv("ingname", outnames)
                                    .kv("ingmembers", outgmembers)
                                    .kv("outnames", opMergeMatchCount0.get("outnames"))
                                    .kv("outgmembers", opMergeMatchCount0.get("outgmembers"))
                                    .build();
                            opList.add(opMerge);

                            // delete current match
                            final Map<String, Object> opDelete = new MapBuilder<String, Object>()
                                    .kv("op", DELETE_OP)
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
                            .kv("op", INSERT_OP)
                            .kv("ingname", ingname)
                            .kv("ingmembers", ingmembers)
                            .build();
                    opList.add(opInsert);
                }
                // Process opList
                logger.fine(String.format("Process opList: %s", opList));
                processOpList(out, opList);
            }
            final long endTime = System.currentTimeMillis();
            logger.fine(String.format("evaluate start %d ms, end %d ms, duration %d ms",
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
            logger.fine(String.format("Process opList: %s", opList));
            for (final Map<String, Object> opElement : opList) {

                final String op = (String) opElement.getOrDefault("op", "-");
                if (MERGE_OP.equals(op)) {
                    // can merge
                    final Set<String> op_ingname = (Set<String>) opElement.get("ingname");
                    final Set<String> op_ingmembers = (Set<String>) opElement.get("ingmembers");
                    final Set<String> op_outnames = (Set<String>) opElement.get("outnames");
                    final Set<String> op_outgmembers = (Set<String>) opElement.get("outgmembers");

                    op_outnames.addAll(op_ingname);
                    op_outgmembers.addAll(op_ingmembers);
                } else if (INSERT_OP.equals(op)) {
                    // add in to out
                    final String op_ingname = (String) opElement.get("ingname");
                    final Set<String> op_ingmembers = (Set<String>) opElement.get("ingmembers");

                    out.groupsMergedList.add(new MapBuilder<String, Set<String>>()
                            .kv(Data.kName, new HashSet<>(Arrays.asList(op_ingname)))
                            .kv(Data.kGroup, new HashSet<>(op_ingmembers))
                            .build()
                    );
                } else if (DELETE_OP.equals(op)) {
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

    /**
     * Implementation of algorithm merging groups.
     * <p>
     * This implementation uses explicit class OpRecordProcessor instead of Map.
     *
     */
    static class Algorithm2 implements IAlgorithm {

        private static final Logger logger = Logger.getLogger(Algorithm2.class.getName());

        @Override
        public Data evaluate(final Data in) {
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
                final OpRecordProcessor opList = new OpRecordProcessor();

                // try to find ingname, ingmembers in out.groupsMergedList
                // find a match for [ingname, ingmembers] in groupsMergedList
                int matchCount = 0;
                for (final Map<String, Set<String>> groupsMergedElement : out.groupsMergedList) {
                    final Set<String> outnames = groupsMergedElement.get(Data.kName);
                    final Set<String> outgmembers = groupsMergedElement.get(Data.kGroup);

                    final boolean match = ingmembers.stream().anyMatch((theIngmember) -> outgmembers.contains(theIngmember));
                    if (match) {
                        // Thers is already a group containing ingmember
                        if (matchCount == 0) {
                            OpRecordMerge opRecordMerge = new OpRecordMerge(
                                    newSetBuilderVs(ingname),
                                    ingmembers,
                                    outnames,
                                    outgmembers);
                            opList.addOpRecord(opRecordMerge);
                        } else {
                            // This is another match
                            final OpRecordMerge opMergeMatchCount0 = (OpRecordMerge) opList.getOpRecord(0);
                            // merge current match to first merge
                            OpRecordMerge opRecordMerge = new OpRecordMerge(
                                    outnames,
                                    outgmembers,
                                    opMergeMatchCount0.merge_outgnames,
                                    opMergeMatchCount0.merge_outgmembers);
                            opList.addOpRecord(opRecordMerge);

                            // delete current match
                            OpRecordDelete opRecordDelete = new OpRecordDelete(
                                    outnames,
                                    outgmembers);
                            opList.addOpRecord(opRecordDelete);
                        }
                        matchCount += 1;
                    }
                }
                // PostProcess matchCount=0
                // no matching insert ingname, ingmembers into merged-result
                if (matchCount == 0) {
                    OpRecordInsert opRecordInsert = new OpRecordInsert(
                            ingname,
                            ingmembers);
                    opList.addOpRecord(opRecordInsert);
                }
                // Process opList
                logger.fine(String.format("Process opList: %s", opList));
                opList.process(out);
            }
            final long endTime = System.currentTimeMillis();
            logger.fine(String.format("evaluate start %d ms, end %d ms, duration %d ms",
                    startTime, endTime,
                    (endTime - startTime))
            );
            return out;
        }

        /**
         * Encapsulate processing an {@link  OpRecord}.
         */
        static class OpRecordProcessor {

            enum Op {
                insert,
                merge,
                delete
            }

            final List<OpRecord<?>> opRecordList;

            public OpRecordProcessor() {
                this.opRecordList = new ArrayList<>();
            }

            void addOpRecord(OpRecord opRecord) {
                this.opRecordList.add(opRecord);
            }

            OpRecord getOpRecord(int i) {
                return opRecordList.get(i);
            }

            /**
             * Process the list of {@link OpRecord}s
             *
             * @param out
             * @see OpRecord
             * @see OpRecordInsert
             * @see OpRecordMerge
             * @see OpRecordDelete
             */
            void process(Data out) {
                for (OpRecord opRecord : opRecordList) {
                    opRecord.consume(out);
                }
            }

            static abstract class OpRecord<V> {

                /**
                 * Define the entry for consuming.
                 *
                 * @param out
                 */
                protected abstract void consume(Data out);
            }

            static class OpRecordInsert extends OpRecord<OpRecordInsert> {

                final Op op;
                //---
                final String insert_ingname;
                final Set<String> insert_ingmembers;

                public OpRecordInsert(String insert_ingname, Set<String> insert_ingmembers) {
                    this.op = Op.insert;

                    this.insert_ingname = insert_ingname;
                    this.insert_ingmembers = insert_ingmembers;
                }

                @Override
                protected void consume(Data out) {
                    out.groupsMergedList.add(new MapBuilder<String, Set<String>>()
                            .kv(Data.kName, new HashSet<>(Arrays.asList(this.insert_ingname)))
                            .kv(Data.kGroup, new HashSet<>(this.insert_ingmembers))
                            .build()
                    );
                }
            }

            static class OpRecordMerge extends OpRecord<OpRecordMerge> {

                final Op op;
                //--
                final Set<String> merge_ingnames;
                final Set<String> merge_ingmembers;
                final Set<String> merge_outgnames;
                final Set<String> merge_outgmembers;

                public OpRecordMerge(Set<String> merge_ingnames, Set<String> merge_ingmembers,
                        Set<String> merge_outgnames, Set<String> merge_outgmembers) {
                    this.op = Op.merge;

                    this.merge_ingnames = merge_ingnames;
                    this.merge_ingmembers = merge_ingmembers;
                    this.merge_outgnames = merge_outgnames;
                    this.merge_outgmembers = merge_outgmembers;
                }

                @Override
                protected void consume(Data out) {
                    final Set<String> op_ingname = this.merge_ingnames;
                    final Set<String> op_ingmembers = this.merge_ingmembers;
                    final Set<String> op_outnames = this.merge_outgnames;
                    final Set<String> op_outgmembers = this.merge_outgmembers;

                    op_outnames.addAll(op_ingname);
                    op_outgmembers.addAll(op_ingmembers);

                }

            }

            static class OpRecordDelete extends OpRecord<OpRecordDelete> {

                final Op op;
                //--
                final Set<String> delete_outnames;
                final Set<String> delete_outgmembers;

                public OpRecordDelete(Set<String> delete_outnames, Set<String> delete_outgmembers) {
                    this.op = Op.delete;
                    this.delete_outnames = delete_outnames;
                    this.delete_outgmembers = delete_outgmembers;
                }

                @Override
                protected void consume(Data out) {
                    final Set<String> op_outnames = this.delete_outnames;
                    final Set<String> op_outgmembers = this.delete_outgmembers;
                    final Predicate<Map<String, Set<String>>> p = (m
                            -> m.get(Data.kName) == op_outnames
                            && m.get(Data.kGroup) == op_outgmembers);
                    out.groupsMergedList.removeIf(p);

                }
            }
        }
    }
}
