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
package ru.VladTheMountain.oclide.validator;

/**
 * Validator is an OCIDE built-in code testing tool. It helps the developers to
 * check if their code is runnable on certain system configurations. Refer to
 * <a href="https://github.com/Vladg24YT/OpenComputers-IDE/wiki/How-to-use-Validator">How
 * to use Validator</a> article to get more info.
 *
 * @author VladTheMountain
 */
public class Validator {

    public final String ARCHITECTURE_CASE = "Computer Case";
    public final String ARCHITECTURE_SERVER = "Server";
    public final String ARCHITECTURE_MICROCONTROLLER = "Microcontroller";

    public Validator(String[] prefs) {
        switch (prefs[0]) {
            case ARCHITECTURE_CASE:
                break;
            case ARCHITECTURE_SERVER:
                break;
            case ARCHITECTURE_MICROCONTROLLER:
                break;
            default:
                break;
        }
    }

    public String run() {
        return null;
    }
}
