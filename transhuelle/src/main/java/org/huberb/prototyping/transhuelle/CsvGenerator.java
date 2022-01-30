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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.huberb.prototyping.transhuelle.Supports.SetBuilder;
import org.huberb.prototyping.transhuelle.TransHuelle.Data;

/**
 *
 * @author berni3
 */
class CsvGenerator {

    String toCsv0(List<Map<String, Set<String>>> l) {
        StringBuilder csvLines = new StringBuilder();
        csvLines.append(toSingleLine(new String[]{"key", "value"}));
        for (Map<String, Set<String>> m : l) {
            Set<String> nameSet = m.get(Data.kName);
            Set<String> groupSet = m.get(Data.kGroup);
            final String[] lineValues = new String[]{nameSet.toString(), groupSet.toString()};
            csvLines.append(toSingleLine(lineValues));
        }
        return csvLines.toString();
    }

    List<Map<String, Set<String>>> fromCsv(String s) throws IOException {
        List<Map<String, Set<String>>> l = new ArrayList<>();
        try (final StringReader sr = new StringReader(s)) {
            BufferedReader br = new BufferedReader(sr);
            for (String line; (line = br.readLine()) != null;) {
                Map<String, Set<String>> m = fromSingleLine(line);
                l.add(m);
            }
        }
        return l;
    }

    Map<String, Set<String>> fromSingleLine(String line) {
        Map<String, Set<String>> m = new HashMap<>();
        int indexOfComma = line.indexOf(',');
        String k = line.substring(0, indexOfComma);
        String v = line.substring(indexOfComma + 1);
        //---
        k = stripEncodings(k).strip();

        //---
        v = stripEncodings(v).strip();

        //---
        final Set<String> vSet = Stream.of(v.split(",")).map(s -> s.strip()).collect(Collectors.toSet());
        m.put(Data.kName, new SetBuilder<String>().v(k).build());
        m.put(Data.kGroup, vSet);
        return m;
    }

    String stripEncodings(String s) {
        int index;
        String result = s;

        index = result.indexOf("\"");
        if (index >= 0) {
            result = result.substring(index + 1);
        }

        index = result.lastIndexOf("\"");
        if (index >= 0) {
            result = result.substring(0, index);
        }

        index = result.indexOf("[");
        if (index >= 0) {
            result = result.substring(index + 1);
        }

        index = result.lastIndexOf("]");
        if (index >= 0) {
            result = result.substring(0, index);
        }

        return result;
    }

    String toSingleLine(String[] lineValues) {
        StringBuilder sb = new StringBuilder();
        String csvLine = Stream.of(lineValues).map(this::escapeSpecialCharacters).collect(Collectors.joining(","));
        sb.append(csvLine).append("\n");
        return sb.toString();
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
