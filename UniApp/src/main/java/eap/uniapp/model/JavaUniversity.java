package eap.uniapp.model;
   

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * Η κλάση {@code JavaUniversity} αντιπροσωπεύει ένα Πανεπιστήμιο το οποίο προέρχεται από JSON
 * δεδομένα (με χρήση της βιβλιοθήκης Gson). Περιέχει πεδία που αντιστοιχούν σε ιδιότητες 
 * του Πανεπιστημίου (όνομα, χώρα, λίστα ιστοσελίδων κ.λπ.) και παρέχει μεθόδους για 
 * πρόσβαση και μεταβολή των τιμών αυτών. Επιπλέον, περιλαμβάνει βοηθητικές μεθόδους 
 * όπως {@link #allCountries(List)} που επιστρέφουν όλες τις χώρες από μια λίστα 
 * {@code JavaUniversity}, καθώς και υπερκαθορισμό της {@link #toString()} για φιλική εμφάνιση.
 * </p>
 * 
 * <p>
 * Τα πεδία που φέρουν τον ειδικό σχολιασμό {@link SerializedName} συνδέουν τα ονόματα 
 * που υπάρχουν στο JSON με τα αντίστοιχα πεδία της κλάσης.
 * </p>
 */
public class JavaUniversity {
    
    /**
     * Ο προεπιλεγμένος constructor της κλάσης {@code JavaUniversity}.
     * Χρησιμοποιείται από τη βιβλιοθήκη Gson για την δημιουργία αντικειμένων.
     */
    public JavaUniversity() {
    }
    
    /**
     * Το όνομα του Πανεπιστημίου.
     */
    private String name;

    /**
     * Η πολιτεία/επαρχία του Πανεπιστημίου, αντιστοιχισμένη μέσω {@link SerializedName} στο
     * πεδίο "state-province" του JSON.
     */
    @SerializedName("state-province") // Gson αντιστοίχιση JSON -> Java
    private String stateProvince;

    /**
     * Ο κωδικός δύο χαρακτήρων (π.χ. "GR" για Ελλάδα).
     */
    private String alpha_two_code;

    /**
     * Η χώρα στην οποία ανήκει το Πανεπιστήμιο.
     */
    private String country;

    /**
     * Η λίστα URL που αντιστοιχεί στις ιστοσελίδες του Πανεπιστημίου.
     */
    private List<String> web_pages;

    /**
     * Η λίστα με τα domains που ανήκουν στο Πανεπιστήμιο.
     */
    private List<String> domains;
    
    // getters

    /**
     * Επιστρέφει το όνομα του Πανεπιστημίου.
     * @return Το όνομα
     */
    public String getName(){ return name; }

    /**
     * Επιστρέφει την πολιτεία/επαρχία του Πανεπιστημίου.
     * @return Η πολιτεία/επαρχία
     */
    public String getStateProvince(){ return stateProvince; }

    /**
     * Επιστρέφει τον κωδικό δύο χαρακτήρων (π.χ. "GR").
     * @return Ο κωδικός alpha_two_code
     */
    public String getAlphaTwoCode(){ return alpha_two_code; }

    /**
     * Επιστρέφει τη χώρα του Πανεπιστημίου.
     * @return Η χώρα
     */
    public String getCountry(){ return country; }

    /**
     * Επιστρέφει τη λίστα με τις ιστοσελίδες του Πανεπιστημίου.
     * @return Λίστα από URLs
     */
    public List<String> getWebPages(){ return web_pages; }

    /**
     * Επιστρέφει τη λίστα με τα domains του Πανεπιστημίου.
     * @return Λίστα από domains
     */
    public List<String> getDomains(){ return domains; }
    
    
    // setters

    /**
     * Θέτει το όνομα του Πανεπιστημίου.
     * @param name Το όνομα που θα αποδοθεί
     */
    public void setName(String name){ this.name = name; }

    /**
     * Θέτει την πολιτεία/επαρχία του Πανεπιστημίου.
     * @param stateProvince Η πολιτεία/επαρχία
     */
    public void setStateProvince(String stateProvince){ this.stateProvince = stateProvince; }

    /**
     * Θέτει τον κωδικό δύο χαρακτήρων (alpha_two_code).
     * @param alpha_two_code Ο κωδικός δύο χαρακτήρων
     */
    public void setAlphaTwoCode(String alpha_two_code){ this.alpha_two_code = alpha_two_code; }

    /**
     * Θέτει τη χώρα του Πανεπιστημίου.
     * @param country Η χώρα
     */
    public void setCountry(String country){ this.country = country; }

    /**
     * Θέτει τη λίστα των ιστοσελίδων.
     * @param web_pages Λίστα από URLs
     */
    public void setWebPages(List<String> web_pages){ this.web_pages = web_pages; }

    /**
     * Θέτει τη λίστα των domains.
     * @param domains Λίστα από domains
     */
    public void setDomains(List<String> domains){ this.domains = domains; }
    
    
    // μέθοδος που επιστρέφει σε λίστα όλες τις χώρες που έχουν πανεπιστήμια ταξινομημένες

    /**
     * <p>
     * Επιστρέφει μια λίστα με όλες τις μοναδικές χώρες στις οποίες υπάρχουν Πανεπιστήμια,
     * με αλφαβητική ταξινόμηση. Αν η λίστα Πανεπιστημίων είναι {@code null} ή κενή,
     * επιστρέφει μια κενή αμετάβλητη λίστα.
     * </p>
     * 
     * @param universities Μια λίστα {@link JavaUniversity} αντικειμένων
     * @return Μια ταξινομημένη λίστα με μοναδικές χώρες
     */
    public static List<String> allCountries(List<JavaUniversity> universities){
        // έλεγχος αν η λίστα είναι null ή κενή
        if(universities == null || universities.isEmpty()){
            // επιστροφή κενής αμετάβλητης λίστας
            return List.of();
        }
        
        // αρχικοποίηση HashSet για να μπουν μοναδικές τιμές χωρών
        HashSet<String> countrySet = new HashSet<>();
        
        // γέμισμα countrySet με μοναδικές τιμές χωρών
        for (int i=0;i<universities.size();i++){
            if(universities.get(i).getCountry() != null && !universities.get(i).getCountry().isEmpty()){
                countrySet.add(universities.get(i).getCountry());
            }
        }
        
        // αρχικοποίηση λίστας που θα περιέχει ταξινομημένες (Α-Ζ) μοναδικές τιμές χωρών
        List<String> countryList = new ArrayList<>(countrySet);
        
        Collections.sort(countryList); // ταξινόμηση countryList Α-Ζ
        
        return countryList;
    }
    
    
    // μέθοδος που επιστρέφει σε ένα String τις πληροφορίες του πανεπιστημίου

    /**
     * <p>
     * Επιστρέφει μια συμβολοσειρά που περιγράφει το Πανεπιστήμιο,
     * συμπεριλαμβανομένων όλων των βασικών πεδίων (όνομα, πολιτεία,
     * alpha-2 code, χώρα, ιστοσελίδες, domains).
     * </p>
     * 
     * @return Μια μορφοποιημένη συμβολοσειρά με τα στοιχεία του Πανεπιστημίου
     */
    @Override
    public String toString(){
        StringBuilder webPageStr = new StringBuilder();
        StringBuilder domainStr = new StringBuilder();
        
        // Δημιουργία ενός String για όλες τις ιστοσελίδες
        if(web_pages != null){
            for(int i=0; i<web_pages.size(); i++){
                webPageStr.append(web_pages.get(i)).append(",");
            }
            if(webPageStr.length() > 2)
                webPageStr.setLength(webPageStr.length() - 1);
        } else {
            webPageStr.append("null");
        }
        
        // Δημιουργία ενός String για όλα τα domains
        if(domains != null){
            for(int i=0; i<domains.size(); i++){
                domainStr.append(domains.get(i)).append(", ");
            }
            if(domainStr.length() > 2)
                domainStr.setLength(domainStr.length() - 2);
        } else {
            domainStr.append("null");
        }
        
        return String.format("""
                             University name: %s, 
                             state-province: %s, 
                             alpha-2 code: %s, 
                             country: %s, 
                             webpage: %s, 
                             domain: %s""", 
                getName(), getStateProvince(), getAlphaTwoCode(),
                getCountry(), webPageStr, domainStr);
    }
    
}
