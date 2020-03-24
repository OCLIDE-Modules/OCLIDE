package opencomputers.ide.main;

import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author VladG24 YT
 */

public class Window extends JFrame {
    public Window(){
        setTitle("OCIDE - an OpenComputers programming IDE");
        setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().height, Toolkit.getDefaultToolkit().getScreenSize().width);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(initPanels());
        setVisible(true);
    }
    
    private JPanel initPanels(){
        JPanel panel = new Panels(Window.HEIGHT, Window.WIDTH);
        return panel;
    }
}
