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

import java.nio.file.Path;
import org.apache.tools.ant.taskdefs.Mkdir;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 *
 * @author berni3
 */
public class MkdirBuilderTest {

    @TempDir
    private static Path sharedTempDir;

    @Test
    public void testMkdir() {
        Assertions.assertNotNull(sharedTempDir);
        final Path mkdirTestDir1 = sharedTempDir.resolve("mkdir-test-dir1");
        final String mkdirTestDir1AsString = mkdirTestDir1.toFile().getPath();
        final String m = String.format("Create directory %s", mkdirTestDir1AsString);
        Assertions.assertFalse(mkdirTestDir1.toFile().exists(), m);
        final AntTasksBuilder antTasksBuilder = new AntTasksBuilder();
        final Mkdir mkdir = new MkdirBuilder(antTasksBuilder.project).dir(mkdirTestDir1AsString).build();
        mkdir.execute();
        Assertions.assertTrue(mkdirTestDir1.toFile().exists(), m);
    }

}
