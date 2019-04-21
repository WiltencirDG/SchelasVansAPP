package com.schelas.schelasvans.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.schelas.schelasvans.R;
import com.schelas.schelasvans.model.DestinoRequest;
import com.schelas.schelasvans.model.Destinos;
import com.schelas.schelasvans.model.Passageiros;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListDestino extends AppCompatActivity {
    private static final String URL_FETCH = "https://schelasvansapi.000webhostapp.com/api/get/Destino.php";
    private static final String TAG = "SchelasVans";
    private RecyclerView rvDestinos;
    private ImageView ivToolbar;
    private Toolbar toolbar;
    private List<Destinos> destinos;
    private AdapterDestinos mAdapter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_destino);
        setUI();
        setToolbar();
        destinos = getDestinos();
        setRecyclerView();
        setFab();
    }

    private void setUI(){
        toolbar = findViewById(R.id.destool);
        rvDestinos = findViewById(R.id.rv_destinos);
        fab = findViewById(R.id.fabPass);
    }

    private void setFab(){

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(ListDestino.this, CadastroDestino.class);
                startActivity(register);
            }
        });
    }



    private void setRecyclerView(){

        rvDestinos.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvDestinos.setLayoutManager(mLayoutManager);

        mAdapter = new AdapterDestinos(destinos);
        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToDescription(destinos.get(rvDestinos.getChildLayoutPosition(view)));

            }
        });

        rvDestinos.setAdapter(mAdapter);
        rvDestinos.setItemAnimator(new DefaultItemAnimator());


    }

    private void goToDescription(Destinos destinos){
        Intent intent = new Intent(ListDestino.this, DetailDestino.class);
        intent.putExtra("destino",destinos);
        startActivity(intent);
    }

    private List<Destinos> getDestinos(){
        final List<Destinos> dests = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_FETCH , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jobj = new JSONArray(response);

                    for (int i = 0; i < jobj.length(); i++) {
                        JSONObject ob = jobj.getJSONObject(i);
                        Destinos dest = new Destinos(ob.getInt("DestinoId"),ob.getString("DestinoDesc"),ob.getString("DestinoLogradouro"),ob.getString("DestinoNum"),ob.getString("DestinoBairro"),ob.getString("DestinoCidade"));
                        dests.add(dest);

                    }

                    mAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(ListDestino.this);
        requestQueue.add(stringRequest);

        return dests;
    }

    private void setToolbar(){
        ivToolbar = findViewById(R.id.imgNavBar);
        ivToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
