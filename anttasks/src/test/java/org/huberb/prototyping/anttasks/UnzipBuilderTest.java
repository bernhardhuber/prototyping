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
import java.net.URISyntaxException;
import java.nio.file.Path;
import org.apache.tools.ant.taskdefs.Expand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 *
 * @author berni3
 */
public class UnzipBuilderTest {

    @TempDir
    static Path sharedTempDir;

    @Test
    public void testUnzip() throws URISyntaxException {
        Assertions.assertNotNull(sharedTempDir);
        final File zipFilepath = new File("target/test-classes/sample.zip");
        Assertions.assertTrue(zipFilepath.exists(), String.format("zipFilepath %s", zipFilepath.getPath()));
        final File destinationDirFile = sharedTempDir.resolve("unzip-test-dir1").toFile();
        Assertions.assertTrue(destinationDirFile.mkdirs());
        Assertions.assertTrue(destinationDirFile.exists(), String.format("destinationDirFile %s", destinationDirFile.getPath()));
        //---
        final Expand expand = new AntTasksBuilder().unzip(zipFilepath.getPath(), destinationDirFile.getPath());
        expand.execute();
        //---
        final File f1 = new File(destinationDirFile, "sample-3rows2cols.csv");
        final File f2 = new File(destinationDirFile, "sample-lorem-ipsum.md");
        Assertions.assertAll(
                () -> Assertions.assertTrue(f1.exists(), String.format("unzipped file %s", f1.getPath())),
                () -> Assertions.assertTrue(f2.exists(), String.format("unzipped file %s", f2.getPath()))
        );
    }

}
