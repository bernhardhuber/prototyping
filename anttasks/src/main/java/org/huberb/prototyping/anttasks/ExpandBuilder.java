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
import org.apache.tools.ant.taskdefs.Expand;

/**
 * An ant {@link Expand} builder.
 *
 * @author berni3
 */
public class ExpandBuilder {
    
    private final Expand expand;

    public ExpandBuilder(Project project) {
        this.expand = (Expand) project.createTask("unzip");
    }

    public ExpandBuilder src(String zipFilepath) {
        expand.setSrc(new File(zipFilepath));
        return this;
    }

    public ExpandBuilder dest(String destinationDir) {
        expand.setDest(new File(destinationDir));
        return this;
    }

    public Expand build() {
        return expand;
    }
    
}
