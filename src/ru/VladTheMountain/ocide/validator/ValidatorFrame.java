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
     */
    public ValidatorFrame(/*ArchitecturePanel tl, UpgradesPanel bl, MainComponentsPanel tr, SecondaryComponentsPanel br*/) {
        instance = this;
        /*panelTL = tl;
        panelBL = bl;
        panelTR = tr;
        panelBR = br;*/
        new javax.swing.Timer(300, (java.awt.event.ActionEvent e) -> {
            instance.repaint();
        });
        initComponents();
        instance.setResizable(false);
        instance.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        instance.setTitle("Validator configuration");
    }

    private void initComponents() {
        javax.swing.JMenu config = new javax.swing.JMenu("Configuration");
        config.add(new javax.swing.JMenuItem(new javax.swing.AbstractAction("Import configuration", new javax.swing.ImageIcon(ru.VladTheMountain.ocide.MainForm.class.getResource("assets/ImportIcon.png"))) {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                javax.swing.JFileChooser jfc = new javax.swing.JFileChooser(new java.io.File("/conf/user/"));
                jfc.setDialogType(javax.swing.JFileChooser.OPEN_DIALOG);
                jfc.setDialogTitle("Import configuration from file...");
                jfc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(".ocideconf(OCIDE Configuration File)", "ocideconf"));
                jfc.setApproveButtonText("Import...");
            }
        }));
        config.add(new javax.swing.JMenuItem(new javax.swing.AbstractAction("Save configuration", new javax.swing.ImageIcon(ru.VladTheMountain.ocide.MainForm.class.getResource("assets/SaveIcon.png"))) {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                javax.swing.JFileChooser jfc = new javax.swing.JFileChooser(new java.io.File("/conf/user/"));
                jfc.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
                jfc.setDialogTitle("Save configuration to file...");
                jfc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(".ocideconf(OCIDE Configuration File)", "ocideconf"));
                jfc.setSelectedFile(new java.io.File("user_configuration.ocideconf"));
            }
        }));
        javax.swing.JMenuBar menu = new javax.swing.JMenuBar();
        menu.add(config);
        //bottom row
        javax.swing.JPanel bottomPanel = new javax.swing.JPanel();
        bottomPanel.setLayout(new java.awt.GridBagLayout());

        //Output area
        javax.swing.JTextArea output = new javax.swing.JTextArea("Example Output", 50, 160);
        output.setBackground(java.awt.Color.BLACK);
        output.setForeground(java.awt.Color.WHITE);
        output.setFont(new java.awt.Font(java.awt.Font.MONOSPACED, java.awt.Font.BOLD, 12));
        output.setEditable(false);
        bottomPanel.add(output, new java.awt.GridBagConstraints(
                1, 0,
                3, java.awt.GridBagConstraints.REMAINDER,
                1.0, 1.0,
                java.awt.GridBagConstraints.CENTER, java.awt.GridBagConstraints.BOTH,
                new java.awt.Insets(0, 0, 0, 0), 0, 0));

        //Launch button
        bottomPanel.add(new javax.swing.JButton(new javax.swing.AbstractAction("Launch") {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                javax.swing.JOptionPane.showConfirmDialog(instance, new ru.VladTheMountain.ocide.validator.Validator(instance.getPrefrences()).run(), "Validator output", javax.swing.JOptionPane.OK_OPTION, javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        }), new java.awt.GridBagConstraints(
                4, 2,
                java.awt.GridBagConstraints.REMAINDER, 1,
                0.0, 0.0,
                java.awt.GridBagConstraints.WEST, java.awt.GridBagConstraints.NONE,
                new java.awt.Insets(0, 0, 0, 0), 0, 0));
        
        //panels
        javax.swing.JPanel panels = new javax.swing.JPanel(new java.awt.GridLayout(2, 2));
        panels.add(instance.panelTL);
        panels.add(instance.panelTR);
        panels.add(instance.panelBL);
        panels.add(instance.panelBR);
        //finishing
        instance.getContentPane().setLayout(new javax.swing.BoxLayout(instance.getContentPane(), javax.swing.BoxLayout.PAGE_AXIS));
        instance.getContentPane().add(menu);
        instance.getContentPane().add(panels);
        instance.getContentPane().add(bottomPanel);
        instance.pack();
    }

    protected String[] getPrefrences() {
        return new String[]{
            panelTL.getArchitecture()
        };
    }
}
