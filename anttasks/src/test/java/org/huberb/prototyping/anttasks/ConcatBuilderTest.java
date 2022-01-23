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
import java.nio.file.Path;
import org.apache.tools.ant.taskdefs.Concat;
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
public class ConcatBuilderTest {

    @TempDir
    private static Path sharedTempDir;

    public ConcatBuilderTest() {
    }

    @Test
    public void testConcat() {
        assertNotNull(sharedTempDir);

        final Path destination = sharedTempDir.resolve("concat-test-file1.txt");
        final File destinationFile = destination.toFile();
        final AntTasksBuilder antTasksBuilder = new AntTasksBuilder();

        final Concat concat = new ConcatBuilder(antTasksBuilder.project)
                .concat("Hello world!")
                .destination(destinationFile.getPath())
                .build();
        Assertions.assertAll(
                () -> assertEquals("concat", concat.getTaskName()),
                () -> assertEquals("concat", concat.getTaskType())
        );
        //---
        concat.execute();

        assertTrue(destinationFile.canRead());
    }

}
