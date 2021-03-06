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


    public static void createFileContent(File aFile, String content, int repeatCount) throws IOException {
        try ( FileWriter fw = new FileWriter(aFile, Charset.forName("UTF-8"))) {
            for (int i = 0; i < repeatCount; i++) {
                fw.append(content);
            }
        }
    }
}
