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
package ru.VladTheMountain.oclide.configurator.ocemu.component;

import ru.VladTheMountain.oclide.util.UUIDGenerator;

/**
 *
 * @author VladTheMountain
 */
public class Screen extends OCEmuComponent {

    public Screen(int i1, int i2, int i3, int i4) {
        super(8, UUIDGenerator.create(), String.valueOf(i1), String.valueOf(i2), String.valueOf(i3), String.valueOf(i4));
    }

    /**
     * Use only if the first param is null
     *
     * @param i1
     * @param i2
     * @param i3
     * @param i4
     */
    public Screen(String i1, int i2, int i3, int i4) {
        super(8, UUIDGenerator.create(), i1, String.valueOf(i2), String.valueOf(i3), String.valueOf(i4));
    }
}
