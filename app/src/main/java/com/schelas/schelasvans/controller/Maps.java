package com.schelas.schelasvans.controller;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.schelas.schelasvans.R;
import com.schelas.schelasvans.model.Destinos;
import com.schelas.schelasvans.model.Passageiros;

import java.io.IOException;
import java.util.List;

public class Maps extends FragmentActivity implements OnMapReadyCallback{
    private GoogleMap mMap;
    private Passageiros passageiro;
    private Destinos destino;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps);

        passageiro = (Passageiros) getIntent().getSerializableExtra("passageiro");
        destino = (Destinos) getIntent().getSerializableExtra("destino");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.Maps);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;
        String address;
        String descricao;
        if(passageiro != null) {
            address = passageiro.getAddress() + "," + passageiro.getAddressNumber();
            descricao = passageiro.getName();
        }else {
            address = destino.getRua() + ", " + destino.getNum();
            descricao = destino.getDescricao();
        }

        LatLng latlng = getLocationFromAddress(Maps.this, address);

        if(latlng != null) {
            mMap.addMarker(new MarkerOptions().position(latlng).title(descricao));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latlng)
                    .zoom(18)
                    .tilt(0)
                    .build();
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }


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
            android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(Maps.this);
            builder.setMessage(R.string.addressNotExist)
                    .setNegativeButton(R.string.OK, null)
                    .create()
                    .show();
            return null;
        }

        return p1;
    }


}