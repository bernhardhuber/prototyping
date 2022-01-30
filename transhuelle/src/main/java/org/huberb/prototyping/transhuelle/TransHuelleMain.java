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

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Logger;
import org.huberb.prototyping.transhuelle.TransHuelle.Algorithm;
import org.huberb.prototyping.transhuelle.TransHuelle.Data;
import org.huberb.prototyping.transhuelle.DataFactory;

/**
 * Command line entry launching {@link Algorithm#evaluate(org.huberb.prototyping.transhuelle.TransHuelle.Data)
 * }.
 *
 * @author berni3
 */
public class TransHuelleMain implements Callable<Integer> {

    private static final Logger logger = Logger.getLogger(TransHuelleMain.class.getName());

    public static void main(String[] args) throws Exception {
        int rc = new TransHuelleMain().call();
        System.exit(rc);
    }

    @Override
    public Integer call() throws Exception {
        final List<Data> dinList = Arrays.asList(
                new DataFactory().createDataSample1(),
                new DataFactory().createDataSample2(),
                new DataFactory().createDataSample3(),
                new DataFactory().createDataSample4(),
                new DataFactory().createDataSample5()
        );
        for (int i = 0; i < dinList.size(); i++) {
            final Data din = dinList.get(i);
            logger.info(String.format(">>>%n%s %d%n"
                    + "din: %s%n",
                    TransHuelle.class, i,
                    din.toString()
            ));
            final Data dout = new Algorithm().evaluate(din);
            logger.info(String.format("%n%s %d%n"
                    + "din: %s%n"
                    + "dout: %s%n"
                    + "<<<%n",
                    TransHuelle.class, i,
                    din.toString(), dout.toString()
            ));
        }
        return 0;
    }
}
