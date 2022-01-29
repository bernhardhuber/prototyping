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

import groovy.json.JsonGenerator;
import groovy.json.JsonGenerator.Options;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.huberb.prototyping.transhuelle.TransHuelle.Algorithm;
import org.huberb.prototyping.transhuelle.TransHuelle.Data;
import org.huberb.prototyping.transhuelle.TransHuelle.DataFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author berni3
 */
public class AlgorithmTest {

    public AlgorithmTest() {
    }

    /**
     * Test of evaluate, of class Algorithm.
     */
    @Test
    public void given_createDataSample1_verify_groupsMergedList() {
        final Data din = new DataFactory().createDataSample1();
        final Data dout = new Algorithm().evaluate(din);

        assertEquals(3, dout.groupsInList.size(), "" + dout);
        assertEquals(2, dout.groupsMergedList.size(), "" + dout);
        assertTrue(dout.groupsMergedList.get(0).get(Data.kName).containsAll(Arrays.asList("S1", "S2")), "" + dout);
        assertTrue(dout.groupsMergedList.get(0).get(Data.kGroup).containsAll(Arrays.asList("A1", "A2", "A3")), "" + dout);
        assertTrue(dout.groupsMergedList.get(1).get(Data.kName).containsAll(Arrays.asList("S3")), "" + dout);
        assertTrue(dout.groupsMergedList.get(1).get(Data.kGroup).containsAll(Arrays.asList("A4")), "" + dout);
    }

    @Test
    public void given_createDataSample2_verify_groupsMergedList() {
        final Data din = new DataFactory().createDataSample2();
        final Data dout = new Algorithm().evaluate(din);

        assertEquals(4, dout.groupsInList.size(), "" + dout);
        assertEquals(1, dout.groupsMergedList.size(), "" + dout);
        assertTrue(dout.groupsMergedList.get(0).get(Data.kName).containsAll(Arrays.asList("S1", "S2")), "" + dout);
        assertTrue(dout.groupsMergedList.get(0).get(Data.kGroup).containsAll(Arrays.asList("A1", "A2", "A3", "A4")), "" + dout);
    }
    @Test
    public void given_createDataSample3_verify_groupsMergedList() {
        final Data din = new DataFactory().createDataSample3();
        final Data dout = new Algorithm().evaluate(din);

assertEquals("dataSample3 no-merge-only-insert", dout.name);
        assertEquals(5, dout.groupsInList.size(), "" + dout);
        assertEquals(5, dout.groupsMergedList.size(), "" + dout);
        assertTrue(dout.groupsMergedList.get(0).get(Data.kName).containsAll(Arrays.asList("S0")), "" + dout);
        assertTrue(dout.groupsMergedList.get(0).get(Data.kGroup).containsAll(Arrays.asList("A0", "A1")), "" + dout);
        assertTrue(dout.groupsMergedList.get(1).get(Data.kName).containsAll(Arrays.asList("S2")), "" + dout);
        assertTrue(dout.groupsMergedList.get(1).get(Data.kGroup).containsAll(Arrays.asList("A2", "A3")), "" + dout);
        assertTrue(dout.groupsMergedList.get(2).get(Data.kName).containsAll(Arrays.asList("S4")), "" + dout);
        assertTrue(dout.groupsMergedList.get(2).get(Data.kGroup).containsAll(Arrays.asList("A4", "A5")), "" + dout);
        assertTrue(dout.groupsMergedList.get(3).get(Data.kName).containsAll(Arrays.asList("S6")), "" + dout);
        assertTrue(dout.groupsMergedList.get(3).get(Data.kGroup).containsAll(Arrays.asList("A6", "A7")), "" + dout);
        assertTrue(dout.groupsMergedList.get(4).get(Data.kName).containsAll(Arrays.asList("S8")), "" + dout);
        assertTrue(dout.groupsMergedList.get(4).get(Data.kGroup).containsAll(Arrays.asList("A8", "A9")), "" + dout);
    }
    @Test
    public void given_createDataSample4_verify_groupsMergedList() {
        final Data din = new DataFactory().createDataSample4();
        final Data dout = new Algorithm().evaluate(din);

assertEquals("dataSample4 merge-alleven-insert-allodd", dout.name);
        assertEquals(10, dout.groupsInList.size(), "" + dout);
        assertEquals(6, dout.groupsMergedList.size(), "" + dout);
        assertTrue(dout.groupsMergedList.get(0).get(Data.kName).containsAll(Arrays.asList("S0", "S2", "S4", "S6", "S8")), "" + dout);
        assertTrue(dout.groupsMergedList.get(0).get(Data.kGroup).containsAll(Arrays.asList("A0", "A2", "A4", "A6", "A8")), "" + dout);
        assertTrue(dout.groupsMergedList.get(1).get(Data.kName).containsAll(Arrays.asList("S1")), "" + dout);
        assertTrue(dout.groupsMergedList.get(1).get(Data.kGroup).containsAll(Arrays.asList("A1")), "" + dout);
        assertTrue(dout.groupsMergedList.get(2).get(Data.kName).containsAll(Arrays.asList("S3")), "" + dout);
        assertTrue(dout.groupsMergedList.get(2).get(Data.kGroup).containsAll(Arrays.asList("A3")), "" + dout);
        assertTrue(dout.groupsMergedList.get(3).get(Data.kName).containsAll(Arrays.asList("S5")), "" + dout);
        assertTrue(dout.groupsMergedList.get(3).get(Data.kGroup).containsAll(Arrays.asList("A5")), "" + dout);
        assertTrue(dout.groupsMergedList.get(4).get(Data.kName).containsAll(Arrays.asList("S7")), "" + dout);
        assertTrue(dout.groupsMergedList.get(4).get(Data.kGroup).containsAll(Arrays.asList("A7")), "" + dout);
        assertTrue(dout.groupsMergedList.get(5).get(Data.kName).containsAll(Arrays.asList("S9")), "" + dout);
        assertTrue(dout.groupsMergedList.get(5).get(Data.kGroup).containsAll(Arrays.asList("A9")), "" + dout);
    }

    static class Generators {

        void toYaml(Data dout) {
            final Yaml yaml = new Yaml();
            final String doutYaml = yaml.dump(dout.toMap());
            System.out.println(String.format("doutYaml:%n%s%n", doutYaml));
        }

        void toJson(Data dout) {
            JsonGenerator jsonGenerator = new Options().build();
            final String doutJson = jsonGenerator.toJson(dout.toMap());
            System.out.println(String.format("doutJson:%n%s%n", doutJson));
        }

        void toCsv(Data dout) {
            final String doutCsv = new CsvGenerator().toCsv(dout.toMap());
            System.out.println(String.format("doutCsv:%n%s%n", doutCsv));
        }

        static class CsvGenerator {

            String toCsv(Map<String, Object> m) {
                //List<String> keys = m.keySet().stream().sorted().collect(Collectors.toList());
                List<Map.Entry<String, Object>> mapEntryList = m.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .collect(Collectors.toList());
                final StringBuilder csvLines = new StringBuilder();
                for (Map.Entry<String, Object> mapEntry : mapEntryList) {
                    String[] lineValues = new String[]{mapEntry.getKey(), mapEntry.getValue().toString()};
                    String csvLine = Stream.of(lineValues)
                            .map(this::escapeSpecialCharacters)
                            .collect(Collectors.joining(","));
                    csvLines.append(csvLine).append("\n");

                }
                return csvLines.toString();
            }

            public String escapeSpecialCharacters(String data) {
                String escapedData = data.replaceAll("\\R", " ");
                if (data.contains(",") || data.contains("\"") || data.contains("'")) {
                    data = data.replace("\"", "\"\"");
                    escapedData = "\"" + data + "\"";
                }
                return escapedData;
            }
        }
    }
}