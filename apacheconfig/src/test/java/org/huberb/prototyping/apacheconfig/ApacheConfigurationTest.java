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
package org.huberb.prototyping.apacheconfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class ApacheConfigurationTest {

    public ApacheConfigurationTest() {
    }

    @Test
    public void testPropertiesConfiguration() throws ConfigurationException, IOException {
        final Configurations configs = new Configurations();
        final File propertiesConfigurationFile = new File("target/testPropertiesConfiguration.properties");
        propertiesConfigurationFile.createNewFile();
        final FileBasedConfigurationBuilder<PropertiesConfiguration> builder = configs.propertiesBuilder(propertiesConfigurationFile);
        final PropertiesConfiguration config = builder.getConfiguration();

        // update property
        config.setProperty("key1", "value1");
        // save configuration
        builder.save();
    }

    @Test
    public void testXMLConfiguration() throws ConfigurationException, IOException {
        final Configurations configs = new Configurations();
        final File xmlConfigurationFile = new File("target/testXMLConfiguration.xml");
        xmlConfigurationFile.createNewFile();

        if (xmlConfigurationFile.length() == 0L) {
            try ( FileWriter fw = new FileWriter(xmlConfigurationFile, Charset.forName("UTF-8"))) {
                fw.append(String.format("<config/>%n"));
            }
        }

        final FileBasedConfigurationBuilder<XMLConfiguration> builder = configs.xmlBuilder(xmlConfigurationFile);
        final XMLConfiguration config = builder.getConfiguration();

        // update property
        config.setProperty("key1", "value1");

        // save configuration
        builder.save();
    }
}
