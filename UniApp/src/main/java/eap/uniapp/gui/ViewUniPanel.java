package eap.uniapp.gui;

import eap.uniapp.UniApp;
import eap.uniapp.db.University;
import eap.uniapp.model.JavaUniversity;
import eap.uniapp.utils.ButtonUtils;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * Η κλάση {@code ViewUniPanel} αποτελεί το πάνελ προβολής λεπτομερειών ενός πανεπιστημίου.
 * Εμφανίζει πληροφορίες που αφορούν το πανεπιστήμιο (όνομα, χώρα, κ.ά.) και παρέχει
 * λειτουργίες για επεξεργασία, αποθήκευση και ανανέωση των στοιχείων του, τόσο από
 * δεδομένα που προέρχονται από το API όσο και από τη βάση δεδομένων.
 * </p>
 * 
 */
public class ViewUniPanel extends JPanel{
    // δήλωση μεταβλητών κλάσης

    /**
     * Αντικείμενο τύπου {@link JavaUniversity} που περιέχει τα δεδομένα του πανεπιστημίου.
     */
    private JavaUniversity university;

    /**
     * Αναφορά στο κύριο πλαίσιο (MainFrame) της εφαρμογής για πλοήγηση μεταξύ panels.
     */
    private final MainFrame mainFrame;

    /**
     * Πεδίο κειμένου για εμφάνιση της χώρας του πανεπιστημίου.
     */
    private JTextField countryField;

    /**
     * Πεδίο κειμένου για εμφάνιση της πολιτείας/επαρχίας του πανεπιστημίου.
     */
    private JTextField stateProninceField;

    /**
     * Πεδίο κειμένου για εμφάνιση του κωδικού δύο χαρακτήρων (Alpha-2 Code).
     */
    private JTextField alphaTwoCodeField;

    /**
     * Πεδίο κειμένου για εμφάνιση της ιστοσελίδας του πανεπιστημίου.
     */
    private JTextField webPagesField;

    /**
     * Πεδίο κειμένου για εμφάνιση των domains του πανεπιστημίου.
     */
    private JTextField domainsField;

    /**
     * Πεδίο κειμένου για εμφάνιση των στοιχείων επικοινωνίας του πανεπιστημίου.
     */
    private JTextField contactField;

    /**
     * Πεδίο κειμένου για εμφάνιση της περιγραφής του πανεπιστημίου.
     */
    private JTextField descriptionField;

    /**
     * Πεδίο κειμένου για εμφάνιση των σχολίων σχετικά με το πανεπιστήμιο.
     */
    private JTextField commentsField;

    /**
     * Ετικέτα που εμφανίζει τον τίτλο του πανεπιστημίου στο πάνελ.
     */
    private JLabel titleLabel;

    /**
     * Μεταβλητή που καθορίζει αν το πάνελ βρίσκεται σε κατάσταση επεξεργασίας (editing).
     */
    private boolean isEditing = false;
    
    
    //constructor

    /**
     * <p>
     * Ο constructor του {@code ViewUniPanel} αρχικοποιεί το πάνελ προβολής με τα δεδομένα
     * του πανεπιστημίου και λαμβάνει αναφορά στο κύριο πλαίσιο της εφαρμογής για περαιτέρω
     * πλοήγηση. Ορίζει επίσης τη διάταξη, τα χρώματα και τα UI στοιχεία που χρησιμοποιούνται
     * για την προβολή και επεξεργασία των πληροφοριών.
     * </p>
     * 
     * @param university Το αντικείμενο {@link JavaUniversity} που περιέχει τις αρχικές πληροφορίες.
     * @param mainFrame  Αναφορά στο {@link MainFrame} για πλοήγηση μεταξύ των panels.
     */
    public ViewUniPanel(JavaUniversity university, MainFrame mainFrame){
        this.university = university;
        this.mainFrame = mainFrame;
        
        
        setLayout(new BorderLayout());
        setBackground(new Color(0xd0edef)); //light blue
        
        System.out.println("ViewUniPanel loaded...");//debugging
        
        // Τίτλος πανεπιστημίου: ρυθμίσεις και αρχικοποίηση
        titleLabel = new JLabel("",SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial",Font.BOLD,26));
        titleLabel.setBackground(new Color(0xffffff));
        titleLabel.setForeground(new Color(0x003366));
        titleLabel.setOpaque(false);
        titleLabel.setPreferredSize(new Dimension(150,60));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        // Πληροφορίες πανεπιστημίου: αρχικοποίηση των JTextFields
        countryField = createTextField("");
        stateProninceField = createTextField("");
        alphaTwoCodeField = createTextField("");
        webPagesField = createTextField("");
        domainsField = createTextField("");
        contactField = createTextField("");
        descriptionField = createTextField("");
        commentsField = createTextField("");
        
        // Δημιουργία του infoPanel που θα εμφανίζει τις πληροφορίες σε δύο στήλες (Ετικέτα - Τιμή)
        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel,BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(0xffffff));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 300, 50, 50));// top-left-bottom-right
        
        // Προσθήκη γραμμών πληροφοριών στο infoPanel
        // Country
        infoPanel.add(createInfoRow("Country:", countryField, new Dimension(350, 30)));
        infoPanel.add(Box.createRigidArea(new Dimension(0,10)));
        // State-Province
        infoPanel.add(createInfoRow("State-Province:", stateProninceField, new Dimension(350, 30)));
        infoPanel.add(Box.createRigidArea(new Dimension(0,10)));
        // Alpha-2 Code
        infoPanel.add(createInfoRow("Alpha-2 Code:", alphaTwoCodeField, new Dimension(350, 30)));
        infoPanel.add(Box.createRigidArea(new Dimension(0,10)));
        // Web page
        infoPanel.add(createInfoRow("Web page:", webPagesField, new Dimension(350, 30)));
        infoPanel.add(Box.createRigidArea(new Dimension(0,10)));
        // Domain
        infoPanel.add(createInfoRow("Domain:", domainsField, new Dimension(350, 30)));
        infoPanel.add(Box.createRigidArea(new Dimension(0,10)));
        // Contact
        infoPanel.add(createInfoRow("Contact:", contactField, new Dimension(350, 60)));
        infoPanel.add(Box.createRigidArea(new Dimension(0,10)));
        // Description
        infoPanel.add(createInfoRow("Description:", descriptionField, new Dimension(350, 90)));
        infoPanel.add(Box.createRigidArea(new Dimension(0,10)));
        // Comments
        infoPanel.add(createInfoRow("Comments:", commentsField, new Dimension(350, 60)));
        
        // Κουμπί επεξεργασίας (Edit/Cancel)
        JButton editButton = ButtonUtils.createButton("Edit",new Dimension(100,40),new Font("Arial",Font.BOLD,16),
                new Color(0xffffff),new Color(0x003366),new Color(0x003366));
        editButton.addActionListener(e -> {
            if(isEditing){
                // Εάν τα πεδία βρίσκονται σε κατάσταση επεξεργασίας, κάνε τα non-editable
                setFieldsEditable(false);
                // Ενημέρωση της κατάστασης επεξεργασίας
                isEditing = false;
                System.out.println("Cancel button pressed. University textfields refreshed: "+ titleLabel.getText()); //debugging
                stableViewPanelfromDB(titleLabel.getText());
                // Επαναφορά του κουμπιού σε "Edit"
                editButton.setText("Edit");
            } else {
                // Εάν τα πεδία δεν είναι σε κατάσταση επεξεργασίας, κάνε τα editable
                setFieldsEditable(true);
                isEditing = true;
                System.out.println("Edit button pressed.");
                // Αλλαγή του κειμένου του κουμπιού σε "Cancel"
                editButton.setText("Cancel");
            }
        });
        
        // Κουμπί αποθήκευσης στη βάση δεδομένων (Save to DB)
        JButton saveToDBButton = ButtonUtils.createButton("Save to DB",new Dimension(100,40),new Font("Segoe UI",Font.BOLD,16),
                new Color(0xffffff),new Color(0x003366),new Color(0x003366));
        saveToDBButton.addActionListener(e -> {
            if(isEditing){
                System.out.println("Save to DB button pressed.");//debugging
                String uniName = titleLabel.getText();
                // Εάν τα πεδία βρίσκονται σε κατάσταση επεξεργασίας, ενημέρωσε τα δεδομένα στη βάση
                updateUniversitytoDB(uniName);
                // Μετά το update, κάνε τα πεδία non-editable
                setFieldsEditable(false);
                // Επαναφορά του κουμπιού σε "Edit"
                editButton.setText("Edit");
            } else {
                JOptionPane.showMessageDialog(this, "Please click 'Edit' to enable editing.",
                        "No changes made.",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        // Κουμπί επιστροφής στο προηγούμενο πάνελ (Back)
        JButton backButton = ButtonUtils.createButton("Back",new Dimension(100,40),new Font("Segoe UI",Font.BOLD,16),
                new Color(0xffffff),new Color(0x003366),new Color(0x003366));
        backButton.addActionListener(e ->{
            System.out.println("Back button pressed.");//debugging
            // Κλήση επιστροφής
            mainFrame.backToSearch();
        });
        
        // Δημιουργία πάνελ για τα κουμπιά στο κάτω μέρος του ViewUniPanel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,50,0));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        bottomPanel.add(editButton);
        bottomPanel.add(saveToDBButton);
        bottomPanel.add(backButton);
        
        // Προσθήκη των στοιχείων στο κύριο JPanel με διάταξη BorderLayout
        add(titleLabel,BorderLayout.NORTH);
        add(infoPanel,BorderLayout.CENTER);
        add(bottomPanel,BorderLayout.SOUTH);
    }//end of constructor
    
    //ΜΕΘΟΔΟΙ ΚΛΑΣΗΣ
    
    /**
     * <p>
     * Ενημερώνει το ViewUniPanel με τα δεδομένα του πανεπιστημίου που επιλέχθηκε.
     * Ελέγχει αν το πανεπιστήμιο υπάρχει ήδη στη βάση. Εάν υπάρχει, φορτώνει τα δεδομένα
     * από τη βάση, διαφορετικά, ανανεώνει το πάνελ με δεδομένα από το API και αποθηκεύει
     * τη νέα εγγραφή στη βάση δεδομένων.
     * </p>
     * 
     * @param uni Το αντικείμενο {@link JavaUniversity} με τα δεδομένα του πανεπιστημίου.
     */
    public void updateUniversity(JavaUniversity uni){
        this.university = uni;
        
        // Ελέγχει αν το πανεπιστήμιο υπάρχει ήδη στη βάση
        boolean flag = checkExistence(university.getName());
        
        // Ανάκτηση του ονόματος του πανεπιστημίου
        String name = university.getName();
        
        // Εάν το πανεπιστήμιο υπάρχει στη βάση, φορτώνει τα δεδομένα από τη βάση,
        // διαφορετικά, φορτώνει τα δεδομένα από το API και αποθηκεύει τη νέα εγγραφή στη βάση.
        if (flag){
            try {
                refreshViewPanelfromDB(name);
            } catch (Exception ex) {
                System.out.println("updateUniversity error: " + ex.getMessage());
                Logger.getLogger(ViewUniPanel.class.getName()).log(Level.SEVERE, "Error in ViewUniPanel (updateUniversity)", ex);
            }
        } else {
            refreshViewPanel(); // Ανανεώνει το περιεχόμενο με δεδομένα από το API
            saveUniversitytoDB(); // Αποθηκεύει το πανεπιστήμιο στη βάση δεδομένων
        }
    }
    
    /**
     * <p>
     * Ελέγχει αν υπάρχει ήδη εγγραφή του πανεπιστημίου στη βάση δεδομένων βάσει του ονόματος.
     * </p>
     * 
     * @param name Το όνομα του πανεπιστημίου.
     * @return {@code true} εάν υπάρχει εγγραφή, {@code false} σε αντίθετη περίπτωση.
     */
    public boolean checkExistence(String name){
        try {
            return (UniApp.controller.findUniversity(name) != null);
        } catch (Exception ex) {
            System.out.println("checkExistence error: " + ex.getMessage());
            Logger.getLogger(ViewUniPanel.class.getName()).log(Level.SEVERE, "Error in ViewUniPanel (checkExistence)", ex);
        }
        return false;
    }
    
    /**
     * <p>
     * Ανανεώνει το περιεχόμενο του ViewUniPanel με δεδομένα που προέρχονται από το API.
     * </p>
     */
    public void refreshViewPanel(){
        System.out.println("method refreshViewPanel from API data:" + university.getName());//debugging
        titleLabel.setText(university.getName());
        countryField.setText(university.getCountry());
        stateProninceField.setText(university.getStateProvince());
        alphaTwoCodeField.setText(university.getAlphaTwoCode());
        webPagesField.setText(String.join(", ", university.getWebPages()));
        domainsField.setText(String.join(", ", university.getDomains()));
        contactField.setText("");
        descriptionField.setText("");
        commentsField.setText("");
    }
    
    /**
     * <p>
     * Ανανεώνει το ViewUniPanel με δεδομένα που προέρχονται από τη βάση δεδομένων.
     * Κατά την ανάκτηση, αυξάνει επίσης τον αριθμό των αναζητήσεων του πανεπιστημίου.
     * </p>
     * 
     * @param name Το όνομα του πανεπιστημίου.
     */
    public void refreshViewPanelfromDB(String name){
        try{
            // Ανάκτηση του πανεπιστημίου από τη βάση δεδομένων
            University dbUniversity = UniApp.controller.findUniversity(name);

            if(dbUniversity == null){
                System.out.println("University not found in Database.");
                return;
            }
            
            // Αύξηση των αναζητήσεων του πανεπιστημίου
            dbUniversity.addSearch();

            // Ενημέρωση της βάσης δεδομένων με τις νέες τιμές
            UniApp.controller.edit(dbUniversity);

            System.out.println("method refreshViewPanel from DB:" + dbUniversity.getName() +
                    ", Total searches: " + dbUniversity.getSearches());//debugging
            
            // Ενημέρωση των στοιχείων του GUI με δεδομένα από τη βάση
            titleLabel.setText(dbUniversity.getName());
            countryField.setText(dbUniversity.getCountry());
            stateProninceField.setText(dbUniversity.getStateprovince());
            alphaTwoCodeField.setText(dbUniversity.getAlphatwocode());
            webPagesField.setText(dbUniversity.getWebpages());
            domainsField.setText(dbUniversity.getDomains());
            contactField.setText(dbUniversity.getContact());
            descriptionField.setText(dbUniversity.getDescription());
            commentsField.setText(dbUniversity.getComments());
            
        } catch(Exception ex) {
            System.out.println("refreshViewPanelfromDB error: " + ex.getMessage());
            Logger.getLogger(ViewUniPanel.class.getName()).log(Level.SEVERE, "Error in ViewUniPanel (refreshViewPanelfromDB)", ex);
        }
    }
    
    /**
     * <p>
     * Ανανεώνει το ViewUniPanel με δεδομένα από τη βάση δεδομένων χωρίς να αυξάνει
     * τον αριθμό των αναζητήσεων του πανεπιστημίου.
     * </p>
     * 
     * @param name Το όνομα του πανεπιστημίου.
     */
    public void stableViewPanelfromDB(String name){
        try{
            // Ανάκτηση του πανεπιστημίου από τη βάση δεδομένων
            University dbUniversity = UniApp.controller.findUniversity(name);

            if(dbUniversity == null){
                System.out.println("University not found in Database.");
                return;
            }
            
            System.out.println("method stableViewPanel from DB:" + dbUniversity.getName() +
                    ", Total searches: " + dbUniversity.getSearches());//debugging
            
            // Ενημέρωση του GUI με τα δεδομένα από τη βάση δεδομένων
            titleLabel.setText(dbUniversity.getName());
            countryField.setText(dbUniversity.getCountry());
            stateProninceField.setText(dbUniversity.getStateprovince());
            alphaTwoCodeField.setText(dbUniversity.getAlphatwocode());
            webPagesField.setText(dbUniversity.getWebpages());
            domainsField.setText(dbUniversity.getDomains());
            contactField.setText(dbUniversity.getContact());
            descriptionField.setText(dbUniversity.getDescription());
            commentsField.setText(dbUniversity.getComments());
            
        } catch(Exception ex) {
            System.out.println("stableViewPanelfromDB error: " + ex.getMessage());
            Logger.getLogger(ViewUniPanel.class.getName()).log(Level.SEVERE, "Error in ViewUniPanel (refreshViewPanelfromDB)", ex);
        }
    }
    
    /**
     * <p>
     * Αποθηκεύει ένα νέο πανεπιστήμιο στη βάση δεδομένων. Δημιουργεί ένα αντικείμενο
     * {@code University} και αντιγράφει σε αυτό τα δεδομένα από το {@code JavaUniversity}
     * αντικείμενο, αυξάνοντας τις αναζητήσεις κατά μία.
     * </p>
     */
    public void saveUniversitytoDB(){
        // Δημιουργία αντικειμένου University για αποθήκευση στη βάση δεδομένων
        University universityToSave = new University();// πρώτη φορά
        
        try{
            // Αντιγραφή δεδομένων από το JavaUniversity στο αντικείμενο University
            universityToSave.setName(university.getName());
            universityToSave.setCountry(university.getCountry());
            universityToSave.setStateprovince(university.getStateProvince());
            universityToSave.setAlphatwocode(university.getAlphaTwoCode());
            universityToSave.setWebpages(String.join(", ", university.getWebPages()));
            universityToSave.setDomains(String.join(", ", university.getDomains()));
            universityToSave.addSearch(); // Αύξηση των αναζητήσεων του πανεπιστημίου στη βάση
            
            // Αποθήκευση του πανεπιστημίου στη βάση δεδομένων μέσω του controller
            UniApp.controller.create(universityToSave);
            System.out.println("New university added to database: "+ universityToSave.getName());
        }
        catch(SQLException sqlEx){
            System.out.println("saveUniversitytoDB SQL error: " + sqlEx.getMessage());
        }
        catch(Exception ex){
            System.out.println("saveUniversitytoDB error: " + ex.getMessage());
        }
    }
    
    /**
     * <p>
     * Ενημερώνει ένα υπάρχον πανεπιστήμιο στη βάση δεδομένων με τις νέες τιμές που
     * έχουν εισαχθεί στα text fields του ViewUniPanel.
     * </p>
     * 
     * @param name Το όνομα του πανεπιστημίου που θα ενημερωθεί στη βάση δεδομένων.
     */
    public void updateUniversitytoDB(String name){
        // Δημιουργία αντικειμένου University για ενημέρωση στη βάση δεδομένων
        University universityToUpdate = null;
        
        try{
            // Αναζήτηση του πανεπιστημίου στη βάση δεδομένων
            universityToUpdate = UniApp.controller.findUniversity(name);
            
            // Ενημέρωση του αντικειμένου με τις νέες τιμές από τα text fields
            universityToUpdate.setCountry(countryField.getText());
            universityToUpdate.setStateprovince(stateProninceField.getText());
            universityToUpdate.setAlphatwocode(alphaTwoCodeField.getText());
            universityToUpdate.setWebpages(webPagesField.getText());
            universityToUpdate.setDomains(domainsField.getText());
            universityToUpdate.setContact(contactField.getText());
            universityToUpdate.setDescription(descriptionField.getText());
            universityToUpdate.setComments(commentsField.getText());
            
            // Αποθήκευση των αλλαγών στη βάση δεδομένων μέσω του controller
            UniApp.controller.edit(universityToUpdate);
            System.out.println("University updated in database: "+ universityToUpdate.getName());
            JOptionPane.showMessageDialog(this, "Changes have been saved to Database.",
                        "Save message.",JOptionPane.INFORMATION_MESSAGE);
            
        } catch(Exception ex){
            System.out.println("Error while updating the university: " + ex.getMessage());
        }
    }
    
    /**
     * <p>
     * Δημιουργεί και επιστρέφει ένα {@link JTextField} με προκαθορισμένη μορφοποίηση.
     * Το πεδίο δεν είναι επεξεργάσιμο από τον χρήστη.
     * </p>
     * 
     * @param text Το αρχικό κείμενο που θα εμφανίζεται στο πεδίο.
     * @return Ένα αντικείμενο {@link JTextField} με τις καθορισμένες ρυθμίσεις.
     */
    private JTextField createTextField(String text){
        JTextField textField = new JTextField(text);
        textField.setFont(new Font("Arial",Font.PLAIN,16));
        textField.setHorizontalAlignment(SwingConstants.LEFT);
        textField.setEditable(false); // Δεν μπορεί να επεξεργαστεί από τον χρήστη
        
        return textField;
    }
    
    /**
     * <p>
     * Δημιουργεί ένα {@link JPanel} που αντιπροσωπεύει μία γραμμή πληροφοριών, αποτελούμενη
     * από μια ετικέτα και ένα text field. Το πάνελ αυτό χρησιμοποιείται για την εμφάνιση των
     * πεδίων πληροφοριών στο ViewUniPanel.
     * </p>
     * 
     * @param labelText Το κείμενο της ετικέτας.
     * @param textField Το {@link JTextField} που εμφανίζει την τιμή του πεδίου.
     * @param size      Η επιθυμητή διάσταση για το {@link JTextField}.
     * @return Ένα {@link JPanel} που περιέχει την ετικέτα και το text field με το ορισμένο layout.
     */
    private JPanel createInfoRow(String labelText, JTextField textField, Dimension size) {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
        rowPanel.setBackground(new Color(0xffffff));
        rowPanel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(120, 30)); // Σταθερό πλάτος ετικέτας
        label.setFont(new Font("Arial",Font.BOLD,16));
        label.setForeground(new Color(0x003366));
        
        // Ορισμός μεγέθους του textField (μέγιστο και προτιμώμενο μέγεθος)
        textField.setMaximumSize(size);
        textField.setPreferredSize(size);

        rowPanel.add(label);
        rowPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Σταθερό κενό μεταξύ ετικέτας και πεδίου
        rowPanel.add(textField);
        rowPanel.add(Box.createHorizontalGlue()); // Επέκταση κενών εντός του container
        
        return rowPanel;
    }
    
    /**
     * <p>
     * Ορίζει εάν τα πεδία κειμένου στο ViewUniPanel θα είναι επεξεργάσιμα ή όχι.
     * </p>
     * 
     * @param editable {@code true} για να γίνουν τα πεδία επεξεργάσιμα, {@code false} για
     *                 να γίνουν μόνο για προβολή.
     */
    private void setFieldsEditable(boolean editable){
        countryField.setEditable(editable);
        stateProninceField.setEditable(editable);
        alphaTwoCodeField.setEditable(editable);
        webPagesField.setEditable(editable);
        domainsField.setEditable(editable);
        contactField.setEditable(editable);
        descriptionField.setEditable(editable);
        commentsField.setEditable(editable);
    }
    
}
