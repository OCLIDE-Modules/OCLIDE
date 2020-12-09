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
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 *
 * @author VladislavTheMountain
 */
public class EmulatorFrame extends JFrame implements KeyListener {

    private static final long serialVersionUID = 1L;

    private String lastEdit;

    private int screenBufferWidth, screenBufferHeight;

    //private JPanel[][] screenBuffer = {};
    private JTextArea console;

    private Timer update;

    public EmulatorFrame(MachineSettings settings) {
        this.screenBufferWidth = settings.getScreenSize().width;
        this.screenBufferHeight = settings.getScreenSize().height;
        /*screenBuffer = new JPanel[this.screenBufferWidth][this.screenBufferHeight];*/
        //System.out.println("screenBuffer initialized");
        //
        this.setTitle("Emulator test");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE/*DISPOSE_ON_CLOSE*/);
        //
        /*GridLayout layout = new GridLayout();
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
        }*/
        console = new JTextArea();
        console.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        console.setFont(new Font("Monospaced", Font.PLAIN, 16));
        console.setBackground(Color.BLACK);
        console.setForeground(Color.WHITE);
        console.setEditable(false);
        console.addKeyListener(this);
        console.setFocusable(true);
        console.setText("/>");
        this.setContentPane(console);
        this.setBounds(0, 0, this.screenBufferWidth * 10, this.screenBufferHeight * 10);

        update = new Timer(300, (ActionEvent e) -> {
            getContentPane().revalidate();
        });
        update.start();

        setVisible(true);
    }

    //Testing method
    public static void main(String args[]) {
        Runnable r = () -> {
            EmulatorFrame e = new EmulatorFrame(new MachineSettings(160, 70));
            System.out.println("frame created");
        };
        r.run();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        lastEdit = lastEdit + e.getKeyChar();
        System.out.println("keyTyped");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            execute(lastEdit);
            lastEdit = null;
        } else {
            lastEdit += e.getKeyChar();
        }
        System.out.println("Key pressed " + e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void print(String s) {
        console.setText(console.getText() + "\n" + s);
    }

    private void execute(String cmd) {
        String currentDir = "/";
        if (cmd.contains("cd ")) {
            if (cmd.equals("cd")) {
                print(currentDir);
            } else {
                currentDir = cmd.substring(3);
            }
        } else if (cmd.equals("dir")) {
            switch (currentDir) {
                case "/bin/":
                    print("cd.lua\tdir.lua\texit.lua\nshutdown.lua");
                    break;
                case "/lib/":
                    print("base32.lua\tbase64.lua\tcomputer.lua\nkeyboard.lua\tterminal.lua");
                    break;
                case "/usr/":
                    print("home.ini");
                    break;
                case "/":
                    print("bin/\tlib/\t/usr\n.init.lua\tautorun.lua");
                    break;
                default:
                    print("[ERROR] Unknown Directory");
                    break;
            }
        } else {
            print("Command is not recognized.");
        }
        print(currentDir + ">");
    }
}
