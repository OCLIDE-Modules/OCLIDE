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

import javax.swing.JFrame;

/**
 *
 * @author VladTheMountain
 */
public class EmulatorFrame extends JFrame {

    /*private JTextPane consoleView;
    private String input;
    private DefaultStyledDocument terminalDoc;*/
    public EmulatorFrame(String osName) {
        // NOT WORKING CODE
        /*this.setTitle("Emulator Test");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //--
        terminalDoc = new javax.swing.text.DefaultStyledDocument();
        terminalDoc.addUndoableEditListener((UndoableEditEvent e) -> {
            input = e.getEdit().toString();
        });
        //--
        consoleView = new JTextPane();
        consoleView.setBackground(Color.BLACK);
        consoleView.setForeground(Color.WHITE);
        consoleView.setFont(new java.awt.Font("Monospaced", Font.BOLD, 12));
        consoleView.setDocument(terminalDoc);
        //--
        this.getContentPane().add(consoleView);
        consoleView.setSize(consoleView.getParent().getSize());
        this.pack();
    }

    public void print(Object msg) {
        consoleView.setText(consoleView.getText() + "\n" + msg);
    }

    public Object read() {
        return input;
    }*/
        this.setTitle(osName + " Emulator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }
}
