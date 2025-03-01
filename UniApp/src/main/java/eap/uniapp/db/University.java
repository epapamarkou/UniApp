package eap.uniapp.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * <p>
 * Η κλάση {@code University} αντιπροσωπεύει μια οντότητα Πανεπιστημίου,
 * με όλα τα σχετικά πεδία και μεθόδους πρόσβασης (getters/setters).
 * Χρησιμοποιείται στο πλαίσιο της JPA για την επικοινωνία με τη βάση
 * δεδομένων και την αποθήκευση/ανάκτηση δεδομένων.
 * </p>
 * 
 * <p>
 * Περιλαμβάνει επίσης {@link NamedQueries} για συχνές ερωτήματα (queries)
 * όπως αναζήτηση με βάση το όνομα, τη χώρα, το alphaTwoCode, κ.ά.
 * </p>
 * 
 * 
 */
@Entity
@Table(name = "UNIVERSITY")
@NamedQueries({
    @NamedQuery(name = "University.findAll", query = "SELECT u FROM University u"),
    @NamedQuery(name = "University.findByName", query = "SELECT u FROM University u WHERE u.name = :name"),
    @NamedQuery(name = "University.findByStateprovince", query = "SELECT u FROM University u WHERE u.stateprovince = :stateprovince"),
    @NamedQuery(name = "University.findByAlphatwocode", query = "SELECT u FROM University u WHERE u.alphatwocode = :alphatwocode"),
    @NamedQuery(name = "University.findByCountry", query = "SELECT u FROM University u WHERE u.country = :country"),
    @NamedQuery(name = "University.findByWebpages", query = "SELECT u FROM University u WHERE u.webpages = :webpages"),
    @NamedQuery(name = "University.findByDomains", query = "SELECT u FROM University u WHERE u.domains = :domains"),
    @NamedQuery(name = "University.findBySearches", query = "SELECT u FROM University u WHERE u.searches = :searches"),
    @NamedQuery(name = "University.findByComments", query = "SELECT u FROM University u WHERE u.comments = :comments"),
    @NamedQuery(name = "University.findByDescription", query = "SELECT u FROM University u WHERE u.description = :description"),
    @NamedQuery(name = "University.findByContact", query = "SELECT u FROM University u WHERE u.contact = :contact")})
public class University implements Serializable {

    /**
     * Σειριακός αριθμός έκδοσης (χρήσιμος για διαδικασίες serialization).
     */
    private static final long serialVersionUID = 1L;

    /**
     * Το όνομα του Πανεπιστημίου (πρωτεύον κλειδί στη βάση δεδομένων).
     */
    @Id
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;

    /**
     * Η πολιτεία ή επαρχία στην οποία βρίσκεται το Πανεπιστήμιο.
     */
    @Column(name = "STATEPROVINCE")
    private String stateprovince;

    /**
     * Ο κωδικός δύο χαρακτήρων (π.χ. GR για Ελλάδα).
     */
    @Basic(optional = false)
    @Column(name = "ALPHATWOCODE")
    private String alphatwocode;

    /**
     * Η χώρα στην οποία ανήκει το Πανεπιστήμιο.
     */
    @Basic(optional = false)
    @Column(name = "COUNTRY")
    private String country;

    /**
     * Σύνδεσμοι (URLs) των ιστοσελίδων που σχετίζονται με το Πανεπιστήμιο.
     */
    @Basic(optional = false)
    @Column(name = "WEBPAGES")
    private String webpages;

    /**
     * Τα domains (π.χ. "uoa.gr", "auth.gr") που συνδέονται με το Πανεπιστήμιο.
     */
    @Basic(optional = false)
    @Column(name = "DOMAINS")
    private String domains;

    /**
     * Ο αριθμός των αναζητήσεων (searches) που έχουν πραγματοποιηθεί για το συγκεκριμένο Πανεπιστήμιο.
     */
    @Column(name = "SEARCHES")
    private Integer searches;

    /**
     * Σχόλια (comments) σχετικά με το Πανεπιστήμιο.
     */
    @Column(name = "COMMENTS")
    private String comments;

    /**
     * Περιγραφή (description) του Πανεπιστημίου (π.χ. ιστορικό, ιδρυτικές πληροφορίες).
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * Στοιχεία επικοινωνίας (contact) για το Πανεπιστήμιο, όπως διεύθυνση ή τηλέφωνο.
     */
    @Column(name = "CONTACT")
    private String contact;
    
    
    /**
     * Βασικός constructor χωρίς ορίσματα.
     * Χρησιμοποιείται από την JPA κατά τη δημιουργία αντικειμένων.
     */
    public University() {
    }
    
    /**
     * Constructor που δέχεται μόνο το όνομα του Πανεπιστημίου.
     * @param name Το όνομα του Πανεπιστημίου
     */
    public University(String name) {
        this.name = name;
    }
    
    /**
     * Constructor που αρχικοποιεί βασικά πεδία του Πανεπιστημίου.
     * @param name           Το όνομα του Πανεπιστημίου
     * @param alphatwocode   Ο κωδικός δύο χαρακτήρων (π.χ. GR)
     * @param country        Η χώρα στην οποία ανήκει
     * @param webpages       Οι ιστοσελίδες που σχετίζονται με το Πανεπιστήμιο
     * @param domains        Τα domains του Πανεπιστημίου
     */
    public University(String name, String alphatwocode, String country, String webpages, String domains) {
        this.name = name;
        this.alphatwocode = alphatwocode;
        this.country = country;
        this.webpages = webpages;
        this.domains = domains;
    }
    
    /**
     * Επιστρέφει το όνομα του Πανεπιστημίου.
     * @return Το όνομα του Πανεπιστημίου
     */
    public String getName() { return name; }

    /**
     * Επιστρέφει την πολιτεία ή επαρχία του Πανεπιστημίου.
     * @return Η πολιτεία/επαρχία
     */
    public String getStateprovince() { return stateprovince; }

    /**
     * Επιστρέφει τον κωδικό δύο χαρακτήρων (π.χ. GR).
     * @return Ο alphaTwoCode
     */
    public String getAlphatwocode() { return alphatwocode; }

    /**
     * Επιστρέφει τη χώρα του Πανεπιστημίου.
     * @return Η χώρα
     */
    public String getCountry() { return country; }

    /**
     * Επιστρέφει τις ιστοσελίδες που σχετίζονται με το Πανεπιστήμιο.
     * @return Τα URL των ιστοσελίδων
     */
    public String getWebpages() { return webpages; }

    /**
     * Επιστρέφει τα domains του Πανεπιστημίου.
     * @return Τα domains
     */
    public String getDomains() { return domains; }

    /**
     * Επιστρέφει τον αριθμό των αναζητήσεων.
     * @return Ο αριθμός των αναζητήσεων
     */
    public Integer getSearches() { return searches; }

    /**
     * Επιστρέφει τα σχόλια (comments) για το Πανεπιστήμιο.
     * @return Τα σχόλια
     */
    public String getComments() { return comments; }

    /**
     * Επιστρέφει την περιγραφή (description) του Πανεπιστημίου.
     * @return Η περιγραφή
     */
    public String getDescription() { return description; }

    /**
     * Επιστρέφει τα στοιχεία επικοινωνίας (contact) του Πανεπιστημίου.
     * @return Τα στοιχεία επικοινωνίας
     */
    public String getContact() { return contact; }
    
    //setters

    /**
     * Θέτει το όνομα του Πανεπιστημίου.
     * @param name Το όνομα του Πανεπιστημίου
     */
    public void setName(String name) { this.name = name;}

    /**
     * Θέτει την πολιτεία/επαρχία του Πανεπιστημίου.
     * @param stateprovince Η πολιτεία/επαρχία
     */
    public void setStateprovince(String stateprovince) { this.stateprovince = stateprovince; }

    /**
     * Θέτει τον κωδικό δύο χαρακτήρων (alphaTwoCode).
     * @param alphatwocode Ο κωδικός δύο χαρακτήρων
     */
    public void setAlphatwocode(String alphatwocode) { this.alphatwocode = alphatwocode; }

    /**
     * Θέτει τη χώρα του Πανεπιστημίου.
     * @param country Η χώρα
     */
    public void setCountry(String country) { this.country = country; }

    /**
     * Θέτει τις ιστοσελίδες (webpages) του Πανεπιστημίου.
     * @param webpages Οι ιστοσελίδες
     */
    public void setWebpages(String webpages) { this.webpages = webpages; }

    /**
     * Θέτει τα domains του Πανεπιστημίου.
     * @param domains Τα domains
     */
    public void setDomains(String domains) { this.domains = domains; }

    /**
     * Θέτει τον αριθμό των αναζητήσεων.
     * @param searches Ο αριθμός των αναζητήσεων
     */
    public void setSearches(Integer searches) { this.searches = searches; }

    /**
     * Θέτει τα σχόλια (comments).
     * @param comments Τα σχόλια
     */
    public void setComments(String comments) { this.comments = comments; }

    /**
     * Θέτει την περιγραφή (description) του Πανεπιστημίου.
     * @param description Η περιγραφή
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Θέτει τα στοιχεία επικοινωνίας (contact).
     * @param contact Τα στοιχεία επικοινωνίας
     */
    public void setContact(String contact) { this.contact = contact; }
    
    //μέθοδος αύξησης των Searches-αναζητήσεων κατά 1

    /**
     * Αυξάνει τον αριθμό των αναζητήσεων (searches) κατά 1. 
     * Αν δεν έχει οριστεί ακόμα, αρχικοποιείται σε 1.
     */
    public void addSearch(){
        if(getSearches() != null){
            setSearches(getSearches() + 1);
        }else{
            setSearches(1);
        }
        
    }
    
    /**
     * Μέθοδος που επιστρέφει τον hash code, με βάση το όνομα του Πανεπιστημίου.
     * @return hash code που βασίζεται στο πεδίο {@code name}
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    /**
     * Ελέγχει αν το παρόν αντικείμενο είναι ίσο με ένα άλλο {@code University}.
     * @param object Ένα αντικείμενο προς σύγκριση
     * @return {@code true} αν έχουν το ίδιο {@code name}, αλλιώς {@code false}
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof University)) {
            return false;
        }
        University other = (University) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    /**
     * Επιστρέφει μια συμβολοσειρά που αντιπροσωπεύει αυτό το αντικείμενο,
     * περιλαμβάνοντας το όνομα του Πανεπιστημίου.
     * @return Μια συμβολοσειρά (String) που περιέχει το όνομα 
     */
    @Override
    public String toString() {
        return "eap.uniapp.db.University[ name=" + name + " ]";
    }
    
}
