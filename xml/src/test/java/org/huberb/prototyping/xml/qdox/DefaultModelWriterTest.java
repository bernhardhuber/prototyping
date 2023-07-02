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
import com.thoughtworks.qdox.writer.impl.DefaultModelWriter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collection;

/**
 * @author berni3
 */
public class DefaultModelWriterTest {

    @Test
    public void given_java_sources_then_packages_parsed() {
        JavaProjectBuilder builder = new JavaProjectBuilder();
        File directory = new File("src");
        builder.addSourceTree(directory);

        final Collection<JavaPackage> jpCollection = builder.getPackages();
        jpCollection.forEach(jp -> {
            System.out.printf("JavaPackage:%s%n", jp.getName());
        });
    }

    @Test
    public void given_java_sources_then_java_sources_parsed() {
        JavaProjectBuilder builder = new JavaProjectBuilder();
        File directory = new File("src");
        builder.addSourceTree(directory);
        final Collection<JavaSource> javaSourceCollection = builder.getSources();
        javaSourceCollection.forEach(source -> {
            final ModelWriter dmw = new DefaultModelWriter();
            dmw.writeSource(source);
            System.out.printf("JavaSource:%n%s%n", dmw.toString());
        });

    }

}
