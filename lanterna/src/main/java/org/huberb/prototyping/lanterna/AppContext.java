/*
 * Copyright 2021 berni3.
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
package org.huberb.prototyping.lanterna;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author berni3
 */
public class AppContext implements Serializable {

    String appName;
    final Map<String, Object> m;

    public AppContext(String appName) {
        this.appName = appName;
        this.m = new HashMap<>();
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppName() {
        return appName;
    }

    public Map<String, Object> getM() {
        return m;
    }

    public void setProperty(String key, Object value) {
        m.put(key, value);
    }

}
