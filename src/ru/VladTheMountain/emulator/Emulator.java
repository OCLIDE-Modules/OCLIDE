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
import totoro.ocelot.brain.event.BeepEvent;
import totoro.ocelot.brain.event.BeepPatternEvent;
import totoro.ocelot.brain.event.Event;
import totoro.ocelot.brain.event.EventBus;
import totoro.ocelot.brain.event.FileSystemActivityEvent;
import totoro.ocelot.brain.event.MachineCrashEvent;
import totoro.ocelot.brain.event.RelayActivityEvent;
import totoro.ocelot.brain.event.TextBufferCopyEvent;
import totoro.ocelot.brain.event.TextBufferFillEvent;
import totoro.ocelot.brain.event.TextBufferSetBackgroundColorEvent;
import totoro.ocelot.brain.event.TextBufferSetColorDepthEvent;
import totoro.ocelot.brain.event.TextBufferSetEvent;
import totoro.ocelot.brain.event.TextBufferSetForegroundColorEvent;
import totoro.ocelot.brain.event.TextBufferSetPaletteColorEvent;
import totoro.ocelot.brain.event.TextBufferSetResolutionEvent;
import totoro.ocelot.brain.event.TextBufferSetViewportEvent;
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
        start();
    }

    /**
     * What to do when emulation starting
     */
    private void start() {
        emulator.initialize();
        //

        //Event handlers
        EventBus.listenTo(BeepEvent.class, (Event v1) -> {
            BeepEvent event = (BeepEvent) v1;
            return null;
        });
        EventBus.listenTo(BeepPatternEvent.class, (Event v1) -> {
            BeepPatternEvent event = (BeepPatternEvent) v1;
            return null;
        });
        EventBus.listenTo(FileSystemActivityEvent.class, (Event v1) -> {
            FileSystemActivityEvent event = (FileSystemActivityEvent) v1;
            return null;
        });
        EventBus.listenTo(MachineCrashEvent.class, (Event v1) -> {
            MachineCrashEvent event = (MachineCrashEvent) v1;
            return null;
        });
        EventBus.listenTo(RelayActivityEvent.class, (Event v1) -> {
            RelayActivityEvent event = (RelayActivityEvent) v1;
            return null;
        });
        EventBus.listenTo(TextBufferCopyEvent.class, (Event v1) -> {
            TextBufferCopyEvent event = (TextBufferCopyEvent) v1;
            return null;
        });
        EventBus.listenTo(TextBufferFillEvent.class, (Event v1) -> {
            TextBufferFillEvent event = (TextBufferFillEvent) v1;
            return null;
        });
        EventBus.listenTo(TextBufferSetBackgroundColorEvent.class, (Event v1) -> {
            TextBufferSetBackgroundColorEvent event = (TextBufferSetBackgroundColorEvent) v1;
            return null;
        });
        EventBus.listenTo(TextBufferSetColorDepthEvent.class, (Event v1) -> {
            TextBufferSetColorDepthEvent event = (TextBufferSetColorDepthEvent) v1;
            return null;
        });
        EventBus.listenTo(TextBufferSetEvent.class, (Event v1) -> {
            TextBufferSetEvent event = (TextBufferSetEvent) v1;
            return null;
        });
        EventBus.listenTo(TextBufferSetForegroundColorEvent.class, (Event v1) -> {
            TextBufferSetForegroundColorEvent event = (TextBufferSetForegroundColorEvent) v1;
            return null;
        });
        EventBus.listenTo(TextBufferSetPaletteColorEvent.class, (Event v1) -> {
            TextBufferSetPaletteColorEvent event = (TextBufferSetPaletteColorEvent) v1;
            return null;
        });
        EventBus.listenTo(TextBufferSetResolutionEvent.class, (Event v1) -> {
            TextBufferSetResolutionEvent event = (TextBufferSetResolutionEvent) v1;
            return null;
        });
        EventBus.listenTo(TextBufferSetViewportEvent.class, (Event v1) -> {
            TextBufferSetViewportEvent event = (TextBufferSetViewportEvent) v1;
            return null;
        });
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

    public void exit() {
        stop();
        emulator.shutdown();
    }
}
