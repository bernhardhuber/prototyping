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
package org.huberb.prototyping.lanterna;

import java.util.concurrent.Callable;

/**
 *
 * @author berni3
 */
public class LanternaLauncher implements Callable<Integer> {

    public static void main(String[] args) throws Exception {
        final String className = args[0];
        final String[] argsRemaining;
        if (args.length == 1) {
            argsRemaining = new String[0];
        } else {
            final int argsRemainingLenght = args.length - 1;
            argsRemaining = new String[argsRemainingLenght];
            System.arraycopy(args, 1, argsRemaining, 0, argsRemainingLenght);
        }
        int rc = launchWithClassName(className, argsRemaining);
        System.exit(rc);
    }

    public static int launchWithClassName(String className, String[] args) throws Exception {

        final Class<? extends LanternaDialogTemplate> clazz
                = (Class<? extends LanternaDialogTemplate>) LanternaLauncher.class.getClassLoader().loadClass(className);
        int rc = launchWithClass(clazz, args);
        return rc;
    }

    public static int launchWithClass(Class<? extends LanternaDialogTemplate> clazz, String[] args) throws Exception {
        final LanternaLauncher lanternaLauncher = new LanternaLauncher();
        lanternaLauncher.clazz = clazz;
        int rc = lanternaLauncher.call();
        return rc;
    }

    Class<? extends LanternaDialogTemplate> clazz;

    LanternaLauncher() {
    }

    @Override
    public Integer call() throws Exception {
        final LanternaDialogTemplate laternaDialogTemplate = clazz.getDeclaredConstructor().newInstance();
        laternaDialogTemplate.launch();
        return 0;
    }

}
