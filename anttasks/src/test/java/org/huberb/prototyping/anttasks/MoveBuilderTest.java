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
import org.apache.tools.ant.taskdefs.Move;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 *
 * @author berni3
 */
public class MoveBuilderTest {

    @TempDir
    private static Path sharedTempDir;

    @Test
    public void testMove() throws IOException {
        Assertions.assertNotNull(sharedTempDir);
        final String source = sharedTempDir.resolve("move-test-source1.txt").toString();
        final String dest = sharedTempDir.resolve("move-test-dest1.txt").toString();
        final File sourceFile = new File(source);
        sourceFile.createNewFile();
        Assertions.assertTrue(sourceFile.exists(), String.format("sourceFile %s", sourceFile.getPath()));
        final File destFile = new File(dest);
        Assertions.assertFalse(destFile.exists(), String.format("destFile %s", destFile.getPath()));
        //---
        final AntTasksBuilder antTasksBuilder = new AntTasksBuilder();
        final Move move = new MoveBuilder(antTasksBuilder.project)
                .file(source)
                .tofile(dest)
                .build();
        move.execute();
        //---
        Assertions.assertAll(
                () -> Assertions.assertFalse(sourceFile.exists(), String.format("sourceFile %s", sourceFile.getPath())),
                () -> Assertions.assertTrue(destFile.exists(), String.format("destFIle %s", destFile.getPath()))
        );
    }

}
