package com.schelas.schelasvans.controller;

import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.schelas.schelasvans.R;
import com.schelas.schelasvans.model.Veiculos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RouteMaps extends FragmentActivity implements OnMapReadyCallback, DirectionCallback {
    private GoogleMap mMap;

    private Veiculos veiculo;

    private LatLng origin;
    private LatLng destination;
    private List<String> sDestinations;
    private List<LatLng> destinations;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps);

        veiculo = (Veiculos) getIntent().getSerializableExtra("veiculo");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        destination = getLocationFromAddress(RouteMaps.this,Veiculos.getDestination(this,veiculo));

        for(String forDestination : Veiculos.getListDestinations(this,veiculo)){
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
            Toast.makeText(this, "Direction success", Toast.LENGTH_SHORT).show();
            Route route = direction.getRouteList().get(0);
            mMap.addMarker(new MarkerOptions().position(origin));
            mMap.addMarker(new MarkerOptions().position(destination));

            ArrayList<LatLng> directionPositionList = route.getLegList().get(0).getDirectionPoint();
            mMap.addPolyline(DirectionConverter.createPolyline(this, directionPositionList, 5, Color.RED));
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
            return null;
        }

        return p1;
    }

    public class GPSListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            // your code and required methods go here...
            Log.d("SCHELAS", "onLocationChanged: "+location);
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

        }
    }

    private void requestGPS(){
        try {
            locationManager.requestSingleUpdate( LocationManager.GPS_PROVIDER, new GPSListener(), null );
        } catch ( SecurityException e ) {
            e.printStackTrace();
            Toast.makeText(this, "Não foi permitido o acesso ao GPS", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}