package eap.uniapp.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


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

    public University() {
    }

    public University(String name) {
        this.name = name;
    }

    public University(String name, String alphatwocode, String country, String webpages, String domains) {
        this.name = name;
        this.alphatwocode = alphatwocode;
        this.country = country;
        this.webpages = webpages;
        this.domains = domains;
    }
    
    //getters
    public String getName() { return name; }
    public String getStateprovince() { return stateprovince; }
    public String getAlphatwocode() { return alphatwocode; }
    public String getCountry() { return country; }
    public String getWebpages() { return webpages; }
    public String getDomains() { return domains; }
    public Integer getSearches() { return searches; }
    public String getComments() { return comments; }
    public String getDescription() { return description; }
    public String getContact() { return contact; }
    
    //setters
    public void setName(String name) { this.name = name;}
    public void setStateprovince(String stateprovince) { this.stateprovince = stateprovince; }
    public void setAlphatwocode(String alphatwocode) { this.alphatwocode = alphatwocode; }
    public void setCountry(String country) { this.country = country; }
    public void setWebpages(String webpages) { this.webpages = webpages; }
    public void setDomains(String domains) { this.domains = domains; }
    public void setSearches(Integer searches) { this.searches = searches; }
    public void setComments(String comments) { this.comments = comments; }
    public void setDescription(String description) { this.description = description; }
    public void setContact(String contact) { this.contact = contact; }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

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

    @Override
    public String toString() {
        return "eap.uniapp.db.University[ name=" + name + " ]";
    }
    
}
