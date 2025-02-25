package eap.uniapp.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 * Κλάση δημιουργίας κουμπιών με συγκεκριμένες παραμέτρους εμφάνισης
 * Δημιουργεί κουμπιά με παραμετροποιημένα χαρακτηριστικά
 * @author 
 */
public class ButtonUtils {
    
    /**
     * Κλάση δημιουργίας κουμπιών
     * @param button_text κείμενο του κουμπιού
     * @param size διαστάσεις κουμπιού
     * @param font γραμματοσειρά κουμπιού
     * @param back_color χρώμα φόντου κουμπιού
     * @param fore_color χρώμα κειμένου κουμπιού
     * @param border_color χρώμα περιγράμματος κουμπιού
     * @return επιστρέφει το κουμπί με τις καθορισμένες παραμέτρους
     */
    public static JButton createButton(String button_text, Dimension size, Font font, 
            Color back_color, Color fore_color,Color border_color){
        
        JButton button = new JButton(button_text); //ορισμός κειμένου
        button.setPreferredSize(size); //ορισμός μεγέθους
        button.setFont(font); //ορισμός γραμματοσειράς
        button.setBackground(back_color); //ορισμός χρώματος background
        button.setForeground(fore_color); //ορισμός χρώματος κειμένου του κουμπιού
        
        //αφαίρεση εφέ γεμίσματος
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        
        //περίγραμμα χρώμα και πάχος γραμμής
        button.setBorder(BorderFactory.createLineBorder(border_color,3));
        
        return button;
    }
    
}

