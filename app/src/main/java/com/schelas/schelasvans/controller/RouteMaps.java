package com.schelas.schelasvans.controller;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.schelas.schelasvans.R;
import com.schelas.schelasvans.main.Dashboard;
import com.schelas.schelasvans.model.Veiculos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteMaps extends FragmentActivity implements OnMapReadyCallback, DirectionCallback {
    private GoogleMap mMap;

    private String veiculo;

    private LatLng origin;
    private LatLng destination;
    private List<String> sDestinations;
    private String destinos;
    private List<LatLng> destinations;
    private LocationManager locationManager;
    private ImageView ivToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps);

        setToolbar();

        veiculo =  getIntent().getStringExtra("veiculo");
        destination = getLocationFromAddress(RouteMaps.this,getIntent().getStringExtra("destination"));
        destinos = getIntent().getStringExtra("destinations");

        sDestinations = new ArrayList<String>(Arrays.asList(destinos.split(",")));

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        destinations = new ArrayList<>();

        for(String forDestination : sDestinations){
            destinations.add(getLocationFromAddress(RouteMaps.this,forDestination));
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.Maps);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        requestGPS();
        if(origin != null)
            requestDirection();

    }
    public void requestDirection() {

        GoogleDirection.withServerKey(getResources().getString(R.string.google_maps_key))
                .from(origin)
                .and(destinations)
                .to(destination)
                .transportMode(TransportMode.DRIVING)
                .execute(this);

    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {

        if (direction.isOK()) {
            Route route = direction.getRouteList().get(0);
            int legCount = route.getLegList().size();
            for (int index = 0; index < legCount; index++) {
                Leg leg = route.getLegList().get(index);
                if(index == 0) {
                    mMap.addMarker(new MarkerOptions().position(leg.getStartLocation().getCoordination()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                }else{
                    mMap.addMarker(new MarkerOptions().position(leg.getStartLocation().getCoordination()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                }
                if (index == legCount - 1) {
                    mMap.addMarker(new MarkerOptions().position(leg.getEndLocation().getCoordination()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                }
                List<Step> stepList = leg.getStepList();
                ArrayList<PolylineOptions> polylineOptionList = DirectionConverter.createTransitPolyline(this, stepList, 4, Color.RED, 3, Color.BLUE);
                for (PolylineOptions polylineOption : polylineOptionList) {
                    mMap.addPolyline(polylineOption);
                }
            }
            setCameraWithCoordinationBounds(route);

        } else {

            Toast.makeText(this,direction.getErrorMessage(), Toast.LENGTH_SHORT).show();
            Log.e("error",""+direction.getErrorMessage());
        }
    }

    @Override
    public void onDirectionFailure(Throwable t) {

    }

    private void setCameraWithCoordinationBounds(Route routeMaps) {
        LatLng southwest = routeMaps.getBound().getSouthwestCoordination().getCoordination();
        LatLng northeast = routeMaps.getBound().getNortheastCoordination().getCoordination();
        LatLngBounds bounds = new LatLngBounds(southwest, northeast);
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 1);
            if (address == null) {
                return null;
            }else {
                Address location = address.get(0);
                p1 = new LatLng(location.getLatitude(), location.getLongitude());
            }

        } catch (IOException ex) {

            ex.printStackTrace();
        } catch (IndexOutOfBoundsException e){
            AlertDialog.Builder builder = new AlertDialog.Builder(RouteMaps.this);
            builder.setMessage(R.string.addressNotExist)
                    .setNegativeButton(R.string.OK, null)
                    .create()
                    .show();
            finish();
            return null;
        }

        return p1;
    }

    public class GPSListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            origin = new LatLng(location.getLatitude(),location.getLongitude());
            requestDirection();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(RouteMaps.this, "GPS desligado", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(RouteMaps.this,"Recebendo sua localização...", Toast.LENGTH_LONG).show();
        }
    }

    private void requestGPS(){
        try {
            Toast.makeText(this,"Recebendo sua localização...", Toast.LENGTH_LONG).show();
            locationManager.requestSingleUpdate( LocationManager.GPS_PROVIDER, new GPSListener(), null );
        } catch ( SecurityException e ) {
            e.printStackTrace();
            Toast.makeText(this, "Não foi permitido o acesso ao GPS", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void setToolbar(){
        ivToolbar = findViewById(R.id.imgNavBar);
        ivToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(RouteMaps.this, Dashboard.class);
                RouteMaps.this.startActivity(intent);
            }
        });

    }



}