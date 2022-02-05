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

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Echo;
import org.apache.tools.ant.taskdefs.Echo.EchoLevel;
import org.apache.tools.ant.types.resources.StringResource;

/**
 * An ant {@link Echo} builder.
 *
 * @author berni3
 */
public class EchoBuilder {

    private final Echo echo;

    public EchoBuilder(Project project) {
        this.echo = (Echo) project.createTask("echo");
        this.echo.init();
    }

    public EchoBuilder append(boolean append) {
        echo.setAppend(append);
        return this;
    }

    public EchoBuilder append(String msg) {
        echo.addText(msg);
        return this;
    }

    public EchoBuilder echoLevel(EchoLevel echoLevel) {
        echo.setLevel(echoLevel);
        return this;
    }

    public EchoBuilder echoLevel(String echoLevelAsString) {
        final EchoLevel echoLevel = new EchoLevel();
        echoLevel.setValue(echoLevelAsString);
        return this.echoLevel(echoLevel);
    }

    public EchoBuilder message(String msg) {
        echo.setMessage(msg);
        return this;
    }

    public EchoBuilder output(StringResource output) {
        echo.setOutput(output);
        return this;
    }

    public Echo build() {
        return echo;
    }

}
