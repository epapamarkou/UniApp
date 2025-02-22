package eap.uniapp.model;
   

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class JavaUniversity {
    
    private String name;
    @SerializedName("state-province") //Gson αντιστοιχίση JSON -> Java
    private String stateProvince;
    private String alpha_two_code;
    private String country;
    private List<String> web_pages;
    private List<String> domains;
    
    //θα χρησιμοποιηθεί ο default constructor
    
    //getters
    public String getName(){ return name; }
    public String getStateProvince(){ return stateProvince; }
    public String getAlphaTwoCode(){ return alpha_two_code; }
    public String getCountry(){ return country; }
    public List<String> getWebPages(){ return web_pages; }
    public List<String> getDomains(){ return domains; }
    
    
    //setters
    public void setName(String name){ this.name = name; }
    public void setStateProvince(String stateProvince){ this.stateProvince = stateProvince; }
    public void setAlphaTwoCode(String alpha_two_code){ this.alpha_two_code = alpha_two_code; }
    public void setCountry(String country){ this.country = country; }
    public void setWebPages(List<String> web_pages){ this.web_pages = web_pages; }
    public void setDomains(List<String> domains){ this.domains = domains; }
    
    
    //μέθοδος που επιστρέφει σε λίστα όλες τις χώρες που έχουν πανεπιστήμια ταξινομημένες
    public static List<String> allCountries(List<JavaUniversity> universities){
        //έλεγχος αν η λίστα είναι null ή κενή
        if(universities == null || universities.isEmpty()){
            //επιστροφή κενής αμετάβλητης λίστας
            return List.of();
        }
        
        //αρχικοποίηση HashSet για να μπουν μοναδικές τιμές χωρών
        HashSet<String> countrySet = new HashSet<>();
        
        //γέμισμα countrySet με μοναδικές τιμές χωρών
        for (int i=0;i<universities.size();i++){
            if(universities.get(i).getCountry() != null && !universities.get(i).getCountry().isEmpty()){
                countrySet.add(universities.get(i).getCountry());
            }
        }
        
        //αρχικοποίηση λίστας που θα περιέχει ταξινομημένες (Α-Ζ) μοναδικές τιμές χωρών
        List<String> countryList = new ArrayList<>(countrySet);
        
        Collections.sort(countryList);//ταξινόμηση countryList Α-Ζ
        
        return countryList;
    }
    
    
    //μέθοδος που επιστρέφει σε ένα String τις πληροφορίες του πανεπιστημίου
    @Override
    public String toString(){
        StringBuilder webPageStr = new StringBuilder();
        StringBuilder domainStr = new StringBuilder();
        
        //create one String for all webpages
        if(web_pages != null){
            for(int i=0; i<web_pages.size();i++){
                webPageStr.append(web_pages.get(i)).append(",");
            }
            if(webPageStr.length() > 2)
                webPageStr.setLength(webPageStr.length() - 1);
        }else{
            webPageStr.append("null");
        }
        
        //create one String for all domains
        if(domains != null){
            for(int i=0; i<domains.size();i++){
                domainStr.append(domains.get(i)).append(", ");
            }
            if(domainStr.length() > 2)
                domainStr.setLength(domainStr.length() - 2);
        }else{
            domainStr.append("null");
        }
        
        return String.format("""
                             University name: %s, 
                             state-province: %s, 
                             alpha-2 code: %s, 
                             country: %s, 
                             webpage: %s, 
                             domain: %s""", 
                getName(), getStateProvince(),getAlphaTwoCode(),
                getCountry(),webPageStr,domainStr);
    }
    
}
