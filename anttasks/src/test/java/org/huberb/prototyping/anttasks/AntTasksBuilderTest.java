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
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import org.apache.tools.ant.taskdefs.Length;
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
public class AntTasksBuilderTest {

    @TempDir
    private static Path sharedTempDir;

    public AntTasksBuilderTest() {
    }

    @Test
    public void testLength() throws IOException {
        assertNotNull(sharedTempDir);

        final Path lengthPath = sharedTempDir.resolve("length-test-file1");
        final File lengthFile = lengthPath.toFile();
        assertFalse(lengthFile.exists(), String.format("lengthFile %s", lengthFile.getPath()));
        assertTrue(lengthFile.createNewFile());
        //---
        final Length length = new AntTasksBuilder().length(lengthFile.getPath());
        length.execute();
        //---
        Assertions.assertAll(
                () -> assertTrue(lengthFile.exists(), String.format("lengthFile %s", lengthFile.getPath())),
                () -> assertEquals("0", length.getProject().getProperty("length"))
        );
    }

    public static void createFileContent(File aFile, String content, int repeatCount) throws IOException {
        try ( FileWriter fw = new FileWriter(aFile, Charset.forName("UTF-8"))) {
            for (int i = 0; i < repeatCount; i++) {
                fw.append(content);
            }
        }
    }
}
