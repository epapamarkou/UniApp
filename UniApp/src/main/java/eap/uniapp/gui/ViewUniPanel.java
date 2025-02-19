package eap.uniapp.gui;

import javax.swing.*;
import java.awt.*;


public class ViewUniPanel extends JPanel{
    
    //constructor
    public ViewUniPanel(){
        
        setBackground(new Color(0xd0edef)); //light blue
        
        setLayout(new FlowLayout());
        JLabel viewLabel = new JLabel("View coming...");
        add(viewLabel);
        
    }
}
