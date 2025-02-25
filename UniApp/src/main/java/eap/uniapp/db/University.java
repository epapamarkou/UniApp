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
 * POJOs classes
 * @author
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

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;
    @Column(name = "STATEPROVINCE")
    private String stateprovince;
    @Basic(optional = false)
    @Column(name = "ALPHATWOCODE")
    private String alphatwocode;
    @Basic(optional = false)
    @Column(name = "COUNTRY")
    private String country;
    @Basic(optional = false)
    @Column(name = "WEBPAGES")
    private String webpages;
    @Basic(optional = false)
    @Column(name = "DOMAINS")
    private String domains;
    @Column(name = "SEARCHES")
    private Integer searches;
    @Column(name = "COMMENTS")
    private String comments;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "CONTACT")
    private String contact;
    
    
    /**
     * constructor 1
     */
    public University() {
    }
    
    /**
     * constructor 2
     * @param name
     */
    public University(String name) {
        this.name = name;
    }
    
    /**
     * constructor 3
     * @param name
     * @param alphatwocode
     * @param country
     * @param webpages
     * @param domains
     */
    public University(String name, String alphatwocode, String country, String webpages, String domains) {
        this.name = name;
        this.alphatwocode = alphatwocode;
        this.country = country;
        this.webpages = webpages;
        this.domains = domains;
    }
    
    /**
     * getter για name
     * @return
     */
    public String getName() { return name; }

    /**
     * getter για stateprovince
     * @return
     */
    public String getStateprovince() { return stateprovince; }

    /**
     * getter για alphatwocode
     * @return
     */
    public String getAlphatwocode() { return alphatwocode; }

    /**
     * getter για country
     * @return
     */
    public String getCountry() { return country; }

    /**
     * getter για webpages
     * @return
     */
    public String getWebpages() { return webpages; }

    /**
     * getter για domains
     * @return
     */
    public String getDomains() { return domains; }

    /**
     * getter για searches
     * @return
     */
    public Integer getSearches() { return searches; }

    /**
     * getter για comments
     * @return
     */
    public String getComments() { return comments; }

    /**
     * getter για description
     * @return
     */
    public String getDescription() { return description; }

    /**
     * getter για contact
     * @return
     */
    public String getContact() { return contact; }
    
    //setters

    /**
     *
     * @param name
     */
    public void setName(String name) { this.name = name;}

    /**
     *
     * @param stateprovince
     */
    public void setStateprovince(String stateprovince) { this.stateprovince = stateprovince; }

    /**
     *
     * @param alphatwocode
     */
    public void setAlphatwocode(String alphatwocode) { this.alphatwocode = alphatwocode; }

    /**
     *
     * @param country
     */
    public void setCountry(String country) { this.country = country; }

    /**
     *
     * @param webpages
     */
    public void setWebpages(String webpages) { this.webpages = webpages; }

    /**
     *
     * @param domains
     */
    public void setDomains(String domains) { this.domains = domains; }

    /**
     *
     * @param searches
     */
    public void setSearches(Integer searches) { this.searches = searches; }

    /**
     *
     * @param comments
     */
    public void setComments(String comments) { this.comments = comments; }

    /**
     *
     * @param description
     */
    public void setDescription(String description) { this.description = description; }

    /**
     *
     * @param contact
     */
    public void setContact(String contact) { this.contact = contact; }
    
    //μέθοδος αύξησης των Searches-αναζητήσεων κατά 1

    /**
     *
     */
    public void addSearch(){
        if(getSearches() != null){
            setSearches(getSearches() + 1);
        }else{
            setSearches(1);
        }
        
    }
    
    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    /**
     *
     * @param object
     * @return
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
     *
     * @return
     */
    @Override
    public String toString() {
        return "eap.uniapp.db.University[ name=" + name + " ]";
    }
    
}
