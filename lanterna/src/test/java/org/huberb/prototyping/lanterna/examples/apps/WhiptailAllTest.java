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
package org.huberb.prototyping.lanterna.examples.apps;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author berni3
 */
public class WhiptailAllTest {

    public WhiptailAllTest() {
    }

    @Test
    public void given_whiptail_all_yaml_verify_options() throws IOException {
        final DumperOptions dumperOptions = createDumperOptions();
        final Yaml yaml = new Yaml(dumperOptions);

        try (final InputStream is = this.getClass().getClassLoader().getResourceAsStream("whiptail_all.yml")) {
            final Object yamlLoadResult = yaml.load(is);
            assertNotNull(yamlLoadResult);
            assertTrue(Map.class.isAssignableFrom(yamlLoadResult.getClass()));
            final Map<String, Object> m = (Map<String, Object>) yamlLoadResult;
            final Map<String, Object> options = (Map<String, Object>) m.get("options");

            assertAll(
                    () -> assertEquals(true, options.get("autoOpenTerminalEmulatorWindow")),
                    () -> assertEquals(false, options.get("forceTextTerminal")),
                    () -> assertEquals(false, options.get("preferTerminalEmulator")),
                    () -> assertEquals(false, options.get("forceAWTOverSwing")),
                    () -> assertEquals("appName", options.get("terminalEmulatorTitle")),
                    () -> assertEquals(80, options.get("initialTerminalSizeColumns")),
                    () -> assertEquals(25, options.get("initialTerminalSizeRows")),
                    () -> assertEquals("BLUE", options.get("backgroundColor")),
                    () -> assertEquals("default", options.get("themeName"))
            );

        }
    }

    @Test
    public void given_whiptail_all_yaml_verify_dialogs() throws IOException {
        final DumperOptions dumperOptions = createDumperOptions();
        final Yaml yaml = new Yaml(dumperOptions);

        try (final InputStream is = this.getClass().getClassLoader().getResourceAsStream("whiptail_all.yml")) {
            final Object yamlLoadResult = yaml.load(is);
            assertNotNull(yamlLoadResult);
            assertTrue(Map.class.isAssignableFrom(yamlLoadResult.getClass()));
            final Map<String, Object> m = (Map<String, Object>) yamlLoadResult;
            final List<Map<String, Object>> dialogs = (List<Map<String, Object>>) m.get("dialogs");
            for (int i = 0; i < dialogs.size(); i++) {
                final Map<String, Object> dialog = dialogs.get(i);
                System.out.printf("%d: type %s, name %s%ndialog %s%n", i, dialog.get("type"), dialog.get("name"), dialog);
            }
        }
    }

    DumperOptions createDumperOptions() {
        final DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(FlowStyle.BLOCK);
        dumperOptions.setPrettyFlow(true);
        dumperOptions.setIndent(2);
        dumperOptions.setIndicatorIndent(2);
        dumperOptions.setIndentWithIndicator(true);
        //dumperOptions.setCanonical(true);
        return dumperOptions;
    }
}
