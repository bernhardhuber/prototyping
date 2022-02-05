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

// load h2
/* load via blaze_tasks_3_h2_1.conf:
blaze.dependencies [
    "com.h2database:h2:1.4.200"
]
//@Grab('com.h2database:h2:1.4.200')
//@GrabConfig(systemClassLoader=true)
*/

import groovy.sql.Sql
import org.h2.tools.Csv

/**
 * Use groovy-sql to access H2 database.
 */
def createH2TestTable() {
    // define h2 database access
    final String driver = 'org.h2.Driver'

    //final String url = 'jdbc:h2:tcp://localhost:9093/gemplzstr'
    final String url = 'jdbc:h2:mem:blaze_tasks3_h2_1'
    final String user = 'sa1'
    final String password = 'sa1'
    final Map<String, String> jdbcConnection = [ 'url':url, 'user': user, 'password': password, 'driver': driver ]

    def sqls = """
        DROP TABLE IF EXISTS TEST;
        CREATE TABLE TEST(ID INT PRIMARY KEY, NAME VARCHAR(255));

        INSERT INTO TEST VALUES(1, 'Hello');
        INSERT INTO TEST VALUES(2, 'World');

        SELECT * FROM TEST ORDER BY ID;

        UPDATE TEST SET NAME='Hi' WHERE ID=1;
        DELETE FROM TEST WHERE ID=2;

        SELECT COUNT(*) FROM TEST;
    """
    println "Process following sql statement ${sqls}"

    Sql.withInstance( jdbcConnection ) { sql ->
        sql.execute "DROP TABLE IF EXISTS TEST"
        sql.execute "CREATE TABLE TEST(ID INT PRIMARY KEY, NAME VARCHAR(255))"
        sql.execute "INSERT INTO TEST VALUES(1, 'Hello')"
        sql.execute "INSERT INTO TEST VALUES(2, 'World')"

        sql.eachRow( "SELECT * FROM TEST ORDER BY ID" ) { row -> 
            println "TEST1: row ${row}"
        }

        sql.execute "UPDATE TEST SET NAME='Hi' WHERE ID=1"
        sql.execute "DELETE FROM TEST WHERE ID=2"
        sql.eachRow( "SELECT COUNT(*) FROM TEST" ) { row -> 
            println "TEST2: row ${row}"
        }

    }

}

/**
 * Just for checking groovy way of processing a list.
 */
def printListOfSomething() {
  def l = [ "A", "B", "C" ]
  for (String s in l) {
    println s
  }
}
