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

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Superclass for all components used in OCEmu
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

    /**
     * Creates a new OCEmu component of defined type, address and options. It is
     * recommended to override the constructor in a sub-class, then call it from
     * this class.
     *
     * @param componentType the type of the component, see
     * {@link io.VladTheMountain.oclide.configurator.ocemu.ConfigMaker} to see
     * which id corresponds to which component
     * @param componentAddress the UUID of the component in OCEmu's machine
     * @param options additional options/parameters that can be passed with the
     * component (maximum of 5)
     */
    public OCEmuComponent(int componentType, String componentAddress, String... options) {
        if (componentAddress == null) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Attempted to create an OCEmuComponent with no address. The result is unpredictable.");
        }
        this.type = componentType;
        this.address = componentAddress;
        this.opts = new String[4];
        System.arraycopy(options, 0, opts, 0, options.length);
    }

    /**
     * Returns component type
     *
     * @return int, which stores the component's type
     */
    public int getComponentType() {
        return type;
    }

    /**
     * Returns component address
     *
     * @return UUID as a String
     */
    public String getComponentAddress() {
        return address;
    }

    /**
     * Returns a specific parameter of the component
     *
     * @param pos position of the parameter (beginning with 0)
     * @return value of the parameter
     */
    public String getOptionAt(int pos) {
        return opts[pos];
    }

    @Override
    public String toString() {
        return "OCEmuComponent {" + type + "," + address + "," + Arrays.toString(opts) + "}";
    }
}
