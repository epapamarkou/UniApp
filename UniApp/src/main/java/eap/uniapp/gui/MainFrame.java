package eap.uniapp.gui;

import eap.uniapp.UniApp;
import eap.uniapp.model.JavaUniversity;
import eap.uniapp.utils.ButtonUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Ersi
 */
public class MainFrame extends JFrame{
    
    private final JPanel mainPanel;
    private final CardLayout cardLayout;
    private JavaUniversity selectedUni;
    private ViewUniPanel viewUniPanel;
    
    
    /**
     * constructor κεντρικού frame
     */
    public MainFrame(){
        setTitle("UniApp"); //ορισμός τίτλου εφαρμογής
        setSize(1000,700); //ορισμός μεγέθους του παραθύρου
        setLocationRelativeTo(null); //τοποθέτηση frame στο κέντρο της οθόνης
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //αποτροπή κλεισίματος εφαρμογής από το Χ του παραθύρου
        setLayout(new BorderLayout()); //ορισμός Layout Manager του Frame σε 5 ζώνες N,S,E,W,C
        
        //προσθήκη Listener για το κλείσιμο της εφαρμογής από το Χ του παραθύρου
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                closeDatabase();
                System.exit(0);
                System.out.println("App closed from Window X.");//debugging
            }
        });
        
        //βόρεια ζώνη με logo και κουμπιά
        
        //δημιουργία background στη βόρεια ζώνη και καθορισμός αυτής
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(new Color(0xa9cdd7));
        backgroundPanel.setPreferredSize(new Dimension(1000,100));
        backgroundPanel.setLayout(new BorderLayout());
        
        //δημιουργία ενός panel για logo και κουμπιά
        JPanel contentPanel = new JPanel (new BorderLayout()); 
        contentPanel.setOpaque(false);
        
        //δημιουργία αριστερού Panel για το logo
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,25,20));
        leftPanel.setOpaque(false);
        
        //logo UniApp
        JLabel logo = new JLabel("UniApp");
        logo.setFont(new Font("Segoe UI",Font.BOLD,32));
        logo.setBackground(new Color(0xffffff));
        logo.setForeground(new Color(0x003366));
        logo.setOpaque(true);
        logo.setPreferredSize(new Dimension(150,60));
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setVerticalAlignment(SwingConstants.CENTER);
        leftPanel.add(logo);
        
        //δημιουργία δεξιού Panel για τα κουμπιά
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,30));
        rightPanel.setOpaque(false);
        
        //δημιουργία κουμπιών
        //καθορισμός παραμέτρων
        Font font = new Font("Arial",Font.BOLD,14);
        Color backgroundColor = new Color(0xffffff); //λευκό
        Color foregroundColor = new Color(0x003366); //μπλε
        Color borderColor = new Color(0x003366); //μπλε
        
        //δημιουργία κουμπιών με τη μέθοδο createButton
        JButton buttonSearch = ButtonUtils.createButton("Search",new Dimension(120,40),font,backgroundColor,foregroundColor,borderColor);
        JButton buttonStats = ButtonUtils.createButton("Statistics",new Dimension(100,40),font,backgroundColor,foregroundColor,borderColor);
        JButton buttonInfo = ButtonUtils.createButton("Team Info",new Dimension(120,40),font,backgroundColor,foregroundColor,borderColor);
        JButton buttonExit = ButtonUtils.createButton("Exit",new Dimension(100,40),font,backgroundColor,foregroundColor,borderColor);
        
        //τοποθέτηση των κουμπιών στο rightPanel
        rightPanel.add(buttonSearch);
        rightPanel.add(buttonStats);
        rightPanel.add(buttonInfo);
        rightPanel.add(buttonExit);
        
        //προσθήκη leftPanel και rightPanel στο contentPanel
        contentPanel.add(leftPanel,BorderLayout.WEST);
        contentPanel.add(rightPanel,BorderLayout.EAST);
        
        //προσθήκη contentPanel στο backgroundPanel
        backgroundPanel.add(contentPanel,BorderLayout.CENTER);
        
        //προσθήκη του backgroundPanel στο JFrame στη βόρεια ζώνη του BorderLayout
        add(backgroundPanel, BorderLayout.NORTH);
        
        //δημιουργία διαχειριστή διάταξης για εναλλαγή διαφορετικών JPanels στο κέντρο του JFrame
        cardLayout = new CardLayout(); //επιτρέπει διαφορετικά JPanel στο ίδιο σημείο
        //δημιουργία JPanel με διάταξη CardLayout
        mainPanel = new JPanel(cardLayout);
        //προσθήκη του mainPanel στο JFrame με κατεύθυνση ΚΕΝΤΡΟ
        add(mainPanel,BorderLayout.CENTER);
        
        
        //ΔΗΜΙΟΥΡΓΙΑ ΔΙΑΦΟΡΕΤΙΚΏΝ JPanels και προσθήκη αυτών στο mainPanel
        mainPanel.add(new WelcomePanel(), "welcome");
        mainPanel.add(new SearchPanel(this), "search");
        mainPanel.add(new StatisticsPanel(), "stats");
        mainPanel.add(new InfoPanel(), "info");
        //
        viewUniPanel = new ViewUniPanel(null,this);
        mainPanel.add(viewUniPanel, "view");
        
        //προσθήκη listeners στα κουμπιά για εναλλαγή των JPanels
        buttonSearch.addActionListener(e -> cardLayout.show(mainPanel, "search")); 
        buttonStats.addActionListener(e -> cardLayout.show(mainPanel, "stats"));
        buttonInfo.addActionListener(e -> cardLayout.show(mainPanel, "info")); 
        buttonExit.addActionListener(e -> {
            closeDatabase();
            System.exit(0);
            System.out.println("App closed from button exit.");//debugging
        }); //κλείσιμο εφαρμογής από κουμπί Exit
        
        cardLayout.show(mainPanel, "welcome"); //ξεκινά με το WelcomePanel
        
        setVisible(true);
    }//end constructor
    
    
    /**
     * μέθοδος κλεισίματος Entity Manager Factory πριν το κλείσιμο της εφαρμογής
     */
    public void closeDatabase(){
        if(UniApp.emf != null){
            UniApp.emf.close();
            System.out.println("Entity Manager Factory closed.");
        }
    }
    
    /**
     * setter για selectedUni
     * @param uni
     */
    public void setSelectedUni(JavaUniversity uni){
        this.selectedUni = uni;
    }
    
    
    //ΔΙΑΧΕΙΡΙΣΗ ΤΩΝ JPANELS μέσω του Mainframe
    
    /**
     * μέθοδος επιστροφής στο SearchPanel
     */
    public void backToSearch(){ cardLayout.show(mainPanel, "search"); }
    
    /**
     * μέθοδος εμφάνισης ViewUniPanel
     */
    public void showViewUniPanel(){
        // ενημέρωση με το επιλεγμένο πανεπιστήμιο
        viewUniPanel.updateUniversity(selectedUni);
        // εμφάνιση στο mainPanel του ViewUniPanel με το επιλεγμένο πανεπιστήμιο
        cardLayout.show(mainPanel, "view");
    }
    
} //end MainFrame

