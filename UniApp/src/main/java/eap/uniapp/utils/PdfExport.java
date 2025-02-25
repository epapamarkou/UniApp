package eap.uniapp.utils;

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
 * Κλάση που υλοποιεί την εξαγωγή των δεδομένων σε αρχείο PDF
 * με μια λίστα University δίνεται ως παράμετρος.
 * @author 
 */
public class PdfExport {
    //πεδία κλάσης
    private List <University> data; //Η λίστα με τα University που θα αποτυπωθούν στο PDF
    
    /**
     * Constructor με παράμετρο τη λίστα University
     * @param data λίστα University
     */
    public PdfExport(List <University> data){
        this.data = data;
    }
    
    /**
     * Δημιουργεί το όνομα του αρχείου PDF με timestamp
     * @return το όνομα του αρχείου
     */
    public String generateFileName(){
        // document name + timestamp 
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return "Results_" + timeStamp + ".pdf";
    }
    
    /**
     * Μέθοδος που εξάγει τα δεδομένα σε αρχείο PDF
     * @throws DocumentException
     * @throws IOException
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
        headerCell1.setVerticalAlignment(Element.ALIGN_CENTER);
        
        PdfPCell headerCell2 = new PdfPCell(new Paragraph("University Name",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD,12)));
        headerCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell2.setVerticalAlignment(Element.ALIGN_CENTER);
        
        PdfPCell headerCell3 = new PdfPCell(new Paragraph("Number of Searches",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD,12)));
        headerCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell3.setVerticalAlignment(Element.ALIGN_CENTER);
        
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
        JOptionPane.showMessageDialog(null,"PDF exported successfully as:\n" + filename,
                    "Export message",JOptionPane.INFORMATION_MESSAGE);
        
    }
    
}
