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
package org.huberb.prototyping.transhuelle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.logging.Logger;
import org.huberb.prototyping.transhuelle.CsvGenerator.CsvReader;
import org.huberb.prototyping.transhuelle.CsvGenerator.CsvWriter;
import org.huberb.prototyping.transhuelle.TransHuelle.Algorithm;
import org.huberb.prototyping.transhuelle.TransHuelle.Algorithm2;
import org.huberb.prototyping.transhuelle.TransHuelle.Data;
import org.huberb.prototyping.transhuelle.TransHuelle.IAlgorithm;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * Command line entry launching {@link Algorithm#evaluate(org.huberb.prototyping.transhuelle.TransHuelle.Data)
 * }.
 *
 * @author berni3
 */
@Command(name = "transhuelle",
        mixinStandardHelpOptions = true,
        showAtFileInUsageHelp = true,
        showDefaultValues = true,
        version = "transhuelle 1.0-SNAPSHOT",
        description = "Process transhuelle")
public class TransHuelleMain implements Callable<Integer> {

    private static final Logger logger = Logger.getLogger(TransHuelleMain.class.getName());
    @Option(names = {"--csv-file"},
            required = false,
            description = "load csv file, and process it")
    private File csvFile;

    @Option(names = {"--data-factory"},
            required = false,
            description = "process preset data, and process it"
    )
    private boolean dataFactory;

    enum AlgorithmMode {
        algorithm {
            @Override
            IAlgorithm createAnAlgorithm() {
                final IAlgorithm algorithm = new Algorithm();
                return algorithm;
            }
        }, algorithm2 {
            @Override
            IAlgorithm createAnAlgorithm() {
                final IAlgorithm algorithm = new Algorithm2();
                return algorithm;
            }
        };

        abstract IAlgorithm createAnAlgorithm();
    }

    @Option(names = {"--algorithm"},
            required = false,
            description = "choose algorithm",
            defaultValue = "algorithm2"
    )
    private AlgorithmMode algorithmMode;

    public static void main(String[] args) throws Exception {
        final int exitCode = new CommandLine(new TransHuelleMain()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        //Logger.getLogger("org.huberb.prototyping.transhuelle").setLevel(Level.WARNING);
        if (dataFactory) {
            processDataFactory();
        } else {
            if (!this.csvFile.exists()) {
                throw new FileNotFoundException(this.csvFile.getPath());
            }
            processCsvFile(this.csvFile);
        }
        return 0;
    }

    /**
     * Process data from {@link DataFactory}.
     *
     */
    void processDataFactory() {
        final List<Data> dinList = Arrays.asList(
                new DataFactory().createDataSample1(),
                new DataFactory().createDataSample2(),
                new DataFactory().createDataSample3(),
                new DataFactory().createDataSample4(),
                new DataFactory().createDataSample5()
        );
        final IAlgorithm iAlgorithm = this.algorithmMode.createAnAlgorithm();
        for (int i = 0; i < dinList.size(); i++) {
            final Data din = dinList.get(i);
            final Data dout = iAlgorithm.evaluate(din);
            outputData(dout);
        }
    }

    /**
     * Process data from a CSV file.
     */
    void processCsvFile(File theCsvFile) throws IOException {
        final String csvContent = Files.readString(theCsvFile.toPath());
        final CsvReader csvReader = new CsvReader();
        final List<Map<String, Set<String>>> inMap = csvReader.fromCsv(csvContent);
        final Data din = new Data("csv file " + theCsvFile.getPath(),
                inMap,
                Collections.emptyList()
        );
        final IAlgorithm iAlgorithm = this.algorithmMode.createAnAlgorithm();
        final Data dout = iAlgorithm.evaluate(din);
        outputData(dout);
    }

    /**
     * Output {@link Data} via {@link Logger}.
     *
     * @param data
     */
    void outputData(Data data) {
        final CsvWriter csvWriter = new CsvWriter();
        final String csvGroupsInList = csvWriter.toCsv(data.groupsInList);
        final String csvGroupsMergedList = csvWriter.toCsv(data.groupsMergedList);
        logger.info(String.format("outputData%n"
                + "data name %s%n"
                + "data groupsInList CSV%n%s%n"
                + "data groupsMergedList CSV%n%s%n",
                data.name,
                csvGroupsInList,
                csvGroupsMergedList)
        );
    }
}
