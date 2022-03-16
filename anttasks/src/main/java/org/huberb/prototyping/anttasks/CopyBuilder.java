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
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.types.FileSet;

/**
 * An ant {@link org.apache.tools.ant.taskdefs.Copy} builder.
 *
 * @author berni3
 * @version $Id: $Id
 */
public class CopyBuilder {

    private final Copy copy;

    /**
     * <p>Constructor for CopyBuilder.</p>
     *
     * @param project a {@link org.apache.tools.ant.Project} object
     */
    public CopyBuilder(Project project) {
        this.copy = (Copy) project.createTask("copy");
        this.copy.init();
        copy.setOverwrite(false);
    }

    /**
     * <p>file.</p>
     *
     * @param f a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.CopyBuilder} object
     */
    public CopyBuilder file(String f) {
        this.copy.setFile(new File(f));
        return this;
    }

    /**
     * <p>tofile.</p>
     *
     * @param f a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.CopyBuilder} object
     */
    public CopyBuilder tofile(String f) {
        this.copy.setTofile(new File(f));
        return this;
    }

    /**
     * <p>addfileset.</p>
     *
     * @param fileSet a {@link org.apache.tools.ant.types.FileSet} object
     * @return a {@link org.huberb.prototyping.anttasks.CopyBuilder} object
     */
    public CopyBuilder addfileset(FileSet fileSet) {
        this.copy.addFileset(fileSet);
        return this;
    }

    /**
     * <p>todir.</p>
     *
     * @param d a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.CopyBuilder} object
     */
    public CopyBuilder todir(String d) {
        this.copy.setTodir(new File(d));
        return this;
    }

    /**
     * <p>build.</p>
     *
     * @return a {@link org.apache.tools.ant.taskdefs.Copy} object
     */
    public Copy build() {
        return this.copy;
    }


}
