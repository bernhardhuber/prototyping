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
import org.apache.tools.ant.taskdefs.GZip;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 *
 * @author berni3
 */
public class GzipBuilderTest {

    @TempDir
    private static Path sharedTempDir;

    @Test
    public void testGzip() throws IOException {
        Assertions.assertNotNull(sharedTempDir);
        final Path gzipPath = sharedTempDir.resolve("gzip-test-file1.txt");
        final File gzipFile = gzipPath.toFile();
        Assertions.assertFalse(gzipFile.exists(), String.format("gzipFile %s", gzipFile.getPath()));
        final File gzippedFile = new File(gzipFile.getPath() + ".gz");
        Assertions.assertFalse(gzippedFile.exists(), String.format("gzippedFile %s", gzippedFile.getPath()));
        //---
        Assertions.assertTrue(gzipFile.createNewFile());
        AntTasksBuilderTest.createFileContent(gzipFile, "testGzipContent", 100);
        //---
        final AntTasksBuilder antTasksBuilder = new AntTasksBuilder();
        final GZip gzip = new GZipBuilder(antTasksBuilder.project)
                .src(gzipFile.getPath())
                .destfile(gzippedFile.getPath())
                .build();
        gzip.execute();
        //---
        Assertions.assertAll(
                () -> Assertions.assertTrue(gzipFile.exists(), String.format("gzipFile %s", gzipFile.getPath())),
                () -> Assertions.assertTrue(gzippedFile.exists(), String.format("gzippedFile %s", gzippedFile))
        );
    }

}
