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

import ru.VladTheMountain.oclide.ui.emulator.OCEmuForm;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.accessibility.AccessibleContext;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.apache.commons.io.FileUtils;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.CompletionCellRenderer;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.fife.ui.rtextarea.ToolTipSupplier;
import ru.VladTheMountain.oclide.editor.OpenComputersCompletionProvider;
import ru.VladTheMountain.oclide.ui.dialog.CreateNewProjectDialog;
import ru.VladTheMountain.oclide.ui.dialog.OpenFileFileChooser;
import ru.VladTheMountain.oclide.ui.dialog.ProjectFileChooser;
import ru.VladTheMountain.oclide.util.ConsoleOutputStream;

/**
 *
 * @author VladTheMountain
 */
public class EditorFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Creates new form MainFrame
     */
    public EditorFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(EditorFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();
        System.setOut(new PrintStream(new ConsoleOutputStream(outputTextArea)));
    }

    /**
     * Creates a new file and opens it in a new {@link RSyntaxTextArea}
     *
     * @param file A file to save contents to
     */
    private void newFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(EditorFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        openFile(file); //:D
    }

    /**
     * Reads the contents of {@code file} into a new {@link RSyntaxTextArea} in
     * a new tab
     *
     * @param file the {@link File}, which contents are to read
     */
    private void openFile(File file) {
        if (!(file.exists())) {
            JOptionPane.showMessageDialog(this, "The requested file " + file.getAbsolutePath() + " does not exist.", "File does not exist", JOptionPane.ERROR_MESSAGE);
        }
        String fileContent = "";
        try {
            fileContent = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException ex) {
            Logger.getLogger(EditorFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        RSyntaxTextArea newFile = new RSyntaxTextArea(fileContent);
        newFile.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LUA);
        newFile.setCodeFoldingEnabled(true);
        newFile.addCaretListener((CaretEvent e) -> {
            newFile.getAccessibleContext().setAccessibleDescription(newFile.getText());
        });
        newFile.getAccessibleContext().setAccessibleDescription(newFile.getText());
        //autocompletion
        CompletionProvider occp = OpenComputersCompletionProvider.getProvider();
        AutoCompletion occ = new AutoCompletion(occp);
        occ.setListCellRenderer(new CompletionCellRenderer());
        occ.setAutoCompleteEnabled(true);
        occ.setAutoActivationEnabled(true);
        occ.setAutoActivationDelay(100);
        occ.setShowDescWindow(true);
        occ.setParameterAssistanceEnabled(true);
        //occ.install(newFile);
        /*UNCOMMENT BEFORE BETA RELEASE*/
        newFile.setToolTipSupplier((ToolTipSupplier) occp);
        ToolTipManager.sharedInstance().registerComponent(newFile);
        //
        RTextScrollPane sp = new RTextScrollPane(newFile);
        sp.setName(file.getAbsolutePath());
        this.editorTabs.add(file.getName(), sp);
        this.editorTabs.setSelectedIndex(this.editorTabs.getTabCount() - 1);
        updateProjectsTree();
    }

    /**
     * Saves {@link RSyntaxTextArea} contents to the file
     *
     * @param f The {@link File} to save current {@link RSyntaxTextArea}
     * contents to
     */
    private void saveFile(File f) {
        if (!(f.exists())) {
            f.mkdirs();
            try {
                f.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(EditorFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            Files.write(f.toPath(), this.editorTabs.getComponentAt(this.editorTabs.getSelectedIndex()).getAccessibleContext().getAccessibleChild(0).getAccessibleContext().getAccessibleChild(0).getAccessibleContext().getAccessibleDescription().getBytes());
        } catch (IOException ex) {
            Logger.getLogger(EditorFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        updateProjectsTree();
    }

    /**
     * Creates a new project and adds it to the projects' {@link JTree}
     *
     * @param name Project's name
     * @param projectDir Path to project's files
     */
    public static void createProject(String name, String projectDir) {
        File proj = new File(projectDir);
        if (!(proj.exists())) {
            proj.mkdirs();
        } else {
            JOptionPane.showMessageDialog(null, "Such directory already exists", "Wrong project name", JOptionPane.ERROR_MESSAGE);
        }
        updateProjectsTree();
    }

    /**
     * Refreshes the {@code projectsTree} {@link JTree}
     */
    private static void updateProjectsTree() {
        //Tree values
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Projects");
        DefaultTreeModel projTreeModel = new DefaultTreeModel(rootNode);
        //Files' bullsh*t
        File projectsDir = new File("projects");
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
    private static void recursivelyAddFiles(File file, DefaultMutableTreeNode n) {
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            DefaultMutableTreeNode cNode = new DefaultMutableTreeNode(f.getName());
            n.add(cNode);
            if (f.isDirectory()) {
                recursivelyAddFiles(f, cNode);
            }
        }
    }

    /**
     * Refreshes the {@code variableTree} {@link JTree}
     */
    private void updateVariableTree() {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(this.editorTabs.getTitleAt(this.editorTabs.getSelectedIndex()));
        DefaultTreeModel model = new DefaultTreeModel(rootNode);
        //
        
        //
        this.variableTree.setModel(model);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileManagementPopup = new JPopupMenu();
        popupSaveFile = new JMenuItem();
        popupCloseFile = new JMenuItem();
        projectManagementPopup = new JPopupMenu();
        newMenu = new JMenu();
        addFileMenuItem = new JMenuItem();
        addFolderMenuItem = new JMenuItem();
        jSeparator7 = new JPopupMenu.Separator();
        runInOCEmu = new JMenuItem();
        jSeparator8 = new JPopupMenu.Separator();
        renameMenuItem = new JMenuItem();
        deleteMenuItem = new JMenuItem();
        jSeparator9 = new JPopupMenu.Separator();
        propertiesMenuItem = new JMenuItem();
        projectChooser = new JFileChooser();
        jSplitPane1 = new JSplitPane();
        jSplitPane3 = new JSplitPane();
        editorTabs = new JTabbedPane();
        jTabbedPane1 = new JTabbedPane();
        jScrollPane2 = new JScrollPane();
        outputTextArea = new JTextArea();
        jSplitPane2 = new JSplitPane();
        jScrollPane1 = new JScrollPane();
        variableTree = new JTree();
        projectsScroll = new JScrollPane();
        projectsTree = new JTree();
        projectToolbar = new JToolBar();
        newProjectButton = new JButton();
        openProjectButton = new JButton();
        addFileButton = new JButton();
        saveButton = new JButton();
        deleteProjectButton = new JButton();
        undoRedoToolbar = new JToolBar();
        undoButton = new JButton();
        redoButton = new JButton();
        jToolBar1 = new JToolBar();
        runOCEmuButton = new JButton();
        runOcelotButton = new JButton();
        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        createProject = new JMenuItem();
        openProject = new JMenuItem();
        jSeparator1 = new JPopupMenu.Separator();
        createFile = new JMenuItem();
        openFile = new JMenuItem();
        jSeparator10 = new JPopupMenu.Separator();
        save = new JMenuItem();
        jSeparator6 = new JPopupMenu.Separator();
        deleteProject = new JMenuItem();
        jSeparator2 = new JPopupMenu.Separator();
        exit = new JMenuItem();
        editMenu = new JMenu();
        undo = new JMenuItem();
        redo = new JMenuItem();
        jSeparator3 = new JPopupMenu.Separator();
        cut = new JMenuItem();
        copy = new JMenuItem();
        paste = new JMenuItem();
        jSeparator4 = new JPopupMenu.Separator();
        find = new JMenuItem();
        runMenu = new JMenu();
        ocemu = new JMenuItem();
        jSeparator5 = new JPopupMenu.Separator();
        ocelotD = new JMenuItem();
        helpMenu = new JMenu();
        settings = new JMenuItem();

        popupSaveFile.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/save_icon&16.png"))); // NOI18N
        popupSaveFile.setText("Save current file");
        popupSaveFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                popupSaveFileActionPerformed(evt);
            }
        });
        fileManagementPopup.add(popupSaveFile);

        popupCloseFile.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/doc_delete_icon&16.png"))); // NOI18N
        popupCloseFile.setText("Close current file");
        popupCloseFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                popupCloseFileActionPerformed(evt);
            }
        });
        fileManagementPopup.add(popupCloseFile);

        newMenu.setText("Add...");
        newMenu.setToolTipText("");

        addFileMenuItem.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/document_icon&16.png"))); // NOI18N
        addFileMenuItem.setText("File");
        addFileMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addFileMenuItemActionPerformed(evt);
            }
        });
        newMenu.add(addFileMenuItem);

        addFolderMenuItem.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/folder_icon&16.png"))); // NOI18N
        addFolderMenuItem.setText("Folder");
        addFolderMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addFolderMenuItemActionPerformed(evt);
            }
        });
        newMenu.add(addFolderMenuItem);

        projectManagementPopup.add(newMenu);
        projectManagementPopup.add(jSeparator7);

        runInOCEmu.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/app_window_shell&16.png"))); // NOI18N
        runInOCEmu.setText("Launch OCEmu");
        runInOCEmu.setToolTipText("");
        runInOCEmu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                runInOCEmuActionPerformed(evt);
            }
        });
        projectManagementPopup.add(runInOCEmu);
        projectManagementPopup.add(jSeparator8);

        renameMenuItem.setText("Rename");
        renameMenuItem.setToolTipText("");
        renameMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                renameMenuItemActionPerformed(evt);
            }
        });
        projectManagementPopup.add(renameMenuItem);

        deleteMenuItem.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/folder_delete_icon&16.png"))); // NOI18N
        deleteMenuItem.setText("Delete");
        deleteMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                deleteMenuItemActionPerformed(evt);
            }
        });
        projectManagementPopup.add(deleteMenuItem);
        projectManagementPopup.add(jSeparator9);

        propertiesMenuItem.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/cogs_icon&16.png"))); // NOI18N
        propertiesMenuItem.setText("Properties");
        propertiesMenuItem.setToolTipText("");
        propertiesMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                propertiesMenuItemActionPerformed(evt);
            }
        });
        projectManagementPopup.add(propertiesMenuItem);

        projectChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        projectChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("OCLIDE - OpenComputers Lua Integrated Development Environment (v0.0.7)");
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        jSplitPane3.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPane3.setResizeWeight(1.0);

        editorTabs.setPreferredSize(getMaximumSize());
        editorTabs.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                editorTabsMouseClicked(evt);
            }
        });
        jSplitPane3.setLeftComponent(editorTabs);

        outputTextArea.setColumns(20);
        outputTextArea.setFont(new Font("Lucida Console", 0, 12)); // NOI18N
        outputTextArea.setRows(5);
        jScrollPane2.setViewportView(outputTextArea);

        jTabbedPane1.addTab("IDE Output", jScrollPane2);

        jSplitPane3.setRightComponent(jTabbedPane1);

        jSplitPane1.setRightComponent(jSplitPane3);

        jSplitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setResizeWeight(1.0);

        variableTree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode(
            this.editorTabs.getSelectedIndex()==-1 ?
            "Variables"
            : this.editorTabs.getTitleAt(this.editorTabs.getSelectedIndex()))));
jScrollPane1.setViewportView(variableTree);

jSplitPane2.setBottomComponent(jScrollPane1);

        DefaultMutableTreeNode treeNode1 = new DefaultMutableTreeNode("Projects");
projectsTree.setModel(new DefaultTreeModel(treeNode1));
projectsTree.addMouseListener(new MouseAdapter() {
    public void mouseClicked(MouseEvent evt) {
        projectsTreeMouseClicked(evt);
    }
    });
    projectsScroll.setViewportView(projectsTree);
    updateProjectsTree();

    jSplitPane2.setLeftComponent(projectsScroll);

    jSplitPane1.setLeftComponent(jSplitPane2);

    projectToolbar.setFloatable(false);
    projectToolbar.setRollover(true);

    newProjectButton.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/folder_plus_icon&24.png"))); // NOI18N
    newProjectButton.setToolTipText("Create a new project");
    newProjectButton.setFocusable(false);
    newProjectButton.setHorizontalTextPosition(SwingConstants.CENTER);
    newProjectButton.setVerticalTextPosition(SwingConstants.BOTTOM);
    newProjectButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            newProjectButtonActionPerformed(evt);
        }
    });
    projectToolbar.add(newProjectButton);

    openProjectButton.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/folder_open_icon&24.png"))); // NOI18N
    openProjectButton.setToolTipText("Open project");
    openProjectButton.setFocusable(false);
    openProjectButton.setHorizontalTextPosition(SwingConstants.CENTER);
    openProjectButton.setVerticalTextPosition(SwingConstants.BOTTOM);
    openProjectButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            openProjectButtonActionPerformed(evt);
        }
    });
    projectToolbar.add(openProjectButton);

    addFileButton.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/doc_plus_icon&24.png"))); // NOI18N
    addFileButton.setToolTipText("Add a file");
    addFileButton.setFocusable(false);
    addFileButton.setHorizontalTextPosition(SwingConstants.CENTER);
    addFileButton.setVerticalTextPosition(SwingConstants.BOTTOM);
    addFileButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            addFileButtonActionPerformed(evt);
        }
    });
    projectToolbar.add(addFileButton);

    saveButton.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/save_icon&24.png"))); // NOI18N
    saveButton.setToolTipText("Save current file");
    saveButton.setFocusable(false);
    saveButton.setHorizontalTextPosition(SwingConstants.CENTER);
    saveButton.setVerticalTextPosition(SwingConstants.BOTTOM);
    saveButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            saveButtonActionPerformed(evt);
        }
    });
    projectToolbar.add(saveButton);

    deleteProjectButton.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/folder_delete_icon&24.png"))); // NOI18N
    deleteProjectButton.setToolTipText("Delete project");
    deleteProjectButton.setFocusable(false);
    deleteProjectButton.setHorizontalTextPosition(SwingConstants.CENTER);
    deleteProjectButton.setVerticalTextPosition(SwingConstants.BOTTOM);
    deleteProjectButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            deleteProjectButtonActionPerformed(evt);
        }
    });
    projectToolbar.add(deleteProjectButton);

    undoRedoToolbar.setFloatable(false);
    undoRedoToolbar.setRollover(true);

    undoButton.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/undo_icon&24.png"))); // NOI18N
    undoButton.setToolTipText("Undo");
    undoButton.setFocusable(false);
    undoButton.setHorizontalTextPosition(SwingConstants.CENTER);
    undoButton.setVerticalTextPosition(SwingConstants.BOTTOM);
    undoRedoToolbar.add(undoButton);

    redoButton.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/redo_icon&24.png"))); // NOI18N
    redoButton.setToolTipText("Redo");
    redoButton.setFocusable(false);
    redoButton.setHorizontalTextPosition(SwingConstants.CENTER);
    redoButton.setVerticalTextPosition(SwingConstants.BOTTOM);
    undoRedoToolbar.add(redoButton);

    jToolBar1.setFloatable(false);
    jToolBar1.setRollover(true);

    runOCEmuButton.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/app_window_shell&24.png"))); // NOI18N
    runOCEmuButton.setToolTipText("Launch OCEmu");
    runOCEmuButton.setFocusable(false);
    runOCEmuButton.setHorizontalTextPosition(SwingConstants.CENTER);
    runOCEmuButton.setVerticalTextPosition(SwingConstants.BOTTOM);
    runOCEmuButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            runOCEmuButtonActionPerformed(evt);
        }
    });
    jToolBar1.add(runOCEmuButton);

    runOcelotButton.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/app_window_black&24.png"))); // NOI18N
    runOcelotButton.setToolTipText("Launch Ocelot Desktop");
    runOcelotButton.setEnabled(false);
    runOcelotButton.setFocusable(false);
    runOcelotButton.setHorizontalTextPosition(SwingConstants.CENTER);
    runOcelotButton.setVerticalTextPosition(SwingConstants.BOTTOM);
    runOcelotButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            runOcelotButtonActionPerformed(evt);
        }
    });
    jToolBar1.add(runOcelotButton);

    fileMenu.setText("File");

    createProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
    createProject.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/folder_plus_icon&16.png"))); // NOI18N
    createProject.setText("Create project");
    createProject.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            createProjectActionPerformed(evt);
        }
    });
    fileMenu.add(createProject);

    openProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
    openProject.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/folder_open_icon&16.png"))); // NOI18N
    openProject.setText("Open project");
    openProject.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            openProjectActionPerformed(evt);
        }
    });
    fileMenu.add(openProject);
    fileMenu.add(jSeparator1);

    createFile.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/doc_plus_icon&16.png"))); // NOI18N
    createFile.setText("Create file");
    createFile.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            createFileActionPerformed(evt);
        }
    });
    fileMenu.add(createFile);

    openFile.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/document_icon&16.png"))); // NOI18N
    openFile.setText("Open file");
    openFile.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            openFileActionPerformed(evt);
        }
    });
    fileMenu.add(openFile);
    fileMenu.add(jSeparator10);

    save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
    save.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/save_icon&16.png"))); // NOI18N
    save.setText("Save");
    save.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            saveActionPerformed(evt);
        }
    });
    fileMenu.add(save);
    fileMenu.add(jSeparator6);

    deleteProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, InputEvent.CTRL_MASK));
    deleteProject.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/folder_delete_icon&16.png"))); // NOI18N
    deleteProject.setText("Delete project");
    deleteProject.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            deleteProjectActionPerformed(evt);
        }
    });
    fileMenu.add(deleteProject);
    fileMenu.add(jSeparator2);

    exit.setText("Exit");
    exit.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            exitActionPerformed(evt);
        }
    });
    fileMenu.add(exit);

    menuBar.add(fileMenu);

    editMenu.setText("Edit");

    undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
    undo.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/undo_icon&16.png"))); // NOI18N
    undo.setText("Undo");
    editMenu.add(undo);

    redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
    redo.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/redo_icon&16.png"))); // NOI18N
    redo.setText("Redo");
    editMenu.add(redo);
    editMenu.add(jSeparator3);

    cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
    cut.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/clipboard_cut_icon&16.png"))); // NOI18N
    cut.setText("Cut");
    cut.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            cutActionPerformed(evt);
        }
    });
    editMenu.add(cut);

    copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
    copy.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/clipboard_copy_icon&16.png"))); // NOI18N
    copy.setText("Copy");
    copy.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            copyActionPerformed(evt);
        }
    });
    editMenu.add(copy);

    paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
    paste.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/clipboard_past_icon&16.png"))); // NOI18N
    paste.setText("Paste");
    paste.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            pasteActionPerformed(evt);
        }
    });
    editMenu.add(paste);
    editMenu.add(jSeparator4);

    find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
    find.setText("Find...");
    find.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            findActionPerformed(evt);
        }
    });
    editMenu.add(find);

    menuBar.add(editMenu);

    runMenu.setText("Run");

    ocemu.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/app_window_shell&16.png"))); // NOI18N
    ocemu.setText("Run OCEmu");
    ocemu.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            ocemuActionPerformed(evt);
        }
    });
    runMenu.add(ocemu);
    runMenu.add(jSeparator5);

    ocelotD.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/app_window_black&16.png"))); // NOI18N
    ocelotD.setText("Run Ocelot");
    ocelotD.setEnabled(false);
    ocelotD.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            ocelotDActionPerformed(evt);
        }
    });
    runMenu.add(ocelotD);

    menuBar.add(runMenu);

    helpMenu.setText("Help");

    settings.setIcon(new ImageIcon(getClass().getResource("/ru/VladTheMountain/oclide/resources/assets/icons/cogs_icon&16.png"))); // NOI18N
    settings.setText("About");
    settings.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            settingsActionPerformed(evt);
        }
    });
    helpMenu.add(settings);

    menuBar.add(helpMenu);

    setJMenuBar(menuBar);

        GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addComponent(projectToolbar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(undoRedoToolbar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jToolBar1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addContainerGap(209, Short.MAX_VALUE))
        .addComponent(jSplitPane1)
    );
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                .addComponent(undoRedoToolbar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(projectToolbar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jToolBar1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jSplitPane1, GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
            .addContainerGap())
    );

    pack();
    setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ocemuActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ocemuActionPerformed
        try {
            runOCEmu();
        } catch (IOException ex) {
            Logger.getLogger(EditorFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ocemuActionPerformed

    private void settingsActionPerformed(ActionEvent evt) {//GEN-FIRST:event_settingsActionPerformed
        new SettingsFrame().setVisible(true);
    }//GEN-LAST:event_settingsActionPerformed

    private void createProjectActionPerformed(ActionEvent evt) {//GEN-FIRST:event_createProjectActionPerformed
        new CreateNewProjectDialog(this).setVisible(true);
    }//GEN-LAST:event_createProjectActionPerformed

    private void ocelotDActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ocelotDActionPerformed
        runOcelot();
    }//GEN-LAST:event_ocelotDActionPerformed

    private void exitActionPerformed(ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        this.saveActionPerformed(evt);
        System.exit(0);
    }//GEN-LAST:event_exitActionPerformed

    private void deleteProjectActionPerformed(ActionEvent evt) {//GEN-FIRST:event_deleteProjectActionPerformed
        int result = JOptionPane.showConfirmDialog(this, "Are you really want to delete your project?", "Deleting a project", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            String projectName = String.valueOf(projectsTree.getSelectionPath().getPath()[1]);
            File f = new File("projects/" + projectName);
            if (f.delete()) {
                JOptionPane.showMessageDialog(this, "Project successfully deleted.", "Deleting a project", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_deleteProjectActionPerformed

    private void saveActionPerformed(ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        this.saveFile(new File(this.editorTabs.getComponentAt(this.editorTabs.getSelectedIndex()).getName()));
    }//GEN-LAST:event_saveActionPerformed

    private void openProjectActionPerformed(ActionEvent evt) {//GEN-FIRST:event_openProjectActionPerformed
        JFileChooser projectChoooser = new ProjectFileChooser();
        if (projectChoooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File dir = projectChoooser.getSelectedFile();
            try {
                FileUtils.copyDirectory(dir, new File("projects"));
            } catch (IOException ex) {
                Logger.getLogger(EditorFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            updateProjectsTree();
        }
    }//GEN-LAST:event_openProjectActionPerformed

    private void cutActionPerformed(ActionEvent evt) {//GEN-FIRST:event_cutActionPerformed
        AccessibleContext ac = this.editorTabs.getComponentAt(this.editorTabs.getSelectedIndex()).getAccessibleContext().getAccessibleChild(0).getAccessibleContext().getAccessibleChild(0).getAccessibleContext();
        ac.getAccessibleEditableText().cut(ac.getAccessibleEditableText().getSelectionStart(), ac.getAccessibleEditableText().getSelectionEnd());
    }//GEN-LAST:event_cutActionPerformed

    private void copyActionPerformed(ActionEvent evt) {//GEN-FIRST:event_copyActionPerformed
        AccessibleContext ac = this.editorTabs.getComponentAt(this.editorTabs.getSelectedIndex()).getAccessibleContext().getAccessibleChild(0).getAccessibleContext().getAccessibleChild(0).getAccessibleContext();
        ac.getAccessibleEditableText().getTextRange(ac.getAccessibleEditableText().getSelectionStart(), ac.getAccessibleEditableText().getSelectionEnd());
    }//GEN-LAST:event_copyActionPerformed

    private void pasteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_pasteActionPerformed
        AccessibleContext ac = this.editorTabs.getComponentAt(this.editorTabs.getSelectedIndex()).getAccessibleContext().getAccessibleChild(0).getAccessibleContext().getAccessibleChild(0).getAccessibleContext();
        ac.getAccessibleEditableText().paste(ac.getAccessibleEditableText().getCaretPosition());
    }//GEN-LAST:event_pasteActionPerformed

    private void findActionPerformed(ActionEvent evt) {//GEN-FIRST:event_findActionPerformed
        JOptionPane.showMessageDialog(this, "Unimplemented feature. Wait for next updates");
    }//GEN-LAST:event_findActionPerformed

    private void popupSaveFileActionPerformed(ActionEvent evt) {//GEN-FIRST:event_popupSaveFileActionPerformed
        this.saveActionPerformed(evt);
    }//GEN-LAST:event_popupSaveFileActionPerformed

    private void popupCloseFileActionPerformed(ActionEvent evt) {//GEN-FIRST:event_popupCloseFileActionPerformed
        this.editorTabs.remove(this.editorTabs.getSelectedIndex());
    }//GEN-LAST:event_popupCloseFileActionPerformed

    private void editorTabsMouseClicked(MouseEvent evt) {//GEN-FIRST:event_editorTabsMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON3) {
            this.fileManagementPopup.show(this.editorTabs, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_editorTabsMouseClicked

    private void projectsTreeMouseClicked(MouseEvent evt) {//GEN-FIRST:event_projectsTreeMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON3 && projectsTree.getSelectionPath().getParentPath() == projectsTree.getPathForRow(0)) {
            this.projectManagementPopup.show(projectsTree, evt.getX(), evt.getY());
        } else if (projectsTree.getSelectionCount() != 0 && FileSystems.getDefault().getPath("", projectsTree.getSelectionPath().toString().substring(1, projectsTree.getSelectionPath().toString().length() - 1).split(", ")).toFile().isFile() && evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 2) {
            this.openFile(FileSystems.getDefault().getPath("", projectsTree.getSelectionPath().toString().substring(1, projectsTree.getSelectionPath().toString().length() - 1).split(", ")).toFile());
        }
    }//GEN-LAST:event_projectsTreeMouseClicked

    private void runInOCEmuActionPerformed(ActionEvent evt) {//GEN-FIRST:event_runInOCEmuActionPerformed
        try {
            runOCEmu();
        } catch (IOException ex) {
            Logger.getLogger(EditorFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_runInOCEmuActionPerformed

    private void newProjectButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_newProjectButtonActionPerformed
        new CreateNewProjectDialog(this).setVisible(true);
    }//GEN-LAST:event_newProjectButtonActionPerformed

    private void openProjectButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_openProjectButtonActionPerformed
        this.openProjectActionPerformed(evt);
    }//GEN-LAST:event_openProjectButtonActionPerformed

    private void addFileButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_addFileButtonActionPerformed
        this.addFileMenuItemActionPerformed(evt);
    }//GEN-LAST:event_addFileButtonActionPerformed

    private void addFileMenuItemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_addFileMenuItemActionPerformed
        String name = JOptionPane.showInputDialog(this, "Name a new file:", "");
        if (name != null) {
            this.newFile(new File("projects/" + String.valueOf(projectsTree.getSelectionPath().getPath()[1]) + FileSystems.getDefault().getSeparator() + name));
        }
    }//GEN-LAST:event_addFileMenuItemActionPerformed

    private void addFolderMenuItemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_addFolderMenuItemActionPerformed
        String path = JOptionPane.showInputDialog(this, "Path to a new directory:", new File("projects/" + String.valueOf(projectsTree.getSelectionPath().getPath()[1])).getAbsolutePath());
        if (path != null) {
            new File(path).mkdirs();
        }
    }//GEN-LAST:event_addFolderMenuItemActionPerformed

    private void renameMenuItemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_renameMenuItemActionPerformed
        new File("projects/" + String.valueOf(projectsTree.getSelectionPath().getPath()[1])).renameTo(new File(JOptionPane.showInputDialog(this, "New project name:", String.valueOf(projectsTree.getSelectionPath().getPath()[1]))));
    }//GEN-LAST:event_renameMenuItemActionPerformed

    private void deleteMenuItemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_deleteMenuItemActionPerformed
        this.deleteProjectActionPerformed(evt);
    }//GEN-LAST:event_deleteMenuItemActionPerformed

    private void propertiesMenuItemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_propertiesMenuItemActionPerformed
        JOptionPane.showMessageDialog(this, "Project name: " + String.valueOf(projectsTree.getSelectionPath().getPath()[1]) + "\nProject path:" + new File("projects/" + String.valueOf(projectsTree.getSelectionPath().getPath()[1])).getAbsolutePath() + "\nCreated with " + this.getTitle(), String.valueOf(projectsTree.getSelectionPath().getPath()[1]) + " project properties", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_propertiesMenuItemActionPerformed

    private void saveButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        this.saveActionPerformed(evt);
    }//GEN-LAST:event_saveButtonActionPerformed

    private void deleteProjectButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_deleteProjectButtonActionPerformed
        this.deleteProjectActionPerformed(evt);
    }//GEN-LAST:event_deleteProjectButtonActionPerformed

    private void runOCEmuButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_runOCEmuButtonActionPerformed
        try {
            runOCEmu();
        } catch (IOException ex) {
            Logger.getLogger(EditorFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_runOCEmuButtonActionPerformed

    private void runOcelotButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_runOcelotButtonActionPerformed
        //runOcelot();
        new OcelotEmulatorFrame().setVisible(true);
    }//GEN-LAST:event_runOcelotButtonActionPerformed

    private void openFileActionPerformed(ActionEvent evt) {//GEN-FIRST:event_openFileActionPerformed
        JFileChooser fileChooser = new OpenFileFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            openFile(fileChooser.getSelectedFile());
        }
    }//GEN-LAST:event_openFileActionPerformed

    private void createFileActionPerformed(ActionEvent evt) {//GEN-FIRST:event_createFileActionPerformed
        this.addFileMenuItemActionPerformed(evt);
    }//GEN-LAST:event_createFileActionPerformed

    private void runOCEmu() throws IOException {
        if (projectsTree.getSelectionPath().getPath().length > 1 && projectsTree.getSelectionPath().getPath().length < 3) {
            if (String.valueOf(projectsTree.getSelectionPath().getPath()[1]) == null || "".equals(String.valueOf(projectsTree.getSelectionPath().getPath()[1]))) {
                JOptionPane.showMessageDialog(this, "Invalid project selection.", "Error: Can't run OCEmu", JOptionPane.ERROR_MESSAGE);
            } else {
                new OCEmuForm().setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No project chosen. Please select a project folder in the file tree and then launch OCEmu.", "Project not set", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void runOcelot() {
        try {
            ProcessBuilder pb = new ProcessBuilder("java", "-jar", "Ocelot" + FileSystems.getDefault().getSeparator() + "ocelot.jar");
            pb.redirectErrorStream(true);
            Process p = pb.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String outLine;
            Logger.getLogger(EditorFrame.class.getName()).log(Level.INFO, "Starting Ocelot Desktop...");
            while (true) {
                outLine = r.readLine();
                if (outLine == null) {
                    break;
                }
                System.out.println(outLine);
            }
        } catch (IOException ex) {
            Logger.getLogger(EditorFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton addFileButton;
    private JMenuItem addFileMenuItem;
    private JMenuItem addFolderMenuItem;
    private JMenuItem copy;
    private JMenuItem createFile;
    private JMenuItem createProject;
    private JMenuItem cut;
    private JMenuItem deleteMenuItem;
    private JMenuItem deleteProject;
    private JButton deleteProjectButton;
    private JMenu editMenu;
    private JTabbedPane editorTabs;
    private JMenuItem exit;
    private JPopupMenu fileManagementPopup;
    private JMenu fileMenu;
    private JMenuItem find;
    private JMenu helpMenu;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JPopupMenu.Separator jSeparator1;
    private JPopupMenu.Separator jSeparator10;
    private JPopupMenu.Separator jSeparator2;
    private JPopupMenu.Separator jSeparator3;
    private JPopupMenu.Separator jSeparator4;
    private JPopupMenu.Separator jSeparator5;
    private JPopupMenu.Separator jSeparator6;
    private JPopupMenu.Separator jSeparator7;
    private JPopupMenu.Separator jSeparator8;
    private JPopupMenu.Separator jSeparator9;
    private JSplitPane jSplitPane1;
    private JSplitPane jSplitPane2;
    private JSplitPane jSplitPane3;
    private JTabbedPane jTabbedPane1;
    private JToolBar jToolBar1;
    private JMenuBar menuBar;
    private JMenu newMenu;
    private JButton newProjectButton;
    private JMenuItem ocelotD;
    private JMenuItem ocemu;
    private JMenuItem openFile;
    private JMenuItem openProject;
    private JButton openProjectButton;
    public static JTextArea outputTextArea;
    private JMenuItem paste;
    private JMenuItem popupCloseFile;
    private JMenuItem popupSaveFile;
    private JFileChooser projectChooser;
    private JPopupMenu projectManagementPopup;
    private JToolBar projectToolbar;
    private JScrollPane projectsScroll;
    private static JTree projectsTree;
    private JMenuItem propertiesMenuItem;
    private JMenuItem redo;
    private JButton redoButton;
    private JMenuItem renameMenuItem;
    private JMenuItem runInOCEmu;
    private JMenu runMenu;
    private JButton runOCEmuButton;
    private JButton runOcelotButton;
    private JMenuItem save;
    private JButton saveButton;
    private JMenuItem settings;
    private JMenuItem undo;
    private JButton undoButton;
    private JToolBar undoRedoToolbar;
    private JTree variableTree;
    // End of variables declaration//GEN-END:variables
}
