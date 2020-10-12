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
package ru.VladTheMountain.oclide.emulator.lang;

/**
 *
 * @author VladTheMountain
 */
public class Token {

    //Required variables per each token
    public int type;
    public int line;
    public int pos;
    public String name;

    //Preserved variables
    public static final int TYPE_IDENTIFIER = 1;
    public static final int TYPE_OPERATOR = 2;

    /**
     *
     * @param i2
     * @param i3
     * @param i4
     * @param s
     */
    public Token(int i2, int i3, int i4, String s) {
        this.type = i2;
        this.line = i3;
        this.pos = i4;
        this.name = s;
    }
}
