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


import org.huberb.prototyping.anttasks.AntTasksBuilder;
import java.io.File;
import org.apache.tools.ant.taskdefs.Concat;
import org.apache.tools.ant.taskdefs.Echo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class AntTasksBuilderTest {

    public AntTasksBuilderTest() {
    }

    @Test
    public void testEcho() {
        AntTasksBuilder antTasksBuilder = new AntTasksBuilder();
        antTasksBuilder.name("project1");
        Echo echo = antTasksBuilder.echo("Hello world!");
        //---
        echo.execute();
    }

    @Test
    public void testConcat() {
        AntTasksBuilder antTasksBuilder = new AntTasksBuilder();
        antTasksBuilder.name("project1");
        Concat concat = antTasksBuilder.concat("Hello world!", "target/concat1.txt");
        //---
        concat.execute();

        assertTrue(new File("target/concat1.txt").canRead());
    }

}
