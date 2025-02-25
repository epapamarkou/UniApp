package eap.uniapp.gui;

import javax.swing.*;
import java.awt.*;


public class InfoPanel extends JPanel{
    
    //constructor
    public InfoPanel(){
        
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setBackground(new Color(0xd0edef));//χρώμα background
        
        //Μηνύματα
        JLabel title = new JLabel("UniApp");
        title.setFont(new Font("Arial",Font.BOLD,40));
        title.setForeground(new Color(0x003366));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitle1 = new JLabel("Group Project");
        subtitle1.setFont(new Font("Arial",Font.BOLD,28));
        subtitle1.setForeground(new Color(0x003366));
        subtitle1.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitle2 = new JLabel("Course: PLI-24");
        subtitle2.setFont(new Font("Arial",Font.BOLD,20));
        subtitle2.setForeground(new Color(0x003366));
        subtitle2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitle3 = new JLabel("Academic Year 2024-25");
        subtitle3.setFont(new Font("Arial",Font.BOLD,20));
        subtitle3.setForeground(new Color(0x003366));
        subtitle3.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //JPanel για product owner και Scrum Team
        JPanel teams = new JPanel();
        teams.setBackground(new Color (0xffffff));teams.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //αριστερό μέρος-Product owner
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color (0xffffff));
        
        JLabel leftLabel1 = new JLabel("Product Owner");
        leftLabel1.setFont(new Font("Arial",Font.BOLD,20));
        leftLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(leftLabel1);
        
        JLabel leftLabel2 = new JLabel("Alepis Efthymios");
        leftLabel2.setFont(new Font("Arial",Font.BOLD,16));
        leftLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(leftLabel2);
        
        //κενό JPanel
        JPanel centerPanel = new JPanel();
        centerPanel.setPreferredSize(new Dimension(100,0));
        centerPanel.setBackground(new Color (0xffffff));
        
        //δεξιό μέρος-Scrum team
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
        rightPanel.setBackground(new Color (0xffffff));
        
        JLabel rightLabel1 = new JLabel("Scrum Team");
        rightLabel1.setFont(new Font("Arial",Font.BOLD,20));
        rightLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(rightLabel1);
        
        JLabel rightLabel2 = new JLabel("Arvanitidou Maria");
        rightLabel2.setFont(new Font("Arial",Font.BOLD,16));
        rightLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(rightLabel2);
        
        JLabel rightLabel3 = new JLabel("Gerokostas Stylianos-Chrysovalantis");
        rightLabel3.setFont(new Font("Arial",Font.BOLD,16));
        rightLabel3.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(rightLabel3);
        
        JLabel rightLabel4 = new JLabel("Papamarkou Erasmia");
        rightLabel4.setFont(new Font("Arial",Font.BOLD,16));
        rightLabel4.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(rightLabel4);
        
        //προσθήκη των leftPanel και rightPanel στο teams JPanel
        teams.add(leftPanel);
        teams.add(centerPanel);
        teams.add(rightPanel);
        
        //εικόνα ΕΑΠ
        JLabel imageLabel = new JLabel();
        //από τον φάκελο resources στο project
        imageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eap120x46.png")));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        //προσθήκη των 3 JLabels στο BorderLayout WelcomePanel και κενών μεταξύ τους
        add(Box.createVerticalGlue());
        //add(Box.createVerticalStrut(30));
        add(title);
        add(Box.createVerticalStrut(30));
        add(subtitle1);
        add(subtitle2);
        add(subtitle3);
        add(Box.createVerticalStrut(30));
        
        add(teams);
        add(Box.createVerticalStrut(30));
        add(imageLabel);
        add(Box.createVerticalGlue());
        
    }// end of constructor
    
}
