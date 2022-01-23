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

    public static class TouchBuilder {

        private final Touch touch;

        public TouchBuilder(Project project) {
            this.touch = (Touch) project.createTask("touch");
        }

        public TouchBuilder file(String source) {

            touch.setFile(new File(source));
            return this;
        }

        public Touch build() {
            return touch;
        }
    }

    public static class AvailableBuilder {

        private final Available available;

        public AvailableBuilder(Project project) {
            this.available = (Available) project.createTask("available");
            available.setProperty("available");
        }

        public AvailableBuilder file(String source) {
            available.setFile(new File(source));
            return this;
        }

        public Available build() {
            return available;
        }
    }

    public Length length(String source) {
        final Length touch = (Length) project.createTask("length");

        touch.setFile(new File(source));
        touch.setProperty("length");
        return touch;
    }

    public static class ExpandBuilder {

        private final Expand expand;

        public ExpandBuilder(Project project) {
            this.expand = (Expand) project.createTask("unzip");
        }

        public ExpandBuilder src(String zipFilepath) {
            expand.setSrc(new File(zipFilepath));
            return this;
        }

        public ExpandBuilder dest(String destinationDir) {
            expand.setDest(new File(destinationDir));
            return this;
        }

        public Expand build() {
            return expand;
        }
    }
}
