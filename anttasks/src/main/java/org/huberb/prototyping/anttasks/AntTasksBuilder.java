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

import org.apache.tools.ant.BuildLogger;
import org.apache.tools.ant.NoBannerLogger;
import org.apache.tools.ant.Project;

/**
 * Build ant task action.
 *
 * @author berni3
 * @version $Id: $Id
 */
public class AntTasksBuilder {

    final Project project;

    /**
     * <p>Constructor for AntTasksBuilder.</p>
     */
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

    /**
     * <p>name.</p>
     *
     * @param name a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.AntTasksBuilder} object
     */
    public AntTasksBuilder name(String name) {
        this.project.setName(name);
        return this;
    }

    void executeTarget(String targetName) {
        project.executeTarget(targetName);
    }

}
