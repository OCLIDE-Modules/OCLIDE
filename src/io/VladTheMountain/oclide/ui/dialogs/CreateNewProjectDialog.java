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
package io.VladTheMountain.oclide.ui.dialogs;

import io.VladTheMountain.oclide.ui.frames.EditorFrame;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.FileSystems;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author VladTheMountain
 */
public class CreateNewProjectDialog extends javax.swing.JDialog {
    
    final ResourceBundle localiztionResource = ResourceBundle.getBundle("ru.VladTheMountain.oclide.resources.dialog.Dialog", Locale.getDefault());

    private static final long serialVersionUID = 1L;

    /**
     * Creates new form createNewProject
     *
     * @param parent
     */
    public CreateNewProjectDialog(java.awt.Frame parent) {
        super(parent, true);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        controlPanel = new JPanel();
        finishButton = new JButton();
        nextButton = new JButton();
        previousButton = new JButton();
        cancelButton = new JButton();
        jTabbedPane2 = new JTabbedPane();
        projectTypePanel = new JPanel();
        descriptionPanel = new JPanel();
        jScrollPane3 = new JScrollPane();
        descriptionText = new JTextArea();
        categoriesPanel = new JPanel();
        jScrollPane1 = new JScrollPane();
        categorylist = new JList<>();
        projectsPanel = new JPanel();
        jScrollPane2 = new JScrollPane();
        projectTypeList = new JList<>();
        projectPropertiesPanel = new JPanel();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        projectNameField = new JTextField();
        projectFolderField = new JTextField(new File(System.getProperty("user.dir") + FileSystems.getDefault().getSeparator() + "projects").getAbsolutePath() + FileSystems.getDefault().getSeparator() + projectNameField.getText())
        ;
        scriptSeparator = new JSeparator();
        jLabel3 = new JLabel();
        librarySeparator = new JSeparator();
        jLabel4 = new JLabel();
        libraryNameField = new JTextField();
        scriptNameField = new JTextField();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Creating a new project");
        setResizable(false);

        finishButton.setFont(new Font("Segoe UI", 0, 12)); // NOI18N
        finishButton.setText("Finish");
        finishButton.setEnabled(false);
        finishButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                finishButtonActionPerformed(evt);
            }
        });

        nextButton.setFont(new Font("Segoe UI", 0, 12)); // NOI18N
        nextButton.setText("Next >>");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        previousButton.setFont(new Font("Segoe UI", 0, 12)); // NOI18N
        previousButton.setText("<< Previous");
        previousButton.setEnabled(false);
        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                previousButtonActionPerformed(evt);
            }
        });

        cancelButton.setFont(new Font("Segoe UI", 0, 12)); // NOI18N
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
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(previousButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nextButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(finishButton)
                .addContainerGap())
        );
        controlPanelLayout.setVerticalGroup(controlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, controlPanelLayout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(controlPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(finishButton)
                    .addComponent(nextButton)
                    .addComponent(previousButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        jTabbedPane2.setTabPlacement(JTabbedPane.LEFT);
        jTabbedPane2.setEnabled(false);
        jTabbedPane2.setFont(new Font("Segoe UI", 0, 12)); // NOI18N

        descriptionPanel.setBorder(BorderFactory.createTitledBorder(null, "Description:", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, new Font("Segoe UI", 1, 12))); // NOI18N

        jScrollPane3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        descriptionText.setEditable(false);
        descriptionText.setColumns(20);
        descriptionText.setFont(new Font("Monospaced", 0, 12)); // NOI18N
        descriptionText.setRows(5);
        descriptionText.setText("Creates a new, blank project.");
        jScrollPane3.setViewportView(descriptionText);

        GroupLayout descriptionPanelLayout = new GroupLayout(descriptionPanel);
        descriptionPanel.setLayout(descriptionPanelLayout);
        descriptionPanelLayout.setHorizontalGroup(descriptionPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );
        descriptionPanelLayout.setVerticalGroup(descriptionPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        categoriesPanel.setBorder(BorderFactory.createTitledBorder(null, "Categories:", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, new Font("Segoe UI", 1, 12))); // NOI18N

        categorylist.setFont(new Font("Tahoma", 0, 12)); // NOI18N
        categorylist.setModel(new AbstractListModel<String>() {
            String[] strings = { "OpenComputers" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        categorylist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        categorylist.setSelectedIndex(0);
        jScrollPane1.setViewportView(categorylist);

        GroupLayout categoriesPanelLayout = new GroupLayout(categoriesPanel);
        categoriesPanel.setLayout(categoriesPanelLayout);
        categoriesPanelLayout.setHorizontalGroup(categoriesPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
        );
        categoriesPanelLayout.setVerticalGroup(categoriesPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
        );

        projectsPanel.setBorder(BorderFactory.createTitledBorder(null, "Projects:", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, new Font("Segoe UI", 1, 12))); // NOI18N

        projectTypeList.setFont(new Font("Tahoma", 0, 12)); // NOI18N
        projectTypeList.setModel(new AbstractListModel<String>() {
            String[] strings = { "Blank Project", "OpenOS Script", "OpenOS Library" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        projectTypeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        projectTypeList.setSelectedIndex(0);
        projectTypeList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                projectTypeListValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(projectTypeList);

        GroupLayout projectsPanelLayout = new GroupLayout(projectsPanel);
        projectsPanel.setLayout(projectsPanelLayout);
        projectsPanelLayout.setHorizontalGroup(projectsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
        );
        projectsPanelLayout.setVerticalGroup(projectsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );

        GroupLayout projectTypePanelLayout = new GroupLayout(projectTypePanel);
        projectTypePanel.setLayout(projectTypePanelLayout);
        projectTypePanelLayout.setHorizontalGroup(projectTypePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(projectTypePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(projectTypePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(descriptionPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(projectTypePanelLayout.createSequentialGroup()
                        .addComponent(categoriesPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(projectsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        projectTypePanelLayout.setVerticalGroup(projectTypePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(projectTypePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(projectTypePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(categoriesPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(projectsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descriptionPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Project type", projectTypePanel);

        jLabel1.setFont(new Font("Segoe UI", 0, 12)); // NOI18N
        jLabel1.setText("Project Name:");

        jLabel2.setFont(new Font("Segoe UI", 0, 12)); // NOI18N
        jLabel2.setText("Created folder:");

        projectNameField.setFont(new Font("Tahoma", 0, 12)); // NOI18N
        projectNameField.setText("LuaScript1");
        projectNameField.addCaretListener(new CaretListener() {
            public void caretUpdate(CaretEvent evt) {
                projectNameFieldCaretUpdate(evt);
            }
        });

        projectFolderField.setEditable(false);
        projectFolderField.setFont(new Font("Tahoma", 0, 12)); // NOI18N

        jLabel3.setFont(new Font("Segoe UI", 0, 12)); // NOI18N
        jLabel3.setText("Script name:");

        jLabel4.setFont(new Font("Segoe UI", 0, 12)); // NOI18N
        jLabel4.setText("Library name:");

        libraryNameField.setFont(new Font("Tahoma", 0, 12)); // NOI18N

        scriptNameField.setFont(new Font("Tahoma", 0, 12)); // NOI18N

        GroupLayout projectPropertiesPanelLayout = new GroupLayout(projectPropertiesPanel);
        projectPropertiesPanel.setLayout(projectPropertiesPanelLayout);
        projectPropertiesPanelLayout.setHorizontalGroup(projectPropertiesPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(projectPropertiesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(projectPropertiesPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(scriptSeparator, GroupLayout.Alignment.TRAILING)
                    .addGroup(GroupLayout.Alignment.TRAILING, projectPropertiesPanelLayout.createSequentialGroup()
                        .addGroup(projectPropertiesPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(projectPropertiesPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(projectNameField)
                            .addComponent(projectFolderField)))
                    .addComponent(librarySeparator)
                    .addGroup(projectPropertiesPanelLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(scriptNameField, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE))
                    .addGroup(projectPropertiesPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(libraryNameField, GroupLayout.PREFERRED_SIZE, 348, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        projectPropertiesPanelLayout.setVerticalGroup(projectPropertiesPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(projectPropertiesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(projectPropertiesPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(projectNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(projectPropertiesPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(projectFolderField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scriptSeparator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(projectPropertiesPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(scriptNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(librarySeparator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(projectPropertiesPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(libraryNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(210, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Project configuration", projectPropertiesPanel);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(controlPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jTabbedPane2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(controlPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void previousButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_previousButtonActionPerformed
        this.jTabbedPane2.setSelectedIndex(this.jTabbedPane2.getSelectedIndex() - 1);
        this.previousButton.setEnabled(false);
        this.nextButton.setEnabled(true);
        this.finishButton.setEnabled(false);
    }//GEN-LAST:event_previousButtonActionPerformed

    private void nextButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        this.jTabbedPane2.setSelectedIndex(this.jTabbedPane2.getSelectedIndex() + 1);
        this.previousButton.setEnabled(true);
        this.nextButton.setEnabled(false);
        this.finishButton.setEnabled(true);
        switch (this.projectTypeList.getSelectedIndex()) {
            case 0:
            default:
                this.scriptSeparator.setVisible(false);
                this.jLabel3.setVisible(false);
                this.scriptNameField.setVisible(false);
                this.librarySeparator.setVisible(false);
                this.jLabel4.setVisible(false);
                this.libraryNameField.setVisible(false);
                break;
            case 1:
                this.scriptSeparator.setVisible(true);
                this.jLabel3.setVisible(true);
                this.scriptNameField.setVisible(true);
                this.librarySeparator.setVisible(false);
                this.jLabel4.setVisible(false);
                this.libraryNameField.setVisible(false);
                break;
            case 2:
                this.scriptSeparator.setVisible(false);
                this.jLabel3.setVisible(false);
                this.scriptNameField.setVisible(false);
                this.librarySeparator.setVisible(true);
                this.jLabel4.setVisible(true);
                this.libraryNameField.setVisible(true);
                break;
        }
    }//GEN-LAST:event_nextButtonActionPerformed

    private void finishButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_finishButtonActionPerformed
        EditorFrame.createProject(this.projectNameField.getText(), this.projectFolderField.getText());
        this.dispose();
    }//GEN-LAST:event_finishButtonActionPerformed

    private void projectTypeListValueChanged(ListSelectionEvent evt) {//GEN-FIRST:event_projectTypeListValueChanged
        switch (this.projectTypeList.getSelectedIndex()) {
            case 0:
                this.descriptionText.setText("Creates a new, blank project.");
                break;
            case 1:
                this.descriptionText.setText("Creates a new project with /bin/ folder.\nAllows to customize the name of the script.");
                break;
            case 2:
                this.descriptionText.setText("Creates a new project with /lib/ folder.\nAllows to customize the name of the library.");
                break;
            default:
                Logger.getLogger(CreateNewProjectDialog.class.getName()).log(Level.SEVERE, "Something's wrong at projectTypeListValueChanged - wrong index: {0}", this.projectTypeList.getSelectedIndex());
        }
    }//GEN-LAST:event_projectTypeListValueChanged

    private void projectNameFieldCaretUpdate(CaretEvent evt) {//GEN-FIRST:event_projectNameFieldCaretUpdate
        this.projectFolderField.setText(new File("projects").getAbsolutePath() + FileSystems.getDefault().getSeparator() + projectNameField.getText());
    }//GEN-LAST:event_projectNameFieldCaretUpdate

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton cancelButton;
    private JPanel categoriesPanel;
    private JList<String> categorylist;
    private JPanel controlPanel;
    private JPanel descriptionPanel;
    private JTextArea descriptionText;
    private JButton finishButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JTabbedPane jTabbedPane2;
    private JTextField libraryNameField;
    private JSeparator librarySeparator;
    private JButton nextButton;
    private JButton previousButton;
    private JTextField projectFolderField;
    private JTextField projectNameField;
    private JPanel projectPropertiesPanel;
    private JList<String> projectTypeList;
    private JPanel projectTypePanel;
    private JPanel projectsPanel;
    private JTextField scriptNameField;
    private JSeparator scriptSeparator;
    // End of variables declaration//GEN-END:variables
}
