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
package ru.VladTheMountain.oclide.configurators.ocemu;

import javax.swing.JOptionPane;
import li.cil.repack.org.luaj.vm2.lib.jse.JsePlatform;
import ru.VladTheMountain.oclide.configurators.ocemu.component.Computer;
import ru.VladTheMountain.oclide.configurators.ocemu.component.EEPROM;
import ru.VladTheMountain.oclide.configurators.ocemu.component.Filesystem;
import ru.VladTheMountain.oclide.configurators.ocemu.component.GPU;
import ru.VladTheMountain.oclide.configurators.ocemu.component.Internet;
import ru.VladTheMountain.oclide.configurators.ocemu.component.Keyboard;
import ru.VladTheMountain.oclide.configurators.ocemu.component.Modem;
import ru.VladTheMountain.oclide.configurators.ocemu.component.OCEmu;
import ru.VladTheMountain.oclide.configurators.ocemu.component.OCEmuComponent;
import ru.VladTheMountain.oclide.configurators.ocemu.component.Screen;

/**
 *
 * @author VladTheMountain
 */
public class OCEmuLauncher {

    //Config itself
    private ConfigMaker currentConfiguration;
    //Config defaults
    public final static OCEmuComponent[] DEFAULT = {
        new GPU(0, 160, 50, 3),
        new Modem(1, false),
        new EEPROM(9, "lua/bios.lua"),
        new Filesystem(7, "tmpfs", ".machine/tmpfs", true, 0),
        new Filesystem(-1, "tmpfs", "tmpfs", false, 0),
        new Filesystem(5, "nil", "nil", false, 0),
        new Internet(),
        new Computer(),
        new OCEmu(),
        new Screen("nil", 80, 25, 3),
        new Keyboard()
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
