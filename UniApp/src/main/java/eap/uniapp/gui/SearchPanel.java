package eap.uniapp.gui;

import eap.uniapp.model.JavaUniversity;
import eap.uniapp.utils.UrlCall;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class SearchPanel extends JPanel{
    
    // δήλωση μεταβλητών κλάσης
    private final JTextField nameField;
    private final JButton searchButton;
    private final UrlCall urlCall;
    private final JTable universityTable;
    private final DefaultTableModel tableModel;
    
    //constructor
    public SearchPanel(){
        
        setLayout(new BorderLayout());
        setBackground(new Color(0xd0edef)); //light blue
        
        //αρχικοποίηση urlCall
        urlCall = new UrlCall();
        
        //βόρεια ζώνη τίτλος Panel
        JLabel titleLabel = new JLabel("University Search", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial",Font.BOLD,20));
        //προσθήκη κενού μεταξύ titleLabel και mainPanel KAI 
        //μεταξύ titleLabel και searchPanel
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        add(titleLabel,BorderLayout.NORTH); //προσθήκη στο BorderLayout βόρεια
        
        //κεντρική ζώνη πεδίο αναζήτησης και κουμπι GO
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.setBackground(new Color(0xd0edef));
        searchPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        nameField = new JTextField(20); //φιλοξενεί 20 χαρακτήρες
        nameField.setPreferredSize(new Dimension(150,40)); //διαστάσεις κουτιου αναζήτησης
        //γραμματοσειρά που φαινεται οταν πληκτρολογεί ο χρήστης
        nameField.setFont(new Font("Arial",Font.PLAIN,20));
        
        searchButton = createButton("Go",new Dimension(100,40),new Font("Arial",Font.BOLD,14),
                new Color(0xffffff),new Color(0x003366),new Color(0x003366));
        
        //προσθήκη των nameField και searchButton στο searchPanel
        searchPanel.add(nameField);
        searchPanel.add(searchButton);
        
        //προσθήκη searchPanel στο BorderLayout κέντρο
        //add(searchPanel,BorderLayout.CENTER);
        
        //κάθετη διάταξη πίνακα αποτελεσμάτων
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(0xd0edef));
        
        //προσθήκη searchPanel στο centerPanel
        centerPanel.add(searchPanel);
        
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
        
    }
    
    //μέθοδος αναζήτησης και ενημέρωσης πίνακα
    private void performSearch(){
        
        //καθαρισμός πίνακα
        tableModel.setRowCount(0);

        //παίρνει το string του χρήστη και αφαιρεί τα whitespaces στην αρχή και στο τέλος
        String name = nameField.getText().trim();
        
        //αναδυόμενο παράθυρο με WARNING_MESSAGE
        if(name.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please enter a university name",
                    "Input required.",JOptionPane.WARNING_MESSAGE);
            return; //σταματά εδώ και δεν εκτελείται ο παρακάτω κώδικας
        }
        
        //δημιουργία List με αντικείμενα JavaUniversity
        List<JavaUniversity> universities = urlCall.SearchUniversities(name, "");
        
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
    } //τέλος μεθόδου αναζήτησης και ενημέρωσης πίνακα
    
    
    //μέθοδος δημιουργίας κουμπιών
    private JButton createButton(String button_text, Dimension size, Font font, 
            Color back_color, Color fore_color,Color border_color){
        
        JButton button = new JButton(button_text); //ορισμός κειμένου
        button.setPreferredSize(size); //ορισμός μεγέθους
        button.setFont(font); //ορισμός γραμματοσειράς
        button.setBackground(back_color); //ορισμός χρώματος background
        button.setForeground(fore_color); //ορισμός χρώματος κειμένου του κουμπιού
        
        //αφαίρεση εφέ γεμίσματος
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        
        //περίγραμμα χρώμα και πάχος γραμμής
        button.setBorder(BorderFactory.createLineBorder(border_color,3));
        
        return button;
    } //τέλος μεθόδου δημιουργίας κουμπιών
    
    
}
