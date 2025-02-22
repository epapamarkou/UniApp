package eap.uniapp.gui;

import eap.uniapp.UniApp;
import eap.uniapp.model.JavaUniversity;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    
    private final JPanel mainPanel;
    private final CardLayout cardLayout;
    private JavaUniversity selectedUni;
    
    
    //constructor κεντρικού frame
    public MainFrame(){
        setTitle("UniApp"); //ορισμός τίτλου εφαρμογής
        setSize(1000,700); //ορισμός μεγέθους του παραθύρου
        setLocationRelativeTo(null); //τοποθέτηση frame στο κέντρο της οθόνης
        setDefaultCloseOperation(EXIT_ON_CLOSE); //κλείσιμο εφαρμογής από το Χ του παραθύρου
        setLayout(new BorderLayout()); //ορισμός Layout Manager του Frame σε 5 ζώνες N,S,E,W,C
        
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
        JButton buttonSearch = createButton("Search",new Dimension(120,40),font,backgroundColor,foregroundColor,borderColor);
        JButton buttonStats = createButton("Statistics",new Dimension(100,40),font,backgroundColor,foregroundColor,borderColor);
        JButton buttonInfo = createButton("Team Info",new Dimension(120,40),font,backgroundColor,foregroundColor,borderColor);
        JButton buttonExit = createButton("Exit",new Dimension(100,40),font,backgroundColor,foregroundColor,borderColor);
        
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
        
        
        //προσθήκη listeners στα κουμπιά για εναλλαγή των JPanels
        buttonSearch.addActionListener(e -> cardLayout.show(mainPanel, "search")); 
        buttonStats.addActionListener(e -> cardLayout.show(mainPanel, "stats"));
        buttonInfo.addActionListener(e -> cardLayout.show(mainPanel, "info")); 
        buttonExit.addActionListener(e -> System.exit(0)); //κλείσιμο εφαρμογής από κουμπί Exit
        //buttonExit.addActionListener(e -> UniApp.exitApp()); //κλείσιμο εφαρμογής από κουμπί Exit
        
        cardLayout.show(mainPanel, "welcome"); //ξεκινά με το WelcomePanel
        
        setVisible(true);
    }//end constructor
    
    //setter για selectedUni
    public void setSelectedUni(JavaUniversity uni){
        this.selectedUni = uni;
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
    }
    
    //ΔΙΑΧΕΙΡΙΣΗ ΤΩΝ JPANELS
    //μέθοδος επιστροφής στο SearchPanel
    public void backToSearch(){ cardLayout.show(mainPanel, "search"); }
    
    //μέθοδος εμφάνισης ViewUniPanel
    public void showViewUniPanel(){
        //δημιουργία νέου ViewUniPanel με το επιλεγμένο πανεπιστήμιο και αναφορά στο MainFrame
        ViewUniPanel viewUniPanel = new ViewUniPanel(selectedUni,this);
        // προσθήκη του viewUniPanel στο mainPanel
        mainPanel.add(viewUniPanel, "view");
        // εμφάνιση στο mainPanel του ViewUniPanel με το επιλεγμένο πανεπιστήμιο
        cardLayout.show(mainPanel, "view");
    }
    
} //end MainFrame

