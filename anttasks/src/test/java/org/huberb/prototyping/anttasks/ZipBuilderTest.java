/*
 * Copyright 2021 berni3.
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
import java.util.Arrays;
import org.apache.tools.ant.taskdefs.Zip;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 *
 * @author berni3
 */
public class ZipBuilderTest {

    @TempDir
    private static Path sharedTempDir;

    public ZipBuilderTest() {
    }

    @Test
    public void testSync() throws IOException {
        assertNotNull(sharedTempDir);

        final long expectedLength = "ABC".length() * 100L;

        final Path basedir = sharedTempDir.resolve("zip-src-test-dir1");
        for (final Path p : Arrays.asList(
                basedir.resolve("testXXX-file1-1.ext1"),
                basedir.resolve("testXXX-file2-1.ext2"),
                basedir.resolve("testXXX-file2-2.ext2"),
                basedir.resolve("testXXX-file3-1.ext3"),
                basedir.resolve("testXXX-file4-1.ext4"))) {
            final File parentFile = p.toFile().getParentFile();
            final File file = p.toFile();
            if (!parentFile.exists()) {
                assertTrue(parentFile.mkdir(), "" + parentFile);
            }
            assertTrue(file.createNewFile(), "" + file);

            AntTasksBuilderTest.createFileContent(file, "ABC", 100);
            assertEquals(expectedLength, file.length());
        }

        final File destinationFile = sharedTempDir.resolve("zip-destination-file-1.zip").toFile();
        assertFalse(destinationFile.exists(), "" + destinationFile);

        //---
        final AntTasksBuilder antTasksBuilder = new AntTasksBuilder();
        final Zip zip = new ZipBuilder(antTasksBuilder.project)
                .basedir(basedir.toFile().getPath())
                .destFile(destinationFile.getPath())
                .includes("*.ext2")
                .build();
        zip.execute();
        //---
        Assertions.assertAll(
                () -> assertTrue(destinationFile.exists(), String.format("destinationFile %s", destinationFile))
        );
    }

}
