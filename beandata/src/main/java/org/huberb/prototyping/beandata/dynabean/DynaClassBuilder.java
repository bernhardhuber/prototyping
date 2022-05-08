/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.huberb.prototyping.beandata.dynabean;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;

/**
 *
 * @author berni3
 */
public class DynaClassBuilder implements Factories.IFactory<DynaBean, ReflectiveOperationException> {
    
    private final List<DynaProperty> l = new ArrayList<>();
    private String name = "Bean";

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
