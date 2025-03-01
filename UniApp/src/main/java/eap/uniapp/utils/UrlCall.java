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
 * <p>
 * Η κλάση {@code UrlCall} είναι υπεύθυνη για την πραγματοποίηση HTTP αιτήσεων
 * ώστε να ανακτηθούν δεδομένα πανεπιστημίων από το διαδίκτυο. Χρησιμοποιεί τη βιβλιοθήκη
 * OkHttp για την εκτέλεση των αιτήσεων και τη Gson για τη μετατροπή των JSON δεδομένων
 * σε αντικείμενα Java.
 * </p>
 * 
 */
public class UrlCall {
    
    /**
     * Η βασική διεύθυνση URL για την αναζήτηση πανεπιστημίων.
     */
    private static final String URL_BASE = "http://universities.hipolabs.com/search?";

    /**
     * Διεύθυνση URL που οδηγεί σε ένα στατικό JSON αρχείο με λίστα πανεπιστημίων και domains.
     */
    private static final String URL_JSON = "https://raw.githubusercontent.com/Hipo/university-domains-list/refs/heads/master/world_universities_and_domains.json";

    /**
     * Αντικείμενο OkHttpClient για την εκτέλεση HTTP αιτήσεων.
     */
    private final OkHttpClient client;

    /**
     * Αντικείμενο Gson για τη μετατροπή JSON δεδομένων σε Java αντικείμενα.
     */
    private final Gson gson;
    
    
    /**
     * <p>
     * Ο προεπιλεγμένος constructor της κλάσης, ο οποίος αρχικοποιεί τα αντικείμενα
     * OkHttpClient και Gson για μελλοντική χρήση.
     * </p>
     */
    public UrlCall() {
        this.client = new OkHttpClient();
        this.gson = new Gson();;
    }
    
    
    //Mέθοδος που επιστρέφει τα πανεπιστήμια του request σε λίστα ανάλογα με το 
    //φίλτρο αναζήτησης. Μετατρέπει το JSON array με τα JSON objects, σε λίστα
    //με Java objects

    /**
     * <p>
     * Εκτελεί αναζήτηση πανεπιστημίων βάσει των κριτηρίων που δίνονται: 
     * το όνομα και η χώρα. Εάν η παράμετρος {@code useJsonUrl} είναι {@code true},
     * τότε χρησιμοποιείται το στατικό URL που περιέχει το JSON αρχείο. Διαφορετικά,
     * κατασκευάζεται ένα URL με βάση τα κριτήρια που έχουν δοθεί από τον χρήστη.
     * </p>
     * <p>
     * Μετά την εκτέλεση της HTTP αίτησης, το JSON array που λαμβάνεται μετατρέπεται σε λίστα
     * αντικειμένων {@link JavaUniversity} και επιστρέφεται.
     * </p>
     * 
     * @param name Το όνομα του πανεπιστημίου προς αναζήτηση (μπορεί να είναι {@code null} ή κενό).
     * @param country Η χώρα προς αναζήτηση (μπορεί να είναι {@code null} ή κενό).
     * @param useJsonUrl Εάν είναι {@code true}, χρησιμοποιείται το στατικό JSON URL,
     *                   διαφορετικά κατασκευάζεται URL με βάση τα κριτήρια.
     * @return Μια λίστα από αντικείμενα {@link JavaUniversity} που αντιστοιχούν στα αποτελέσματα της αναζήτησης.
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
        
        //επιστροφή κενής λίστας
        return new ArrayList<>();
    }
    // end of SearchUniversities method
    
    
}
