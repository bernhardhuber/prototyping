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

import com.googlecode.lanterna.gui2.dialogs.DirectoryDialog;
import com.googlecode.lanterna.gui2.dialogs.DirectoryDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.ListSelectDialog;
import com.googlecode.lanterna.gui2.dialogs.ListSelectDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialog;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;
import java.io.IOException;
import java.io.StringWriter;
import org.apache.commons.configuration2.AbstractConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.huberb.prototyping.lanterna.examples.dialogs.CheckListDialog;
import org.huberb.prototyping.lanterna.examples.dialogs.CheckListDialogBuilder;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class ApacheConfigurationAdaptersTest {

    public ApacheConfigurationAdaptersTest() {
    }

    /**
     * Test of retriveSubset method, of class ApacheConfigurationAdapters.
     */
    @Test
    public void testRetriveSubset_DirectoryDialogBuilder() throws IOException, ConfigurationException {
        final PropertiesConfiguration propsConfig = new PropertiesConfiguration();
        propsConfig.setHeader("header testRetriveSubset_DirectoryDialogBuilder");
        propsConfig.setFooter("footer testRetriveSubset_DirectoryDialogBuilder");

        final AbstractConfiguration config = propsConfig;
        final String configSubSetNameA = "directoryDialogA";

        final ApacheConfigurationAdapters instance = new ApacheConfigurationAdapters();
        final Configuration subSetConfigurationA = instance.retriveSubset(config, configSubSetNameA);

        final DirectoryDialogBuilder ddb = instance.ddbcFactory.createBuilder(subSetConfigurationA);
        final DirectoryDialog dd = ddb.build();
        assertEquals("", dd.getTitle());

        ddb.setTitle("titleA");
        ddb.setDescription("descriptionA");

        instance.ddbcFactory.createConfiguration(ddb, subSetConfigurationA);

        assertEquals("titleA", subSetConfigurationA.getString("title"));
        assertEquals("descriptionA", subSetConfigurationA.getString("description"));

        try ( StringWriter sw = new StringWriter()) {
            propsConfig.write(sw);
            sw.flush();

            System.out.printf("%n%s%n", sw.toString());

            Assertions.assertAll(
                    () -> assertTrue(sw.toString().contains("directoryDialogA.title = titleA")),
                    () -> assertTrue(sw.toString().contains("directoryDialogA.description = descriptionA"))
            );
        }

    }

    /**
     * Test of retriveSubset method, of class ApacheConfigurationAdapters.
     */
    @Test
    public void testRetriveSubset_ListSelectDialogBuilder() throws IOException, ConfigurationException {
        final PropertiesConfiguration propsConfig = new PropertiesConfiguration();
        propsConfig.setHeader("header testRetriveSubset_ListSelectDialogBuilder");
        propsConfig.setFooter("footer testRetriveSubset_ListSelectDialogBuilder");

        final AbstractConfiguration config = propsConfig;
        final String configSubSetNameA = "listSelectA";

        final ApacheConfigurationAdapters instance = new ApacheConfigurationAdapters();
        final Configuration subSetConfigurationA = instance.retriveSubset(config, configSubSetNameA);

        final ListSelectDialogBuilder lsdb = instance.lsdbFactory.createBuilder(subSetConfigurationA);
        final ListSelectDialog lsd = (ListSelectDialog) lsdb.build();
        assertEquals("", lsd.getTitle());

        lsdb.setTitle("titleA");
        lsdb.setDescription("descriptionA");

        instance.lsdbFactory.createConfiguration(lsdb, subSetConfigurationA);

        assertEquals("titleA", subSetConfigurationA.getString("title"));
        assertEquals("descriptionA", subSetConfigurationA.getString("description"));

        try ( StringWriter sw = new StringWriter()) {
            propsConfig.write(sw);
            sw.flush();

            System.out.printf("%n%s%n", sw.toString());

            Assertions.assertAll(
                    () -> assertTrue(sw.toString().contains("listSelectA.title = titleA")),
                    () -> assertTrue(sw.toString().contains("listSelectA.description = descriptionA"))
            );
        }
    }

    /**
     * Test of retriveSubset method, of class ApacheConfigurationAdapters.
     */
    @Test
    public void testRetriveSubset_TextInputDialogBuilder() throws IOException, ConfigurationException {
        final PropertiesConfiguration propsConfig = new PropertiesConfiguration();
        propsConfig.setHeader("header testRetriveSubset_TextInputDialogBuilder");
        propsConfig.setFooter("footer testRetriveSubset_TextInputDialogBuilder");

        final AbstractConfiguration config = propsConfig;
        final String configSubSetNameA = "textInputA";

        final ApacheConfigurationAdapters instance = new ApacheConfigurationAdapters();
        final Configuration subSetConfigurationA = instance.retriveSubset(config, configSubSetNameA);

        final TextInputDialogBuilder tidb = instance.tibFactory.createBuilder(subSetConfigurationA);
        final TextInputDialog tid = (TextInputDialog) tidb.build();
        assertEquals("", tid.getTitle());

        tidb.setTitle("titleA");
        tidb.setDescription("descriptionA");

        instance.tibFactory.createConfiguration(tidb, subSetConfigurationA);

        assertEquals("titleA", subSetConfigurationA.getString("title"));
        assertEquals("descriptionA", subSetConfigurationA.getString("description"));

        try ( StringWriter sw = new StringWriter()) {
            propsConfig.write(sw);
            sw.flush();

            System.out.printf("%n%s%n", sw.toString());

            Assertions.assertAll(
                    () -> assertTrue(sw.toString().contains("textInputA.title = titleA")),
                    () -> assertTrue(sw.toString().contains("textInputA.description = descriptionA"))
            );
        }
    }

    /**
     * Test of retriveSubset method, of class ApacheConfigurationAdapters.
     */
    @Test
    public void testRetriveSubset_TextInputPasswordDialogBuilder() throws IOException, ConfigurationException {
        final PropertiesConfiguration propsConfig = new PropertiesConfiguration();
        propsConfig.setHeader("header testRetriveSubset_TextInputPasswordDialogBuilder");
        propsConfig.setFooter("footer testRetriveSubset_TextInputPasswordDialogBuilder");

        final AbstractConfiguration config = propsConfig;
        final String configSubSetNameA = "textInputPasswordA";

        final ApacheConfigurationAdapters instance = new ApacheConfigurationAdapters();
        final Configuration subSetConfigurationA = instance.retriveSubset(config, configSubSetNameA);

        final TextInputDialogBuilder tidb = instance.tipbFactory.createBuilder(subSetConfigurationA);
        final TextInputDialog tid = (TextInputDialog) tidb.build();
        assertEquals("", tid.getTitle());

        tidb.setTitle("titleA");
        tidb.setDescription("descriptionA");

        instance.tipbFactory.createConfiguration(tidb, subSetConfigurationA);

        assertEquals("titleA", subSetConfigurationA.getString("title"));
        assertEquals("descriptionA", subSetConfigurationA.getString("description"));

        try ( StringWriter sw = new StringWriter()) {
            propsConfig.write(sw);
            sw.flush();

            System.out.printf("%n%s%n", sw.toString());

            Assertions.assertAll(
                    () -> assertTrue(sw.toString().contains("textInputPasswordA.title = titleA")),
                    () -> assertTrue(sw.toString().contains("textInputPasswordA.description = descriptionA"))
            );
        }
    }

    /**
     * Test of retriveSubset method, of class ApacheConfigurationAdapters.
     */
    @Test
    public void testRetriveSubset_CheckListDialogBuilder() throws IOException, ConfigurationException {
        final PropertiesConfiguration propsConfig = new PropertiesConfiguration();
        propsConfig.setHeader("header testRetriveSubset_CheckListDialogBuilder");
        propsConfig.setFooter("footer testRetriveSubset_CheckListDialogBuilder");

        final AbstractConfiguration config = propsConfig;
        final String configSubSetNameA = "checkListA";

        final ApacheConfigurationAdapters instance = new ApacheConfigurationAdapters();
        final Configuration subSetConfigurationA = instance.retriveSubset(config, configSubSetNameA);

        final CheckListDialogBuilder chlb = instance.chlbFactory.createBuilder(subSetConfigurationA);
        final CheckListDialog cld = (CheckListDialog) chlb.build();
        assertEquals("", cld.getTitle());

        chlb.setTitle("titleA");
        chlb.setDescription("descriptionA");

        instance.chlbFactory.createConfiguration(chlb, subSetConfigurationA);

        assertEquals("titleA", subSetConfigurationA.getString("title"));
        assertEquals("descriptionA", subSetConfigurationA.getString("description"));

        try ( StringWriter sw = new StringWriter()) {
            propsConfig.write(sw);
            sw.flush();

            System.out.printf("%n%s%n", sw.toString());

            Assertions.assertAll(
                    () -> assertTrue(sw.toString().contains("checkListA.title = titleA")),
                    () -> assertTrue(sw.toString().contains("checkListA.description = descriptionA"))
            );
        }
    }

}
