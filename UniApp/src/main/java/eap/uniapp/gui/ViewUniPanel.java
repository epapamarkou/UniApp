package eap.uniapp.gui;

import eap.uniapp.model.JavaUniversity;
import javax.swing.*;
import java.awt.*;


public class ViewUniPanel extends JPanel{
    //δήλωση μεταβλητών κλάσης
    private final JavaUniversity university;
    private MainFrame mainFrame;
    
    //constructor
    public ViewUniPanel(JavaUniversity university,MainFrame mainFrame){
        this.university = university;
        this.mainFrame = mainFrame;
        
        setLayout(new BorderLayout());
        setBackground(new Color(0xd0edef)); //light blue
        
        //τίτλος πανεπιστημίου
        JLabel titleLabel = new JLabel(university.getName(),SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI",Font.BOLD,26));
        titleLabel.setBackground(new Color(0xffffff));
        titleLabel.setForeground(new Color(0x003366));
        titleLabel.setOpaque(false);
        titleLabel.setPreferredSize(new Dimension(150,60));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        //ΠΛΗΡΟΦΟΡΙΕΣ ΠΑΝΕΠΙΣΤΗΜΙΟΥ
        
        //δημιουργία των JTextFields
        JTextField countryField = createTextField(university.getCountry());
        JTextField stateProninceField = createTextField(university.getStateProvince());
        JTextField alphaTwoCodeField = createTextField(university.getAlphaTwoCode());
        JTextField webPagesField = createTextField(String.join(", ", university.getWebPages()));
        JTextField domainsField = createTextField(String.join(", ", university.getDomains()));
        JTextField contactField = createTextField("");
        JTextField descriptionField = createTextField("");
        
        //δημιουργία του infoPanel που θα τις πληροφορίες σε δύο στήλες Όνομα-Τιμή
        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel,BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(0xffffff));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 300, 50, 50));//top-left-bottom-right
        
        //προσθήκη Ονόματος πεδίου - Τιμής στο infoPanel
        //Country
        infoPanel.add(createInfoRow("Country:", countryField, new Dimension(350, 30)));
        infoPanel.add(Box.createRigidArea(new Dimension(0,10)));
        //state-pronince
        infoPanel.add(createInfoRow("State-Province:", stateProninceField, new Dimension(350, 30)));
        infoPanel.add(Box.createRigidArea(new Dimension(0,10)));
        //alphaTwoCode
        infoPanel.add(createInfoRow("Alpha-2 Code:", alphaTwoCodeField, new Dimension(350, 30)));
        infoPanel.add(Box.createRigidArea(new Dimension(0,10)));
        //webPages
        infoPanel.add(createInfoRow("Web page:", webPagesField, new Dimension(350, 30)));
        infoPanel.add(Box.createRigidArea(new Dimension(0,10)));
        //domains
        infoPanel.add(createInfoRow("Domain:", domainsField, new Dimension(350, 30)));
        infoPanel.add(Box.createRigidArea(new Dimension(0,10)));
        //contact
        infoPanel.add(createInfoRow("Contact:", contactField, new Dimension(350, 60)));
        infoPanel.add(Box.createRigidArea(new Dimension(0,10)));
        //description
        infoPanel.add(createInfoRow("Description:", descriptionField, new Dimension(350, 90)));
        
        
        //κουμπί edit
        JButton editButton = createButton("Edit",new Dimension(100,40),new Font("Segoe UI",Font.BOLD,16),
                new Color(0xffffff),new Color(0x003366),new Color(0x003366));
        editButton.addActionListener(e -> {
            //τα πεδία γίνονται επεξεργάσιμα με το πάτημα του editButton
            countryField.setEditable(true);
            stateProninceField.setEditable(true);
            alphaTwoCodeField.setEditable(true);
            webPagesField.setEditable(true);
            domainsField.setEditable(true);
            contactField.setEditable(true);
            descriptionField.setEditable(true);
        });
        
        //κουμπί αποθήκευση στη ΒΔ
        JButton saveToDBButton = createButton("Save to DB",new Dimension(100,40),new Font("Segoe UI",Font.BOLD,16),
                new Color(0xffffff),new Color(0x003366),new Color(0x003366));
        //saveToDBButton.addActionListener(e -> mainFrame.backToSearch());
        
        
        //κουμπί πίσω
        JButton backButton = createButton("Back",new Dimension(100,40),new Font("Segoe UI",Font.BOLD,16),
                new Color(0xffffff),new Color(0x003366),new Color(0x003366));
        backButton.addActionListener(e -> mainFrame.backToSearch());//κλήση επιστροφής
        
        
        //δημιουργία Panel που φιλοξενεί τα κουμπιά
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,50,0));//απόσταση 20pixels οριζόντια
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        bottomPanel.add(editButton);
        bottomPanel.add(saveToDBButton);
        bottomPanel.add(backButton);
        
        
        //προσθήκη στο κεντρικό JPanel
        add(titleLabel,BorderLayout.NORTH);
        add(infoPanel,BorderLayout.CENTER);
        add(bottomPanel,BorderLayout.SOUTH);
    }
    
    //μέθοδος δημιουργίας JTextField για την εμφάνιση του ονόματος του κάθε πεδίου
    private JTextField createTextField(String text){
        JTextField textField = new JTextField(text);
        textField.setFont(new Font("Arial",Font.PLAIN,16));
        textField.setHorizontalAlignment(SwingConstants.LEFT);
        textField.setEditable(false); // δεν μπορεί να επεξεργαστεί από τον χρήστη
        
        return textField;
    }
    
    //μέθοδος δημιουργίας ενός JPanel για την εμφάνιση της πληροφορίας του κάθε πεδίου
    private JPanel createInfoRow(String labelText, JTextField textField, Dimension size) {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
        rowPanel.setBackground(new Color(0xffffff));
        rowPanel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(120, 30)); //σταθερό πλάτος ετικέτας
        label.setFont(new Font("Arial",Font.BOLD,16));
        label.setForeground(new Color(0x003366));
        
        //ορισμός μεγέθους textField (μαξ + προτιμόμενο μέγεθος)
        textField.setMaximumSize(size); //πλάτος 200 pixels new Dimension(200, 30)
        textField.setPreferredSize(size);//πλάτος 200 pixels

        rowPanel.add(label);
        rowPanel.add(Box.createRigidArea(new Dimension(10, 0))); //σταθερό κενό μεταξύ ετικέτας και πεδίου
        rowPanel.add(textField);
        rowPanel.add(Box.createHorizontalGlue()); //κενό επέκτασης μέσα στο container JPanel
        
        return rowPanel;
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

