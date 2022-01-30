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
package org.huberb.prototyping.groovyintegration;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.logging.Logger;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 *
 * @author berni3
 */
@Command(name = "gshloader",
        mixinStandardHelpOptions = true,
        showAtFileInUsageHelp = true,
        showDefaultValues = true,
        version = "gshloader 1.0-SNAPSHOT",
        description = "Load a groovyscript file")
public class GshloaderMain implements Callable<Integer> {

    private static final Logger logger = Logger.getLogger(GshloaderMain.class.getName());

    @Option(names = {"--groovyscript-file"},
            required = false,
            description = "evaluate groovy script file")
    private File groovyScriptFile;
    @Option(names = {"--groovyscript-inline"},
            required = false,
            description = "evaluate groovy inline script")
    private String groovyInlineScript;

    public static void main(String... args) {
        final int exitCode = new CommandLine(new GshloaderMain()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception { // your business logic goes here...

        if (groovyScriptFile != null && groovyScriptFile.exists()) {
            evaluateGroovyScriptFile(this.groovyScriptFile);
        } else if (groovyInlineScript != null && !groovyInlineScript.isEmpty()) {
            evaluateGroovyInlineScript(groovyInlineScript);
        } else {
            evaluateGroovyDefaultScript();
        }
        return 0;
    }

    void evaluateGroovyDefaultScript() {
        var sharedData = new Binding();
        var shell = new GroovyShell(sharedData);
        var now = new Date();
        sharedData.setProperty("text", "I am shared data!");
        sharedData.setProperty("date", now);

        final Object result = shell.evaluate("\"At $date, $text\"");

        logger.info(() -> String.format("evaluateGroovyDefaultScript result `%s'", result));
        //assert result == "At $now, I am shared data!"
    }

    void evaluateGroovyInlineScript(String theGroovyInlineScript) {
        var sharedData = new Binding();
        var shell = new GroovyShell(sharedData);

        final Object result = shell.evaluate(theGroovyInlineScript);

        logger.info(() -> String.format("evaluateGroovyInlineScript result `%s'", result));
    }

    void evaluateGroovyScriptFile(File theGroovyScriptFile) throws IOException {
        var sharedData = new Binding();
        var shell = new GroovyShell(sharedData);

        final Object result = shell.evaluate(theGroovyScriptFile);

        logger.info(() -> String.format("evaluateGroovyScriptFile result `%s'", result));
    }

}
