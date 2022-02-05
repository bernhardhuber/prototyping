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

import com.fizzed.blaze.core.DefaultContext;

/**
 *
 * @author berni3
 */
public class blaze_tasks_1 {

    public int main() {
        System.out.println("main task");
        return 0;
    }

    public void echoDefaultContext() {
        final DefaultContext defaultContext = new DefaultContext();

        final String defaultContextFormatted = String.format("defaultContext%n"
                + "baseDir: %s%n"
                + "config: %s%n"
                + "toString: %s%n"
                + "scriptFile: %s%n",
                defaultContext.baseDir(),
                defaultContext.config(),
                defaultContext.toString(),
                defaultContext.scriptFile());

        defaultContext.logger().info(defaultContextFormatted);
    }

    public void helloWorld() {
        final DefaultContext defaultContext = new DefaultContext();
        defaultContext.logger().info("Hello world");
    }
}
