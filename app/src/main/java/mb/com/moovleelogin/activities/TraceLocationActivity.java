package mb.com.moovleelogin.activities;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

//public class TraceLocationActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
//
//    private GoogleMap map;
//    private LocationManager locationManager;
//    ToggleButton startRide;
//    EditText enterPickUp;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (isGoogleServiceAvailable()) {
//            Toast.makeText(this, "Perfect", Toast.LENGTH_SHORT).show();
//            setContentView(R.layout.activity_trace_location);
//            startRide=(ToggleButton) findViewById(R.id.toggleButton);
//            enterPickUp=(EditText) findViewById(R.id.PickupLocation);
//            initMap();
//        } else {
//            Toast.makeText(this, "No Google Map", Toast.LENGTH_SHORT).show();
//        }
//        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
//        {
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10, 5, new android.location.LocationListener() {
//                @Override
//                public void onLocationChanged(Location location) {
//                    //get the latitude
//                    double latitude=location.getLatitude();
//                    //get the lognitude
//                    double lognitude=location.getLongitude();
//                    //instantitate the object of LatLng class
//                    LatLng latLng=new LatLng(latitude,lognitude);
//                    //instantiate GeoCoder class
//                    Geocoder geocoder=new Geocoder(getApplicationContext());
//                    try {
//                        List<android.location.Address> addressList=geocoder.getFromLocation(latitude,lognitude,1);
//                        String str=addressList.get(0).getLocality()+"";
//                        str+=addressList.get(0).getCountryName();
//                        map.addMarker(new MarkerOptions().position(latLng).title(str));
//                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.2f));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//                @Override
//                public void onStatusChanged(String provider, int status, Bundle extras) {
//
//                }
//
//                @Override
//                public void onProviderEnabled(String provider) {
//
//                }
//
//                @Override
//                public void onProviderDisabled(String provider) {
//
//                }
//            });
//        }
//        else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
//        {
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 5, new android.location.LocationListener() {
//                @Override
//                public void onLocationChanged(Location location) {
//                    //get the lognitude
//                    double latitude=location.getLatitude();
//                    //get the lognitude
//                    double lognitude=location.getLongitude();
//                    //instantitate the object of LatLng class
//                    LatLng latLng=new LatLng(latitude,lognitude);
//                    //instantiate GeoCoder class
//                    Geocoder geocoder=new Geocoder(getApplicationContext());
//                    try {
//                        List<android.location.Address> addressList=geocoder.getFromLocation(latitude,lognitude,1);
//                        String str=addressList.get(0).getLocality()+"";
//                        str+=addressList.get(0).getCountryName();
//                        map.addMarker(new MarkerOptions().position(latLng).title(str));
//                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.2f));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onStatusChanged(String provider, int status, Bundle extras) {
//
//                }
//
//                @Override
//                public void onProviderEnabled(String provider) {
//
//                }
//
//                @Override
//                public void onProviderDisabled(String provider) {
//
//                }
//            });
//        }
//    }
//
//
//
//    private void initMap() {
//        MapFragment mapfargment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
//        //It will give the map detail and returns map object
//        mapfargment.getMapAsync(this);
//    }
//
//    public boolean isGoogleServiceAvailable() {
//        GoogleApiAvailability apiAvalability = GoogleApiAvailability.getInstance();
//        int isAvailable = apiAvalability.isGooglePlayServicesAvailable(this);
//        if (isAvailable == ConnectionResult.SUCCESS) {
//            return true;
//        } else if (apiAvalability.isUserResolvableError(isAvailable)) {
//            Dialog dialog = apiAvalability.getErrorDialog(this, isAvailable, 0);
//            dialog.show();
//        } else {
//            Toast.makeText(this, "Can't connect to play servivcs", Toast.LENGTH_SHORT).show();
//        }
//        return false;
//    }
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        map = googleMap;
//    }
//    String pickUpLpcation="";
//    public  void enterPickUp()
//    {
//         pickUpLpcation=enterPickUp.getText().toString();;
//    }
//    public void setPickup(View view) throws IOException {
//        enterPickUp();
//        Geocoder gc=new Geocoder(getApplicationContext());
//        List<android.location.Address> pickUpArray= gc.getFromLocationName(pickUpLpcation,1);
//        try {
//            pickUpArray = gc.getFromLocationName(pickUpLpcation,1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        android.location.Address adr=pickUpArray.get(0);
//        double lat=adr.getLatitude();
//        double lon=adr.getLongitude();
//        LatLng latLng=new LatLng(lat,lon);
//        String exactPickup=pickUpArray.get(0).getLocality()+"";
//        exactPickup+=pickUpArray.get(0).getCountryName();
//        map.addMarker(new MarkerOptions().position(latLng).title(exactPickup));
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.2f));
//    }
//
//    @Override
//    public void onClick(View v) {
//        int id=v.getId();
//        switch (id)
//        {
//            case R.id.toggleButton:
//                try {
//                    setPickup(v);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                break;
//        }
//    }
//
//
//
//}

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class TraceLocationActivity extends Service
{
    private static final String TAG = "BOOMBOOMTESTGPS";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 10f;

    private class LocationListener implements android.location.LocationListener
    {
        Location mLastLocation;

        public LocationListener(String provider)
        {
            Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location)
        {
            Log.e(TAG, "onLocationChanged: " + location);
            mLastLocation.set(location);
        }

        @Override
        public void onProviderDisabled(String provider)
        {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider)
        {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
            Log.e(TAG, "onStatusChanged: " + provider);
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[] {
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate()
    {
        Log.e(TAG, "onCreate");
        initializeLocationManager();
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }

    @Override
    public void onDestroy()
    {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }
}
