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
import java.util.List;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.huberb.prototyping.beandata.dynabean.Delegates.Reference;

/**
 *
 * @author berni3
 */
public class Factories {

    @FunctionalInterface
    public static interface IFactory<T, E extends Exception> {

        T createInstance() throws E;
    }

    public static class EventFactory implements IFactory<DynaBean, ReflectiveOperationException> {

        public DynaClass createEventDynaClass() {
            final DynaProperty[] props = new DynaProperty[]{
                new DynaProperty("title", String.class),
                new DynaProperty("dtstart", String.class),
                new DynaProperty("dtend", String.class),
                new DynaProperty("duration", String.class),
                new DynaProperty("description", String.class)
            };
            final DynaClass dynaClass = new BasicDynaClass("Event", null, props);
            return dynaClass;
        }

        @Override
        public DynaBean createInstance() throws ReflectiveOperationException {
            final DynaClass dc = createEventDynaClass();
            final DynaBean event = dc.newInstance();
            return event;
        }
    }

    public static class CardFactory implements IFactory<DynaBean, ReflectiveOperationException> {

        public DynaClass createCardDynaClass() {
            final DynaProperty[] props = new DynaProperty[]{
                new DynaProperty("fn", String.class),
                new DynaProperty("n", String.class),
                new DynaProperty("familyname", String.class),
                new DynaProperty("givenname", String.class),
                new DynaProperty("honorificprefixes", String.class),
                new DynaProperty("honorificsuffixes", String.class),
                new DynaProperty("nickname", String.class),
                new DynaProperty("email", String.class),
                new DynaProperty("tel", String.class),
                new DynaProperty("note", String.class)
            };
            final DynaClass dynaClass = new BasicDynaClass("Card", null, props);
            return dynaClass;
        }

        @Override
        public DynaBean createInstance() throws ReflectiveOperationException {
            final DynaClass dc = createCardDynaClass();
            final DynaBean card = dc.newInstance();
            return card;
        }

    }

    public static class AddressFactory implements IFactory<DynaBean, ReflectiveOperationException> {

        public DynaClass createAddressDynaClass() {
            final DynaProperty[] props = new DynaProperty[]{
                new DynaProperty("street", String.class),
                new DynaProperty("street1", String.class),
                new DynaProperty("street2", String.class),
                new DynaProperty("city", String.class),
                new DynaProperty("plz", String.class),
                new DynaProperty("state", String.class),
                new DynaProperty("country", String.class)
            };
            final DynaClass dynaClass = new BasicDynaClass("Address", null, props);
            return dynaClass;
        }

        @Override
        public DynaBean createInstance() throws ReflectiveOperationException {
            final DynaClass dc = createAddressDynaClass();
            final DynaBean address = dc.newInstance();
            return address;

        }
    }

    public static class PersonFactory implements IFactory<DynaBean, ReflectiveOperationException> {

        public static enum PropNames {
            name, card, address
        };

        public DynaClass createPersonDynaClass() {
            final DynaClass dynaClass = new DynaClassBuilder()
                    .name("Person")
                    .prop(PropNames.name.name(), String.class)
                    .prop(PropNames.card.name(), Reference.class, DynaBean.class)
                    .prop(PropNames.address.name(), Reference.class, DynaBean.class)
                    .build();
            return dynaClass;
        }

        @Override
        public DynaBean createInstance() throws ReflectiveOperationException {
            final DynaClass dc = createPersonDynaClass();
            final DynaBean address = dc.newInstance();
            return address;
        }
    }

    public static class DynaClassBuilder implements IFactory<DynaBean, ReflectiveOperationException> {

        List<DynaProperty> l = new ArrayList<>();
        String name = "Bean";

        public DynaClassBuilder name(String name) {
            this.name = name;
            return this;
        }

        public DynaClassBuilder prop(String name, Class<?> clazz) {
            l.add(new DynaProperty(name, clazz));
            return this;
        }

        public DynaClassBuilder prop(String name, Class<?> clazz, Class<?> contentType) {
            l.add(new DynaProperty(name, clazz, contentType));
            return this;
        }

        public DynaClass build() {
            DynaProperty[] a = l.toArray(DynaProperty[]::new);
            final BasicDynaClass dynaClass = new BasicDynaClass(name, null, a);
            return dynaClass;
        }

        @Override
        public DynaBean createInstance() throws ReflectiveOperationException {
            final DynaClass dynaClass = build();
            final DynaBean dynaBean = dynaClass.newInstance();
            return dynaBean;
        }
    }
}
