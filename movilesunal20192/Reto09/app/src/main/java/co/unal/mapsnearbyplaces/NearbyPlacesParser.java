package co.unal.mapsnearbyplaces;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NearbyPlacesParser {

    public static final String JSON_KEY_ID = "id";
    public static final String JSON_KEY_LAT = "lat";
    public static final String JSON_KEY_LNG = "lng";
    public static final String JSON_KEY_NAME = "name";
    public static final String JSON_KEY_VICINITY = "vicinity";
    public static final String JSON_ARRAY_RESULTS = "results";
    public static final String JSON_OBJECT_GEOMETRY = "geometry";
    public static final String JSON_OBJECT_LOCATION = "location";
    public static final String JSON_KEY_NEXT_PAGE_TOKEN = "next_page_token";
    private List<NearbyPlace> nearbyPlaceList = new ArrayList<>();

    public List<NearbyPlace> listNearbyPlace(String nearbyPlacesURLString) {
        String pageToken = "";
        String jsonNearbyPlacesString = "";
        InputStream inputStream = null;        
        HttpURLConnection httpURLConnection = null;

        do {
            try {
                URL nearbyPlacesURL = new URL(nearbyPlacesURLString);
                httpURLConnection = (HttpURLConnection) nearbyPlacesURL.openConnection();
                inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
                jsonNearbyPlacesString = stringBuffer.toString();
                bufferedReader.close();
                
                pageToken = fullListNearbyPlace(jsonNearbyPlacesString);
                nearbyPlacesURLString = MapsActivity.URL_PREFIX.concat(MapsActivity.PARAMETER_PAGETOKEN).concat(pageToken);

                try {
                    Thread.sleep(2*1000);
                } catch (Exception e) {
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                    httpURLConnection.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } while (pageToken.length() > 0);
        
        return nearbyPlaceList;
    }

    private String fullListNearbyPlace(String jsonNearbyPlaces) {

        try {
            JSONObject jsonObject = new JSONObject(jsonNearbyPlaces);
            JSONArray jsonArray = jsonObject.getJSONArray(JSON_ARRAY_RESULTS);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjNearbyPlace = jsonArray.getJSONObject(i);
                
                NearbyPlace nearbyPlace = new NearbyPlace();
                nearbyPlace.setId(jsonObjNearbyPlace.getString(JSON_KEY_ID));
                nearbyPlace.setName(jsonObjNearbyPlace.getString(JSON_KEY_NAME));
                nearbyPlace.setVicinity(jsonObjNearbyPlace.getString(JSON_KEY_VICINITY));
                nearbyPlace.setLatitude(Double.parseDouble(jsonObjNearbyPlace.getJSONObject(JSON_OBJECT_GEOMETRY).getJSONObject(JSON_OBJECT_LOCATION).getString(JSON_KEY_LAT)));
                nearbyPlace.setLongitude(Double.parseDouble(jsonObjNearbyPlace.getJSONObject(JSON_OBJECT_GEOMETRY).getJSONObject(JSON_OBJECT_LOCATION).getString(JSON_KEY_LNG)));
                nearbyPlaceList.add(nearbyPlace);
            }
            
            return jsonObject.getString(JSON_KEY_NEXT_PAGE_TOKEN);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }
}
