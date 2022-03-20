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
import org.apache.tools.ant.taskdefs.GUnzip;

/**
 * An ant {@link org.apache.tools.ant.taskdefs.GUnzip} builder.
 *
 * @author berni3
 * @version $Id: $Id
 */
public class GUnzipBuilder {

    private final GUnzip gunzip;

    /**
     * Constructor for GUnzipBuilder.
     *
     * @param project a {@link org.apache.tools.ant.Project} object
     */
    public GUnzipBuilder(Project project) {
        this.gunzip = (GUnzip) project.createTask("gunzip");
    }

    /**
     * Define the source of gunzip task.
     *
     * @param source a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.GUnzipBuilder} object
     */
    public GUnzipBuilder src(String source) {
        gunzip.setSrc(new File(source));
        return this;
    }

    /**
     * Define the destination of gunzip task.
     *
     * @param destination a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.GUnzipBuilder} object
     */
    public GUnzipBuilder dest(String destination) {
        gunzip.setDest(new File(destination));
        return this;
    }

    /**
     * Build the gunzip task.
     *
     * @return a {@link org.apache.tools.ant.taskdefs.GUnzip} object
     */
    public GUnzip build() {
        return gunzip;
    }

}
