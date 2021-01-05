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
package ru.VladTheMountain.oclide.editor;

/**
 *
 * @author VladTheMountain
 */
public class MainFrame extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        initComponents();
        this.openFile(new java.io.File("projects/Calculator/main.lua"));
    }

    /**
     * Creates a new file and opens it in a new {@link RSyntaxTextArea}
     *
     * @param file A file to save contents to
     */
    private void newFile(java.io.File file) {
        //file.mkdirs();
        try {
            file.createNewFile();
        } catch (java.io.IOException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        openFile(file); //:D
    }

    /**
     * Reads the contents of {@code file} into a new {@link RSyntaxTextArea} in
     * a new tab
     *
     * @param file the {@link File}, which contents are to read
     */
    private void openFile(java.io.File file) {
        if (!(file.exists())) {
            javax.swing.JOptionPane.showMessageDialog(this, "The requested file " + file.getAbsolutePath() + " does not exist.", "File does not exist", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        String fileContent = "";
        try {
            fileContent = new String(java.nio.file.Files.readAllBytes(file.toPath()));
        } catch (java.io.IOException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        org.fife.ui.rsyntaxtextarea.RSyntaxTextArea newFile = new org.fife.ui.rsyntaxtextarea.RSyntaxTextArea(fileContent);
        newFile.setSyntaxEditingStyle(org.fife.ui.rsyntaxtextarea.SyntaxConstants.SYNTAX_STYLE_LUA);
        newFile.setCodeFoldingEnabled(true);
        newFile.addCaretListener((javax.swing.event.CaretEvent e) -> {
            newFile.getAccessibleContext().setAccessibleDescription(newFile.getText());
        });
        newFile.getAccessibleContext().setAccessibleDescription(newFile.getText());
        org.fife.ui.rtextarea.RTextScrollPane sp = new org.fife.ui.rtextarea.RTextScrollPane(newFile);
        sp.setName(file.getAbsolutePath());
        this.editorTabs.add(file.getName(), sp);
        this.editorTabs.setSelectedIndex(this.editorTabs.getTabCount() - 1);
    }

    /**
     * Saves {@link RSyntaxTextArea} contents to the file
     *
     * @param f The {@link File} to save current {@link RSyntaxTextArea}
     * contents to
     */
    void saveFile(java.io.File f) {
        if (!(f.exists())) {
            f.mkdirs();
            try {
                f.createNewFile();
            } catch (java.io.IOException ex) {
                java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
        try {
            java.nio.file.Files.write(f.toPath(), this.editorTabs.getComponentAt(this.editorTabs.getSelectedIndex()).getAccessibleContext().getAccessibleChild(0).getAccessibleContext().getAccessibleChild(0).getAccessibleContext().getAccessibleDescription().getBytes());
        } catch (java.io.IOException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates a new project and adds it to the projects' {@link JTree}
     *
     * @param name Project's name
     * @param projectDir Path to project's files
     */
    public static void createProject(String name, String projectDir) {
        java.io.File proj = new java.io.File(projectDir);
        if (!(proj.exists())) {
            proj.mkdirs();
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Such directory already exists", "Wrong project name", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        updateProjectsTree();
    }

    /**
     * Refreshes the {@code projectsTree} {@link JTree}
     */
    static void updateProjectsTree() {
        //Tree values
        javax.swing.tree.DefaultMutableTreeNode rootNode = new javax.swing.tree.DefaultMutableTreeNode("Projects");
        javax.swing.tree.DefaultTreeModel projTreeModel = new javax.swing.tree.DefaultTreeModel(rootNode);
        //Files' bullsh*t
        java.io.File projectsDir = new java.io.File("projects");
        if (projectsDir.exists()) {
            recursivelyAddFiles(projectsDir, rootNode);
        }
        //Refreshing the JTree itself
        projectsTree.setModel(projTreeModel);
    }

    /**
     * Recursively gets all files in the project folder
     *
     * @param file Project folder as a {@link File}
     * @param n Project's tree node
     */
    static void recursivelyAddFiles(java.io.File file, javax.swing.tree.DefaultMutableTreeNode n) {
        java.io.File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (java.io.File f : files) {
            javax.swing.tree.DefaultMutableTreeNode cNode = new javax.swing.tree.DefaultMutableTreeNode(f.getName());
            n.add(cNode);
            if (f.isDirectory()) {
                recursivelyAddFiles(f, cNode);
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

        fileManagementPopup = new javax.swing.JPopupMenu();
        popupSaveFile = new javax.swing.JMenuItem();
        popupCloseFile = new javax.swing.JMenuItem();
        projectManagementPopup = new javax.swing.JPopupMenu();
        newMenu = new javax.swing.JMenu();
        addFileMenuItem = new javax.swing.JMenuItem();
        addFolderMenuItem = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        runInOCEmu = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        renameMenuItem = new javax.swing.JMenuItem();
        moveMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        propertiesMenuItem = new javax.swing.JMenuItem();
        projectChooser = new javax.swing.JFileChooser();
        jSplitPane1 = new javax.swing.JSplitPane();
        projectsScroll = new javax.swing.JScrollPane();
        projectsTree = new javax.swing.JTree();
        editorTabs = new javax.swing.JTabbedPane();
        projectToolbar = new javax.swing.JToolBar();
        newProjectButton = new javax.swing.JButton();
        openProjectButton = new javax.swing.JButton();
        addFileButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        deleteProjectButton = new javax.swing.JButton();
        undoRedoToolbar = new javax.swing.JToolBar();
        undoButton = new javax.swing.JButton();
        redoButton = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        runOCEmuButton = new javax.swing.JButton();
        runOcelotButton = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        createProject = new javax.swing.JMenuItem();
        openProject = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        createFile = new javax.swing.JMenuItem();
        openFile = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        save = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        deleteProject = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        exit = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        undo = new javax.swing.JMenuItem();
        redo = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        cut = new javax.swing.JMenuItem();
        copy = new javax.swing.JMenuItem();
        paste = new javax.swing.JMenuItem();
        delete = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        find = new javax.swing.JMenuItem();
        runMenu = new javax.swing.JMenu();
        ocemu = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        ocelotD = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        settings = new javax.swing.JMenuItem();

        popupSaveFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/assets/icons/save_icon&16.png"))); // NOI18N
        popupSaveFile.setText("Save current file");
        popupSaveFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupSaveFileActionPerformed(evt);
            }
        });
        fileManagementPopup.add(popupSaveFile);

        popupCloseFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/assets/icons/doc_delete_icon&16.png"))); // NOI18N
        popupCloseFile.setText("Close current file");
        popupCloseFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupCloseFileActionPerformed(evt);
            }
        });
        fileManagementPopup.add(popupCloseFile);

        newMenu.setText("Add...");
        newMenu.setToolTipText("");

        addFileMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/assets/icons/document_icon&16.png"))); // NOI18N
        addFileMenuItem.setText("File");
        addFileMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFileMenuItemActionPerformed(evt);
            }
        });
        newMenu.add(addFileMenuItem);

        addFolderMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/assets/icons/folder_icon&16.png"))); // NOI18N
        addFolderMenuItem.setText("Folder");
        addFolderMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFolderMenuItemActionPerformed(evt);
            }
        });
        newMenu.add(addFolderMenuItem);

        projectManagementPopup.add(newMenu);
        projectManagementPopup.add(jSeparator7);

        runInOCEmu.setText("Launch OCEmu");
        runInOCEmu.setToolTipText("");
        runInOCEmu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runInOCEmuActionPerformed(evt);
            }
        });
        projectManagementPopup.add(runInOCEmu);
        projectManagementPopup.add(jSeparator8);

        renameMenuItem.setText("Rename");
        renameMenuItem.setToolTipText("");
        renameMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                renameMenuItemActionPerformed(evt);
            }
        });
        projectManagementPopup.add(renameMenuItem);

        moveMenuItem.setText("Move");
        moveMenuItem.setToolTipText("");
        moveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveMenuItemActionPerformed(evt);
            }
        });
        projectManagementPopup.add(moveMenuItem);

        deleteMenuItem.setText("Delete");
        deleteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMenuItemActionPerformed(evt);
            }
        });
        projectManagementPopup.add(deleteMenuItem);
        projectManagementPopup.add(jSeparator9);

        propertiesMenuItem.setText("Properties");
        propertiesMenuItem.setToolTipText("");
        propertiesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                propertiesMenuItemActionPerformed(evt);
            }
        });
        projectManagementPopup.add(propertiesMenuItem);

        projectChooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home")));
        projectChooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("OCLIDE - OpenComputers Lua Integrated Development Environment (indev build, commit 111)");
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Projects");
        projectsTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        projectsTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                projectsTreeMouseClicked(evt);
            }
        });
        projectsTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                projectsTreeValueChanged(evt);
            }
        });
        projectsScroll.setViewportView(projectsTree);
        updateProjectsTree();

        jSplitPane1.setLeftComponent(projectsScroll);

        editorTabs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editorTabsMouseClicked(evt);
            }
        });
        jSplitPane1.setRightComponent(editorTabs);

        projectToolbar.setFloatable(false);
        projectToolbar.setRollover(true);

        newProjectButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/assets/icons/folder_plus_icon&24.png"))); // NOI18N
        newProjectButton.setToolTipText("Create a new project");
        newProjectButton.setFocusable(false);
        newProjectButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        newProjectButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        newProjectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newProjectButtonActionPerformed(evt);
            }
        });
        projectToolbar.add(newProjectButton);

        openProjectButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/assets/icons/folder_open_icon&24.png"))); // NOI18N
        openProjectButton.setToolTipText("Open project");
        openProjectButton.setFocusable(false);
        openProjectButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        openProjectButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        openProjectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openProjectButtonActionPerformed(evt);
            }
        });
        projectToolbar.add(openProjectButton);

        addFileButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/assets/icons/doc_plus_icon&24.png"))); // NOI18N
        addFileButton.setToolTipText("Add a file");
        addFileButton.setFocusable(false);
        addFileButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addFileButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFileButtonActionPerformed(evt);
            }
        });
        projectToolbar.add(addFileButton);

        saveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/assets/icons/save_icon&24.png"))); // NOI18N
        saveButton.setToolTipText("Save current file");
        saveButton.setFocusable(false);
        saveButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        projectToolbar.add(saveButton);

        deleteProjectButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/assets/icons/folder_delete_icon&24.png"))); // NOI18N
        deleteProjectButton.setToolTipText("Delete project");
        deleteProjectButton.setFocusable(false);
        deleteProjectButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deleteProjectButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        deleteProjectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteProjectButtonActionPerformed(evt);
            }
        });
        projectToolbar.add(deleteProjectButton);

        undoRedoToolbar.setFloatable(false);
        undoRedoToolbar.setRollover(true);

        undoButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/assets/icons/undo_icon&24.png"))); // NOI18N
        undoButton.setToolTipText("Undo");
        undoButton.setFocusable(false);
        undoButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        undoButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        undoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoButtonActionPerformed(evt);
            }
        });
        undoRedoToolbar.add(undoButton);

        redoButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/assets/icons/redo_icon&24.png"))); // NOI18N
        redoButton.setToolTipText("Redo");
        redoButton.setFocusable(false);
        redoButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        redoButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        redoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redoButtonActionPerformed(evt);
            }
        });
        undoRedoToolbar.add(redoButton);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        runOCEmuButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/assets/icons/app_window_shell&24.png"))); // NOI18N
        runOCEmuButton.setToolTipText("Launch OCEmu");
        runOCEmuButton.setFocusable(false);
        runOCEmuButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        runOCEmuButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        runOCEmuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runOCEmuButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(runOCEmuButton);

        runOcelotButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/assets/icons/app_window_black&24.png"))); // NOI18N
        runOcelotButton.setToolTipText("Launch Ocelot Desktop");
        runOcelotButton.setFocusable(false);
        runOcelotButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        runOcelotButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        runOcelotButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runOcelotButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(runOcelotButton);

        fileMenu.setText("File");

        createProject.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        createProject.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/assets/icons/folder_plus_icon&16.png"))); // NOI18N
        createProject.setText("Create project");
        createProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createProjectActionPerformed(evt);
            }
        });
        fileMenu.add(createProject);

        openProject.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openProject.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/assets/icons/folder_open_icon&16.png"))); // NOI18N
        openProject.setText("Open project");
        openProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openProjectActionPerformed(evt);
            }
        });
        fileMenu.add(openProject);
        fileMenu.add(jSeparator1);

        createFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/assets/icons/doc_plus_icon&16.png"))); // NOI18N
        createFile.setText("Create file");
        fileMenu.add(createFile);

        openFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/assets/icons/document_icon&16.png"))); // NOI18N
        openFile.setText("Open file");
        fileMenu.add(openFile);
        fileMenu.add(jSeparator10);

        save.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/assets/icons/save_icon&16.png"))); // NOI18N
        save.setText("Save");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        fileMenu.add(save);
        fileMenu.add(jSeparator6);

        deleteProject.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, java.awt.event.InputEvent.CTRL_MASK));
        deleteProject.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/assets/icons/folder_delete_icon&16.png"))); // NOI18N
        deleteProject.setText("Delete project");
        deleteProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteProjectActionPerformed(evt);
            }
        });
        fileMenu.add(deleteProject);
        fileMenu.add(jSeparator2);

        exit.setText("Exit");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });
        fileMenu.add(exit);

        menuBar.add(fileMenu);

        editMenu.setText("Edit");

        undo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        undo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/assets/icons/undo_icon&16.png"))); // NOI18N
        undo.setText("Undo");
        undo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoActionPerformed(evt);
            }
        });
        editMenu.add(undo);

        redo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        redo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/assets/icons/redo_icon&16.png"))); // NOI18N
        redo.setText("Redo");
        redo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redoActionPerformed(evt);
            }
        });
        editMenu.add(redo);
        editMenu.add(jSeparator3);

        cut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        cut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/assets/icons/clipboard_cut_icon&16.png"))); // NOI18N
        cut.setText("Cut");
        cut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutActionPerformed(evt);
            }
        });
        editMenu.add(cut);

        copy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        copy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/assets/icons/clipboard_copy_icon&16.png"))); // NOI18N
        copy.setText("Copy");
        copy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyActionPerformed(evt);
            }
        });
        editMenu.add(copy);

        paste.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        paste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/assets/icons/clipboard_past_icon&16.png"))); // NOI18N
        paste.setText("Paste");
        paste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteActionPerformed(evt);
            }
        });
        editMenu.add(paste);

        delete.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        delete.setText("Delete");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });
        editMenu.add(delete);
        editMenu.add(jSeparator4);

        find.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        find.setText("Find...");
        find.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findActionPerformed(evt);
            }
        });
        editMenu.add(find);

        menuBar.add(editMenu);

        runMenu.setText("Run");

        ocemu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/assets/icons/app_window_shell&16.png"))); // NOI18N
        ocemu.setText("Run OCEmu");
        ocemu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ocemuActionPerformed(evt);
            }
        });
        runMenu.add(ocemu);
        runMenu.add(jSeparator5);

        ocelotD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/assets/icons/app_window_black&16.png"))); // NOI18N
        ocelotD.setText("Run Ocelot");
        ocelotD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ocelotDActionPerformed(evt);
            }
        });
        runMenu.add(ocelotD);

        menuBar.add(runMenu);

        helpMenu.setText("Help");

        settings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/assets/icons/cogs_icon&16.png"))); // NOI18N
        settings.setText("Settings");
        settings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsActionPerformed(evt);
            }
        });
        helpMenu.add(settings);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(projectToolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(undoRedoToolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(undoRedoToolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(projectToolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ocemuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ocemuActionPerformed
        new ru.VladTheMountain.oclide.configurator.ocemu.ConfiguratorForm().setVisible(true);
    }//GEN-LAST:event_ocemuActionPerformed

    private void settingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsActionPerformed
        new ru.VladTheMountain.oclide.settings.SettingsFrame().setVisible(true);
    }//GEN-LAST:event_settingsActionPerformed

    private void createProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createProjectActionPerformed
        new CreateNewProjectDialog(this).setVisible(true);
    }//GEN-LAST:event_createProjectActionPerformed

    private void ocelotDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ocelotDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ocelotDActionPerformed

    private void projectsTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_projectsTreeValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_projectsTreeValueChanged

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        this.saveActionPerformed(evt);
        System.exit(0);
    }//GEN-LAST:event_exitActionPerformed

    private void deleteProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteProjectActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteProjectActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        this.saveFile(new java.io.File(this.editorTabs.getComponentAt(this.editorTabs.getSelectedIndex()).getName()));
    }//GEN-LAST:event_saveActionPerformed

    private void openProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openProjectActionPerformed
        projectChooser.showOpenDialog(this);
    }//GEN-LAST:event_openProjectActionPerformed

    private void undoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_undoActionPerformed

    private void redoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_redoActionPerformed

    private void cutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cutActionPerformed
        javax.accessibility.AccessibleContext ac = this.editorTabs.getComponentAt(this.editorTabs.getSelectedIndex()).getAccessibleContext().getAccessibleChild(0).getAccessibleContext().getAccessibleChild(0).getAccessibleContext();
        ac.getAccessibleEditableText().cut(ac.getAccessibleEditableText().getSelectionStart(), ac.getAccessibleEditableText().getSelectionEnd());
    }//GEN-LAST:event_cutActionPerformed

    private void copyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyActionPerformed
        javax.accessibility.AccessibleContext ac = this.editorTabs.getComponentAt(this.editorTabs.getSelectedIndex()).getAccessibleContext().getAccessibleChild(0).getAccessibleContext().getAccessibleChild(0).getAccessibleContext();
        ac.getAccessibleEditableText().getTextRange(ac.getAccessibleEditableText().getSelectionStart(), ac.getAccessibleEditableText().getSelectionEnd());
    }//GEN-LAST:event_copyActionPerformed

    private void pasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasteActionPerformed
        javax.accessibility.AccessibleContext ac = this.editorTabs.getComponentAt(this.editorTabs.getSelectedIndex()).getAccessibleContext().getAccessibleChild(0).getAccessibleContext().getAccessibleChild(0).getAccessibleContext();
        ac.getAccessibleEditableText().paste(ac.getAccessibleEditableText().getCaretPosition());
    }//GEN-LAST:event_pasteActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteActionPerformed

    private void findActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_findActionPerformed

    private void popupSaveFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupSaveFileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_popupSaveFileActionPerformed

    private void popupCloseFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupCloseFileActionPerformed
        this.editorTabs.remove(this.editorTabs.getSelectedIndex());
    }//GEN-LAST:event_popupCloseFileActionPerformed

    private void editorTabsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editorTabsMouseClicked
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            this.fileManagementPopup.show(this.editorTabs, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_editorTabsMouseClicked

    private void projectsTreeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_projectsTreeMouseClicked
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3 && projectsTree.getSelectionPath().getParentPath() == projectsTree.getPathForRow(0)) {
            this.projectManagementPopup.show(projectsTree, evt.getX(), evt.getY());
        } else if (projectsTree.getSelectionCount() != 0 && java.nio.file.FileSystems.getDefault().getPath("", projectsTree.getSelectionPath().toString().substring(1, projectsTree.getSelectionPath().toString().length() - 1).split(", ")).toFile().isFile() && evt.getButton() == java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount() == 2) {
            this.openFile(java.nio.file.FileSystems.getDefault().getPath("", projectsTree.getSelectionPath().toString().substring(1, projectsTree.getSelectionPath().toString().length() - 1).split(", ")).toFile());
        }
    }//GEN-LAST:event_projectsTreeMouseClicked

    private void runInOCEmuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runInOCEmuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_runInOCEmuActionPerformed

    private void newProjectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newProjectButtonActionPerformed
        new CreateNewProjectDialog(this).setVisible(true);
    }//GEN-LAST:event_newProjectButtonActionPerformed

    private void openProjectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openProjectButtonActionPerformed
        projectChooser.showOpenDialog(this);
    }//GEN-LAST:event_openProjectButtonActionPerformed

    private void addFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFileButtonActionPerformed
        this.addFileMenuItemActionPerformed(evt);
    }//GEN-LAST:event_addFileButtonActionPerformed

    private void addFileMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFileMenuItemActionPerformed
        String path = javax.swing.JOptionPane.showInputDialog(this, "Path to the file:", "Creating a new file...", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        if (path != null) {
            this.newFile(new java.io.File(path));
        }
    }//GEN-LAST:event_addFileMenuItemActionPerformed

    private void addFolderMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFolderMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addFolderMenuItemActionPerformed

    private void renameMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_renameMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_renameMenuItemActionPerformed

    private void moveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_moveMenuItemActionPerformed

    private void deleteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteMenuItemActionPerformed

    private void propertiesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_propertiesMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_propertiesMenuItemActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_saveButtonActionPerformed

    private void deleteProjectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteProjectButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteProjectButtonActionPerformed

    private void undoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_undoButtonActionPerformed

    private void redoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redoButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_redoButtonActionPerformed

    private void runOCEmuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runOCEmuButtonActionPerformed
        try {
            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "cd OCEmu && run.bat");
            pb.redirectErrorStream(true);
            Process p = pb.start();
            java.io.BufferedReader r = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
            String outLine;
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.INFO, "Starting OCEmu...");
            while (true) {
                outLine = r.readLine();
                if (outLine == null) {
                    break;
                }
                System.out.println(outLine);
            }
        } catch (java.io.IOException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_runOCEmuButtonActionPerformed

    private void runOcelotButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runOcelotButtonActionPerformed
        try {
            ProcessBuilder pb = new ProcessBuilder("java", "-jar", "Ocelot\\ocelot.jar");
            pb.redirectErrorStream(true);
            Process p = pb.start();
            java.io.BufferedReader r = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
            String outLine;
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.INFO, "Starting Ocelot Desktop...");
            while (true) {
                outLine = r.readLine();
                if (outLine == null) {
                    break;
                }
                System.out.println(outLine);
            }
        } catch (java.io.IOException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_runOcelotButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addFileButton;
    private javax.swing.JMenuItem addFileMenuItem;
    private javax.swing.JMenuItem addFolderMenuItem;
    private javax.swing.JMenuItem copy;
    private javax.swing.JMenuItem createFile;
    private javax.swing.JMenuItem createProject;
    private javax.swing.JMenuItem cut;
    private javax.swing.JMenuItem delete;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JMenuItem deleteProject;
    private javax.swing.JButton deleteProjectButton;
    private javax.swing.JMenu editMenu;
    private javax.swing.JTabbedPane editorTabs;
    private javax.swing.JMenuItem exit;
    private javax.swing.JPopupMenu fileManagementPopup;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem find;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem moveMenuItem;
    private javax.swing.JMenu newMenu;
    private javax.swing.JButton newProjectButton;
    private javax.swing.JMenuItem ocelotD;
    private javax.swing.JMenuItem ocemu;
    private javax.swing.JMenuItem openFile;
    private javax.swing.JMenuItem openProject;
    private javax.swing.JButton openProjectButton;
    private javax.swing.JMenuItem paste;
    private javax.swing.JMenuItem popupCloseFile;
    private javax.swing.JMenuItem popupSaveFile;
    private javax.swing.JFileChooser projectChooser;
    private javax.swing.JPopupMenu projectManagementPopup;
    private javax.swing.JToolBar projectToolbar;
    private javax.swing.JScrollPane projectsScroll;
    private static javax.swing.JTree projectsTree;
    private javax.swing.JMenuItem propertiesMenuItem;
    private javax.swing.JMenuItem redo;
    private javax.swing.JButton redoButton;
    private javax.swing.JMenuItem renameMenuItem;
    private javax.swing.JMenuItem runInOCEmu;
    private javax.swing.JMenu runMenu;
    private javax.swing.JButton runOCEmuButton;
    private javax.swing.JButton runOcelotButton;
    private javax.swing.JMenuItem save;
    private javax.swing.JButton saveButton;
    private javax.swing.JMenuItem settings;
    private javax.swing.JMenuItem undo;
    private javax.swing.JButton undoButton;
    private javax.swing.JToolBar undoRedoToolbar;
    // End of variables declaration//GEN-END:variables
}
