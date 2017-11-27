package mb.com.moovleelogin.userRelatedClasses;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import mb.com.moovleelogin.activities.DashboardActivity;

/**
 * Created by Anshul on 22-11-17.
 */

public class CreatePolyLine  {

    public GoogleMap mmap;
    private LocationRequest locationRequest;
    private Location CurrentLocation;
    private static final String TAG = "CreatePolyLine";
    private static  long INTERVAL ;//= 1000 * 60 * 1; //1 minute
    private static  long FASTEST_INTERVAL;// = 1000 * 60 * 1; // 1 minute
    private static  float SMALLEST_DISPLACEMENT ;//= 0.25F; //quarter of a meter
    public  ArrayList<LatLng> points=new ArrayList<>();
    Polyline line;
    private static Context context;
    DashboardActivity dashboardActivity=new DashboardActivity();

    public CreatePolyLine(Context c) {
        context = c;
    }

    protected LocationRequest createLocationRequest()
    {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        locationRequest.setSmallestDisplacement(SMALLEST_DISPLACEMENT); //added
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }
    private  void addMarker() {

        MarkerOptions options = new MarkerOptions();
        CurrentLocation=dashboardActivity.getLocation();
        LatLng currentLatLng = new LatLng(CurrentLocation.getLatitude(),CurrentLocation.getLongitude());
        options.position(currentLatLng);
        Marker mapMarker = mmap.addMarker(options);
        Log.d(TAG, "Marker added.............................");
        mmap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 13));
        Log.d(TAG, "Zoom done.............................");
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(points.get(0).latitude, points.get(0).longitude))
                .zoom(17)
                .bearing(90)
                .tilt(40)
                .build();
        mmap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void redrawLine(){
        createLocationRequest();
        mmap=dashboardActivity.getMap();
        mmap.clear();  //clears all Markers and Polylines
        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        points=dashboardActivity.getPoints();
        if (points.size() <= 1)
            Toast.makeText(context, "No Location Selected to Display!", Toast.LENGTH_SHORT).show();
        for (int i = 0; i < points.size(); i++) {
            LatLng point = points.get(i);
            options.add(point);
        }
        addMarker(); //add Marker in current position
        line = dashboardActivity.map.addPolyline(options); //add Polyline

    }

}
