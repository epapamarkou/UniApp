package eap.uniapp.gui;

import eap.uniapp.utils.UrlCall;
import javax.swing.*;
import java.awt.*;

public class CountryPanel extends JPanel{
    
    //δήλωση μεταβλητών κλάσης
    private final UrlCall urlCall;
    
    //constructor
    public CountryPanel(){
        
        setLayout(new BorderLayout());
        setBackground(new Color(0xd0edef)); //light blue
        
        //αρχικοποίηση urlCall
        urlCall = new UrlCall();
        
        //βόρεια ζώνη τίτλος Panel
        JLabel titleLabel = new JLabel("Select Country", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial",Font.BOLD,20));
        //προσθήκη κενού μεταξύ titleLabel και mainPanel KAI 
        //μεταξύ titleLabel και searchPanel
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        add(titleLabel,BorderLayout.NORTH); //προσθήκη στο BorderLayout βόρεια
        
        //κεντρική ζώνη πεδίο αναζήτησης και κουμπι GO
        
        
        
        
        
    }
    
    
}
