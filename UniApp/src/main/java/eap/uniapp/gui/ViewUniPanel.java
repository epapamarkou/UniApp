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


public class ViewUniPanel extends JPanel{
    //δήλωση μεταβλητών κλάσης
    private JavaUniversity university;
    private final MainFrame mainFrame;
    private JTextField countryField;
    private JTextField stateProninceField;
    private JTextField alphaTwoCodeField;
    private JTextField webPagesField;
    private JTextField domainsField;
    private JTextField contactField;
    private JTextField descriptionField;
    private JTextField commentsField;
    private JLabel titleLabel;
    private boolean isEditing = false;
    
    
    //constructor
    public ViewUniPanel(JavaUniversity university,MainFrame mainFrame){
        this.university = university;
        this.mainFrame = mainFrame;
        
        
        setLayout(new BorderLayout());
        setBackground(new Color(0xd0edef)); //light blue
        
        System.out.println("ViewUniPanel loaded...");
        
        //τίτλος πανεπιστημίου settings και αρχικοποίηση
        titleLabel = new JLabel("",SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial",Font.BOLD,26));
        titleLabel.setBackground(new Color(0xffffff));
        titleLabel.setForeground(new Color(0x003366));
        titleLabel.setOpaque(false);
        titleLabel.setPreferredSize(new Dimension(150,60));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        //ΠΛΗΡΟΦΟΡΙΕΣ ΠΑΝΕΠΙΣΤΗΜΙΟΥ
        
        //αρχικοποίηση των JTextFields
        countryField = createTextField("");
        stateProninceField = createTextField("");
        alphaTwoCodeField = createTextField("");
        webPagesField = createTextField("");
        domainsField = createTextField("");
        contactField = createTextField("");
        descriptionField = createTextField("");
        commentsField = createTextField("");
        
        //δημιουργία του infoPanel που θα τις πληροφορίες σε δύο στήλες Όνομα-Τιμή
        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel,BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(0xffffff));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 300, 50, 50));//top-left-bottom-right
        
        //προσθήκη Ονόματος πεδίου - Τιμής στο infoPanel
        //Country
        infoPanel.add(createInfoRow("Country:", countryField, new Dimension(350, 30)));
        infoPanel.add(Box.createRigidArea(new Dimension(0,10)));
        //state-pronince
        infoPanel.add(createInfoRow("State-Province:", stateProninceField, new Dimension(350, 30)));
        infoPanel.add(Box.createRigidArea(new Dimension(0,10)));
        //alphaTwoCode
        infoPanel.add(createInfoRow("Alpha-2 Code:", alphaTwoCodeField, new Dimension(350, 30)));
        infoPanel.add(Box.createRigidArea(new Dimension(0,10)));
        //webPages
        infoPanel.add(createInfoRow("Web page:", webPagesField, new Dimension(350, 30)));
        infoPanel.add(Box.createRigidArea(new Dimension(0,10)));
        //domains
        infoPanel.add(createInfoRow("Domain:", domainsField, new Dimension(350, 30)));
        infoPanel.add(Box.createRigidArea(new Dimension(0,10)));
        //contact
        infoPanel.add(createInfoRow("Contact:", contactField, new Dimension(350, 60)));
        infoPanel.add(Box.createRigidArea(new Dimension(0,10)));
        //description
        infoPanel.add(createInfoRow("Description:", descriptionField, new Dimension(350, 90)));
        infoPanel.add(Box.createRigidArea(new Dimension(0,10)));
        //comments
        infoPanel.add(createInfoRow("Comments:", commentsField, new Dimension(350, 60)));
        
        //κουμπί edit
        JButton editButton = ButtonUtils.createButton("Edit",new Dimension(100,40),new Font("Arial",Font.BOLD,16),
                new Color(0xffffff),new Color(0x003366),new Color(0x003366));
        editButton.addActionListener(e -> {
            if(isEditing){
                //αν είναι σε κατάσταση επεξεργασίας, με το πάτημα του cancel, τα πεδία γίνονται non-editable
                setFieldsEditable(false);
                //αλλαγή τιμής στη μεταβλητή isEditing
                isEditing = false;
                //
                System.out.println("Cancel button pressed. University textfields refreshed: "+ titleLabel.getText()); //debugging
                stableViewPanelfromDB(titleLabel.getText());
                //το κουμπί Cancel επαναφέρεται σε Edit
                editButton.setText("Edit");
            }else{
                //αν δεν είναι σε κατάσταση επεξεργασίας, με το πάτημα του edit, τα πεδία γίνονται editable
                setFieldsEditable(true);
                //αλλαγή τιμής στη μεταβλητή isEditing
                isEditing = true;
                System.out.println("Edit button pressed.");
                //το κουμπί Edit αλλάζει σε Cancel
                editButton.setText("Cancel");
            }
        });
        
        //κουμπί αποθήκευση στη ΒΔ
        JButton saveToDBButton = ButtonUtils.createButton("Save to DB",new Dimension(100,40),new Font("Segoe UI",Font.BOLD,16),
                new Color(0xffffff),new Color(0x003366),new Color(0x003366));
        saveToDBButton.addActionListener(e -> {
            if(isEditing){
                String uniName = titleLabel.getText();
                //είναι σε κατάσταση επεξεργασίας και αποθηκεύονται/ανανεώνονται τα περιεχόμενα στη ΒΔ
                updateUniversitytoDB(uniName);
                //μετά το update τα πεδία γίνονται non-editable
                setFieldsEditable(false);
                //το κουμπί Cancel επαναφέρεται σε Edit
                editButton.setText("Edit");
                
            }else{
                JOptionPane.showMessageDialog(this, "Please click 'Edit' to enable editing.",
                        "No changes made.",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        
        //κουμπί πίσω
        JButton backButton = ButtonUtils.createButton("Back",new Dimension(100,40),new Font("Segoe UI",Font.BOLD,16),
                new Color(0xffffff),new Color(0x003366),new Color(0x003366));
        backButton.addActionListener(e -> mainFrame.backToSearch());//κλήση επιστροφής
        
        
        //δημιουργία Panel που φιλοξενεί τα κουμπιά
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,50,0));//απόσταση 20pixels οριζόντια
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        bottomPanel.add(editButton);
        bottomPanel.add(saveToDBButton);
        bottomPanel.add(backButton);
        
        
        //προσθήκη στο κεντρικό JPanel
        add(titleLabel,BorderLayout.NORTH);
        add(infoPanel,BorderLayout.CENTER);
        add(bottomPanel,BorderLayout.SOUTH);
    }//end of constructor
    
    //ΜΕΘΟΔΟΙ ΚΛΑΣΗΣ
    
    //εδώ έρχεται το πανεπιστήμιο που επιλέχθηκε για προεπισκόπιση στο SearchPanel
    public void updateUniversity(JavaUniversity uni){
        this.university = uni;
        
        //μεταβλητή που φέρει το αποτέλεσμα αν βρέθηκε ή όχι στη ΒΔ
        boolean flag = checkExistence(university.getName());
        
        //μεταβλητή που φέρει το όνομα του πανεπιστημίου
        String name = university.getName();
        
        //αν το flag=true, φορτώνει δεδομένα απ'τη ΒΔ
        //αν το flag=false, φορτώνει δεδομένα απ'το API και αποθηκεύει νέα εγγραφή στη ΒΔ
        if (flag){
            try {
                refreshViewPanelfromDB(name);
            } catch (Exception ex) {
                System.out.println("updateUniversity error: " + ex.getMessage());
                Logger.getLogger(ViewUniPanel.class.getName()).log(Level.SEVERE, "Error in ViewUniPanel (updateUniversity)", ex);
            }
        }else{
            refreshViewPanel(); //καλείται για ανανέωση περιεχομένου
            saveUniversitytoDB(); //αποθήκευση πανεπιστημίου στη ΒΔ
        }
    }
    
    //μέθοδος που επιστρέφει TRUE αν υπάρχει εγγραφή του πανεπιστημίου στη ΒΔ και
    //FALSE αν δεν υπάρχει εγγραφή. Έλεγχος με το κλειδί name.
    public boolean checkExistence(String name){
        try {
            //αντί για if-else γράφω απευθείας την έκφραση που αποτιμάται σε Τ ή F
            return (UniApp.controller.findUniversity(name) != null);
        } catch (Exception ex) {
            System.out.println("checkExistence error: " + ex.getMessage());
            Logger.getLogger(ViewUniPanel.class.getName()).log(Level.SEVERE, "Error in ViewUniPanel (checkExistence)", ex);
        }
        return false;
    }
    
    // ανανεωση του περιεχόμενου του ViewUniPanel from API data
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
    
    // ανανεωση του περιεχόμενου του ViewUniPanel from DB με ΑΥΞΗΣΗ ΤΩΝ ΑΝΑΖΗΤΗΣΕΩΝ
    public void refreshViewPanelfromDB(String name){
        try{
            //ανάκτηση πανεπιστημίου απ'τη ΒΔ
            University dbUniversity = UniApp.controller.findUniversity(name);

            if(dbUniversity == null){
                System.out.println("University not found in Database.");
                return;
            }
            
            //αύξηση των εμφανίσεων του πανεπιστημίου
            dbUniversity.addSearch();

            //αποθήκευση της αύξησης (αλλαγή) στη βαση δεδομενων (ο controller αποθηκεύει στη ΒΔ)
            UniApp.controller.edit(dbUniversity);

            System.out.println("method refreshViewPanel from DB:" + dbUniversity.getName() +
                    ", Total searches: " + dbUniversity.getSearches());//debugging
            
            //ενημέρωση GUI με δεδομένα από τη ΒΔ
            titleLabel.setText(dbUniversity.getName());
            countryField.setText(dbUniversity.getCountry());
            stateProninceField.setText(dbUniversity.getStateprovince());
            alphaTwoCodeField.setText(dbUniversity.getAlphatwocode());
            webPagesField.setText(dbUniversity.getWebpages());
            domainsField.setText(dbUniversity.getDomains());
            contactField.setText(dbUniversity.getContact());
            descriptionField.setText(dbUniversity.getDescription());
            commentsField.setText(dbUniversity.getComments());
            
        }catch(Exception ex){
            System.out.println("refreshViewPanelfromDB error: " + ex.getMessage());
            Logger.getLogger(ViewUniPanel.class.getName()).log(Level.SEVERE, "Error in ViewUniPanel (refreshViewPanelfromDB)", ex);
        }
    }
    
    
    // ανανεωση του περιεχόμενου του ViewUniPanel from DB, ΧΩΡΙΣ ΑΥΞΗΣΗ ΤΩΝ ΕΜΦΑΝΙΣΕΩΝ
    public void stableViewPanelfromDB(String name){
        try{
            //ανάκτηση πανεπιστημίου απ'τη ΒΔ
            University dbUniversity = UniApp.controller.findUniversity(name);

            if(dbUniversity == null){
                System.out.println("University not found in Database.");
                return;
            }
            
            System.out.println("method stableViewPanel from DB:" + dbUniversity.getName() +
                    ", Total searches: " + dbUniversity.getSearches());//debugging
            
            //ενημέρωση GUI με δεδομένα από τη ΒΔ
            titleLabel.setText(dbUniversity.getName());
            countryField.setText(dbUniversity.getCountry());
            stateProninceField.setText(dbUniversity.getStateprovince());
            alphaTwoCodeField.setText(dbUniversity.getAlphatwocode());
            webPagesField.setText(dbUniversity.getWebpages());
            domainsField.setText(dbUniversity.getDomains());
            contactField.setText(dbUniversity.getContact());
            descriptionField.setText(dbUniversity.getDescription());
            commentsField.setText(dbUniversity.getComments());
            
        }catch(Exception ex){
            System.out.println("stableViewPanelfromDB error: " + ex.getMessage());
            Logger.getLogger(ViewUniPanel.class.getName()).log(Level.SEVERE, "Error in ViewUniPanel (refreshViewPanelfromDB)", ex);
        }
    }
    
    
    //πρώτη αποθήκευση στη βάση δεδομένων
    public void saveUniversitytoDB(){
        //δημιουργία αντικειμένου University για αποθήκευση στη ΒΔ
        University universityToSave = new University();//πρώτη φορά
        
        try{
            //αντιγραφή δεδομένων από JavaUniversity που είναι προς προβολή και 
            //ανάθεσή τους στο University που θα αποθηκευτεί στη ΒΔ, χρησιμοποιώντας 
            //τις μεθόδους της University.java
            universityToSave.setName(university.getName());
            universityToSave.setCountry(university.getCountry());
            universityToSave.setStateprovince(university.getStateProvince());
            universityToSave.setAlphatwocode(university.getAlphaTwoCode());
            universityToSave.setWebpages(String.join(", ", university.getWebPages()));
            universityToSave.setDomains(String.join(", ", university.getDomains()));
            universityToSave.addSearch(); //αύξηση των εμφανίσεων του πανεπιστημίου στη ΒΔ
            
            //o controller αποθηκεύει το πανεπιστήμιο στη ΒΔ
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
    
    //Ενημέρωση πανεπιστημίου στη βάση δεδομένων
    public void updateUniversitytoDB(String name){
        //δημιουργία αντικειμένου University from db για αποθήκευση στη ΒΔ
        University universityToUpdate = null;
        
        try{
            //αναζήτηση του πανεπιστημίου στη βάση δεδομένων
            universityToUpdate = UniApp.controller.findUniversity(name);
            
            //Αν βρεθεί ενημερώνεται με τις νέες τιμές το universityToUpdate
            universityToUpdate.setCountry(countryField.getText());
            universityToUpdate.setStateprovince(stateProninceField.getText());
            universityToUpdate.setAlphatwocode(alphaTwoCodeField.getText());
            universityToUpdate.setWebpages(webPagesField.getText());
            universityToUpdate.setDomains(domainsField.getText());
            universityToUpdate.setContact(contactField.getText());
            universityToUpdate.setDescription(descriptionField.getText());
            universityToUpdate.setComments(commentsField.getText());
            
            //o controller αποθηκεύει τις αλλαγές στο πανεπιστήμιο στη ΒΔ
            UniApp.controller.edit(universityToUpdate);
            System.out.println("University updated in database: "+ universityToUpdate.getName());
            JOptionPane.showMessageDialog(this, "Changes have been saved to Database.",
                        "Save message.",JOptionPane.INFORMATION_MESSAGE);
            
        }catch(Exception ex){
            System.out.println("Error while updating the university: " + ex.getMessage());
        }
    }
    
    //μέθοδος δημιουργίας JTextField για την εμφάνιση του ονόματος του κάθε πεδίου
    private JTextField createTextField(String text){
        JTextField textField = new JTextField(text);
        textField.setFont(new Font("Arial",Font.PLAIN,16));
        textField.setHorizontalAlignment(SwingConstants.LEFT);
        textField.setEditable(false); // δεν μπορεί να επεξεργαστεί από τον χρήστη
        
        return textField;
    }
    
    //μέθοδος δημιουργίας ενός JPanel για την εμφάνιση της πληροφορίας του κάθε πεδίου
    private JPanel createInfoRow(String labelText, JTextField textField, Dimension size) {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
        rowPanel.setBackground(new Color(0xffffff));
        rowPanel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(120, 30)); //σταθερό πλάτος ετικέτας
        label.setFont(new Font("Arial",Font.BOLD,16));
        label.setForeground(new Color(0x003366));
        
        //ορισμός μεγέθους textField (μαξ + προτιμόμενο μέγεθος)
        textField.setMaximumSize(size); //πλάτος 200 pixels new Dimension(200, 30)
        textField.setPreferredSize(size);//πλάτος 200 pixels

        rowPanel.add(label);
        rowPanel.add(Box.createRigidArea(new Dimension(10, 0))); //σταθερό κενό μεταξύ ετικέτας και πεδίου
        rowPanel.add(textField);
        rowPanel.add(Box.createHorizontalGlue()); //κενό επέκτασης μέσα στο container JPanel
        
        return rowPanel;
    }

    
    //μέθοδος για να γίνουν τα Text Fields editable ή όχι
    private void setFieldsEditable(boolean editable){
        //τα πεδία γίνονται επεξεργάσιμα ή όχι ανάλογα με το editable
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

