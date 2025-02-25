package eap.uniapp.gui;

import eap.uniapp.model.JavaUniversity;
import eap.uniapp.utils.ButtonUtils;
import eap.uniapp.utils.UrlCall;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class SearchPanel extends JPanel{
    
    // δήλωση μεταβλητών κλάσης
    private final JTextField nameField;
    private final JButton searchButton;
    private final UrlCall urlCall;
    private final JTable universityTable;
    private final DefaultTableModel tableModel;
    private List<JavaUniversity> universities;
    private final MainFrame mainFrame;
    private final JComboBox<String> countryComboBox;
    
    //constructor
    public SearchPanel(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        
        setLayout(new BorderLayout());
        setBackground(new Color(0xd0edef)); //light blue
        
        //αρχικοποίηση urlCall
        urlCall = new UrlCall();
        
        
        //βόρεια ζώνη τίτλος Panel
        JLabel titleLabel = new JLabel("Find the University", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial",Font.BOLD,28));
        titleLabel.setForeground(new Color(0x003366));
        //προσθήκη κενού μεταξύ titleLabel και mainPanel KAI 
        //μεταξύ titleLabel και searchPanel
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        add(titleLabel,BorderLayout.NORTH); //προσθήκη στο BorderLayout βόρεια
        
        //κεντρική ζώνη πεδίο αναζήτησης πανεπιστημίου, πεδίο αναζήτησης χώρας και κουμπι GO
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.setBackground(new Color(0xd0edef));
        searchPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel countryPanel = new JPanel(new FlowLayout());
        countryPanel.setBackground(new Color(0xd0edef));
        countryPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //δημιουργία Label που φιλοξενεί το μήνυμα Select university:
        JLabel universityLabel = new JLabel("Select university:", SwingConstants.CENTER);
        universityLabel.setFont(new Font("Arial",Font.BOLD,20));
        
        nameField = new JTextField(20); //φιλοξενεί 20 χαρακτήρες
        nameField.setPreferredSize(new Dimension(150,40)); //διαστάσεις κουτιου αναζήτησης πανεπιστημίου
        //γραμματοσειρά που φαινεται οταν πληκτρολογεί ο χρήστης
        nameField.setFont(new Font("Arial",Font.PLAIN,20));
        
        searchButton = ButtonUtils.createButton("Go",new Dimension(100,40),new Font("Arial",Font.BOLD,16),
                new Color(0xffffff),new Color(0x003366),new Color(0x003366));
        
        
        //δημιουργία Label που φιλοξενεί το μήνυμα Select country:
        JLabel countryLabel = new JLabel("Select country:", SwingConstants.CENTER);
        countryLabel.setFont(new Font("Arial",Font.BOLD,20));
        
        //δημιουργία ComboBox που θα φιλοξενεί όλες τις χώρες
        countryComboBox = new JComboBox<>();
        countryComboBox.setPreferredSize(new Dimension(250,40));//διαστάσεις κουτιου αναζήτησης χώρας
        countryComboBox.setFont(new Font("Arial",Font.PLAIN,16));
        countryComboBox.setBackground(new Color(0xffffff));
        
        //προσθήκη των nameField και searchButton στο searchPanel
        searchPanel.add(universityLabel);
        searchPanel.add(nameField);
        searchPanel.add(searchButton);
        
        //προσθήκη των countryLabel και countryComboBox στο countryPanel
        countryPanel.add(countryLabel);
        countryPanel.add(countryComboBox);
        
        //προσθήκη searchPanel στο BorderLayout κέντρο
        //add(searchPanel,BorderLayout.CENTER);
        
        //κάθετη διάταξη πίνακα αποτελεσμάτων
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(0xd0edef));
        
        //προσθήκη searchPanel στο centerPanel
        centerPanel.add(searchPanel);
        
        //προσθήκη countryPanel στο centerPanel
        centerPanel.add(countryPanel);
        
        //προσθήκη κάθετου κενού 30 pixel
        centerPanel.add(Box.createVerticalStrut(30));
        
        //εμφάνιση πίνακα αποτελεσμάτων
        
        //ορισμός μοντέλου για χειρισμό του JTable
        tableModel = new DefaultTableModel();
        //ορισμών στηλών του πίνακα
        tableModel.addColumn("University Name");
        tableModel.addColumn("Country");
        
        //δημιουργία JTable με όρισμα την πηγή δεδομένων
        universityTable = new JTable(tableModel);
        universityTable.setFillsViewportHeight(true);
        //αλλαγή γραμματοσειράς και φόντου headers πίνακα
        universityTable.getTableHeader().setFont(new Font("Arial",Font.BOLD,16));
        universityTable.getTableHeader().setBackground(new Color(0xc4dfe5));
        universityTable.getTableHeader().setPreferredSize(new Dimension(0,30));
        //αλλαγή γραμματοσειράς δεδομένων πίνακα
        universityTable.setFont(new Font("Arial",Font.PLAIN,14));
        //ύψος γραμμών πίνακα
        universityTable.setRowHeight(25);
        //απενεργοποίηση επεξεργασίας κελιών πίνακα
        universityTable.setDefaultEditor(Object.class, null);
        
        // προσθήκη mouse listener - διαχείριση μόνο διπλού κλικ
        universityTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                handleMouseClicked(e);
            }
        });
        
        //δημιουργία JScrollPane και προσθήκη σ'αυτο του universityTable
        JScrollPane uniTableScrollPane = new JScrollPane(universityTable);
        uniTableScrollPane.setPreferredSize(new Dimension(800,300));
        uniTableScrollPane.setBackground(new Color(0xd0edef));
        
        //κεντράρισμα στο BoxLayout
        uniTableScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        //περιθώρια γύρω από τον πίνακα
        uniTableScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        
        //προσθήκη uniTableScrollPane στο centerPanel
        centerPanel.add(uniTableScrollPane);
        
        //προσθήκη centerPanel στο κέντρο του κύριου JPanel
        add(centerPanel,BorderLayout.CENTER);
        
        
        //Listener για το κουμπί GO
        searchButton.addActionListener(e -> performSearch());
        
        //γέμισμα Combobox με χώρες
        fillCountryComboBox();
        
    }//end of constructor
    
    
    //μέθοδος γεμίσματος Combobox με χώρες
    private void fillCountryComboBox(){
        //άδειασμα σε περίπτωση που δεν είναι άδειο
        countryComboBox.removeAllItems();
        
        countryComboBox.addItem("");
        
        //καλείται το αρχείο .json (true) και γεμίζει το allUnis
        List<JavaUniversity> allUnis = urlCall.SearchUniversities("", "", true);
        //καλείται η μέθοδος allCountries και γεμίζει το countries (μοναδικές τιμές)
        List<String> countries = JavaUniversity.allCountries(allUnis);
        
        //προσθήκη χωρών στο Combobox
        for(int i=0; i<countries.size();i++){
            countryComboBox.addItem(countries.get(i));
        }
    }
    
    
    // μέθοδος διαχείρισης για διπλό mouseclick επιλέγοντας πανεπιστήμιο στον πίνακα
    private void handleMouseClicked(MouseEvent e){
        if(e.getClickCount() == 2 && universityTable.getSelectedRow() != -1){
            int selectedRow = universityTable.getSelectedRow();
            JavaUniversity selectedUni = universities.get(selectedRow);
            mainFrame.setSelectedUni(selectedUni);
            mainFrame.showViewUniPanel();
        }
    }
    
    //Μέθοδος αναζήτησης και ενημέρωσης πίνακα
    //4 επιλογές. Α)να μην δώσει κανένα input ο χρήστης, Β)να δώσει όνομα,
    //Γ)να δώσει χώρα, Δ)να δώσει όνομα και χώρα
    private void performSearch(){
        
        //καθαρισμός πίνακα
        tableModel.setRowCount(0);

        //παίρνει το string του χρήστη και αφαιρεί τα whitespaces στην αρχή και στο τέλος
        String name = nameField.getText().trim();
        
        //παίρνει την επιλογή από το countryComboBox
        String country = (String) countryComboBox.getSelectedItem();
        
        //ΑΝ Ο ΧΡΗΣΤΗΣ ΔΕΝ ΔΩΣΕΙ ΟΥΤΕ ΟΝΟΜΑ ΠΑΝΕΠΙΣΤΗΜΙΟΥ ΟΥΤΕ ΧΩΡΑ
        
        //αναδυόμενο παράθυρο με WARNING_MESSAGE όταν ο χρήστης έχει ΠΛΗΡΩΣ ΚΕΝΗ ΑΝΑΖΗΤΗΣΗ
        //το πρόγραμμα δεν συνεχίζει, γυρίζει πίσω
        if(name.isEmpty() && country.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please enter a university name or choose a country.",
                    "Input required.",JOptionPane.WARNING_MESSAGE);
            return; //σταματά εδώ και δεν εκτελείται ο παρακάτω κώδικας
        }
        
        //ΑΝ Ο ΧΡΗΣΤΗΣ ΔΩΣΕΙ ΜΟΝΟ ΟΝΟΜΑ ΠΑΝΕΠΙΣΤΗΜΙΟΥ
        
        if (!name.isEmpty() && country.isEmpty()){
            System.out.println("performSearch for University Name");//debugging
            //δημιουργία List με αντικείμενα JavaUniversity
            //το false δηλώνει ότι θα χρησιμοποιήσει το API
            universities = urlCall.SearchUniversities(name, "", false);

            //debugging
            System.out.println("performSearch: Size of universityList: " + universities.size());

            //αναδυόμενο παράθυρο με INFORMATION_MESSAGE
            if(universities.isEmpty()){
                JOptionPane.showMessageDialog(this, "No universities found.",
                        "Search results.",JOptionPane.INFORMATION_MESSAGE);
            }else{
                //αναδυόμενο παράθυρο με INFORMATION_MESSAGE για επιστρεφόμενο αριθμό πανεπιστημίων
                if(universities.size() == 1){
                    JOptionPane.showMessageDialog(this, "1 University found.",
                        "Search results.",JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(this,universities.size() + " Universities found.",
                        "Search results.",JOptionPane.INFORMATION_MESSAGE);
                }

                //γέμισμα πίνακα αποτελεσμάτων
                for(int i=0;i<universities.size();i++){
                    tableModel.addRow(new Object[]{universities.get(i).getName(),universities.get(i).getCountry()});
                }
            }
            System.out.println("performSearch End for University Name\n");//debugging
            return;
        }
        
        
        //ΑΝ Ο ΧΡΗΣΤΗΣ ΔΩΣΕΙ ΜΟΝΟ ΧΩΡΑ
        
        if (name.isEmpty() && !country.isEmpty()){
            System.out.println("performSearch for Country"); //debugging
            //δημιουργία List με αντικείμενα JavaUniversity
            //το false δηλώνει ότι θα χρησιμοποιήσει το API
            universities = urlCall.SearchUniversities("", country, false);
            
            //αν το universities είναι κενό, θα ψάξει και στο αρχείο JSON τη χώρα
            //για βεβαιότητα
            if(universities.isEmpty()){
                //το true δηλώνει ότι θα ψάξει στο URL με το αρχείο JSON
                List<JavaUniversity> unisFromFile = urlCall.SearchUniversities("", "", true);
                
                if (!unisFromFile.isEmpty()){
                    universities.clear(); //καθάρισμα του universities
                    for(int i=0;i<unisFromFile.size();i++){
                        //αν βρεθούν πανεπιστήμια που περιέχουν το string country που
                        //έδωσε ο χρήστης, μπαίνουν στο universities
                        if(unisFromFile.get(i).getCountry().contains(country)){
                            universities.add(unisFromFile.get(i));
                        }
                    }
                }
            }

            //debugging
            System.out.println("performSearch Size of universityList: " + universities.size());

            //αναδυόμενο παράθυρο με INFORMATION_MESSAGE
            if(universities.isEmpty()){
                JOptionPane.showMessageDialog(this, "No universities found.",
                        "Search results.",JOptionPane.INFORMATION_MESSAGE);
            }else{
                //αναδυόμενο παράθυρο με INFORMATION_MESSAGE για επιστρεφόμενο αριθμό πανεπιστημίων
                if(universities.size() == 1){
                    JOptionPane.showMessageDialog(this, "1 University found.",
                        "Search results.",JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(this,universities.size() + " Universities found.",
                        "Search results.",JOptionPane.INFORMATION_MESSAGE);
                }

                //γέμισμα πίνακα αποτελεσμάτων
                for(int i=0;i<universities.size();i++){
                    tableModel.addRow(new Object[]{universities.get(i).getName(),universities.get(i).getCountry()});
                }
            }
            
            System.out.println("performSearch End for Country\n");//debugging
            return;
        }
        
        //ΑΝ Ο ΧΡΗΣΤΗΣ ΔΩΣΕΙ ΟΝΟΜΑ ΠΑΝΕΠΙΣΤΗΜΙΟΥ ΚΑΙ ΧΩΡΑ
        
        if (!name.isEmpty() && !country.isEmpty()){
            System.out.println("performSearch for Name and Country"); //debugging
            
            //δημιουργία List με αντικείμενα JavaUniversity
            //το false δηλώνει ότι θα χρησιμοποιήσει το API
            universities = urlCall.SearchUniversities(name, country, false);
            
            //αν το universities είναι κενό, θα ψάξει και στο αρχείο JSON τη χώρα
            //για βεβαιότητα
            if(universities.isEmpty()){
                //το true δηλώνει ότι θα ψάξει στο URL με το αρχείο JSON
                List<JavaUniversity> unisFromFile = urlCall.SearchUniversities("", "", true);
                
                if (!unisFromFile.isEmpty()){
                    universities.clear(); //καθάρισμα του universities
                    for(int i=0;i<unisFromFile.size();i++){
                        //αν βρεθούν πανεπιστήμια που περιέχουν το string country που
                        //έδωσε ο χρήστης, μπαίνουν στο universities
                        if(unisFromFile.get(i).getName().toLowerCase().contains(name.toLowerCase()) && 
                                unisFromFile.get(i).getCountry().contains(country)){
                            universities.add(unisFromFile.get(i));
                        }
                    }
                }
            }

            //debugging
            System.out.println("performSearch Size of universityList: " + universities.size());

            //αναδυόμενο παράθυρο με INFORMATION_MESSAGE
            if(universities.isEmpty()){
                JOptionPane.showMessageDialog(this, "No universities found.",
                        "Search results.",JOptionPane.INFORMATION_MESSAGE);
            }else{
                //αναδυόμενο παράθυρο με INFORMATION_MESSAGE για επιστρεφόμενο αριθμό πανεπιστημίων
                if(universities.size() == 1){
                    JOptionPane.showMessageDialog(this, "1 University found.",
                        "Search results.",JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(this,universities.size() + " Universities found.",
                        "Search results.",JOptionPane.INFORMATION_MESSAGE);
                }

                //γέμισμα πίνακα αποτελεσμάτων
                for(int i=0;i<universities.size();i++){
                    tableModel.addRow(new Object[]{universities.get(i).getName(),universities.get(i).getCountry()});
                }
            }
            
            System.out.println("performSearch End for Name and Country\n");//debugging
        }
        
        
    } //τέλος μεθόδου αναζήτησης και ενημέρωσης πίνακα
    
}
