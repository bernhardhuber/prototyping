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
import java.util.Iterator;

/**
 *
 * @author berni3
 */
public class QdoxMain {

    public static void main(String[] args) {
        JavaProjectBuilder builder = new JavaProjectBuilder();
        File directory = new File("src");
        builder.addSourceTree(directory);

        final Collection<JavaPackage> jpCollection = builder.getPackages();
        for (Iterator<JavaPackage> it = jpCollection.iterator(); it.hasNext();) {
            final JavaPackage jp = it.next();
            System.out.printf("JavaPackage:%s%n", jp.getName());

        }

        final Collection<JavaSource> javaSourceCollection = builder.getSources();
        for (Iterator<JavaSource> it = javaSourceCollection.iterator(); it.hasNext();) {
            final JavaSource source = it.next();
            final XmlModelWriter dmw = new XmlModelWriter();
            dmw.writeSource(source);
            System.out.printf("JavaSource:%n%s%n", dmw.toString());
        }
    }
}
