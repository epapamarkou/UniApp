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

/**
 *
 * @author
 */
public class UniApp {
    
    /**
     * δημιουργία στατικού Entity Manager Factory 
     */
    public static EntityManagerFactory emf;
    
    /**
     * δημιουργία στατικού University Jpa Controller 
     */
    public static UniversityJpaController controller;
    
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Starting UniApp...");
        
        System.out.println("Working Directory: " + System.getProperty("user.dir"));//debugging
        
        emf = Persistence.createEntityManagerFactory("eap_UniApp_jar_1.0-SNAPSHOTPU");
        controller = new UniversityJpaController(emf);
        
        /**
         * άνοιγμα γραφικού περιβάλλοντος εφαρμογής
         */
        launchGUI();
        
        System.out.println("UniApp started...");
        
    } //end of Main
    
    
    /**
     * μέθοδος εκκίνησης γραφικού περιβάλλοντος στο Event Dispatched Thread
     */
    private static void launchGUI(){
        SwingUtilities.invokeLater(MainFrame::new);
    }
    
    
    /**
     * μέθοδος δημιουργίας σύνδεσης στη ΒΔ (χρησιμοποιήθηκε για την αρχική δημιουργία της ΒΔ)
     * @return 
     */
    public static Connection connect(){
        // αρχική δημιουργία βάσης
        String connectionString = "jdbc:derby:db/university;create=true";
        
        try {
            Connection connection = DriverManager.getConnection(connectionString);
            return connection;
        } catch (SQLException ex) {
            System.out.println("Connection error: " + ex.getLocalizedMessage());
        }
        return null;
    }
    
    
    /**
     * μέθοδος δημιουργίας πινάκων της βάσης δεδομένων (χρησιμοποιήθηκε για την αρχική δημιουργία της ΒΔ)
     */
    public static void createDatabase(){
        Connection connection = connect();
        if (connection == null){
            System.out.println("Connection failure to the Database.");
            return;
        }
        
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
        
        //try-with-resources
        try (Statement statement = connection.createStatement())
        {
            //δημιουργία πίνακα
            statement.executeUpdate(createUniversity);
            System.out.println("Table 'University' created.");
            
        } catch (SQLException ex) {
           System.out.println("Error creating table: " + ex.getLocalizedMessage());
        } finally {
            try{
                if (connection != null){
                    connection.close();
                }
            }catch(SQLException ex){
                System.out.println("Error closing connection: " + ex.getLocalizedMessage());
            }
        }
        //end try-catch-finally
    }
    
}
