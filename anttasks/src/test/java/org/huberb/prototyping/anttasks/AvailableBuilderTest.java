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
import org.apache.tools.ant.taskdefs.Available;
import org.huberb.prototyping.anttasks.AntTasksBuilder.AvailableBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 *
 * @author berni3
 */
public class AvailableBuilderTest {

    @TempDir
    private static Path sharedTempDir;

    @Test
    public void testAvailable() throws IOException {
        Assertions.assertNotNull(sharedTempDir);
        final Path availablePath = sharedTempDir.resolve("available-test-file1");
        final File availableFile = availablePath.toFile();
        Assertions.assertFalse(availableFile.exists());
        Assertions.assertTrue(availableFile.createNewFile());
        //---
        final AntTasksBuilder antTasksBuilder = new AntTasksBuilder();
        final Available available = new AvailableBuilder(antTasksBuilder.project).file(availableFile.getPath()).build();
        available.execute();
        //---
        Assertions.assertAll(() -> Assertions.assertTrue(availableFile.exists()), () -> Assertions.assertEquals("true", available.getProject().getProperty("available")));
    }

}
