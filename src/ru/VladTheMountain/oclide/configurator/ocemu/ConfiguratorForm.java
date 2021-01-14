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
package ru.VladTheMountain.oclide.configurator.ocemu;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import org.apache.commons.io.FileUtils;
import ru.VladTheMountain.oclide.configurator.ocemu.component.OCEmuComponent;
import ru.VladTheMountain.oclide.configurator.ocemu.util.UUIDGenerator;

/**
 *
 * @author VladTheMountain
 */
public class ConfiguratorForm extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    private OCEmuComponent[] componentsArray = {};

    /**
     * Creates new form ConfiguratorForm
     *
     * @param projectName Project directory to copy
     */
    public ConfiguratorForm(String projectName) {
        Timer t = new Timer(300, (ActionEvent e) -> {
            this.repaint();
        });
        initComponents();
        if (!(new File("OCEmu/.machine/ocemu.cfg").exists()) || this.componentsArray == null || this.componentsArray.length == 0) {
            this.componentsArray = new OCEmuComponent[ConfigMaker.defaultComponentSet.length];
            System.arraycopy(ConfigMaker.defaultComponentSet, 0, componentsArray, 0, ConfigMaker.defaultComponentSet.length);
        } else {
            try {
                new ConfigMaker(this.componentsArray).readConfig(new File("OCEmu/.machine/ocemu.cfg"));
            } catch (IOException ex) {
                Logger.getLogger(ConfiguratorForm.class.getName()).log(Level.SEVERE, null, ex);
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
            if (i < index) {
                this.componentsArray[i] = tmp[i];
            } else {
                this.componentsArray[i] = tmp[i + 1];
            }
        }
    }

    /**
     * Updates componentList {@link JTree}
     */
    private void updateComponentList() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (int i = 0; i < componentsArray.length; i++) {
            model.insertElementAt(this.componentsArray[i].getComponentAddress(), i);
        }
        this.componentList.setModel(model);
    }

    /**
     * Copies OpenOS from OCEmu's loot folder to target filesystem
     */
    private void installOpenOS() {
        String[] filesystems = new String[this.componentsArray.length];
        int nextFreeIndex = 0;
        for (OCEmuComponent component : this.componentsArray) {
            if (component.getComponentType() == 2) {
                filesystems[nextFreeIndex] = component.getComponentAddress();
            }
            nextFreeIndex++;
        }
        int input = JOptionPane.showOptionDialog(this, "Choose a filesystem to install OpenOS to:", "Machine setup", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, filesystems, filesystems[0]);
        File machineDir = new File("OCEmu/.machine/" + filesystems[input]);
        try {
            FileUtils.copyDirectory(new File("OCEmu/loot/OpenOS/bin"), machineDir);
            FileUtils.copyDirectory(new File("OCEmu/loot/OpenOS/boot"), machineDir);
            FileUtils.copyDirectory(new File("OCEmu/loot/OpenOS/etc"), machineDir);
            FileUtils.copyDirectory(new File("OCEmu/loot/OpenOS/home"), machineDir);
            FileUtils.copyDirectory(new File("OCEmu/loot/OpenOS/lib"), machineDir);
            FileUtils.copyDirectory(new File("OCEmu/loot/OpenOS/usr"), machineDir);
            Files.copy(new File("OCEmu/loot/OpenOS/.osprop").toPath(), machineDir.toPath(), (CopyOption[]) null);
            Files.copy(new File("OCEmu/loot/OpenOS/init.lua").toPath(), machineDir.toPath(), (CopyOption[]) null);
        } catch (IOException ex) {
            Logger.getLogger(ConfiguratorForm.class.getName()).log(Level.SEVERE, null, ex);
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

        configChooser = new javax.swing.JFileChooser();
        javax.swing.JPanel controlPanel = new javax.swing.JPanel();
        javax.swing.JButton launchButton = new javax.swing.JButton();
        javax.swing.JButton cancelButton = new javax.swing.JButton();
        javax.swing.JPanel componentSettingsPanel = new javax.swing.JPanel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        componentAddressField = new javax.swing.JTextField();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        option1Field = new javax.swing.JTextField();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        option2Field = new javax.swing.JTextField();
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        option3Field = new javax.swing.JTextField();
        javax.swing.JPanel descriptionPanel = new javax.swing.JPanel();
        javax.swing.JScrollPane jScrollPane2 = new javax.swing.JScrollPane();
        componentDescriptionArea = new javax.swing.JTextArea();
        javax.swing.JLabel jLabel5 = new javax.swing.JLabel();
        componentTypeComboBox = new javax.swing.JComboBox<>();
        javax.swing.JLabel jLabel6 = new javax.swing.JLabel();
        option4Field = new javax.swing.JTextField();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        componentList = new javax.swing.JList<>();
        javax.swing.JMenuBar jMenuBar1 = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem resetConfigItem = new javax.swing.JMenuItem();
        javax.swing.JPopupMenu.Separator jSeparator1 = new javax.swing.JPopupMenu.Separator();
        javax.swing.JMenuItem importConfigItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem saveConfigItem = new javax.swing.JMenuItem();
        javax.swing.JPopupMenu.Separator jSeparator2 = new javax.swing.JPopupMenu.Separator();
        javax.swing.JMenuItem exitItem = new javax.swing.JMenuItem();
        javax.swing.JMenu componentMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem jMenuItem1 = new javax.swing.JMenuItem();
        javax.swing.JMenuItem jMenuItem2 = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();

        configChooser.setDialogTitle("Open config...");
        configChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(java.io.File f){
                if(f.getName().equals("ocemu.cfg")){
                    return true;
                }
                return false;
            }

            @Override
            public String getDescription(){
                return "OCEmu config file ('ocemu.cfg')";
            }
        }
    );
    configChooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_AND_DIRECTORIES);

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("Configure OCEmu");
    setResizable(false);

    launchButton.setText("Launch");
    launchButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            launchButtonActionPerformed(evt);
        }
    });

    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            cancelButtonActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout controlPanelLayout = new javax.swing.GroupLayout(controlPanel);
    controlPanel.setLayout(controlPanelLayout);
    controlPanelLayout.setHorizontalGroup(
        controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, controlPanelLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(cancelButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(launchButton)
            .addContainerGap())
    );
    controlPanelLayout.setVerticalGroup(
        controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, controlPanelLayout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(launchButton)
                .addComponent(cancelButton))
            .addContainerGap())
    );

    jLabel1.setText("Component's address:");

    componentAddressField.setEditable(false);

    jLabel2.setText("Option 1");

    jLabel3.setText("Option 2");

    jLabel4.setText("Option 3");

    descriptionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Description", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 10))); // NOI18N

    jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    componentDescriptionArea.setEditable(false);
    componentDescriptionArea.setColumns(20);
    componentDescriptionArea.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
    componentDescriptionArea.setRows(5);
    componentDescriptionArea.setWrapStyleWord(true);
    jScrollPane2.setViewportView(componentDescriptionArea);

    javax.swing.GroupLayout descriptionPanelLayout = new javax.swing.GroupLayout(descriptionPanel);
    descriptionPanel.setLayout(descriptionPanelLayout);
    descriptionPanelLayout.setHorizontalGroup(
        descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane2)
    );
    descriptionPanelLayout.setVerticalGroup(
        descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(descriptionPanelLayout.createSequentialGroup()
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, Short.MAX_VALUE))
    );

    jLabel5.setText("Component Type:");

    componentTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "computer", "cpu", "eeprom", "filesystem", "gpu", "internet", "keyboard", "modem", "ocemu", "screen", " " }));
    componentTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            componentTypeComboBoxActionPerformed(evt);
        }
    });

    jLabel6.setText("Option 4");

    javax.swing.GroupLayout componentSettingsPanelLayout = new javax.swing.GroupLayout(componentSettingsPanel);
    componentSettingsPanel.setLayout(componentSettingsPanelLayout);
    componentSettingsPanelLayout.setHorizontalGroup(
        componentSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(componentSettingsPanelLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(componentSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(descriptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, componentSettingsPanelLayout.createSequentialGroup()
                    .addGroup(componentSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2)
                        .addComponent(jLabel5)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4)
                        .addComponent(jLabel6))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(componentSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(option3Field)
                        .addComponent(option2Field)
                        .addComponent(componentTypeComboBox, 0, 208, Short.MAX_VALUE)
                        .addComponent(option1Field)
                        .addComponent(option4Field)
                        .addComponent(componentAddressField, javax.swing.GroupLayout.Alignment.TRAILING))))
            .addContainerGap())
    );
    componentSettingsPanelLayout.setVerticalGroup(
        componentSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(componentSettingsPanelLayout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(componentSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel5)
                .addComponent(componentTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(componentSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(componentAddressField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(componentSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel2)
                .addComponent(option1Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(componentSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(option2Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel3))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(componentSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel4)
                .addComponent(option3Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(componentSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel6)
                .addComponent(option4Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(descriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );

    componentList.setModel(new javax.swing.AbstractListModel<String>() {
        String[] strings = { "gpu", "modem", "eeprom", "filesystem", "filesystem", "filesystem", "internet", "computer", "ocemu", "screen", "keyboard" };
        public int getSize() { return strings.length; }
        public String getElementAt(int i) { return strings[i]; }
    });
    componentList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    componentList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
        public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
            componentListValueChanged(evt);
        }
    });
    jScrollPane1.setViewportView(componentList);

    fileMenu.setText("File");

    resetConfigItem.setText("Reset config");
    resetConfigItem.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            resetConfigItemActionPerformed(evt);
        }
    });
    fileMenu.add(resetConfigItem);
    fileMenu.add(jSeparator1);

    importConfigItem.setText("Import from file");
    importConfigItem.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            importConfigItemActionPerformed(evt);
        }
    });
    fileMenu.add(importConfigItem);

    saveConfigItem.setText("Save to file");
    saveConfigItem.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            saveConfigItemActionPerformed(evt);
        }
    });
    fileMenu.add(saveConfigItem);
    fileMenu.add(jSeparator2);

    exitItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
    exitItem.setText("Exit");
    exitItem.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            exitItemActionPerformed(evt);
        }
    });
    fileMenu.add(exitItem);

    jMenuBar1.add(fileMenu);

    componentMenu.setText("Component");

    jMenuItem1.setText("Add");
    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenuItem1ActionPerformed(evt);
        }
    });
    componentMenu.add(jMenuItem1);

    jMenuItem2.setText("Delete selected");
    jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenuItem2ActionPerformed(evt);
        }
    });
    componentMenu.add(jMenuItem2);

    jMenuBar1.add(componentMenu);

    helpMenu.setText("Help");
    jMenuBar1.add(helpMenu);

    setJMenuBar(jMenuBar1);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(componentSettingsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addComponent(controlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(componentSettingsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(controlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    pack();
    setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void componentListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_componentListValueChanged
        int s = this.componentList.getSelectedIndex();
        if (s >= 0) {
            this.componentTypeComboBox.setSelectedIndex(componentsArray[s].getComponentType() + 1);
            this.componentAddressField.setText(componentsArray[s].getComponentAddress());
            updateFields(componentsArray[s]);
            updateComponentList();
        }
    }//GEN-LAST:event_componentListValueChanged

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void launchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_launchButtonActionPerformed
        boolean isOSInstalled = false;
        for (File file : new File("OCEmu/.machine").listFiles()) {
            for (String current : file.list()) {
                //Well, technically it allows to setup ANY OS, compatible with Lua BIOS EEPROM bootloader script
                if (current.equals("init.lua")) {
                    isOSInstalled = true;
                }
            }
        }
        if (!(isOSInstalled)) {
            installOpenOS();
        }
        try {
            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "cd OCEmu && run.bat");
            pb.redirectErrorStream(true);
            Process p = pb.start();
            java.io.BufferedReader r = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
            String outLine;
            java.util.logging.Logger.getLogger(ConfiguratorForm.class.getName()).log(java.util.logging.Level.INFO, "Starting OCEmu...");
            while (true) {
                outLine = r.readLine();
                if (outLine == null) {
                    break;
                }
                System.out.println(outLine);
            }
        } catch (java.io.IOException ex) {
            java.util.logging.Logger.getLogger(ConfiguratorForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_launchButtonActionPerformed

    private void exitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitItemActionPerformed
        this.dispose();
    }//GEN-LAST:event_exitItemActionPerformed

    private void importConfigItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importConfigItemActionPerformed
        if (this.configChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                new ConfigMaker(this.componentsArray).readConfig(this.configChooser.getSelectedFile());
            } catch (IOException ex) {
                Logger.getLogger(ConfiguratorForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            updateComponentList();
        }
    }//GEN-LAST:event_importConfigItemActionPerformed

    private void saveConfigItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveConfigItemActionPerformed
        try {
            new ConfigMaker(this.componentsArray).createConfig();
        } catch (IOException ex) {
            Logger.getLogger(ConfiguratorForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_saveConfigItemActionPerformed

    private void componentTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_componentTypeComboBoxActionPerformed
        updateComponentList();
    }//GEN-LAST:event_componentTypeComboBoxActionPerformed

    private void resetConfigItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetConfigItemActionPerformed
        try {
            new ConfigMaker(ConfigMaker.defaultComponentSet).createConfig();
            this.componentsArray = new OCEmuComponent[ConfigMaker.defaultComponentSet.length];
            System.arraycopy(ConfigMaker.defaultComponentSet, 0, this.componentsArray, 0, ConfigMaker.defaultComponentSet.length);
        } catch (IOException ex) {
            Logger.getLogger(ConfiguratorForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        updateComponentList();
    }//GEN-LAST:event_resetConfigItemActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        this.addComponent(new OCEmuComponent(0, UUIDGenerator.create(), ""));
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        if (this.componentList.getSelectedValue() != null) {
            deleteComponent(this.componentList.getSelectedIndex());
        } else {
            JOptionPane.showMessageDialog(this, "No component selected.", "Error.", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JTextField componentAddressField;
    javax.swing.JTextArea componentDescriptionArea;
    javax.swing.JList<String> componentList;
    javax.swing.JComboBox<String> componentTypeComboBox;
    javax.swing.JFileChooser configChooser;
    javax.swing.JTextField option1Field;
    javax.swing.JTextField option2Field;
    javax.swing.JTextField option3Field;
    javax.swing.JTextField option4Field;
    // End of variables declaration//GEN-END:variables
}
