package com.schelas.schelasvans.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.schelas.schelasvans.R;
import com.schelas.schelasvans.main.Dashboard;
import com.schelas.schelasvans.model.AlocaPassageiroRequest;
import com.schelas.schelasvans.model.AlocaVeiculoRequest;
import com.schelas.schelasvans.model.Destinos;
import com.schelas.schelasvans.model.Veiculos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AlocaVeic extends AppCompatActivity {


    private Button btnAlocar;
    private ImageView ivToolbar;
    private Spinner spVeic;
    private ListView spDest;
    private List<String> veics;
    private List<Destinos> dests;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alocarvei);

        setUI();
        setData();
        setToolbar();
        Act();
    }

    private void setUI(){
        spVeic = findViewById(R.id.spinner1);
        spDest = findViewById(R.id.spinner3);
        btnAlocar = findViewById(R.id.alocarVei);
    }

    private void setToolbar(){
        ivToolbar = (ImageView) findViewById(R.id.imgNavBar);

        ivToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void setData(){
        dests = getDests();

        ArrayAdapter<Destinos> adapter = new ArrayAdapter<Destinos>(this, android.R.layout.simple_list_item_multiple_choice, dests){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                Destinos dest = (Destinos) spDest.getAdapter().getItem(position);
                tv.setText(dest.getDescricao());

                return view;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                Destinos dest = (Destinos) spDest.getAdapter().getItem(position);
                tv.setText(dest.getDescricao());

                return view;
            }
        };

        spDest.setAdapter(adapter);

        veics = getVeics();

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, veics);
        spVeic.setAdapter(adapter2);

    }

    private List<Destinos> getDests(){
        final List<Destinos> listDest = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://schelasvansapi.000webhostapp.com/api/get/Destino.php" , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jobj = new JSONArray(response);

                    for (int i = 0; i < jobj.length(); i++) {
                        JSONObject ob = jobj.getJSONObject(i);
                        listDest.add(new Destinos(ob.getInt("DestinoId"),ob.getString("DestinoDesc")));
                    }

                    ((BaseAdapter) spDest.getAdapter()).notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(AlocaVeic.this);
        requestQueue.add(stringRequest);

        return listDest;
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
                        listVeic.add(ob.getString("VeiculoPlaca")+" - Restam "+ob.getString("VeiculoCapacidade")+ " lugares.");
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

        RequestQueue requestQueue= Volley.newRequestQueue(AlocaVeic.this);
        requestQueue.add(stringRequest);

        return listVeic;
    }


    private void Act(){
        btnAlocar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> listPass = new ArrayList<>();
                String veic = "";

                SparseBooleanArray checked = spDest.getCheckedItemPositions();

                for (int i = 0; i < spDest.getAdapter().getCount(); i++) {
                    if (checked.get(i)) {
                        Destinos destAtual = (Destinos) spDest.getAdapter().getItem(i);
                        listPass.add(destAtual.getId().toString());
                    }
                }

                veic = spVeic.getSelectedItem().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("SchelasVans", "Response: "+response);

                            if (response.contains("true")) {

                                Intent intent = new Intent(AlocaVeic.this, Dashboard.class);
                                startActivity(intent);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AlocaVeic.this);
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

                AlocaVeiculoRequest veiculoRequest = new AlocaVeiculoRequest(listPass,veic,responseListener);
                RequestQueue queue = Volley.newRequestQueue(AlocaVeic.this);
                queue.add(veiculoRequest);


            }
        });
    }

}
