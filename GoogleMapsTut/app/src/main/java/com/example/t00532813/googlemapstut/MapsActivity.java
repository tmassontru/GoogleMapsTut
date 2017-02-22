package com.example.t00532813.googlemapstut;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.security.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback ,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private Polygon polygon;
    private PolygonOptions rectOptions;

    private Polygon polygon1, polygon2, polygon3;
    private PolygonOptions rectOptions1, rectOptions2, rectOptions3;
    private boolean touched1,touched2, touched3 = false;

    Boolean crossed = false;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Marker mCurrLocationMarker;
    double lattitude;
    double longitude;
    double lat1,long1,lat2,long2;
    Date startTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
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
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        lat1 = 50.671230;
        long1 = -120.363513;
        lat2 = 50.670750;
        long2 = -120.362405;

        // Add a marker in TRU and move the camera
        LatLng kamloops = new LatLng(50.670504,-120.361911);
        /*mMap.addMarker(new MarkerOptions().position(kamloops).title("Marker in Kamloops 1")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_1)));*/
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kamloops, 17));
        new AlertDialog.Builder(this)
                .setTitle("Start The Hunt")
                .setMessage("Touch the boxes in the right order to win.\nPress OK to start.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startTime = new Date();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


        /*// Add a polygon
        rectOptions = new PolygonOptions()
                .add(   new LatLng(lat1, long1),
                        new LatLng(lat1, long2),
                        new LatLng(lat2, long2),
                        new LatLng(lat2, long1));
        if (crossed == true) {
            rectOptions.strokeColor(-65536);
            rectOptions.fillColor(Color.argb(20, 255, 80, 255));
        }
        // Get back the mutable Polygon
        rectOptions.fillColor(Color.GREEN);
        polygon = mMap.addPolygon(rectOptions);*/


        rectOptions1 = new PolygonOptions()
                .add(   new LatLng(50.670783, -120.362407),
                        new LatLng(50.670790, -120.361910),
                        new LatLng(50.670591, -120.361965),
                        new LatLng(50.670593, -120.362446));

        rectOptions1.strokeColor(-65536);
        rectOptions1.fillColor(Color.argb(20, 255, 80, 255));

        rectOptions2 = new PolygonOptions()
                .add(   new LatLng(50.670742, -120.361825),
                        new LatLng(50.670761, -120.361394),
                        new LatLng(50.670525, -120.361376),
                        new LatLng(50.670500, -120.361838));

        rectOptions2.strokeColor(-65536);
        rectOptions2.fillColor(Color.argb(20, 255, 80, 255));


        rectOptions3 = new PolygonOptions()
                .add(   new LatLng(50.670427, -120.362548),
                        new LatLng(50.670422, -120.362088),
                        new LatLng(50.670246, -120.362103),
                        new LatLng(50.670234, -120.362494));

        rectOptions3.strokeColor(-65536);
        rectOptions3.fillColor(Color.argb(20, 255, 80, 255));

        // Get back the mutable Polygon
        polygon1 = mMap.addPolygon(rectOptions1);
        polygon2 = mMap.addPolygon(rectOptions2);
        polygon3 = mMap.addPolygon(rectOptions3);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION)) {

                Log.d("Log", "#1 INFO: shouldShowRequestPermissionRationale");
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                showMessageOKCancel("You need to allow access to your location. Otherwise, you can't use the app!");

            } else {

                Log.d("Log", "#2 INFO: request the permission - No explanation needed");

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {

            //Permission granted
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }


    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private void showMessageOKCancel(String message) {
        Log.d("Log", "#5 INFO: call AlertDialog");

        new AlertDialog.Builder(MapsActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", listener)
                .setNegativeButton("Cancel", listener)
                .create()
                .show();
    }


    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

        final int BUTTON_NEGATIVE = -2;
        final int BUTTON_POSITIVE = -1;

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case BUTTON_NEGATIVE:
                    // int which = -2
                    Log.d("Log", "#7 INFO: permission is denied for the second time!");

                    dialog.dismiss();
                    break;

                case BUTTON_POSITIVE:
                    // int which = -1
                    Log.d("Log", "#6 INFO: send a second request");

                    ActivityCompat.requestPermissions(
                            MapsActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);
                    dialog.dismiss();
                    break;
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.d("Log", "#3 INFO: received a response permission was granted");
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    } else {
                        buildGoogleApiClient();
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    Log.d("Log", "#4 INFO: received a response permission - denied");

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        if (location != null) {
            //mLastLocation = location;
            lattitude = location.getLatitude();
            longitude = location.getLongitude();


            //Check in poly2
            if (polygon2!=null && isInside(polygon2,lattitude,longitude) && !touched2) {
                polygon2.setStrokeColor(Color.GREEN);
                touched2 = true;
                Toast.makeText(getBaseContext(),"You found the first point!", Toast.LENGTH_SHORT).show();
            }
            //Check in poly1
            if (polygon1!=null &&isInside(polygon1,lattitude,longitude) && !touched1) {
                if (touched2) {
                    polygon1.setStrokeColor(Color.GREEN);
                    touched1 = true;
                    Toast.makeText(getBaseContext(), "You found the second point!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Come Back Later.", Toast.LENGTH_SHORT).show();
                }
            }
            //Check in poly3
            if (polygon3!=null &&isInside(polygon3,lattitude,longitude) && !touched3) {
                if (touched2 && touched1) {
                    polygon3.setStrokeColor(Color.GREEN);
                    touched3 = true;
                    Toast.makeText(getBaseContext(), "You found the last point!", Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(this)
                            .setTitle("You did it!")
                            .setMessage("You completed the hunt in  " + (new Date().getTime() - startTime.getTime()))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                } else {
                    Toast.makeText(getBaseContext(), "Come Back Later.", Toast.LENGTH_SHORT).show();
                }
            }
            //isInside(polygon, lattitude, longitude);
            /*if (lattitude <= lat1 && lattitude >= lat2 && longitude <= long2 && longitude >= long1){
                Toast.makeText(getBaseContext(),"Current Location: Lat = " + lattitude + ", and longitude = " + longitude, Toast.LENGTH_SHORT).show();
            }*/
        }
    }

    public boolean isInside(Polygon polygon, double lat, double longitude) {
        List<LatLng> points = polygon.getPoints();

        //Toast.makeText(getBaseContext(),points.get(0)+", "+points.get(1)+", "+points.get(2)+", "+points.get(3), Toast.LENGTH_SHORT).show();
        if (lat <= points.get(0).latitude && lat >= points.get(2).latitude && longitude <= points.get(1).longitude && longitude >= points.get(0).longitude){
            //Toast.makeText(getBaseContext(),"Current Location: Lat = " + lattitude + ", and longitude = " + longitude, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
