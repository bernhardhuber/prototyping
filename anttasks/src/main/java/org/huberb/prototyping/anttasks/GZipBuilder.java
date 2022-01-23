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
import org.apache.tools.ant.taskdefs.GZip;

/**
 *
 * @author berni3
 */
public class GZipBuilder {
    
    private final GZip gzip;

    public GZipBuilder(Project project) {
        this.gzip = (GZip) project.createTask("gzip");
    }

    public GZipBuilder src(String source) {
        gzip.setSrc(new File(source));
        return this;
    }

    public GZipBuilder destfile(String dest) {
        gzip.setDestfile(new File(dest));
        return this;
    }

    public GZip build() {
        return gzip;
    }
    
}
