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
import org.apache.tools.ant.taskdefs.XSLTProcess;

/**
 * An ant {@link XSLTProcess} builder.
 *
 * @author berni3
 */
public class XsltBuilder {

    private final XSLTProcess xsltProcess;

    public XsltBuilder(Project project) {
        this.xsltProcess = (XSLTProcess) project.createTask("xslt");
    }

    public XsltBuilder destdir(String destdir) {
        xsltProcess.setDestdir(new File(destdir));
        return this;
    }

    public XsltBuilder basedir(String basedir) {
        xsltProcess.setBasedir(new File(basedir));
        return this;
    }

    public XsltBuilder style(String xslfile) {
        xsltProcess.setStyle(xslfile);
        return this;
    }

    public XsltBuilder extension(String xslfile) {
        xsltProcess.setExtension(xslfile);
        return this;
    }

    public XsltBuilder in(String in) {
        xsltProcess.setIn(new File(in));
        return this;
    }

    public XsltBuilder out(String out) {
        xsltProcess.setOut(new File(out));
        return this;
    }

    public XSLTProcess build() {
        return xsltProcess;
    }

}
