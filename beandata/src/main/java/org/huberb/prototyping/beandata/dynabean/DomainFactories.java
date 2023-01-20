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

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.huberb.prototyping.beandata.dynabean.Delegates.Reference;

/**
 *
 * @author berni3
 */
public class DomainFactories {

    @FunctionalInterface
    public static interface IFactory<T, E extends Exception> {

        T createInstance() throws E;
    }

    public static class EventFactory implements IFactory<DynaBean, ReflectiveOperationException> {

        public static enum PropNames {
            title,
            dtstart,
            dtend,
            duration,
            description
        };

        public DynaClass createEventDynaClass() {
            final DynaProperty[] props = new DynaProperty[]{
                new DynaProperty(PropNames.title.name(), String.class),
                new DynaProperty(PropNames.dtstart.name(), String.class),
                new DynaProperty(PropNames.dtend.name(), String.class),
                new DynaProperty(PropNames.duration.name(), String.class),
                new DynaProperty(PropNames.description.name(), String.class)
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

        public static enum PropNames {
            fn,
            n,
            familyname, givenname,
            honorificprefixes, honorificsuffixes,
            nickname,
            email, tel,
            note
        };

        public DynaClass createCardDynaClass() {
            final DynaProperty[] props = new DynaProperty[]{
                new DynaProperty(PropNames.fn.name(), String.class),
                new DynaProperty(PropNames.n.name(), String.class),
                new DynaProperty(PropNames.familyname.name(), String.class),
                new DynaProperty(PropNames.givenname.name(), String.class),
                new DynaProperty(PropNames.honorificprefixes.name(), String.class),
                new DynaProperty(PropNames.honorificsuffixes.name(), String.class),
                new DynaProperty(PropNames.nickname.name(), String.class),
                new DynaProperty(PropNames.email.name(), String.class),
                new DynaProperty(PropNames.tel.name(), String.class),
                new DynaProperty(PropNames.note.name(), String.class)
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

        public static enum PropNames {
            street, street1, street2,
            city,
            plz,
            state,
            country
        };

        public DynaClass createAddressDynaClass() {
            final DynaProperty[] props = new DynaProperty[]{
                new DynaProperty(PropNames.street.name(), String.class),
                new DynaProperty(PropNames.street1.name(), String.class),
                new DynaProperty(PropNames.street2.name(), String.class),
                new DynaProperty(PropNames.city.name(), String.class),
                new DynaProperty(PropNames.plz.name(), String.class),
                new DynaProperty(PropNames.state.name(), String.class),
                new DynaProperty(PropNames.country.name(), String.class)
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

}
