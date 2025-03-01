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
 * <p>
 * Η κλάση {@code StatisticsPanel} εμφανίζει στατιστικά δεδομένα για τις αναζητήσεις των
 * πανεπιστημίων που αποθηκεύονται στη βάση δεδομένων. Σε αυτό το πάνελ, ένας πίνακας εμφανίζει
 * τα κορυφαία πανεπιστήμια με βάση τον αριθμό των αναζητήσεων, ενώ παρέχονται επιλογές για
 * εξαγωγή των δεδομένων σε αρχείο PDF ή εμφάνιση γραφήματος.
 * </p>
 * 
 */
public class StatisticsPanel extends JPanel{
    
    /**
     * Ο πίνακας που εμφανίζει τα στατιστικά αποτελέσματα (κατάταξη, όνομα και αναζητήσεις).
     */
    private final JTable universityTable;

    /**
     * Το μοντέλο δεδομένων που χρησιμοποιείται για την ενημέρωση και διαχείριση των γραμμών του πίνακα.
     */
    private final DefaultTableModel tableModel;
    
    /**
     * Η λίστα που περιέχει τα τρέχοντα αποτελέσματα (τα κορυφαία πανεπιστήμια βάσει αναζητήσεων).
     */
    private List<University> currentTopSearches;
    
    //constructor

    /**
     * <p>
     * Ο constructor της κλάσης {@code StatisticsPanel} δημιουργεί το UI για την εμφάνιση
     * των στατιστικών. Ορίζει τη διάταξη του πάνελ με BorderLayout και διαμορφώνει τα
     * στοιχεία της βόρειας, κεντρικής και νότια ζώνης, όπως ο τίτλος, τα φίλτρα, ο πίνακας
     * αποτελεσμάτων και τα κουμπιά δράσης (PDF export και γραφικό).
     * </p>
     */
    public StatisticsPanel(){
        
        setLayout(new BorderLayout());
        setBackground(new Color(0xd0edef)); //light blue
        
        // NORTH BORDER LAYOUT
        JLabel titleLabel = new JLabel("Statistics", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI",Font.BOLD,24));
        titleLabel.setForeground(new Color(0x003366));
        titleLabel.setOpaque(false);
        // προσθήκη κενού μεταξύ του titleLabel και των υπολοίπων στοιχείων
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        
        
        // CENTER BORDER LAYOUT
        
        // Δημιουργία panel για το φίλτρο "Top number of searches" και το ComboBox επιλογής
        JPanel topsearchPanel = new JPanel(new FlowLayout());
        topsearchPanel.setBackground(new Color(0xd0edef));
        
        // Label που εμφανίζει το μήνυμα για την επιλογή κορυφαίων αναζητήσεων
        JLabel messageLabel = new JLabel("Top number of searches:", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Segoe UI",Font.BOLD,20));
        messageLabel.setForeground(new Color(0x003366));
        
        // ComboBox επιλογής για τις κορυφαίες αναζητήσεις (5, 10, 15, 20)
        Integer [] options = {5,10,15,20};
        JComboBox<Integer> searchesComboBox = new JComboBox<>(options);
        searchesComboBox.setPreferredSize(new Dimension(60,30));
        searchesComboBox.setFont(new Font("Segoe UI",Font.BOLD,16));
        searchesComboBox.setBackground(new Color(0xffffff));
        
        // Listener για το ComboBox που ενημερώνει τον πίνακα με τα κορυφαία αποτελέσματα
        searchesComboBox.addActionListener((ActionEvent e) -> {
            int selectedValue = (Integer) searchesComboBox.getSelectedItem();
            updateTable(selectedValue);
        });
        
        // Προσθήκη των στοιχείων στο panel φίλτρου
        topsearchPanel.add(messageLabel);
        topsearchPanel.add(searchesComboBox);
        
        // Δημιουργία και διαμόρφωση του πίνακα αποτελεσμάτων
        
        // Ορισμός μοντέλου για το JTable και προσθήκη στηλών
        tableModel = new DefaultTableModel();
        tableModel.addColumn("#");
        tableModel.addColumn("University Name");
        tableModel.addColumn("Searches");
        
        // Δημιουργία JTable με το παραπάνω μοντέλο
        universityTable = new JTable(tableModel);
        universityTable.setFillsViewportHeight(true);
        // Διαμόρφωση header του πίνακα
        universityTable.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD,16));
        universityTable.getTableHeader().setBackground(new Color(0xc4dfe5));
        universityTable.getTableHeader().setForeground(new Color(0x003366));
        universityTable.getTableHeader().setPreferredSize(new Dimension(0,30));
        // Διαμόρφωση γραμματοσειράς για τα δεδομένα του πίνακα
        universityTable.setFont(new Font("Segoe UI",Font.PLAIN,16));
        // Ορισμός πλάτους στηλών
        universityTable.getColumnModel().getColumn(0).setPreferredWidth(20);
        universityTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        universityTable.getColumnModel().getColumn(2).setPreferredWidth(20);
        // Ορισμός ύψους γραμμών
        universityTable.setRowHeight(25);
        // Δημιουργία renderer για κεντρική στοίχιση ορισμένων στηλών
        DefaultTableCellRenderer cellrenderer = new DefaultTableCellRenderer();
        cellrenderer.setHorizontalAlignment(SwingConstants.CENTER);
        universityTable.getColumnModel().getColumn(0).setCellRenderer(cellrenderer);
        universityTable.getColumnModel().getColumn(2).setCellRenderer(cellrenderer);
        
        // Απενεργοποίηση επεξεργασίας κελιών
        universityTable.setDefaultEditor(Object.class, null);
        
        // Δημιουργία JScrollPane για το JTable
        JScrollPane uniTableScrollPane = new JScrollPane(universityTable);
        uniTableScrollPane.setPreferredSize(new Dimension(800,300));
        uniTableScrollPane.setBackground(new Color(0xd0edef));
        uniTableScrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Προσθήκη του JScrollPane στο panel φίλτρου
        topsearchPanel.add(uniTableScrollPane);
        
        
        // SOUTH BORDER LAYOUT
        
        // Δημιουργία κουμπιού εξαγωγής σε PDF
        JButton pdfButton = ButtonUtils.createButton("Export PDF", new Dimension(100,40),
                new Font("Segoe UI",Font.BOLD,16), new Color(0xffffff), new Color(0x003366), new Color(0x003366));
        pdfButton.addActionListener(e -> {
            System.out.println("Export PDF button pressed.");//debugging
            try{
                // Δημιουργία αντικειμένου PdfExport με τα τρέχοντα κορυφαία αποτελέσματα
                PdfExport pdfExport = new PdfExport(currentTopSearches);
                pdfExport.exportToPdf();
                
            } catch(IOException ex) {
                System.out.println("IOException error: " + ex.getMessage());
            } catch(DocumentException ex) {
                System.out.println("DocumentException error: " + ex.getMessage());
            }
        });
        
        // Δημιουργία κουμπιού για εμφάνιση γραφήματος
        JButton chartButton = ButtonUtils.createButton("Chart", new Dimension(100,40),
                new Font("Segoe UI",Font.BOLD,16), new Color(0xffffff), new Color(0x003366), new Color(0x003366));
        chartButton.addActionListener(e -> {
            System.out.println("Chart button pressed.");//debugging
            if (currentTopSearches == null || currentTopSearches.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "No data to display in chart. Please select a number of top searches first.",
                    "Chart Info",
                    JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }
            // Δημιουργία και εμφάνιση του γραφήματος με βάση τα κορυφαία αποτελέσματα
            CreateChart chartDialog = new CreateChart(SwingUtilities.getWindowAncestor(this), currentTopSearches);
            chartDialog.setVisible(true);
        });
        
        // Δημιουργία panel για τα κουμπιά δράσης στο κάτω μέρος
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,50,0));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        bottomPanel.add(pdfButton);
        bottomPanel.add(chartButton);
        
        // Προσθήκη των στοιχείων στο κύριο πάνελ με διάταξη BorderLayout
        add(titleLabel, BorderLayout.NORTH);
        add(topsearchPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
    } // end of constructor
    
    // ΜΕΘΟΔΟΙ ΚΛΑΣΗΣ STATISTICS
    
    /**
     * <p>
     * Ενημερώνει τον πίνακα αποτελεσμάτων με τα κορυφαία πανεπιστήμια βάσει του αριθμού αναζητήσεων.
     * Η μέθοδος ανακτά όλα τα πανεπιστήμια από τη βάση, ταξινομεί τα αποτελέσματα κατά φθίνουσα
     * σειρά με βάση το πεδίο "searches", περιορίζει τα αποτελέσματα στο πλήθος που έχει επιλεγεί,
     * και ενημερώνει το μοντέλο του πίνακα.
     * </p>
     * 
     * @param selection Ο αριθμός κορυφαίων αποτελεσμάτων που πρέπει να εμφανιστούν.
     */
    public void updateTable(Integer selection){
        // καθαρισμός του πίνακα αποτελεσμάτων
        tableModel.setRowCount(0);
        
        try{
            // Ανάκτηση όλων των αντικειμένων University από τη βάση δεδομένων
            List<University> topSearches = UniApp.controller.findUniversityEntities();
            
            // Ταξινόμηση της λίστας με βάση τις αναζητήσεις (searches) σε φθίνουσα σειρά
            topSearches.sort((u1, u2) -> {
                int s1 = (u1.getSearches() != null) ? u1.getSearches() : 0;
                int s2 = (u2.getSearches() != null) ? u2.getSearches() : 0;
                return Integer.compare(s2, s1);
            });
            
            // Περιορισμός των αποτελεσμάτων στις πρώτες "selection" εγγραφές
            if (topSearches.size() > selection) {
                topSearches = topSearches.subList(0, selection);
            }
            
            // Ενημέρωση του πίνακα με τα αποτελέσματα και προβολή του rank
            int rank = 1;
            for (University univ : topSearches) {
                tableModel.addRow(new Object[]{
                    rank,
                    univ.getName(),
                    univ.getSearches() == null ? 0 : univ.getSearches()
                });
                rank++;
            }
            
            // Αποθήκευση της περιορισμένης λίστας στο πεδίο currentTopSearches
            currentTopSearches = topSearches;
            
            System.out.println("Statistics table updated.");//debugging
            // Εμφάνιση μηνύματος επιτυχούς ενημέρωσης του πίνακα
            JOptionPane.showMessageDialog(this, "Table updated.",
                        "Info message.", JOptionPane.INFORMATION_MESSAGE);
            
        } catch(Exception ex) {
            System.out.println("updateTable error: " + ex.getMessage());
        }
        
    }
    
}
