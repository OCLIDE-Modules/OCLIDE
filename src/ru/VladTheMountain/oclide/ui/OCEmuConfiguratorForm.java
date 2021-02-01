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
package ru.VladTheMountain.oclide.ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import org.apache.commons.io.FileUtils;
import ru.VladTheMountain.oclide.configurator.ocemu.ConfigMaker;
import ru.VladTheMountain.oclide.configurator.ocemu.OCEmuLauncher;
import ru.VladTheMountain.oclide.configurator.ocemu.UUIDGenerator;
import ru.VladTheMountain.oclide.configurator.ocemu.component.OCEmuComponent;

/**
 *
 * @author VladTheMountain
 */
public class OCEmuConfiguratorForm extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    private OCEmuComponent[] componentsArray = {};

    /**
     * Creates new form ConfiguratorForm
     *
     */
    public OCEmuConfiguratorForm() {
        Timer t = new Timer(300, (ActionEvent e) -> {
            this.repaint();
        });
        initComponents();
        if (!(new File("OCEmu/.machine/ocemu.cfg").exists()) || this.componentsArray == null || this.componentsArray.length == 0) {
            this.componentsArray = new OCEmuComponent[OCEmuLauncher.DEFAULT.length];
            System.arraycopy(OCEmuLauncher.DEFAULT, 0, componentsArray, 0, OCEmuLauncher.DEFAULT.length);
        } else {
            try {
                new ConfigMaker(this.componentsArray).readConfig(new File("OCEmu/.machine/ocemu.cfg"));
            } catch (IOException ex) {
                Logger.getLogger(OCEmuConfiguratorForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        updateComponentList();
        t.start();
    }

    /**
     * Adds a new component to the list
     *
     * @param c
     */
    private void addComponent(OCEmuComponent c) {
        OCEmuComponent[] tmp = new OCEmuComponent[this.componentsArray.length + 1];
        System.arraycopy(this.componentsArray, 0, tmp, 0, this.componentsArray.length);
        tmp[this.componentsArray.length] = c;
        this.componentsArray = new OCEmuComponent[tmp.length];
        System.arraycopy(tmp, 0, this.componentsArray, 0, tmp.length);
        updateComponentList();
    }

    /**
     * Deletes a component at {@code index}
     *
     * @param index
     */
    private void deleteComponent(int index) {
        OCEmuComponent[] tmp = new OCEmuComponent[this.componentsArray.length];
        System.arraycopy(this.componentsArray, 0, tmp, 0, tmp.length);
        this.componentsArray = new OCEmuComponent[tmp.length - 1];
        for (int i = 0; i < this.componentsArray.length; i++) {
            this.componentsArray[i] = i < index ? tmp[i] : tmp[i + 1];
        }
    }

    /**
     * Updates componentList {@link JTree}
     */
    private void updateComponentList() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (int i = 0; i < componentsArray.length; i++) {
            model.insertElementAt(this.componentTypeComboBox.getItemAt(this.componentsArray[i].getComponentType() + 1), i);
        }
        this.componentList.setModel(model);
    }

    /**
     * Copies OpenOS from OCEmu's loot folder to target filesystem
     */
    private void installOpenOS() {
        String input = "tmpfs";
        File machineDir = new File("OCEmu/.machine/" + input);
        try {
            FileUtils.copyDirectory(new File("OCEmu/loot/openos/bin"), new File(machineDir.getAbsoluteFile() + "/bin"));
            FileUtils.copyDirectory(new File("OCEmu/loot/openos/boot"), new File(machineDir.getAbsoluteFile() + "/boot"));
            FileUtils.copyDirectory(new File("OCEmu/loot/openos/etc"), new File(machineDir.getAbsoluteFile() + "/etc"));
            FileUtils.copyDirectory(new File("OCEmu/loot/openos/home"), new File(machineDir.getAbsoluteFile() + "/home"));
            FileUtils.copyDirectory(new File("OCEmu/loot/openos/lib"), new File(machineDir.getAbsoluteFile() + "/lib"));
            FileUtils.copyDirectory(new File("OCEmu/loot/openos/usr"), new File(machineDir.getAbsoluteFile() + "/usr"));
            Files.copy(new File("OCEmu/loot/openos/.prop").toPath(), new File(machineDir.getAbsoluteFile() + "/.prop").toPath(), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(new File("OCEmu/loot/openos/init.lua").toPath(), new File(machineDir.getAbsoluteFile() + "/init.lua").toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(OCEmuConfiguratorForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Updates description and option fields
     *
     * @param type
     */
    private void updateFields(OCEmuComponent c) {
        switch (c.getComponentType()) {
            case 0:
                //Description set
                this.componentDescriptionArea.setText("Computer represents the \n'computer' library.");
                //Option change
                this.option1Field.setText("");
                this.option2Field.setText("");
                this.option3Field.setText("");
                this.option4Field.setText("");
                //Option management
                this.option1Field.setEditable(false);
                this.option2Field.setEditable(false);
                this.option3Field.setEditable(false);
                this.option4Field.setEditable(false);
                break;
            case 1:
                this.componentDescriptionArea.setText("EEPROM is responsible for \nbooting up the machine.");
                //Option change
                this.option1Field.setText(c.getOptionAt(0));
                this.option2Field.setText(c.getOptionAt(1));
                this.option3Field.setText("");
                this.option4Field.setText("");
                //Option management
                this.option1Field.setEditable(true);
                this.option2Field.setEditable(true);
                this.option3Field.setEditable(false);
                this.option4Field.setEditable(false);
                break;
            case 2:
                this.componentDescriptionArea.setText("Filesystem is responsible \nfor file management \nand represents the 'filesystem' \nlibrary.");
                //Option change
                this.option1Field.setText(c.getOptionAt(0));
                this.option2Field.setText(c.getOptionAt(1));
                this.option3Field.setText(c.getOptionAt(2));
                this.option4Field.setText("");
                //Option management
                this.option1Field.setEditable(true);
                this.option2Field.setEditable(true);
                this.option3Field.setEditable(true);
                this.option4Field.setEditable(false);
                break;
            case 3:
                this.componentDescriptionArea.setText("GPU is responsible for screen \nbuffer manupulations \nand represents the 'gpu' \ncomponent library.");
                //Option change
                this.option1Field.setText(c.getOptionAt(0));
                this.option2Field.setText(c.getOptionAt(1));
                this.option3Field.setText(c.getOptionAt(2));
                this.option4Field.setText(c.getOptionAt(3));
                //Option management
                this.option1Field.setEditable(true);
                this.option2Field.setEditable(true);
                this.option3Field.setEditable(true);
                this.option4Field.setEditable(true);
                break;
            case 4:
                this.componentDescriptionArea.setText("Internet is responsible for \nHTTP-requests and \nrepresents the 'internet' \ncomponent library.");
                //Option change
                this.option1Field.setText("");
                this.option2Field.setText("");
                this.option3Field.setText("");
                this.option4Field.setText("");
                //Option management
                this.option1Field.setEditable(false);
                this.option2Field.setEditable(false);
                this.option3Field.setEditable(false);
                this.option4Field.setEditable(false);
                break;
            case 5:
                this.componentDescriptionArea.setText("Keyboard is responsible for \nuser's input and \nrepresents the 'keyboard' \ncomponent library.");
                //Option change
                this.option1Field.setText("");
                this.option2Field.setText("");
                this.option3Field.setText("");
                this.option4Field.setText("");
                //Option management
                this.option1Field.setEditable(false);
                this.option2Field.setEditable(false);
                this.option3Field.setEditable(false);
                this.option4Field.setEditable(false);
                break;
            case 6:
                this.componentDescriptionArea.setText("Modem is responsible for \ncross-machine networking \nand represents the 'modem' \ncomponent library.");
                //Option change
                this.option1Field.setText(c.getOptionAt(0));
                this.option2Field.setText(c.getOptionAt(1));
                this.option3Field.setText("");
                this.option4Field.setText("");
                //Option management
                this.option1Field.setEditable(true);
                this.option2Field.setEditable(true);
                this.option3Field.setEditable(false);
                this.option4Field.setEditable(false);
                break;
            case 7:
                this.componentDescriptionArea.setText("OCEmu is responsible for \nintgration of OCEmu's code \nwith original OpenComputers'\n code.");
                //Option change
                this.option1Field.setText("");
                this.option2Field.setText("");
                this.option3Field.setText("");
                this.option4Field.setText("");
                //Option management
                this.option1Field.setEditable(false);
                this.option2Field.setEditable(false);
                this.option3Field.setEditable(false);
                this.option4Field.setEditable(false);
                break;
            case 8:
                this.componentDescriptionArea.setText("Screen is responsible for \nthe screen block and \nrepresents the 'screen' library.");
                //Option change
                this.option1Field.setText(c.getOptionAt(0));
                this.option2Field.setText(c.getOptionAt(1));
                this.option3Field.setText(c.getOptionAt(2));
                this.option4Field.setText(c.getOptionAt(3));
                //Option management
                this.option1Field.setEditable(true);
                this.option2Field.setEditable(true);
                this.option3Field.setEditable(true);
                this.option4Field.setEditable(true);
                break;
            default:
                this.componentDescriptionArea.setText("Couldn't read the component");
                //Option change
                this.option1Field.setText("");
                this.option2Field.setText("");
                this.option3Field.setText("");
                this.option4Field.setText("");
                //Option management
                this.option1Field.setEditable(false);
                this.option2Field.setEditable(false);
                this.option3Field.setEditable(false);
                this.option4Field.setEditable(false);
                break;
        }
    }

    /**
     * Recursively gets all files in the project folder
     *
     * @param file Project folder as a {@link File}
     * @param n Project's tree node
     */
    private static void recursivelyCopyFiles(File src, File targ) {
        File[] files = src.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            if (f.isFile()) {
                try {
                    Files.copy(f.toPath(), new File(targ.getAbsolutePath() + FileSystems.getDefault().getSeparator() + f.getName()).toPath());
                } catch (IOException ex) {
                    Logger.getLogger(OCEmuConfiguratorForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    FileUtils.copyDirectory(f, targ);
                } catch (IOException ex) {
                    Logger.getLogger(OCEmuConfiguratorForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (f.isDirectory()) {
                recursivelyCopyFiles(src, targ);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        configChooser = new JFileChooser();
        controlPanel = new JPanel();
        launchButton = new JButton();
        cancelButton = new JButton();
        componentSettingsPanel = new JPanel();
        jLabel1 = new JLabel();
        componentAddressField = new JTextField();
        jLabel2 = new JLabel();
        option1Field = new JTextField();
        jLabel3 = new JLabel();
        option2Field = new JTextField();
        jLabel4 = new JLabel();
        option3Field = new JTextField();
        descriptionPanel = new JPanel();
        jScrollPane2 = new JScrollPane();
        componentDescriptionArea = new JTextArea();
        jLabel5 = new JLabel();
        componentTypeComboBox = new JComboBox<>();
        jLabel6 = new JLabel();
        option4Field = new JTextField();
        jScrollPane1 = new JScrollPane();
        componentList = new JList<>();
        jMenuBar1 = new JMenuBar();
        fileMenu = new JMenu();
        resetConfigItem = new JMenuItem();
        jSeparator1 = new JPopupMenu.Separator();
        importConfigItem = new JMenuItem();
        saveConfigItem = new JMenuItem();
        jSeparator2 = new JPopupMenu.Separator();
        exitItem = new JMenuItem();
        componentMenu = new JMenu();
        jMenuItem1 = new JMenuItem();
        jMenuItem2 = new JMenuItem();
        helpMenu = new JMenu();

        configChooser.setDialogTitle("Open config...");
        configChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f){
                return f.getName().equals("ocemu.cfg") ? true : false;
            }

            @Override
            public String getDescription(){
                return "OCEmu config file ('ocemu.cfg')";
            }
        }
    );
    configChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("Configure OCEmu");
    setResizable(false);

    launchButton.setText("Launch");
    launchButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            launchButtonActionPerformed(evt);
        }
    });

    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            cancelButtonActionPerformed(evt);
        }
    });

        GroupLayout controlPanelLayout = new GroupLayout(controlPanel);
    controlPanel.setLayout(controlPanelLayout);
    controlPanelLayout.setHorizontalGroup(controlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(GroupLayout.Alignment.TRAILING, controlPanelLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(cancelButton)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(launchButton)
            .addContainerGap())
    );
    controlPanelLayout.setVerticalGroup(controlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(GroupLayout.Alignment.TRAILING, controlPanelLayout.createSequentialGroup()
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(controlPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(launchButton)
                .addComponent(cancelButton))
            .addContainerGap())
    );

    jLabel1.setText("Component's address:");

    componentAddressField.setEditable(false);

    jLabel2.setText("Option 1");

    jLabel3.setText("Option 2");

    jLabel4.setText("Option 3");

    descriptionPanel.setBorder(BorderFactory.createTitledBorder(null, "Description", TitledBorder.RIGHT, TitledBorder.TOP, new Font("Segoe UI", 1, 10))); // NOI18N

    jScrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    componentDescriptionArea.setEditable(false);
    componentDescriptionArea.setColumns(20);
    componentDescriptionArea.setFont(new Font("Monospaced", 0, 12)); // NOI18N
    componentDescriptionArea.setRows(5);
    componentDescriptionArea.setWrapStyleWord(true);
    jScrollPane2.setViewportView(componentDescriptionArea);

        GroupLayout descriptionPanelLayout = new GroupLayout(descriptionPanel);
    descriptionPanel.setLayout(descriptionPanelLayout);
    descriptionPanelLayout.setHorizontalGroup(descriptionPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane2)
    );
    descriptionPanelLayout.setVerticalGroup(descriptionPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(descriptionPanelLayout.createSequentialGroup()
            .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, Short.MAX_VALUE))
    );

    jLabel5.setText("Component Type:");

    componentTypeComboBox.setModel(new DefaultComboBoxModel<>(new String[] { "computer", "cpu", "eeprom", "filesystem", "gpu", "internet", "keyboard", "modem", "ocemu", "screen", " " }));
    componentTypeComboBox.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            componentTypeComboBoxActionPerformed(evt);
        }
    });

    jLabel6.setText("Option 4");

        GroupLayout componentSettingsPanelLayout = new GroupLayout(componentSettingsPanel);
    componentSettingsPanel.setLayout(componentSettingsPanelLayout);
    componentSettingsPanelLayout.setHorizontalGroup(componentSettingsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(componentSettingsPanelLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(componentSettingsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(descriptionPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(GroupLayout.Alignment.TRAILING, componentSettingsPanelLayout.createSequentialGroup()
                    .addGroup(componentSettingsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2)
                        .addComponent(jLabel5)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4)
                        .addComponent(jLabel6))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(componentSettingsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(option3Field)
                        .addComponent(option2Field)
                        .addComponent(componentTypeComboBox, 0, 208, Short.MAX_VALUE)
                        .addComponent(option1Field)
                        .addComponent(option4Field)
                        .addComponent(componentAddressField, GroupLayout.Alignment.TRAILING))))
            .addContainerGap())
    );
    componentSettingsPanelLayout.setVerticalGroup(componentSettingsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(componentSettingsPanelLayout.createSequentialGroup()
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(componentSettingsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel5)
                .addComponent(componentTypeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(componentSettingsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(componentAddressField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(componentSettingsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel2)
                .addComponent(option1Field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(componentSettingsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(option2Field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel3))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(componentSettingsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel4)
                .addComponent(option3Field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(componentSettingsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel6)
                .addComponent(option4Field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(descriptionPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );

    componentList.setModel(new AbstractListModel<String>() {
        String[] strings = { "gpu", "modem", "eeprom", "filesystem", "filesystem", "filesystem", "internet", "computer", "ocemu", "screen", "keyboard" };
        public int getSize() { return strings.length; }
        public String getElementAt(int i) { return strings[i]; }
    });
    componentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    componentList.addListSelectionListener(new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent evt) {
            componentListValueChanged(evt);
        }
    });
    jScrollPane1.setViewportView(componentList);

    fileMenu.setText("File");

    resetConfigItem.setText("Reset config");
    resetConfigItem.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            resetConfigItemActionPerformed(evt);
        }
    });
    fileMenu.add(resetConfigItem);
    fileMenu.add(jSeparator1);

    importConfigItem.setText("Import from file");
    importConfigItem.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            importConfigItemActionPerformed(evt);
        }
    });
    fileMenu.add(importConfigItem);

    saveConfigItem.setText("Save to file");
    saveConfigItem.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            saveConfigItemActionPerformed(evt);
        }
    });
    fileMenu.add(saveConfigItem);
    fileMenu.add(jSeparator2);

    exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
    exitItem.setText("Exit");
    exitItem.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            exitItemActionPerformed(evt);
        }
    });
    fileMenu.add(exitItem);

    jMenuBar1.add(fileMenu);

    componentMenu.setText("Component");

    jMenuItem1.setText("Add");
    jMenuItem1.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            jMenuItem1ActionPerformed(evt);
        }
    });
    componentMenu.add(jMenuItem1);

    jMenuItem2.setText("Delete selected");
    jMenuItem2.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            jMenuItem2ActionPerformed(evt);
        }
    });
    componentMenu.add(jMenuItem2);

    jMenuBar1.add(componentMenu);

    helpMenu.setText("Help");
    jMenuBar1.add(helpMenu);

    setJMenuBar(jMenuBar1);

        GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(componentSettingsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addComponent(controlPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(componentSettingsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1)))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(controlPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
    );

    pack();
    setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void componentListValueChanged(ListSelectionEvent evt) {//GEN-FIRST:event_componentListValueChanged
        int s = this.componentList.getSelectedIndex();
        if (s >= 0) {
            this.componentTypeComboBox.setSelectedIndex(componentsArray[s].getComponentType() + 1);
            this.componentAddressField.setText(componentsArray[s].getComponentAddress());
            updateFields(componentsArray[s]);
        }
    }//GEN-LAST:event_componentListValueChanged

    private void cancelButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void launchButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_launchButtonActionPerformed
        boolean isOSInstalled = false;
        String OpenOSUUID = "tmpfs";
        File machineFolder = new File("OCEmu" + FileSystems.getDefault().getSeparator() + ".machine");
        if (machineFolder.exists() && machineFolder.listFiles().length > 0) {
            /*for (File file : machineFolder.listFiles()) {
                if (file.isDirectory()) {
                    for (String current : file.list()) {
                        //Well, technically it allows to setup ANY OS, compatible with Lua BIOS EEPROM bootloader script
                        if (current.equals("init.lua")) {
                            isOSInstalled = true;
                            OpenOSUUID = file.getName();
                        }
                    }
                }
            }*/
        } else {
            machineFolder.mkdirs();
        }
        System.out.println("OpenOS installed = " + isOSInstalled);
        if (!(isOSInstalled)) {
            installOpenOS();
        }
        System.out.println("OpenOS is installed");
        //Creating config
        try {
            new ConfigMaker(this.componentsArray).createConfig();
        } catch (IOException ex) {
            Logger.getLogger(OCEmuConfiguratorForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Config ready");
        //Files' copy
        if (new File("projects").exists()) {
            try {
                //recursivelyCopyFiles(new File("projects"), new File("OCEmu"+FileSystems.getDefault().getSeparator()+".machine"+FileSystems.getDefault().getSeparator()+OpenOSUUID+FileSystems.getDefault().getSeparator()+"home"));
                FileUtils.copyDirectory(new File("projects"), new File(/*System.getenv("APPDATA") + FileSystems.getDefault().getSeparator() + "OCEmu"*/".machine" + FileSystems.getDefault().getSeparator() + OpenOSUUID + FileSystems.getDefault().getSeparator() + "home"));
                System.out.println("Copied " + new File("projects").getAbsolutePath() + " to " + new File(/*System.getenv("APPDATA") + FileSystems.getDefault().getSeparator() + "OCEmu"*/".machine" + FileSystems.getDefault().getSeparator() + OpenOSUUID + FileSystems.getDefault().getSeparator() + "home").getAbsolutePath());
            } catch (IOException ex) {
                Logger.getLogger(OCEmuConfiguratorForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Files copied");
        //
        Thread t = new Thread() {
            @Override
            public void run() {
                ProcessBuilder pb;
                pb = System.getProperty("os.name").contains("Windows") ? new ProcessBuilder("cmd.exe", "/c", "start", "/D", "OCEmu", "OCEmu\\OCEmu.exe") : new ProcessBuilder("lua", "OCEmu/boot.lua", "./.machine");
                pb.redirectErrorStream(true).inheritIO();
                try {
                    Process p = pb.start();
                    BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String outLine;
                    Logger.getLogger(OCEmuConfiguratorForm.class.getName()).log(Level.INFO, "Starting OCEmu...");
                    while (true) {
                        outLine = r.readLine();
                        if (outLine == null) {
                            break;
                        }
                        System.out.println(outLine);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(OCEmuConfiguratorForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        t.start();
        System.out.println("Tread started");
    }//GEN-LAST:event_launchButtonActionPerformed

    private void exitItemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_exitItemActionPerformed
        this.dispose();
    }//GEN-LAST:event_exitItemActionPerformed

    private void importConfigItemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_importConfigItemActionPerformed
        if (this.configChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                new ConfigMaker(this.componentsArray).readConfig(this.configChooser.getSelectedFile());
            } catch (IOException ex) {
                Logger.getLogger(OCEmuConfiguratorForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            updateComponentList();
        }
    }//GEN-LAST:event_importConfigItemActionPerformed

    private void saveConfigItemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_saveConfigItemActionPerformed
        try {
            new ConfigMaker(this.componentsArray).createConfig();
        } catch (IOException ex) {
            Logger.getLogger(OCEmuConfiguratorForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_saveConfigItemActionPerformed

    private void componentTypeComboBoxActionPerformed(ActionEvent evt) {//GEN-FIRST:event_componentTypeComboBoxActionPerformed
        updateComponentList();
    }//GEN-LAST:event_componentTypeComboBoxActionPerformed

    private void resetConfigItemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_resetConfigItemActionPerformed
        try {
            new ConfigMaker(OCEmuLauncher.DEFAULT).createConfig();
            this.componentsArray = new OCEmuComponent[OCEmuLauncher.DEFAULT.length];
            System.arraycopy(OCEmuLauncher.DEFAULT, 0, this.componentsArray, 0, OCEmuLauncher.DEFAULT.length);
        } catch (IOException ex) {
            Logger.getLogger(OCEmuConfiguratorForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        updateComponentList();
    }//GEN-LAST:event_resetConfigItemActionPerformed

    private void jMenuItem1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        this.addComponent(new OCEmuComponent(0, UUIDGenerator.create(), ""));
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        if (this.componentList.getSelectedValue() != null) {
            deleteComponent(this.componentList.getSelectedIndex());
        } else {
            JOptionPane.showMessageDialog(this, "No component selected.", "Error.", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton cancelButton;
    JTextField componentAddressField;
    JTextArea componentDescriptionArea;
    JList<String> componentList;
    private JMenu componentMenu;
    private JPanel componentSettingsPanel;
    JComboBox<String> componentTypeComboBox;
    JFileChooser configChooser;
    private JPanel controlPanel;
    private JPanel descriptionPanel;
    private JMenuItem exitItem;
    private JMenu fileMenu;
    private JMenu helpMenu;
    private JMenuItem importConfigItem;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JMenuBar jMenuBar1;
    private JMenuItem jMenuItem1;
    private JMenuItem jMenuItem2;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JPopupMenu.Separator jSeparator1;
    private JPopupMenu.Separator jSeparator2;
    private JButton launchButton;
    JTextField option1Field;
    JTextField option2Field;
    JTextField option3Field;
    JTextField option4Field;
    private JMenuItem resetConfigItem;
    private JMenuItem saveConfigItem;
    // End of variables declaration//GEN-END:variables
}
