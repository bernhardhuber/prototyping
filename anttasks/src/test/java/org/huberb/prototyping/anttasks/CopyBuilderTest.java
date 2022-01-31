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
import org.apache.tools.ant.taskdefs.Copy;
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
public class CopyBuilderTest {

    @TempDir
    private static Path sharedTempDir;

    public CopyBuilderTest() {
    }

    @Test
    public void given_an_existing_file_copy_it_to_a_new_file() throws IOException {
        assertNotNull(sharedTempDir);

        final Path srcPath = sharedTempDir.resolve("copy-src-test-file1.ext1");
        final File srcFile = srcPath.toFile();
        assertFalse(srcFile.exists(), String.format("srcFile %s", srcFile.getPath()));
        final File destinationFile = new File(srcFile.getPath() + ".copied");
        assertFalse(destinationFile.exists(), String.format("destinationFile %s", destinationFile.getPath()));
        //---
        assertTrue(srcFile.createNewFile());
        final String content = "testCopyContent";
        AntTasksBuilderTest.createFileContent(srcFile, content, 100);
        final long expectedLength = content.length() * 100L;
        assertEquals(expectedLength, srcFile.length());
        //---
        final AntTasksBuilder antTasksBuilder = new AntTasksBuilder();
        final Copy copy = new CopyBuilder(antTasksBuilder.project)
                .file(srcFile.getPath())
                .tofile(destinationFile.getPath())
                .build();
        copy.execute();
        //---
        Assertions.assertAll(
                () -> assertTrue(srcFile.exists(), String.format("srcFile %s", srcFile.getPath())),
                () -> assertTrue(destinationFile.exists(), String.format("destinationFile %s", destinationFile)),
                () -> assertEquals(expectedLength, destinationFile.length())
        );
    }

}
