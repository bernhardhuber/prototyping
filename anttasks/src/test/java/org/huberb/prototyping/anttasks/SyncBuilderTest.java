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
import org.apache.tools.ant.taskdefs.Sync;
import org.apache.tools.ant.types.FileSet;
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
public class SyncBuilderTest {

    @TempDir
    private static Path sharedTempDir;

    public SyncBuilderTest() {
    }

    @Test
    public void testSync() throws IOException {
        assertNotNull(sharedTempDir);

        final Path srcPath = sharedTempDir.resolve("sync-src-test-file1.ext1");
        final File srcFile = srcPath.toFile();
        assertFalse(srcFile.exists(), String.format("srcFile %s", srcFile.getPath()));
        final File destinationDir = sharedTempDir.resolve("sync-destination-dir-1").toFile();
        assertTrue(destinationDir.mkdir(), "" + destinationDir);
        final File destinationFile = new File(destinationDir, "sync-src-test-file1.ext1");
        assertFalse(destinationFile.exists(), "" + destinationFile);

        //---
        assertTrue(srcFile.createNewFile());
        final String content = "testSyncContent";
        AntTasksBuilderTest.createFileContent(srcFile, content, 100);
        final long expectedLength = content.length() * 100L;
        assertEquals(expectedLength, srcFile.length());
        //---
        final FileSet fileSet = new FileSetBuilder().dir(sharedTempDir.toString()).includes("*.ext1").build();
        final AntTasksBuilder antTasksBuilder = new AntTasksBuilder();
        final Sync copy = new SyncBuilder(antTasksBuilder.project)
                .addfileset(fileSet)
                .todir(destinationDir.getPath())
                .build();
        copy.execute();
        //---
        Assertions.assertAll(
                () -> assertTrue(srcFile.exists(), String.format("srcFile %s", srcFile.getPath())),
                () -> assertEquals(expectedLength, srcFile.length(), String.format("srcFile %s", srcFile.getPath())),
                () -> assertTrue(destinationFile.exists(), String.format("destinationFile %s", destinationDir)),
                () -> assertEquals(expectedLength, destinationFile.length(), String.format("destinationFile %s", destinationDir))
        );
    }

}
