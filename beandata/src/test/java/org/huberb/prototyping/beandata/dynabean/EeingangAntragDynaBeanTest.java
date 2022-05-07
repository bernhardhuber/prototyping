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

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class EeingangAntragDynaBeanTest {

    public EeingangAntragDynaBeanTest() {
    }

    @Test
    public void testDescribe() throws ReflectiveOperationException {
        final EeingangAntragFactory factory = new EeingangAntragFactory();

        final DynaBean kunde1 = factory.createKunde(new Svnr("svnrk:1"), "vnk1", "znk1");
        final String kunde1Message = "" + PropertyUtils.describe(kunde1);
        System.out.printf("kunde1 %s%n", kunde1Message);

        final DynaBean antrag1 = factory.createAntragFallRechnung(new Svnr("svnr1erb:1"));
        final String antrag1Message = ""
                + PropertyUtils.describe(antrag1);
        System.out.printf("antrag1 %s%n", antrag1Message);

        PropertyUtils.setProperty(antrag1, "fallList[0].konto", "IBAN1");
        System.out.printf("antrag1.fallList[0] %s%n", PropertyUtils.describe(PropertyUtils.getProperty(antrag1, "fallList[0]")));
    }

    @Test
    public void testIntrospection() throws ReflectiveOperationException {
        /*
PropertyDescriptor[] pdKunde1 [
  java.beans.PropertyDescriptor[
    name=map; 
    values={
      expert=false; visualUpdate=false; hidden=false; enumerationValues=[Ljava.lang.Object;@4d5b6aac; required=false
    }; 
    propertyType=interface java.util.Map; 
    readMethod=public java.util.Map org.apache.commons.beanutils.BasicDynaBean.getMap()
  ], 
  java.beans.PropertyDescriptor[
    name=dynaClass; 
    values={
      expert=false; 
      visualUpdate=false; 
      hidden=false; 
      enumerationValues=[Ljava.lang.Object;@3e84448c; 
      required=false
    }; 
    propertyType=interface org.apache.commons.beanutils.DynaClass; 
    readMethod=public org.apache.commons.beanutils.DynaClass org.apache.commons.beanutils.BasicDynaBean.getDynaClass()
  ]
]

PropertyDescriptor[] pdAntrag1 [
  java.beans.PropertyDescriptor[
    name=map; 
    values={expert=false; visualUpdate=false; hidden=false; enumerationValues=[Ljava.lang.Object;@4d5b6aac; required=false}; 
    propertyType=interface java.util.Map; readMethod=public java.util.Map org.apache.commons.beanutils.BasicDynaBean.getMap()
  ], 
  java.beans.PropertyDescriptor[
    name=dynaClass; 
    values={expert=false; visualUpdate=false; hidden=false; enumerationValues=[Ljava.lang.Object;@3e84448c; required=false
  }; 
  propertyType=
    interface org.apache.commons.beanutils.DynaClass; 
    readMethod=public org.apache.commons.beanutils.DynaClass org.apache.commons.beanutils.BasicDynaBean.getDynaClass()
  ]
]
         */
        final EeingangAntragFactory factory = new EeingangAntragFactory();

        final DynaBean kunde1 = factory.createKunde(new Svnr("svnrk:1"), "vnk1", "znk1");
        PropertyDescriptor[] pdKunde1 = PropertyUtils.getPropertyDescriptors(kunde1);
        System.out.printf("PropertyDescriptor[] pdKunde1 %s%n", Arrays.toString(pdKunde1));

        final DynaBean antrag1 = factory.createAntragFallRechnung(new Svnr("svnr1erb:1"));
        PropertyDescriptor[] pdAntrag1 = PropertyUtils.getPropertyDescriptors(antrag1);
        System.out.printf("PropertyDescriptor[] pdAntrag1 %s%n", Arrays.toString(pdAntrag1));
    }

}
