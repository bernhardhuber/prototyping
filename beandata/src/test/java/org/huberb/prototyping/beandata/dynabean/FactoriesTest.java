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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.huberb.prototyping.beandata.dynabean.Delegates.Reference;
import org.huberb.prototyping.beandata.dynabean.Delegates.Value;
import org.huberb.prototyping.beandata.dynabean.Factories.PersonFactory;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class FactoriesTest {

    public FactoriesTest() {
    }

    @Test
    public void hello() throws ReflectiveOperationException {
        final DynaBean db = new Factories.EventFactory().createInstance();

        final DynaProperty dbTitle = db.getDynaClass().getDynaProperty("title");
        assertAll(
                () -> assertEquals("title", dbTitle.getName()),
                () -> assertEquals(java.lang.String.class, dbTitle.getType()),
                () -> assertEquals(null, dbTitle.getContentType())
        );

        System.out.printf("PropertyUtils.describe(%s) : %s%n", db, org.apache.commons.beanutils.PropertyUtils.describe(db));
        System.out.printf("BeanUtils.describe(%s) : %s%n", db, org.apache.commons.beanutils.BeanUtils.describe(db));

    }

    @Test
    public void given_DynaBean_Event_then_title_is_setable() throws ReflectiveOperationException {
        DynaBean db = new Factories.EventFactory().createInstance();

        for (DynaProperty dp : Arrays.asList(db.getDynaClass().getDynaProperties())) {
            if (!dp.isIndexed()
                    && !dp.isMapped()
                    && dp.getType().isAssignableFrom(String.class)) {
                String n = dp.getName();
                if (db.get(n) == null) {
                    db.set(n, "");
                }
            }
        }
        assertAll(
                () -> assertNotNull(db),
                () -> assertEquals("", db.get("title"))
        );

    }

    public void given_Event_and_retrieving_unknonwproperty_then_IllegalArgumentException_is_thrown() throws ReflectiveOperationException {
        final DynaBean db = new Factories.EventFactory().createInstance();

        assertAll(
                () -> assertNotNull(db),
                () -> {
                    try {
                        assertEquals(null, db.get("unknownproperty"));
                        fail("Expecting exception 'IllegalArgumentException'");
                    } catch (IllegalArgumentException iaex) {
                        String m = iaex.getMessage();
                        assertTrue(m.contains("Invalid property name 'unknownproperty'"), m);
                    }
                }
        );
    }

    @Test
    public void hello2() throws ReflectiveOperationException {
        final DynaBean dbEvent = new Factories.EventFactory().createInstance();
        final DynaBean dbAddress = new Factories.AddressFactory().createInstance();
        final DynaBean dbCard = new Factories.CardFactory().createInstance();

        final DynaBean dbPerson = new Factories.PersonFactory().createInstance();

        final Function<DynaBean, List<DynaProperty>> f = (db) -> Arrays.asList(db.getDynaClass().getDynaProperties());
        final BiConsumer<DynaBean, List<DynaProperty>> c = new BiConsumer<>() {
            @Override
            public void accept(DynaBean t, List<DynaProperty> l) {
                l.stream()
                        .filter((dp) -> !dp.isIndexed())
                        .filter((dp) -> !dp.isMapped())
                        .filter((dp) -> dp.getType().isAssignableFrom(String.class))
                        .filter((dp) -> dp.getName() != null)
                        .filter((dp) -> t.get(dp.getName()) == null)
                        .forEach((dp) -> {
                            t.set(dp.getName(), "");
                        });
            }
        };

        c.accept(dbEvent, f.apply(dbEvent));
        c.accept(dbAddress, f.apply(dbAddress));
        c.accept(dbCard, f.apply(dbCard));
        c.accept(dbPerson, f.apply(dbPerson));

        assertAll(
                () -> assertEquals("", dbEvent.get("title")),
                () -> assertEquals("", dbEvent.get("description"))
        );
        assertAll(
                () -> assertEquals("", dbAddress.get("street")),
                () -> assertEquals("", dbAddress.get("city"))
        );
        assertAll(
                () -> assertEquals("", dbCard.get("n")),
                () -> assertEquals("", dbCard.get("email"))
        );
        assertAll(
                () -> {
                    assertEquals("", dbPerson.get("name"));
                    assertEquals(null, dbPerson.get("card"));
                    assertEquals(null, dbPerson.get("address"));
                },
                () -> {
                    assertEquals("", dbPerson.get(PersonFactory.PropNames.name.name()));
                    assertEquals(null, dbPerson.get(PersonFactory.PropNames.card.name()));
                    assertEquals(null, dbPerson.get(PersonFactory.PropNames.address.name()));
                }
        );

    }

    @Test
    public void hello3() throws ReflectiveOperationException {
        final DynaBean dbEvent = new Factories.EventFactory().createInstance();
        final DynaBean dbAddress = new Factories.AddressFactory().createInstance();
        final DynaBean dbCard = new Factories.CardFactory().createInstance();

        final DynaBean dbPerson = new Factories.PersonFactory().createInstance();

        final Function<DynaBean, Map<String, Object>> f = (db) -> {
            final Map<String, Object> m = new HashMap<>();
            final List<DynaProperty> l = Arrays.asList(db.getDynaClass().getDynaProperties());
            l.stream()
                    .filter((dp) -> !dp.isIndexed())
                    .filter((dp) -> !dp.isMapped())
                    .filter((dp) -> dp.getType().isAssignableFrom(String.class))
                    .filter((dp) -> dp.getName() != null)
                    .filter((dp) -> db.get(dp.getName()) == null)
                    .forEach((dp) -> {
                        m.put(dp.getName(), "");
                    });
            return m;
        };
        BeanUtils.populate(dbEvent, f.apply(dbEvent));
        BeanUtils.populate(dbAddress, f.apply(dbAddress));
        BeanUtils.populate(dbCard, f.apply(dbCard));
        BeanUtils.populate(dbPerson, f.apply(dbPerson));

        assertAll(
                () -> assertEquals("", dbEvent.get("title")),
                () -> assertEquals("", dbEvent.get("description"))
        );
        assertAll(
                () -> assertEquals("", dbAddress.get("street")),
                () -> assertEquals("", dbAddress.get("city"))
        );
        assertAll(
                () -> assertEquals("", dbCard.get("n")),
                () -> assertEquals("", dbCard.get("email"))
        );
        assertAll(
                () -> {
                    assertEquals("", dbPerson.get("name"));
                    assertEquals(null, dbPerson.get("card"));
                    assertEquals(null, dbPerson.get("address"));
                },
                () -> {
                    assertEquals("", dbPerson.get(PersonFactory.PropNames.name.name()));
                    assertEquals(null, dbPerson.get(PersonFactory.PropNames.card.name()));
                    assertEquals(null, dbPerson.get(PersonFactory.PropNames.address.name()));
                }
        );

    }

    @Test
    public void hello4() throws ReflectiveOperationException {
        final DynaBean dbEvent = new Factories.EventFactory().createInstance();
        final DynaBean dbAddress = new Factories.AddressFactory().createInstance();
        final DynaBean dbCard = new Factories.CardFactory().createInstance();

        final DynaBean dbPerson = new Factories.PersonFactory().createInstance();

        BeanUtils.setProperty(dbPerson, PersonFactory.PropNames.name.name(), "name");
        BeanUtils.setProperty(dbPerson, PersonFactory.PropNames.card.name(), new Reference<DynaBean>(dbCard));
        BeanUtils.setProperty(dbPerson, PersonFactory.PropNames.address.name(), new Reference<DynaBean>(dbAddress));

        BeanUtils.setProperty(dbPerson, "name", "name1");
        BeanUtils.setProperty(dbPerson, "card.value.fn", "fn1");
        BeanUtils.setProperty(dbPerson, "card.value.nickname", "nickname1");
        BeanUtils.setProperty(dbPerson, "address.value.city", "city1");
        BeanUtils.setProperty(dbPerson, "address.value.country", "country1");

        final Function<DynaBean, Map<String, Object>> f = (db) -> {
            final Map<String, Object> m = new LinkedHashMap<>();
            final List<DynaProperty> l = Arrays.asList(db.getDynaClass().getDynaProperties());
            l.stream()
                    .filter((dp) -> dp.getName() != null)
                    .forEach((dp) -> {
                        final String name = dp.getName();
                        final Object value = db.get(name);
                        m.put(name, value);
                    });
            return m;
        };
        System.out.printf("%s properties%s%n", dbEvent, f.apply(dbEvent));
        System.out.printf("%s properties%s%n", dbPerson, f.apply(dbPerson));

        Map<String, Object> m = f.apply(dbPerson);
        m.put("name", "name2");
        m.put("card.value.fn", "fn2");
        m.put("card.value.nickname", "nickname2");
        m.put("address.value.city", "city2");
        m.put("address.value.country", "country2");

        new org.apache.commons.beanutils.BeanUtilsBean().populate(dbPerson, m);
        new org.apache.commons.beanutils.BeanUtilsBean2().populate(dbPerson, m);
        System.out.printf("%s function properties %s%n", dbPerson, f.apply(dbPerson));

        final List<String> propNames = new PropertyVistor().dynaPropertyVistor(dbPerson);
        System.out.printf("%s dynaPropertyVistor%n%s%n", dbPerson, propNames.stream().collect(Collectors.joining("\n")));
    }

    @Test
    public void hello5() throws ReflectiveOperationException {
        final DynaBean db1 = new Factories.DynaClassBuilder().name("Dyna1")
                .prop("prop1", String.class)
                .prop("prop2", Integer[].class)
                .prop("prop3", Map.class, Double.class)
                .prop("prop4", List.class, Double.class)
                .prop("prop5", Reference.class, Long.class)
                .prop("prop6", Value.class, Long.class)
                .createInstance();
        db1.set("prop1", "Prop1Value");
        db1.set("prop2", new Integer[]{1, 2});
        db1.set("prop3", new HashMap<String, Double>());
        db1.set("prop3", "p1", 12.3d);
        db1.set("prop4", Arrays.asList(3, 4));
        db1.set("prop5", new Reference<Long>(5L));
        db1.set("prop6", new Value<Long>(6L));
        final List<String> propNames = new PropertyVistor().dynaPropertyVistor(db1);
        System.out.printf("%s%ndynaPropertyVistor %s%n", db1, propNames.stream().collect(Collectors.joining("\n")));
    }

    static class PropertyVistor {

        public List<String> dynaPropertyVistor(DynaBean db) {
            final Stack<String> prefixes = new Stack<>();
            prefixes.add("");
            int i = 0;
            List<String> m = new ArrayList<String>();
            return _dynaPropertyVistor(i, db, m, prefixes);
        }

        List<String> _dynaPropertyVistor(int i, DynaBean db, List<String> m, java.util.Stack<String> prefixes) {
            if (i > 100) {
                return m;
            }
            String prefix = prefixes.pop();
            final List<DynaProperty> l = Arrays.asList(db.getDynaClass().getDynaProperties());
            for (DynaProperty dp : l) {
                String name = dp.getName();
                Class<?> typeClass = dp.getType();
                Class<?> contentTypeClass = dp.getContentType();
                Object value = db.get(name);
                String fullName = "".equals(prefix) ? name : prefix + "." + name;
                if (1 == 1) {
                    m.add(String.format("name: %s, dp: %s", fullName, dp));
                } else {
                    m.add(String.format("name: %s, value: %s, "
                            + "typeClass: %s, isIndexed: %s, isMapped: %s, "
                            + "contentTypeClass: %s",
                            fullName,
                            value,
                            String.valueOf(typeClass), dp.isIndexed(), dp.isMapped(),
                            String.valueOf(contentTypeClass))
                    );
                }

                if (typeClass != null && typeClass.isAssignableFrom(DynaBean.class)) {
                    DynaBean dpValue = (DynaBean) value;
                    prefixes.push(name);
                    List<String> result = _dynaPropertyVistor(i + 1, dpValue, m, prefixes);
                } else if (typeClass != null && typeClass.isAssignableFrom(Delegates.Reference.class)) {
                    if (contentTypeClass != null && contentTypeClass.isAssignableFrom(DynaBean.class)) {
                        DynaBean dpValue = (DynaBean) ((Delegates.Reference) value).getValue();
                        prefixes.push(fullName + ".value");
                        List<String> result = _dynaPropertyVistor(i + 1, dpValue, m, prefixes);
                    }
                } else if (typeClass != null && typeClass.isAssignableFrom(Delegates.Value.class)) {
                    if (contentTypeClass != null && contentTypeClass.isAssignableFrom(DynaBean.class)) {
                        DynaBean dpValue = (DynaBean) ((Delegates.Value) value).getValue();
                        prefixes.push(fullName + ".value");
                        List<String> result = _dynaPropertyVistor(i + 1, dpValue, m, prefixes);
                    }
                }
            }
            return m;
        }
    }
}
