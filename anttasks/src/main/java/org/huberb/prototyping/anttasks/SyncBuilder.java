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
import org.apache.tools.ant.taskdefs.Sync;
import org.apache.tools.ant.types.FileSet;

/**
 * An ant {@link org.apache.tools.ant.taskdefs.Sync} builder.
 *
 * @author berni3
 * @version $Id: $Id
 */
public class SyncBuilder {

    private final Sync sync;

    /**
     * <p>Constructor for SyncBuilder.</p>
     *
     * @param project a {@link org.apache.tools.ant.Project} object
     */
    public SyncBuilder(Project project) {
        this.sync = (Sync) project.createTask("sync");
        this.sync.init();
        sync.setOverwrite(false);
    }

    /**
     * <p>todir.</p>
     *
     * @param f a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.SyncBuilder} object
     */
    public SyncBuilder todir(String f) {
        this.sync.setTodir(new File(f));
        return this;
    }

    /**
     * <p>addfileset.</p>
     *
     * @param fileSet a {@link org.apache.tools.ant.types.FileSet} object
     * @return a {@link org.huberb.prototyping.anttasks.SyncBuilder} object
     */
    public SyncBuilder addfileset(FileSet fileSet) {
        this.sync.addFileset(fileSet);
        return this;
    }

    /**
     * <p>build.</p>
     *
     * @return a {@link org.apache.tools.ant.taskdefs.Sync} object
     */
    public Sync build() {
        return this.sync;
    }

}
