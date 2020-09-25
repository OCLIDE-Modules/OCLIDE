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
package ru.VladTheMountain.ocide;

/**
 * JPanel responsible for the editor
 * 
 * @author VladTheMountain
 */
public class EditorPanel extends javax.swing.JPanel {

    private javax.swing.JPanel INSTANCE;
    
    //GUI
    javax.swing.JTabbedPane tabsPane;
    
    public Editor(java.io.File[] openedFiles){
        INSTANCE = this;
        createGUI();
        if(openedFiles!=null){
               
        } else {
            
        }
    }
    
    void createGUI(){
        java.awt.Container rootPane = INSTANCE.getContentPane();
        //Tabbed pane
        tabsPane = new javax.swing.JTabbedPane(javax.swing.JTabbedPane.TOP);
        tabsPane.setBounds(0, 0, rootPane.getWidth(), rootPane.getHeight());
        //Adding
        rootPane.add(tabsPane);
    }
    
    void openFiles(java.io.File[] files){
        for(java.io.File currentFile : files){
            tabsPane.addTabbedPane(new javax.swing.JEditorPane("text/plain", new java.lang.String(new java.io.BufferedReader(new java.nio.Files(currentFile).lines()))));   
        }
    }
}
