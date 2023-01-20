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
package org.huberb.prototyping.beandata.h2;

import org.h2.mvstore.MVMap;
import org.h2.mvstore.MVStore;

/**
 *
 * @author berni3
 */
public class MVStoreStarterMain {

    public static void main(String[] args) {
        String fileName = "mvstore-1";

        // open the store (in-memory if fileName is null)
        try ( MVStore s = MVStore.open(fileName)) {

            // create/get the map named "data"
            MVMap<String, String> map = s.openMap("data");


            // add and read some data
            map.put("A", "Hello World");
            System.out.println(map.get("A"));
        }
    }
}
