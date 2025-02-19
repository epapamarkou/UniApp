package eap.uniapp.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    //constructor κεντρικού frame
    public MainFrame(){
        setTitle("UniApp"); //ορισμός τίτλου εφαρμογής
        setSize(1000,600); //ορισμός μεγέθους του παραθύρου
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
        Color borderColor = new Color(0xffff00); //κιτρινο
        
        //δημιουργία κουμπιών με τη μέθοδο createButton
        JButton buttonSearch = createButton("Universities by Name",new Dimension(180,40),font,backgroundColor,foregroundColor,borderColor);
        JButton buttonList = createButton("Universities by Country",new Dimension(180,40),font,backgroundColor,foregroundColor,borderColor);
        JButton buttonStats = createButton("Statistics",new Dimension(100,40),font,backgroundColor,foregroundColor,borderColor);
        JButton buttonInfo = createButton("Team Info",new Dimension(120,40),font,backgroundColor,foregroundColor,borderColor);
        JButton buttonExit = createButton("Exit",new Dimension(100,40),font,backgroundColor,foregroundColor,borderColor);
        
        //τοποθέτηση των κουμπιών στο rightPanel
        rightPanel.add(buttonSearch);
        rightPanel.add(buttonList);
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
        mainPanel.add(new WelcomePanel()); //βγαίνει πρώτο με το που ανοίγει η εφαρμογή
        mainPanel.add(new SearchPanel(), "search");
        mainPanel.add(new CountryPanel(), "country");
        mainPanel.add(new StatisticsPanel(), "stats");
        mainPanel.add(new InfoPanel(), "info");
        mainPanel.add(new ViewUniPanel(), "view"); //αυτό θα βγαίνει με επιλογή είτε απ΄το SearchPanel, είτε απ΄το CountryPanel
        
        //προσθήκη listeners στα κουμπιά για εναλλαγή των JPanels
        buttonSearch.addActionListener(e -> cardLayout.show(mainPanel, "search")); 
        buttonList.addActionListener(e -> cardLayout.show(mainPanel, "country")); 
        buttonStats.addActionListener(e -> cardLayout.show(mainPanel, "stats"));
        buttonInfo.addActionListener(e -> cardLayout.show(mainPanel, "info")); 
        buttonExit.addActionListener(e -> System.exit(0)); //κλείσιμο εφαρμογής από κουμπί Exit
        
        setVisible(true);
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
    
} //end MainFrame

