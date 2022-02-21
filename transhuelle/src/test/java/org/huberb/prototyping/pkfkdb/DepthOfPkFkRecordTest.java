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

import java.util.List;
import org.huberb.prototyping.pkfkdb.PkFkDb.DepthOfPkFkRecord;
import org.huberb.prototyping.pkfkdb.PkFkDb.PkFkRecord;
import org.huberb.prototyping.transhuelle.CsvGenerator;
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
        final List<PkFkRecord> pkFkRecordList = PkFkRecord.createTEEINGAENGE_ANTRAEGE_1();
        final DepthOfPkFkRecord instance = new DepthOfPkFkRecord(pkFkRecordList);
        final List result = instance.calcDepth1();

        System.out.printf("testCalcDepth1: %s%n", result);
/*
[
Pair{u=T_ANTRAEGE,      v=[T_RECHNUNGEN, T_LEISTUNGSZEILEN, T_FAELLE]}, => 3
Pair{u=T_ANTRAEGE,      v=[T_EEINGAENGE_ANTRAEGE]},                     => 2
Pair{u=T_FAELLE,        v=[T_RECHNUNGEN, T_LEISTUNGSZEILEN]},           => 2
Pair{u=T_KONTO,         v=[T_RECHNUNGEN, T_LEISTUNGSZEILEN, T_FAELLE]}, => 3
Pair{u=T_RECHNUNGEN,    v=[T_LEISTUNGSZEILEN]},                         => 1
Pair{u=T_PARTNER,       v=[T_RECHNUNGEN, T_LEISTUNGSZEILEN]},           => 2
Pair{u=T_KUNDE,         v=[T_RECHNUNGEN, T_LEISTUNGSZEILEN]},           => 2
Pair{u=T_EEINGAENGE,    v=[T_EEINGAENGE_ANTRAEGE]}                      => 1
]
*/
    }

    @Test
    public void testCalcDepth2() {
        final List<PkFkRecord> pkFkRecordList = PkFkRecord.createTEEINGAENGE_ANTRAEGE_1();
        final DepthOfPkFkRecord instance = new DepthOfPkFkRecord(pkFkRecordList);
        final Data dout = instance.calcDepth2();

        System.out.println(String.format("csv testCalcDepth2%n"
                + "groupsInList:%n%s%n"
                + "groupsMergedList:%n%s%n",
                new CsvGenerator.CsvWriter().toCsv(dout.getGroupsInList()),
                new CsvGenerator.CsvWriter().toCsv(dout.getGroupsMergedList()))
        );

        assertEquals("pkfk", dout.getName());

    }
}
