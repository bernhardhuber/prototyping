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
package org.huberb.prototyping.pkfkdb;

import org.huberb.prototyping.pkfkdb.PkFkDb.PkFkRecordTree;
import org.huberb.prototyping.pkfkdb.PkFkDb.TreeNodeWithChildren;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class PkFkRecordTreeTest {

    @Test
    public void testCreatePkFkTreeFor() {
        PkFkRecordTree instance = new PkFkRecordTree();
        String startTablename = "T_AN";
        TreeNodeWithChildren<String> result = instance.createPkFkTreeFor(startTablename);
        System.out.printf("%ntestCreatePkFkTreeFor visitNodes%n");
        instance.visit(result, (i, e) -> System.out.printf("testCreatePkFkTreeFor %d %s%n", i, e.data));
    }

    @Test
    public void testCreatePkFkTreeAll() {
        PkFkRecordTree instance = new PkFkRecordTree();
        TreeNodeWithChildren<String> result = instance.createPkFkTreeAll();
        System.out.printf("%ncreatePkFkTreeAll visitNodes%n");
        instance.visit(result, (i, e) -> System.out.printf("createPkFkTreeAll %d %s%n", i, e.data));
    }
}
