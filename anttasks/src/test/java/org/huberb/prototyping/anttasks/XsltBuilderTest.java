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
import org.apache.tools.ant.taskdefs.XSLTProcess;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class XsltBuilderTest {

    @Test
    public void give_parent_child_xml_and_xsl_then_generated_html_is_created() {
        final String in = "src/test/resources/xslt/parent_child.xml";
        final String out = "target/parent_child.html";
        final String style = "src/test/resources/xslt/parent_child.xsl";

        assertTrue(new File(in).exists(), "file: " + in);
        assertTrue(new File(style).exists(), "file: " + style);

        final AntTasksBuilder antTasksBuilder = new AntTasksBuilder();
        final XSLTProcess xsltProcess = new XsltBuilder(antTasksBuilder.project)
                .in(in)
                .out(out)
                .style(style)
                .build();

        xsltProcess.execute();

        assertTrue(new File(out).exists(), "file: " + out);
    }

    @Test
    public void give_w3cschools_catalog_xml_and_xsl_then_generated_html_is_created() {
        final String in = "src/test/resources/xslt/w3cschools_catalog.xml";
        final String out = "target/w3cschools_catalog.html";
        final String style = "src/test/resources/xslt/w3cschools_catalog.xsl";

        assertTrue(new File(in).exists(), "file: " + in);
        assertTrue(new File(style).exists(), "file: " + style);

        final AntTasksBuilder antTasksBuilder = new AntTasksBuilder();
        final XSLTProcess xsltProcess = new XsltBuilder(antTasksBuilder.project)
                .in(in)
                .out(out)
                .style(style)
                .build();

        xsltProcess.execute();

        assertTrue(new File(out).exists(), "file: " + out);
    }
}
