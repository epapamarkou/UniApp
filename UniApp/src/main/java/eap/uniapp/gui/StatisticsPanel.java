package eap.uniapp.gui;

import com.itextpdf.text.DocumentException;
import eap.uniapp.UniApp;
import eap.uniapp.db.University;
import eap.uniapp.utils.ButtonUtils;
import eap.uniapp.utils.CreateChart;
import eap.uniapp.utils.PdfExport;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author 
 */
public class StatisticsPanel extends JPanel{
    
    private final JTable universityTable;
    private final DefaultTableModel tableModel;
    // Λίστα που περιέχει τα τρέχοντα αποτελέσματα (τα top Search πανεπιστήμια)
    private List<University> currentTopSearches;
    
    //constructor

    /**
     *
     */
    public StatisticsPanel(){
        
        setLayout(new BorderLayout());
        setBackground(new Color(0xd0edef)); //light blue
        
        //NORTH BORDER LAYOUT
        JLabel titleLabel = new JLabel("Statistics", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI",Font.BOLD,24));
        titleLabel.setForeground(new Color(0x003366));
        titleLabel.setOpaque(false);
        //προσθήκη κενού μεταξύ titleLabel και mainPanel KAI 
        //μεταξύ titleLabel και searchPanel
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        
        
        //CENTER BORDER LAYOUT
        
        //κεντρική ζώνη πεδίο για μήνυμα top searches και combo box
        JPanel topsearchPanel = new JPanel(new FlowLayout());
        topsearchPanel.setBackground(new Color(0xd0edef));
        
        //αρχικοποίηση Label που φιλοξενεί το μήνυμα για top searches
        JLabel messageLabel = new JLabel("Top number of searches:", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Segoe UI",Font.BOLD,20));
        messageLabel.setForeground(new Color(0x003366));
        
        //αρχικοποίηση ComboBox που θα φιλοξενεί ακέραιους με τις 5-10-15-20 top searches
        Integer [] options = {5,10,15,20};
        JComboBox<Integer> searchesComboBox = new JComboBox<>(options);
        searchesComboBox.setPreferredSize(new Dimension(60,30));//διαστάσεις κουτιου αναζήτησης χώρας
        searchesComboBox.setFont(new Font("Segoe UI",Font.BOLD,16));
        searchesComboBox.setBackground(new Color(0xffffff));
        
        //Listener για Combobox
        searchesComboBox.addActionListener((ActionEvent e) -> {
            int selectedValue = (Integer) searchesComboBox.getSelectedItem();
            updateTable(selectedValue);
        });
        
        //προσθήκη στο topsearchPanel των messageLabel και searchesComboBox
        topsearchPanel.add(messageLabel);
        topsearchPanel.add(searchesComboBox);
        
        //εμφάνιση πίνακα αποτελεσμάτων
        
        //ορισμός μοντέλου για χειρισμό του JTable
        tableModel = new DefaultTableModel();
        //ορισμών στηλών του πίνακα
        tableModel.addColumn("#");
        tableModel.addColumn("University Name");
        tableModel.addColumn("Searches");
        
        //δημιουργία JTable με όρισμα την πηγή δεδομένων
        universityTable = new JTable(tableModel);
        universityTable.setFillsViewportHeight(true);
        //αλλαγή γραμματοσειράς και φόντου headers πίνακα
        universityTable.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD,16));
        universityTable.getTableHeader().setBackground(new Color(0xc4dfe5));
        universityTable.getTableHeader().setForeground(new Color(0x003366));
        universityTable.getTableHeader().setPreferredSize(new Dimension(0,30));
        //αλλαγή γραμματοσειράς δεδομένων πίνακα
        universityTable.setFont(new Font("Segoe UI",Font.PLAIN,16));
        //μήκος γραμμών πίνακα
        universityTable.getColumnModel().getColumn(0).setPreferredWidth(20);
        universityTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        universityTable.getColumnModel().getColumn(2).setPreferredWidth(20);
        //ύψος γραμμών πίνακα
        universityTable.setRowHeight(25);
        //δημιουργία renderer για στοιχιση στο κέντρο
        DefaultTableCellRenderer cellrenderer = new DefaultTableCellRenderer();
        cellrenderer.setHorizontalAlignment(SwingConstants.CENTER);
        //εφαρμογή στη στήλη #
        universityTable.getColumnModel().getColumn(0).setCellRenderer(cellrenderer);
        //εφαρμογή στη στήλη Searches
        universityTable.getColumnModel().getColumn(2).setCellRenderer(cellrenderer);
        
        //απενεργοποίηση επεξεργασίας κελιών πίνακα
        universityTable.setDefaultEditor(Object.class, null);
        
        //δημιουργία JScrollPane και προσθήκη σ'αυτο του universityTable
        JScrollPane uniTableScrollPane = new JScrollPane(universityTable);
        uniTableScrollPane.setPreferredSize(new Dimension(800,300));
        uniTableScrollPane.setBackground(new Color(0xd0edef));
        
        //περιθώρια γύρω από τον πίνακα
        uniTableScrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        //προσθήκη uniTableScrollPane στο centerPanel
        topsearchPanel.add(uniTableScrollPane);
        
        
        //SOUTH BORDER LAYOUT
        
        //κουμπί δημιουργίας αρχείου PDF
        JButton pdfButton = ButtonUtils.createButton("Export PDF",new Dimension(100,40),new Font("Segoe UI",Font.BOLD,16),
                new Color(0xffffff),new Color(0x003366),new Color(0x003366));
        pdfButton.addActionListener(e -> {
            
            try{
                //Δημιουργία αντικειμένου PdfExport με τα δεδομένα των πανεπιστημίων
                PdfExport pdfExport = new PdfExport(currentTopSearches);
                //Εξαγωγή δεδομένων σε αρχείο PDF / info message από τη μέθοδο exportToPdf
                pdfExport.exportToPdf();
                
            }catch(IOException ex){
                //Ενημέρωση για λάθος στην εξαγωγή αρχείου
                System.out.println("IOException error: " + ex.getMessage());
                
            }catch(DocumentException ex){
                //Ενημέρωση για λάθος στη δημιουργία εγγράφου
                System.out.println("DocumentException error: " + ex.getMessage());
            }
        });
        
        //κουμπί δημιουργίας γραφήματος
        JButton chartButton = ButtonUtils.createButton("Chart",new Dimension(100,40),new Font("Segoe UI",Font.BOLD,16),
                new Color(0xffffff),new Color(0x003366),new Color(0x003366));
        chartButton.addActionListener(e -> {
            /*
            Ελέγχουμε εάν η λίστα "currentTopSearches" είναι είτε null είτε κενή.
            Αν ισχύει αυτό, εμφανίζουμε ένα παράθυρο (JOptionPane) που ενημερώνει 
            τον χρήστη ότι δεν υπάρχουν διαθέσιμα δεδομένα για εμφάνιση
            στο γράφημα, και στη συνέχεια τερματίζουμε πρόωρα τη μέθοδο (return).
            */
            if (currentTopSearches == null || currentTopSearches.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "No data to display in chart. Please select a number of top searches first.",
                    "Chart Info",
                    JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }
            // Δημιουργία και εμφάνιση του γραφήματος
            CreateChart chartDialog = new CreateChart(SwingUtilities.getWindowAncestor(this), currentTopSearches);
            chartDialog.setVisible(true);
        });
        
        
        //δημιουργία Panel που φιλοξενεί τα κουμπιά
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,50,0));//απόσταση 20pixels οριζόντια
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        bottomPanel.add(pdfButton);
        bottomPanel.add(chartButton);
        
        //προσθήκη στο κεντρικό JPanel
        add(titleLabel,BorderLayout.NORTH); //προσθήκη στο BorderLayout βόρεια
        add(topsearchPanel,BorderLayout.CENTER); //προσθήκη στο BorderLayout κέντρο
        add(bottomPanel,BorderLayout.SOUTH); //προσθήκη στο BorderLayout νότια
        
    }//end of constructor
    
    //ΜΕΘΟΔΟΙ ΚΛΑΣΗΣ STATISTICS
    
    //μέθοδος γεμίσματος πίνακα με τις κορυφαίες αναζητήσεις
    //μέθοδος γεμίσματος πίνακα με τις κορυφαίες αναζητήσεις

    /**
     *
     * @param selection
     */
    public void updateTable(Integer selection){
        //καθαρισμός πίνακα
        tableModel.setRowCount(0);
        
        try{
            // Παίρνουμε όλα τα University αντικείμενα από τη Βάση Δεδομένων,
            // καλώντας τη μέθοδο findUniversityEntities του controller.
            // Έπειτα τα αποθηκεύουμε στη λίστα topSearches.
            List<University> topSearches = UniApp.controller.findUniversityEntities();
            
            // Ταξινομούμε τη λίστα topSearches κατά φθίνουσα σειρά
            // με βάση το πεδίο searches. Εάν το searches ενός University είναι null,
            // τότε το αντιμετωπίζουμε σαν 0.
            topSearches.sort((u1, u2) -> {
                // Έλεγχος αν το searches είναι null
                int s1 = (u1.getSearches() != null) ? u1.getSearches() : 0;
                int s2 = (u2.getSearches() != null) ? u2.getSearches() : 0;
                // Επιστρέφουμε σύγκριση κατά φθίνουσα σειρά (δηλ. το μεγαλύτερο πρώτα)
                return Integer.compare(s2, s1);
            });
            
            // Περιορίζουμε τη λίστα στις πρώτες 'selection' εγγραφές, δηλαδή
            // κρατάμε μόνο τα κορυφαία N (selection) πανεπιστήμια από τη λίστα
            if (topSearches.size() > selection) {
                topSearches = topSearches.subList(0, selection);
            }
            
            // Διάσχιση (loop) της φιλτραρισμένης/ταξινομημένης λίστας topSearches
            // και προσθήκη των δεδομένων στο tableModel. 
            // Το rank κρατάει τη σειρά κατάταξης (1,2,3...).
            int rank = 1;
            for (University univ : topSearches) {
                // Εάν το getSearches() είναι null το αντικαθιστούμε με 0 κατά την εμφάνιση
                tableModel.addRow(new Object[]{
                    rank,
                    univ.getName(),
                    univ.getSearches() == null ? 0 : univ.getSearches()
                });
                rank++;
            }
            
            // Αποθήκευση της (περιορισμένης) λίστας στο πεδίο currentTopSearches
            currentTopSearches = topSearches;
            
            // Εμφάνιση μηνύματος επιτυχούς ενημέρωσης του πίνακα στον χρήστη.
            JOptionPane.showMessageDialog(this, "Table updated.",
                        "Info message.",JOptionPane.INFORMATION_MESSAGE);
            
        }catch(Exception ex){
            System.out.println("updateTable error: " + ex.getMessage());
        }
        
    }
    
    
}
