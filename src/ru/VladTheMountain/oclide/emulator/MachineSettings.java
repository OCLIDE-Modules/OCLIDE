/*
 * The MIT License
 *
 * Copyright 2020 Vladislav Gorskii.
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
package ru.VladTheMountain.oclide.emulator;

import java.awt.Dimension;

/**
 *
 * @author VladislavTheMountain
 */
public class MachineSettings {

    //PRESETS
    protected static int TIER_1_DEFAULTS = 10;
    protected static int TIER_2_DEFAULTS = 20;
    protected static int TIER_3_DEFAULTS = 30;

    //PER-COMPONENT SPECIFICATION
    //CPU
    protected static int CPU_TIER_1 = 1;
    protected static int CPU_TIER_2 = 2;
    protected static int CPU_TIER_3 = 3;

    //Class variables
    private int sWidth, sHeight;

    public MachineSettings(int preset) {

    }

    public MachineSettings(int wid, int hei) {
        this.sWidth = wid;
        this.sHeight = hei;
    }

    public MachineSettings(int caseType, String eeprom, int cpu, int gpu, int mem1, int mem2, int hdd1, int hdd2) {
        
    }

    public Dimension getScreenSize() {
        return new Dimension(sWidth, sHeight);
    }
}
