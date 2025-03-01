package eap.uniapp.db.exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Η κλάση {@code IllegalOrphanException} αντιπροσωπεύει μια εξαίρεση που ρίχνεται
 * όταν υπάρχουν "ορφανά" αντικείμενα που δεν μπορούν να διαγραφούν ή να ενημερωθούν
 * λόγω περιορισμών ακεραιότητας των δεδομένων.
 * </p>
 * 
 */
public class IllegalOrphanException extends Exception {

    /**
     * Η λίστα με τα μηνύματα που περιγράφουν τα σφάλματα που οδήγησαν στην
     * ρίψη αυτής της εξαίρεσης.
     */
    private List<String> messages;

    /**
     * <p>
     * Κατασκευάζει ένα νέο {@code IllegalOrphanException} με μια λίστα μηνυμάτων.
     * Το πρώτο μήνυμα της λίστας χρησιμοποιείται ως κύριο μήνυμα της εξαίρεσης.
     * </p>
     * 
     * @param messages Η λίστα με τα μηνύματα που εξηγούν τις παράνομες ορφές.
     */
    public IllegalOrphanException(List<String> messages) {
        super((messages != null && messages.size() > 0 ? messages.get(0) : null));
        if (messages == null) {
            this.messages = new ArrayList<String>();
        }
        else {
            this.messages = messages;
        }
    }

    /**
     * <p>
     * Επιστρέφει τη λίστα με τα μηνύματα που περιγράφουν τις παράνομες ορφές.
     * </p>
     * 
     * @return Μια λίστα από {@code String} με τα μηνύματα σφάλματος.
     */
    public List<String> getMessages() {
        return messages;
    }
}
