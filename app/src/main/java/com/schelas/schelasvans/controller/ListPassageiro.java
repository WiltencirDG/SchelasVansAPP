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
import android.widget.LinearLayout;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.schelas.schelasvans.R;
import com.schelas.schelasvans.model.PassageiroRequest;
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
    private AdapterPassageiros mAdapterPassageiros;
    private FloatingActionButton fab;
    private TextView tvEmpty;


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
        tvEmpty = findViewById(R.id.emptyList);
    }

    private void setFab(){

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(ListPassageiro.this, CadastroPassageiro.class);
                register.putExtra("type","ins");
                startActivity(register);
            }
        });
    }



    private void setRecyclerView(){

        rvPassageiros.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvPassageiros.setLayoutManager(mLayoutManager);

        mAdapterPassageiros = new AdapterPassageiros(passageiros);
        mAdapterPassageiros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToDescription(passageiros.get(rvPassageiros.getChildLayoutPosition(view)));

            }
        });

        rvPassageiros.setAdapter(mAdapterPassageiros);
        rvPassageiros.setItemAnimator(new DefaultItemAnimator());


    }

    private void goToDescription(Passageiros passageiro){
        Intent intent = new Intent(ListPassageiro.this, DetailPassageiro.class);
        intent.putExtra("passageiro", passageiro);
        startActivity(intent);
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
                        Passageiros pass = new Passageiros(ob.getString("PassageiroNome"), ob.getString("PassageiroId"), ob.getString("PassageiroLogradouro"), ob.getString("PassageiroNum"), ob.getString("PassageiroFone"), ob.getString("PassageiroEmail"), ob.getString("PassageiroBairro"), ob.getString("PassageiroCidade"));
                        passes.add(pass);

                    }
                    if(passes.size() == 0){
                        tvEmpty.setVisibility(View.VISIBLE);
                    }
                    mAdapterPassageiros.notifyDataSetChanged();

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
