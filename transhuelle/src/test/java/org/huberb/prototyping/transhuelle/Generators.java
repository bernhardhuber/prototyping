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
package org.huberb.prototyping.transhuelle;

import groovy.json.JsonGenerator;
import groovy.json.JsonGenerator.Options;
import org.huberb.prototyping.transhuelle.TransHuelle.Data;
import org.yaml.snakeyaml.Yaml;

public class Generators {

    public void toYaml(Data d) {
        toYaml(d.toMap());
    }

    public void toYaml(Object object) {
        final Yaml yaml = new Yaml();
        final String doutYaml = yaml.dump(object);
        System.out.println(String.format("yaml:%n%s%n", doutYaml));
    }

    public void toJson(Data d) {
        toYaml(d.toMap());
    }

    public void toJson(Object object) {
        JsonGenerator jsonGenerator = new Options().build();
        final String doutJson = jsonGenerator.toJson(object);
        System.out.println(String.format("json:%n%s%n", doutJson));
    }

}
