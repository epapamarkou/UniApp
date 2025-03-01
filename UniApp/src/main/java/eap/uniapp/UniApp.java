package eap.uniapp;

import eap.uniapp.db.UniversityJpaController;
import eap.uniapp.gui.MainFrame;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.SwingUtilities;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

/**
 * <p>
 * Η κλάση {@code UniApp} αποτελεί το κύριο σημείο εκκίνησης για την
 * εκτέλεση της εφαρμογής. Φροντίζει για την αρχικοποίηση της βάσης
 * δεδομένων (εφόσον δεν υπάρχει), την προετοιμασία των JPA Controllers
 * και την εκκίνηση του γραφικού περιβάλλοντος.
 * </p>
 */
public class UniApp {

    /**
     * Στατικό {@link EntityManagerFactory} που χρησιμοποιείται για τη διαχείριση
     * των οντοτήτων και την αλληλεπίδραση με τη βάση δεδομένων.
     */
    public static EntityManagerFactory emf;

    /**
     * Στατικός {@link UniversityJpaController} που παρέχει μεθόδους διαχείρισης
     * για τις οντότητες {@code University}.
     */
    public static UniversityJpaController controller;

    /**
     * Ο προεπιλεγμένος (default) constructor της κλάσης {@code UniApp}.
     * <p>
     * Ο constructor είναι private καθώς η κλάση περιέχει μόνο static μεθόδους και δεν προορίζεται
     * για δημιουργία αντικειμένων.
     * </p>
     */
    private UniApp() {
        // Δεν επιτρέπεται η δημιουργία αντικειμένων από αυτή την κλάση.
    }

    /**
     * <p>
     * Η κύρια μέθοδος {@code main}, από την οποία ξεκινά η εκτέλεση της εφαρμογής.
     * Αρχικοποιεί τη βάση δεδομένων, δημιουργεί το {@link EntityManagerFactory},
     * εκκινεί το {@link UniversityJpaController} και ανοίγει το γραφικό περιβάλλον
     * του χρήστη.
     * </p>
     *
     * @param args πίνακας συμβολοσειρών που μπορεί να περιέχει ορίσματα γραμμής
     *             εντολών (δεν χρησιμοποιείται εδώ).
     */
    public static void main(String[] args) {
        System.out.println("Starting UniApp...");

        // Εμφάνιση του τρέχοντος φακέλου εργασίας (μόνο για λόγους εντοπισμού προβλημάτων)
        System.out.println("Working Directory: " + System.getProperty("user.dir"));

        // Αρχικοποίηση βάσης δεδομένων (δημιουργία αν δεν υπάρχει) και αποσύνδεση
        initializeDatabaseConnection();

        // Δημιουργία EntityManagerFactory και UniversityJpaController
        emf = Persistence.createEntityManagerFactory("eap_UniApp_jar_1.0-SNAPSHOTPU");
        controller = new UniversityJpaController(emf);

        // Εκκίνηση του γραφικού περιβάλλοντος της εφαρμογής
        launchGUI();

        System.out.println("UniApp started...");
    }

    /**
     * <p>
     * Εκκινεί το γραφικό περιβάλλον της εφαρμογής μέσα από το
     * {@link SwingUtilities#invokeLater(Runnable)}. Η κλήση της
     * {@code MainFrame::new} δημιουργεί το κύριο παράθυρο της εφαρμογής.
     * </p>
     */
    private static void launchGUI() {
        SwingUtilities.invokeLater(MainFrame::new);
    }

    /**
     * <p>
     * Αρχικοποιεί τη σύνδεση με τη βάση δεδομένων, διαγράφοντας πρώτα το αρχείο
     * κλειδώματος (εφόσον υπάρχει) και δημιουργώντας τη βάση (πίνακες) αν δεν έχει
     * ήδη δημιουργηθεί. Εφόσον ολοκληρώσει τη διαδικασία, αποσυνδέεται από τη
     * βάση δεδομένων.
     * </p>
     */
    private static void initializeDatabaseConnection() {
        deleteLockFile();
        Connection connection = connect();

        if (connection != null) {
            createDatabase(connection);
            disconnect(connection);
        } else {
            System.out.println("Database connection failed.");
        }
    }

    /**
     * <p>
     * Δημιουργεί σύνδεση προς τη βάση δεδομένων Derby, η οποία ορίζεται
     * από το connection string. Σε περίπτωση που η βάση δεν υπάρχει,
     * δημιουργείται αυτόματα.
     * </p>
     *
     * @return Ένα αντικείμενο {@link Connection} εάν η σύνδεση είναι επιτυχής,
     *         αλλιώς {@code null}.
     */
    public static Connection connect() {
        String connectionString = "jdbc:derby:db/university;create=true";
        try {
            System.out.println("CONNECTING to database...");
            Connection connection = DriverManager.getConnection(connectionString);
            return connection;
        } catch (SQLException ex) {
            System.out.println("Connection error: " + ex.getLocalizedMessage());
        }
        return null;
    }

    /**
     * <p>
     * Κλείνει τη σύνδεση με τη βάση δεδομένων, αν αυτή είναι ανοιχτή.
     * Εκτυπώνει μήνυμα επιτυχίας ή σφάλματος.
     * </p>
     *
     * @param connection το αντικείμενο {@link Connection} που θα κλείσει.
     */
    public static void disconnect(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("DISCONNECTED from Database");
            }
        } catch (SQLException ex) {
            System.out.println("Error closing connection: " + ex.getLocalizedMessage());
        }
    }

    /**
     * <p>
     * Δημιουργεί τον πίνακα {@code University} στη βάση δεδομένων, εάν αυτός
     * δεν υπάρχει. Αν ο πίνακας υπάρχει ήδη, εμφανίζεται σχετικό μήνυμα για
     * αποφυγή εξαίρεσης.
     * </p>
     *
     * @param connection η ενεργή σύνδεση {@link Connection} για την εκτέλεση
     *                   των εντολών SQL δημιουργίας.
     */
    public static void createDatabase(Connection connection) {
        final String createUniversity = """
                CREATE TABLE University (
                  name          varchar(255) NOT NULL, 
                  stateProvince varchar(50), 
                  alphaTwoCode  varchar(10) NOT NULL, 
                  country       varchar(50) NOT NULL, 
                  webPages      varchar(255) NOT NULL, 
                  domains       varchar(255) NOT NULL, 
                  searches      integer, 
                  comments      varchar(500), 
                  description   varchar(500), 
                  contact       varchar(500), 
                  PRIMARY KEY (name))
                """;

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createUniversity);
            System.out.println("Table 'University' created.");
        } catch (SQLException ex) {
            if (ex.getSQLState().equals("X0Y32")) {
                System.out.println("Table 'University' already exists.");
            } else {
                System.out.println("Error creating table: " + ex.getLocalizedMessage());
            }
        }
    }

    /**
     * <p>
     * Διαγράφει το αρχείο κλειδώματος (lock file) που χρησιμοποιεί η βάση Derby
     * για να δηλώσει ότι βρίσκεται σε χρήση. Εάν το αρχείο υπάρχει, το
     * διαγράφει· αλλιώς εμφανίζεται σχετικό μήνυμα ότι δεν βρέθηκε αρχείο
     * κλειδώματος.
     * </p>
     */
    public static void deleteLockFile() {
        String DB_LOCK_FILE_PATH = "db/university/db.lck";
        Path lockFilePath = Paths.get(DB_LOCK_FILE_PATH);

        if (Files.exists(lockFilePath)) {
            try {
                Files.delete(lockFilePath);
                System.out.println("Lock file deleted successfully.");
            } catch (IOException ex) {
                System.out.println("Error deleting lock file: " + ex.getMessage());
            }
        } else {
            System.out.println("No lock file found.");
        }
    }

    /**
     * <p>
     * Διαγράφει τον πίνακα {@code University} από τη βάση δεδομένων, εφόσον υπάρχει.
     * Χρησιμοποιείται κυρίως για δοκιμές, όταν θέλουμε να δημιουργήσουμε τον πίνακα
     * από την αρχή.
     * </p>
     */
    public static void dropTable() {
        Connection connection = connect();

        if (connection != null) {
            final String dropUniversity = "DROP TABLE University";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(dropUniversity);
                System.out.println("Table 'University' dropped.");
            } catch (SQLException ex) {
                if (ex.getSQLState().equals("42Y07")) {
                    System.out.println("Table 'University' does not exist.");
                } else {
                    System.out.println("Error dropping table: " + ex.getLocalizedMessage());
                }
            } finally {
                disconnect(connection);
            }
        }
    }
}
