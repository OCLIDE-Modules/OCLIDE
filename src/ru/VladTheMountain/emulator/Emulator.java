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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.util.logging.Level;
import javax.swing.JComponent;
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
public class Emulator extends JComponent {

    private static final long serialVersionUID = 1L;

    //JComponent
    private Color back = Color.BLACK;
    private Color fore = Color.WHITE;
    private final int CHAR_WIDTH = 9;
    private final int CHAR_HEIGHT = 22;
    private final Font font = Font.getFont("Monospaced Plain 16");
    //Ocelot
    private Workspace world;
    private NBTTagCompound emulation;
    //Custom
    private EmuLogger logger;

    //Emulator-specific
    private Color[] colors = { //Data was taken from https://minecraft-ru.gamepedia.com/OpenComputers/Colors_API
        new Color(255, 255, 255), //White
        new Color(255, 127, 0), //Orange
        new Color(255, 0, 255), //Magenta
        new Color(0, 127, 127), //Lightblue
        new Color(255, 255, 0), //Yellow
        new Color(0, 255, 0),//Lime,
        new Color(255, 127, 255), //Pink
        new Color(85, 85, 85), //Gray
        new Color(170, 170, 170), //Silver
        new Color(0, 255, 255), //Cyan
        new Color(127, 0, 127), //Purple
        new Color(0, 0, 127), //Blue
        new Color(127, 0, 0), //Brown
        new Color(0, 127, 0), //Green
        new Color(255, 0, 0), //Red
        new Color(0, 0, 0) //Black
    };

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
    public void start() {
        Ocelot.initialize();
        //

        //Event handlers
        EventBus.listenTo(BeepEvent.class, (Event v1) -> {
            BeepEvent event = (BeepEvent) v1;
            logger.log(Level.INFO, "Computer beeped with " + event.frequency() + " Hz for " + event.duration());
            return null;
        });
        EventBus.listenTo(BeepPatternEvent.class, (Event v1) -> {
            BeepPatternEvent event = (BeepPatternEvent) v1;
            logger.log(Level.INFO, "Computer beeped with pattern " + event.pattern());
            return null;
        });
        EventBus.listenTo(FileSystemActivityEvent.class, (Event v1) -> {
            FileSystemActivityEvent event = (FileSystemActivityEvent) v1;
            logger.log(Level.INFO, "Filesystem actvity at " + event.address());
            return null;
        });
        EventBus.listenTo(MachineCrashEvent.class, (Event v1) -> {
            MachineCrashEvent event = (MachineCrashEvent) v1;
            logger.log(Level.WARNING, "Machine crashed. Info:\n" + event.message());
            return null;
        });
        EventBus.listenTo(RelayActivityEvent.class, (Event v1) -> {
            RelayActivityEvent event = (RelayActivityEvent) v1;
            logger.log(Level.INFO, "Relay activity at " + event.relay().toString());
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
        Ocelot.shutdown();
    }

    /**
     * GPU-related event handling
     *
     * @param gr
     */
    @Override
    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        EventBus.listenTo(TextBufferCopyEvent.class, (Event v1) -> {
            TextBufferCopyEvent event = (TextBufferCopyEvent) v1;
            logger.log(Level.INFO, "GPU: Copied area " + event.width() + "x" + event.height() + "at" + event.x() + ":" + event.y() + " to " + (event.x() + event.horizontalTranslation()) + ":" + (event.y() + event.verticalTranslation()));
            gr.copyArea(event.x(), event.y(), event.width(), event.height(), event.x() + event.horizontalTranslation(), event.y() + event.verticalTranslation());
            return null;
        });
        EventBus.listenTo(TextBufferFillEvent.class, (Event v1) -> {
            TextBufferFillEvent event = (TextBufferFillEvent) v1;
            logger.log(Level.INFO, "GPU: Filled area at " + event.x() + ":" + event.y() + " with size of " + event.width() + "x" + event.height());
            gr.fillRect(event.x(), event.y(), event.width(), event.height());
            return null;
        });
        EventBus.listenTo(TextBufferSetBackgroundColorEvent.class, (Event v1) -> {
            TextBufferSetBackgroundColorEvent event = (TextBufferSetBackgroundColorEvent) v1;
            logger.log(Level.INFO, "GPU: Background set to " + event.color());
            this.setBackground(new Color(event.color()));
            return null;
        });
        EventBus.listenTo(TextBufferSetColorDepthEvent.class, (Event v1) -> {
            TextBufferSetColorDepthEvent event = (TextBufferSetColorDepthEvent) v1;
            logger.log(Level.INFO, "GPU: Set color depth to " + event.depth());
            return null;
        });
        EventBus.listenTo(TextBufferSetEvent.class, (Event v1) -> {
            TextBufferSetEvent event = (TextBufferSetEvent) v1;
            logger.log(Level.INFO, "GPU: Printed string " + event.value() + " at " + event.x() + ":" + event.y());
            gr.drawString(event.value(), event.x() * this.CHAR_WIDTH, event.y() * this.CHAR_HEIGHT);
            return null;
        });
        EventBus.listenTo(TextBufferSetForegroundColorEvent.class, (Event v1) -> {
            TextBufferSetForegroundColorEvent event = (TextBufferSetForegroundColorEvent) v1;
            logger.log(Level.INFO, "GPU: Foreground set to " + event.color());
            gr.setColor(new Color(event.color()));
            return null;
        });
        EventBus.listenTo(TextBufferSetPaletteColorEvent.class, (Event v1) -> {
            TextBufferSetPaletteColorEvent event = (TextBufferSetPaletteColorEvent) v1;
            logger.log(Level.INFO, "GPU: Set palette color " + event.index());
            return null;
        });
        EventBus.listenTo(TextBufferSetResolutionEvent.class, (Event v1) -> {
            TextBufferSetResolutionEvent event = (TextBufferSetResolutionEvent) v1;
            logger.log(Level.INFO, "GPU: Resolution is set to " + event.width() + "x" + event.height());
            this.setSize(event.width() * this.CHAR_WIDTH, event.height() * this.CHAR_HEIGHT);
            return null;
        });
        EventBus.listenTo(TextBufferSetViewportEvent.class, (Event v1) -> {
            TextBufferSetViewportEvent event = (TextBufferSetViewportEvent) v1;
            // TODO
            return null;
        });
    }
}
