package mb.com.moovleelogin.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mb.com.moovleelogin.DataBase.DatabaseHelper;
import mb.com.moovleelogin.R;
import mb.com.moovleelogin.UserRelatedClasses.CircularTransformation;
import mb.com.moovleelogin.UserRelatedClasses.CreatePolyLine;
import mb.com.moovleelogin.UserRelatedClasses.LoginUserDetails;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback, View.OnClickListener {

    private static long INTERVAL=1000 * 30 * 1;
    private static float SMALLEST_DISPLACEMENT=0.1F;//0.25F;
    ToggleButton startRide;
    EditText enterPickUp;
    public TextView userName, userEmail;
    public ImageView userPic;

    public static GoogleMap map;
    private LocationManager locationManager;
    public static Location CurrentLocation;

    public static ArrayList<LatLng> points = new ArrayList<>();
    LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isGoogleServiceAvailable()) {
            Toast.makeText(this, "Perfect", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_dashboard);
            startRide = (ToggleButton) findViewById(R.id.toggleButton);
            enterPickUp = (EditText) findViewById(R.id.PickupLocation);
            startRide.setOnClickListener(this);
            initMap();
        } else {
            Toast.makeText(this, "No Google Map", Toast.LENGTH_SHORT).show();
        }
        //createLocationRequest();
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
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, INTERVAL, SMALLEST_DISPLACEMENT, new android.location.LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    CurrentLocation = location;
                    getCurrentLocation();

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
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, INTERVAL, SMALLEST_DISPLACEMENT, new android.location.LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    CurrentLocation = location;
                    getCurrentLocation();
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

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //Updating the header
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View v = navigationView.getHeaderView(0);
        userName = (TextView) v.findViewById(R.id.Username);
        userEmail = (TextView) v.findViewById(R.id.Useremail);
        userPic = (ImageView) v.findViewById(R.id.userImage);
        updateUI();

    }

    public void getCurrentLocation() {
        //get the latitude
        double latitude = CurrentLocation.getLatitude();
        //get the lognitude
        double lognitude = CurrentLocation.getLongitude();
        //instantitate the object of LatLng class
        latLng = new LatLng(latitude, lognitude);
        points.add(latLng); //added
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



    public void updateUI() {
        //Getting information from gamilsigninactivity

        Bundle bundle = getIntent().getExtras();
        String Name = bundle.getString("UserName");
        String Email = bundle.getString("Email");
        String userPicUrl = bundle.getString("PicUri");
        userName.setText(Name);
        userEmail.setText(Email);

        if (userPicUrl == null) {
            userPic.setImageResource(R.drawable.defaultpic);
        } else {
            Glide.with(getApplicationContext()).load(userPicUrl)
                    .transform(new CircularTransformation(DashboardActivity.this))
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(userPic);
        }

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

    //String pickUpLpcation = "";

//    public void enterPickUp() {
//        pickUpLpcation = enterPickUp.getText().toString();
//        ;
//    }

//    public void setPickup(View view) throws IOException {
//        enterPickUp();
//        Geocoder gc = new Geocoder(getApplicationContext());
//        List<android.location.Address> pickUpArray = gc.getFromLocationName(pickUpLpcation, 1);
//        try {
//            pickUpArray = gc.getFromLocationName(pickUpLpcation, 1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        android.location.Address adr = pickUpArray.get(0);
//        double lat = adr.getLatitude();
//        double lon = adr.getLongitude();
//        LatLng latLng = new LatLng(lat, lon);
//        String exactPickup = pickUpArray.get(0).getLocality() + "";
//        exactPickup += pickUpArray.get(0).getCountryName();
//        map.addMarker(new MarkerOptions().position(latLng).title(exactPickup));
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.2f));
//    }

    int count = 0;

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.toggleButton:
                if (count == 0) {
                    int s;
                    LatLng l;
                    s = points.size();
                    l = points.get(s - 1);
                    points.clear();
                    points.add(l);
                    count++;
                    break;
                } else {
                    map.clear();
                    CreatePolyLine createPolyLine = new CreatePolyLine(DashboardActivity.this);
                    createPolyLine.redrawLine();
                    count--;
                    break;
                }
        }


    }

    public GoogleMap getMap()
    {
        return map;
    }
    public Location getLocation()
    {
        return CurrentLocation;
    }
    public ArrayList<LatLng> getPoints()
    {
        return points;
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
//        if (id == R.id.gotorideHistory) {
//            return true;
//        }
//        if(id==R.id.gotohome)
//        {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.rideHistory) {
             //Handle the ride history
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
