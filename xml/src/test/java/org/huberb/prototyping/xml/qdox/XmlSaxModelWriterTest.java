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
package org.huberb.prototyping.xml.qdox;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaPackage;
import com.thoughtworks.qdox.model.JavaSource;
import com.thoughtworks.qdox.writer.ModelWriter;
import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import javax.xml.stream.XMLStreamException;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class XmlSaxModelWriterTest {

    @Test
    public void given_java_sources_then_packages_parsed() {
        JavaProjectBuilder builder = new JavaProjectBuilder();
        File directory = new File("src");
        builder.addSourceTree(directory);

        final Collection<JavaPackage> jpCollection = builder.getPackages();
        for (Iterator<JavaPackage> it = jpCollection.iterator(); it.hasNext();) {
            final JavaPackage jp = it.next();
            System.out.printf("JavaPackage:%s%n", jp.getName());
        }
    }

    @Test
    public void given_java_sources_then_java_sources_parsed() throws XMLStreamException {
        JavaProjectBuilder builder = new JavaProjectBuilder();
        File directory = new File("src");
        builder.addSourceTree(directory);
        final Collection<JavaSource> javaSourceCollection = builder.getSources();
        for (Iterator<JavaSource> it = javaSourceCollection.iterator(); it.hasNext();) {
            final JavaSource source = it.next();
            final ModelWriter dmw = new XmlSaxModelWriter();
            dmw.writeSource(source);
            System.out.printf("JavaSource:%n%s%n", dmw.toString());
        }

    }

}
