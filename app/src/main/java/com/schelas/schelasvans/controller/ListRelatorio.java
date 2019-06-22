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

public class ListRelatorio extends AppCompatActivity {
    private static final String URL_FETCH = "https://schelasvansapi.000webhostapp.com/api/get/Veiculo.php";
    private static final String TAG = "SchelasVans";
    private RecyclerView rvRelatorios;
    private ImageView ivToolbar;
    private Toolbar toolbar;
    private List<String> relatorios;
    private AdapterRelatorios mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_relatorio);
        setUI();
        setToolbar();
        relatorios = getRelatorios();
        setRecyclerView();
    }

    private void setUI(){
        toolbar = findViewById(R.id.destool);
        rvRelatorios = findViewById(R.id.rv_relatorio);
    }

    private void setRecyclerView(){

        rvRelatorios.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvRelatorios.setLayoutManager(mLayoutManager);

        mAdapter = new AdapterRelatorios(relatorios);
        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToDescription(relatorios.get(rvRelatorios.getChildLayoutPosition(view)));

            }
        });

        rvRelatorios.setAdapter(mAdapter);
        rvRelatorios.setItemAnimator(new DefaultItemAnimator());


    }

    private void goToDescription(String relatorio){
        //TODO: If stataments for direct to the right one
        Log.d(TAG,relatorio);
    }

    private List<String> getRelatorios(){
        final List<String> relats = new ArrayList<>();

        relats.add("Passageiros");
        relats.add("Rotas");
        relats.add("Presença");

        return relats;
    }

    private void setToolbar(){
        ivToolbar = findViewById(R.id.imgNavBar);
        ivToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

