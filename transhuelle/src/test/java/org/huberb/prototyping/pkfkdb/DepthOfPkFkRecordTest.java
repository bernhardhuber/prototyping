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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.huberb.prototyping.pkfkdb.PkFkDb.DepthOfPkFkRecord;
import org.huberb.prototyping.pkfkdb.PkFkDb.PkFkRecord;
import org.huberb.prototyping.transhuelle.CsvGenerator;
import org.huberb.prototyping.transhuelle.Supports.MapBuilder;
import org.huberb.prototyping.transhuelle.Supports.Pair;
import org.huberb.prototyping.transhuelle.TransHuelle.Algorithm2;
import org.huberb.prototyping.transhuelle.TransHuelle.Data;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class DepthOfPkFkRecordTest {

    @Test
    public void testCalcDepth1() {
        final List<PkFkRecord> pkFkRecordList = PkFkRecord.createT_EE_AN_1();
        final DepthOfPkFkRecord instance = new DepthOfPkFkRecord(pkFkRecordList);
        final List<Pair<String, Set<String>>> result = instance.calcDepth1();

        final String resultFormatted = result.stream()
                .map((e) -> e.toString())
                .collect(Collectors.joining("\n"));

        System.out.printf("%ntestCalcDepth1:%n%s%n", resultFormatted);

        final List<Map<String, Set<String>>> inl = result.stream()
                .map(e -> new MapBuilder<String, Set<String>>()
                .kv(Data.kName, Data.newSetBuilderVs(e.getLeft()))
                .kv(Data.kGroup, e.getRight())
                .build())
                .collect(Collectors.toList());
        final Data din = new Data("testCalcDepth1", inl, new ArrayList<>());
        final Data dout = new Algorithm2().evaluate(din);
        System.out.println(String.format("%ncsv testCalcDepth1:%n"
                + "groupsInList:%n%s%n"
                + "groupsMergedList:%n%s%n",
                new CsvGenerator.CsvWriter().toCsv(dout.getGroupsInList()),
                new CsvGenerator.CsvWriter().toCsv(dout.getGroupsMergedList()))
        );
    }

    @Test
    public void testCalcDepth2() {
        final List<PkFkRecord> pkFkRecordList = PkFkRecord.createT_EE_AN_1();
        final DepthOfPkFkRecord instance = new DepthOfPkFkRecord(pkFkRecordList);
        final Data dout = instance.calcDepth2();

        System.out.println(String.format("%ncsv testCalcDepth2:%n"
                + "groupsInList:%n%s%n"
                + "groupsMergedList:%n%s%n",
                new CsvGenerator.CsvWriter().toCsv(dout.getGroupsInList()),
                new CsvGenerator.CsvWriter().toCsv(dout.getGroupsMergedList()))
        );

        assertEquals("pkfk", dout.getName());

    }
}
