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

import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.huberb.prototyping.lanterna.examples.dialogs.ItemLabelWrappings.IItemLabel;

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

    public void storeResult(String k, String v) {
        this.setProperty(k, v);
    }

    public void storeResult(String k, MessageDialogButton v) {
        this.setProperty(k, v);
    }

    public void storeResult(String k, IItemLabel v) {
        this.setProperty(k, v);
    }

    public void storeResult(String k, List<? extends IItemLabel> v) {
        this.setProperty(k, v);
    }

    public void storeResult(String k, File v) {
        this.setProperty(k, v);
    }

}
