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
package ru.VladTheMountain.oclide.api.ocemu;

import ru.VladTheMountain.oclide.api.ocemu.OCEmuConfig;
import javax.swing.JOptionPane;
import li.cil.repack.org.luaj.vm2.lib.jse.JsePlatform;
import ru.VladTheMountain.oclide.api.ocemu.component.ComponentComputer;
import ru.VladTheMountain.oclide.api.ocemu.component.ComponentEEPROM;
import ru.VladTheMountain.oclide.api.ocemu.component.ComponentFilesystem;
import ru.VladTheMountain.oclide.api.ocemu.component.ComponentGPU;
import ru.VladTheMountain.oclide.api.ocemu.component.ComponentInternet;
import ru.VladTheMountain.oclide.api.ocemu.component.ComponentKeyboard;
import ru.VladTheMountain.oclide.api.ocemu.component.ComponentModem;
import ru.VladTheMountain.oclide.api.ocemu.component.ComponentOCEmu;
import ru.VladTheMountain.oclide.api.ocemu.component.OCEmuComponent;
import ru.VladTheMountain.oclide.api.ocemu.component.ComponentScreen;

/**
 *
 * @author VladTheMountain
 */
public class OCEmuLauncher {

    //Config itself
    private OCEmuConfig currentConfiguration;
    //Config defaults
    public final static OCEmuComponent[] DEFAULT = {
        new ComponentGPU(0, 160, 50, 3),
        new ComponentModem(1, false),
        new ComponentEEPROM(9, "lua/bios.lua"),
        new ComponentFilesystem(7, "tmpfs", ".machine/tmpfs", true, 0),
        new ComponentFilesystem(-1, "tmpfs", "tmpfs", false, 0),
        new ComponentFilesystem(5, "nil", "nil", false, 0),
        new ComponentInternet(),
        new ComponentComputer(),
        new ComponentOCEmu(),
        new ComponentScreen("nil", 80, 25, 3),
        new ComponentKeyboard()
    };

    /**
     * Launcher constructor. Starts OCEmu with default config
     */
    /*public OCEmuLauncher() {

    }*/
    /**
     * Launcher constructor. Starts OCEmu with {@link OCEmuComponent} as a
     * source for config
     *
     * @param configuration Array for component config
     */
    /*public OCEmuLauncher(OCEmuComponent[] configuration) {
        
    }*/
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

    public static String runOCEmu() {
        return JsePlatform.standardGlobals().loadfile("OCEmu/boot.lua").call().tojstring();
    }
}
