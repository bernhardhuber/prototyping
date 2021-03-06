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
import org.apache.tools.ant.taskdefs.GUnzip;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 *
 * @author berni3
 */
public class GunzipBuilderTest {

    @TempDir
    private static Path sharedTempDir;

    @Test
    public void given_an_existing_gzip_file_then_gunzip_it() throws IOException {
        Assertions.assertNotNull(sharedTempDir);
        final File gzippedFile = new File("target/test-classes/sample-lorem-ipsum.md.gz");
        Assertions.assertTrue(gzippedFile.exists());
        final Path gunzippedPath = sharedTempDir.resolve("gunzipped-test-file1.md");
        final File gunzippedFile = gunzippedPath.toFile();
        Assertions.assertFalse(gunzippedFile.exists());
        //---
        final AntTasksBuilder antTasksBuilder = new AntTasksBuilder();
        final GUnzip gunzip = new GUnzipBuilder(antTasksBuilder.project)
                .src(gzippedFile.getPath())
                .dest(gunzippedFile.getPath())
                .build();
        gunzip.execute();
        //---
        Assertions.assertAll(
                () -> Assertions.assertTrue(gzippedFile.exists()),
                () -> Assertions.assertTrue(gunzippedFile.exists())
        );
    }

}
