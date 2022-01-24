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
import org.apache.tools.ant.taskdefs.Length;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 *
 * @author berni3
 */
public class LengthBuilderTest {

    @TempDir
    private static Path sharedTempDir;

    @Test
    public void given_empty_file_calc_length() throws IOException {
        Assertions.assertNotNull(sharedTempDir);
        final Path lengthPath = sharedTempDir.resolve("length-test-file1");
        final File lengthFile = lengthPath.toFile();
        Assertions.assertFalse(lengthFile.exists(), String.format("lengthFile %s", lengthFile.getPath()));
        Assertions.assertTrue(lengthFile.createNewFile());
        //---
        final AntTasksBuilder antTasksBuilder = new AntTasksBuilder();
        final Length length = new LengthBuilder(antTasksBuilder.project)
                .file(lengthFile.getPath())
                .build();
        length.execute();
        //---
        Assertions.assertAll(
                () -> Assertions.assertTrue(lengthFile.exists(), String.format("lengthFile %s", lengthFile.getPath())),
                () -> Assertions.assertEquals("0", length.getProject().getProperty("length")));
    }

    @Test
    public void given_file_with_content_calc_length() throws IOException {
        Assertions.assertNotNull(sharedTempDir);
        final Path lengthPath = sharedTempDir.resolve("length-test-file2.txt");
        final File lengthFile = lengthPath.toFile();

        final String content = "testLengthContent";
        AntTasksBuilderTest.createFileContent(lengthFile, content, 100);
        final long expectedLength = content.length() * 100L;
        Assertions.assertEquals(1700L, expectedLength);

        Assertions.assertTrue(lengthFile.exists());

        //---
        final AntTasksBuilder antTasksBuilder = new AntTasksBuilder();
        final Length length = new LengthBuilder(antTasksBuilder.project)
                .file(lengthFile.getPath())
                .build();
        length.execute();
        //---
        Assertions.assertAll(
                () -> Assertions.assertTrue(lengthFile.exists(), String.format("lengthFile %s", lengthFile.getPath())),
                () -> Assertions.assertEquals(expectedLength, Long.valueOf(length.getProject().getProperty("length")))
        );

    }
}
