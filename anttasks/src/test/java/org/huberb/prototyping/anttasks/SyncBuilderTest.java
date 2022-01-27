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
import org.apache.tools.ant.taskdefs.Sync;
import org.apache.tools.ant.types.FileSet;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 *
 * @author berni3
 */
public class SyncBuilderTest {

    @TempDir
    private static Path sharedTempDir;

    public SyncBuilderTest() {
    }

    @Test
    public void testSync() throws IOException {
        assertNotNull(sharedTempDir);

        final long expectedLength = "ABC".length() * 100L;
        for (final Path p : Arrays.asList(
                sharedTempDir.resolve("sync-src-test-dir1/testXXX-file1-1.ext1"),
                sharedTempDir.resolve("sync-src-test-dir1/testXXX-file2-1.ext2"),
                sharedTempDir.resolve("sync-src-test-dir1/testXXX-file2-2.ext2"),
                sharedTempDir.resolve("sync-src-test-dir1/testXXX-file3-1.ext3"),
                sharedTempDir.resolve("sync-src-test-dir1/testXXX-file4-1.ext4"))) {
            final File parentFile = p.toFile().getParentFile();
            final File file = p.toFile();
            if (!parentFile.exists()) {
                assertTrue(parentFile.mkdir(), "" + parentFile);
            }
            assertTrue(file.createNewFile(), "" + file);

            AntTasksBuilderTest.createFileContent(file, "ABC", 100);
            assertEquals(expectedLength, file.length());
        }

        final File destinationDir = sharedTempDir.resolve("sync-destination-dir-1").toFile();
        assertTrue(destinationDir.mkdir(), "" + destinationDir);

        //---
        //---
        final FileSet fileSet = new FileSetBuilder()
                .dir(sharedTempDir.toString())
                .includes("sync-src-test-dir1/*.ext2")
                .build();
        final AntTasksBuilder antTasksBuilder = new AntTasksBuilder();
        final Sync sync = new SyncBuilder(antTasksBuilder.project)
                .addfileset(fileSet)
                .todir(destinationDir.getPath())
                .build();
        sync.execute();
        //---
        final File d1 = new File(destinationDir, "sync-src-test-dir1/testXXX-file2-1.ext2");
        final File d2 = new File(destinationDir, "sync-src-test-dir1/testXXX-file2-2.ext2");
        Assertions.assertAll(
                () -> assertTrue(d1.exists(), String.format("destinationFile %s", d1)),
                () -> assertEquals(expectedLength, d1.length(), String.format("destinationFile %s", d1))
        );
        Assertions.assertAll(
                () -> assertTrue(d2.exists(), String.format("destinationFile %s", d2)),
                () -> assertEquals(expectedLength, d2.length(), String.format("destinationFile %s", d2))
        );
    }

}
