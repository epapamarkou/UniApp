package eap.uniapp.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 * Κλάση δημιουργίας κουμπιών με συγκεκριμένες παραμέτρους εμφάνισης.
 * Δημιουργεί κουμπιά με παραμετροποιημένα χαρακτηριστικά.
 * <p>
 * Αυτή η κλάση είναι μια utility κλάση και δεν προορίζεται να δημιουργούνται
 * αντικείμενα από αυτήν.
 * </p>
 */
public class ButtonUtils {
    
    /**
     * Ιδιωτός constructor για αποφυγή δημιουργίας αντικειμένων από την κλάση.
     */
    private ButtonUtils() {
        // Δεν επιτρέπεται η δημιουργία αντικειμένων από αυτή την κλάση.
    }
    
    /**
     * Δημιουργεί και επιστρέφει ένα κουμπί με τις καθορισμένες παραμέτρους.
     * 
     * @param button_text το κείμενο του κουμπιού
     * @param size        οι διαστάσεις του κουμπιού
     * @param font        η γραμματοσειρά του κουμπιού
     * @param back_color  το χρώμα φόντου του κουμπιού
     * @param fore_color  το χρώμα του κειμένου του κουμπιού
     * @param border_color το χρώμα του περιγράμματος του κουμπιού
     * @return το κουμπί με τις καθορισμένες παραμέτρους
     */
    public static JButton createButton(String button_text, Dimension size, Font font, 
            Color back_color, Color fore_color, Color border_color){
        
        JButton button = new JButton(button_text); // ορισμός κειμένου
        button.setPreferredSize(size); // ορισμός μεγέθους
        button.setFont(font); // ορισμός γραμματοσειράς
        button.setBackground(back_color); // ορισμός χρώματος φόντου
        button.setForeground(fore_color); // ορισμός χρώματος κειμένου
        
        // Αφαίρεση εφέ γεμίσματος
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        
        // Ορισμός περιγράμματος: χρώμα και πάχος γραμμής
        button.setBorder(BorderFactory.createLineBorder(border_color, 3));
        
        return button;
    }
    
}
