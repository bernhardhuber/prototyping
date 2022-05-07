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
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.LazyDynaList;
import org.apache.commons.beanutils.PropertyUtilsBean;

/**
 *
 * @author berni3
 */
public class EeingangAntragFactory {

    public BasicDynaClass createKundeDynaClass() {
        final DynaProperty[] props = new DynaProperty[]{
            new DynaProperty("svnr", Svnr.class),
            new DynaProperty("vorname", String.class),
            new DynaProperty("zuname", String.class),
            new DynaProperty("afsnummerList", List.class)
        };
        final BasicDynaClass dynaClass = new BasicDynaClass("Kunde", null, props);
        return dynaClass;
    }

    public DynaBean createKunde(Svnr svnr, String fn, String ln) throws ReflectiveOperationException {
        final BasicDynaClass dc = createKundeDynaClass();
        final DynaBean kunde = dc.newInstance();
        kunde.set("svnr", svnr);
        kunde.set("vorname", fn);
        kunde.set("zuname", ln);
        kunde.set("afsnummerList", new ArrayList<String>());
        return kunde;
    }

    public BasicDynaClass createEeingangDynaClass() {
        final DynaProperty[] props = new DynaProperty[]{
            new DynaProperty("svnr", Svnr.class),
            new DynaProperty("afsnummer",
            String.class)
        };
        final BasicDynaClass dynaClass = new BasicDynaClass("EEingang", null, props);
        return dynaClass;
    }

    public DynaBean createEeingang(Svnr svnr, String afsnummer) throws ReflectiveOperationException {
        final BasicDynaClass dc = createEeingangDynaClass();
        final DynaBean eeingang = dc.newInstance();
        eeingang.set("svnr", svnr);
        eeingang.set("afsnummer", afsnummer);
        return eeingang;
    }

    public BasicDynaClass createAntragDynaClass() {
        final DynaProperty[] props = new DynaProperty[]{
            new DynaProperty("antragId", String.class),
            new DynaProperty("eeingangList", LazyDynaList.class),
            new DynaProperty("fallList", LazyDynaList.class)
        };
        final BasicDynaClass dynaClass = new BasicDynaClass("Antrag", null, props);
        return dynaClass;
    }

    public BasicDynaClass createFallDynaClass() {
        final DynaProperty[] props = new DynaProperty[]{
            new DynaProperty("fallId", String.class),
            new DynaProperty("konto", String.class),
            new DynaProperty("rechnungList",
            LazyDynaList.class)};
        final BasicDynaClass dynaClass = new BasicDynaClass("Fall", null, props);
        return dynaClass;
    }

    public BasicDynaClass createRechnungDynaClass() {
        final DynaProperty[] props = new DynaProperty[]{
            new DynaProperty("rechnungId", String.class),
            new DynaProperty("leistungserbringer", String.class),
            new DynaProperty("leistungsempfaenger", String.class),
            new DynaProperty("leistungszeileList", LazyDynaList.class)
        };
        final BasicDynaClass dynaClass = new BasicDynaClass("Rechnung", null, props);
        return dynaClass;
    }

    static class IdGenerator {

        private final static AtomicInteger atomicInteger = new AtomicInteger(1);
        private final static IdGenerator INSTANCE = new IdGenerator();

        private IdGenerator() {
        }

        public static IdGenerator instance() {
            return INSTANCE;
        }

        String generatorNextId(String prefix) {
            String v = "" + prefix + atomicInteger.getAndAdd(1);
            return v;
        }
    }

    static <T> T getIt(Object bean, Class<? extends T> clazz) {
        final T convertedToT = (T) bean;
        return convertedToT;
    }

    public DynaBean createAntragFallRechnung(Svnr svnr) throws ReflectiveOperationException {
        final IdGenerator idGenerator = IdGenerator.instance();

        final BasicDynaClass dcAntrag = createAntragDynaClass();
        final BasicDynaClass dcFall = createFallDynaClass();
        final BasicDynaClass dcRechnung = createRechnungDynaClass();
        //---
        final DynaBean antrag = dcAntrag.newInstance();
        antrag.set("antragId", idGenerator.generatorNextId("A"));
        antrag.set("eeingangList", new LazyDynaList());
        antrag.set("fallList", new LazyDynaList(dcFall));
        //---
        final DynaBean fall = dcFall.newInstance();
        fall.set("fallId", idGenerator.generatorNextId("F"));
        fall.set("konto", "keineKonto");
        fall.set("rechnungList", new LazyDynaList(dcRechnung));
        //---
        final DynaBean rechnung = dcRechnung.newInstance();
        rechnung.set("rechnungId", idGenerator.generatorNextId("R"));
        rechnung.set("leistungserbringer", "keinLeistunserbringer");
        rechnung.set("leistungsempfaenger", "keinLeistungsempfaenger");
        rechnung.set("leistungszeileList", new LazyDynaList(String.class));
        getIt(new PropertyUtilsBean().getSimpleProperty(antrag, "fallList"), LazyDynaList.class).add(fall);
        //new PropertyUtilsBean().setIndexedProperty(antrag, "fallList", 0, fall);
        new PropertyUtilsBean().setIndexedProperty(fall, "rechnungList", 0, rechnung);
        return antrag;
    }

    BasicDynaClass createDokumentDynaClass() {
        final DynaProperty[] props = new DynaProperty[]{
            new DynaProperty("dokumentId", String.class),
            new DynaProperty("qsosId", String.class),
            new DynaProperty("qlaId", String.class),
            new DynaProperty("dokumentInhalt", String.class)
        };
        final BasicDynaClass dynaClass = new BasicDynaClass("Dokument", null, props);
        return dynaClass;
    }

}
