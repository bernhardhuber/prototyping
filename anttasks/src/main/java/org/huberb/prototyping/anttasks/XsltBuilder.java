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
 * An ant {@link org.apache.tools.ant.taskdefs.XSLTProcess} builder.
 *
 * @author berni3
 * @version $Id: $Id
 */
public class XsltBuilder {

    private final XSLTProcess xsltProcess;

    /**
     * <p>Constructor for XsltBuilder.</p>
     *
     * @param project a {@link org.apache.tools.ant.Project} object
     */
    public XsltBuilder(Project project) {
        this.xsltProcess = (XSLTProcess) project.createTask("xslt");
    }

    /**
     * <p>destdir.</p>
     *
     * @param destdir a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.XsltBuilder} object
     */
    public XsltBuilder destdir(String destdir) {
        xsltProcess.setDestdir(new File(destdir));
        return this;
    }

    /**
     * <p>basedir.</p>
     *
     * @param basedir a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.XsltBuilder} object
     */
    public XsltBuilder basedir(String basedir) {
        xsltProcess.setBasedir(new File(basedir));
        return this;
    }

    /**
     * <p>style.</p>
     *
     * @param xslfile a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.XsltBuilder} object
     */
    public XsltBuilder style(String xslfile) {
        xsltProcess.setStyle(xslfile);
        return this;
    }

    /**
     * <p>extension.</p>
     *
     * @param xslfile a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.XsltBuilder} object
     */
    public XsltBuilder extension(String xslfile) {
        xsltProcess.setExtension(xslfile);
        return this;
    }

    /**
     * <p>in.</p>
     *
     * @param in a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.XsltBuilder} object
     */
    public XsltBuilder in(String in) {
        xsltProcess.setIn(new File(in));
        return this;
    }

    /**
     * <p>out.</p>
     *
     * @param out a {@link java.lang.String} object
     * @return a {@link org.huberb.prototyping.anttasks.XsltBuilder} object
     */
    public XsltBuilder out(String out) {
        xsltProcess.setOut(new File(out));
        return this;
    }

    /**
     * <p>build.</p>
     *
     * @return a {@link org.apache.tools.ant.taskdefs.XSLTProcess} object
     */
    public XSLTProcess build() {
        return xsltProcess;
    }

}
