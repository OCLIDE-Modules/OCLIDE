/*
 * The MIT License
 *
 * Copyright 2021 Vladislav Gorskii.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package ru.VladTheMountain.oclide.configurator.ocemu;

import javax.swing.JOptionPane;
import ru.VladTheMountain.oclide.configurator.ocemu.component.OCEmuComponent;

/**
 *
 * @author VladTheMountain
 */
public class OCEmuLauncher {

    /**
     * Launcher constructor. Starts OCEmu with default config
     */
    public OCEmuLauncher() {

    }

    /**
     * Launcher constructor. Starts OCEmu with {@link OCEmuComponent} as a
     * source for config
     *
     * @param configuration Array for component config
     */
    public OCEmuLauncher(OCEmuComponent[] configuration) {

    }

    /**
     * Attempts to recreate the config file. The result of an attempt is
     * returned in {@link JOptionPane} message types.
     *
     * @return {@code JOptionPane.INFROMATION_MESSAGE} if the attempt succeeds,
     * {@code JOptionPane.ERROR_MESSAGE} if the attempt fails
     */
    public int reloadConfig() {

        return JOptionPane.ERROR_MESSAGE;
    }
}
