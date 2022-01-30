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
import java.util.stream.Stream;
import org.huberb.prototyping.transhuelle.CsvGenerator.CsvWriter;
import org.huberb.prototyping.transhuelle.Supports.MapBuilder;
import org.huberb.prototyping.transhuelle.Supports.SetBuilder;
import org.huberb.prototyping.transhuelle.TransHuelle.Data;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author berni3
 */
public class CsvWriterTest {

    @ParameterizedTest
    @MethodSource("given_csvalue_then_escape_this_valueProvider")
    public void given_csvalue_escape_this_value(String expected, String value) {
        final CsvWriter instance = new CsvWriter();
        assertEquals(expected, instance.escapeSpecialCharacters(value));
    }

    static Stream<Arguments> given_csvalue_then_escape_this_valueProvider() {
        return Stream.of(
                Arguments.of("XY", "XY"),
                Arguments.of("\"X,Y\"", "X,Y"),
                Arguments.of("\"X\"\"Y\"", "X\"Y"),
                Arguments.of("\"X'Y\"", "X'Y"),
                Arguments.of("X Y", "X\nY"),
                Arguments.of("X Y", "X\rY"),
                Arguments.of("X Y", "X\r\nY"),
                Arguments.of("X\tY", "X\tY"),
                Arguments.of("Z", "Z")
        );
    }

    @ParameterizedTest
    @MethodSource("given_csv_values_then_generate_a_csv_lineProvider")
    public void given_csv_values_generate_a_csv_line(String expected, String[] values) {
        final CsvWriter instance = new CsvWriter();
        assertEquals(expected, instance.toSingleLine(values));
    }

    static Stream<Arguments> given_csv_values_then_generate_a_csv_lineProvider() {
        return Stream.of(
                Arguments.of("X,Y\n", new String[]{"X", "Y"}),
                Arguments.of("X,Y Z\n", new String[]{"X", "Y Z"}),
                Arguments.of("X,\"Y,Z\"\n", new String[]{"X", "Y,Z"}),
                Arguments.of("X,\"Y, Z\"\n", new String[]{"X", "Y, Z"}),
                Arguments.of("X,\" Y , Z \"\n", new String[]{"X", " Y , Z "}),
                Arguments.of("X,\"[Y,Z]\"\n", new String[]{"X", "[Y,Z]"}),
                Arguments.of("Z\n", new String[]{"Z"})
        );
    }

    @Test
    public void given_list_of_maps_then_generate_csv() {
        final CsvWriter instance = new CsvWriter();
        List<Map<String, Set<String>>> l = new ArrayList<>();

        l.add(new MapBuilder<String, Set<String>>()
                .kv(Data.kName, new SetBuilder<String>().vs("S1").build())
                .kv(Data.kGroup, new SetBuilder<String>().vs("A1", "A2").build())
                .build()
        );
        String csv = instance.toCsv(l);
        assertEquals("groups,members\n"
                + "[S1],\"[A1, A2]\"\n"
                + "", csv);
    }
}
