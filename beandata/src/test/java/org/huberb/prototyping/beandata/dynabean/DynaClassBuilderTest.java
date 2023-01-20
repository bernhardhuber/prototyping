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
package org.huberb.prototyping.beandata.dynabean;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class DynaClassBuilderTest {

    public DynaClassBuilderTest() {
    }

    @Test
    public void test_DynaClassBuilder() throws ReflectiveOperationException {
        final DynaBean db1 = new DynaClassBuilder().name("DynaClass1")
                .prop("propString", String.class)
                .prop("propInteger", Integer.class)
                .createInstance();
    }

    enum SomePropNames {
        prop1, prop2, prop3
    }

    @Test
    public void test_DynaClassBuilder_with_enum_SomePropNames() throws ReflectiveOperationException {
        // create DynBean from SomrPtopNames enum
        final DynaClassBuilder dcb = new DynaClassBuilder().name("DynaClass1");
        Arrays.asList(SomePropNames.values()).stream()
                .map((e) -> e.name())
                .forEach((name) -> dcb.prop(name, String.class));
        final DynaBean db1 = dcb.createInstance();

        Arrays.asList(SomePropNames.values()).stream()
                .map((e) -> e.name())
                .forEach((name) -> db1.set(name, name + "Value"));
        assertAll(
                () -> assertEquals("prop1Value", db1.get(SomePropNames.prop1.name())),
                () -> assertEquals("prop2Value", db1.get(SomePropNames.prop2.name())),
                () -> assertEquals("prop3Value", db1.get(SomePropNames.prop3.name()))
        );
    }

    enum SomePropNamesWithDefaults {
        prop1("prop1DefaultValue"),
        prop2("prop2DefaultValue"), prop3("prop3DefaultValue");

        private final String defaultValue;

        private SomePropNamesWithDefaults(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

    }

    @Test
    public void test_DynaClassBuilder_with_enum_SomePropNamesWithDefaults() throws ReflectiveOperationException {
        // create DynBean from SomePropNamesWithDefaults enum
        final DynaClassBuilder dcb = new DynaClassBuilder().name("DynaClass1");
        Arrays.asList(SomePropNamesWithDefaults.values()).stream()
                .map((e) -> e.name())
                .forEach((name) -> dcb.prop(name, String.class));
        final DynaBean db1 = dcb.createInstance();

        Arrays.asList(SomePropNamesWithDefaults.values()).stream()
                .forEach((e) -> db1.set(e.name(), e.getDefaultValue()));
        assertAll(
                () -> assertEquals("prop1DefaultValue", db1.get(SomePropNamesWithDefaults.prop1.name())),
                () -> assertEquals("prop2DefaultValue", db1.get(SomePropNamesWithDefaults.prop2.name())),
                () -> assertEquals("prop3DefaultValue", db1.get(SomePropNamesWithDefaults.prop3.name()))
        );
    }

    @Test
    public void test_BeanUtilsBean2_populate() throws ReflectiveOperationException {
        final DynaBean db1 = new DynaClassBuilder().name("DynaClass1")
                .prop("propString", String.class)
                .prop("propInteger", Integer.class)
                .createInstance();
        final Map<String, Object> m = new LinkedHashMap<>();
        m.put("propString", "propStringValue1");
        m.put("propInteger", "1");
        new BeanUtilsBean2().populate(db1, m);

        for (final Map.Entry<String, Object> e : m.entrySet()) {
            final String name = e.getKey();
            final Object expectedValue = e.getValue();
            final Object value = new BeanUtilsBean2().getProperty(db1, name);
            assertEquals(expectedValue, value, "" + name + ", " + expectedValue + ", " + value);
        }
        assertAll(
                () -> assertEquals("propStringValue1", new BeanUtilsBean().getProperty(db1, "propString")),
                () -> assertEquals("propStringValue1", new BeanUtilsBean().getSimpleProperty(db1, "propString")),
                () -> assertEquals("1", new BeanUtilsBean().getProperty(db1, "propInteger")),
                () -> assertEquals("1", new BeanUtilsBean().getSimpleProperty(db1, "propInteger"))
        );
        assertAll(
                () -> assertEquals("propStringValue1", new BeanUtilsBean2().getProperty(db1, "propString")),
                () -> assertEquals("propStringValue1", new BeanUtilsBean2().getSimpleProperty(db1, "propString")),
                () -> assertEquals("1", new BeanUtilsBean2().getProperty(db1, "propInteger")),
                () -> assertEquals("1", new BeanUtilsBean2().getSimpleProperty(db1, "propInteger"))
        );
        assertAll(
                () -> assertEquals("propStringValue1", new PropertyUtilsBean().getProperty(db1, "propString")),
                () -> assertEquals("propStringValue1", new PropertyUtilsBean().getSimpleProperty(db1, "propString")),
                () -> assertEquals(Integer.valueOf(1), new PropertyUtilsBean().getProperty(db1, "propInteger")),
                () -> assertEquals(Integer.valueOf(1), new PropertyUtilsBean().getSimpleProperty(db1, "propInteger"))
        );

    }

}
