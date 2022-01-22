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
package org.huberb.prototyping.lanterna.examples1;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author berni3
 */
public class AllDialogExample {

    public static void main(String[] args) throws Exception {
        final String[] theArgs = args;

        //ActionListDialogExample.main(args1);
        invokeMain(ActionListDialogExample.class, theArgs);

        DirectoryDialogExample.main(theArgs);
        FileDialogExample.main(theArgs);
        ListSelectDialogExample.main(theArgs);
        MessageDialogButtonExample.main(theArgs);
        TextInputDialogExample.main(theArgs);
        //---
        CheckListDialogExample.main(theArgs);
        ComboBoxDialogExample.main(theArgs);
        MenuListDialogExample.main(theArgs);
        RadioListDialogExample.main(theArgs);
        invokeMain(TextBoxDialogExample.class, theArgs);
    }

    static void invokeMain(Class<?> mainClass, String[] mainArgs) throws
            NoSuchMethodException,
            IllegalAccessException,
            IllegalArgumentException,
            InvocationTargetException {
        final Class<?> clazz = mainClass;
        final Method method = clazz.getMethod("main", String[].class);

        final Object[] args = new Object[1];
        //args[0] = new String[]{"1", "2"};
        args[0] = mainArgs;
        method.invoke(null, args);
    }
}
