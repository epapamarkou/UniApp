package eap.uniapp.utils;

import com.itextpdf.text.BaseColor;
import eap.uniapp.db.University;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * <p>
 * Κλάση που υλοποιεί την εξαγωγή των δεδομένων σε αρχείο PDF, χρησιμοποιώντας την
 * βιβλιοθήκη iText. Δέχεται ως παράμετρο μια λίστα από {@link University} αντικείμενα,
 * τα οποία εμφανίζονται σε πίνακα (με στήλες για αύξοντα αριθμό, όνομα και αναζητήσεις).
 * </p>
 * <p>
 * Παρέχονται μέθοδοι για τη δημιουργία ονόματος αρχείου (συμπεριλαμβάνοντας timestamp)
 * και για την πραγματική διαδικασία εξαγωγής σε PDF, με εμφάνιση αντίστοιχων μηνυμάτων
 * επιτυχίας ή σφάλματος.
 * </p>
 * 
 */
public class PdfExport {
    //πεδία κλάσης

    /**
     * Η λίστα με τα αντικείμενα {@link University} που θα αποτυπωθούν στο PDF.
     */
    private List <University> data; //Η λίστα με τα University που θα αποτυπωθούν στο PDF
    
    /**
     * <p>
     * Constructor που αρχικοποιεί το αντικείμενο {@code PdfExport} με βάση τη λίστα
     * των πανεπιστημίων ({@link University}) που δίδεται ως παράμετρος.
     * </p>
     * 
     * @param data λίστα από {@link University} αντικείμενα που θα εμφανιστούν στο PDF
     */
    public PdfExport(List <University> data){
        this.data = data;
    }
    
    /**
     * <p>
     * Δημιουργεί το όνομα του αρχείου PDF, συνδυάζοντας ένα σταθερό πρόθεμα ("Results_")
     * με τη χρονοσφραγίδα (timestamp) της τρέχουσας ημερομηνίας/ώρας.
     * </p>
     * 
     * @return Ένα {@code String} που αναπαριστά το όνομα του αρχείου PDF 
     *         (π.χ. "Results_20230215_143000.pdf")
     */
    public String generateFileName(){
        // document name + timestamp 
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return "Results_" + timeStamp + ".pdf";
    }
    
    /**
     * <p>
     * Εξάγει τα δεδομένα της λίστας {@code data} σε αρχείο PDF. Σε περίπτωση
     * που η λίστα είναι κενή ή μηδενική, εμφανίζει μήνυμα σφάλματος και 
     * ακυρώνει τη διαδικασία.
     * </p>
     * <p>
     * Δημιουργείται ένα αρχείο PDF, στο οποίο εισάγεται τίτλος και ένας πίνακας
     * με τρεις στήλες: αύξων αριθμός, όνομα πανεπιστημίου και αριθμός αναζητήσεων.
     * Η μέθοδος καταλήγει σε παράθυρο διαλόγου που ενημερώνει για την επιτυχία
     * της εξαγωγής ή, σε περίπτωση σφάλματος, εμφανίζεται αντίστοιχο μήνυμα.
     * </p>
     * 
     * @throws DocumentException Εάν προκύψει σφάλμα κατά τη δημιουργία ή την επεξεργασία του PDF
     * @throws IOException Εάν δεν είναι δυνατή η εγγραφή στο αρχείο ή προκύψει άλλο I/O σφάλμα
     */
    public void exportToPdf() throws DocumentException, IOException{
        if (data == null || data.isEmpty()) {
            System.out.println("No university data available to export.");
            JOptionPane.showMessageDialog(
                    null,"No university data available to export.",
                    "Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //Δημιουργία εγγράφου PDF
        Document document = new Document();
        String filename = generateFileName();//δημιουργία ονόματος αρχείου με timestamp
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        document.open();
        
        //Προσθήκη τίτλου στο PDF
        Paragraph title = new Paragraph("Most Popular University Searches",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD,18));
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("\n")); //κενή γραμμή για αισθητικούς λόγους
        
        // Δημιουργία πίνακα με 3 στήλες
        PdfPTable pdfTable = new PdfPTable(3);
        
        //Ρύθμιση μήκους στηλών
        pdfTable.setWidths(new float[] {1f,3f,1f});
        
        // Δημιουργία κεφαλίδων για τον πίνακα
        PdfPCell headerCell1 = new PdfPCell(new Paragraph("#",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD,12)));
        headerCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell1.setBackgroundColor(new BaseColor(0xd0,0xed,0xef));
        
        PdfPCell headerCell2 = new PdfPCell(new Paragraph("University Name",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD,12)));
        headerCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell2.setBackgroundColor(new BaseColor(0xd0,0xed,0xef));
        
        PdfPCell headerCell3 = new PdfPCell(new Paragraph("Number of Searches",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD,12)));
        headerCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell3.setBackgroundColor(new BaseColor(0xd0,0xed,0xef));
        
        //Προσθήκη κεφαλίδων στον πίνακα
        pdfTable.addCell(headerCell1);
        pdfTable.addCell(headerCell2);
        pdfTable.addCell(headerCell3);
        
        // Γέμισμα του πίνακα με τα δεδομένα των πανεπιστημίων
        int rank = 1;
        for (University university : data) {
            // 1η στήλη: rank
            PdfPCell rankCell = new PdfPCell(new Paragraph(String.valueOf(rank),FontFactory.getFont(FontFactory.HELVETICA,12)));
            rankCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            rankCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfTable.addCell(rankCell);
            
            // 2η στήλη: name
            PdfPCell nameCell = new PdfPCell(new Paragraph(university.getName(),FontFactory.getFont(FontFactory.HELVETICA,12)));
            nameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            nameCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfTable.addCell(nameCell);
            
            // 3η στήλη: searches (ή 0 αν είναι null)
            PdfPCell searchesCell = new PdfPCell(new Paragraph(String.valueOf((university.getSearches() != null) ? university.getSearches() : 0),
                    FontFactory.getFont(FontFactory.HELVETICA,12)));
            searchesCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            searchesCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfTable.addCell(searchesCell);
            
            rank++;
        }

        // Προσθήκη του πίνακα στο PDF
        document.add(pdfTable);

        // Κλείσιμο εγγράφου
        document.close();
        
        // Ειδοποίηση για επιτυχή ολοκλήρωση εξαγωγής σε PDF
        System.out.println("PDF exported successfully as:" + 
                System.getProperty("user.dir") + "\\" + filename);//debugging
        JOptionPane.showMessageDialog(null,"PDF exported successfully at:\n" + filename,
                    "Export message",JOptionPane.INFORMATION_MESSAGE);
        
    }
    
}
