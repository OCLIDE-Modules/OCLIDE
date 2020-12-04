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
package ru.VladTheMountain.oclide.emulator;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author VladislavTheMountain
 */
public class EmulatorFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private int screenBufferWidth, screenBufferHeight;

    private JPanel[][] screenBuffer = {};

    private Timer update;

    public EmulatorFrame(MachineSettings settings) {
        this.screenBufferWidth = settings.getScreenSize().width;
        this.screenBufferHeight = settings.getScreenSize().height;
        screenBuffer = new JPanel[this.screenBufferWidth][this.screenBufferHeight];
        System.out.println("screenBuffer initialized");
        //
        this.setTitle("Emulator test");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        System.out.println("frame configuration DONE");
        //
        GridLayout layout = new GridLayout();
        System.out.println("GridLayout - DONE");
        //
        //this.getContentPane().setLayout(layout);
        for (int i = 0; i < screenBufferHeight; i++) {
            for (int j = 0; j < screenBufferWidth; j++) {
                JPanel panel = new JPanel();
                panel.setBackground(Color.BLACK);
                panel.setSize(1, 1);
                this.getContentPane().add(panel);
                screenBuffer[j][i] = panel;
            }
        }
        System.out.println("components inserted");
        //
        //this.pack();
        this.setBounds(0, 0, this.screenBufferWidth*10, this.screenBufferHeight*10);
        System.out.println("frame packed");

        update = new Timer(300, (ActionEvent e) -> {
            getContentPane().revalidate();
        });
        System.out.println("Timer DONE");

        update.start();
        System.out.println("Timer started");
        
        setVisible(true);
        System.out.println("frame now visible");
    }

    //Testing method
    public static void main(String args[]) {
        Runnable r = () -> {
            EmulatorFrame e = new EmulatorFrame(new MachineSettings(160, 70));
            System.out.println("frame created");
        };
        r.run();
    }
}
