package eap.uniapp.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import eap.uniapp.model.JavaUniversity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.*;

/**
 *
 * @author Ersi
 */
public class UrlCall {
    
    private static final String URL_BASE = "http://universities.hipolabs.com/search?";
    private static final String URL_JSON = "https://raw.githubusercontent.com/Hipo/university-domains-list/refs/heads/master/world_universities_and_domains.json";
    private final OkHttpClient client;
    private final Gson gson;
    
    //constructor

    /**
     *
     */
    public UrlCall() {
        this.client = new OkHttpClient();
        this.gson = new Gson();;
    }
    
    
    //Mέθοδος που επιστρέφει τα πανεπιστήμια του request σε λίστα ανάλογα με το 
    //φίλτρο αναζήτησης. Μετατρέπει το JSON array με τα JSON objects, σε λίστα
    //με Java objects

    /**
     *
     * @param name
     * @param country
     * @param useJsonUrl
     * @return
     */
    
    public List<JavaUniversity> SearchUniversities(String name, String country, boolean useJsonUrl){
        //δόμηση του url
        String URL;
        
        if (useJsonUrl){ //αν μπει εδώ, παίρνει το URL του αρχείου
            URL = URL_JSON;
        }else{ //αν μπει εδώ, φτιάχνει το URL με βάση τα δεδομένα που εισήγαγε ο χρήστης
            //δόμηση του urlBuilder
            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append(URL_BASE);
            
            //αν το name ΔΕΝ ΕΙΝΑΙ null και κενή συμβολοσειρά, τότε βάλε στο urlBuilder
            //name= και ό,τι name ήρθε στη μέθοδο
            if (name != null && !name.isEmpty()){
                urlBuilder.append("name=").append(name);
            }

            //αν το country ΔΕΝ ΕΙΝΑΙ null και κενή συμβολοσειρά, τότε βάλε στο urlBuilder
            //country= και ό,τι country ήρθε στη μέθοδο. Επίσης ελέγχει αν έχει μπει ήδη 
            //κάποιο name για να προσθέσει στο urlBuilder τον χαρακτήρα &
            if (country != null && !country.isEmpty()){
                if (urlBuilder.toString().contains("name=")){
                    urlBuilder.append("&");
                }
                urlBuilder.append("country=").append(country);
            }
            
            URL = urlBuilder.toString();
        }
        
        Request request = new Request.Builder().url(URL).build();
        
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                
                String responseString = response.body().string();//JSON Array to String
                
                //System.out.println(responseString);
                
                //Convert json array with json objects to Java list with java objects
                Type listType = new TypeToken<List<JavaUniversity>>(){}.getType();
                return gson.fromJson(responseString, listType);
            }
        }
        catch (IOException e){
            //e.printStackTrace();
            e.getMessage();
        }
        
        //επιστροφή κενής μεταβλητή λίστας
        //return List.of();
        return new ArrayList<>();
    }
    // end of SearchUniversities method
    
    
}
