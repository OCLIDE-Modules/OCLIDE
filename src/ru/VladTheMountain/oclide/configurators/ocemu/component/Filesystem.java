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
package ru.VladTheMountain.oclide.configurators.ocemu.component;

import ru.VladTheMountain.oclide.util.UUIDGenerator;

/**
 *
 * @author VladTheMountain
 */
public class Filesystem extends OCEmuComponent {

    /**
     * Creates a new OCEmu machine component
     *
     * @param i1 unknown
     * @param directory the directory, where the files will be stored
     * @param label the name of the filesystem
     * @param readOnly if it is read-only
     * @param speed unknowns
     */
    public Filesystem(int i1, String directory, String label, boolean readOnly, int speed) {
        super(2, UUIDGenerator.create(), String.valueOf(i1), directory, label, String.valueOf(readOnly));
    }

    /**
     * Only if the first parameter is {@code null}
     *
     * @param i1 i1
     * @param s1 s1
     * @param b1 b1
     */
    /*public Filesystem(String i1, String s1, boolean b1, int speed) {
        super(2, UUIDGenerator.create(), i1, s1, String.valueOf(b1));
    }*/
}
