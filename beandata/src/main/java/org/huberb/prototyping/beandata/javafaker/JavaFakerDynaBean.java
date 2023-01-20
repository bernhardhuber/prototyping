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
package org.huberb.prototyping.beandata.javafaker;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.datafaker.Faker;
import net.datafaker.Name;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.beanutils.LazyDynaClass;
import org.huberb.prototyping.beandata.dynabean.DynaClassBuilder;

/**
 *
 * @author berni3
 */
public class JavaFakerDynaBean {

    DynaBean english() throws ReflectiveOperationException {
        final Faker faker = new Faker(Locale.ENGLISH);

        final Name fakerName = faker.name();
        final String name = fakerName.fullName(); // Miss Samanta Schmidt
        final String firstName = fakerName.firstName(); // Emory
        final String lastName = fakerName.lastName(); // Barton

        final String streetAddress = faker.address().streetAddress(); // 60018 Sawayn Brooks Suite 449

        final DynaBean db1 = new DynaClassBuilder()
                .name("DynaBean1")
                .prop("name")
                .prop("firstName")
                .prop("lastName")
                .prop("streetAddress")
                .createInstance();

        db1.set("name", name);
        db1.set("firstName", firstName);
        db1.set("lastName", lastName);
        db1.set("streetAddress", streetAddress);
        return db1;
    }

    DynaBean de_AT() throws ReflectiveOperationException {
        //Locale locale = Locale.forLanguageTag("de-AT");
        Locale locale = new Locale("de", "AT");
        final Faker faker = new Faker(locale);

        final String name = faker.name().fullName(); // Miss Samanta Schmidt
        final String firstName = faker.name().firstName(); // Emory
        final String lastName = faker.name().lastName(); // Barton

        final String streetAddress = faker.address().streetAddress(); // 60018 Sawayn Brooks Suite 449

        final DynaBean db1 = new DynaClassBuilder()
                .name("DynaBean1")
                .prop("name")
                .prop("firstName")
                .prop("lastName")
                .prop("streetAddress")
                .createInstance();

        db1.set("name", name);
        db1.set("firstName", firstName);
        db1.set("lastName", lastName);
        db1.set("streetAddress", streetAddress);
        return db1;
    }

    DynaBean de_BasicDynaBean() throws ReflectiveOperationException {
        final Locale locale = new Locale("de");
        final Faker faker = new Faker(locale);

        final String n1 = faker.numerify("AT-####");
        final String tel_1_mobile = faker.numerify("+43 66# ### ### ###");
        final String tel_2_0800 = faker.numerify("0800 ### ### ###");
        final String kfz_1 = faker.bothify("?? ### ??", true);
        final Map<String, String> m = new HashMap<>();
        m.put("n1", n1);
        m.put("tel_1_mobile", tel_1_mobile);
        m.put("tel_2_0800", tel_2_0800);
        m.put("kfz_1", kfz_1);

        final DynaBean db1 = new DynaClassBuilder()
                .name("DynaBean1")
                .prop("n1")
                .prop("tel_1_mobile")
                .prop("tel_2_0800")
                .prop("kfz_1")
                .createInstance();
        new BeanUtilsBean().populate(db1, m);

        final LazyDynaBean ldb1 = new LazyDynaBean("LazyDynaBean1");
        new BeanUtilsBean().populate(ldb1, m);

        return db1;
    }

    DynaBean de_LazyDynaBean() throws ReflectiveOperationException {
        final Locale locale = new Locale("de");
        final Faker faker = new Faker(locale);

        final String n1 = faker.numerify("AT-####");
        final String tel_1_mobile = faker.numerify("+43 66# ### ### ###");
        final String tel_2_0800 = faker.numerify("0800 ### ### ###");
        final String kfz_1 = faker.bothify("?? ### ??", true);
        final Map<String, String> m = new HashMap<>();
        m.put("n1", n1);
        m.put("tel_1_mobile", tel_1_mobile);
        m.put("tel_2_0800", tel_2_0800);
        m.put("kfz_1", kfz_1);

        final LazyDynaBean ldb1 = new LazyDynaBean("LazyDynaBean1");

        if (!((LazyDynaClass) ldb1.getDynaClass()).isRestricted()) {
            new BeanUtilsBean().populate(ldb1, m);
        }
        new BeanUtilsBean().copyProperties(ldb1, m);

        return ldb1;
    }
}
