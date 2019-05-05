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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.schelas.schelasvans.R;
import com.schelas.schelasvans.model.Veiculos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListVeiculo extends AppCompatActivity {
    private static final String URL_FETCH = "https://schelasvansapi.000webhostapp.com/api/get/Veiculo.php";
    private static final String TAG = "SchelasVans";
    private RecyclerView rvVeiculos;
    private ImageView ivToolbar;
    private Toolbar toolbar;
    private List<Veiculos> veiculos;
    private AdapterVeiculos mAdapter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_veiculo);
        setUI();
        setToolbar();
        veiculos = getVeiculos();
        setRecyclerView();
        setFab();
    }

    private void setUI(){
        toolbar = findViewById(R.id.destool);
        rvVeiculos = findViewById(R.id.rv_veiculos);
        fab = findViewById(R.id.fabPass);
    }

    private void setFab(){

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(ListVeiculo.this, CadastroVeiculo.class);
                startActivity(register);
            }
        });
    }



    private void setRecyclerView(){

        rvVeiculos.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvVeiculos.setLayoutManager(mLayoutManager);

        mAdapter = new AdapterVeiculos(veiculos);
        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToDescription(veiculos.get(rvVeiculos.getChildLayoutPosition(view)));

            }
        });

        rvVeiculos.setAdapter(mAdapter);
        rvVeiculos.setItemAnimator(new DefaultItemAnimator());


    }

    private void goToDescription(Veiculos veiculos){
        Intent intent = new Intent(ListVeiculo.this, DetailVeiculo.class);
        intent.putExtra("veiculo",veiculos);
        startActivity(intent);
    }

    private List<Veiculos> getVeiculos(){
        final List<Veiculos> veics = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_FETCH , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jobj = new JSONArray(response);

                    for (int i = 0; i < jobj.length(); i++) {
                        JSONObject ob = jobj.getJSONObject(i);
                        Veiculos veic = new Veiculos(ob.getInt("VeiculoId"),ob.getString("VeiculoPlaca"),ob.getString("VeiculoCor"),ob.getString("VeiculoModelo"),ob.getString("VeiculoMarca"),ob.getInt("VeiculoCapacidade"));
                        veics.add(veic);

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

        RequestQueue requestQueue= Volley.newRequestQueue(ListVeiculo.this);
        requestQueue.add(stringRequest);

        return veics;
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

