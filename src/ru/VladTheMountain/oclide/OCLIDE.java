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
package ru.VladTheMountain.oclide;

import ru.VladTheMountain.oclide.ui.frames.EditorFrame;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Launchong script
 *
 * @author VladTheMountain
 */
public class OCLIDE {

    /**
     * Main method of the application
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final SplashScreen splash = SplashScreen.getSplashScreen();
        if (splash == null) {
            Logger.getLogger(OCLIDE.class.getName()).log(Level.INFO, "No splash was found to display, ignoring...");
            //return;
        } else {
            Graphics2D g = splash.createGraphics();
            if (g == null) {
                Logger.getLogger(OCLIDE.class.getName()).log(Level.WARNING, "No suitable Graphics2D was found to display the splash");
                return;
            }
        }
        try {
            Thread.sleep(2000);
            new EditorFrame().setVisible(true);
        } catch (InterruptedException ex) {
            Logger.getLogger(OCLIDE.class.getName()).log(Level.SEVERE, "Couldn't start main JFrame", ex);
        }
    }
}
