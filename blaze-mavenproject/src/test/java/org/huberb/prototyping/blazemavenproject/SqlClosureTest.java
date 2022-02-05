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
import groovy.sql.Sql;
import java.sql.SQLException;
import javax.sql.DataSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class SqlClosureTest {

    @Test
    public void x1() throws SQLException {
        Closure<Integer> sqlClosure = new Closure(null) {
            Integer doCall(Sql sql) {
                return 0;
            }
        };
        Sql sql = new Sql((DataSource) null);
        assertEquals(0, sqlClosure.call(sql));

//    Sql.withInstance( jdbcConnection ) { sql ->
//        sql.execute "DROP TABLE IF EXISTS TEST"
//        sql.execute "CREATE TABLE TEST(ID INT PRIMARY KEY, NAME VARCHAR(255))"
//        sql.execute "INSERT INTO TEST VALUES(1, 'Hello')"
//        sql.execute "INSERT INTO TEST VALUES(2, 'World')"
//
//        sql.eachRow( "SELECT * FROM TEST ORDER BY ID" ) { row -> 
//            System.out.println( "TEST1: row ${row}" );
//        }
//
//        sql.execute "UPDATE TEST SET NAME='Hi' WHERE ID=1"
//        sql.execute "DELETE FROM TEST WHERE ID=2"
//        sql.eachRow( "SELECT COUNT(*) FROM TEST" ) { row -> 
//            println "TEST2: row ${row}"
//        }
//    }
    }
}
