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
import com.fizzed.blaze.Config;
import com.fizzed.blaze.Contexts;
import groovy.lang.Closure;
import groovy.sql.Sql;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author berni3
 */
public class BlazeTasks3H21 {

    Map<String, Object> jdbcConnection() {
        Config config = Contexts.config();

        // define h2 database access
        final String driver = config
                .value("jdbcConnection.driver")
                .orElse("org.h2.Driver");

        //final String url = 'jdbc:h2:tcp://localhost:9093/gemplzstr'
        final String url = config
                .value("jdbcConnection.url")
                .orElse("jdbc:h2:mem:blaze_tasks3_h2_1");
        final String user = config
                .value("jdbcConnection.user")
                .orElse("sa1");
        final String password = config
                .value("jdbcConnection.password")
                .orElse("sa1");

        final Map<String, Object> jdbcConnection = new HashMap();
        jdbcConnection.put("url", url);
        jdbcConnection.put("user", user);
        jdbcConnection.put("password", password);
        jdbcConnection.put("driver", driver);
        Contexts.logger().info("Jdbc connection info " + jdbcConnection);
        return jdbcConnection;
    }

    /**
     * Use groovy-sql to access H2 database.
     */
    public void createH2TestTable() throws SQLException, ClassNotFoundException {

        final String sqls = "\n"
                + "        DROP TABLE IF EXISTS TEST;\n"
                + "        CREATE TABLE TEST(ID INT PRIMARY KEY, NAME VARCHAR(255));\n"
                + "\n"
                + "        INSERT INTO TEST VALUES(1, 'Hello');\n"
                + "        INSERT INTO TEST VALUES(2, 'World');\n"
                + "\n"
                + "        SELECT * FROM TEST ORDER BY ID;\n"
                + "\n"
                + "        UPDATE TEST SET NAME='Hi' WHERE ID=1;\n"
                + "        DELETE FROM TEST WHERE ID=2;\n"
                + "\n"
                + "        SELECT COUNT(*) FROM TEST;\n"
                + "";

        Contexts.logger().info("Process following sql statement " + sqls);

        /* 
            original groovy code:

            Sql.withInstance( jdbcConnection ) { sql ->
                sql.execute "DROP TABLE IF EXISTS TEST"
                sql.execute "CREATE TABLE TEST(ID INT PRIMARY KEY, NAME VARCHAR(255))"
                sql.execute "INSERT INTO TEST VALUES(1, 'Hello')"
                sql.execute "INSERT INTO TEST VALUES(2, 'World')"
        
                sql.eachRow( "SELECT * FROM TEST ORDER BY ID" ) { row -> 
                    System.out.println( "TEST1: row ${row}" );
                }
        
                sql.execute "UPDATE TEST SET NAME='Hi' WHERE ID=1"
                sql.execute "DELETE FROM TEST WHERE ID=2"
                sql.eachRow( "SELECT COUNT(*) FROM TEST" ) { row -> 
                    println "TEST2: row ${row}"
                }
            }
         */
        final Map<String, Object> jdbcConnection = jdbcConnection();
        final Sql sql = Sql.newInstance(jdbcConnection);
        sql.execute("DROP TABLE IF EXISTS TEST");
        sql.execute("CREATE TABLE TEST(ID INT PRIMARY KEY, NAME VARCHAR(255))");
        sql.execute("INSERT INTO TEST VALUES(1, 'Hello')");
        sql.execute("INSERT INTO TEST VALUES(2, 'World')");

        //sql.eachRow( "SELECT * FROM TEST ORDER BY ID" ) { row -> System.out.println( "TEST1: row ${row}" ); }
        sql.eachRow("SELECT * FROM TEST ORDER BY ID", new Closure(null) {
            void doCall(ResultSet row) {
                System.out.println("TEST1: row " + row);
            }
        });
        sql.execute("UPDATE TEST SET NAME='Hi' WHERE ID=1");
        sql.execute("DELETE FROM TEST WHERE ID=2");
        //sql.eachRow( "SELECT COUNT(*) FROM TEST" ) { row ->  println "TEST2: row ${row}" }
        sql.eachRow("SELECT COUNT(*) FROM TEST", new Closure(null) {
            void doCall(ResultSet row) {
                System.out.println("TEST2: row " + row);
            }
        });

    }

}
