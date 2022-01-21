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

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import org.huberb.prototyping.lanterna.examples.apps.YamlDumpTest.RepresentersConstructors.YamlConstructors;
import org.huberb.prototyping.lanterna.examples.apps.YamlDumpTest.RepresentersConstructors.YamlRepresenters;
import org.huberb.prototyping.lanterna.examples.dialogs.Factories.MapBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

/**
 *
 * @author berni3
 */
public class YamlDumpTest {

    @Test
    public void given_Bean1_dump_and_load() {
        final Bean1 bean1 = new Bean1();
        bean1.l = Arrays.asList("l1", "l2", "l3");
        bean1.i1 = 1;
        bean1.s1 = "s1StringValue";

        final DumperOptions dumperOptions = createDumperOptions();
        final Yaml yaml = new Yaml(dumperOptions);
        final String yamlDumpResult = yaml.dump(bean1);
        System.out.printf("given_Bean1_dump_and_load%n%s%n", yamlDumpResult);

        final Object yamlLoadResult = yaml.load(yamlDumpResult);
        assertNotNull(yamlLoadResult);
        assertEquals(Bean1.class.getName(), yamlLoadResult.getClass().getName());
        final Bean1 bean1FromYamlLoadResult = (Bean1) yamlLoadResult;
        assertEquals(bean1.l, bean1FromYamlLoadResult.l);
        assertEquals(bean1.i1, bean1FromYamlLoadResult.i1);
        assertEquals(bean1.s1, bean1FromYamlLoadResult.s1);
    }

    @Test
    public void given_HierarchyOfMaps_dump_load() {
        final Map<String, Object> m = new MapBuilder<String, Object>()
                .kv("key1", Arrays.asList("v1", "v2", "v3"))
                .kv("key2", Arrays.asList(
                        new MapBuilder<String, String>().kv("key211", "v21").kv("key212", "12").kv("keys213", "abc").build(),
                        new MapBuilder<String, Integer>().kv("key221", -1).kv("key222", 0).kv("keys223", 1).build(),
                        new MapBuilder<String, Object>().kv("key231", -100).kv("key232", "0").kv("keys233", 100.100d).build()
                )).build();
        final DumperOptions dumperOptions = createDumperOptions();
        final Yaml yaml = new Yaml(dumperOptions);
        final String yamlDumpResult = yaml.dump(m);
        System.out.printf("given_HierarchOfMaps_dump_load%n%s%n", yamlDumpResult);

        final Object yamlLoadResult = yaml.load(yamlDumpResult);
        assertNotNull(yamlLoadResult);
        assertEquals(LinkedHashMap.class.getName(), yamlLoadResult.getClass().getName());
        final HashMap<String, Object> mFromYamlLoadResult = (HashMap<String, Object>) yamlLoadResult;
        assertEquals("[v1, v2, v3]", m.get("key1").toString());
        assertEquals("[{keys213=abc, key212=12, key211=v21}, "
                + "{keys223=1, key222=0, key221=-1}, "
                + "{key231=-100, keys233=100.1, key232=0}]", m.get("key2").toString());
    }

    @Test
    public void given_Map_dump() {
        final Map<String, Object> m = new HashMap<>();
        m.put("bean1Key00", "bean1Value0");
        m.put("bean1Key01", "bean1-Value-1");
        m.put("bean1Key02", "bean1\tValue\n2");
        m.put("bean1Key03", "bean1Value3");

        m.put("bean1Key04Integer", 100);
        m.put("bean1Key05Double", 100.0d);
        m.put("bean1Key06Date", new Date());
        m.put("bean1Key07List", Arrays.asList("l1", "l2", "l3"));
        m.put("bean1Key08GregorianCalendar", GregorianCalendar.getInstance());
        m.put("bean1Key09LocalDateTime", java.time.LocalDateTime.now().minusDays(7).format(DateTimeFormatter.ISO_DATE));
        m.put("bean1Key10", "100+200-100");
        m.put("bean1Key11", new File("."));
        //---
        final Properties props = new Properties();
        props.setProperty("bean1PropK1", "bean1PropV1");
        props.setProperty("bean1PropK2", "bean1PropV2");
        props.setProperty("bean1PropK3", "bean1PropV3");
        m.put("bean1Key90Props", props);
        m.put("bean1Key91SystemProps", System.getProperties());
        m.put("bean1Key92SystemEnv", System.getenv());

        final DumperOptions dumperOptions = createDumperOptions();
        final Yaml yaml = new Yaml(
                new YamlConstructors(),
                new YamlRepresenters(),
                dumperOptions);

        System.out.printf("test2%n%s%n", yaml.dump(m));
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

    // *must* be public for snake-yaml being able to create an instance
    public static class Bean1 {

        private List<String> l;
        private String s1;
        private int i1;

        private Map<String, String> m;
        private Properties props;

        public Bean1() {
            l = Arrays.asList("bean1L1", "bean1L2", "bean1L3");
            s1 = "bean1S1";
            i1 = 100;

            m = new HashMap<>();
            m.put("bean1Key1", "bean1Value1");
            m.put("bean1Key2", "bean1Value2");
            m.put("bean1Key3", "bean1Value3");
            props = new Properties();
            props.setProperty("bean1PropK1", "bean1PropV1");
            props.setProperty("bean1PropK2", "bean1PropV2");
            props.setProperty("bean1PropK3", "bean1PropV3");
        }

        public List<String> getL() {
            return l;
        }

        public void setL(List<String> l) {
            this.l = l;
        }

        public String getS1() {
            return s1;
        }

        public void setS1(String s1) {
            this.s1 = s1;
        }

        public int getI1() {
            return i1;
        }

        public void setI1(int i1) {
            this.i1 = i1;
        }

        public Map<String, String> getM() {
            return m;
        }

        public void setM(Map<String, String> m) {
            this.m = m;
        }

        public Properties getProps() {
            return props;
        }

        public void setProps(Properties props) {
            this.props = props;
        }

    }

    static class RepresentersConstructors {

        static class YamlRepresenters extends Representer {

            class RepresentUUID implements Represent {

                @Override
                public Node representData(Object o) {
                    final UUID uuid = (UUID) o;
                    return representScalar(new Tag("!uuid"), uuid.getMostSignificantBits() + " " + uuid.getLeastSignificantBits());
                }
            }

            class RepresentFile implements Represent {

                @Override
                public Node representData(Object arg0) {
                    final File file = (File) arg0;
                    return representScalar(new Tag("!file"), file.getPath());
                }
            }

            public YamlRepresenters() {
                this.representers.put(UUID.class, new RepresentUUID());
                this.representers.put(File.class, new RepresentFile());
            }

        }

        static class YamlConstructors extends Constructor {

            private class ConstructUUID extends AbstractConstruct {

                @Override
                public Object construct(Node node) {
                    final Object o = constructScalar((ScalarNode) node);
                    final String[] uuidBits = o.toString().split(" ");
                    return new UUID(Long.parseLong(uuidBits[0]), Long.parseLong(uuidBits[1]));
                }
            }

            class ConstructFile extends AbstractConstruct {

                @Override
                public Object construct(Node arg0) {
                    final Object o = constructScalar((ScalarNode) arg0);
                    final String filename = o.toString();
                    return new File(filename);
                }
            }

            public YamlConstructors() {
                this.yamlConstructors.put(new Tag("!uuid"), new ConstructUUID());
                this.yamlConstructors.put(new Tag("!file"), new ConstructFile());
            }

        }
    }
}
