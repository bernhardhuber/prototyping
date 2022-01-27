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
package org.huberb.prototyping.anttasks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;
import org.apache.tools.ant.taskdefs.Available;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author berni3
 */
public class AvailableBuilderTest {

    @TempDir
    private static Path sharedTempDir;

    @Test
    public void given_existing_file_test_its_availability() throws IOException {
        Assertions.assertNotNull(sharedTempDir);
        final Path availablePath = sharedTempDir.resolve("given_existing_file_test_its_availability_file1.txt");
        final File availableFile = availablePath.toFile();
        Assertions.assertFalse(availableFile.exists());
        Assertions.assertTrue(availableFile.createNewFile());
        //---
        final AntTasksBuilder antTasksBuilder = new AntTasksBuilder();
        final Available available = new AvailableBuilder(antTasksBuilder.project)
                .file(availableFile.getPath())
                .build();
        available.execute();
        //---
        final Object availableValue = available.getProject().getProperty("available");
        Assertions.assertAll(
                () -> Assertions.assertTrue(availableFile.exists()),
                () -> Assertions.assertTrue(String.class.isAssignableFrom(availableValue.getClass()), String.format("class: %s", availableValue.getClass())),
                () -> Assertions.assertEquals("true", availableValue)
        );
    }

    @ParameterizedTest
    @MethodSource("filenameAndExpectedExistsProvider")
    public void given_filename_then_test_its_availability(String filename, boolean expectedExists) throws IOException {
        Assertions.assertNotNull(sharedTempDir);
        final Path availablePath = sharedTempDir.resolve(filename);
        final File availableFile = availablePath.toFile();
        Assertions.assertFalse(availableFile.exists());
        if (expectedExists) {
            Assertions.assertTrue(availableFile.createNewFile());
        }
        //---
        final AntTasksBuilder antTasksBuilder = new AntTasksBuilder();
        final Available available = new AvailableBuilder(antTasksBuilder.project)
                .file(availableFile.getPath())
                .build();
        available.execute();
        //---
        final String availableValueAsString = Optional
                .ofNullable(available.getProject().getProperty("available"))
                .orElse("false");
        final Boolean availableValueAsBoolean = Optional
                .ofNullable(available.getProject().getProperty("available"))
                .map((s) -> Boolean.valueOf(s))
                .orElse(false);
        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedExists, availableFile.exists()),
                () -> Assertions.assertEquals(expectedExists, Boolean.valueOf(availableValueAsString.toString())),
                () -> Assertions.assertEquals(expectedExists, availableValueAsBoolean)
        );
    }

    static Stream<Arguments> filenameAndExpectedExistsProvider() {
        return Stream.of(
                arguments("filenameAndExpectedExistsProvider-available-test-file1-exists.txt", true),
                arguments("filenameAndExpectedExistsProvider-available-test-file1-does-not-exists.txt", false)
        );
    }
}
