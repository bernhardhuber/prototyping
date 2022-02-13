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

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.huberb.prototyping.transhuelle.Supports.SetBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class SetBuilderTest {

    @Test
    public void given_a_Set_then_verify_its_members() {
        final SetBuilder<String> instance = new SetBuilder<>();
        instance.v("S1")
                .vs("S2", "S3")
                .v(Arrays.asList("S4", "S5"));
        final Set<String> s = instance.build();
        assertEquals(5, s.size());

        //---
        for (String elem : Arrays.asList("S1", "S2", "S3", "S4", "S5")) {
            assertTrue(s.contains(elem),
                    String.format("expecting %s in set %s", elem, s)
            );
        }

        //---
        final List<String> l = s.stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        assertEquals(5, l.size());
        assertEquals("[S1, S2, S3, S4, S5]", l.toString());

    }
}
