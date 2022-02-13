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
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.huberb.prototyping.transhuelle.Supports.MapBuilder;
import org.huberb.prototyping.transhuelle.Supports.SetBuilder;
import org.huberb.prototyping.transhuelle.TransHuelle.Algorithm2.OpRecordProcessor;
import org.huberb.prototyping.transhuelle.TransHuelle.Algorithm2.OpRecordProcessor.OpRecord;
import org.huberb.prototyping.transhuelle.TransHuelle.Algorithm2.OpRecordProcessor.OpRecordDelete;
import org.huberb.prototyping.transhuelle.TransHuelle.Algorithm2.OpRecordProcessor.OpRecordInsert;
import org.huberb.prototyping.transhuelle.TransHuelle.Algorithm2.OpRecordProcessor.OpRecordMerge;
import org.huberb.prototyping.transhuelle.TransHuelle.Data;
import static org.huberb.prototyping.transhuelle.TransHuelle.Data.newSetBuilderVs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class OpRecordProcessorTest {

    @Test
    public void given_OpRecordInsert_then_process_it() {
        final Data dataOut = new Data("given_OpRecordInsert_then_process_it");
        final OpRecord opRecordInsert = new OpRecordInsert("S1", new SetBuilder<String>().vs("A1", "A2").build());

        //---
        final OpRecordProcessor opRecordProcessor = new OpRecordProcessor();
        opRecordProcessor.addOpRecord(opRecordInsert);

        //---
        opRecordProcessor.process(dataOut);

        assertEquals(0, dataOut.groupsInList.size());
        assertEquals(1, dataOut.groupsMergedList.size());
        assertEquals(1, dataOut.groupsMergedList.get(0).get(Data.kName).size());
        assertEquals(2, dataOut.groupsMergedList.get(0).get(Data.kGroup).size());

        assertEquals("[S1]", dataOut.groupsMergedList.get(0).get(Data.kName).toString());
        assertEquals("[A1, A2]", dataOut.groupsMergedList.get(0).get(Data.kGroup).toString());
    }

    @Test
    public void given_OpRecordMerge_then_process_it() {
        final TransHuelle.Data dataOut = new TransHuelle.Data("given_OpRecordInsert_then_process_it");
        final List<Map<String, Set<String>>> arg0 = new ArrayList<>();
        arg0.add(new MapBuilder<String, Set<String>>()
                .kv(TransHuelle.Data.kName, newSetBuilderVs("S1"))
                .kv(TransHuelle.Data.kGroup, newSetBuilderVs("A1", "A2"))
                .build());
        dataOut.groupsMergedList.addAll(arg0);

        //---
        final OpRecordProcessor opRecordProcessor = new OpRecordProcessor();
        final OpRecord opRecordMerge = new OpRecordMerge(
                new SetBuilder<String>().v("S1").build(),
                new SetBuilder<String>().vs("A3", "A4").build(),
                dataOut.groupsMergedList.get(0).get(Data.kName),
                dataOut.groupsMergedList.get(0).get(Data.kGroup)
        );
        opRecordProcessor.addOpRecord(opRecordMerge);

        //---
        opRecordProcessor.process(dataOut);

        assertEquals(0, dataOut.groupsInList.size());
        assertEquals(1, dataOut.groupsMergedList.size());
        assertEquals(1, dataOut.groupsMergedList.get(0).get(Data.kName).size());
        assertEquals(4, dataOut.groupsMergedList.get(0).get(Data.kGroup).size());

        assertEquals("[S1]", dataOut.groupsMergedList.get(0).get(Data.kName).toString());
        assertEquals("[A1, A2, A3, A4]", dataOut.groupsMergedList.get(0).get(Data.kGroup).toString());
    }

    @Test
    public void given_OpRecordDelete_then_process_it() {
        final TransHuelle.Data dataOut = new TransHuelle.Data("given_OpRecordInsert_then_process_it");
        final List<Map<String, Set<String>>> arg0 = new ArrayList<>();
        arg0.add(new MapBuilder<String, Set<String>>()
                .kv(TransHuelle.Data.kName, newSetBuilderVs("S1"))
                .kv(TransHuelle.Data.kGroup, newSetBuilderVs("A1", "A2"))
                .build());
        dataOut.groupsMergedList.addAll(arg0);

        //---
        final OpRecordProcessor opRecordProcessor = new OpRecordProcessor();
        final OpRecord opRecordDelete = new OpRecordDelete(
                dataOut.groupsMergedList.get(0).get(Data.kName),
                dataOut.groupsMergedList.get(0).get(Data.kGroup)
        );
        opRecordProcessor.addOpRecord(opRecordDelete);

        //---
        opRecordProcessor.process(dataOut);

        assertEquals(0, dataOut.groupsInList.size());
        assertEquals(0, dataOut.groupsMergedList.size());
    }
}
