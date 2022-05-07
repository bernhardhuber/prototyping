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
package org.huberb.prototyping.xml.trang;

import com.thaiopensource.relaxng.translate.Driver;
import com.thaiopensource.xml.sax.ErrorHandlerImpl;
import java.io.StringWriter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class DriverTest {

    public DriverTest() {
    }

    @Test
    public void testPomXml2Rnc() {

        final Driver driver = createDriver();
        final String[] args = {
            "pom.xml",
            "target/pom_xml.rnc"
        };
        int rc = driver.run(args);
        assertEquals(0, rc);
    }
    @Test
    public void testPomXml2Xsd() {

        final Driver driver = createDriver();
        final String[] args = {
            "pom.xml",
            "target/pom_xml.xsd"
        };
        int rc = driver.run(args);
        assertEquals(0, rc);
    }

    Driver createDriver() {
        final Driver driver;
        if (1 == 1) {
            driver = new Driver();
        } else {
            final StringWriter sw = new StringWriter();
            final ErrorHandlerImpl errorHandlerImpl = new ErrorHandlerImpl(sw);
            driver = new Driver(errorHandlerImpl);
        }
        return driver;
    }
}
