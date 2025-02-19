package eap.uniapp.model;
   

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class JavaUniversity {
    
    private String name;
    @SerializedName("state-province") //Gson αντιστοιχίση JSON -> Java
    private String stateProvince;
    private String alpha_two_code;
    private String country;
    private List<String> web_pages;
    private List<String> domains;
    
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
