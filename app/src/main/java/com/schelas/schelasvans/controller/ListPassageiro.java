package com.schelas.schelasvans.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
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
import com.schelas.schelasvans.main.Dashboard;
import com.schelas.schelasvans.model.Passageiros;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListPassageiro extends AppCompatActivity {
    private static final String URL_FETCH = "https://schelasvansapi.000webhostapp.com/api/get/Passageiro.php";
    private static final String TAG = "SchelasVans";
    private RecyclerView rvPassageiros;
    private ImageView ivToolbar;
    private Toolbar toolbar;
    private List<Passageiros> passageiros;
    private Adapter mAdapter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_passageiro);
        setUI();
        setToolbar();
        passageiros = getPassageiros();
        setRecyclerView();
        setFab();
    }

    private void setUI(){
        toolbar = findViewById(R.id.destool);
        rvPassageiros = findViewById(R.id.rv_passageiros);
        fab = (FloatingActionButton) findViewById(R.id.fabPass);
    }

    private void setFab(){

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(ListPassageiro.this, CadastroPassageiro.class);
                startActivity(register);
            }
        });
    }



    private void setRecyclerView(){

        rvPassageiros.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvPassageiros.setLayoutManager(mLayoutManager);

        mAdapter = new Adapter(passageiros);
        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListPassageiro.this);
                                    builder.setMessage(passageiros.get(rvPassageiros.getChildLayoutPosition(view)).getIdPass())
                                            .setNegativeButton(passageiros.get(rvPassageiros.getChildLayoutPosition(view)).getName(), null)
                                            .create()
                                            .show();
            }
        });

        rvPassageiros.setAdapter(mAdapter);
        rvPassageiros.setItemAnimator(new DefaultItemAnimator());


    }

    private List<Passageiros> getPassageiros(){
        final List<Passageiros> passes = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_FETCH , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jobj = new JSONArray(response);

                    for (int i = 0; i < jobj.length(); i++) {
                        JSONObject ob = jobj.getJSONObject(i);
                        Passageiros pass = new Passageiros(ob.getString("PassageiroNome"), ob.getString("PassageiroId"), 1);
                        passes.add(pass);

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

        RequestQueue requestQueue= Volley.newRequestQueue(ListPassageiro.this);
        requestQueue.add(stringRequest);

        return passes;
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
