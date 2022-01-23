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
import org.apache.tools.ant.taskdefs.Available;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Length;
import org.apache.tools.ant.taskdefs.Touch;

/**
 * Build ant task action.
 *
 * @author berni3
 */
public class AntTasksBuilder {

    final Project project;

    public AntTasksBuilder() {
        // setup project
        this.project = new Project();

        final BuildLogger logger = new NoBannerLogger();
        logger.setMessageOutputLevel(org.apache.tools.ant.Project.MSG_DEBUG);
        logger.setOutputPrintStream(System.out);
        logger.setErrorPrintStream(System.err);

        project.addBuildListener(logger);
        project.init();
        project.getBaseDir();
    }

    public AntTasksBuilder name(String name) {
        this.project.setName(name);
        return this;
    }

    void executeTarget(String targetName) {
        project.executeTarget(targetName);
    }

    public Touch touch(String source) {
        final Touch touch = (Touch) project.createTask("touch");

        touch.setFile(new File(source));
        return touch;
    }

    public Available available(String source) {
        final Available touch = (Available) project.createTask("available");

        touch.setFile(new File(source));
        touch.setProperty("available");
        return touch;
    }

    public Length length(String source) {
        final Length touch = (Length) project.createTask("length");

        touch.setFile(new File(source));
        touch.setProperty("length");
        return touch;
    }

    public Expand unzip(String zipFilepath, String destinationDir) {
        final Expand expand = (Expand) project.createTask("unzip");

        expand.setSrc(new File(zipFilepath));
        expand.setDest(new File(destinationDir));
        return expand;
    }
}
