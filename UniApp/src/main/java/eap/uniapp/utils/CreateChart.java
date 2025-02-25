package eap.uniapp.utils;

import eap.uniapp.db.University;
import java.awt.*;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author Ersi
 */
public class CreateChart extends JDialog{
    
    /**
     * Constructor του διαλόγου (JDialog) που εμφανίζει το γράφημα.
     *
     * @param parent  Το "γονικό" παράθυρο (Window) από το οποίο ανοίγουμε το διάλογο,
     *                ώστε να εμφανίζεται modal (κεντρικά) σε σχέση με αυτό.
     * @param data    Η λίστα των University που περιέχει τις τιμές των searches για το γράφημα.
     */
    
    public CreateChart(Window parent, List<University> data) {
        super(parent, "Bar Chart", ModalityType.APPLICATION_MODAL);
        setSize(600, 400); //μέγεθος pop-up παραθύρου
        setLocationRelativeTo(parent); //κεντράρισμα παραθύρου σε σχέση με το γονικό παράθυρο

        // Δημιουργία JPanel (ChartPanel) που αναλαμβάνει να ζωγραφίσει το ραβδόγραμμα
        ChartPanel chartPanel = new ChartPanel(data);
        add(chartPanel, BorderLayout.CENTER);

        // Μπορούμε να προσθέσουμε και ένα κουμπί "Close" στο κάτω μέρος, αν θέλουμε, π.χ.:
        JButton closeButton = createButton("Close",new Dimension(60,30),new Font("Segoe UI",Font.BOLD,14),
                new Color(0xffffff),new Color(0x003366),new Color(0x003366));
        closeButton.addActionListener(e -> dispose()); //κλείσιμο pop-up
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    
    /**
     * Εσωτερική κλάση που επεκτείνει το JPanel και υλοποιεί τη λογική
     * σχεδίασης (rendering) ενός απλού ραβδογράμματος (bar chart).
     */
    private static class ChartPanel extends JPanel {
        private final List<University> data;

        /**
         * Constructor που λαμβάνει τη λίστα με τα δεδομένα (University).
         *
         * @param data  Η λίστα των University για το γράφημα.
         */
        public ChartPanel(List<University> data) {
            this.data = data;
            setPreferredSize(new Dimension(600, 400));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Μετατροπή του Graphics σε Graphics2D για καλύτερο (antialiasing) rendering
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Ορισμός περιθωρίων και υπολογισμός διαστάσεων
            int margin = 40;
            int width = getWidth() - 2 * margin;
            int height = getHeight() - 2 * margin;
            int originX = margin;
            int originY = margin;

            // Εντοπίζουμε τη μέγιστη τιμή των searches ώστε να κλιμακώσουμε το ύψος των bars
            int maxSearches = 0;
            for (University u : data) {
                int s = (u.getSearches() != null) ? u.getSearches() : 0;
                if (s > maxSearches) {
                    maxSearches = s;
                }
            }

            // Σχεδιάζουμε τους δύο άξονες (Χ - οριζόντιος, Υ - κατακόρυφος)
            g2.setColor(Color.BLACK);
            // Άξονας Χ
            g2.drawLine(originX, originY + height, originX + width, originY + height);
            // Άξονας Υ
            g2.drawLine(originX, originY, originX, originY + height);
            
            //Τίτλος γραφήματος
            String title = "Number of Searches per University";
            g2.setFont(new Font("Segoe UI",Font.BOLD,12));
            g2.drawString(title, CENTER_ALIGNMENT, CENTER_ALIGNMENT);
            
            
            // Έλεγχος αν υπάρχουν δεδομένα
            int barCount = data.size();
            if (barCount == 0) {
                return;
            }

            // Απόσταση (κενό) ανάμεσα στα bars
            int spacing = 10;
            // Το συνολικό πλάτος που απομένει για τις μπάρες
            int totalBarsWidth = width - (barCount + 1) * spacing;
            // Πλάτος κάθε μπάρας
            int barWidth = totalBarsWidth / barCount;

            // Θέση X για την πρώτη μπάρα
            int xPos = originX + spacing;

            // Σχεδίαση κάθε μπάρας
            for (University u : data) {
                int val = (u.getSearches() != null) ? u.getSearches() : 0;

                // Υπολογισμός σχετικού ύψους μπάρας ως ποσοστό του maxSearches
                int barHeight = 0;
                if (maxSearches > 0) {
                    barHeight = (int) ((double) val / maxSearches * height);
                }

                // Συντεταγμένη Y από την οποία ξεκινά η μπάρα (πάνω προς τα κάτω)
                int yPos = originY + (height - barHeight);

                // Χρώμα μπάρας
                g2.setColor(new Color(0x0077CC));
                g2.fillRect(xPos, yPos, barWidth, barHeight);

                // Περίγραμμα μπάρας
                g2.setColor(Color.BLACK);
                g2.drawRect(xPos, yPos, barWidth, barHeight);

                // Εμφάνιση της τιμής val πάνω από την μπάρα
                String valStr = String.valueOf(val);
                g2.drawString(valStr, xPos + (barWidth / 4), yPos - 5);

                // Εμφάνιση του ονόματος του Πανεπιστημίου κάτω από τη μπάρα
                // (σε περίπτωση που το όνομα είναι πολύ μεγάλο, το περικόπτουμε λίγο)
                String nameStr = u.getName();
                if (nameStr.length() > 10) {
                    nameStr = nameStr.substring(0, 10) + "...";
                }
                g2.drawString(nameStr, xPos + (barWidth / 4), originY + height + 15);

                // Ενημέρωση της επόμενης θέσης X
                xPos += barWidth + spacing;
            }
        }
    }
    
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
