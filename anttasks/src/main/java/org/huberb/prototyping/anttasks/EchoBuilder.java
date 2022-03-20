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
 * An ant {@link org.apache.tools.ant.taskdefs.Echo} builder.
 *
 * @author berni3
 * @version $Id: $Id
 */
public class EchoBuilder {

    private final Echo echo;

    /**
     * Constructor for EchoBuilder.
     *
     * @param project a {@link org.apache.tools.ant.Project} object
     */
    public EchoBuilder(Project project) {
        this.echo = (Echo) project.createTask("echo");
        this.echo.init();
    }

    /**
     * Operate built echo task in append mode.
     *
     * @param append a boolean
     * @return a {@link org.huberb.prototyping.anttasks.EchoBuilder} object
     */
    public EchoBuilder append(boolean append) {
        echo.setAppend(append);
        return this;
    }

    /**
     * Append an echo text.
     *
     * @param msg a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.EchoBuilder} object
     */
    public EchoBuilder append(String msg) {
        echo.addText(msg);
        return this;
    }

    /**
     * Define the echoLevel.
     *
     * @param echoLevel a {@link org.apache.tools.ant.taskdefs.Echo.EchoLevel}
     * object
     * @return a {@link org.huberb.prototyping.anttasks.EchoBuilder} object
     * @see EchoLevel
     */
    public EchoBuilder echoLevel(EchoLevel echoLevel) {
        echo.setLevel(echoLevel);
        return this;
    }

    /**
     * Define the echoLevel from string value.
     * <p>
     * Valid values are: "error", "warn", "warning", "info", "verbose", "debug"
     *
     * @param echoLevelAsString a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.EchoBuilder} object
     *
     */
    public EchoBuilder echoLevel(String echoLevelAsString) {
        final EchoLevel echoLevel = new EchoLevel();
        echoLevel.setValue(echoLevelAsString);
        return this.echoLevel(echoLevel);
    }

    /**
     * Define the echo message.
     *
     * @param msg a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.EchoBuilder} object
     */
    public EchoBuilder message(String msg) {
        echo.setMessage(msg);
        return this;
    }

    /**
     * Define the output of the echo task.
     *
     * @param output a
     * {@link org.apache.tools.ant.types.resources.StringResource} object
     * @return a {@link org.huberb.prototyping.anttasks.EchoBuilder} object
     */
    public EchoBuilder output(StringResource output) {
        echo.setOutput(output);
        return this;
    }

    /**
     * Build echo task.
     *
     * @return a {@link org.apache.tools.ant.taskdefs.Echo} object
     */
    public Echo build() {
        return echo;
    }

}
