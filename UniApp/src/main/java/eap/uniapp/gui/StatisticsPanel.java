package eap.uniapp.gui;

import javax.swing.*;
import java.awt.*;


public class StatisticsPanel extends JPanel{
    
    //constructor
    public StatisticsPanel(){
        
        setLayout(new BorderLayout());
        setBackground(new Color(0xd0edef)); //light blue
        
        //βόρεια ζώνη τίτλος Panel
        JLabel titleLabel = new JLabel("Statistics", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial",Font.BOLD,20));
        //προσθήκη κενού μεταξύ titleLabel και mainPanel KAI 
        //μεταξύ titleLabel και searchPanel
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        add(titleLabel,BorderLayout.NORTH); //προσθήκη στο BorderLayout βόρεια
        
        
        
        
        
    }
    
    
}
