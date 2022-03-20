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
import org.apache.tools.ant.taskdefs.Expand;

/**
 * An ant {@link org.apache.tools.ant.taskdefs.Expand} builder.
 *
 * @author berni3
 * @version $Id: $Id
 */
public class ExpandBuilder {

    private final Expand expand;

    /**
     * Constructor for ExpandBuilder.
     *
     * @param project a {@link org.apache.tools.ant.Project} object
     */
    public ExpandBuilder(Project project) {
        this.expand = (Expand) project.createTask("unzip");
    }

    /**
     * Define the source for expansion task.
     *
     * @param zipFilepath a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.ExpandBuilder} object
     */
    public ExpandBuilder src(String zipFilepath) {
        expand.setSrc(new File(zipFilepath));
        return this;
    }

    /**
     * Define the destination of expansion task.
     *
     * @param destinationDir a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.ExpandBuilder} object
     */
    public ExpandBuilder dest(String destinationDir) {
        expand.setDest(new File(destinationDir));
        return this;
    }

    /**
     * Build the ant expansion task.
     *
     * @return a {@link org.apache.tools.ant.taskdefs.Expand} object
     */
    public Expand build() {
        return expand;
    }

}
