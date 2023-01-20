package org.huberb.app;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void testHelloWorld() {
        App instance = new App();
        assertEquals("Hello World!", instance.greeting());
    }

    @Test
    public void testAppMain() throws IOException {
        PrintStream systemOutOrig = System.out;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); PrintStream systeOutRedirected = new PrintStream(baos)) {
            System.setOut(systeOutRedirected);

            App.main(new String[0]);
            systeOutRedirected.flush();

            String s = baos.toString();
            assertEquals("Hello World!", s.strip());
        } finally {
            System.setOut(systemOutOrig);
        }
    }
}
