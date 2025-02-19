package eap.uniapp;

import eap.uniapp.gui.MainFrame;
import eap.uniapp.model.JavaUniversity;
import eap.uniapp.utils.UrlCall;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.swing.SwingUtilities;



public class UniApp {
    
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        System.out.println("Starting UniApp..."); //μήνυμα στην κονσόλα
        
        launchGUI();
        
        System.out.println("UniApp started..."); //μήνυμα στην κονσόλα
        /*
        UrlCall a = new UrlCall();
        List <JavaUniversity> jlist = a.SearchUniversities("open", "");
        System.out.println("Number of objects are: " + jlist.size());
        for(int i=0;i<jlist.size();i++){
            System.out.println((i+1)+ ") " + jlist.get(i) + "\n");
        }
        */
        
        
    } //end of Main
    
    // μέθοδος εκκίνησης γραφικού περιβάλλοντος στο Event Dispatched Thread
    private static void launchGUI(){
        SwingUtilities.invokeLater(MainFrame::new);
    }
    
    // μέθοδος δημιουργίας σύνδεσης στη ΒΔ
    public static Connection connect(){
        // αρχική δημιουργία βάσης
        String connectionString = "jdbc:derby:db/university";
        
        try {
            Connection connection = DriverManager.getConnection(connectionString);
            return connection;
        } catch (SQLException ex) {
            System.out.println("Connection error: " + ex.getLocalizedMessage());
        }
        return null;
    }
    
    // μέθοδος δημιουργίας πινάκων της βάσης δεδομένων
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
