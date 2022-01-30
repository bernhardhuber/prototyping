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

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import org.huberb.prototyping.transhuelle.CsvGenerator.CsvReader;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author berni3
 */
public class CsvReaderTest {

    @ParameterizedTest
    @MethodSource("given_X_Y_varyingProvider")
    public void given_X_Y_varying(String line) {
        final CsvReader instance = new CsvReader();
        final String expected = "{name=[X], group=[Y]}";

        final String l1 = "X,Y";
        assertEquals(expected, instance.fromSingleLine(l1).toString());

        final String l2 = "X,[Y]";
        assertEquals(expected, instance.fromSingleLine(l2).toString());

        final String l3 = "X,\"[Y]\"";
        assertEquals(expected, instance.fromSingleLine(l3).toString());
    }

    static Stream<Arguments> given_X_Y_varyingProvider() {
        return Stream.of(
                Arguments.of("X,Y"),
                Arguments.of("X, Y"),
                Arguments.of(" X , Y "),
                Arguments.of("X,[Y]"),
                Arguments.of("X, [Y]"),
                Arguments.of("X,\"[Y]\""),
                Arguments.of("X, \"[Y]\""),
                Arguments.of(" X , \" [ Y ] \" ")
        );

    }

    @ParameterizedTest
    @MethodSource("given_X_Y_Z_varyingProvider")
    public void given_X_Y_Z_varying(String line) {
        final CsvReader instance = new CsvReader();
        final String expected = "{name=[X], group=[Y, Z]}";

        assertEquals(expected, instance.fromSingleLine(line).toString());

    }

    static Stream<Arguments> given_X_Y_Z_varyingProvider() {
        return Stream.of(
                Arguments.of("X,\"Y,Z\""),
                Arguments.of("X,\"Y, Z\""),
                Arguments.of("X,\"[Y,Z]\""),
                Arguments.of("X,\"[Y, Z]\""),
                Arguments.of(" X, \" [Y , Z ] \" ")
        );
    }

    @Test
    public void given_csv_parse() throws IOException {
        final CsvReader instance = new CsvReader();
        final String csv
                = "S1, \"[A1,A2]\"\n"
                + "S2, \"[A2,A3]\"\n"
                + "S3, A4";

        final List<Map<String, Set<String>>> l = instance.fromCsv(csv);
        assertEquals("[{name=[S1], group=[A1, A2]}, {name=[S2], group=[A2, A3]}, {name=[S3], group=[A4]}]", l.toString());
    }

    @ParameterizedTest
    @MethodSource("given_input_strip_encodingProvider")
    public void given_input_strip_encoding(String expected, String input) {
        final CsvReader instance = new CsvReader();
        assertEquals(expected, instance.stripEncodings(input));
    }

    static Stream<Arguments> given_input_strip_encodingProvider() {
        return Stream.of(
                Arguments.of("X", "X"),
                Arguments.of("X", " X "),
                Arguments.of("X", "\"X\""),
                Arguments.of("X", " \" X \" "),
                Arguments.of("X", "[X]"),
                Arguments.of("X", " [ X ] "),
                Arguments.of("X", "\"[X]\""),
                Arguments.of("X", " \" [ X ] \" "),
                //---
                Arguments.of("X,Y", "X,Y"),
                Arguments.of("X,Y", " X,Y "),
                Arguments.of("X, Y", " X, Y "),
                Arguments.of("X,Y", "\"X,Y\""),
                Arguments.of("X,Y", " \" X,Y \" "),
                Arguments.of("X, Y", " \" X, Y \" "),
                Arguments.of("X,Y", "[X,Y]"),
                Arguments.of("X,Y", " [ X,Y ] "),
                Arguments.of("X, Y", " [ X, Y ] "),
                Arguments.of("X,Y", "\"[X,Y]\""),
                Arguments.of("X,Y", " \" [ X,Y ] \" "),
                Arguments.of("X, Y", " \" [ X, Y ] \" ")
        );
    }
}
