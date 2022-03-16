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
import org.apache.tools.ant.taskdefs.Available;

/**
 * An ant {@link org.apache.tools.ant.taskdefs.Available} builder.
 *
 * @author berni3
 * @version $Id: $Id
 */
public class AvailableBuilder {

    private final Available available;

    /**
     * <p>Constructor for AvailableBuilder.</p>
     *
     * @param project a {@link org.apache.tools.ant.Project} object
     */
    public AvailableBuilder(Project project) {
        this.available = (Available) project.createTask("available");
        this.available.init();
        available.setProperty("available");
    }

    /**
     * <p>file.</p>
     *
     * @param source a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.AvailableBuilder} object
     */
    public AvailableBuilder file(String source) {
        available.setFile(new File(source));
        return this;
    }

    /**
     * <p>build.</p>
     *
     * @return a {@link org.apache.tools.ant.taskdefs.Available} object
     */
    public Available build() {
        return available;
    }

}
