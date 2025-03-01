package eap.uniapp.gui;

import javax.swing.*;
import java.awt.*;

/**
 * <p>
 * Η κλάση {@code WelcomePanel} αποτελεί το πάνελ καλωσορίσματος της εφαρμογής UniApp.
 * Εμφανίζει ένα ευπρόσδεκτο μήνυμα μαζί με ένα υποτίτλο και την εικόνα του ΕΑΠ.
 * Η διάταξη υλοποιείται με χρήση {@link BoxLayout} σε κάθετη στοίχιση, ώστε τα στοιχεία
 * να κεντράρονται ομοιόμορφα στο available χώρο.
 * </p>
 * 
 */
public class WelcomePanel extends JPanel{
    
    //constructor

    /**
     * <p>
     * Ο προεπιλεγμένος constructor της κλάσης {@code WelcomePanel} δημιουργεί το UI
     * του πάνελ καλωσορίσματος. Ορίζει τη διάταξη, το background color και προσθέτει
     * τα στοιχεία όπως ο τίτλος, ο υπότιτλος και η εικόνα του ΕΑΠ, με κατάλληλα κενά
     * ανάμεσα τους για ευχάριστη εμφάνιση.
     * </p>
     */
    public WelcomePanel(){
        
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setBackground(new Color(0xd0edef));//χρώμα background
        
        // Μηνύματα
        JLabel title = new JLabel("Welcome to UniApp");
        title.setFont(new Font("Arial",Font.BOLD,48));
        title.setForeground(new Color(0x003366));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitle = new JLabel("The world of education is... at your fingertips.");
        subtitle.setFont(new Font("Arial",Font.BOLD,20));
        subtitle.setForeground(new Color(0x003366));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Εικόνα ΕΑΠ
        JLabel imageLabel = new JLabel();
        // Φόρτωση εικόνας από τον φάκελο resources στο project
        imageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eap120x46.png")));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Προσθήκη των στοιχείων στο πάνελ με οριζόντια κατανομή και κενά μεταξύ τους
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
