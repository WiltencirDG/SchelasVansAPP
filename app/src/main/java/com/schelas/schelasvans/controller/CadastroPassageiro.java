package com.schelas.schelasvans.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.schelas.schelasvans.model.Passageiros;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CadastroPassageiro extends AppCompatActivity {
    private static final String URL_FETCH = "https://demo2749685.mockable.io/passageiros";
    private RecyclerView rvPassageiros;
    private ImageView ivToolbar;
    private Toolbar toolbar;
    private List<Passageiros> passageiros;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_passageiro);
        setUI();
        setToolbar();
        passageiros = getPassageiros();
        setRecyclerView();
    }

    private void setUI(){
        toolbar = findViewById(R.id.destool);
        rvPassageiros = findViewById(R.id.rv_passageiros);
    }

    private void setRecyclerView(){
        rvPassageiros.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvPassageiros.setLayoutManager(mLayoutManager);

        PassageirosAdapter mAdapter = new PassageirosAdapter(passageiros);
        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                (passageiros.get(rvPassageiros.getChildLayoutPosition(view)));
            }
        });

        rvPassageiros.setAdapter(mAdapter);
        rvPassageiros.setItemAnimator(new DefaultItemAnimator());
    }

    private List<Passageiros> getPassageiros(){
        final List<Passageiros> passes = new ArrayList<>();

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_FETCH , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject ob = array.getJSONObject(i);
                        Passageiros pass = new Passageiros(ob.getString("nomePass"), ob.getString("idPass"), 1);
                        passes.add(pass);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(CadastroPassageiro.this);
        requestQueue.add(stringRequest);


        Passageiros pass = new Passageiros("WIltera", "1", 1);
        passes.add(pass);

        pass = new Passageiros("André", "2", 1);
        passes.add(pass);

        pass = new Passageiros("Bruno", "3", 1);
        passes.add(pass);

        pass = new Passageiros("Paulo", "4", 1);
        passes.add(pass);

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
