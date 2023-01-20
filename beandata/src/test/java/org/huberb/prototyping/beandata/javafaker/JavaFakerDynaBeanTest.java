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

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class JavaFakerDynaBeanTest {

    public JavaFakerDynaBeanTest() {
    }

    /**
     * Test of english method, of class JavaFakerDynaBean.
     */
    @Test
    public void test_english() throws Exception {
        JavaFakerDynaBean instance = new JavaFakerDynaBean();
        DynaBean result = instance.english();
        System.out.printf("test_english result %s%n", PropertyUtils.describe(result));
    }

    /**
     * Test of de_AT method, of class JavaFakerDynaBean.
     */
    @Test
    public void test_de_AT() throws Exception {
        JavaFakerDynaBean instance = new JavaFakerDynaBean();
        DynaBean result = instance.de_AT();
        System.out.printf("test_de_AT result %s%n", PropertyUtils.describe(result));
    }
    @Test
    public void test_de_BasicDynaBean() throws Exception {
        JavaFakerDynaBean instance = new JavaFakerDynaBean();
        DynaBean result = instance.de_BasicDynaBean();
        System.out.printf("test_de_BasicDynaBean result %s%n", PropertyUtils.describe(result));
    }
    @Test
    public void test_de_LazyDynaBean() throws Exception {
        JavaFakerDynaBean instance = new JavaFakerDynaBean();
        DynaBean result = instance.de_LazyDynaBean();
        System.out.printf("test_de_LazyDynaBean result %s%n", PropertyUtils.describe(result));
    }

}
