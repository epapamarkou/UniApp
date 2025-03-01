package eap.uniapp.db.exceptions;

/**
 * <p>
 * Η κλάση {@code PreexistingEntityException} υποδηλώνει ότι επιχειρείται η δημιουργία
 * μιας οντότητας που ήδη υπάρχει στη βάση δεδομένων. Χρησιμοποιείται για να
 * ρίξει εξαίρεση όταν εντοπιστεί διπλοεγγραφή ή προσπάθεια εισαγωγής δεδομένων που παραβιάζουν
 * τους περιορισμούς μοναδικότητας.
 * </p>
 * 
 */
public class PreexistingEntityException extends Exception {

    /**
     * <p>
     * Δημιουργεί ένα νέο {@code PreexistingEntityException} με μήνυμα και αιτία, παρέχοντας
     * λεπτομερείς πληροφορίες για το σφάλμα που προέκυψε.
     * </p>
     *
     * @param message Το μήνυμα που εξηγεί την εξαίρεση.
     * @param cause   Η αιτία της εξαίρεσης, δηλαδή το αρχικό exception που προκάλεσε το σφάλμα.
     */
    public PreexistingEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Δημιουργεί ένα νέο {@code PreexistingEntityException} με μήνυμα που εξηγεί το σφάλμα,
     * χωρίς να αναφέρεται σε κάποια προκαλούμενη αιτία.
     * </p>
     *
     * @param message Το μήνυμα που εξηγεί την εξαίρεση.
     */
    public PreexistingEntityException(String message) {
        super(message);
    }
}
