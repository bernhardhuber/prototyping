package org.huberb.prototyping.anttasks;

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
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import org.apache.tools.ant.taskdefs.Concat;
import org.apache.tools.ant.taskdefs.Echo;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Mkdir;
import org.apache.tools.ant.taskdefs.Move;
import org.apache.tools.ant.types.resources.StringResource;
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
    static Path sharedTempDir;

    public AntTasksBuilderTest() {
    }

    @Test
    public void testConcat() {
        final Concat concat = new AntTasksBuilder()
                .name("project1")
                .concat("Hello world!", "target/concat1.txt");
        Assertions.assertAll(
                () -> assertEquals("concat", concat.getTaskName()),
                () -> assertEquals("concat", concat.getTaskType())
        );
        //---
        concat.execute();

        assertTrue(new File("target/concat1.txt").canRead());
    }

    @Test
    public void testCopy() {
        assertNotNull(sharedTempDir);
    }

    @Test
    public void testEcho() {
        final Echo echo = new AntTasksBuilder()
                .name("echo1")
                .echo("Hello world!");
        Assertions.assertAll(
                () -> assertEquals("echo", echo.getTaskName()),
                () -> assertEquals("echo", echo.getTaskType())
        );

        //---
        final StringResource sr = new StringResource();
        echo.setOutput(sr);
        echo.execute();

        assertEquals("Hello world!", sr.getValue());
    }

    @Test
    public void testMkdir() {
        assertNotNull(sharedTempDir);

        final Path mkdirTestDir1 = sharedTempDir.resolve("mkdir-test-dir1");
        final String mkdirTestDir1AsString = mkdirTestDir1.toFile().getPath();
        final String m = String.format("Create directory %s", mkdirTestDir1AsString);
        assertFalse(mkdirTestDir1.toFile().exists(), m);
        final Mkdir mkdir = new AntTasksBuilder().mkdir(mkdirTestDir1AsString);
        mkdir.execute();
        assertTrue(mkdirTestDir1.toFile().exists(), m);
    }

    @Test
    public void testMove() throws IOException {
        assertNotNull(sharedTempDir);
        String source = sharedTempDir.resolve("source.txt").toString();
        String dest = sharedTempDir.resolve("dest.txt").toString();
        File sourceFile = new File(source);
        sourceFile.createNewFile();
        assertTrue(sourceFile.exists(), String.format("sourceFile %s", sourceFile.getPath()));
        final File destFile = new File(dest);
        assertFalse(destFile.exists(), String.format("destFile %s", destFile.getPath()));
        //---
        final Move move = new AntTasksBuilder().move(source, dest);
        move.execute();
        //---
        assertFalse(sourceFile.exists(), String.format("sourceFile %s", sourceFile.getPath()));
        assertTrue(destFile.exists(), String.format("destFIle %s", destFile.getPath()));
    }

    @Test
    public void testUnzip() throws URISyntaxException {
        assertNotNull(sharedTempDir);
        final File zipFilepath = new File("target/test-classes/sample.zip");
        assertTrue(zipFilepath.exists(), String.format("zipFilepath %s", zipFilepath.getPath()));
        final File destinationDirFile = sharedTempDir.resolve("unzip-dir1").toFile();
        assertTrue(destinationDirFile.mkdirs());
        assertTrue(destinationDirFile.exists(), String.format("destinationDirFile %s", destinationDirFile.getPath()));
        //---
        final Expand expand = new AntTasksBuilder().unzip(zipFilepath.getPath(), destinationDirFile.getPath());
        expand.execute();
        //---
        final File f1 = new File(destinationDirFile, "sample-3rows2cols.csv");
        assertTrue(f1.exists(), String.format("unzipped file %s", f1.getPath()));
        final File f2 = new File(destinationDirFile, "sample-lorem-ipsum.md");
        assertTrue(f2.exists(), String.format("unzipped file %s", f2.getPath()));
    }

}
