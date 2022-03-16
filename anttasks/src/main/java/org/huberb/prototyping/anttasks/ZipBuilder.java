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
import org.apache.tools.ant.taskdefs.Zip;

/**
 * An ant {@link org.apache.tools.ant.taskdefs.Zip} builder.
 *
 * @author berni3
 * @version $Id: $Id
 */
public class ZipBuilder {

    private final Zip zip;

    /**
     * <p>Constructor for ZipBuilder.</p>
     *
     * @param project a {@link org.apache.tools.ant.Project} object
     */
    public ZipBuilder(Project project) {
        this.zip = (Zip) project.createTask("zip");
this.zip.init();
    }

    /**
     * <p>destFile.</p>
     *
     * @param destFile a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.ZipBuilder} object
     */
    public ZipBuilder destFile(String destFile) {
        zip.setDestFile(new File(destFile));
        return this;
    }

    /**
     * <p>basedir.</p>
     *
     * @param basedir a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.ZipBuilder} object
     */
    public ZipBuilder basedir(String basedir) {
        zip.setBasedir(new File(basedir));
        return this;
    }

    /**
     * <p>includes.</p>
     *
     * @param includes a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.ZipBuilder} object
     */
    public ZipBuilder includes(String includes) {
        zip.setIncludes(includes);
        return this;
    }

    /**
     * <p>excludes.</p>
     *
     * @param excludes a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.ZipBuilder} object
     */
    public ZipBuilder excludes(String excludes) {
        zip.setExcludes(excludes);
        return this;
    }

    /**
     * <p>build.</p>
     *
     * @return a {@link org.apache.tools.ant.taskdefs.Zip} object
     */
    public Zip build() {
        return zip;
    }

}
