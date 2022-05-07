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

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtilsBean;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class SimpleDynaBeanTest {

    @Test
    public void given_dynabean_then_props_are_set() throws ReflectiveOperationException {
        final DynaBean employee = createEmployee();
        assertAll(
                () -> assertEquals("Fred", employee.get("firstName")),
                () -> assertEquals("Flintstone", employee.get("lastName"))
        );

    }

    @Test
    public void given_dynabean_then_a_dynabeans_describe_exists() throws ReflectiveOperationException {

        final DynaBean employee = createEmployee();
        employee.set("address", "street", "street1");
        employee.set("address", "city", "city1");
        final Map<String, Object> describeMap = new PropertyUtilsBean().describe(employee);
        final String m = "" + describeMap;
        System.out.printf("given_dynabean_then_a_dynabeans_describe_exists describeMap %s%n", m);
        assertAll(
                () -> assertTrue(m.contains("firstName=Fred"), m),
                () -> assertTrue(m.contains("lastName=Flintstone"), m)
        );
    }

    DynaBean createEmployee() throws ReflectiveOperationException {
        final DynaProperty[] props = new DynaProperty[]{
            new DynaProperty("address", java.util.Map.class),
            new DynaProperty("subordinate", Employee[].class),
            new DynaProperty("firstName", String.class),
            new DynaProperty("lastName", String.class)
        };
        final BasicDynaClass dynaClass = new BasicDynaClass("employee", null, props);

        final DynaBean employee = dynaClass.newInstance();
        employee.set("address", new HashMap());
        employee.set("subordinate", new Employee[0]);
        employee.set("firstName", "Fred");
        employee.set("lastName", "Flintstone");
        return employee;
    }

    static class Employee {

        private String name;
        private String departname;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDepartname() {
            return departname;
        }

        public void setDepartname(String departname) {
            this.departname = departname;
        }

    }
}
