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
package ru.VladTheMountain.oclide.emulator.ocemu.component;

/**
 *
 * @author VladTheMountain
 */
public class OCEmuComponent {

    //FIELD 1
    private int type;
    //FIELD 2
    private String address;
    //the rest
    private String[] opts;

    public OCEmuComponent(int componentType, String componentAddress, String... options) {
        this.type = componentType;
        this.address = componentAddress;
        this.opts = new String[4];
        System.arraycopy(options, 0, opts, 0, options.length);
    }

    public int getComponentType() {
        return type;
    }

    public String getComponentAddress() {
        return address;
    }

    public String getOptionAt(int pos) {
        return opts[pos];
    }
}