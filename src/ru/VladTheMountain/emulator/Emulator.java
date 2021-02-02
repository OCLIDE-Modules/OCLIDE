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
package ru.VladTheMountain.emulator;

import java.io.File;
import totoro.ocelot.brain.Ocelot;
import totoro.ocelot.brain.nbt.NBTTagCompound;
import totoro.ocelot.brain.workspace.Workspace;

/**
 *
 * @author VladTheMountain
 */
public class Emulator {

    private Ocelot emulator;
    private Workspace world;
    private NBTTagCompound emulation;

    /**
     * Launch emulator with a premade workspace
     *
     * @param workspace Workspace to deploy
     */
    public Emulator(Workspace workspace) {
        world = workspace;
    }

    /**
     * Launch emulator with a premade workspace
     *
     * @param workspaceDir File to get workspace from
     */
    public Emulator(File workspaceDir) {
        world = new Workspace(workspaceDir.toPath());
    }

    /**
     * What to do when emulation starting
     */
    private void start() {
        Ocelot.initialize();
        //

        // DO NOT MODIFY //
        while (true) {
            if (loop() != 1) {
                break;
            } else {
                world.update();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    System.out.println("[" + Emulator.class.getName() + "]" + ex.getMessage());
                }
            }
        }
        stop();
    }

    /**
     * What to perform while working
     *
     * @return status of performed operation
     */
    private int loop() {
        return 1;
    }

    /**
     * What to do when stopping the emulation
     */
    private void stop() {

        // DO NOT MODIFY //
        world.save(emulation);
    }
}
