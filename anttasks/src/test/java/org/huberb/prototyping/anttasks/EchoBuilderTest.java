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

import org.apache.tools.ant.taskdefs.Echo;
import org.apache.tools.ant.types.resources.StringResource;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class EchoBuilderTest {

    public EchoBuilderTest() {
    }

    @Test
    public void given_some_text_echo_it() {
        final AntTasksBuilder antTasksBuilder = new AntTasksBuilder();
        final Echo echo = new EchoBuilder(antTasksBuilder.project)
                .message("Hello world!")
                .build();
        echo.execute();
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
}
