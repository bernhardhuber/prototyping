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
import com.thoughtworks.qdox.model.JavaSource;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.huberb.prototyping.xml.qdox.XmlSaxWriter.XmlModelSaxWriterFactory;
import org.huberb.prototyping.xml.qdox.XmlSaxWriter.XmlStreamWriterConsumerTemplates;
import picocli.CommandLine;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Spec;

/**
 * @author berni3
 */
@CommandLine.Command(name = "QdoxCli",
        mixinStandardHelpOptions = true,
        showAtFileInUsageHelp = true,
        showDefaultValues = true,
        version = "QdoxCli 0.1-SNAPSHOT",
        description = "Run qdox from the command line%n"
)
public class QdoxMain implements Callable<Integer> {

    @Spec
    private CommandSpec spec;

    @Parameters(index = "0..*",
            description = "input sources.")
    private List<String> sources;

    @Option(names = {"-o", "--output"},
            description = "Write evaluated expressions to file.")
    private String output = null;

    enum ModelWriterMode {
        XmlModelWriter, XmlSaxModelWriter;
    }
    @Option(names = {"-m", "--model-writer"},
            description = "Choose model-writer",
            defaultValue = "XmlSaxModelWriter"
    )
    ModelWriterMode modelWriterMode;

    private LoggingSystem loggingSystem;

    public static void main(String[] args) throws Exception {
        final int exitCode = new CommandLine(new QdoxMain()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        loggingSystem = new LoggingSystem(spec);
        loggingSystem.info("Hello %s", this.getClass().getName());

        JavaProjectBuilder javaProjectBuilder = createFromInputSources(this.sources);
        if (!javaProjectBuilder.getSources().isEmpty()) {
            if (modelWriterMode == ModelWriterMode.XmlModelWriter) {
                processSourcesUsingXmlModelWriter(javaProjectBuilder);
            } else if (modelWriterMode == ModelWriterMode.XmlSaxModelWriter) {
                processSourcesUsingXmlSaxModelWriter(javaProjectBuilder);
            }
        }
        return 0;
    }

    JavaProjectBuilder createFromInputSources(List<String> theSources) {
        JavaProjectBuilder result = new JavaProjectBuilder();
        if (theSources != null && !theSources.isEmpty()) {
            theSources.stream()
                    .map(String::trim)
                    .map(File::new)
                    .filter(f -> f.exists() && f.canRead())
                    .forEach(f -> {
                        if (f.isDirectory()) {
                            result.addSourceTree(f);
                        } else if (f.isFile()) {
                            try {
                                result.addSource(f);
                            } catch (IOException ex) {
                                loggingSystem.info(ex, "file %s", f);
                            }
                        }
                    });
        }
        return result;
    }

    void processSourcesUsingXmlModelWriter(JavaProjectBuilder builder) {
        final Collection<JavaSource> javaSourceCollection = builder.getSources();
        for (JavaSource js : javaSourceCollection) {
            final XmlModelWriter xmlModelWriter = new XmlModelWriter();
            xmlModelWriter.writeSource(js);
            loggingSystem.System_out_format("JavaSource:%n%s%n", xmlModelWriter.toString());
        }
    }

    void processSourcesUsingXmlSaxModelWriter(JavaProjectBuilder builder) throws XMLStreamException, IOException {

        try (final StringWriter sw = new StringWriter()) {
            final XMLStreamWriter xsw = XmlModelSaxWriterFactory.createXMLStreamWriter(sw);
            final XmlSaxWriter xsw1 = new XmlSaxWriter();

            final XmlStreamWriterConsumerTemplates xswctStart = new XmlStreamWriterConsumerTemplates();
            xsw1.accept(xsw, xswctStart.startDocument().build());

            final Collection<JavaSource> javaSourceCollection = builder.getSources();
            for (JavaSource source : javaSourceCollection) {
                try {
                    final XmlSaxModelWriter xsmw = new XmlSaxModelWriter();
                    xsmw.writeSource(source);
                    xsw1.accept(xsw, xsmw.getXswct().build());

                    sw.flush();

                    loggingSystem.System_out_format("JavaSource:%n%s%n", sw.toString());
                    int start = 0;
                    int end = sw.getBuffer().length();
                    if (end > 0) {
                        sw.getBuffer().delete(start, end);
                    }

                } catch (XMLStreamException ex) {
                    loggingSystem.info(ex, "Unexpecting processing error '%s'", source);
                }
            }
            final XmlStreamWriterConsumerTemplates xswctEnd = new XmlStreamWriterConsumerTemplates();
            xsw1.accept(xsw, xswctEnd.endDocument().build());
            loggingSystem.System_out_format("JavaSource:%n%s%n", sw.toString());
        }
    }

    static class LoggingSystem {

        private static final Logger LOGGER = Logger.getLogger(LoggingSystem.class.getName());
        private final CommandSpec spec;

        public LoggingSystem(CommandSpec spec) {
            this.spec = spec;
        }

        public void System_out_format(String format, Object... args) {
            spec.commandLine().getOut().format(format, args);
        }

        public void info(Exception ex, String format, Object... args) {
            LOGGER.log(Level.INFO, ex, () -> String.format(format, args));
        }

        public void info(String format, Object... args) {
            LOGGER.info(() -> String.format(format, args));
        }
    }

}
