package opencomputers.ide.main;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpinnerListModel;

/**
 * @author VladG24 YT
 */

public class Panels extends JPanel implements ActionListener {
  
    //Buttons
    private JButton File, Edit, Launch, Info;
    //Panels
    private JPanel TextTools, IconTools, Editor, Debugger, Projects;
    //Spinners
    private SpinnerListModel FileSpinner, EditSpinner, ContextMenuSpinner;
            
    public Panels(int winHeight, int winWidth) {
        //Buttons config
        //File Spinner
        File = new JButton("File");
        File.setLocation(0, 0);
        File.setActionCommand("FileSpinnerOpener");
        File.addActionListener(this);
        //Edit Spinner
        Edit = new JButton("Edit");
        Edit.setLocation(File.getWidth(), 0);
        Edit.setActionCommand("EditSpinnerOpener");
        Edit.addActionListener(this);
        //Launch button
        Launch = new JButton("Launch app");
        Launch.setLocation(File.getWidth()+Edit.getWidth(), 0);
        Launch.addActionListener(this);
        //Info button
        Info = new JButton("About");
        Info.setLocation(File.getWidth()+Edit.getWidth()+Launch.getWidth(), 0);
        Info.addActionListener(this);
        //Panels config
        //TextTools
        TextTools = new JPanel();
        TextTools.setBounds(0, 0, (winHeight/10)/5, winWidth);
        TextTools.add(File);
        TextTools.add(Edit);
        TextTools.add(Launch);
        TextTools.add(Info);
    } 
    
    public void paintComponent(Graphics gr){
        super.paintComponent(gr);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("FileSpinnerOpener")){
            FileSpinner = new SpinnerListModel();
        } else if(command.equals("EditSpinnerOpener")){
            
        } else if(command.equals("Launch app")){
            
        } else if(command.equals("About")){
            
        }
    }
}
