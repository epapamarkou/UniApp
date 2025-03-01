package eap.uniapp.db.exceptions;

/**
 * <p>
 * Η κλάση {@code NonexistentEntityException} υποδηλώνει ότι μια ζητούμενη οντότητα
 * δεν υπάρχει στη βάση δεδομένων. Χρησιμοποιείται κυρίως όταν επιχειρείται η επεξεργασία
 * ή διαγραφή μιας οντότητας που δεν έχει βρεθεί.
 * </p>
 * 
 */
public class NonexistentEntityException extends Exception {

    /**
     * <p>
     * Κατασκευάζει ένα αντικείμενο {@code NonexistentEntityException} με ένα μήνυμα
     * και μια αιτία (exception cause), που παρέχουν περισσότερες πληροφορίες για το σφάλμα.
     * </p>
     *
     * @param message Το μήνυμα που εξηγεί την εξαίρεση.
     * @param cause   Η αιτία της εξαίρεσης, δηλαδή το αρχικό exception που προκάλεσε το σφάλμα.
     */
    public NonexistentEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Κατασκευάζει ένα αντικείμενο {@code NonexistentEntityException} με ένα μήνυμα,
     * το οποίο εξηγεί το σφάλμα χωρίς να αναφέρεται σε κάποια αιτία.
     * </p>
     *
     * @param message Το μήνυμα που εξηγεί την εξαίρεση.
     */
    public NonexistentEntityException(String message) {
        super(message);
    }
}
