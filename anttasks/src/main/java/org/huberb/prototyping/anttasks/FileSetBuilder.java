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
import java.util.List;
import org.apache.tools.ant.types.FileSet;

/**
 * An ant {@link org.apache.tools.ant.types.FileSet} builder.
 *
 * @author berni3
 * @version $Id: $Id
 */
public class FileSetBuilder {

    private final FileSet fileSet;

    /**
     * Constructor for FileSetBuilder.
     */
    public FileSetBuilder() {
        this.fileSet = new FileSet();
    }

    /**
     * <p>dir.</p>
     *
     * @param dir a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.FileSetBuilder} object
     */
    public FileSetBuilder dir(String dir) {
        fileSet.setDir(new File(dir));
        return this;
    }

    //----
    /**
     * <p>includes.</p>
     *
     * @param includes a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.FileSetBuilder} object
     */
    public FileSetBuilder includes(String includes) {
        fileSet.setIncludes(includes);
        return this;
    }

    /**
     * <p>includes.</p>
     *
     * @param includesAsList a {@link java.util.List} object
     * @return a {@link org.huberb.prototyping.anttasks.FileSetBuilder} object
     */
    public FileSetBuilder includes(List<String> includesAsList) {
        return includes(includesAsList.toArray(String[]::new));
    }

    /**
     * <p>includes.</p>
     *
     * @param includes a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.FileSetBuilder} object
     */
    public FileSetBuilder includes(String... includes) {
        if (includes != null && includes.length > 0) {
            fileSet.appendIncludes(includes);
        }
        return this;
    }

    //----
    /**
     * <p>excludes.</p>
     *
     * @param excludes a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.FileSetBuilder} object
     */
    public FileSetBuilder excludes(String excludes) {
        fileSet.setExcludes(excludes);
        return this;
    }

    /**
     * <p>excludes.</p>
     *
     * @param excludesAsList a {@link java.util.List} object
     * @return a {@link org.huberb.prototyping.anttasks.FileSetBuilder} object
     */
    public FileSetBuilder excludes(List<String> excludesAsList) {
        return excludes(excludesAsList.toArray(String[]::new));
    }

    /**
     * <p>excludes.</p>
     *
     * @param excludes a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.FileSetBuilder} object
     */
    public FileSetBuilder excludes(String... excludes) {
        if (excludes != null && excludes.length > 0) {
            fileSet.appendExcludes(excludes);
        }
        return this;
    }

    /**
     * <p>build.</p>
     *
     * @return a {@link org.apache.tools.ant.types.FileSet} object
     */
    public FileSet build() {
        return fileSet;
    }

}
