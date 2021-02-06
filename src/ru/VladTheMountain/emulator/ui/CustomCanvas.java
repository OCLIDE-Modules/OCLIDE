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
package ru.VladTheMountain.emulator.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author VladTheMountain
 */
public class CustomCanvas extends Canvas {

    private static final long serialVersionUID = 1L;

    private Color back = Color.BLACK;
    private Color fore = Color.WHITE;
    private final int CHAR_WIDTH = 9;
    private final int CHAR_HEIGHT = 22;
    private final Font font = Font.getFont("Monospaced Plain 16");
    private char[][] buffer;

    private Graphics mainGraphics;

    public CustomCanvas(Graphics g) {
        super();
        setFont(font);
        mainGraphics = g.create();
    }

    @Override
    public void paint(Graphics gr) {

    }

    /**
     * Private logic handler
     */
    private Color translateToRGB(String s) {
        return new Color(Integer.parseInt(s.substring(0, 2), 16), Integer.parseInt(s.substring(2, 4), 16), Integer.parseInt(s.substring(4, 6), 16));
    }

    /**
     * Changes tier and resets the buffer
     *
     * @param i
     */
    public void setTier(int i) {
        switch (i) {
            case 2:
                buffer = new char[80][25];
                break;
            case 3:
                buffer = new char[160][50];
                break;
            case 1:
            default:
                buffer = new char[50][16];
                break;
        }
        System.out.println("[Ocelot/INFO] Changed screen tier to " + i);
    }

    /**
     * The same as {@code gpu.setBackground(colors.x)}
     *
     * @param c
     */
    public void setBackgroundColor(int c) {
        back = new Color(c);
    }

    /**
     * The same as {@code gpu.setBackground("xxxxxx")}
     *
     * @param hex
     */
    public void setBackgroundColor(String hex) {
        back = translateToRGB(hex);
    }

    /**
     * The same as {@code gpu.setForeground(colors.x)}
     *
     * @param c
     */
    public void setForegroundColor(int c) {
        fore = new Color(c);
    }

    /**
     * The same as {@code gpu.setForeground("xxxxxx")}
     *
     * @param hex
     */
    public void setForegroundColor(String hex) {
        fore = translateToRGB(hex);
    }

    /**
     * The same as {@code gpu.set()}
     *
     * @param x
     * @param y
     * @param s
     */
    public void set(int x, int y, String s) {
        mainGraphics.setFont(font);
        mainGraphics.setColor(back);
        mainGraphics.fillRect(x, y, s.length() * this.CHAR_WIDTH, this.CHAR_HEIGHT);
        mainGraphics.setColor(fore);
        mainGraphics.drawString(s, x, y);
        System.out.println("[Ocelot/INFO] Drew " + s + " at " + x + ":" + y);
    }

    /**
     * The same as {@code gpu.fill()}
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param charecter
     */
    public void fill(int x, int y, int width, int height, String charecter) {
        if (charecter.length() == 1) {
            mainGraphics.setColor(back);
            mainGraphics.fillRect(x, y, width * this.CHAR_WIDTH, height * this.CHAR_HEIGHT);
            mainGraphics.setColor(fore);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    mainGraphics.drawString(charecter, x + j * this.CHAR_WIDTH, y + i * this.CHAR_HEIGHT);
                }
            }
            System.out.println("[Ocelot/INFO] Filled a " + width + "x" + height + " rectangle at " + x + ":" + y);
        } else {
            System.out.println("[Ocelot/ERROR] Wrong char length of " + charecter + ". Expecting 1, found " + charecter.length());
        }
    }
}
