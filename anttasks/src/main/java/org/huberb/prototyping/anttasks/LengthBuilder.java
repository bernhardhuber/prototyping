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
import org.apache.tools.ant.taskdefs.Length;

/**
 * An ant {@link org.apache.tools.ant.taskdefs.Length} builder.
 *
 * @author berni3
 * @version $Id: $Id
 */
public class LengthBuilder {

    private final Length length;

    /**
     * Constructor for LengthBuilder.
     *
     * @param project a {@link org.apache.tools.ant.Project} object
     */
    public LengthBuilder(Project project) {
        this.length = (Length) project.createTask("length");
        this.length.setProperty("length");
    }

    /**
     * Sets the file for which the length is calculated.
     *
     * @param file calculate the length of this file
     * @return a {@link org.huberb.prototyping.anttasks.LengthBuilder} object
     */
    public LengthBuilder file(String file) {
        length.setFile(new File(file));
        return this;
    }

    /**
     * Build the length ant task.
     *
     * @return a {@link org.apache.tools.ant.taskdefs.Length} object
     */
    public Length build() {
        return length;
    }

}
