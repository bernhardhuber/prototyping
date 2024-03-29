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
import org.apache.tools.ant.taskdefs.Concat;

/**
 * An ant {@link org.apache.tools.ant.taskdefs.Concat} builder.
 *
 * @author berni3
 * @version $Id: $Id
 */
public class ConcatBuilder {

    private final Concat concat;

    /**
     * Constructor for ConcatBuilder.
     *
     * @param project a {@link org.apache.tools.ant.Project} object
     */
    public ConcatBuilder(Project project) {
        this.concat = (Concat) project.createTask("concat");
    }

    /**
     * Add a text for concatenation.
     *
     * @param text a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.ConcatBuilder} object
     */
    public ConcatBuilder addText(String text) {
        concat.addText(text);
        return this;
    }

    /**
     * Sets the destination file of the concatenation.
     *
     * @param destination a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.ConcatBuilder} object
     */
    public ConcatBuilder destination(String destination) {
        final File destinationFile = new File(destination);
        concat.setDestfile(destinationFile);
        return this;
    }

    /**
     * Build the concat ant task.
     *
     * @return a {@link org.apache.tools.ant.taskdefs.Concat} object
     */
    public Concat build() {
        return concat;
    }

}
