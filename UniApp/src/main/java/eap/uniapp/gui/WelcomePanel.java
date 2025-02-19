package eap.uniapp.gui;

import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends JPanel{
    
    //constructor
    public WelcomePanel(){
        
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setBackground(new Color(0xd0edef));//χρώμα background
        
        //Μηνύματα
        JLabel title = new JLabel("Welcome to UniApp");
        title.setFont(new Font("Arial",Font.BOLD,48));
        title.setForeground(new Color(0x003366));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitle = new JLabel("The world of education is... at your fingertips.");
        subtitle.setFont(new Font("Arial",Font.BOLD,20));
        subtitle.setForeground(new Color(0x003366));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //εικόνα ΕΑΠ
        JLabel imageLabel = new JLabel();
        //από τον φάκελο resources στο project
        imageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eap120x46.png")));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //προσθήκη των 3 JLabels στο BorderLayout WelcomePanel και κενών μεταξύ τους
        add(Box.createVerticalGlue());
        //add(Box.createVerticalStrut(30));
        add(title);
        add(Box.createVerticalStrut(80));
        add(subtitle);
        add(Box.createVerticalStrut(80));
        add(imageLabel);
        add(Box.createVerticalGlue());
        
    } //end constructor WelcomePanel
}//end WelcomePanel
