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
import org.apache.tools.ant.BuildLogger;
import org.apache.tools.ant.NoBannerLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Concat;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.taskdefs.Echo;
import org.apache.tools.ant.taskdefs.Expand;

/**
 *
 * @author berni3
 */
public class AntTasksBuilder {

    final Project project;

    AntTasksBuilder() {
        project = new Project();
        final BuildLogger logger = new NoBannerLogger();

        logger.setMessageOutputLevel(org.apache.tools.ant.Project.MSG_DEBUG);
        logger.setOutputPrintStream(System.out);
        logger.setErrorPrintStream(System.err);

        project.addBuildListener(logger);

        project.init();
        project.getBaseDir();
    }

    AntTasksBuilder name(String name) {
        this.project.setName(name);
        return this;
    }

    void executeTarget(String targetName) {
        project.executeTarget(targetName);
    }

    Expand unzip(String zipFilepath, String destinationDir) {
        final Expand expand = (Expand) project.createTask("unzip");
        expand.setTaskName("unzip");

        expand.setSrc(new File(zipFilepath));
        expand.setDest(new File(destinationDir));
        return expand;
    }

    Copy copy(String sourceFile, String destFile) {
        final Copy copy = (Copy) project.createTask("copy");
        copy.setTaskName("copy");
        copy.setFile(new File(sourceFile));
        copy.setTofile(new File(destFile));
        return copy;
    }

    Echo echo(String message) {
        final Echo echo = (Echo) project.createTask("echo");
        echo.setTaskName("echo");
        echo.setMessage(message);
        return echo;
    }

    Concat concat(String text, String destinationFile) {
        Concat concat = (Concat) project.createTask("concat");
        concat.addText(text);
        concat.setDestfile(new File(destinationFile));
        return concat;
    }
}
