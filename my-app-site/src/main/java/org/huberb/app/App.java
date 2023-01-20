package org.huberb.app;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        final App app = new App();
        final String message = app.greeting();
        System.out.println(message);
    }

    public String greeting() {
        final String greeting = "Hello World!";
        return greeting;
    }
}
