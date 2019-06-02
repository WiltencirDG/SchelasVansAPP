package com.schelas.schelasvans.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.Dash;
import com.schelas.schelasvans.R;
import com.schelas.schelasvans.main.Dashboard;
import com.schelas.schelasvans.model.AlocaPassageiroRequest;
import com.schelas.schelasvans.model.Passageiros;
import com.schelas.schelasvans.model.VeiculoRequest;
import com.schelas.schelasvans.model.Veiculos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AlocaPass extends AppCompatActivity {


    private Button btnAlocar;
    private ImageView ivToolbar;
    private ListView spVeic;
    private ListView spPass;
    private List<String> passes;
    private List<String> veics;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alocarpass);

        setUI();
        setData();
        setToolbar();
        Act();
    }

    private void setUI(){
        spVeic = findViewById(R.id.spinner2);
        spPass = findViewById(R.id.spinner1);
        btnAlocar = findViewById(R.id.alocarPass);
    }

    private void setToolbar(){
        ivToolbar = (ImageView) findViewById(R.id.imgNavBar);

        ivToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void setData(){
        passes = getPasses();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, passes);
        spPass.setAdapter(adapter);

        veics = getVeics();

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, veics);
        spVeic.setAdapter(adapter2);

    }

    private List<String> getPasses(){
        final List<String> listPass = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://schelasvansapi.000webhostapp.com/api/getDisp/Passageiro.php" , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jobj = new JSONArray(response);

                    for (int i = 0; i < jobj.length(); i++) {
                        JSONObject ob = jobj.getJSONObject(i);
                        listPass.add(ob.getString("PassageiroEmail"));
                    }

                    ((BaseAdapter) spPass.getAdapter()).notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(AlocaPass.this);
        requestQueue.add(stringRequest);

        return listPass;
    }


    private List<String> getVeics(){
        final List<String> listVeic = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://schelasvansapi.000webhostapp.com/api/getDisp/Veiculo.php" , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jobj = new JSONArray(response);

                    for (int i = 0; i < jobj.length(); i++) {
                        JSONObject ob = jobj.getJSONObject(i);
                        listVeic.add(ob.getString("VeiculoPlaca")+" - "+ob.getString("VeiculoCapacidade")+ " lugares restam");
                    }
                    ((BaseAdapter) spVeic.getAdapter()).notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(AlocaPass.this);
        requestQueue.add(stringRequest);

        return listVeic;
    }


    private void Act(){
        btnAlocar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> listPass = new ArrayList<>();
                String veic = "";

                SparseBooleanArray checked = spPass.getCheckedItemPositions();

                for (int i = 0; i < spPass.getAdapter().getCount(); i++) {
                    if (checked.get(i)) {
                        listPass.add('"'+spPass.getAdapter().getItem(i).toString()+'"');
                    }
                }

                checked = spVeic.getCheckedItemPositions();

                for (int i = 0; i < spVeic.getAdapter().getCount(); i++) {
                    if (checked.get(i)) {
                        veic = '"'+spVeic.getAdapter().getItem(i).toString().substring(0,spVeic.getAdapter().getItem(i).toString().indexOf(" "))+'"';

                    }
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            if (response.contains("true")) {

                                Intent intent = new Intent(AlocaPass.this, Dashboard.class);
                                startActivity(intent);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AlocaPass.this);
                                builder.setMessage(R.string.cadastroFail)
                                        .setNegativeButton(R.string.tryAgain, null)
                                        .create()
                                        .show();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                };

                AlocaPassageiroRequest veiculoRequest = new AlocaPassageiroRequest(listPass,veic,responseListener);
                RequestQueue queue = Volley.newRequestQueue(AlocaPass.this);
                queue.add(veiculoRequest);


            }
        });
    }

}
