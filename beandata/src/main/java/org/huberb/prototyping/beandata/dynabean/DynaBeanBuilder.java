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

import java.util.Map;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.PropertyUtilsBean;

/**
 *
 * @author berni3
 */
public class DynaBeanBuilder {

    final DynaBean db;

    public DynaBeanBuilder(DynaBean db) {
        this.db = db;
    }

    DynaBeanBuilder value(String k, Object v) {
        db.set(k, v);
        return this;
    }

    DynaBeanBuilder propValues(Map<String, Object> m) throws ReflectiveOperationException {
        //new BeanUtilsBean().copyProperties(db, m);
        //new BeanUtilsBean2().copyProperties(db, m);
        new PropertyUtilsBean().copyProperties(db, m);
        return this;
    }

    DynaBean build() {
        return this.db;
    }
}
