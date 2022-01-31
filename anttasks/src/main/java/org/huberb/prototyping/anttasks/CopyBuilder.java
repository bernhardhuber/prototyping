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
 * An ant {@link Copy} builder.
 *
 * @author berni3
 */
public class CopyBuilder {

    private final Copy copy;

    public CopyBuilder(Project project) {
        this.copy = (Copy) project.createTask("copy");
        this.copy.init();
        copy.setOverwrite(false);
    }

    public CopyBuilder file(String f) {
        this.copy.setFile(new File(f));
        return this;
    }

    public CopyBuilder tofile(String f) {
        this.copy.setTofile(new File(f));
        return this;
    }

    public CopyBuilder addfileset(FileSet fileSet) {
        this.copy.addFileset(fileSet);
        return this;
    }

    public CopyBuilder todir(String d) {
        this.copy.setTodir(new File(d));
        return this;
    }

    public Copy build() {
        return this.copy;
    }


}
