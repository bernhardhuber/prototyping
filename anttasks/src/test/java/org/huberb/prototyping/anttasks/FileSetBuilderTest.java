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
import java.util.Arrays;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.types.FileSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 *
 * @author berni3
 */
public class FileSetBuilderTest {

    @TempDir
    private static Path sharedTempDir;

    public FileSetBuilderTest() {
    }

    /**
     * Test of dir method, of class FileSetBuilder.
     */
    @Test
    public void given_files_in_basedir_match_extensions() throws IOException {
        assertNotNull(sharedTempDir);
        for (final Path p : Arrays.asList(
                sharedTempDir.resolve("testXXX-file1-1.ext1"),
                sharedTempDir.resolve("testXXX-file2-1.ext2"),
                sharedTempDir.resolve("testXXX-file2-2.ext2"),
                sharedTempDir.resolve("testXXX-file3-1.ext3"),
                sharedTempDir.resolve("testXXX-file4-1.ext4"))) {
            final File file = p.toFile();
            assertTrue(file.createNewFile(), "" + p.toFile());
        }

        final AntTasksBuilder antTasksBuilder = new AntTasksBuilder();
        final String dir = sharedTempDir.toFile().getPath();
        final FileSetBuilder instance = new FileSetBuilder();
        instance.dir(dir)
                .includes("*.ext2");
        final FileSet fileSet = instance.build();
        final DirectoryScanner directoryScanner = fileSet.getDirectoryScanner(antTasksBuilder.project);
        assertEquals(dir, directoryScanner.getBasedir().getPath());
        assertEquals("[]", Arrays.asList(directoryScanner.getIncludedDirectories()).toString());
        assertEquals("[testXXX-file2-1.ext2, testXXX-file2-2.ext2]", Arrays.asList(directoryScanner.getIncludedFiles()).toString());
    }

    /**
     * Test of dir method, of class FileSetBuilder.
     */
    @Test
    public void given_files_in_subdirs_match_extensions() throws IOException {
        assertNotNull(sharedTempDir);
        for (final Path p : Arrays.asList(
                sharedTempDir.resolve("test-dir1/testXXX-file1-1.ext1"),
                sharedTempDir.resolve("test-dir2/testXXX-file2-1.ext2"),
                sharedTempDir.resolve("test-dir3/testXXX-file2-2.ext2"),
                sharedTempDir.resolve("test-dir4/testXXX-file3-1.ext3"),
                sharedTempDir.resolve("test-dir5/testXXX-file4-1.ext4"))) {
            final File parentFile = p.toFile().getParentFile();
            final File file = p.toFile();
            assertTrue(parentFile.mkdir(), "" + parentFile);
            assertTrue(file.createNewFile(), "" + file);
        }

        final AntTasksBuilder antTasksBuilder = new AntTasksBuilder();
        final String dir = sharedTempDir.toFile().getPath();
        final FileSetBuilder instance = new FileSetBuilder();
        instance.dir(dir)
                .includes("**/*.ext2");
        final FileSet fileSet = instance.build();
        final DirectoryScanner directoryScanner = fileSet.getDirectoryScanner(antTasksBuilder.project);
        assertEquals(dir, directoryScanner.getBasedir().getPath());
        assertEquals("[]", Arrays.asList(directoryScanner.getIncludedDirectories()).toString());
        /* Fix separator char \\ under windows, and / under unix
            Error:  Failures: 
            Error:    FileSetBuilderTest.given_files_in_subdirs_match_extensions:97 
            expected: <[test-dir2\testXXX-file2-1.ext2, test-dir3\testXXX-file2-2.ext2, testXXX-file2-1.ext2, testXXX-file2-2.ext2]> 
            but was:  <[test-dir2/testXXX-file2-1.ext2, test-dir3/testXXX-file2-2.ext2, testXXX-file2-1.ext2, testXXX-file2-2.ext2]>
         */
        final String expected = "["
                + "test-dir2\\testXXX-file2-1.ext2, "
                + "test-dir3\\testXXX-file2-2.ext2, "
                + "testXXX-file2-1.ext2, "
                + "testXXX-file2-2.ext2"
                + "]";
        final String expectedNormalized = normalizeFileSeparator(expected);
        assertEquals(expectedNormalized,
                normalizeFileSeparator(Arrays.asList(directoryScanner.getIncludedFiles()).toString())
        );
    }

    private String normalizeFileSeparator(String fileName) {
        final String fileNameNormalized = fileName.replace('\\', '/');
        return fileNameNormalized;
    }

}
