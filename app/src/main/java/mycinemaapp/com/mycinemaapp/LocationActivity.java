package mycinemaapp.com.mycinemaapp;

import android.app.Dialog;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kristian on 15-3-11.
 * <p/>
 * Used example:
 * https://software.intel.com/en-us/android/articles/implementing-map-and-geofence-features-in-android-business-apps
 */
public class LocationActivity extends FragmentActivity implements LocationListener, View.OnClickListener {

    private GoogleMap googleMap;
    private static final float ZOOM_LEVEL = 14;
    private static final float ZOOM_LEVEL_CITY = 10;
    private static final float MAX_SEARCH_ZOOM_LEVEL = 6;
    private boolean isGPSEnabled;
    private boolean isNetworkEnabled;
    private boolean canGetLocation;
    private long MIN_TIME_BW_UPDATES = 2;
    private float MIN_DISTANCE_CHANGE_FOR_UPDATES = 100;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    private Location location;
    private double latitude;
    private double longitude;
    private LatLng ltLng;
    private LocationManager locationManager;

    private static final ArrayList<Location> ALL_CINEMA_LOCATION = new ArrayList<>();
    private ArrayList<Marker> markerInArea = new ArrayList<>();
    private float RADIUS = 50000;
    private ArrayList<Geofence> mGeofenceList = new ArrayList<>();

    private Button getCinemas, confirm;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_layout);

        getCinemas = (Button) findViewById(R.id.get_cinemas);
        back = (ImageView) findViewById(R.id.back);
        confirm = (Button) findViewById(R.id.confirm);
        getCinemas.setOnClickListener(this);
        back.setOnClickListener(this);
        confirm.setOnClickListener(this);

        HashMap<String, String[]> allLocations = new HashMap<>();

        Location storeGrand = new Location("Grand Mall");
        Location storeVarna = new Location("Mall Varna");
        Location storeRuse = new Location("Mall Ruse");
        Location storeShumen = new Location("Mall Shumen");

        storeGrand.setLatitude(43.217570);
        storeGrand.setLongitude(27.898307);

        storeVarna.setLatitude(43.220332);
        storeVarna.setLongitude(27.889395);

        storeRuse.setLatitude(43.853763);
        storeRuse.setLongitude(25.990372);

        storeShumen.setLatitude(43.272004);
        storeShumen.setLongitude(26.935474);

        ALL_CINEMA_LOCATION.add(storeGrand);
        ALL_CINEMA_LOCATION.add(storeRuse);
        ALL_CINEMA_LOCATION.add(storeVarna);
        ALL_CINEMA_LOCATION.add(storeShumen);

        // Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        // Showing status
        if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        } else { // Google Play Services are available

            // Getting reference to the SupportMapFragment of activity_main.xml
            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();


            // Getting GoogleMap object from the fragment
            // googleMap = fm.getMap();

            // Enabling MyLocation Layer of Google Map
            googleMap.setMyLocationEnabled(true);


            UiSettings settings = googleMap.getUiSettings();
            settings.setMyLocationButtonEnabled(true);
            settings.setZoomControlsEnabled(true);
            settings.setCompassEnabled(true);


            // Getting LocationManager object from System Service LOCATION_SERVICE
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Creating a criteria object to retrieve provider
//            Criteria criteria = new Criteria();

            // Getting the name of the best provider
//            String provider = locationManager.getBestProvider(criteria, true);

            // Getting Current Location
            Location location = getLocation();


            if (location != null) {

//                ltLng = new LatLng(location.getLatitude(), location.getLongitude());

//                googleMap.addMarker(new MarkerOptions()
//                        .position(ALL_CINEMA_LOCATION[0].mLatLng));
//
//                googleMap.addMarker(new MarkerOptions()
//                        .position(ALL_CINEMA_LOCATION[1].mLatLng));

                onLocationChanged(location);
            }

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
        }


//        Location loc = new Location(location);
//        loc.setLatitude(ltLng.latitude);
//        loc.setLongitude(ltLng.longitude);
    }


    /**
     * The method is from
     * http://stackoverflow.com/questions/15997079/getlastknownlocation-always-return-null-after-i-re-install-the-apk-file-via-ecli
     *
     * @return
     */
    public Location getLocation() {
        try {
            locationManager = (LocationManager) this
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    @Override
    public void onLocationChanged(Location location) {

//        TextView tvLocation = (TextView) findViewById(R.id.tv_location);

        // Getting latitude of the current location
        double latitude = location.getLatitude();

        // Getting longitude of the current location
        double longitude = location.getLongitude();

        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Showing the current location in Google Map
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(ZOOM_LEVEL));

        // Setting latitude and longitude in the TextView tv_location
//        tvLocation.setText("Latitude:" +  latitude  + ", Longitude:"+ longitude );
//        Toast.makeText(this, "Latitude:" + latitude + ", Longitude:" + longitude, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onClick(View v) {
        float distance, zoomLevel;
        Marker marker = null;
        LatLngBounds bounds = null;

        switch (v.getId()) {
            case R.id.get_cinemas:
                googleMap.clear();

                Location tempLocation = new Location("");
                LatLng ltLng = googleMap.getCameraPosition().target;
                zoomLevel = googleMap.getCameraPosition().zoom;

                tempLocation.setLongitude(ltLng.longitude);
                tempLocation.setLatitude(ltLng.latitude);

                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                markerInArea.clear();
                for (int i = 0; i < ALL_CINEMA_LOCATION.size(); i++) {
                    distance = tempLocation.distanceTo(ALL_CINEMA_LOCATION.get(i));
                    if (distance <= RADIUS && zoomLevel > MAX_SEARCH_ZOOM_LEVEL) {

                        marker = googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(ALL_CINEMA_LOCATION.get(i).getLatitude()
                                        , ALL_CINEMA_LOCATION.get(i).getLongitude())));
                        markerInArea.add(marker);
                        builder.include(marker.getPosition());
                    }
                }

                if (!markerInArea.isEmpty()) {
                    bounds = builder.build();
                    if (markerInArea.size() == 1) {
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 12F));
                    } else {
                        // Showing the current location in Google Map
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 30));
                    }
                } else {
                    Toast.makeText(this, "Cinemas wasn't found.", Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.confirm:
                break;

            case R.id.back:
                onBackPressed();
                break;
        }
    }
}
