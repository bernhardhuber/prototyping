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
package org.huberb.prototyping.pkfkdb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.huberb.prototyping.transhuelle.Supports.ListBuilder;
import org.huberb.prototyping.transhuelle.Supports.Pair;
import org.huberb.prototyping.transhuelle.Supports.SetBuilder;
import org.huberb.prototyping.transhuelle.TransHuelle.Algorithm2;
import org.huberb.prototyping.transhuelle.TransHuelle.Data;

/**
 *
 * @author berni3
 */
public class PkFkDb {

    static class TreeNodeWithChildren<D> {

        final D data;
        final List<TreeNodeWithChildren<D>> children;

        public TreeNodeWithChildren(D data) {
            this.data = data;
            this.children = new ArrayList<>();
        }
    }

    static class PkFkRecordTree {

        final List<PkFkRecord> pkFkRecordList = PkFkRecord.createT_EE_AN_1();

        TreeNodeWithChildren<String> createPkFkTreeFor(String startTablename) {
            final TreeNodeWithChildren<String> rootNode = new TreeNodeWithChildren<>("empty");
            final TreeNodeWithChildren<String> startNode = new TreeNodeWithChildren<>(startTablename);
            final Set<String> processed = new HashSet<>();
            rootNode.children.add(startNode);

            //---
            addChildren(startNode, processed);
            return rootNode;
        }

        TreeNodeWithChildren<String> createPkFkTreeAll() {
            final TreeNodeWithChildren<String> rootNode = new TreeNodeWithChildren<>("empty");

            final Set<String> processed = new HashSet<>();

            final Set<String> pkTablenameSet = this.pkFkRecordList.stream()
                    .map((e) -> e.pkTablename)
                    .collect(Collectors.toSet());
            pkTablenameSet.forEach((e) -> {
                String startTablename = e;
                if (!processed.contains(e)) {

                    final TreeNodeWithChildren<String> startNode = new TreeNodeWithChildren<>(startTablename);
                    rootNode.children.add(startNode);
                    //---
                    addChildren(startNode, processed);
                }
            });
            return rootNode;
        }

        List<String> fkTablenameFromPkTablename(String pkTablename) {
            final List<PkFkRecord> l = this.pkFkRecordList;
            final List<String> childPkFkTablenameList = l.stream()
                    .filter((e) -> pkTablename.equals(e.pkTablename))
                    .map((e) -> e.fkTablename)
                    .collect(Collectors.toList());
            return childPkFkTablenameList;
        }

        void addChildren(TreeNodeWithChildren<String> parentNode, Set<String> processed) {
            final List<PkFkRecord> l = this.pkFkRecordList;
            final String parentNodeData = parentNode.data;
            if (processed.contains(parentNodeData)) {
                return;
            }
            processed.add(parentNodeData);
            final List<String> fkTablenames = fkTablenameFromPkTablename(parentNodeData);
            fkTablenames.stream().forEach((childTablename) -> {
                final TreeNodeWithChildren<String> childNode = new TreeNodeWithChildren<>(childTablename);
                final long countOfChildTablenameInParentNodeChildren = parentNode.children.stream()
                        .filter((e) -> e.data.equals(childTablename))
                        .count();
                if (countOfChildTablenameInParentNodeChildren == 0L) {
                    parentNode.children.add(childNode);
                    addChildren(childNode, processed);
                }
            });
        }

        void visit(TreeNodeWithChildren<String> rootNode,
                BiConsumer<Integer, TreeNodeWithChildren<String>> consumer) {
            Set<String> visitedAlready = new HashSet<>();
            visit(0, rootNode,
                    visitedAlready,
                    consumer);
        }

        void visit(int startLevel,
                TreeNodeWithChildren<String> rootNode,
                Set<String> visitedAlready,
                BiConsumer<Integer, TreeNodeWithChildren<String>> consumer) {
            if (visitedAlready.contains(rootNode.data)) {
                return;
            }
            visitedAlready.add(rootNode.data);

            consumer.accept(startLevel, rootNode);
            rootNode.children.forEach((e) -> {
                visit(startLevel + 1,
                        e,
                        visitedAlready,
                        consumer);
            });
        }
    }

    static class DepthOfPkFkRecord {

        final List<PkFkRecord> pkFkRecordList;

        public DepthOfPkFkRecord(List<PkFkRecord> l) {
            this.pkFkRecordList = Collections.unmodifiableList(l);
        }

        List<Pair<String, Set<String>>> calcDepth1() {
            final List<Pair<String, Set<String>>> groupsInList = new ArrayList<>();
            for (PkFkRecord pkFkRecord : this.pkFkRecordList) {
                final String pkTablename = pkFkRecord.pkTablename;
                final String fkTablename = pkFkRecord.fkTablename;
                final String name = "g-" + pkTablename;
                final Set<String> members = new SetBuilder<String>().v(fkTablename).build();
                final Pair<String, Set<String>> p = new Pair(pkTablename, members);
                groupsInList.add(p);
            }

            final Function<String, List<PkFkRecord>> findPkTablename = (tablename) -> this.pkFkRecordList.stream()
                    .filter((e) -> tablename.equals(e.pkTablename))
                    .collect(Collectors.toList());

            int totalDiff;
            do {
                totalDiff = 0;
                for (Pair<String, Set<String>> p : groupsInList) {
                    final Set<String> fkSet = p.getRight();
                    final SetBuilder<String> setBuilder = new SetBuilder<String>();
                    for (String fkTablename : fkSet) {
                        final List<PkFkRecord> foundByPkTablenameList = findPkTablename.apply(fkTablename);
                        for (PkFkRecord foundByPkTablename : foundByPkTablenameList) {
                            final String newFkTablename = foundByPkTablename.fkTablename;
                            setBuilder.v(newFkTablename);
                        }
                    }
                    final int oldFkSetSize = fkSet.size();
                    fkSet.addAll(setBuilder.build());
                    final int newFkSetSize = fkSet.size();
                    final int diff = newFkSetSize - oldFkSetSize;
                    totalDiff += diff;
                }
            } while (totalDiff > 0);
            return groupsInList;
        }

        Data calcDepth2() {
            final List<Map<String, Set<String>>> groupsInList = new ArrayList<>();
            for (PkFkRecord pkFkRecord : this.pkFkRecordList) {
                final String pkTablename = pkFkRecord.pkTablename;
                final String fkTablename = pkFkRecord.fkTablename;
                final String name = "g-" + pkTablename;
                final Set<String> members = Data.newSetBuilderVs(fkTablename);
                final Map<String, Set<String>> m = new HashMap<>();
                m.put(Data.kName, Data.newSetBuilderVs(name));
                m.put(Data.kGroup, members);
                groupsInList.add(m);
            }

            final List<Map<String, Set<String>>> groupsMergedList = new ArrayList<>();
            final Data in = new Data("pkfk", groupsInList, groupsMergedList);

            final Algorithm2 algorithm2 = new Algorithm2();
            final Data out = algorithm2.evaluate(in);
            return out;
        }

    }

    static class PkFkRecord {

        final String pkTablename;
        final String pkId;
        final String fkTablename;
        final String fkId;

        public PkFkRecord(String pkTablename, String pkId, String fkTablename, String fkId) {
            this.pkTablename = pkTablename;
            this.pkId = pkId;
            this.fkTablename = fkTablename;
            this.fkId = fkId;
        }

        static List<PkFkRecord> createT_EE_AN_1() {
            final List<PkFkRecord> pkFkRecordList = new ListBuilder<PkFkRecord>()
                    .add(new PkFkRecord("T_AN", "ID", "T_FA", "AN_ID"))
                    .add(new PkFkRecord("T_AN", "ID", "T_EE_AN", "AN_ID"))
                    .add(new PkFkRecord("T_FA", "ID", "T_RE", "FA_ID"))
                    .add(new PkFkRecord("T_KO", "ID", "T_FA", "KO_ID"))
                    .add(new PkFkRecord("T_RE", "ID", "T_LZ", "RECHNUNG_ID"))
                    .add(new PkFkRecord("T_PA", "ID", "T_RE", "LERB_ID"))
                    .add(new PkFkRecord("T_KU", "ID", "T_RE", "LEMPF_ID"))
                    //.add(new PkFkRecord("T_LZ", "ID", "", ""))
                    .add(new PkFkRecord("T_EE", "ID", "T_EE_AN", "EE_ID"))
                    .build();
            return pkFkRecordList;
        }
    }

    static class PkFkData extends PkFkRecord {

        final List<String> pkIdList;

        public PkFkData(PkFkRecord pkFkRecord) {
            this(pkFkRecord.pkTablename, pkFkRecord.pkId, pkFkRecord.fkTablename, pkFkRecord.fkId);
        }

        public PkFkData(String pkTablename, String pkId, String fkTablename, String fkId) {
            super(pkTablename, pkId, fkTablename, fkId);
            this.pkIdList = new ArrayList<>();
        }

    }

    static class XXX {

        final List<PkFkRecord> pkFkRecordList = PkFkRecord.createT_EE_AN_1();

        void xxx() {
            List<PkFkData> pkFkDataList = new ArrayList<>();;
            Map<String, List<PkFkRecord>> m = pkFkRecordList.stream()
                    .collect(Collectors.groupingBy((e) -> e.pkTablename));

        }
    }

}
