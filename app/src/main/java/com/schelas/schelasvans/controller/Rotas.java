package com.schelas.schelasvans.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

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

public class Rotas extends AppCompatActivity {

    private ImageView ivToolbar;
    private Spinner spinner;
    private List<Veiculos> veiculos;
    //private GoogleMap mMap;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotas);

        setToolbar();
        setUI();
        setData();
        Act();
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

    private void setUI() {
        spinner = findViewById(R.id.spinnerRotaVeic);

    }

    private void setData(){
        veiculos = getVeics();
        ArrayAdapter<Veiculos> adapter = new ArrayAdapter<Veiculos>(this, android.R.layout.simple_list_item_1, veiculos){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                Veiculos veic = (Veiculos)spinner.getAdapter().getItem(position);
                tv.setText(veic.getPlaca());

                return view;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                Veiculos veic = (Veiculos)spinner.getAdapter().getItem(position);
                tv.setText(veic.getPlaca());

                return view;
            }
        };

        spinner.setAdapter(adapter);

    }

    private void Act(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                Veiculos veic = (Veiculos)spinner.getAdapter().getItem(position);

                Intent i = new Intent(getBaseContext(), RouteMaps.class);
                i.putExtra("veiculo",veic);
                startActivity(i);

            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    private List<Veiculos> getVeics(){
        final List<Veiculos> listVeic = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://schelasvansapi.000webhostapp.com/api/get/Veiculo.php" , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jobj = new JSONArray(response);

                    for (int i = 0; i < jobj.length(); i++) {
                        JSONObject ob = jobj.getJSONObject(i);
                        listVeic.add(new Veiculos(ob.getInt("VeiculoId"), ob.getString("VeiculoPlaca"), ob.getString("VeiculoCor"), ob.getString("VeiculoModelo"), ob.getString("VeiculoMarca"), ob.getInt("VeiculoCapacidade")));
                    }
                    ((BaseAdapter) spinner.getAdapter()).notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(Rotas.this);
        requestQueue.add(stringRequest);

        return listVeic;
    }
}
