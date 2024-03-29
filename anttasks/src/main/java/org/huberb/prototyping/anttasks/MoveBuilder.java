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
import org.apache.tools.ant.taskdefs.Move;

/**
 * An ant {@link org.apache.tools.ant.taskdefs.Move} builder.
 *
 * @author berni3
 * @version $Id: $Id
 */
public class MoveBuilder {

    private final Move move;

    /**
     * Constructor for MoveBuilder.
     *
     * @param project a {@link org.apache.tools.ant.Project} object
     */
    public MoveBuilder(Project project) {
        this.move = (Move) project.createTask("move");
        move.setOverwrite(false);
    }

    /**
     * Define the source file of the move task.
     *
     * @param file a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.MoveBuilder} object
     */
    public MoveBuilder file(String file) {
        this.move.setFile(new File(file));
        return this;
    }

    /**
     * Define the destination file of the move task.
     *
     * @param tofile a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.MoveBuilder} object
     */
    public MoveBuilder tofile(String tofile) {
        this.move.setTofile(new File(tofile));
        return this;
    }

    /**
     * Define the destination directory of the move task.
     *
     * @param todir a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.MoveBuilder} object
     */
    public MoveBuilder todir(String todir) {
        this.move.setTodir(new File(todir));
        return this;
    }

    /**
     * Build the ant move task.
     *
     * @return a {@link org.apache.tools.ant.taskdefs.Move} object
     */
    public Move build() {
        return move;
    }

}
