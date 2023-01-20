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

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.AbstractTextGUI;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.TextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * A simple lanterna application template.
 * <p>
 * A concrete application shall provide consumers for implementing a concrete
 * lanterna application.
 *
 * @author berni3
 */
public class ConsumerLanternaApplicationTemplate {

    /**
     * Consumer of lanterna resources, like
     * {@link Terminal}, {@link Screen}, {@link MultiWindowTextGUI}.
     *
     * @param <T>
     */
    @FunctionalInterface
    public static interface LanternaConsumer<T> {

        public void accept(T t) throws IOException;

        default LanternaConsumer<T> andThen(LanternaConsumer<? super T> after) throws IOException {
            Objects.requireNonNull(after);
            return (T t) -> {
                accept(t);
                after.accept(t);
            };
        }

        default LanternaConsumer<T> andIfThen(Supplier<Boolean> booleanSupp, LanternaConsumer<? super T> thenAfter) throws IOException {
            Objects.requireNonNull(booleanSupp);
            Objects.requireNonNull(thenAfter);
            return (T t) -> {
                accept(t);
                final Boolean conditionValue = booleanSupp.get();
                if (conditionValue) {
                    thenAfter.accept(t);
                }
            };
        }

        default LanternaConsumer<T> andIfThenElse(Supplier<Boolean> booleanSupp,
                LanternaConsumer<? super T> thenAfter,
                LanternaConsumer<? super T> elseAfter) throws IOException {
            Objects.requireNonNull(booleanSupp);
            Objects.requireNonNull(thenAfter);
            Objects.requireNonNull(elseAfter);
            return (T t) -> {
                accept(t);
                final Boolean conditionValue = booleanSupp.get();
                if (conditionValue) {
                    thenAfter.accept(t);
                } else if (!conditionValue) {
                    elseAfter.accept(t);
                }
            };
        }
    }

    /**
     * Simple factory method creating a lanterna terminal.
     *
     * @return instance of {@link Terminal}
     *
     * @throws IOException if creating of lanterna resources fails
     *
     * @see Terminal
     * @see DefaultTerminalFactory
     */
    public Terminal createTerminal() throws IOException {
        final DefaultTerminalFactory dtf = new DefaultTerminalFactory();
        final Terminal terminal = dtf.createTerminal();
        return terminal;
    }

    /**
     * Simple factory method creating a lanterna screen.
     *
     * @param terminal the lanterna terminal for the created lanterna screen
     *
     * @return instance of {@link Screen}
     *
     * @throws IOException if creating of lanterna resources fails
     *
     * @see Terminal
     * @see Screen
     * @see TerminalScreen
     */
    public Screen createScreen(Terminal terminal) throws IOException {
        final Screen screen = new TerminalScreen(terminal);
        return screen;
    }

    /**
     * Simple factory method creating a lanterna text gui.
     *
     * @param screen the lanterna screen for the created lanterna text gui
     * @return instance of {@link MultiWindowTextGUI}
     * @see AbstractTextGUI
     * @see TextGUI
     */
    public MultiWindowTextGUI createMultiWindowTextGUI(Screen screen) {
        final TextColor.ANSI backgroundColor = TextColor.ANSI.BLUE;
        final MultiWindowTextGUI textGUI = new MultiWindowTextGUI(screen,
                new DefaultWindowManager(),
                new EmptySpace(backgroundColor));
        return textGUI;
    }

    public void withTerminal(Terminal terminal,
            LanternaConsumer<Terminal> terminalConsumer) throws IOException {
        try (terminal) {
            terminalConsumer.accept(terminal);
        }
    }

    public void withScreen(Screen screen,
            LanternaConsumer<Screen> screenConsumer) throws IOException {
        try (screen) {
            screenConsumer.accept(screen);
        }
    }

    public void withCreatedAndStartedScreen(Terminal terminal,
            LanternaConsumer<Screen> screenConsumer) throws IOException {
        try (terminal) {
            try (final Screen screen = createScreen(terminal)) {
                screen.startScreen();
                // consume the created, and started screeen
                screenConsumer.accept(screen);
            }
        }
    }

    /**
     * Consume a created {@link MultiWindowTextGUI}.
     *
     * @param terminal terminal used to createMultiWindowTextGUI, and start a
     * {@link Screen}, and the created {@link  MultiWindowTextGUI}.
     *
     * @param multiWindowTextGUIConsumer a consumer accepting the created
     * {@link  MultiWindowTextGUI}.
     *
     * @throws IOException if creating of lanterna resources fails
     *
     * @see Terminal
     * @see Screen
     * @see MultiWindowTextGUI
     */
    public void withCreatedMultiWindowTextGUI(Terminal terminal,
            LanternaConsumer<MultiWindowTextGUI> multiWindowTextGUIConsumer) throws IOException {
        try (terminal) {
            try (Screen screen = createScreen(terminal)) {
                screen.startScreen();
                final MultiWindowTextGUI multiWindowTextGUI = createMultiWindowTextGUI(screen);
                // consume created multiWindowTextGUI
                multiWindowTextGUIConsumer.accept(multiWindowTextGUI);
            }
        }
    }

}
