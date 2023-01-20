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
import java.util.Collection;
import java.util.List;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;

/**
 * Build a {@link DynaClass}, and create a new instance of {@link DynaBean}.
 *
 * @author berni3
 */
public class DynaClassBuilder implements DomainFactories.IFactory<DynaBean, ReflectiveOperationException> {

    private final List<DynaProperty> l = new ArrayList<>();
    private String name = "Bean";

    public DynaClassBuilder name(String name) {
        this.name = name;
        return this;
    }

    public DynaClassBuilder propsFromEnumList(Collection<Enum> enumList) {
        enumList.forEach((e) -> prop(e.name()));
        return this;
    }

    public DynaClassBuilder propsFromNameList(Collection<String> nameList) {
        nameList.forEach((n) -> prop(n));
        return this;
    }

    public DynaClassBuilder prop(String name) {
        return prop(name, String.class);
    }

    public DynaClassBuilder prop(String name, Class<?> clazz) {
        this.l.add(new DynaProperty(name, clazz));
        return this;
    }

    public DynaClassBuilder prop(String name, Class<?> clazz, Class<?> contentType) {
        this.l.add(new DynaProperty(name, clazz, contentType));
        return this;
    }

    /**
     * Create a new {@link DynaClass} instance.
     *
     * @return
     */
    public DynaClass build() {
        DynaProperty[] a = l.toArray(DynaProperty[]::new);
        final BasicDynaClass dynaClass = new BasicDynaClass(name, null, a);
        return dynaClass;
    }

    /**
     * Create a new {@link DynaBean} instance.
     *
     * @return
     * @throws ReflectiveOperationException
     */
    @Override
    public DynaBean createInstance() throws ReflectiveOperationException {
        final DynaClass dynaClass = build();
        final DynaBean dynaBean = dynaClass.newInstance();
        return dynaBean;
    }

}
