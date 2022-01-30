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
 * Encapsulate CSV writing, and reading of {@link Data}-lists.
 *
 * @author berni3
 */
public class CsvGenerator {

    public static class CsvWriter {

        /**
         * Entry point to write csv.
         *
         * @param mapList
         * @return
         */
        public String toCsv(List<Map<String, Set<String>>> mapList) {
            StringBuilder csvLines = new StringBuilder();
            // add csv-header
            csvLines.append(toSingleLine(new String[]{"groups", "members"}));
            for (Map<String, Set<String>> m : mapList) {
                final Set<String> nameSet = m.get(Data.kName);
                final Set<String> groupSet = m.get(Data.kGroup);
                final String[] lineValues = new String[]{nameSet.toString(), groupSet.toString()};
                csvLines.append(toSingleLine(lineValues));
            }
            return csvLines.toString();
        }

        String toSingleLine(String[] lineValues) {
            final StringBuilder sb = new StringBuilder();
            final String csvLine = Stream.of(lineValues)
                    .map(this::escapeSpecialCharacters)
                    .collect(Collectors.joining(","));
            sb.append(csvLine).append("\n");
            return sb.toString();
        }

        String escapeSpecialCharacters(String data) {
            String escapedData = data.replaceAll("\\R", " ");
            if (data.contains(",") || data.contains("\"") || data.contains("'")) {
                data = data.replace("\"", "\"\"");
                escapedData = "\"" + data + "\"";
            }
            return escapedData;
        }
    }

    public static class CsvReader {

        /**
         * Entry point to read csv.
         *
         * @param s
         * @return
         * @throws IOException
         */
        public List<Map<String, Set<String>>> fromCsv(String s) throws IOException {
            final List<Map<String, Set<String>>> l = new ArrayList<>();
            try (final StringReader sr = new StringReader(s); final BufferedReader br = new BufferedReader(sr)) {
                for (String line; (line = br.readLine()) != null;) {
                    if (line.isEmpty() || line.strip().isEmpty()) {
                        continue;
                    }
                    final Map<String, Set<String>> m = fromSingleLine(line);
                    if (!m.isEmpty()) {
                        l.add(m);
                    }
                }
            }
            return l;
        }

        Map<String, Set<String>> fromSingleLine(String line) {
            final Map<String, Set<String>> m = new HashMap<>();
            int indexOfComma = line.indexOf(',');
            String k = line.substring(0, indexOfComma);
            String v = line.substring(indexOfComma + 1);
            // strip encodings 
            k = stripEncodings(k);
            v = stripEncodings(v);

            if (!k.isEmpty() && !v.isEmpty()) {
                // map v from "A,B,C" -> set: ["A","B","C"]
                final Set<String> vSet = Stream.of(v.split(","))
                        .map(s -> s.strip())
                        .collect(Collectors.toSet());
                m.put(Data.kName, new SetBuilder<String>().v(k).build());
                m.put(Data.kGroup, vSet);
            }
            return m;
        }

        /**
         * Strip encodings.
         * <p>
         * <ul>
         * <li>Remove leading `"´ and trailing `"´
         * <li>Remove leading `[´ and trailing `]´
         * <li>Replace `""´ by `"´
         * <li>Strip leading and trailing white-space
         * </ul>
         *
         * @param s
         * @return decoded value
         */
        String stripEncodings(String s) {
            String result = s;

            // strip leading "
            {
                final int index = result.indexOf("\"");
                if (index >= 0) {
                    result = result.substring(index + 1);
                }
            }
            {
                // strip trailing "
                final int index = result.lastIndexOf("\"");
                if (index >= 0) {
                    result = result.substring(0, index);
                }
            }
            {
                // strip leading [
                final int index = result.indexOf("[");
                if (index >= 0) {
                    result = result.substring(index + 1);
                }
            }
            {
                // strip trailing ]
                final int index = result.lastIndexOf("]");
                if (index >= 0) {
                    result = result.substring(0, index);
                }
                result = result.strip();
            }
            return result;
        }
    }

}
