package eap.uniapp.utils;

import eap.uniapp.db.University;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;
import javax.swing.*;

/**
 * <p>
 * Η κλάση {@code CreateChart} επεκτείνει το {@link JDialog} και δημιουργεί
 * έναν διάλογο που εμφανίζει ένα ραβδόγραμμα (bar chart) με βάση τα δεδομένα
 * αναζητήσεων ({@code searches}) των αντικειμένων {@link University}.
 * </p>
 * <p>
 * Προσφέρει έναν {@code ChartPanel} όπου υλοποιείται η λογική σχεδίασης
 * του ραβδογράμματος, καθώς και έναν μηχανισμό για το κλείσιμο του διαλόγου.
 * </p>
 * 
 * <p>
 * Ο διάλογος είναι modal σε σχέση με το γονικό {@code Window}, γεγονός που σημαίνει
 * ότι πρέπει να κλείσει προτού ο χρήστης αλληλεπιδράσει ξανά με το κύριο παράθυρο.
 * </p>
 * 
 * <p>
 * Για τη σχεδίαση των ράβδων, υπολογίζεται η μέγιστη τιμή αναζητήσεων μεταξύ των
 * πανεπιστημίων για τον καθορισμό του ύψους κάθε ράβδου αναλογικά.
 * </p>
 * 
 * <p>
 * Χρησιμοποιεί {@link ButtonUtils#createButton} για τη δημιουργία ενός κουμπιού
 * "Close" στο κάτω μέρος, το οποίο κλείνει τον διάλογο.
 * </p>
 * 
 */
public class CreateChart extends JDialog {

    /**
     * <p>
     * Constructor του διαλόγου (JDialog) που εμφανίζει το γράφημα. Ο διάλογος
     * ορίζεται ως modal και εμφανίζεται κεντραρισμένος σε σχέση με το πατρικό
     * {@link Window}.
     * </p>
     * 
     * @param parent  Το "γονικό" παράθυρο ({@link Window}) από το οποίο ανοίγουμε το διάλογο,
     *                ώστε να εμφανίζεται modal (κεντρικά) σε σχέση με αυτό.
     * @param data    Η λίστα των {@link University} που περιέχει τις τιμές των
     *                αναζητήσεων ({@code searches}) για το γράφημα.
     */
    public CreateChart(Window parent, List<University> data) {
        super(parent, "Bar Chart", Dialog.ModalityType.APPLICATION_MODAL);
        setSize(1000, 900); //μέγεθος pop-up παραθύρου
        setLocationRelativeTo(parent); //κεντράρισμα παραθύρου σε σχέση με το γονικό παράθυρο

        // Δημιουργία JPanel (ChartPanel) που αναλαμβάνει να ζωγραφίσει το ραβδόγραμμα
        ChartPanel chartPanel = new ChartPanel(data);
        add(chartPanel, BorderLayout.CENTER);

        // Κουμπί "Close" στο κάτω μέρος
        JButton closeButton = ButtonUtils.createButton("Close", new Dimension(60,30), new Font("Segoe UI",Font.BOLD,14),
                                           new Color(0xffffff), new Color(0x003366), new Color(0x003366));
        closeButton.addActionListener(e -> dispose()); //κλείσιμο pop-up
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Εσωτερική κλάση που επεκτείνει το {@link JPanel} και υλοποιεί τη λογική
     * σχεδίασης (rendering) ενός απλού ραβδογράμματος (bar chart). Λαμβάνει ως είσοδο
     * μία λίστα από {@link University} αντικείμενα, από τα οποία αντλεί τις τιμές
     * των αναζητήσεων ({@code searches}).
     */
    private static class ChartPanel extends JPanel {

        /**
         * Η λίστα που περιέχει τα αντικείμενα {@link University}, από όπου λαμβάνονται
         * οι τιμές που θα απεικονιστούν στο ραβδόγραμμα.
         */
        private final List<University> data;

        // Διαχωρισμένα margins για πάνω/κάτω/αριστερά/δεξιά

        /**
         * Περιθώριο (margin) στο επάνω μέρος της περιοχής σχεδίασης.
         */
        private static final int MARGIN_TOP = 40;

        /**
         * Περιθώριο (margin) στο κάτω μέρος της περιοχής σχεδίασης. 
         * Αυξήθηκε για να χωράνε τα ονόματα των Πανεπιστημίων σε κλίση.
         */
        private static final int MARGIN_BOTTOM = 180; // αυξήθηκε για να χωράνε τα ονόματα

        /**
         * Περιθώριο (margin) στην αριστερή πλευρά της περιοχής σχεδίασης.
         */
        private static final int MARGIN_LEFT = 40;

        /**
         * Περιθώριο (margin) στη δεξιά πλευρά της περιοχής σχεδίασης.
         */
        private static final int MARGIN_RIGHT = 40;

        /**
         * <p>
         * Constructor που λαμβάνει τη λίστα με τα δεδομένα ({@link University})
         * και διαμορφώνει το περιβάλλον σχεδίασης (π.χ. μέγεθος και φόντο).
         * </p>
         * 
         * @param data  Η λίστα των {@link University} για το γράφημα.
         */
        public ChartPanel(List<University> data) {
            this.data = data;
            setPreferredSize(new Dimension(1200, 800));
            setBackground(Color.WHITE);
        }

        /**
         * <p>
         * Υπερκαλύπτει τη μέθοδο {@link JPanel#paintComponent(Graphics)} για να υλοποιήσει
         * τη σχεδίαση του ραβδογράμματος. Υπολογίζει τα διαθέσιμα όρια σχεδίασης, σχεδιάζει
         * τους άξονες, υπολογίζει το ύψος κάθε μπάρας ανάλογα με το μέγιστο {@code searches}
         * και γράφει τα ονόματα των Πανεπιστημίων σε κλίση στο κάτω μέρος του γραφήματος.
         * </p>
         * 
         * @param g Το αντικείμενο {@link Graphics} που χρησιμοποιείται για τη σχεδίαση.
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Μετατροπή του Graphics σε Graphics2D για καλύτερο (antialiasing) rendering
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Υπολογισμός διαθέσιμου πλάτους/ύψους με βάση τα margins
            int width = getWidth() - MARGIN_LEFT - MARGIN_RIGHT;
            int height = getHeight() - MARGIN_TOP - MARGIN_BOTTOM;
            int originX = MARGIN_LEFT;
            int originY = MARGIN_TOP;

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

            // Τίτλος γραφήματος (κεντραρισμένος και ορατός)
            String title = "Number of Searches per University";
            g2.setFont(new Font("Segoe UI", Font.BOLD, 18));
            FontMetrics fm = g2.getFontMetrics();
            int titleWidth = fm.stringWidth(title);
            int titleX = (getWidth() - titleWidth) / 2;
            // Τοποθετεί τον τίτλο στο κέντρο, προς το πάνω μέρος του panel
            int titleY = (MARGIN_TOP / 2) + fm.getAscent();
            g2.drawString(title, titleX, titleY);

            // 1) Προσθήκη ονόματος στον άξονα Υ ("Number of Searches")
            String yAxisLabel = "Number of Searches";
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            AffineTransform oldTransform = g2.getTransform();
            g2.translate(originX - 15, originY + height / 2);
            g2.rotate(Math.toRadians(-90));
            g2.drawString(yAxisLabel, 0, 0);
            g2.setTransform(oldTransform);

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

                // Συντεταγμένη Y από την οποία ξεκινά η μπάρα
                int yPos = originY + (height - barHeight);

                // Χρώμα μπάρας
                g2.setColor(new Color(0x4169E1));
                g2.fillRect(xPos, yPos, barWidth, barHeight);

                // Περίγραμμα μπάρας
                g2.setColor(Color.BLACK);
                g2.drawRect(xPos, yPos, barWidth, barHeight);

                // Εμφάνιση της τιμής val πάνω από την μπάρα
                String valStr = String.valueOf(val);
                g2.drawString(valStr, xPos + (barWidth / 4), yPos - 5);

                // Εμφάνιση του ονόματος του Πανεπιστημίου κάτω από τη μπάρα
                // Κόβουμε στα 20 characters και προσθέτουμε "..." αν είναι μεγαλύτερο
                String nameStr = u.getName() != null ? u.getName() : "";
                if (nameStr.length() > 25) {
                    nameStr = nameStr.substring(0, 25) + "...";
                }

                // Move text further down & rotate so it is visible
                AffineTransform old = g2.getTransform();
                // Αυξάνουμε το translate + 160 αν θέλετε πιο κάτω
                g2.translate(xPos + (barWidth / 2), originY + height + 160);
                g2.rotate(Math.toRadians(-85));
                g2.drawString(nameStr, 0, 0);
                g2.setTransform(old);

                // Ενημέρωση της επόμενης θέσης X
                xPos += barWidth + spacing;
            }
        }
    }

}
