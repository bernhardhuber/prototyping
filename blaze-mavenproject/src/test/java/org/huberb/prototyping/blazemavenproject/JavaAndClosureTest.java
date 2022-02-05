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
package org.huberb.prototyping.blazemavenproject;

import groovy.lang.Closure;
import java.util.function.Function;
import java.util.function.Supplier;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class JavaAndClosureTest {

    @Test
    public void given_a_Closure_then_call_it() {
        final CloserTarget closerTarget = new CloserTarget();
        final Object clOwner = closerTarget;
        final Object clThisObject = closerTarget;
        final Closure<Integer> cl = new Closure<Integer>(clOwner, clThisObject) {

            public int doCall() {
                return 1;
            }

            public int doCall(String s) {
                return 2;
            }

            public int doCall(Integer i) {
                return 3;
            }
        };
        {
            Integer result = cl.call();
            assertEquals(1, result.intValue());
        }
        {
            Integer result = cl.call("1");
            assertEquals(2, result.intValue());
        }
        {
            Integer result = cl.call(1);
            assertEquals(3, result.intValue());
        }
    }

    @Test
    public void given_a_Function_and_wrapped_in_aClosure_then_call_Closure() {
        final Object clOwner = null;
        final Object clThisObject = null;
        Supplier<Integer> supplierInteger = () -> {
            return 1;
        };
        Function<String, Integer> functionStringInteger = (v) -> {
            return 2;
        };
        Function<Integer, Integer> functionIntegerInteger = (v) -> {
            return 3;
        };
        final Closure<Integer> cl = new Closure<Integer>(clOwner, clThisObject) {

            public int doCall() {
                return supplierInteger.get();
            }

            public int doCall(String s) {
                return functionStringInteger.apply(s);
            }

            public int doCall(Integer i) {
                return functionIntegerInteger.apply(i);
            }
        };
        {
            Integer result = cl.call();
            assertEquals(1, result.intValue());
        }
        {
            Integer result = cl.call("1");
            assertEquals(2, result.intValue());
        }
        {
            Integer result = cl.call(1);
            assertEquals(3, result.intValue());
        }
    }

    @Test
    public void given_a_Function_then_wrap_it_in_a_Closure_and_create_and_call_it() {
        final Function<Function<String, Integer>, Closure<Integer>> functionClosureFactory = new Function<>() {
            @Override
            public Closure<Integer> apply(Function<String, Integer> function) {
                return new Closure<Integer>(null, null) {
                    public Integer doCall(String s) {
                        return function.apply(s);
                    }
                };
            }
        };
        Function<String, Integer> functionStringInteger = (v) -> {
            return 2;
        };
        assertEquals(2, functionClosureFactory.apply(functionStringInteger).call("1"));
    }

    @Test
    public void give_a_Closure_then_set_and_get_a_predefined_property() {
        final CloserTarget closerTarget = new CloserTarget();
        final Object clOwner = closerTarget;
        final Object clThisObject = closerTarget;
        final Closure<Integer> cl = new Closure<Integer>(clOwner, clThisObject) {
        };
        cl.setProperty("someProperty", "newValueX2");
        assertEquals("newValueX2", cl.getProperty("someProperty"));

    }

    static class CloserTarget {

        String someProperty = "someValue";
    }
}
