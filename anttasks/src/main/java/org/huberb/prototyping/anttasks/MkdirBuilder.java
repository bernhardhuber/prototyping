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
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Mkdir;

/**
 * An ant {@link org.apache.tools.ant.taskdefs.Mkdir} builder.
 *
 * @author berni3
 * @version $Id: $Id
 */
public class MkdirBuilder {

    private final Mkdir mkdir;

    /**
     * Constructor for MkdirBuilder.
     *
     * @param project a {@link org.apache.tools.ant.Project} object
     */
    public MkdirBuilder(Project project) {
        this.mkdir = (Mkdir) project.createTask("mkdir");
    }

    /**
     * Define the directory to be created.
     *
     * @param dir a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.MkdirBuilder} object
     */
    public MkdirBuilder dir(String dir) {
        final File dirFile = new File(dir);
        mkdir.setDir(dirFile);
        return this;
    }

    /**
     * Build the mkdir ant task.
     *
     * @return a {@link org.apache.tools.ant.taskdefs.Mkdir} object
     */
    public Mkdir build() {
        return mkdir;
    }

}
