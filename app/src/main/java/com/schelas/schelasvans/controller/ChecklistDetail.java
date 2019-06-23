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
import com.schelas.schelasvans.model.Passageiros;
import com.schelas.schelasvans.model.Veiculos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChecklistDetail extends AppCompatActivity {

    private ImageView ivToolbar;
    private Spinner spinner;
    private ListView listPasses;
    private List<Veiculos> veiculos;
    private List<Passageiros> passageiros;
    private Button btnCheck;

    private String veicId;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        setToolbar();
        setUI();
        btnCheck.setVisibility(View.GONE);
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
        spinner = findViewById(R.id.spinnerChecklistVeic);
        btnCheck = findViewById(R.id.btn_checklist);
        listPasses = findViewById(R.id.listChecklistPass);
    }

    private void setData(){
        veiculos = getVeics();
        passageiros = getPasses();

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


        ArrayAdapter<Passageiros> adapter2 = new ArrayAdapter<Passageiros>(this, android.R.layout.simple_list_item_multiple_choice, passageiros){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                Passageiros pass = (Passageiros) listPasses.getAdapter().getItem(position);
                tv.setText(pass.getName());

                return view;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                Passageiros pass = (Passageiros) listPasses.getAdapter().getItem(position);
                tv.setText(pass.getName());

                return view;
            }
        };

        spinner.setAdapter(adapter);

        listPasses.setAdapter(adapter2);

    }

    private void Act(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                btnCheck.setVisibility(View.GONE);
                Veiculos veic = (Veiculos)spinner.getAdapter().getItem(position);
                //TODO: Set the right behaviour when select new one
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: ChecklistRequest
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

        RequestQueue requestQueue= Volley.newRequestQueue(ChecklistDetail.this);
        requestQueue.add(stringRequest);

        return listVeic;
    }

    private List<Passageiros> getPasses(){
        final List<Passageiros> listPass = new ArrayList<>();

        //TODO: Pass a new parameter. VeiculoId.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://schelasvansapi.000webhostapp.com/api/get/ChecklistPass.php" , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jobj = new JSONArray(response);

                    for (int i = 0; i < jobj.length(); i++) {
                        JSONObject ob = jobj.getJSONObject(i);
                        Passageiros pass = new Passageiros();
                        pass = pass.getById(getBaseContext(), ob.getString("PassageiroId"));
                        listPass.add(pass);
                        //TODO: Check if will return null. Then: Add all to a list, for into the list, get the objects
                    }
                    ((BaseAdapter) spinner.getAdapter()).notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: Toast to error because of the nullPointer
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(ChecklistDetail.this);
        requestQueue.add(stringRequest);

        return listPass;
    }


}
