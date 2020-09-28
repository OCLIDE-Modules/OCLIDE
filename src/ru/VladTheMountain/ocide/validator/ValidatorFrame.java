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
package ru.VladTheMountain.ocide.validator;

import java.awt.event.ActionEvent;

/**
 * Validator's interface JFrame
 *
 * @author VladTheMountain
 */
public class ValidatorFrame extends javax.swing.JFrame {

    private ValidatorFrame instance;

    ArchitecturePanel panelTL = new ArchitecturePanel();
    UpgradesPanel panelBL = new UpgradesPanel();
    MainComponentsPanel panelTR = new MainComponentsPanel();
    SecondaryComponentsPanel panelBR = new SecondaryComponentsPanel();

    /**
     * Creates a new {@link ru.VladTheMountain.ocide.validator.Validator}
     * interface instance.
     *
     * @param tl Architecture configuration {@link javax.swing.JPanel}
     * @param bl Upgrades' configuration {@link javax.swing.JPanel}
     * @param tr Primary components - EEPROM, CPU/APU, Memory cards, HDDs -
     * configuration {@link javax.swing.JPanel}
     * @param br Secondary components - Data cards, GPUs, Network cards etc. -
     * configuration {@link javax.swing.JPanel}
     */
    public ValidatorFrame(/*ArchitecturePanel tl, UpgradesPanel bl, MainComponentsPanel tr, SecondaryComponentsPanel br*/) {
        instance = this;
        /*panelTL = tl;
        panelBL = bl;
        panelTR = tr;
        panelBR = br;*/
        new javax.swing.Timer(300, (ActionEvent e) -> {
            instance.repaint();
        });
        initComponents();
    }

    private void initComponents() {
        javax.swing.JMenu config = new javax.swing.JMenu("Configuration");
        config.add(new javax.swing.JMenuItem(new javax.swing.AbstractAction("Import configuration", new javax.swing.ImageIcon(ru.VladTheMountain.ocide.MainForm.class.getResource("assets/ImportIcon.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                javax.swing.JFileChooser jfc = new javax.swing.JFileChooser(new java.io.File("/conf/user/"));
                jfc.setDialogType(javax.swing.JFileChooser.OPEN_DIALOG);
                jfc.setDialogTitle("Import configuration from file...");
                jfc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(".ocideconf(OCIDE Configuration File)", "ocideconf"));
                jfc.setApproveButtonText("Import...");
            }
        }));
        config.add(new javax.swing.JMenuItem(new javax.swing.AbstractAction("Save configuration", new javax.swing.ImageIcon(ru.VladTheMountain.ocide.MainForm.class.getResource("assets/SaveIcon.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                javax.swing.JFileChooser jfc = new javax.swing.JFileChooser(new java.io.File("/conf/user/"));
                jfc.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
                jfc.setDialogTitle("Save configuration to file...");
                jfc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(".ocideconf(OCIDE Configuration File)", "ocideconf"));
                jfc.setSelectedFile(new java.io.File("user_configuration.ocideconf"));
            }
        }));
        //bottom row
        javax.swing.JPanel bottomPanel = new javax.swing.JPanel();
        bottomPanel.add(new javax.swing.JButton(new javax.swing.AbstractAction("Launch") {
            @Override
            public void actionPerformed(ActionEvent e) {
                javax.swing.JOptionPane.showConfirmDialog(instance, new ru.VladTheMountain.ocide.validator.Validator(instance.getPrefrences()).run(), "Validator output", javax.swing.JOptionPane.OK_OPTION, javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        }));
        bottomPanel.add(new javax.swing.JButton(new javax.swing.AbstractAction("Cancel") {
            @Override
            public void actionPerformed(ActionEvent e) {
                instance.dispose();
            }
        }));
        //left column
        java.awt.BorderLayout lLayout = new java.awt.BorderLayout();
        lLayout.addLayoutComponent(instance.panelTL, java.awt.BorderLayout.NORTH);
        lLayout.addLayoutComponent(instance.panelBL, java.awt.BorderLayout.SOUTH);
        javax.swing.JPanel leftPanel = new javax.swing.JPanel(lLayout);
        //right column
        java.awt.BorderLayout rLayout = new java.awt.BorderLayout();
        lLayout.addLayoutComponent(instance.panelTR, java.awt.BorderLayout.NORTH);
        lLayout.addLayoutComponent(instance.panelBR, java.awt.BorderLayout.SOUTH);
        javax.swing.JPanel rightPanel = new javax.swing.JPanel(rLayout);
        //finishing
        java.awt.BorderLayout gen = new java.awt.BorderLayout();
        gen.addLayoutComponent(leftPanel, java.awt.BorderLayout.WEST);
        gen.addLayoutComponent(rightPanel, java.awt.BorderLayout.EAST);
        gen.addLayoutComponent(bottomPanel, java.awt.BorderLayout.SOUTH);
        instance.getContentPane().setLayout(gen);
        instance.pack();
    }

    protected String[] getPrefrences() {
        return new String[]{
            panelTL.getArchitecture()
        };
    }
}
