package mb.com.moovleelogin;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback, View.OnClickListener {
    private GoogleMap map;
    private LocationManager locationManager;
    ToggleButton startRide;
    EditText enterPickUp,enterDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isGoogleServiceAvailable()) {
            Toast.makeText(this, "Perfect", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_dashboard);
            startRide = (ToggleButton) findViewById(R.id.toggleButton);
            enterPickUp = (EditText) findViewById(R.id.PickupLocation);
            enterDestination=(EditText)findViewById(R.id.Destination);
            initMap();
        } else {
            Toast.makeText(this, "No Google Map", Toast.LENGTH_SHORT).show();
        }

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10, 5, new android.location.LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //get the latitude
                    double latitude = location.getLatitude();
                    //get the lognitude
                    double lognitude = location.getLongitude();
                    //instantitate the object of LatLng class
                    LatLng latLng = new LatLng(latitude, lognitude);
                    //instantiate GeoCoder class
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, lognitude, 1);
                        String str = addressList.get(0).getLocality() + "";
                        str += addressList.get(0).getCountryName();
                        map.addMarker(new MarkerOptions().position(latLng).title(str));
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.2f));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }

            });
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 5, new android.location.LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //get the lognitude
                    double latitude = location.getLatitude();
                    //get the lognitude
                    double lognitude = location.getLongitude();
                    //instantitate the object of LatLng class
                    LatLng latLng = new LatLng(latitude, lognitude);
                    //instantiate GeoCoder class
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<android.location.Address> addressList = geocoder.getFromLocation(latitude, lognitude, 1);
                        String str = addressList.get(0).getLocality() + "";
                        str += addressList.get(0).getCountryName();
                        map.addMarker(new MarkerOptions().position(latLng).title(str));
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.2f));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void initMap() {
        MapFragment mapfargment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        //It will give the map detail and returns map object
        mapfargment.getMapAsync(this);
    }

    public boolean isGoogleServiceAvailable() {
        GoogleApiAvailability apiAvalability = GoogleApiAvailability.getInstance();
        int isAvailable = apiAvalability.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (apiAvalability.isUserResolvableError(isAvailable)) {
            Dialog dialog = apiAvalability.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Can't connect to play servivcs", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }

    String pickUpLpcation = "";

    public void enterPickUp() {
        pickUpLpcation = enterPickUp.getText().toString();
        ;
    }

    public void setPickup(View view) throws IOException {
        enterPickUp();
        Geocoder gc = new Geocoder(getApplicationContext());
        List<android.location.Address> pickUpArray = gc.getFromLocationName(pickUpLpcation, 1);
        try {
            pickUpArray = gc.getFromLocationName(pickUpLpcation, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        android.location.Address adr = pickUpArray.get(0);
        double lat = adr.getLatitude();
        double lon = adr.getLongitude();
        LatLng latLng = new LatLng(lat, lon);
        String exactPickup = pickUpArray.get(0).getLocality() + "";
        exactPickup += pickUpArray.get(0).getCountryName();
        map.addMarker(new MarkerOptions().position(latLng).title(exactPickup));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.2f));
    }
    public void setDestination(View view) throws IOException {
        enterPickUp();
        Geocoder gc = new Geocoder(getApplicationContext());
        List<android.location.Address> pickUpArray = gc.getFromLocationName(pickUpLpcation, 1);
        try {
            pickUpArray = gc.getFromLocationName(pickUpLpcation, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        android.location.Address adr = pickUpArray.get(0);
        double lat = adr.getLatitude();
        double lon = adr.getLongitude();
        LatLng latLng = new LatLng(lat, lon);
        String exactPickup = pickUpArray.get(0).getLocality() + "";
        exactPickup += pickUpArray.get(0).getCountryName();
        map.addMarker(new MarkerOptions().position(latLng).title(exactPickup));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.2f));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.toggleButton:
                try {
                    setPickup(v);
                    setDestination(v);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
//            case R.id.Destination:
//                try {
//                    setDestination(v);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                break;

        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
