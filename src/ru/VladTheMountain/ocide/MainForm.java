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
 * OCIDE is an Integrated Development Environment, made specifically for
 * creating programs and libraries in Lua 5.2 or Lua 5.3 for OpenComputers 1.7.5
 *
 * Written in Java 8 using Swing GUI Library and Apache NetBeans 12.0
 *
 * @author VladTheMountain
 */
public class MainForm extends javax.swing.JFrame {

    /* Variables declaration */
    //Directories
    private final String APP_DIRECTORY = new java.io.File("").getAbsolutePath();
    private final String PROJECTS_DIR = APP_DIRECTORY + "/projects/";
    private final String OPENOS_SDK_DIR = APP_DIRECTORY + "/SDK/OpenOS/";
    //Workspace variables
    private java.io.File ACTIVE_FILE;
    private String ACTIVE_PROJECT;
    //Project types
    private final int OPENOS_PROG = 1,
            OPENOS_LIB = 2,
            CUSTOM = 3;

    /**
     * App constructor. Used for variable initialization
     */
    public MainForm() {
        ACTIVE_PROJECT = new java.io.File(PROJECTS_DIR).list()[0];
        ACTIVE_FILE = new java.io.File(PROJECTS_DIR + ACTIVE_PROJECT + "/main.lua");
        initComponents();
        new javax.swing.Timer(300, (java.awt.event.ActionEvent e) -> {
            refreshTrees(new javax.swing.tree.DefaultMutableTreeNode(projectsTree.getModel().getRoot()), new javax.swing.tree.DefaultMutableTreeNode(fileTree.getModel().getRoot()), new javax.swing.tree.DefaultMutableTreeNode(fieldsTree.getModel().getRoot()));
            this.repaint();
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, "Repainted");
        }).start();
    }

    /**
     * Refreshes all the JTree data
     *
     * @param projectsTop 'Projects' JTree
     * @param filesTop 'Files' JTree
     * @param fieldsTop Navigator panel
     */
    private void refreshTrees(javax.swing.tree.DefaultMutableTreeNode projectsTop, javax.swing.tree.DefaultMutableTreeNode filesTop, javax.swing.tree.DefaultMutableTreeNode fieldsTop) {

        //Projects
        for (String project : new java.io.File(PROJECTS_DIR).list()) {
            javax.swing.tree.DefaultMutableTreeNode projectNode = new javax.swing.tree.DefaultMutableTreeNode(project);
            javax.swing.tree.DefaultMutableTreeNode src = new javax.swing.tree.DefaultMutableTreeNode("Source");
            javax.swing.tree.DefaultMutableTreeNode lib = new javax.swing.tree.DefaultMutableTreeNode("Libraries");
            if (new java.io.File(PROJECTS_DIR + project).isDirectory()) {
                for (java.io.File file : new java.io.File(PROJECTS_DIR + project).listFiles()) {
                    //Looking for libraries
                    if (file.isDirectory() && file.getName().equals("lib")) {
                        for (java.io.File library : file.listFiles()) {
                            //if (library.isDirectory() || library.getName().contains(".lua")){
                            lib.add(new javax.swing.tree.DefaultMutableTreeNode(library.getName()));
                        }
                    } else {
                        src.add(new javax.swing.tree.DefaultMutableTreeNode(file.getName()));
                    }
                }
            }
            projectNode.add(src);
            projectNode.add(lib);
            projectsTop.add(projectNode);
        }

        //Files
        for (String file : new java.io.File(PROJECTS_DIR + ACTIVE_PROJECT).list()) {
            javax.swing.tree.DefaultMutableTreeNode node = new javax.swing.tree.DefaultMutableTreeNode(file);
            if (new java.io.File(PROJECTS_DIR + ACTIVE_PROJECT + "/" + file).isDirectory()) {
                for (String subFile : new java.io.File(PROJECTS_DIR + ACTIVE_PROJECT + "/" + file).list()) {
                    node.add(new javax.swing.tree.DefaultMutableTreeNode(subFile));
                }
                filesTop.add(node);
            }
        }

        //Navigator
        /*try {
            java.io.FileReader fileReader = new java.io.FileReader(ACTIVE_FILE);
            java.io.BufferedReader reader = new java.io.BufferedReader(fileReader);
            String[] lines = new String[];
            int count = 0;
            while (true) {
                if (reader.readLine() != null) {
                    lines[count] = reader.readLine();
                    count++;
                } else {
                    break;
                }
            }
            String[] words = null;
            for (String line : lines) {
                words = line.split(" ");
                if ((words[0].equals("local") && words[2].equals("=")) || (words[0].equals("function") && words[2].startsWith("("))) {
                    fieldsTop.add(new javax.swing.tree.DefaultMutableTreeNode(words[0] + " " + words[1]));
                }
            }
        } catch (java.io.IOException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }*/
        java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, "Trees refreshed");
    }

    /**
     * This method exists in order to make createProject JDialog's code more
     * readable
     *
     * basically it just creates all the neccessary files and directories
     *
     * @param name Project name
     * @param type Project type
     * @param location Project location (absolute)
     * @param mainFilePath Main project file location (absolute)
     */
    private void createProjectFolder(String name, int type, String location, String mainFilePath) {
        try {
            ACTIVE_PROJECT = location + name;
            ACTIVE_FILE = new java.io.File(mainFilePath);
            new java.io.File(ACTIVE_PROJECT).mkdirs();
            ACTIVE_FILE.createNewFile();
            //Initializing OpenOS libraries
            java.io.File[] fs = {
                // lib
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/bit32.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/buffer.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/colors.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/devfs.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/event.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/filesystem.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/internet.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/io.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/keyboard.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/note.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/package.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/pipe.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/process.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/rc.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/serialization.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/sh.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/shell.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/sides.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/term.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/text.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/thread.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/transforms.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/tty.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/uuid.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/vt100.lua")),
                // lib/core
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/core/boot.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/core/cursor.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/core/device_labeling.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/core/full_buffer.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/core/full_cursor.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/core/full_event.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/core/full_filesystem.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/core/full_keyboard.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/core/full_ls.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/core/full_sh.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/core/full_shell.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/core/full_text.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/core/full_transforms.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/core/full_vt.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/core/install_basics.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/core/install_utils.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/core/lua_shell.lua")),
                // lib/tools
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/tools/programLocations.lua")),
                new java.io.File(new java.net.URI("https://github.com/MightyPirates/OpenComputers/raw/master-MC1.7.10/src/main/resources/assets/opencomputers/loot/openos/lib/tools/transfer.lua"))
            };
            java.util.List<java.io.File> files = java.util.Arrays.asList(fs);
            String path = ACTIVE_PROJECT;
            files.forEach((file) -> {
                try {
                    java.nio.file.Files.copy(file.toPath(), (new java.io.File(path + file.getName())).toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                } catch (java.io.IOException ex) {
                    java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            });
        } catch (java.net.URISyntaxException | java.io.IOException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Apache NetBeans Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        openFileDialog = new javax.swing.JFileChooser();
        cpuOrApu = new javax.swing.ButtonGroup();
        testDialog = new javax.swing.JFrame();
        blocksPanel = new javax.swing.JPanel();
        architectureLabel = new javax.swing.JLabel();
        architectureType = new javax.swing.JComboBox<>();
        screenLabel = new javax.swing.JLabel();
        screenTier = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        defaultSlot1HDDTier = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        defaultSlot2HDDTier = new javax.swing.JComboBox<>();
        defaultSlot3HDDTier = new javax.swing.JComboBox<>();
        eepromLabel = new javax.swing.JLabel();
        eepromType = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jComboBox15 = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jComboBox16 = new javax.swing.JComboBox<>();
        jComboBox17 = new javax.swing.JComboBox<>();
        jPanel10 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        rackSlot1 = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jComboBox21 = new javax.swing.JComboBox<>();
        jComboBox22 = new javax.swing.JComboBox<>();
        jComboBox23 = new javax.swing.JComboBox<>();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jCheckBox9 = new javax.swing.JCheckBox();
        jCheckBox10 = new javax.swing.JCheckBox();
        jCheckBox11 = new javax.swing.JCheckBox();
        jCheckBox12 = new javax.swing.JCheckBox();
        hologramProjector = new javax.swing.JComboBox<>();
        hasGeolyzer = new javax.swing.JCheckBox();
        floppyType = new javax.swing.JComboBox<>();
        componentsPanel = new javax.swing.JPanel();
        isCpuGpu = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        cpuLabel = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        gpuLabel = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        isAPU = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jComboBox8 = new javax.swing.JComboBox<>();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        jComboBox14 = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jComboBox18 = new javax.swing.JComboBox<>();
        jCheckBox3 = new javax.swing.JCheckBox();
        jPanel9 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jComboBox19 = new javax.swing.JComboBox<>();
        jPanel11 = new javax.swing.JPanel();
        jCheckBox14 = new javax.swing.JCheckBox();
        jComboBox24 = new javax.swing.JComboBox<>();
        jCheckBox15 = new javax.swing.JCheckBox();
        jComboBox25 = new javax.swing.JComboBox<>();
        jCheckBox16 = new javax.swing.JCheckBox();
        jComboBox26 = new javax.swing.JComboBox<>();
        jComboBox27 = new javax.swing.JComboBox<>();
        jCheckBox17 = new javax.swing.JCheckBox();
        jCheckBox18 = new javax.swing.JCheckBox();
        jComboBox28 = new javax.swing.JComboBox<>();
        buttonsPanel = new javax.swing.JPanel();
        launchButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        testResultArea = new javax.swing.JTextArea();
        createProjectDialog = new javax.swing.JDialog();
        navigationPanel = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        buttons = new javax.swing.JPanel();
        finishButton = new javax.swing.JButton();
        nextStepButton = new javax.swing.JButton();
        previousStepButton = new javax.swing.JButton();
        cancelProjectCreation = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        projectName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        projectType = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        mainFile = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        projectLocation = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        settingsEtcFrame = new javax.swing.JFrame();
        openProjectDialog = new javax.swing.JDialog();
        topMenu = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newProject = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        openProject = new javax.swing.JMenuItem();
        openFile = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        saveProject = new javax.swing.JMenuItem();
        saveFile = new javax.swing.JMenuItem();
        exportProject = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        exitIDE = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        undoItem = new javax.swing.JMenuItem();
        redoItem = new javax.swing.JMenuItem();
        viewMenu = new javax.swing.JMenu();
        viewProjects = new javax.swing.JCheckBoxMenuItem();
        viewFiles = new javax.swing.JCheckBoxMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        viewNavigator = new javax.swing.JCheckBoxMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        viewOutput = new javax.swing.JCheckBoxMenuItem();
        runMenu = new javax.swing.JMenu();
        launchBasicTest = new javax.swing.JMenuItem();
        launchCustomConfigTest = new javax.swing.JMenuItem();
        toolsMenu = new javax.swing.JMenu();
        libraries = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        modules = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        settings = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        helpItem = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        aboutItem = new javax.swing.JMenuItem();

        openFileDialog.setName("openFileDialog"); // NOI18N

        testDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        testDialog.setTitle("Emulator Control Panel");
        testDialog.setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        testDialog.setName("testDialog"); // NOI18N
        testDialog.setResizable(false);

        blocksPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Blocks", 0, 0, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        blocksPanel.setName("blocksPanel"); // NOI18N

        architectureLabel.setText("Case type");
        architectureLabel.setName("architectureLabel"); // NOI18N

        architectureType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Computer Case Tier 1", "Computer Case Tier 2", "Computer Case Tier 3", "Computer Case Creative", "Drone Case Tier 1", "Drone Case Tier 2", "Drone Case Creative", "Microcontroller Tier 1", "Microcontroller Tier 2", "Microcontroller Creative", "Server Tier 1", "Server Tier 2", "Server Tier 3", "Server Creative", "Tablet Case Tier 1", "Tablet Case Tier 2", "Tablet Case Creative" }));
        architectureType.setName("architectureType"); // NOI18N

        screenLabel.setText("Screen");
        screenLabel.setName("screenLabel"); // NOI18N

        screenTier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tier 1", "Tier 2", "Tier 3" }));
        screenTier.setName("screenTier"); // NOI18N

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Case HDD Slots", 0, 0, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel6.setName("jPanel6"); // NOI18N

        jLabel7.setText("Slot 1");
        jLabel7.setName("jLabel7"); // NOI18N

        defaultSlot1HDDTier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "HDD Tier 1", "HDD Tier 2", "HDD Tier 3" }));
        defaultSlot1HDDTier.setName("defaultSlot1HDDTier"); // NOI18N

        jLabel8.setText("Slot 2");
        jLabel8.setName("jLabel8"); // NOI18N

        jLabel9.setText("Slot 3");
        jLabel9.setName("jLabel9"); // NOI18N

        defaultSlot2HDDTier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "HDD Tier 1", "HDD Tier 2", "HDD Tier 3" }));
        defaultSlot2HDDTier.setName("defaultSlot2HDDTier"); // NOI18N

        defaultSlot3HDDTier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "HDD Tier 1", "HDD Tier 2", "HDD Tier 3" }));
        defaultSlot3HDDTier.setName("defaultSlot3HDDTier"); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(defaultSlot2HDDTier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(defaultSlot1HDDTier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(defaultSlot3HDDTier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(defaultSlot1HDDTier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(defaultSlot2HDDTier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(defaultSlot3HDDTier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        eepromLabel.setText("EEPROM");
        eepromLabel.setName("eepromLabel"); // NOI18N

        eepromType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Empty", "Lua BIOS", "Custom" }));
        eepromType.setName("eepromType"); // NOI18N

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "RAID HDD Slots", 0, 0, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel8.setToolTipText("Set all to none to disable RAID");
        jPanel8.setName("jPanel8"); // NOI18N

        jLabel12.setText("Slot 1");
        jLabel12.setName("jLabel12"); // NOI18N

        jComboBox15.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "HDD Tier 1", "HDD Tier 2", "HDD Tier 3" }));
        jComboBox15.setName("jComboBox15"); // NOI18N

        jLabel13.setText("Slot 2");
        jLabel13.setName("jLabel13"); // NOI18N

        jLabel14.setText("Slot 3");
        jLabel14.setName("jLabel14"); // NOI18N

        jComboBox16.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "HDD Tier 1", "HDD Tier 2", "HDD Tier 3" }));
        jComboBox16.setName("jComboBox16"); // NOI18N

        jComboBox17.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "HDD Tier 1", "HDD Tier 2", "HDD Tier 3" }));
        jComboBox17.setName("jComboBox17"); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jComboBox16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jComboBox17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Rack Properties", 0, 0, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel10.setName("jPanel10"); // NOI18N

        jLabel17.setText("Slot 1");
        jLabel17.setName("jLabel17"); // NOI18N

        rackSlot1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "Tier 1", "Tier 2", "Tier 3", "Creative" }));
        rackSlot1.setName("rackSlot1"); // NOI18N

        jLabel18.setText("Slot 2");
        jLabel18.setName("jLabel18"); // NOI18N

        jLabel19.setText("Slot 3");
        jLabel19.setName("jLabel19"); // NOI18N

        jLabel20.setText("Slot 4");
        jLabel20.setName("jLabel20"); // NOI18N

        jComboBox21.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "Terminal Server", "Disk Drive" }));
        jComboBox21.setName("jComboBox21"); // NOI18N

        jComboBox22.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "Terminal Server", "Disk Drive" }));
        jComboBox22.setName("jComboBox22"); // NOI18N

        jComboBox23.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "Terminal Server", "Disk Drive" }));
        jComboBox23.setName("jComboBox23"); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel18)
                        .addComponent(jLabel19))
                    .addComponent(jLabel20)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rackSlot1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox21, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox22, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox23, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(rackSlot1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jCheckBox5.setText("3D Printer");
        jCheckBox5.setName("jCheckBox5"); // NOI18N

        jCheckBox6.setText("Net Splitter");
        jCheckBox6.setName("jCheckBox6"); // NOI18N

        jCheckBox7.setText("Transposer");
        jCheckBox7.setName("jCheckBox7"); // NOI18N

        jCheckBox8.setText("Switch");
        jCheckBox8.setName("jCheckBox8"); // NOI18N

        jCheckBox9.setText("Relay");
        jCheckBox9.setName("jCheckBox9"); // NOI18N

        jCheckBox10.setText("Access Point");
        jCheckBox10.setName("jCheckBox10"); // NOI18N

        jCheckBox11.setText("Redstone I/O");
        jCheckBox11.setName("jCheckBox11"); // NOI18N

        jCheckBox12.setText("Motion Sensor");
        jCheckBox12.setName("jCheckBox12"); // NOI18N

        hologramProjector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No Hologram Projector", "Hologram Projector Tier 1 (Monochrome)", "Hologram Projector Tier 2 (Tricolor)" }));
        hologramProjector.setName("hologramProjector"); // NOI18N

        hasGeolyzer.setText("Geolyzer");
        hasGeolyzer.setName("hasGeolyzer"); // NOI18N

        floppyType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No Floppy Disk", "Floppy Disk (Empty)", "Plan9k (Operating System)", "Network (Network Stack)", "OpenOS (Operating System)", "Data Card Software", "OpenLoader (Boot Loader)", "Builder", "Generator Upgrade Software", "OPPM (Package Manager)", "Digger", "Mazer", "OpenIRC (IRC Client)" }));
        floppyType.setName("floppyType"); // NOI18N
        floppyType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                floppyTypeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout blocksPanelLayout = new javax.swing.GroupLayout(blocksPanel);
        blocksPanel.setLayout(blocksPanelLayout);
        blocksPanelLayout.setHorizontalGroup(
            blocksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(blocksPanelLayout.createSequentialGroup()
                .addGroup(blocksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(blocksPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(blocksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, blocksPanelLayout.createSequentialGroup()
                                .addGroup(blocksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(eepromLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(screenLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(architectureLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(blocksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(architectureType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(screenTier, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(eepromType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, blocksPanelLayout.createSequentialGroup()
                                .addGroup(blocksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jCheckBox6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jCheckBox7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jCheckBox8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jCheckBox9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jCheckBox10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jCheckBox11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jCheckBox5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jCheckBox12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(hasGeolyzer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(blocksPanelLayout.createSequentialGroup()
                                .addGroup(blocksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(hologramProjector, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(floppyType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        blocksPanelLayout.setVerticalGroup(
            blocksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(blocksPanelLayout.createSequentialGroup()
                .addGroup(blocksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(architectureLabel)
                    .addComponent(architectureType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(blocksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(screenLabel)
                    .addComponent(screenTier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(blocksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(eepromLabel)
                    .addComponent(eepromType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(blocksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(blocksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(blocksPanelLayout.createSequentialGroup()
                        .addComponent(hasGeolyzer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox11)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hologramProjector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(floppyType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        componentsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Components", 0, 0, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        componentsPanel.setName("componentsPanel"); // NOI18N

        cpuOrApu.add(isCpuGpu);
        isCpuGpu.setSelected(true);
        isCpuGpu.setText("CPU/GPU");
        isCpuGpu.setName("isCpuGpu"); // NOI18N
        isCpuGpu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isCpuGpuActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder(0));
        jPanel3.setName("jPanel3"); // NOI18N

        cpuLabel.setText("CPU");
        cpuLabel.setName("cpuLabel"); // NOI18N

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tier 1", "Tier 2", "Tier 3" }));
        jComboBox1.setName("jComboBox1"); // NOI18N

        gpuLabel.setText("GPU");
        gpuLabel.setName("gpuLabel"); // NOI18N

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "Tier 1", "Tier 2", "Tier 3" }));
        jComboBox3.setName("jComboBox3"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(gpuLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(cpuLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cpuLabel)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gpuLabel)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder(0));
        jPanel4.setName("jPanel4"); // NOI18N

        jLabel2.setText("APU");
        jLabel2.setName("jLabel2"); // NOI18N

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tier 2", "Tier 3", "Creative" }));
        jComboBox5.setEnabled(false);
        jComboBox5.setName("jComboBox5"); // NOI18N
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cpuOrApu.add(isAPU);
        isAPU.setText("APU");
        isAPU.setName("isAPU"); // NOI18N
        isAPU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isAPUActionPerformed(evt);
            }
        });

        jLabel3.setText("Data Card");
        jLabel3.setName("jLabel3"); // NOI18N

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "Tier 1", "Tier 2", "Tier 3" }));
        jComboBox6.setName("jComboBox6"); // NOI18N

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setName("jPanel5"); // NOI18N

        jLabel5.setText("Memory Slot 1");
        jLabel5.setName("jLabel5"); // NOI18N

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tier 1", "Tier 1.5", "Tier 2", "Tier 2.5", "Tier 3", "Tier 3.5" }));
        jComboBox7.setName("jComboBox7"); // NOI18N

        jLabel6.setText("Memory Slot 2");
        jLabel6.setName("jLabel6"); // NOI18N

        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "Tier 1", "Tier 1.5", "Tier 2", "Tier 2.5", "Tier 3", "Tier 3.5" }));
        jComboBox8.setName("jComboBox8"); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jCheckBox1.setText("Internet Card");
        jCheckBox1.setName("jCheckBox1"); // NOI18N

        jCheckBox2.setText("Linked Card");
        jCheckBox2.setName("jCheckBox2"); // NOI18N

        jLabel11.setText("Wireless Network Card");
        jLabel11.setName("jLabel11"); // NOI18N

        jComboBox14.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "Tier 1", "Tier 2" }));
        jComboBox14.setName("jComboBox14"); // NOI18N

        jLabel15.setText("Redstone Card");
        jLabel15.setName("jLabel15"); // NOI18N

        jComboBox18.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "Tier 1", "Tier 2" }));
        jComboBox18.setName("jComboBox18"); // NOI18N

        jCheckBox3.setText("Network Card");
        jCheckBox3.setName("jCheckBox3"); // NOI18N

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Server", 0, 0, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel9.setName("jPanel9"); // NOI18N

        jLabel16.setText("Component Bus");
        jLabel16.setName("jLabel16"); // NOI18N

        jComboBox19.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "Tier 1 (8)", "Tier 2 (12)", "Tier 3 (16)", "Creative (1024)" }));
        jComboBox19.setName("jComboBox19"); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(jComboBox19, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jComboBox19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Upgrades", 0, 0, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel11.setName("jPanel11"); // NOI18N

        jCheckBox14.setText("Angel");
        jCheckBox14.setName("jCheckBox14"); // NOI18N

        jComboBox24.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Battery (None)", "Battery (Tier 1)", "Battery (Tier 2)", "Battery (Tier 3)" }));
        jComboBox24.setName("jComboBox24"); // NOI18N

        jCheckBox15.setText("Chunkloader");
        jCheckBox15.setName("jCheckBox15"); // NOI18N

        jComboBox25.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Card Container (None)", "Card Container (Tier 1)", "Card Container (Tier 2)", "Card Container (Tier 3)" }));
        jComboBox25.setName("jComboBox25"); // NOI18N

        jCheckBox16.setText("Crafting");
        jCheckBox16.setName("jCheckBox16"); // NOI18N

        jComboBox26.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Upgrade Container (None)", "Upgrade Container (Tier 1)", "Upgrade Container (Tier 2)", "Upgrade Container (Tier 3)" }));
        jComboBox26.setName("jComboBox26"); // NOI18N

        jComboBox27.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Database (None, 0 entries)", "Database (Tier 1, 9 entries)", "Database (Tier 2, 25 entries)", "Database (Tier 3, 81 entries)" }));
        jComboBox27.setName("jComboBox27"); // NOI18N

        jCheckBox17.setText("Experience");
        jCheckBox17.setName("jCheckBox17"); // NOI18N

        jCheckBox18.setText("Generator");
        jCheckBox18.setName("jCheckBox18"); // NOI18N

        jComboBox28.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hover (None, default)", "Hover (Tier 1, 64 blocks)", "Hover (Tier 2, 256 blocks)" }));
        jComboBox28.setName("jComboBox28"); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jCheckBox18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox27, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox25, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox24, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox26, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox28, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox14)
                    .addComponent(jComboBox24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox16)
                    .addComponent(jComboBox26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox18)
                    .addComponent(jComboBox28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout componentsPanelLayout = new javax.swing.GroupLayout(componentsPanel);
        componentsPanel.setLayout(componentsPanelLayout);
        componentsPanelLayout.setHorizontalGroup(
            componentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(componentsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(componentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, componentsPanelLayout.createSequentialGroup()
                        .addGroup(componentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(isCpuGpu))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(componentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(isAPU)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(componentsPanelLayout.createSequentialGroup()
                        .addGroup(componentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel15)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(componentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(componentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBox3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        componentsPanelLayout.setVerticalGroup(
            componentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(componentsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(componentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(isCpuGpu)
                    .addComponent(isAPU))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(componentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(componentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(componentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox2)
                    .addComponent(jLabel11)
                    .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(componentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jCheckBox3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        buttonsPanel.setName("buttonsPanel"); // NOI18N

        launchButton.setText("Launch!");
        launchButton.setName("launchButton"); // NOI18N
        launchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                launchButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.setName("cancelButton"); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jScrollPane7.setName("jScrollPane7"); // NOI18N

        testResultArea.setColumns(20);
        testResultArea.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        testResultArea.setRows(5);
        testResultArea.setText("Awaiting launch...");
        testResultArea.setName("testResultArea"); // NOI18N
        jScrollPane7.setViewportView(testResultArea);

        javax.swing.GroupLayout buttonsPanelLayout = new javax.swing.GroupLayout(buttonsPanel);
        buttonsPanel.setLayout(buttonsPanelLayout);
        buttonsPanelLayout.setHorizontalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7)
                .addGap(18, 18, 18)
                .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(launchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        buttonsPanelLayout.setVerticalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(buttonsPanelLayout.createSequentialGroup()
                        .addComponent(launchButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cancelButton)))
                .addContainerGap())
        );

        javax.swing.GroupLayout testDialogLayout = new javax.swing.GroupLayout(testDialog.getContentPane());
        testDialog.getContentPane().setLayout(testDialogLayout);
        testDialogLayout.setHorizontalGroup(
            testDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(testDialogLayout.createSequentialGroup()
                .addComponent(blocksPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(componentsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(buttonsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        testDialogLayout.setVerticalGroup(
            testDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(testDialogLayout.createSequentialGroup()
                .addGroup(testDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(componentsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(blocksPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        testDialog.pack();

        createProjectDialog.setTitle("Creating a new project...");
        createProjectDialog.setBounds(new java.awt.Rectangle(0, 0, 600, 300));
        createProjectDialog.setName("createProjectDialog"); // NOI18N
        createProjectDialog.setResizable(false);

        navigationPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        navigationPanel.setName("navigationPanel"); // NOI18N

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel22.setText("OpenComputers IDE");
        jLabel22.setToolTipText("");
        jLabel22.setName("jLabel22"); // NOI18N

        jLabel24.setText("Created by VladG24");
        jLabel24.setName("jLabel24"); // NOI18N

        jLabel25.setText("Version 1.0.0");
        jLabel25.setName("jLabel25"); // NOI18N

        javax.swing.GroupLayout navigationPanelLayout = new javax.swing.GroupLayout(navigationPanel);
        navigationPanel.setLayout(navigationPanelLayout);
        navigationPanelLayout.setHorizontalGroup(
            navigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navigationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(navigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addGroup(navigationPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(navigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(navigationPanelLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel25))
                            .addComponent(jLabel24))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        navigationPanelLayout.setVerticalGroup(
            navigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navigationPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel25)
                .addGap(79, 79, 79))
        );

        buttons.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        buttons.setName("buttons"); // NOI18N

        finishButton.setText("Finish");
        finishButton.setName("finishButton"); // NOI18N
        finishButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finishButtonActionPerformed(evt);
            }
        });

        nextStepButton.setText("Next >>");
        nextStepButton.setEnabled(false);
        nextStepButton.setName("nextStepButton"); // NOI18N

        previousStepButton.setText("<< Previous");
        previousStepButton.setEnabled(false);
        previousStepButton.setName("previousStepButton"); // NOI18N
        previousStepButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousStepButtonActionPerformed(evt);
            }
        });

        cancelProjectCreation.setText("Cancel");
        cancelProjectCreation.setName("cancelProjectCreation"); // NOI18N
        cancelProjectCreation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelProjectCreationActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonsLayout = new javax.swing.GroupLayout(buttons);
        buttons.setLayout(buttonsLayout);
        buttonsLayout.setHorizontalGroup(
            buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelProjectCreation)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(previousStepButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nextStepButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(finishButton)
                .addContainerGap())
        );
        buttonsLayout.setVerticalGroup(
            buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(finishButton)
                    .addComponent(nextStepButton)
                    .addComponent(previousStepButton)
                    .addComponent(cancelProjectCreation))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        mainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Creating a project", 0, 0, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        mainPanel.setName("mainPanel"); // NOI18N

        jLabel1.setText("Project Name");
        jLabel1.setName("jLabel1"); // NOI18N

        projectName.setText("Example Project");
        projectName.setName("projectName"); // NOI18N
        projectName.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                projectNameCaretUpdate(evt);
            }
        });

        jLabel4.setText("Project Type");
        jLabel4.setName("jLabel4"); // NOI18N

        projectType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lua OpenComputers program", "Lua OpenComputers library", "Lua OpenComputers custom" }));
        projectType.setName("projectType"); // NOI18N

        jLabel10.setText("Main File");
        jLabel10.setName("jLabel10"); // NOI18N

        mainFile.setEditable(false);
        mainFile.setText(new java.io.File(PROJECTS_DIR).getPath() + "\\" + projectName.getText() + "\\main.lua");
            mainFile.setName("mainFile"); // NOI18N

            jLabel21.setText("Project Location");
            jLabel21.setName("jLabel21"); // NOI18N

            projectLocation.setText(new java.io.File(PROJECTS_DIR).getPath() + "\\" + projectName.getText());
                projectLocation.setName("projectLocation"); // NOI18N
                projectLocation.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        projectLocationActionPerformed(evt);
                    }
                });

                jSeparator8.setName("jSeparator8"); // NOI18N

                javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
                mainPanel.setLayout(mainPanelLayout);
                mainPanelLayout.setHorizontalGroup(
                    mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator8)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(18, 18, 18)
                                        .addComponent(mainFile, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel21))
                                        .addGap(18, 18, 18)
                                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(projectName)
                                            .addComponent(projectType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(projectLocation, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE))))
                                .addGap(0, 0, Short.MAX_VALUE))))
                );
                mainPanelLayout.setVerticalGroup(
                    mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(projectName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(projectType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(projectLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(mainFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(65, Short.MAX_VALUE))
                );

                javax.swing.GroupLayout createProjectDialogLayout = new javax.swing.GroupLayout(createProjectDialog.getContentPane());
                createProjectDialog.getContentPane().setLayout(createProjectDialogLayout);
                createProjectDialogLayout.setHorizontalGroup(
                    createProjectDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(createProjectDialogLayout.createSequentialGroup()
                        .addComponent(navigationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(buttons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                createProjectDialogLayout.setVerticalGroup(
                    createProjectDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(createProjectDialogLayout.createSequentialGroup()
                        .addGroup(createProjectDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(navigationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                );

                createProjectDialog.pack();

                settingsEtcFrame.setName("settingsEtcFrame"); // NOI18N

                javax.swing.GroupLayout settingsEtcFrameLayout = new javax.swing.GroupLayout(settingsEtcFrame.getContentPane());
                settingsEtcFrame.getContentPane().setLayout(settingsEtcFrameLayout);
                settingsEtcFrameLayout.setHorizontalGroup(
                    settingsEtcFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGap(0, 400, Short.MAX_VALUE)
                );
                settingsEtcFrameLayout.setVerticalGroup(
                    settingsEtcFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGap(0, 300, Short.MAX_VALUE)
                );

                openProjectDialog.setName("openProjectDialog"); // NOI18N

                javax.swing.GroupLayout openProjectDialogLayout = new javax.swing.GroupLayout(openProjectDialog.getContentPane());
                openProjectDialog.getContentPane().setLayout(openProjectDialogLayout);
                openProjectDialogLayout.setHorizontalGroup(
                    openProjectDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGap(0, 400, Short.MAX_VALUE)
                );
                openProjectDialogLayout.setVerticalGroup(
                    openProjectDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGap(0, 300, Short.MAX_VALUE)
                );

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                setTitle("OpenComputers IDE 1.0");
                setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
                setLocation(new java.awt.Point(0, 0));
                setSize(getPreferredSize());

                topMenu.setName("topMenu"); // NOI18N

                fileMenu.setText("File");
                fileMenu.setName("fileMenu"); // NOI18N

                newProject.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
                newProject.setText("New Project...");
                newProject.setName("newProject"); // NOI18N
                newProject.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        newProjectActionPerformed(evt);
                    }
                });
                fileMenu.add(newProject);

                jSeparator1.setName("jSeparator1"); // NOI18N
                fileMenu.add(jSeparator1);

                openProject.setText("Open Project...");
                openProject.setName("openProject"); // NOI18N
                openProject.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        openProjectActionPerformed(evt);
                    }
                });
                fileMenu.add(openProject);

                openFile.setText("Open File...");
                openFile.setName("openFile"); // NOI18N
                fileMenu.add(openFile);

                jSeparator2.setName("jSeparator2"); // NOI18N
                fileMenu.add(jSeparator2);

                saveProject.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
                saveProject.setText("Save Project");
                saveProject.setName("saveProject"); // NOI18N
                fileMenu.add(saveProject);

                saveFile.setText("Save File As...");
                saveFile.setName("saveFile"); // NOI18N
                fileMenu.add(saveFile);

                exportProject.setText("Export Project...");
                exportProject.setName("exportProject"); // NOI18N
                fileMenu.add(exportProject);

                jSeparator5.setName("jSeparator5"); // NOI18N
                fileMenu.add(jSeparator5);

                exitIDE.setText("Exit");
                exitIDE.setName("exitIDE"); // NOI18N
                fileMenu.add(exitIDE);

                topMenu.add(fileMenu);

                editMenu.setText("Edit");
                editMenu.setName("editMenu"); // NOI18N

                undoItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_DOWN_MASK));
                undoItem.setText("Undo");
                undoItem.setName("undoItem"); // NOI18N
                editMenu.add(undoItem);

                redoItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_DOWN_MASK));
                redoItem.setText("Redo");
                redoItem.setName("redoItem"); // NOI18N
                editMenu.add(redoItem);

                topMenu.add(editMenu);

                viewMenu.setText("View");
                viewMenu.setName("viewMenu"); // NOI18N

                viewProjects.setSelected(true);
                viewProjects.setText("Projects");
                viewProjects.setName("viewProjects"); // NOI18N
                viewMenu.add(viewProjects);

                viewFiles.setSelected(true);
                viewFiles.setText("Files");
                viewFiles.setName("viewFiles"); // NOI18N
                viewMenu.add(viewFiles);

                jSeparator6.setName("jSeparator6"); // NOI18N
                viewMenu.add(jSeparator6);

                viewNavigator.setSelected(true);
                viewNavigator.setText("Navigator");
                viewNavigator.setName("viewNavigator"); // NOI18N
                viewMenu.add(viewNavigator);

                jSeparator7.setName("jSeparator7"); // NOI18N
                viewMenu.add(jSeparator7);

                viewOutput.setSelected(true);
                viewOutput.setText("Output");
                viewOutput.setName("viewOutput"); // NOI18N
                viewMenu.add(viewOutput);

                topMenu.add(viewMenu);

                runMenu.setText("Test");
                runMenu.setName("runMenu"); // NOI18N

                launchBasicTest.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0));
                launchBasicTest.setText("Test on basic configurations");
                launchBasicTest.setName("launchBasicTest"); // NOI18N
                launchBasicTest.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        launchBasicTestActionPerformed(evt);
                    }
                });
                runMenu.add(launchBasicTest);

                launchCustomConfigTest.setText("Test on custom configuration...");
                launchCustomConfigTest.setName("launchCustomConfigTest"); // NOI18N
                runMenu.add(launchCustomConfigTest);

                topMenu.add(runMenu);

                toolsMenu.setText("Tools");
                toolsMenu.setName("toolsMenu"); // NOI18N

                libraries.setText("Libraries");
                libraries.setName("libraries"); // NOI18N
                toolsMenu.add(libraries);

                jSeparator3.setName("jSeparator3"); // NOI18N
                toolsMenu.add(jSeparator3);

                modules.setText("Modules");
                modules.setName("modules"); // NOI18N
                toolsMenu.add(modules);

                jSeparator4.setName("jSeparator4"); // NOI18N
                toolsMenu.add(jSeparator4);

                settings.setText("Settings");
                settings.setName("settings"); // NOI18N
                settings.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        settingsActionPerformed(evt);
                    }
                });
                toolsMenu.add(settings);

                topMenu.add(toolsMenu);

                helpMenu.setText("Help");
                helpMenu.setName("helpMenu"); // NOI18N

                helpItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
                helpItem.setText("Help");
                helpItem.setName("helpItem"); // NOI18N
                helpItem.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        helpItemActionPerformed(evt);
                    }
                });
                helpMenu.add(helpItem);

                jSeparator9.setName("jSeparator9"); // NOI18N
                helpMenu.add(jSeparator9);

                aboutItem.setText("About");
                aboutItem.setName("aboutItem"); // NOI18N
                aboutItem.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        aboutItemActionPerformed(evt);
                    }
                });
                helpMenu.add(aboutItem);

                topMenu.add(helpMenu);

                setJMenuBar(topMenu);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGap(0, 339, Short.MAX_VALUE)
                );
                layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGap(0, 578, Short.MAX_VALUE)
                );

                getAccessibleContext().setAccessibleDescription("Yee");

                pack();
            }// </editor-fold>//GEN-END:initComponents

    private void newProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newProjectActionPerformed
        createProjectDialog.setVisible(true);
    }//GEN-LAST:event_newProjectActionPerformed

    private void launchBasicTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_launchBasicTestActionPerformed
        testDialog.setVisible(true);
    }//GEN-LAST:event_launchBasicTestActionPerformed

    private void launchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_launchButtonActionPerformed

    }//GEN-LAST:event_launchButtonActionPerformed

    private void floppyTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_floppyTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_floppyTypeActionPerformed

    private void isAPUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isAPUActionPerformed
        if (isAPU.isSelected()) {
            jComboBox1.setEnabled(false);
            jComboBox3.setEnabled(false);
            jComboBox5.setEnabled(true);
        }
    }//GEN-LAST:event_isAPUActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        testDialog.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void isCpuGpuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isCpuGpuActionPerformed
        if (isCpuGpu.isSelected()) {
            jComboBox1.setEnabled(true);
            jComboBox3.setEnabled(true);
            jComboBox5.setEnabled(false);
        }
    }//GEN-LAST:event_isCpuGpuActionPerformed

    private void helpItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpItemActionPerformed

    }//GEN-LAST:event_helpItemActionPerformed

    private void aboutItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutItemActionPerformed
        settingsEtcFrame.setVisible(true);
    }//GEN-LAST:event_aboutItemActionPerformed

    private void settingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsActionPerformed

    }//GEN-LAST:event_settingsActionPerformed

    private void projectNameCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_projectNameCaretUpdate
        projectLocation.setText(new java.io.File(PROJECTS_DIR).getPath() + "/" + projectName.getText());
        mainFile.setText(new java.io.File(PROJECTS_DIR).getPath() + "/" + projectName.getText() + "/main.lua");
    }//GEN-LAST:event_projectNameCaretUpdate

    private void openProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openProjectActionPerformed
        openProjectDialog.setVisible(true);
    }//GEN-LAST:event_openProjectActionPerformed

    private void projectLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_projectLocationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_projectLocationActionPerformed

    private void finishButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finishButtonActionPerformed
        java.io.File projectMainFile = new java.io.File(mainFile.getText());
        try {
            new java.io.File(projectLocation.getText()).mkdirs();
            projectMainFile.createNewFile();
        } catch (java.io.IOException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        createProjectDialog.dispose();
    }//GEN-LAST:event_finishButtonActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void cancelProjectCreationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelProjectCreationActionPerformed
        createProjectDialog.dispose();
    }//GEN-LAST:event_cancelProjectCreationActionPerformed

    private void previousStepButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousStepButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_previousStepButtonActionPerformed

    /**
     * Main method. The program's starting point
     *
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //L&F setting
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex
            );
        }
        //
        java.awt.EventQueue.invokeLater(() -> {
            new MainForm().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JMenuItem aboutItem;
    javax.swing.JLabel architectureLabel;
    javax.swing.JComboBox<String> architectureType;
    javax.swing.JPanel blocksPanel;
    javax.swing.JPanel buttons;
    javax.swing.JPanel buttonsPanel;
    javax.swing.JButton cancelButton;
    javax.swing.JButton cancelProjectCreation;
    javax.swing.JPanel componentsPanel;
    javax.swing.JLabel cpuLabel;
    javax.swing.ButtonGroup cpuOrApu;
    javax.swing.JDialog createProjectDialog;
    javax.swing.JComboBox<String> defaultSlot1HDDTier;
    javax.swing.JComboBox<String> defaultSlot2HDDTier;
    javax.swing.JComboBox<String> defaultSlot3HDDTier;
    javax.swing.JMenu editMenu;
    javax.swing.JLabel eepromLabel;
    javax.swing.JComboBox<String> eepromType;
    javax.swing.JMenuItem exitIDE;
    javax.swing.JMenuItem exportProject;
    javax.swing.JMenu fileMenu;
    javax.swing.JButton finishButton;
    javax.swing.JComboBox<String> floppyType;
    javax.swing.JLabel gpuLabel;
    javax.swing.JCheckBox hasGeolyzer;
    javax.swing.JMenuItem helpItem;
    javax.swing.JMenu helpMenu;
    javax.swing.JComboBox<String> hologramProjector;
    javax.swing.JRadioButton isAPU;
    javax.swing.JRadioButton isCpuGpu;
    javax.swing.JCheckBox jCheckBox1;
    javax.swing.JCheckBox jCheckBox10;
    javax.swing.JCheckBox jCheckBox11;
    javax.swing.JCheckBox jCheckBox12;
    javax.swing.JCheckBox jCheckBox14;
    javax.swing.JCheckBox jCheckBox15;
    javax.swing.JCheckBox jCheckBox16;
    javax.swing.JCheckBox jCheckBox17;
    javax.swing.JCheckBox jCheckBox18;
    javax.swing.JCheckBox jCheckBox2;
    javax.swing.JCheckBox jCheckBox3;
    javax.swing.JCheckBox jCheckBox5;
    javax.swing.JCheckBox jCheckBox6;
    javax.swing.JCheckBox jCheckBox7;
    javax.swing.JCheckBox jCheckBox8;
    javax.swing.JCheckBox jCheckBox9;
    javax.swing.JComboBox<String> jComboBox1;
    javax.swing.JComboBox<String> jComboBox14;
    javax.swing.JComboBox<String> jComboBox15;
    javax.swing.JComboBox<String> jComboBox16;
    javax.swing.JComboBox<String> jComboBox17;
    javax.swing.JComboBox<String> jComboBox18;
    javax.swing.JComboBox<String> jComboBox19;
    javax.swing.JComboBox<String> jComboBox21;
    javax.swing.JComboBox<String> jComboBox22;
    javax.swing.JComboBox<String> jComboBox23;
    javax.swing.JComboBox<String> jComboBox24;
    javax.swing.JComboBox<String> jComboBox25;
    javax.swing.JComboBox<String> jComboBox26;
    javax.swing.JComboBox<String> jComboBox27;
    javax.swing.JComboBox<String> jComboBox28;
    javax.swing.JComboBox<String> jComboBox3;
    javax.swing.JComboBox<String> jComboBox5;
    javax.swing.JComboBox<String> jComboBox6;
    javax.swing.JComboBox<String> jComboBox7;
    javax.swing.JComboBox<String> jComboBox8;
    javax.swing.JLabel jLabel1;
    javax.swing.JLabel jLabel10;
    javax.swing.JLabel jLabel11;
    javax.swing.JLabel jLabel12;
    javax.swing.JLabel jLabel13;
    javax.swing.JLabel jLabel14;
    javax.swing.JLabel jLabel15;
    javax.swing.JLabel jLabel16;
    javax.swing.JLabel jLabel17;
    javax.swing.JLabel jLabel18;
    javax.swing.JLabel jLabel19;
    javax.swing.JLabel jLabel2;
    javax.swing.JLabel jLabel20;
    javax.swing.JLabel jLabel21;
    javax.swing.JLabel jLabel22;
    javax.swing.JLabel jLabel24;
    javax.swing.JLabel jLabel25;
    javax.swing.JLabel jLabel3;
    javax.swing.JLabel jLabel4;
    javax.swing.JLabel jLabel5;
    javax.swing.JLabel jLabel6;
    javax.swing.JLabel jLabel7;
    javax.swing.JLabel jLabel8;
    javax.swing.JLabel jLabel9;
    javax.swing.JPanel jPanel10;
    javax.swing.JPanel jPanel11;
    javax.swing.JPanel jPanel3;
    javax.swing.JPanel jPanel4;
    javax.swing.JPanel jPanel5;
    javax.swing.JPanel jPanel6;
    javax.swing.JPanel jPanel8;
    javax.swing.JPanel jPanel9;
    javax.swing.JScrollPane jScrollPane7;
    javax.swing.JPopupMenu.Separator jSeparator1;
    javax.swing.JPopupMenu.Separator jSeparator2;
    javax.swing.JPopupMenu.Separator jSeparator3;
    javax.swing.JPopupMenu.Separator jSeparator4;
    javax.swing.JPopupMenu.Separator jSeparator5;
    javax.swing.JPopupMenu.Separator jSeparator6;
    javax.swing.JPopupMenu.Separator jSeparator7;
    javax.swing.JSeparator jSeparator8;
    javax.swing.JPopupMenu.Separator jSeparator9;
    javax.swing.JMenuItem launchBasicTest;
    javax.swing.JButton launchButton;
    javax.swing.JMenuItem launchCustomConfigTest;
    javax.swing.JMenuItem libraries;
    javax.swing.JTextField mainFile;
    javax.swing.JPanel mainPanel;
    javax.swing.JMenuItem modules;
    javax.swing.JPanel navigationPanel;
    javax.swing.JMenuItem newProject;
    javax.swing.JButton nextStepButton;
    javax.swing.JMenuItem openFile;
    javax.swing.JFileChooser openFileDialog;
    javax.swing.JMenuItem openProject;
    javax.swing.JDialog openProjectDialog;
    javax.swing.JButton previousStepButton;
    javax.swing.JTextField projectLocation;
    javax.swing.JTextField projectName;
    javax.swing.JComboBox<String> projectType;
    javax.swing.JComboBox<String> rackSlot1;
    javax.swing.JMenuItem redoItem;
    javax.swing.JMenu runMenu;
    javax.swing.JMenuItem saveFile;
    javax.swing.JMenuItem saveProject;
    javax.swing.JLabel screenLabel;
    javax.swing.JComboBox<String> screenTier;
    javax.swing.JMenuItem settings;
    javax.swing.JFrame settingsEtcFrame;
    javax.swing.JFrame testDialog;
    javax.swing.JTextArea testResultArea;
    javax.swing.JMenu toolsMenu;
    javax.swing.JMenuBar topMenu;
    javax.swing.JMenuItem undoItem;
    javax.swing.JCheckBoxMenuItem viewFiles;
    javax.swing.JMenu viewMenu;
    javax.swing.JCheckBoxMenuItem viewNavigator;
    javax.swing.JCheckBoxMenuItem viewOutput;
    javax.swing.JCheckBoxMenuItem viewProjects;
    // End of variables declaration//GEN-END:variables

    /* End of program's code */
}
