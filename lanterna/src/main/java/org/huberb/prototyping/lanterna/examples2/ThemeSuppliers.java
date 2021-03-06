/*
 * Copyright 2021 pi.
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
package org.huberb.prototyping.lanterna.examples2;

import java.util.Random;

/**
 *
 * @author pi
 */
class ThemeSuppliers {

    private final Random random = new Random();

    public enum DefaultThemes {
        defaultTheme("default"),
        bigsnakeTheme("bigsnake"),
        businessmachineTheme("businessmachine"),
        conquerorTheme("conqueror"),
        defrostTheme("defrost"),
        blasterTheme("blaster");

        private final String themeName;

        DefaultThemes(String themeName) {
            this.themeName = themeName;
        }

        public String themeName() {
            return themeName;
        }
    }

    public String retrieveRandomTheme() {
        DefaultThemes[] values = DefaultThemes.values();
        final int rValue = random.nextInt(values.length);
        return values[rValue].themeName();
    }

    public String retrieveTheme(DefaultThemes dt) {
        return dt.themeName;
    }

}
