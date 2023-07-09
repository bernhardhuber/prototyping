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
import java.io.File;
import java.util.Collection;
import javax.xml.stream.XMLStreamException;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * @author berni3
 */
public class XmlSaxModelWriterTest {

    @Test
    public void given_java_sources_then_packages_parsed() {
        final File directory = new File("src");
        final JavaProjectBuilder builder = new JavaProjectBuilder();
        builder.addSourceTree(directory);

        final Collection<JavaPackage> jpCollection = builder.getPackages();
        jpCollection.forEach(jp -> System.out.printf("JavaPackage:%s%n", jp.getName()));
        assertAll(
                () -> assertNotNull(jpCollection),
                () -> assertFalse(jpCollection.isEmpty())
        );
    }

    @Test
    public void given_java_sources_then_java_sources_parsed() throws XMLStreamException {
        final JavaProjectBuilder builder = new JavaProjectBuilder();
        final File directory = new File("src");
        builder.addSourceTree(directory);

        final Collection<JavaSource> javaSourceCollection = builder.getSources();
        for (final JavaSource source : javaSourceCollection) {
            final XmlSaxModelWriter dmw = new XmlSaxModelWriter();
            dmw.writeSource(source);
            final String emitXml = dmw.emitXml();
            System.out.printf("JavaSource:%n%s%n", emitXml);
            assertAll(
                    () -> assertNotNull(emitXml),
                    () -> assertFalse(emitXml.isEmpty()),
                    () -> assertFalse(emitXml.isBlank()),
                    () -> assertTrue(emitXml.startsWith("<?xml"))
            );
        }

    }

}
