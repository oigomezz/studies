package co.unal.mapsnearbyplaces;

import android.os.AsyncTask;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class GetNearbyPlaces extends AsyncTask<Object, String, String> {

    private GoogleMap mMap;
    private List<NearbyPlace> nearbyPlacesList = new ArrayList<>();
    private NearbyPlacesParser nearbyPlacesParser = new NearbyPlacesParser();

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        String nearbyPlacesURL = (String) objects[1];
        nearbyPlacesList = nearbyPlacesParser.listNearbyPlace(nearbyPlacesURL);
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        for(NearbyPlace nearbyPlace : nearbyPlacesList){
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(nearbyPlace.getLatitude(), nearbyPlace.getLongitude()));
            markerOptions.title(nearbyPlace.getName());
            markerOptions.snippet(nearbyPlace.getVicinity());
            mMap.addMarker(markerOptions);
        }
    }
}
